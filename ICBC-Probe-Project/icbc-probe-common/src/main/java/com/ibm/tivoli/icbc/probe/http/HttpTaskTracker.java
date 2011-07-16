/**
 * 
 */
package com.ibm.tivoli.icbc.probe.http;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaodonglu
 *
 */
public class HttpTaskTracker {
  
  private Map<String, Date> map = new HashMap<String, Date>();

  /**
   * 
   */
  public HttpTaskTracker() {
    super();
  }
  
  public void touch(String url) {
    this.map.put(url, new Date());
  }

  public Date getLast(String url) {
    if (this.map.containsKey(url)) {
       Date result = this.map.get(url);
       return result;
    } else {
      return new Date();
    }
  }
}
