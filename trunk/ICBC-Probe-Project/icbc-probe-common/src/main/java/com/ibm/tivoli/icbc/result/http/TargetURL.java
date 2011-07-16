/**
 * 
 */
package com.ibm.tivoli.icbc.result.http;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Zhao Dong Lu
 *
 */
public class TargetURL {

  private Date timestamp = new Date();
  private String url = null;
  private List<URLAccessResult> accesses = new ArrayList<URLAccessResult>();
  
  private String error = null;
  
  /**
   * 
   */
  public TargetURL() {
    super();
  }

  public TargetURL(String url) {
    super();
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<URLAccessResult> getAccesses() {
    return accesses;
  }

  public void setAccesses(List<URLAccessResult> accesses) {
    this.accesses = accesses;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

}
