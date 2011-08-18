/**
 * 
 */
package com.ibm.tivoli.icbc.probe.http;

import java.io.File;
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
import java.util.Date;
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

import com.ibm.tivoli.icbc.probe.Probe;
import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.probe.http.BrowserExecutor.BrowserResult;
import com.ibm.tivoli.icbc.result.http.IEBrowserResult;
import com.ibm.tivoli.icbc.result.http.PageElementResult;
import com.ibm.tivoli.icbc.result.http.TargetURL;
import com.ibm.tivoli.icbc.result.http.URLAccessResult;
import com.jniwrapper.win32.ie.Browser;

/**
 * @author Zhao Dong Lu
 * 
 */
public class HttpProbeV2Impl implements Probe<IEBrowserResult> {

  private static Log log = LogFactory.getLog(HttpProbeV2Impl.class);

  private String uploadScreenshot = "error";
  /**
   * TopN返回的最大记录数量
   */
  private int maxTopNItem = 3;

  private List<String> targetUrls = new ArrayList<String>();

  private Map<String, String> ipMap = new HashMap<String, String>();

  private int readTimeout = 60 * 1000;

  private HttpTaskTracker lastHttpTaskTimeTracker = null;

  private HtmlElementDetector htmlElementDetector = null;

  private BrowserExecutor browserExecutor;

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
  public HttpProbeV2Impl() {
    super();
  }

  public HttpProbeV2Impl(String[] urls) {
    this.targetUrls.addAll(Arrays.asList(urls));
  }

  /**
   * @return the maxTopNItem
   */
  public int getMaxTopNItem() {
    return maxTopNItem;
  }

  /**
   * @param maxTopNItem
   *          the maxTopNItem to set
   */
  public void setMaxTopNItem(int maxTopNItem) {
    this.maxTopNItem = maxTopNItem;
  }

  /**
   * @return the htmlElementDetector
   */
  public HtmlElementDetector getHtmlElementDetector() {
    return htmlElementDetector;
  }

  /**
   * @param htmlElementDetector
   *          the htmlElementDetector to set
   */
  public void setHtmlElementDetector(HtmlElementDetector htmlElementDetector) {
    this.htmlElementDetector = htmlElementDetector;
  }

  /**
   * @return the uploadScreenshot
   */
  public String getUploadScreenshot() {
    return uploadScreenshot;
  }

  /**
   * @param uploadScreenshot
   *          the uploadScreenshot to set
   */
  public void setUploadScreenshot(String uploadScreenshot) {
    this.uploadScreenshot = uploadScreenshot;
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

  /**
   * @return the lastHttpTaskTimeTracker
   */
  public HttpTaskTracker getLastHttpTaskTimeTracker() {
    return lastHttpTaskTimeTracker;
  }

  /**
   * @param lastHttpTaskTimeTracker
   *          the lastHttpTaskTimeTracker to set
   */
  public void setLastHttpTaskTimeTracker(HttpTaskTracker lastHttpTaskTimeTracker) {
    this.lastHttpTaskTimeTracker = lastHttpTaskTimeTracker;
  }

  /**
   * @return the browserExecutor
   */
  public BrowserExecutor getBrowserExecutor() {
    return browserExecutor;
  }

  /**
   * @param browserExecutor the browserExecutor to set
   */
  public void setBrowserExecutor(BrowserExecutor browserExecutor) {
    this.browserExecutor = browserExecutor;
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
  public IEBrowserResult run() throws ProbeException {
    IEBrowserResult result = new IEBrowserResult();
    result.setMaxTopNItem(this.maxTopNItem);
    for (String target : this.targetUrls) {
      TargetURL targetUrlResult = new TargetURL(target);
      result.getTargetURLs().add(targetUrlResult);
      log.info("HTTPProbe: Target URL:[" + targetUrlResult + "]");
      String serverIP = null;
      String ipURLStr = null;
      try {

        URL url = new URL(target);
        String protocol = url.getProtocol();
        String server = url.getHost();
        int port = url.getPort();
        String path = url.getPath();
        String query = url.getQuery();

        List<String> serverIPs = new ArrayList<String>();

        long dnsTime = 0;
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
        ipURLStr = StringUtils.replace(target, server, serverIP);
        log.info("HTTPProbe: Target URL:[" + target + "]:Open conn to IP URL:[" + ipURLStr + "]");

        // URL ipURL = new URL(ipURLStr);
        long netBeginTime = System.currentTimeMillis();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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

        // 模拟浏览器，获取页面加载时间
        // BrowserExecutor browserExecutor = new CmdLineBrowserExecutorImpl();
        //browserExecutor = new JxBrowserExecutorImpl();

        BrowserResult r = browserExecutor.navigate(target);
        long downloadTime = r.getElapseTime();
        log.info("HTTPProbe: Target URL:[" + target + "]:Download Time " + downloadTime + "ms");

        // 处理页面元素的下载统计
        if (this.htmlElementDetector != null) {
          try {
            PageElementResult re = this.htmlElementDetector.detect(r.getHtmlContent(), r.getLocationURL());
            r.setNumberOfDownloadElements(re.getTotalElement());
            r.setPageElementResult(re);
          } catch (Exception e) {
            log.error("fail to parsing content element", e);
          }
        }

        URLAccessResult accessResult = new URLAccessResult(ipURLStr, dnsTime, netTime, downloadTime, r.getHttpCode(), total);
        if (needToSendScreenShot(target, r)) {
          accessResult.setImageFile(r.getImageFile());
        } else {
          //(new File(r.getImageFile())).delete();
        }
        accessResult.setPageElementResult(r.getPageElementResult());
        accessResult.setDownloadElements(r.getNumberOfDownloadElements());
        // 加入到结果集中
        targetUrlResult.getAccesses().add(accessResult);

      } catch (MalformedURLException e) {
        targetUrlResult.setError("Fail to probe HTTP, cause: " + e.getMessage() + ", URL: " + target + ", resolved target url: " + ipURLStr + ", max timeout: "
            + this.readTimeout + "ms");
        log.error("Fail to probe HTTP, cause: " + e.getMessage() + ", targetURL: " + target + ", resolved ip url: " + ipURLStr, e);
      } catch (TextParseException e) {
        targetUrlResult.setError("Fail to probe HTTP, cause: " + e.getMessage() + ", URL: " + target + ", resolved target url: " + ipURLStr + ", max timeout: "
            + this.readTimeout + "ms");
        log.error("Fail to probe HTTP, cause: " + e.getMessage() + ", targetURL: " + target + ", resolved ip url: " + ipURLStr, e);
      } catch (IOException e) {
        targetUrlResult.setError("Fail to probe HTTP, cause: " + e.getMessage() + ", URL: " + target + ", resolved target url: " + ipURLStr + ", max timeout: "
            + this.readTimeout + "ms");
        log.error("Fail to probe HTTP, cause: " + e.getMessage() + ", targetURL: " + target + ", resolved ip url: " + ipURLStr, e);
      } catch (Exception e) {
        targetUrlResult.setError("Fail to probe HTTP, cause: " + e.getMessage() + ", URL: " + target + ", resolved target url: " + ipURLStr + ", max timeout: "
            + this.readTimeout + "ms");
        log.error("Fail to probe HTTP, cause: " + e.getMessage() + ", targetURL: " + target + ", resolved ip url: " + ipURLStr, e);
      } finally {
        if (this.lastHttpTaskTimeTracker != null) {
          this.lastHttpTaskTimeTracker.touch(target);
        }
      }
    }
    return result;
  }

  private boolean needToSendScreenShot(String targetURL, BrowserResult r) {
    String status = r.getHttpCode();
    if (this.uploadScreenshot == null) {
      // Default
      return false;
    } else if (this.uploadScreenshot.equalsIgnoreCase("disable")) {
      return false;
    } else if (this.uploadScreenshot.equalsIgnoreCase("always")) {
      return true;
    } else if (this.uploadScreenshot.equalsIgnoreCase("error")) {
      if (status.equalsIgnoreCase("200")) {
        return false;
      } else {
        return true;
      }
    } else if (this.uploadScreenshot.startsWith(" schedule")) {
      return isMatchSchedulePattern(targetURL, this.uploadScreenshot, r);
    } else {
      return false;
    }
  }

  private boolean isMatchSchedulePattern(String targetURL, String uploadScreenshot, BrowserResult r) {
    SchedulePatternMatcher m = new SchedulePatternMatcher(this.uploadScreenshot);
    Date lastTime = this.lastHttpTaskTimeTracker.getLast(targetURL);
    return m.match(lastTime, new Date());
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
