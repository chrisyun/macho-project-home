/**
 * 
 */
package com.ibm.tivoli.icbc.ws;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.digester.Digester;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.util.XmlFormatter;


/**
 * @author Zhao Dong Lu
 * 
 */
public class ProbeServiceFrontEndV2 implements ProbeService {
  private Log log = LogFactory.getLog(ProbeServiceFrontEndV2.class);

  private String soapAddress = "http://localhost:9000/Hello";

  private int maxRetry = 1;

  private int retryInterval = 1;

  /**
   * 
   */
  public ProbeServiceFrontEndV2() {
    super();
  }

  public String getSoapAddress() {
    return soapAddress;
  }

  public void setSoapAddress(String soapAddress) {
    this.soapAddress = soapAddress;
  }

  public int getMaxRetry() {
    return maxRetry;
  }

  public void setMaxRetry(int maxRetry) {
    this.maxRetry = maxRetry;
  }

  public int getRetryInterval() {
    return retryInterval;
  }

  public void setRetryInterval(int retryInterval) {
    this.retryInterval = retryInterval;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.icbc.ws.ProbeService#submitXMLData(java.lang.String)
   */
  public Response submitXMLData(String xmlDataMessage) {
    String requestXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
    "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
    "   <soapenv:Header/>\n" +
    "   <soapenv:Body>\n" +
    "      <ns1:submitXMLData xmlns:ns1=\"http://soap.tivoli.ibm.com/\">\n" +
    "         <!--Optional:-->\n" +
    "         <arg0>" + xmlDataMessage + "</arg0>\n" +
    "      </ns1:submitXMLData>\n" +
    "   </soapenv:Body>\n" +
    "</soapenv:Envelope>\n";
    
    int counter = 0;
    while (counter < this.maxRetry) {
        try {
          if (log.isDebugEnabled()) {
             log.debug("SOAP request XML:" + requestXML);
          }
          return postData(requestXML);
        } catch (Exception e) {
          log.error(e.getMessage(), e);
          try {
            Thread.sleep(1000 * this.retryInterval);
          } catch (InterruptedException e1) {
            log.error(e.getMessage(), e1);
          }
        } finally {
          counter++;
        }
    }
    
    return new Response(-1, null, null);
  }

  private Response postData(String requestXML) throws ProbeException {
    PostMethod method = new PostMethod(this.soapAddress);
    try {
      method.setRequestHeader("Content-Type", "text/xml;charset=UTF-8");
      method.setRequestHeader("SOAPAction", "\"\"");

      method.setRequestBody(new ByteArrayInputStream(requestXML.getBytes("UTF-8")));

      HttpClient client = new HttpClient();
      int statusCode = client.executeMethod(method);

      if (statusCode != HttpStatus.SC_OK) {
        log.error("Method failed: " + method.getStatusLine());
      }

      // Read the response body.
      String respXML = new String(method.getResponseBody(), "UTF-8");
      
      if (log.isDebugEnabled()) {
         log.debug("Get response XML: " + XmlFormatter.format(respXML));
      }
      int code = -1;
      String taskConfig = null;
      try {
        int a = respXML.indexOf("<code>");
        if (a > 0) {
           String codeStr = respXML.substring( a+ "<code>".length(), respXML.indexOf("</code>"));
           code = Integer.parseInt(codeStr);
        }
        a = respXML.indexOf("<taskconfig type=\"org.netm.www.ebankmonitor.data.TaskConfig\">");
        if (a > 0) {
           taskConfig = respXML.substring(a + "<taskconfig type=\"org.netm.www.ebankmonitor.data.TaskConfig\">".length(), respXML.indexOf("</taskconfig>"));
        }
      } catch (Exception e) {
      }
      Response response = new Response(code, null, taskConfig);
      if (null != response.getTaskConfiguration()) {
         String taskConfiguration = "<?xml version='1.0' encoding='utf-8'?>\n<taskconfig>" + response.getTaskConfiguration() + "</taskconfig>";
         try {
           taskConfiguration = XmlFormatter.format(taskConfiguration);
         } catch (Exception e) {
           log.warn("Fail to format response XML.", e);
         }
         log.info("Final task config from SOAP response: " + taskConfiguration);
         response.setTaskConfiguration(taskConfiguration);
      }
      
      if (log.isDebugEnabled()) {
         log.debug("Get Task Config: " + response.getTaskConfiguration());
      }
      return response;
    } catch (HttpException e) {
      log.error(e.getMessage(), e);
      throw new ProbeException(e.getMessage(), e);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new ProbeException(e.getMessage(), e);
    } finally {
      if (method != null) {
        try {
          method.releaseConnection();
        } catch (Exception e) {
          log.error(e.getMessage(), e);
          throw new ProbeException(e.getMessage(), e);
        }
      }
    }
  }

  private Response loadResponse(Reader response) throws ProbeException {
    Response result = new Response(-1, null, null);
    Digester digester = this.getDigester();
    digester.push(result);
    try {
      digester.parse(response);
      return result;
    } catch (IOException e) {
      throw new ProbeException("Fail to read TaskConfig from config: " + response, e);
    } catch (SAXException e) {
      log.error("Fail to read TaskConfig from config: " + response, e);
    }
    return result;
  }
  
  private Digester getDigester() {
    Digester digester = new Digester();
    digester.setValidating(false);

    //digester.addObjectCreate("*/soapenv:Envelope/soapenv:Body/SubmitXMLDataResponse/return", Response.class);
    digester.addBeanPropertySetter("*/SubmitXMLDataResponse/return/code", "code");
    digester.addBeanPropertySetter("*/SubmitXMLDataResponse/return/taskconfig", "taskConfiguration");
    
    return (digester);

  }
}
