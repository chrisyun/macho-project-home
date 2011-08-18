/**
 * 
 */
package com.ibm.tivoli.tuna.service;

/**
 * @author zhaodonglu
 *
 */
public class Status {

  private String code = null;
  private String message = null;
  /**
   * 
   */
  public Status() {
    super();
  }
  /**
   * @return the code
   */
  public String getCode() {
    return code;
  }
  /**
   * @param code the code to set
   */
  public void setCode(String code) {
    this.code = code;
  }
  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }
  /**
   * @param message the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

}
