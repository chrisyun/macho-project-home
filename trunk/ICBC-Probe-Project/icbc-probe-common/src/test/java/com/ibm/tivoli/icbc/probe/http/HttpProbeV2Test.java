package com.ibm.tivoli.icbc.probe.http;

import java.io.StringWriter;
import java.net.InetAddress;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import junit.framework.TestCase;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.IgnoreTextAndAttributeValuesDifferenceListener;

import com.ibm.tivoli.icbc.result.Result;
import com.ibm.tivoli.icbc.result.ResultFormater;
import com.ibm.tivoli.icbc.result.ResultFormaterV2;
import com.ibm.tivoli.icbc.result.http.IEBrowserResult;

public class HttpProbeV2Test extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   */
  public void testCase1() throws Exception {
    HttpProbeV2Impl probe = new HttpProbeV2Impl(new String[] { "http://www.icbc.com.cn/icbc/html/guanggao/yuntongka/images/card_mdyt_04.gif" });
    IEBrowserResult result = probe.run();
    StringWriter out = new StringWriter();
    result.toXML(out);
    System.out.println(out.toString());
  }

  /**
   */
  public void testCase2() throws Exception {
    HttpProbeV2Impl probe = new HttpProbeV2Impl(new String[] { "https://member.icbc.com.cn/icbc/html/main/images/wydl.gif" });
    IEBrowserResult result = probe.run();
    StringWriter out = new StringWriter();
    result.toXML(out);
    System.out.println(out.toString());
  }

  /**
   */
  public void testCase3() throws Exception {
    HttpProbeV2Impl probe = new HttpProbeV2Impl(new String[] { "http://www.abchina.com" });
    IEBrowserResult result = probe.run();
    StringWriter out = new StringWriter();
    result.toXML(out);
    System.out.println(out.toString());
  }

  /**
   */
  public void testCase4() throws Exception {
    HttpProbeV2Impl probe = new HttpProbeV2Impl(new String[] { "http://www.abchina.com/cn/" });
    IEBrowserResult result = probe.run();
    StringWriter out = new StringWriter();
    result.toXML(out);
    System.out.println(out.toString());
  }

  /**
   */
  public void testCase5() throws Exception {
    HttpProbeV2Impl probe = new HttpProbeV2Impl(new String[] { "http://www.abchina.com/cn/AboutABC/NewsCenter/201006/W020091201317868972715.jpg" });
    IEBrowserResult result = probe.run();
    StringWriter out = new StringWriter();
    result.toXML(out);
    System.out.println(out.toString());
  }

  /**
   */
  public void testCase6() throws Exception {
    HttpProbeV2Impl probe = new HttpProbeV2Impl(new String[] { "https://211.95.81.1/icbc/html/main/images/wydl.gif" });
    IEBrowserResult result = probe.run();
    StringWriter out = new StringWriter();
    result.toXML(out);
    System.out.println(out.toString());
  }

  public void testCase7() throws Exception {
    HttpProbeV2Impl probe = new HttpProbeV2Impl(new String[] { "http://www.icbc-ltd.com/do_not_delete/noc_cdn.gif" });
    IEBrowserResult result = probe.run();

    Date timestamp = new Date();
    StringWriter out = new StringWriter();
    ResultFormater format = new ResultFormaterV2();
    format.format(timestamp, new Result[] { result }, out);
    System.out.println(out.toString());

    String host = "www.icbc-ltd.com";
    System.out.println(host + ":" + InetAddress.getByName(host).getHostAddress());
    for (InetAddress inetAddress : InetAddress.getAllByName(host)) {
      System.out.println(host + ":" + inetAddress.getHostAddress());
    }
  }

  public void testCase8() throws Exception {
    HttpProbeV2Impl probe = new HttpProbeV2Impl(new String[] { "https://corporbank.icbc.com.cn/icbc/images/webbank.gif" });
    IEBrowserResult result = probe.run();

    Date timestamp = new Date();
    StringWriter out = new StringWriter();
    ResultFormater format = new ResultFormaterV2();
    format.format(timestamp, new Result[] { result }, out);
    System.out.println(out.toString());
  }
  
  public void testForever() throws Exception {
    for (int i = 0; i < 1000; i++) {
        this.testCaseHttp();
    }
  }

  /**
   */
  public void testCaseHttp() throws Exception {
    HttpTaskTracker lastHttpTaskTimeTracker = new HttpTaskTracker();

    HttpProbeV2Impl probe = new HttpProbeV2Impl(new String[] { "http://www.icbc.com.cn" });
    probe.setLastHttpTaskTimeTracker(lastHttpTaskTimeTracker);
    probe.setUploadScreenshot("always");
    
    Date timestamp = new Date();
    Result result = probe.run();
    StringWriter out = new StringWriter();
    ResultFormater format = new ResultFormaterV2();
    format.format(timestamp, new Result[] { result }, out);
    System.err.println(out.toString());

    Diff diff = new Diff("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<dataresult>\n" + "    <ctime>1276950697</ctime>\n" + "    <resultcontext>\n"
        + "        <ctime>1276950697</ctime>\n" + "        <type>null</type>\n" + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n"
        + "        <name>null</name>\n" + "        <request>https://member.icbc.com.cn/icbc/html/main/images/wydl.gif</request>\n" + "        <result1>54</result1>\n"
        + "        <result2>91</result2>\n" + "        <result3>1281</result3>\n" + "        <result4>200</result4>\n" + "        <result5>1383</result5>\n"
        + "        <result6>https://211.95.81.1/icbc/html/main/images/wydl.gif</result6>\n" + "    </resultcontext>\n" + "</dataresult>\n", out.toString());

    diff.overrideDifferenceListener(new IgnoreTextAndAttributeValuesDifferenceListener());

    assertTrue("XML Similar " + diff.toString(), diff.similar());
  }

  public void testCaseCA() throws Exception {
    SSLContext ctx = SSLContext.getInstance("TLS");
    ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
    SSLContext.setDefault(ctx);

    URL url = new URL("https://corporbank.icbc.com.cn/icbc/images/webbank.gif");
    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
    conn.setHostnameVerifier(new HostnameVerifier() {
      @Override
      public boolean verify(String arg0, SSLSession arg1) {
        return true;
      }
    });
    System.out.println(conn.getResponseCode());

    conn.disconnect();

  }

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
  
  public void testJxBrowserExecutorMain() throws Exception {
    JxBrowserExecutorImpl.main(new String[]{"http://www.icbc.com.cn", "200"});
  }
}
