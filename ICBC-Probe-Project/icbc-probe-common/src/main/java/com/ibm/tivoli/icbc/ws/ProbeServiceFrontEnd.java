/**
 * 
 */
package com.ibm.tivoli.icbc.ws;

import org.apache.cxf.frontend.ClientProxyFactoryBean;

/**
 * @author Zhao Dong Lu
 *
 */
public class ProbeServiceFrontEnd implements ProbeService {
  
  private String soapAddress = "http://localhost:9000/Hello";

  private int maxRetry = 1;

  private int retryInterval = 1;
  /**
   * 
   */
  public ProbeServiceFrontEnd() {
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

  /* (non-Javadoc)
   * @see com.ibm.tivoli.icbc.ws.ProbeService#submitXMLData(java.lang.String)
   */
  public Response submitXMLData(String xmlDataMessage) {
    ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
    factory.setServiceClass(ProbeService.class);
    factory.setAddress(this.soapAddress);
    ProbeService client = (ProbeService) factory.create();
    return client.submitXMLData(xmlDataMessage);
  }

}
