/**
 * 
 */
package com.ibm.tivoli.icbc.probe.dns;

import java.io.StringWriter;
import java.util.Date;

import junit.framework.TestCase;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.IgnoreTextAndAttributeValuesDifferenceListener;
import org.custommonkey.xmlunit.XMLUnit;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.Type;

import com.ibm.tivoli.icbc.result.Result;
import com.ibm.tivoli.icbc.result.ResultFormater;
import com.ibm.tivoli.icbc.result.ResultFormaterV1;
import com.ibm.tivoli.icbc.result.ResultFormaterV2;
import com.ibm.tivoli.icbc.result.dns.DNSResult;

/**
 * @author Zhao Dong Lu
 * 
 */
public class DNSProbeTest extends TestCase {

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    // Set XMLUnit Compare policy
    XMLUnit.setIgnoreWhitespace(true);

    System.getProperties().setProperty("dnsjava.options", "verbose=true");
  }

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link com.ibm.tivoli.icbc.probe.dns.CNameProbe#run()}.
   */
  public void testCNameProbe1() throws Exception {
    CNameProbe probe = new CNameProbe(new String[] { "www.icbc.com.cn", "www.icbc-ltd.com", "www.sina.com.cn", "www.ibm.com" });
    DNSResult result = probe.run();
    StringWriter out = new StringWriter();
    result.toXML(out);
    System.out.println(out.toString());
  }

  /**
   * Test method for {@link com.ibm.tivoli.icbc.probe.dns.CNameProbe#run()}.
   */
  public void testCNameProbeV2() throws Exception {
    CNameProbeV2 probe = new CNameProbeV2(new String[] { "www.icbc.com.cn", "www.icbc-ltd.com", "www.sina.com.cn", "www.ibm.com" });
    DNSResult result = probe.run();
    StringWriter out = new StringWriter();
    result.toXML(out);
    System.out.println(out.toString());
  }

  /**
   * Test method for {@link com.ibm.tivoli.icbc.probe.dns.CNameProbe#run()}.
   */
  public void testCNameProbe11() throws Exception {
    CNameProbe probe = new CNameProbe(new String[] { "www.icbc.com.cn" });
    DNSResult result = probe.run();
    StringWriter out = new StringWriter();
    result.toXML(out);
    System.out.println(out.toString());

    ResultFormaterV2 rf = new ResultFormaterV2();
    StringWriter writer = new StringWriter();
    rf.format(new Date(), new Result[] { result }, writer);
    System.out.println(writer.toString());
  }

  /**
   * Test method for {@link com.ibm.tivoli.icbc.probe.dns.CNameProbe#run()}.
   */
  public void testCNameProbeV211() throws Exception {
    CNameProbeV2 probe = new CNameProbeV2(new String[] { "www.icbc.com.cn" });
    DNSResult result = probe.run();

    ResultFormaterV2 rf = new ResultFormaterV2();
    StringWriter writer = new StringWriter();
    rf.format(new Date(), new Result[] { result }, writer);
    System.out.println(writer.toString());
  }

  /**
   * Test method for {@link com.ibm.tivoli.icbc.probe.dns.CNameProbe#run()}.
   */
  public void testCNameProbe2() throws Exception {
    CNameProbe probe = new CNameProbe(new String[] { "www.icbc.com.cn", "www.icbc-ltd.com", "www.sina.com.cn", "www.ibm.com" });
    Date timestamp = new Date();
    DNSResult result = probe.run();
    StringWriter out = new StringWriter();
    ResultFormater formatter = new ResultFormaterV1();
    formatter.format(timestamp, new Result[] { result }, out);
    System.out.println(out.toString());

    Diff diff = new Diff("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<dataresult>\n" + "    <ctime>1276950444</ctime>\n" + "    <resultcontext>\n"
        + "        <ctime>1276950444</ctime>\n" + "        <type>DNS_CNAME</type>\n" + "        <btype>null</btype>\n"
        + "        <businessId>null</businessId>\n" + "        <name>null</name>\n" + "        <request>www.icbc.com.cn</request>\n"
        + "        <result1></result1>\n" + "        <result2></result2>\n" + "        <result3>211.95.81.1</result3>\n" + "    </resultcontext>\n"
        + "    <resultcontext>\n" + "        <ctime>1276950444</ctime>\n" + "        <type>DNS_CNAME</type>\n" + "        <btype>null</btype>\n"
        + "        <businessId>null</businessId>\n" + "        <name>null</name>\n" + "        <request>www.icbc-ltd.com</request>\n"
        + "        <result1></result1>\n" + "        <result2></result2>\n" + "        <result3>211.95.81.6</result3>\n" + "    </resultcontext>\n"
        + "    <resultcontext>\n" + "        <ctime>1276950444</ctime>\n" + "        <type>DNS_CNAME</type>\n" + "        <btype>null</btype>\n"
        + "        <businessId>null</businessId>\n" + "        <name>null</name>\n" + "        <request>www.sina.com.cn</request>\n"
        + "        <result1></result1>\n" + "        <result2></result2>\n" + "        <result3></result3>\n" + "    </resultcontext>\n"
        + "    <resultcontext>\n" + "        <ctime>1276950444</ctime>\n" + "        <type>DNS_CNAME</type>\n" + "        <btype>null</btype>\n"
        + "        <businessId>null</businessId>\n" + "        <name>null</name>\n" + "        <request>www.ibm.com</request>\n"
        + "        <result1>www-int.ibm.com.cs186.net.</result1>\n" + "        <result2></result2>\n" + "        <result3></result3>\n"
        + "    </resultcontext>\n" + "</dataresult>\n", out.toString());

    diff.overrideDifferenceListener(new IgnoreTextAndAttributeValuesDifferenceListener());

    assertTrue("XML Similar " + diff.toString(), diff.similar());
    // assertTrue("XML Identical " + diff.toString(), diff.identical());
  }

  /**
   * Test method for {@link com.ibm.tivoli.icbc.probe.dns.CNameProbe#run()}.
   */
  public void testNSProbe() throws Exception {
    NSProbe probe = new NSProbe(new String[] { "icbc.com.cn", "icbc-ltd.com", "sina.com.cn", "ibm.com" });
    DNSResult result = probe.run();
    StringWriter out = new StringWriter();
    result.toXML(out);
    System.out.println(out.toString());
  }

  /**
   * Test method for {@link com.ibm.tivoli.icbc.probe.dns.CNameProbe#run()}.
   */
  public void testNSProbe2() throws Exception {
    NSProbe probe = new NSProbe(new String[] { "icbc.com.cn", "icbc-ltd.com", "sina.com.cn", "ibm.com" });
    Date timestamp = new Date();
    DNSResult result = probe.run();
    StringWriter out = new StringWriter();
    ResultFormater formatter = new ResultFormaterV1();
    formatter.format(timestamp, new Result[] { result }, out);
    System.out.println(out.toString());

    Diff diff = new Diff("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<dataresult>\n" + "    <ctime>1276950337</ctime>\n" + "    <resultcontext>\n"
        + "        <ctime>1276950337</ctime>\n" + "        <type>DNS_NS</type>\n" + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n"
        + "        <name>null</name>\n" + "        <request>icbc.com.cn</request>\n"
        + "        <result1>60.247.99.245,202.106.83.125,219.142.91.125,61.129.61.245,211.95.81.245,123.127.121.245</result1>\n" + "    </resultcontext>\n"
        + "    <resultcontext>\n" + "        <ctime>1276950338</ctime>\n" + "        <type>DNS_NS</type>\n" + "        <btype>null</btype>\n"
        + "        <businessId>null</businessId>\n" + "        <name>null</name>\n" + "        <request>icbc-ltd.com</request>\n"
        + "        <result1>219.142.91.125,202.106.83.125</result1>\n" + "    </resultcontext>\n" + "    <resultcontext>\n"
        + "        <ctime>1276950338</ctime>\n" + "        <type>DNS_NS</type>\n" + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n"
        + "        <name>null</name>\n" + "        <request>sina.com.cn</request>\n"
        + "        <result1>61.172.201.254,202.108.44.55,202.106.184.166</result1>\n" + "    </resultcontext>\n" + "    <resultcontext>\n"
        + "        <ctime>1276950338</ctime>\n" + "        <type>DNS_NS</type>\n" + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n"
        + "        <name>null</name>\n" + "        <request>ibm.com</request>\n" + "        <result1>9.14.1.3,9.78.1.2,9.46.1.2</result1>\n"
        + "    </resultcontext>\n" + "</dataresult>\n", out.toString());

    diff.overrideDifferenceListener(new IgnoreTextAndAttributeValuesDifferenceListener());

    assertTrue("XML Similar " + diff.toString(), diff.similar());
  }

  /**
   * Test method for {@link com.ibm.tivoli.icbc.probe.dns.CNameProbe#run()}.
   */
  public void testNSAndARecordProbe() throws Exception {
    NSAndARecordProbe probe = new NSAndARecordProbe(new String[] { "www.icbc.com.cn", "www.icbc-ltd.com", "www.sina.com.cn", "www.ibm.com" });
    DNSResult result = probe.run();
    StringWriter out = new StringWriter();
    result.toXML(out);
    System.out.println(out.toString());
  }

  /**
   * Test method for {@link com.ibm.tivoli.icbc.probe.dns.CNameProbe#run()}.
   */
  public void testNSAndARecordProbe2() throws Exception {
    NSAndARecordProbe probe = new NSAndARecordProbe(new String[] { "www.icbc.com.cn", "www.icbc-ltd.com", "www.sina.com.cn", "www.ibm.com" });
    Date timestamp = new Date();
    DNSResult result = probe.run();
    StringWriter out = new StringWriter();
    ResultFormater formatter = new ResultFormaterV2();
    formatter.format(timestamp, new Result[] { result }, out);
    System.out.println(out.toString());

    Diff diff = new Diff("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<dataresult>\n" + "    <ctime>1276950176</ctime>\n" + "    <resultcontext>\n"
        + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n" + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n"
        + "        <name>null</name>\n" + "        <request>www.icbc.com.cn</request>\n" + "        <result1>219.142.91.125</result1>\n"
        + "        <result1>60.247.99.1</result1>\n" + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n"
        + "        <type>DNS_A</type>\n" + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.icbc.com.cn</request>\n" + "        <result1>61.129.61.245</result1>\n" + "        <result1>60.247.99.1</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.icbc.com.cn</request>\n" + "        <result1>211.95.81.245</result1>\n" + "        <result1>60.247.99.1</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.icbc.com.cn</request>\n" + "        <result1>123.127.121.245</result1>\n" + "        <result1>60.247.99.1</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.icbc.com.cn</request>\n" + "        <result1>60.247.99.245</result1>\n" + "        <result1>60.247.99.1</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.icbc.com.cn</request>\n" + "        <result1>202.106.83.125</result1>\n" + "        <result1>60.247.99.1</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>61.172.201.254</result1>\n" + "        <result1>60.215.128.131</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>61.172.201.254</result1>\n" + "        <result1>60.215.128.132</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>61.172.201.254</result1>\n" + "        <result1>60.215.128.133</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>61.172.201.254</result1>\n" + "        <result1>60.215.128.134</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>61.172.201.254</result1>\n" + "        <result1>60.215.128.135</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>61.172.201.254</result1>\n" + "        <result1>60.215.128.136</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>61.172.201.254</result1>\n" + "        <result1>60.215.128.137</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>61.172.201.254</result1>\n" + "        <result1>60.215.128.138</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>61.172.201.254</result1>\n" + "        <result1>60.215.128.139</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>61.172.201.254</result1>\n" + "        <result1>60.215.128.140</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>61.172.201.254</result1>\n" + "        <result1>60.215.128.141</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>61.172.201.254</result1>\n" + "        <result1>60.215.128.142</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>61.172.201.254</result1>\n" + "        <result1>60.215.128.143</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>61.172.201.254</result1>\n" + "        <result1>60.215.128.144</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>61.172.201.254</result1>\n" + "        <result1>60.215.128.145</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>61.172.201.254</result1>\n" + "        <result1>60.215.128.146</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.108.44.55</result1>\n" + "        <result1>60.215.128.137</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.108.44.55</result1>\n" + "        <result1>60.215.128.138</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.108.44.55</result1>\n" + "        <result1>60.215.128.139</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.108.44.55</result1>\n" + "        <result1>60.215.128.140</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.108.44.55</result1>\n" + "        <result1>60.215.128.141</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.108.44.55</result1>\n" + "        <result1>60.215.128.142</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.108.44.55</result1>\n" + "        <result1>60.215.128.143</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.108.44.55</result1>\n" + "        <result1>60.215.128.144</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.108.44.55</result1>\n" + "        <result1>60.215.128.145</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.108.44.55</result1>\n" + "        <result1>60.215.128.146</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.108.44.55</result1>\n" + "        <result1>60.215.128.147</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.108.44.55</result1>\n" + "        <result1>60.215.128.148</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.108.44.55</result1>\n" + "        <result1>60.215.128.149</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.108.44.55</result1>\n" + "        <result1>60.215.128.128</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.108.44.55</result1>\n" + "        <result1>60.215.128.123</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.108.44.55</result1>\n" + "        <result1>60.215.128.124</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.106.184.166</result1>\n" + "        <result1>60.215.128.134</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.106.184.166</result1>\n" + "        <result1>60.215.128.135</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.106.184.166</result1>\n" + "        <result1>60.215.128.136</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.106.184.166</result1>\n" + "        <result1>60.215.128.137</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.106.184.166</result1>\n" + "        <result1>60.215.128.138</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.106.184.166</result1>\n" + "        <result1>60.215.128.139</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.106.184.166</result1>\n" + "        <result1>60.215.128.140</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.106.184.166</result1>\n" + "        <result1>60.215.128.141</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.106.184.166</result1>\n" + "        <result1>60.215.128.142</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.106.184.166</result1>\n" + "        <result1>60.215.128.143</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.106.184.166</result1>\n" + "        <result1>60.215.128.144</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.106.184.166</result1>\n" + "        <result1>60.215.128.145</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.106.184.166</result1>\n" + "        <result1>60.215.128.146</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.106.184.166</result1>\n" + "        <result1>60.215.128.147</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.106.184.166</result1>\n" + "        <result1>60.215.128.148</result1>\n"
        + "    </resultcontext>\n" + "    <resultcontext>\n" + "        <ctime>1276950176</ctime>\n" + "        <type>DNS_A</type>\n"
        + "        <btype>null</btype>\n" + "        <businessId>null</businessId>\n" + "        <name>null</name>\n"
        + "        <request>www.sina.com.cn</request>\n" + "        <result1>202.106.184.166</result1>\n" + "        <result1>60.215.128.149</result1>\n"
        + "    </resultcontext>\n" + "</dataresult>\n", out.toString());

    diff.overrideDifferenceListener(new IgnoreTextAndAttributeValuesDifferenceListener());

    assertTrue("XML Similar " + diff.toString(), diff.similar());
  }

  /**
   * Test method for {@link com.ibm.tivoli.icbc.probe.dns.CNameProbe#run()}.
   */
  public void testNSAndARecordProbe3() throws Exception {
    NSAndARecordProbeV2 probe = new NSAndARecordProbeV2(new String[] { "www.icbc.com.cn" });
    DNSResult result = probe.run();
    StringWriter out = new StringWriter();
    result.toXML(out);
    System.out.println(out.toString());
  }

  public void testDNSAOutput() throws Exception {
    NSAndARecordProbeV2 probe = new NSAndARecordProbeV2(new String[] { "www.icbc.com.cn", "mybank.icbc.com.cn", "www.icbc-ltd.com" });
    Date timestamp = new Date();
    DNSResult result = probe.run();
    StringWriter out = new StringWriter();
    ResultFormater formatter = new ResultFormaterV2();
    formatter.format(timestamp, new Result[] { result }, out);
    System.out.println(out.toString());
  }

  public void testDNSARecord() throws Exception {
    Lookup lookupTarget = new Lookup("www.icbc.com.cn", Type.A);
    lookupTarget.setCache(null);
    for (int i = 0; i < 20; i++) {
      lookupTarget.setResolver(new SimpleResolver());
      long begin = System.currentTimeMillis();
      Record[] rs1 = lookupTarget.run();
      long end = System.currentTimeMillis();
      if (rs1 != null) {
        assertEquals(1, rs1.length);
        if (rs1[0] instanceof ARecord) {
           ARecord ar = (ARecord)rs1[0];
           assertEquals("221.130.23.236", ar.getAddress().getHostAddress());
        }
        
      }
      System.out.println(String.format("Elapsed time: %s", (end - begin)));
    }

  }

}
