/**
 * 
 */
package com.ibm.tivoli.icbc.probe.http;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jniwrapper.win32.ie.HeadlessBrowser;
import com.jniwrapper.win32.ie.WebBrowser;
import com.thoughtworks.xstream.XStream;

/**
 * @author zhaodonglu
 * 
 */
public class JxBrowserExecutorImpl implements BrowserExecutor {

  private static Log log = LogFactory.getLog(JxBrowserExecutorImpl.class);
  public JxBrowserExecutorImpl() {
    super();
  }


  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.icbc.probe.http.BrowserExecutor#navigate(java.lang.String)
   */
  private static WebBrowser browser = null;
  @Override
  public BrowserResult navigate(String url) {
    String html = null;
    String locationURL = null;
    BrowserResult result = new BrowserResult();
    synchronized (JxBrowserExecutorImpl.class) {
      // Need singleton pattern for Browser
      //Browser browser = new Browser();
      if (browser == null) {
         browser = new HeadlessBrowser();
      }
      browser.setSilent(true);
      /*
      JFrame frame = new JFrame();

      frame.getContentPane().add(browser, BorderLayout.CENTER);
      frame.setSize(100, 100);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      */
      browser.waitReady();

      browser.setEventHandler(new MyWebBrowserEventsHandler(result));
      browser.addNavigationListener(new MyNavigationEventAdapter(result));

      log.info(url);
      browser.navigate(url);
      browser.waitReady();

      try {
        //String htmlContent = browser.getContent();
        locationURL = browser.getLocationURL();
        result.setLocationURL(locationURL);
        // This method returns the actual content of a document according to its DOM.
        html = browser.getContent(false);
        result.setHtmlContent(html);
        //Image image = browser.getScreenShot(true);
        Image image = ((HeadlessBrowser) browser).getScreenShot();
        File imgFile = File.createTempFile("screenshot", ".png");
        ImageIO.write((BufferedImage) image, "PNG", imgFile);
        result.setImageFile(imgFile.getCanonicalPath());
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        result.setHttpCode("" + 500);
      } finally {
        //frame.dispose();
      }
    }
    return result;
  }
  

  public static void main(String[] args) throws Exception {
    BrowserExecutor exec = new JxBrowserExecutorImpl();
    BrowserResult r = exec.navigate("http://www.icbc.com.cn");
    XStream xs = CmdLineBrowserExecutorImpl.getXStream();
    String xml = xs.toXML(r);
    System.out.println(xml);
    System.out.flush();
    System.exit(0);
  }  

}
