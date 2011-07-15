/**
 * 
 */
package com.ibm.tivoli.cars.handler;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ibm.tivoli.cars.WebSEALRequestLogEvent;
import com.ibm.tivoli.cars.entity.Application;

/**
 * @author zhaodonglu
 * 
 */
public class StaticsEventHandler implements EventHandler {

  /**
   * begin time of this statics
   */
  private Date beginTime = null;
  /**
   * App name and total transport bytes
   */
  private Map<String, Long> appBytes = new ConcurrentHashMap<String, Long>();

  /**
   * App name and total http requests
   */
  private Map<String, Long> appRequests = new ConcurrentHashMap<String, Long>();

  /**
   * 
   */
  public StaticsEventHandler() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.cars.handler.EventHandler#handle(com.ibm.tivoli.cars.
   * WebSEALRequestLogEvent)
   */
  public void handle(String logLine, WebSEALRequestLogEvent logEvent) throws Exception {
    if (this.beginTime == null) {
      beginTime = new Date();
    }
    Application application = logEvent.getApplication();
    if (application == null) {
      return;
    }
    String appName = application.getName();
    if (appBytes.containsKey(appName)) {
      Long last = appBytes.get(appName);
      long total = last.longValue() + logEvent.getContentLength();
      appBytes.put(appName, total);
    } else {
      appBytes.put(appName, new Long(logEvent.getContentLength()));
    }

    if (appRequests.containsKey(appName)) {
      Long last = appRequests.get(appName);
      long total = last.longValue() + 1;
      appRequests.put(appName, total);
    } else {
      appRequests.put(appName, new Long(1));
    }
  }

  /**
   * @return the beginTime
   */
  public Date getBeginTime() {
    return beginTime;
  }

  /**
   * @return the appBytes
   */
  public Map<String, Long> getAppBytes() {
    return appBytes;
  }

  /**
   * @return the appRequests
   */
  public Map<String, Long> getAppRequests() {
    return appRequests;
  }
  
  public long getTotalRequest() {
    long result = 0;
    for (Long value: this.appRequests.values()) {
        result += value.longValue();
    }
    return result;
  }

  public long getTotalBytes() {
    long result = 0;
    for (Long value: this.appBytes.values()) {
        result += value.longValue();
    }
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final int maxLen = 100;
    return String.format("StaticsEventHandler [getTotalRequest()=%s, getTotalBytes()=%s, getBeginTime()=%s, beginTime=%s, appBytes=%s, appRequests=%s]",
        getTotalRequest(), getTotalBytes(), getBeginTime(), beginTime, appBytes != null ? toString(appBytes.entrySet(), maxLen) : null,
        appRequests != null ? toString(appRequests.entrySet(), maxLen) : null);
  }

  private String toString(Collection<?> collection, int maxLen) {
    StringBuilder builder = new StringBuilder();
    builder.append("[");
    int i = 0;
    for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
      if (i > 0)
        builder.append(", ");
      builder.append(iterator.next());
    }
    builder.append("]");
    return builder.toString();
  }


}
