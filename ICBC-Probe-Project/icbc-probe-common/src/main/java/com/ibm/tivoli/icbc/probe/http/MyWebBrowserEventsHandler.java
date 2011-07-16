package com.ibm.tivoli.icbc.probe.http;

import java.awt.Dimension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.icbc.probe.http.BrowserExecutor.BrowserResult;
import com.jniwrapper.win32.ie.WebBrowser;
import com.jniwrapper.win32.ie.event.DefaultWebBrowserEventsHandler;
import com.jniwrapper.win32.ie.event.StatusCode;

public class MyWebBrowserEventsHandler extends DefaultWebBrowserEventsHandler {
  
  private static Log log = LogFactory.getLog(MyWebBrowserEventsHandler.class);
  
  private BrowserResult result = new BrowserResult();

  public MyWebBrowserEventsHandler() {
    super();
  }

  public MyWebBrowserEventsHandler(BrowserResult result) {
    super();
    this.result = result;
  }

  public BrowserResult getResult() {
    return result;
  }

  public void setResult(BrowserResult result) {
    this.result = result;
  }

  public boolean beforeNavigate(WebBrowser webBrowser, String url, String targetFrameName, String postData, String headers) {
    log.debug("url = " + url);
    log.debug("postData = " + postData);
    log.debug("headers = " + headers);

    if (result.getBeginFirstNavigationTime() == 0) {
       result.setBeginFirstNavigationTime(System.currentTimeMillis());
    }
    // proceed
    return false;
  }
  
  public Dimension clientAreaSizeRequested(java.awt.Dimension clientAreaSize)  {
    return super.clientAreaSizeRequested(clientAreaSize);
  }
  
  public boolean navigationErrorOccured(WebBrowser webBrowser,
      java.lang.String url,
      java.lang.String frame,
      StatusCode statusCode) {
    this.result.setHttpCode("" + statusCode.getValue());
    this.result.setElapseTime(System.currentTimeMillis() - result.getBeginFirstNavigationTime());
    
    return false;
  }
}
