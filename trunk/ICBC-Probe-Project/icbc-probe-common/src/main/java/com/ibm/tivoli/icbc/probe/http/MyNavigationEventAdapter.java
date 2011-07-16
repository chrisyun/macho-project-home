/**
 * 
 */
package com.ibm.tivoli.icbc.probe.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.tivoli.icbc.probe.http.BrowserExecutor.BrowserResult;
import com.jniwrapper.win32.ie.WebBrowser;
import com.jniwrapper.win32.ie.dom.HTMLDocument;
import com.jniwrapper.win32.ie.event.NavigationEventAdapter;

/**
 * @author zhaodonglu
 * 
 */
public class MyNavigationEventAdapter extends NavigationEventAdapter {

  private static Log log = LogFactory.getLog(MyWebBrowserEventsHandler.class);

  private BrowserResult result = new BrowserResult();

  private String[] externalObjectTag = { "img", "object", "frame", "iframe" };

  //private HtmlElementDetector htmlElementDetector = null;

  /**
   * 
   */
  public MyNavigationEventAdapter() {
    super();
  }

  public BrowserResult getResult() {
    return result;
  }

  public void setResult(BrowserResult result) {
    this.result = result;
  }

  public MyNavigationEventAdapter(BrowserResult result) {
    super();
    this.result = result;
  }


  public void documentCompleted(WebBrowser webBrowser, String url) {
    log.info("Document completed: " + url);
    HTMLDocument document = webBrowser.getDocument();
    if (document != null) {
      int numberOfDownloadElements = 0;
      for (String tag : this.externalObjectTag) {
        NodeList nodes = document.getElementsByTagName(tag);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
        }
        numberOfDownloadElements += nodes.getLength();
      }

      this.result.setNumberOfDownloadElements(numberOfDownloadElements);
    }
    // System.err.println("Content: " + webBrowser.getContent());
    this.result.setElapseTime(System.currentTimeMillis() - result.getBeginFirstNavigationTime());
    if (this.result.getHttpCode() == null) {
      this.result.setHttpCode("200");
    }    
  }

}
