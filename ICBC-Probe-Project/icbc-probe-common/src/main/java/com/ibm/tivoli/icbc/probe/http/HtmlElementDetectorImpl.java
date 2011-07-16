package com.ibm.tivoli.icbc.probe.http;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ibm.tivoli.icbc.result.http.HttpResult;
import com.ibm.tivoli.icbc.result.http.PageElementItem;
import com.ibm.tivoli.icbc.result.http.PageElementResult;

public class HtmlElementDetectorImpl implements HtmlElementDetector {

  private static Log log = LogFactory.getLog(HtmlElementDetectorImpl.class);

  /* (non-Javadoc)
   * @see com.ibm.tivoli.icbc.probe.http.HtmlElementDetector#detect(java.lang.String, java.lang.String)
   */
  @Override
  public PageElementResult detect(String html, String baseUri) throws Exception {
    Document doc = Jsoup.parse(html, baseUri);
    return detect(doc);
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.icbc.probe.http.HtmlElementDetector#detect(java.net.URL, int)
   */
  public PageElementResult detect(URL url, int timeout) throws Exception {
    Document doc = Jsoup.parse(url, timeout);
    return detect(doc);
  }
  

  private PageElementResult detect(Document doc) {
    Map<String, Element> result = new HashMap<String, Element>();

    Element[] elements = doc.select("img").toArray(new Element[0]);
    for (Element e : elements) {
      String imgUrl = e.attr("src");
      if (StringUtils.isNotEmpty(imgUrl)) {
        imgUrl = e.absUrl("src");
      }
      log.debug(imgUrl);
      result.put(imgUrl.toString(), e);
    }
    elements = doc.select("iframe").toArray(new Element[0]);
    for (Element e : elements) {
      String imgUrl = e.attr("src");
      if (StringUtils.isNotEmpty(imgUrl)) {
        imgUrl = e.absUrl("src");
      }
      log.debug(imgUrl);
      result.put(imgUrl.toString(), e);
    }
    elements = doc.select("object").toArray(new Element[0]);
    for (Element e : elements) {
      String imgUrl = e.attr("src");
      if (StringUtils.isNotEmpty(imgUrl)) {
        imgUrl = e.absUrl("src");
      }
      log.debug(imgUrl);
      result.put(imgUrl.toString(), e);
    }

    Set<PageElementItem> topN4Size = new TreeSet<PageElementItem>(new Comparator<PageElementItem>() {

      @Override
      public int compare(PageElementItem r1, PageElementItem r2) {
        if (r1 == null && r2 == null) {
          return 0;
        } else if (r1 == null) {
          return 1;
        } else if (r2 == null) {
          return -1;
        } else {
          return (r2.getDownloadSize() - r1.getDownloadSize());
        }
      }
    });

    Set<PageElementItem> topN4Slow = new TreeSet<PageElementItem>(new Comparator<PageElementItem>() {

      @Override
      public int compare(PageElementItem r1, PageElementItem r2) {
        if (r1 == null && r2 == null) {
          return 0;
        } else if (r1 == null) {
          return 1;
        } else if (r2 == null) {
          return -1;
        } else {
          return (r2.getDownloadElapseTime() - r1.getDownloadElapseTime());
        }
      }
    });

    for (int i = 0; i < result.keySet().size(); i++) {
      String elementUrl = new ArrayList<String>(result.keySet()).get(i);
      log.debug(i + ": " + elementUrl);
      if (StringUtils.isEmpty(elementUrl)) {
        continue;
      }
      HttpProbeImpl probe = new HttpProbeImpl(new String[] { elementUrl });
      probe.setReadTimeout(0);
      probe.setResolvIPAddress(false);
      try {
        HttpResult httpResult = probe.run();
        if (httpResult != null) {
          PageElementItem r = PageElementItem.newInstance(httpResult);
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
    log.debug(topN4Size);
    log.debug(topN4Slow);
    PageElementResult r = new PageElementResult(topN4Size, topN4Slow);
    
    // 设置页面元素的数量
    r.setTotalElement(result.size());
    return r;
  }

}
