package com.ibm.tivoli.icbc.ws;

public interface ProbeService {
  
  public void setMaxRetry(int maxRetry);

  public void setRetryInterval(int retryInterval);

  public abstract Response submitXMLData(String xmlDataMessage);

}