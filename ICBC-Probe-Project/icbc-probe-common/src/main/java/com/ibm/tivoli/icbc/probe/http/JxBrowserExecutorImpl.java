/**
 * 
 */
package com.ibm.tivoli.icbc.probe.http;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jniwrapper.win32.ie.HeadlessBrowser;
import com.thoughtworks.xstream.XStream;

/**
 * @author zhaodonglu
 * 
 */
public class JxBrowserExecutorImpl implements BrowserExecutor {

  private static Log log = LogFactory.getLog(JxBrowserExecutorImpl.class);

  public static final String PROBE_TEMP_FILE_PREFIX = "probe_";
  public JxBrowserExecutorImpl() {
    super();
  }


  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.icbc.probe.http.BrowserExecutor#navigate(java.lang.String)
   */
  //private WebBrowser browser = null;
  @Override
  public BrowserResult navigate(String url) {
    String html = null;
    String locationURL = null;
    BrowserResult result = new BrowserResult();
    synchronized (JxBrowserExecutorImpl.class) {
      // Need singleton pattern for Browser
      //Browser browser = new Browser();
      HeadlessBrowser browser = null;
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
        File htmlFile = createTempFile(locationURL, ".html");
        log.debug("Output html to file: [" + htmlFile.getCanonicalPath() + "]");
        FileUtils.writeStringToFile(htmlFile, html);
        //Image image = browser.getScreenShot(true);
        Image image = ((HeadlessBrowser) browser).getScreenShot();
        File imgFile = createTempFile(locationURL, ".png");
        ImageIO.write((BufferedImage) image, "PNG", imgFile);
        log.debug("Output screenshot to file: [" + imgFile.getCanonicalPath() + "]");
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


  private File createTempFile(String locationURL, String suffix) throws IOException {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd.HHmmss");
    String timeStr = df.format(new Date());;
    return new File(System.getProperty("java.io.tmpdir"), PROBE_TEMP_FILE_PREFIX + timeStr + "_" + getHost(locationURL) + suffix);
  }
  
  private static String getHost(String url) {
    String result = "";
    try {
      URL u = new URL(url);
      result = u.getHost();
    } catch (MalformedURLException e) {
      log.error(e.getMessage(), e);
    }
    return result;
  }
  

  /**
   * @param args
   *        0  - URL
   *        1  - Timeout in seconds
   * @throws Exception
   */
  private static long timeoutInSeconds = 60;
  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
       log.debug("Missing cmd args.");
    }
    String url = args[0];
    if (args.length > 1) {
       try {
        timeoutInSeconds = Long.parseLong(args[1]);
      } catch (Exception e) {
        log.error("failure to parse timeout with: " + args[1]);
      }
    }
    
    Runnable delayKiller = new Runnable() {

      @Override
      public void run() {
        try {
          log.info("Browser process killer has been started, will be delayed [" + timeoutInSeconds + "s");
          Thread.sleep(timeoutInSeconds * 1000);
          log.info("Browser process will be killed!");
          System.exit(0);
        } catch (InterruptedException e) {
          log.warn("Browser killer thread interrupted", e);
        }
      }
      
    };
    if (timeoutInSeconds > 0) {
       Thread t = new Thread(delayKiller);
       t.start();
    }
    
    BrowserExecutor exec = new JxBrowserExecutorImpl();
    BrowserResult r = exec.navigate(url);
    XStream xs = CmdLineBrowserExecutorImpl.getXStream();
    String xml = xs.toXML(r);
    System.out.println(xml);
    System.out.flush();
    System.exit(0);
  }  

}
