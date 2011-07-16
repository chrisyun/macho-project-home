/**
 * 
 */
package com.ibm.tivoli.icbc.probe.http;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ibm.tivoli.icbc.result.http.PageElementResult;

/**
 * @author zhaodonglu
 *
 */
public class HtmlElementDetectorCacheImpl implements HtmlElementDetector {
  private Map<String, Date> timeStampMap = new HashMap<String, Date>();
  private Map<String, PageElementResult> dataCache = new HashMap<String, PageElementResult>();
  
  private HtmlElementDetector realDetector = null;
  private int cacheAgeInSeconds;

  /**
   * 
   */
  public HtmlElementDetectorCacheImpl() {
    super();
  }

  /**
   * @return the cacheAge
   */
  public int getCacheAge() {
    return cacheAgeInSeconds;
  }

  /**
   * @param cacheAge the cacheAge to set
   */
  public void setCacheAge(int cacheAge) {
    this.cacheAgeInSeconds = cacheAge;
  }

  /**
   * @return the realDetector
   */
  public HtmlElementDetector getRealDetector() {
    return realDetector;
  }

  /**
   * @return the cacheAgeInSeconds
   */
  public int getCacheAgeInSeconds() {
    return cacheAgeInSeconds;
  }

  /**
   * @param cacheAgeInSeconds the cacheAgeInSeconds to set
   */
  public void setCacheAgeInSeconds(int cacheAgeInSeconds) {
    this.cacheAgeInSeconds = cacheAgeInSeconds;
  }

  /**
   * @param realDetector the realDetector to set
   */
  public void setRealDetector(HtmlElementDetector realDetector) {
    this.realDetector = realDetector;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.icbc.probe.http.HtmlElementDetector#detect(java.lang.String, java.lang.String)
   */
  @Override
  public PageElementResult detect(String html, String baseUri) throws Exception {
    if (this.timeStampMap.containsKey(baseUri)) {
       // 确定是否超期
       Date timestamp = this.timeStampMap.get(baseUri);
       if (System.currentTimeMillis() - timestamp.getTime() < this.cacheAgeInSeconds * 1000) {
          return this.dataCache.get(baseUri);
       }
    }
    
    PageElementResult result = this.realDetector.detect(html, baseUri);
    this.timeStampMap.put(baseUri, new Date());
    this.dataCache.put(baseUri, result);
    return result;
  }


}
