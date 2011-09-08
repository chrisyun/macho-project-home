/**
 * 
 */
package com.ibm.tivoli.tuna.service;

/**
 * @author zhaodonglu
 * 
 */
public class Status {

  public static final String SUCCESS = "success";
  public static final String FAILURE = "failure";	
	
  private String code = null;
  private String message = null;

  /**
   * 
   */
  public Status() {
    super();
  }

  public Status(String code, String message) {
    super();
    this.code = code;
    this.message = message;
  }

  /**
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * @param code
   *          the code to set
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
   * @param message
   *          the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("Status [code=%s, message=%s]", code, message);
  }

}
