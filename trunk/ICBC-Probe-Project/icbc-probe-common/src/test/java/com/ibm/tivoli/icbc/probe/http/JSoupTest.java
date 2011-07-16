package com.ibm.tivoli.icbc.probe.http;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ibm.tivoli.icbc.result.http.HttpResult;

public class JSoupTest extends TestCase {
  
  private static Log log = LogFactory.getLog(JSoupTest.class);
  
  public static class ElementResult {
    private String url = null;
    private int downloadSize = 0;
    private int downloadElapseTime = 0;
    private String httpCode = null;
    
    public static ElementResult newInstance(HttpResult httpResult) {
      if (httpResult == null || httpResult.getTargetURLs() == null || httpResult.getTargetURLs().size() == 0 || httpResult.getTargetURLs().get(0).getAccesses() == null || httpResult.getTargetURLs().get(0).getAccesses().size() == 0){
        return null;
     }
     return new ElementResult(httpResult.getTargetURLs().get(0).getUrl(), httpResult.getTargetURLs().get(0).getAccesses().get(0).getHttpCode(), httpResult.getTargetURLs().get(0).getAccesses().get(0).getDownloadSize(), (int)httpResult.getTargetURLs().get(0).getAccesses().get(0).getDownloadTime());
    }
    
    public ElementResult(String url, String httpCode, int downloadSize, int downloadElapseTime) {
      super();
      this.httpCode = httpCode;
      this.downloadSize = downloadSize;
      this.downloadElapseTime = downloadElapseTime;
    }
    
    /**
     * @return the url
     */
    public String getUrl() {
      return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
      this.url = url;
    }

    /**
     * @return the downloadSize
     */
    public int getDownloadSize() {
      return downloadSize;
    }
    /**
     * @param downloadSize the downloadSize to set
     */
    public void setDownloadSize(int downloadSize) {
      this.downloadSize = downloadSize;
    }
    /**
     * @return the downloadElapseTime
     */
    public int getDownloadElapseTime() {
      return downloadElapseTime;
    }
    /**
     * @param downloadElapseTime the downloadElapseTime to set
     */
    public void setDownloadElapseTime(int downloadElapseTime) {
      this.downloadElapseTime = downloadElapseTime;
    }
    /**
     * @return the httpCode
     */
    public String getHttpCode() {
      return httpCode;
    }
    /**
     * @param httpCode the httpCode to set
     */
    public void setHttpCode(String httpCode) {
      this.httpCode = httpCode;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return "ElementResult [downloadSize=" + downloadSize + ", downloadElapseTime=" + downloadElapseTime + ", httpCode=" + httpCode + ", url=" + url + "]\n";
    }

    
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testCase1() throws Exception {
    Document doc = Jsoup.parse(new URL("http://www.icbc.com.cn/icbc"), 5000);
    Map<String, Element> result = new HashMap<String, Element>();
    
    Element[] elements = doc.select("img").toArray(new Element[0]);
    for (Element e : elements) {
      String imgUrl = e.attr("src");
      if (StringUtils.isNotEmpty(imgUrl)) {
        imgUrl = e.absUrl("src");
      }
      System.out.println(imgUrl);
      result.put(imgUrl.toString(), e);
    }
    elements = doc.select("iframe").toArray(new Element[0]);
    for (Element e : elements) {
      String imgUrl = e.attr("src");
      if (StringUtils.isNotEmpty(imgUrl)) {
        imgUrl = e.absUrl("src");
      }
      System.out.println(imgUrl);
      result.put(imgUrl.toString(), e);
    }
    elements = doc.select("object").toArray(new Element[0]);
    for (Element e : elements) {
      String imgUrl = e.attr("src");
      if (StringUtils.isNotEmpty(imgUrl)) {
        imgUrl = e.absUrl("src");
      }
      System.out.println(imgUrl);
      result.put(imgUrl.toString(), e);
    }
    
    Set<ElementResult> topN4Size = new TreeSet<ElementResult>(new Comparator<ElementResult>(){

      @Override
      public int compare(ElementResult r1, ElementResult r2) {
        if (r1 == null && r2 == null){
          return 0;
        } else if (r1 == null) {
          return 1;
        } else if (r2 == null) {
          return -1;
        } else {
          return (r2.getDownloadSize() - r1.getDownloadSize());
        }
      }});
    
    Set<ElementResult> topN4Slow = new TreeSet<ElementResult>(new Comparator<ElementResult>(){

      @Override
      public int compare(ElementResult r1, ElementResult r2) {
        if (r1 == null && r2 == null){
          return 0;
        } else if (r1 == null) {
          return 1;
        } else if (r2 == null) {
          return -1;
        } else {
          return (r2.getDownloadElapseTime() - r1.getDownloadElapseTime());
        }
      }});
    
    for (int i = 0; i < result.keySet().size(); i++) {
        String elementUrl = new ArrayList<String>(result.keySet()).get(i);
        System.out.println(i + ": " + elementUrl);
        if (StringUtils.isEmpty(elementUrl)) {
           continue;
        }
        HttpProbeImpl probe = new HttpProbeImpl(new String[]{elementUrl});
        probe.setResolvIPAddress(false);
        try {
          HttpResult httpResult = probe.run();
          if (httpResult != null) {
             ElementResult r = ElementResult.newInstance(httpResult);
             if (r != null) {
                r.setUrl(elementUrl);
                topN4Size.add(r);
                topN4Slow.add(r);
             }
          }
        } catch (Exception e1) {
          log.warn("Fail to get URL: " + elementUrl, e1);
        }
    }
    System.err.println(topN4Size);
    System.err.println(topN4Slow);
    assertNotNull(elements);
  }

}
