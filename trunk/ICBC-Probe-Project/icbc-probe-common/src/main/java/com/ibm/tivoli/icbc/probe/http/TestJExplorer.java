package com.ibm.tivoli.icbc.probe.http;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import junit.framework.TestCase;

import com.jniwrapper.win32.ie.Browser;
import com.jniwrapper.win32.ie.WebBrowser;
import com.jniwrapper.win32.ie.dom.HTMLDocument;
import com.jniwrapper.win32.ie.event.DefaultWebBrowserEventsHandler;
import com.jniwrapper.win32.ie.event.NavigationEventAdapter;
import com.jniwrapper.win32.ie.event.StatusCode;

public class TestJExplorer extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testCase1() throws Exception {
    Browser browser = new Browser();

    JFrame frame = new JFrame();
    frame.getContentPane().add(browser, BorderLayout.CENTER);
    frame.setSize(100, 100);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    browser.waitReady();
    
    browser.setEventHandler(new DefaultWebBrowserEventsHandler() {
      public boolean beforeNavigate(WebBrowser webBrowser, String url, String targetFrameName, String postData, String headers) {
        System.out.println("url = " + url);
        System.out.println("postData = " + postData);
        System.out.println("headers = " + headers);

        System.out.println("Navigation Data");

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
        return false;
      }
    });

    browser.addNavigationListener(new NavigationEventAdapter() {
      public void documentCompleted(WebBrowser webBrowser, String url) {
        System.out.println("Document completed: " + url);
        HTMLDocument document = webBrowser.getDocument();
        int imgCount = document.getElementsByTagName("img").getLength();
        int objectCount = document.getElementsByTagName("object").getLength();
        int frameCount = document.getElementsByTagName("frame").getLength();
        int iframeCount = document.getElementsByTagName("iframe").getLength();
        
        //System.err.println("Content: " + webBrowser.getContent());
      }
    });

    
    browser.navigate("http://www.icbc.com.cn");
    browser.waitReady();

    Image image = browser.getScreenShot(true);
    ImageIO.write((BufferedImage) image, "PNG", new File("C:\\temp\\webpage.png"));

  }
}
