/**
 * 
 */
package com.ibm.tivoli.bsm.service;

/**
 * @author ZhaoDongLu
 * 
 */
public class DataFeedResponse {
  
  public static final String STATUS_SUCCESS = "0";
  public static final String STATUS_FAIL = "41";

  private String status = null;
  private String message = null;

  /**
   * 
   */
  public DataFeedResponse() {
    super();
  }

  public DataFeedResponse(String status) {
    super();
    this.status = status;
  }

  public DataFeedResponse(String status, String message) {
    super();
    this.status = status;
    this.message = message;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
