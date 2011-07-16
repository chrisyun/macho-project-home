/**
 * 
 */
package com.ibm.tivoli.icbc.ws;

import java.io.StringReader;

import junit.framework.TestCase;

import com.ibm.tivoli.icbc.probe.task.TaskConfig;
import com.ibm.tivoli.icbc.probe.task.TaskConfigFactory;

/**
 * @author Zhao Dong Lu
 *
 */
public class ProbeServiceFrontEndV2Test extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testCase1() throws Exception {
    ProbeServiceFrontEndV2 client = new ProbeServiceFrontEndV2();
    client.setSoapAddress("http://202.106.83.84:80/EBankMonitor/services/ProbeServiceImplService");
    Response response = client.submitXMLData("<dataresult>\n" +
        "    <ctime>1279205899</ctime>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_CNAME</type>\n" +
        "        <btype>btype_dns_01</btype>\n" +
        "        <businessId>DNS_01</businessId>\n" +
        "        <name>2.2.1.1 探测门户Cache站点DNS服务</name>\n" +
        "        <request>www.icbc.com.cn</request>\n" +
        "        <result1>cdn.icbc.com.chinacache.net.</result1>\n" +
        "        <result2>cc00052.h.cncssr.chinacache.net.</result2>\n" +
        "        <result3>123.125.162.58,202.108.251.167</result3>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_CNAME</type>\n" +
        "        <btype>btype_dns_02</btype>\n" +
        "        <businessId>DNS_02</businessId>\n" +
        "        <name>2.2.1.2 探测国际化Cache站点DNS服务</name>\n" +
        "        <request>www.icbc-ltd.com</request>\n" +
        "        <result1>cdn.icbcltd.com.chinacache.net.</result1>\n" +
        "        <result2>cc00052.h.cncssr.chinacache.net.</result2>\n" +
        "        <result3>123.125.162.58,202.108.251.167</result3>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_NS</type>\n" +
        "        <btype>btype_dns_03</btype>\n" +
        "        <businessId>DNS_03</businessId>\n" +
        "        <name>2.2.1.3 探测NS记录</name>\n" +
        "        <request>icbc.com.cn</request>\n" +
        "        <result1>61.129.61.245</result1>\n" +
        "        <result2>60.247.99.245</result2>\n" +
        "        <result3>202.106.83.125</result3>\n" +
        "        <result4>211.95.81.245</result4>\n" +
        "        <result5>219.142.91.125</result5>\n" +
        "        <result6>123.127.121.245</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_A</type>\n" +
        "        <btype>btype_dns_04</btype>\n" +
        "        <businessId>DNS_04</businessId>\n" +
        "        <name>2.2.1.4 探测源站门户网银域名a记录</name>\n" +
        "        <request>www.icbc.com.cn</request>\n" +
        "        <result1>211.95.81.1</result1>\n" +
        "        <result2>211.95.81.1</result2>\n" +
        "        <result3>211.95.81.1</result3>\n" +
        "        <result4>211.95.81.1</result4>\n" +
        "        <result5>211.95.81.1</result5>\n" +
        "        <result6>211.95.81.1</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_A</type>\n" +
        "        <btype>btype_dns_05</btype>\n" +
        "        <businessId>DNS_05</businessId>\n" +
        "        <name>2.2.1.5 探测源站个人网银域名a记录</name>\n" +
        "        <request>mybank.icbc.com.cn</request>\n" +
        "        <result1>211.95.81.2</result1>\n" +
        "        <result2>211.95.81.2</result2>\n" +
        "        <result3>211.95.81.2</result3>\n" +
        "        <result4>211.95.81.2</result4>\n" +
        "        <result5>211.95.81.2</result5>\n" +
        "        <result6>211.95.81.2</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_A</type>\n" +
        "        <btype>btype_dns_06</btype>\n" +
        "        <businessId>DNS_06</businessId>\n" +
        "        <name>2.2.1.6 探测源站国际化网银域名a记录</name>\n" +
        "        <request>www.icbc-ltd.com</request>\n" +
        "        <result1>fail</result1>\n" +
        "        <result2>fail</result2>\n" +
        "        <result3>fail</result3>\n" +
        "        <result4>fail</result4>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_A</type>\n" +
        "        <btype>btype_dns_07</btype>\n" +
        "        <businessId>DNS_07</businessId>\n" +
        "        <name>2.2.1.7 探测源站企业网银域名a记录</name>\n" +
        "        <request>corporbank.icbc.com.cn</request>\n" +
        "        <result1>211.95.81.4</result1>\n" +
        "        <result2>211.95.81.4</result2>\n" +
        "        <result3>211.95.81.4</result3>\n" +
        "        <result4>211.95.81.4</result4>\n" +
        "        <result5>211.95.81.4</result5>\n" +
        "        <result6>211.95.81.4</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_A</type>\n" +
        "        <btype>btype_dns_08</btype>\n" +
        "        <businessId>DNS_08</businessId>\n" +
        "        <name>2.2.1.8 探测源站b2c网银域名a记录</name>\n" +
        "        <request>b2c.icbc.com.cn</request>\n" +
        "        <result1>211.95.81.41</result1>\n" +
        "        <result2>211.95.81.41</result2>\n" +
        "        <result3>211.95.81.41</result3>\n" +
        "        <result4>211.95.81.41</result4>\n" +
        "        <result5>211.95.81.41</result5>\n" +
        "        <result6>211.95.81.41</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_A</type>\n" +
        "        <btype>btype_dns_09</btype>\n" +
        "        <businessId>DNS_09</businessId>\n" +
        "        <name>2.2.1.9 探测源站金融电脑杂志社网银域名a记录</name>\n" +
        "        <request>bbs.fcc.com.cn</request>\n" +
        "        <result1>123.127.135.195</result1>\n" +
        "        <result2>123.127.135.195</result2>\n" +
        "        <result3>123.127.135.195</result3>\n" +
        "        <result4>123.127.135.195</result4>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_A</type>\n" +
        "        <btype>btype_dns_10</btype>\n" +
        "        <businessId>DNS_10</businessId>\n" +
        "        <name>2.2.1.10    探测源站银企互联网银域名a记录</name>\n" +
        "        <request>directbank.icbc.com.cn</request>\n" +
        "        <result1>211.95.81.5</result1>\n" +
        "        <result2>211.95.81.5</result2>\n" +
        "        <result3>211.95.81.5</result3>\n" +
        "        <result4>211.95.81.5</result4>\n" +
        "        <result5>211.95.81.5</result5>\n" +
        "        <result6>211.95.81.5</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_A</type>\n" +
        "        <btype>btype_dns_11</btype>\n" +
        "        <businessId>DNS_11</businessId>\n" +
        "        <name>2.2.1.11    探测源站银企互联网银域名a记录</name>\n" +
        "        <request>directbank.icbc.com.cn</request>\n" +
        "        <result1>211.95.81.5</result1>\n" +
        "        <result2>211.95.81.5</result2>\n" +
        "        <result3>211.95.81.5</result3>\n" +
        "        <result4>211.95.81.5</result4>\n" +
        "        <result5>211.95.81.5</result5>\n" +
        "        <result6>211.95.81.5</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_A</type>\n" +
        "        <btype>btype_dns_12</btype>\n" +
        "        <businessId>DNS_12</businessId>\n" +
        "        <name>2.2.1.12    探测源站MailRelay网银域名a记录</name>\n" +
        "        <request>mx1.icbc.com.cn</request>\n" +
        "        <result1>211.95.81.195</result1>\n" +
        "        <result2>211.95.81.195</result2>\n" +
        "        <result3>211.95.81.195</result3>\n" +
        "        <result4>211.95.81.195</result4>\n" +
        "        <result5>211.95.81.195</result5>\n" +
        "        <result6>211.95.81.195</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_A</type>\n" +
        "        <btype>btype_dns_13</btype>\n" +
        "        <businessId>DNS_13</businessId>\n" +
        "        <name>2.2.1.13    探测源站WAP普通版业务域名a记录</name>\n" +
        "        <request>mywap.icbc.com.cn</request>\n" +
        "        <result1>211.95.81.49</result1>\n" +
        "        <result2>211.95.81.49</result2>\n" +
        "        <result3>211.95.81.49</result3>\n" +
        "        <result4>211.95.81.49</result4>\n" +
        "        <result5>211.95.81.49</result5>\n" +
        "        <result6>211.95.81.49</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_A</type>\n" +
        "        <btype>btype_dns_14</btype>\n" +
        "        <businessId>DNS_14</businessId>\n" +
        "        <name>2.2.1.14    探测源站WAP 3G版业务域名a记录</name>\n" +
        "        <request>mywap2.icbc.com.cn</request>\n" +
        "        <result1>211.95.81.51</result1>\n" +
        "        <result2>211.95.81.51</result2>\n" +
        "        <result3>211.95.81.51</result3>\n" +
        "        <result4>211.95.81.51</result4>\n" +
        "        <result5>211.95.81.51</result5>\n" +
        "        <result6>211.95.81.51</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_A</type>\n" +
        "        <btype>btype_dns_15</btype>\n" +
        "        <businessId>DNS_15</businessId>\n" +
        "        <name>2.2.1.15    探测源站BBS网银域名a记录</name>\n" +
        "        <request>service.icbc.com.cn</request>\n" +
        "        <result1>211.95.81.3</result1>\n" +
        "        <result2>211.95.81.3</result2>\n" +
        "        <result3>211.95.81.3</result3>\n" +
        "        <result4>211.95.81.3</result4>\n" +
        "        <result5>211.95.81.3</result5>\n" +
        "        <result6>fail</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_A</type>\n" +
        "        <btype>btype_dns_16</btype>\n" +
        "        <businessId>DNS_16</businessId>\n" +
        "        <name>2.2.1.16    探测源站贵宾室网银域名a记录</name>\n" +
        "        <request>vip.icbc.com.cn</request>\n" +
        "        <result1>211.95.81.46</result1>\n" +
        "        <result2>211.95.81.46</result2>\n" +
        "        <result3>211.95.81.46</result3>\n" +
        "        <result4>211.95.81.46</result4>\n" +
        "        <result5>211.95.81.46</result5>\n" +
        "        <result6>211.95.81.46</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_A</type>\n" +
        "        <btype>btype_dns_17</btype>\n" +
        "        <businessId>DNS_17</businessId>\n" +
        "        <name>2.2.1.17    探测源站WAP普通版门户域名a记录</name>\n" +
        "        <request>wap.icbc.com.cn</request>\n" +
        "        <result1>211.95.81.48</result1>\n" +
        "        <result2>211.95.81.48</result2>\n" +
        "        <result3>211.95.81.48</result3>\n" +
        "        <result4>211.95.81.48</result4>\n" +
        "        <result5>211.95.81.48</result5>\n" +
        "        <result6>211.95.81.48</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205845</ctime>\n" +
        "        <type>DNS_A</type>\n" +
        "        <btype>btype_dns_18</btype>\n" +
        "        <businessId>DNS_18</businessId>\n" +
        "        <name>2.2.1.18    探测源站WAP 3G版门户域名a记录</name>\n" +
        "        <request>wap2.icbc.com.cn</request>\n" +
        "        <result1>211.95.81.50</result1>\n" +
        "        <result2>211.95.81.50</result2>\n" +
        "        <result3>211.95.81.50</result3>\n" +
        "        <result4>211.95.81.50</result4>\n" +
        "        <result5>211.95.81.50</result5>\n" +
        "        <result6>211.95.81.50</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205860</ctime>\n" +
        "        <type>HTTPS_DNS_MODE</type>\n" +
        "        <btype>HTTPS_01</btype>\n" +
        "        <businessId>HTTPS_01</businessId>\n" +
        "        <name>2.2.3.1 探测源站个人网银HTTPS服务</name>\n" +
        "        <request>https://mybank.icbc.com.cn/icbc/newperbank/images/login_05.gif</request>\n" +
        "        <result1>78</result1>\n" +
        "        <result2>94</result2>\n" +
        "        <result3>94</result3>\n" +
        "        <result4>200</result4>\n" +
        "        <result5>7677</result5>\n" +
        "        <result6>https://202.108.88.8/icbc/newperbank/images/login_05.gif</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205861</ctime>\n" +
        "        <type>HTTPS_DNS_MODE</type>\n" +
        "        <btype>HTTPS_02</btype>\n" +
        "        <businessId>HTTPS_02</businessId>\n" +
        "        <name>2.2.3.2 探测源站贵宾网银HTTPS服务</name>\n" +
        "        <request>https://vip.icbc.com.cn/icbc/newperbank/images/vip/logo.gif</request>\n" +
        "        <result1>62</result1>\n" +
        "        <result2>16</result2>\n" +
        "        <result3>16</result3>\n" +
        "        <result4>200</result4>\n" +
        "        <result5>3282</result5>\n" +
        "        <result6>https://202.108.88.55/icbc/newperbank/images/vip/logo.gif</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205862</ctime>\n" +
        "        <type>HTTPS_DNS_MODE</type>\n" +
        "        <btype>HTTPS_03</btype>\n" +
        "        <businessId>HTTPS_03</businessId>\n" +
        "        <name>2.2.3.3 3 探测源站B2C网银HTTPS服务</name>\n" +
        "        <request>https://b2c.icbc.com.cn/icbc/newperbank/images/login_05.gif</request>\n" +
        "        <result1>fail</result1>\n" +
        "        <result2>Connection timed out: connect</result2>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205846</ctime>\n" +
        "        <type>HTTP_DNS_MODE</type>\n" +
        "        <btype>HTTP_01</btype>\n" +
        "        <businessId>HTTP_01</businessId>\n" +
        "        <name>2.2.2.1 探测门户Cache站点HTTP服务</name>\n" +
        "        <request>http://www.icbc.com.cn/icbc/html/guanggao/yuntongka/images/card_mdyt_04.gif</request>\n" +
        "        <result1>0</result1>\n" +
        "        <result2>31</result2>\n" +
        "        <result3>46</result3>\n" +
        "        <result4>200</result4>\n" +
        "        <result5>19100</result5>\n" +
        "        <result6>http://202.108.251.34/icbc/html/guanggao/yuntongka/images/card_mdyt_04.gif</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205846</ctime>\n" +
        "        <type>HTTP_DNS_MODE</type>\n" +
        "        <btype>HTTP_02</btype>\n" +
        "        <businessId>HTTP_02</businessId>\n" +
        "        <name>2.2.2.2 探测国际化门户Cache站点HTTP服务</name>\n" +
        "        <request>http://www.icbc.com.cn/icbc/html/guanggao/yuntongka/images/card_mdyt_04.gif</request>\n" +
        "        <result1>0</result1>\n" +
        "        <result2>15</result2>\n" +
        "        <result3>31</result3>\n" +
        "        <result4>200</result4>\n" +
        "        <result5>19100</result5>\n" +
        "        <result6>http://202.108.251.167/icbc/html/guanggao/yuntongka/images/card_mdyt_04.gif</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205847</ctime>\n" +
        "        <type>HTTP_DNS_MODE</type>\n" +
        "        <btype>HTTP_03</btype>\n" +
        "        <businessId>HTTP_03</businessId>\n" +
        "        <name>2.2.2.3 探测源站门户网银HTTP服务</name>\n" +
        "        <request>http://mybank.icbc.com.cn/icbc/html/guanggao/yuntongka/images/card_mdyt_04.gif</request>\n" +
        "        <result1>15</result1>\n" +
        "        <result2>16</result2>\n" +
        "        <result3>32</result3>\n" +
        "        <result4>200</result4>\n" +
        "        <result5>19100</result5>\n" +
        "        <result6>http://202.99.30.209/icbc/html/guanggao/yuntongka/images/card_mdyt_04.gif</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205848</ctime>\n" +
        "        <type>HTTP_DNS_MODE</type>\n" +
        "        <btype>HTTP_04</btype>\n" +
        "        <businessId>HTTP_04</businessId>\n" +
        "        <name>2.2.2.4 探测各银行网银及主流门户网站的首页</name>\n" +
        "        <request>http://www.abchina.com/cn/</request>\n" +
        "        <result1>0</result1>\n" +
        "        <result2>593</result2>\n" +
        "        <result3>609</result3>\n" +
        "        <result4>200</result4>\n" +
        "        <result5>21855</result5>\n" +
        "        <result6>http://124.74.251.240/cn/</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205848</ctime>\n" +
        "        <type>HTTP_DNS_MODE</type>\n" +
        "        <btype>HTTP_04</btype>\n" +
        "        <businessId>HTTP_04</businessId>\n" +
        "        <name>2.2.2.4 探测各银行网银及主流门户网站的首页</name>\n" +
        "        <request>http://www.boc.cn</request>\n" +
        "        <result1>0</result1>\n" +
        "        <result2>16</result2>\n" +
        "        <result3>31</result3>\n" +
        "        <result4>200</result4>\n" +
        "        <result5>7122</result5>\n" +
        "        <result6>http://202.99.10.180</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205848</ctime>\n" +
        "        <type>HTTP_DNS_MODE</type>\n" +
        "        <btype>HTTP_04</btype>\n" +
        "        <businessId>HTTP_04</businessId>\n" +
        "        <name>2.2.2.4 探测各银行网银及主流门户网站的首页</name>\n" +
        "        <request>http://www.ccb.com</request>\n" +
        "        <result1>0</result1>\n" +
        "        <result2>16</result2>\n" +
        "        <result3>16</result3>\n" +
        "        <result4>200</result4>\n" +
        "        <result5>72</result5>\n" +
        "        <result6>http://202.106.80.106</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205848</ctime>\n" +
        "        <type>HTTP_DNS_MODE</type>\n" +
        "        <btype>HTTP_04</btype>\n" +
        "        <businessId>HTTP_04</businessId>\n" +
        "        <name>2.2.2.4 探测各银行网银及主流门户网站的首页</name>\n" +
        "        <request>http://www.icbc.com.cn</request>\n" +
        "        <result1>0</result1>\n" +
        "        <result2>0</result2>\n" +
        "        <result3>0</result3>\n" +
        "        <result4>200</result4>\n" +
        "        <result5>492</result5>\n" +
        "        <result6>http://202.108.251.167</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205848</ctime>\n" +
        "        <type>HTTP_DNS_MODE</type>\n" +
        "        <btype>HTTP_04</btype>\n" +
        "        <businessId>HTTP_04</businessId>\n" +
        "        <name>2.2.2.4 探测各银行网银及主流门户网站的首页</name>\n" +
        "        <request>http://www.sina.com.cn</request>\n" +
        "        <result1>0</result1>\n" +
        "        <result2>15</result2>\n" +
        "        <result3>140</result3>\n" +
        "        <result4>200</result4>\n" +
        "        <result5>117357</result5>\n" +
        "        <result6>http://202.108.33.96</result6>\n" +
        "    </resultcontext>\n" +
        "    <resultcontext>\n" +
        "        <ctime>1279205848</ctime>\n" +
        "        <type>HTTP_DNS_MODE</type>\n" +
        "        <btype>HTTP_04</btype>\n" +
        "        <businessId>HTTP_04</businessId>\n" +
        "        <name>2.2.2.4 探测各银行网银及主流门户网站的首页</name>\n" +
        "        <request>http://www.sohu.com</request>\n" +
        "        <result1>0</result1>\n" +
        "        <result2>16</result2>\n" +
        "        <result3>62</result3>\n" +
        "        <result4>200</result4>\n" +
        "        <result5>62413</result5>\n" +
        "        <result6>http://61.135.179.190</result6>\n" +
        "    </resultcontext>\n" +
        "</dataresult>\n");
    assertNotNull(response);
    TaskConfigFactory factory = TaskConfigFactory.newInstance();
    TaskConfig config = factory.loadTaskConfig(new StringReader(response.getTaskConfiguration()));
    assertNotNull(config);
  }

}
