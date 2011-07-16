/**
 * 
 */
package com.ibm.tivoli.icbc.probe.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import com.ibm.tivoli.icbc.probe.HttpProbe;
import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.result.http.HttpResult;
import com.ibm.tivoli.icbc.result.http.TargetURL;
import com.ibm.tivoli.icbc.result.http.URLAccessResult;

/**
 * @author Zhao Dong Lu
 * 
 */
public class HttpProbeImpl implements HttpProbe {

  private static Log log = LogFactory.getLog(HttpProbeImpl.class);

  private List<String> targetUrls = new ArrayList<String>();

  private Map<String, String> ipMap = new HashMap<String, String>();

  private int readTimeout = 60 * 1000;

  private boolean resolvIPAddress = true;

  private static class DefaultTrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
      return null;
    }
  }

  static {
    try {
      SSLContext ctx = SSLContext.getInstance("TLS");
      ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
      SSLContext.setDefault(ctx);
    } catch (Throwable tx) {
      log.fatal("Could not reset TLS TrustManager, cause: " + tx.getMessage(), tx);
    }
  }

  /**
   * 
   */
  public HttpProbeImpl() {
    super();
  }

  public HttpProbeImpl(String[] urls) {
    this.targetUrls.addAll(Arrays.asList(urls));
  }

  public List<String> getTargetUrls() {
    return targetUrls;
  }

  public void setTargetUrls(List<String> targetUrls) {
    this.targetUrls = targetUrls;
  }

  public Map<String, String> getIpMap() {
    return ipMap;
  }

  public void setIpMap(Map<String, String> ipMap) {
    this.ipMap = ipMap;
  }

  /**
   * @return the resolvIPAddress
   */
  public boolean isResolvIPAddress() {
    return resolvIPAddress;
  }

  /**
   * @param resolvIPAddress the resolvIPAddress to set
   */
  public void setResolvIPAddress(boolean resolvIPAddress) {
    this.resolvIPAddress = resolvIPAddress;
  }

  public void setIpTables(List<String> tables) {
    for (String line : tables) {
      String[] vs = StringUtils.split(line, ",");
      if (vs != null && vs.length == 2) {
        this.ipMap.put(vs[0].trim(), vs[1].trim());
      } else {
        throw new RuntimeException("Parsing IP tables error, source line: " + line);
      }
    }
  }

  public int getReadTimeout() {
    return readTimeout;
  }

  public void setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout;
  }

  private static boolean isIPAddress(String s) {
    String regex0 = "(2[0-4]\\d)" + "|(25[0-5])";
    String regex1 = "1\\d{2}";
    String regex2 = "[1-9]\\d";
    String regex3 = "\\d";
    String regex = "(" + regex0 + ")|(" + regex1 + ")|(" + regex2 + ")|(" + regex3 + ")";
    regex = "(" + regex + ").(" + regex + ").(" + regex + ").(" + regex + ")";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(s);
    return m.matches();
  }

  @Override
  public HttpResult run() throws ProbeException {
    HttpResult result = new HttpResult();
    for (String target : this.targetUrls) {
      TargetURL targetUrlResult = new TargetURL(target);
      result.getTargetURLs().add(targetUrlResult);
      log.info("HTTPProbe: Target URL:[" + targetUrlResult + "]");
      String serverIP = null;
      String targetURLStr = target;
      try {

        URL url = new URL(target);
        String protocol = url.getProtocol();
        String server = url.getHost();
        int port = url.getPort();
        String path = url.getPath();
        String query = url.getQuery();

        List<String> serverIPs = new ArrayList<String>();

        long dnsTime = 0;
        if (this.resolvIPAddress) {
          if (!isIPAddress(server)) {
            // is hostname, resolv it
            log.info("HTTPProbe: Target URL:[" + target + "]:lookup DNA A for [" + server + "]");
            long dnsBeginTime = System.currentTimeMillis();
            // resolvIPByLookup(target, targetUrlResult, server, serverIPs);
            resolvIPByNative(target, targetUrlResult, server, serverIPs);

            long endDNSLookup = System.currentTimeMillis();
            dnsTime = endDNSLookup - dnsBeginTime;
            log.info("HTTPProbe: Target URL:[" + target + "]:DNS Time " + dnsTime + "ms for URL: " + url);
          } else {
            // is ip address
            serverIPs.add(server);
          }

          if (serverIPs == null || serverIPs.size() == 0) {
            log.error("could not resolv ip address for Target URL:[" + target);
            targetUrlResult.setError("could not resolv ip address for Target URL:[" + target);
            continue;
          }

          log.info("HTTPProbe: Target URL:[" + target + "]:Get DNS resolved result:[" + serverIPs + "]");
          serverIP = serverIPs.get(0);
          targetURLStr = StringUtils.replace(target, server, serverIP);
        }

        log.info("HTTPProbe: Target URL:[" + target + "]:Open conn to IP URL:[" + targetURLStr + "]");
        URL targetURL = new URL(targetURLStr);
        long netBeginTime = System.currentTimeMillis();
        HttpURLConnection conn = (HttpURLConnection) targetURL.openConnection();
        conn.setReadTimeout(this.readTimeout);

        // Add Header
        conn.addRequestProperty("Host", server);
        conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3 ( .NET CLR 3.5.30729)");
        conn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        conn.addRequestProperty("Accept-Language", "en-us,en;q=0.5");
        conn.addRequestProperty("Accept-Encoding", "gzip,deflate");
        conn.addRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");

        if (conn instanceof HttpsURLConnection) {

          HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
          httpsConn.setHostnameVerifier(new HostnameVerifier() {

            @Override
            public boolean verify(String hostname, SSLSession session) {
              return true;
            }
          });
        }

        long downloadBeginTime = System.currentTimeMillis();
        InputStream in = null;
        while (true) {
          try {
            in = conn.getInputStream();
            break;
          } catch (Exception e) {
            if (System.currentTimeMillis() - this.readTimeout > downloadBeginTime) {
              throw e;
            }
            Thread.sleep(100);
          }
        }

        int total = 0;
        byte[] buf = new byte[512];
        int len = in.read(buf);

        long netEndTime = System.currentTimeMillis();
        long netTime = netEndTime - netBeginTime;
        log.info("HTTPProbe: Target URL:[" + target + "]:NET Time " + netTime + "ms");

        while (len > 0) {
          // System.out.print(new String(buf, 0, len));
          total += len;
          len = in.read(buf);
        }
        in.close();
        long downloadEndTime = System.currentTimeMillis();

        long downloadTime = downloadEndTime - downloadBeginTime;
        log.info("HTTPProbe: Target URL:[" + target + "]:Download Time " + downloadTime + "ms");

        URLAccessResult accessResult = new URLAccessResult(targetURLStr, dnsTime, netTime, downloadTime, "" + conn.getResponseCode(), total);
        targetUrlResult.getAccesses().add(accessResult);

      } catch (MalformedURLException e) {
        targetUrlResult.setError("Fail to probe HTTP, cause: " + e.getMessage() + ", URL: " + target + ", resolved target url: " + targetURLStr
            + ", max timeout: " + this.readTimeout + "ms");
        log.error("Fail to probe HTTP, cause: " + e.getMessage() + ", targetURL: " + target + ", resolved ip url: " + targetURLStr, e);
      } catch (TextParseException e) {
        targetUrlResult.setError("Fail to probe HTTP, cause: " + e.getMessage() + ", URL: " + target + ", resolved target url: " + targetURLStr
            + ", max timeout: " + this.readTimeout + "ms");
        log.error("Fail to probe HTTP, cause: " + e.getMessage() + ", targetURL: " + target + ", resolved ip url: " + targetURLStr, e);
      } catch (IOException e) {
        targetUrlResult.setError("Fail to probe HTTP, cause: " + e.getMessage() + ", URL: " + target + ", resolved target url: " + targetURLStr
            + ", max timeout: " + this.readTimeout + "ms");
        log.error("Fail to probe HTTP, cause: " + e.getMessage() + ", targetURL: " + target + ", resolved ip url: " + targetURLStr, e);
      } catch (Exception e) {
        targetUrlResult.setError("Fail to probe HTTP, cause: " + e.getMessage() + ", URL: " + target + ", resolved target url: " + targetURLStr
            + ", max timeout: " + this.readTimeout + "ms");
        log.error("Fail to probe HTTP, cause: " + e.getMessage() + ", targetURL: " + target + ", resolved ip url: " + targetURLStr, e);
      }
    }
    return result;
  }

  /**
   * @param target
   * @param targetUrlResult
   * @param server
   * @param serverIPs
   * @throws TextParseException
   */
  private void resolvIPByLookup(String target, TargetURL targetUrlResult, String server, List<String> serverIPs) throws TextParseException {
    Lookup lookup = new Lookup(server, Type.A);
    lookup.setCache(null);

    Record[] records = lookup.run();
    if (records == null || records.length == 0) {
      targetUrlResult.setError("Could not resolv HTTP server: " + server);
    } else {

      for (Record record : records) {
        ARecord r = (ARecord) record;
        String serverIPAddr = r.getAddress().getHostAddress();
        log.info("HTTPProbe: Target URL:[" + target + "]:DNS Lookup [" + server + "]->[" + serverIPAddr + "]");
        if (this.ipMap.containsKey(serverIPAddr)) {
          log.info("HTTPProbe: Target URL:[" + target + "]:IP Mapping table [" + serverIPAddr + "]->[" + this.ipMap.get(serverIPAddr) + "]");
          serverIPAddr = this.ipMap.get(serverIPAddr);
        }
        serverIPs.add(serverIPAddr);
      }
    }
  }

  /**
   * @param target
   * @param targetUrlResult
   * @param server
   * @param serverIPs
   * @throws TextParseException
   */
  private void resolvIPByNative(String target, TargetURL targetUrlResult, String server, List<String> serverIPs) throws TextParseException {
    InetAddress[] records = null;
    try {
      records = InetAddress.getAllByName(server);
    } catch (UnknownHostException e) {
      log.error("fail to resolv: " + server + ", cause: " + e.getMessage(), e);
    }
    if (records == null || records.length == 0) {
      targetUrlResult.setError("Could not resolv HTTP server: " + server);
    } else {

      for (InetAddress inetAddress : records) {
        String serverIPAddr = inetAddress.getHostAddress();
        log.info("HTTPProbe: Target URL:[" + target + "]:DNS Lookup [" + server + "]->[" + serverIPAddr + "]");
        if (this.ipMap.containsKey(serverIPAddr)) {
          log.info("HTTPProbe: Target URL:[" + target + "]:IP Mapping table [" + serverIPAddr + "]->[" + this.ipMap.get(serverIPAddr) + "]");
          serverIPAddr = this.ipMap.get(serverIPAddr);
        }
        serverIPs.add(serverIPAddr);
      }
    }
  }

}
