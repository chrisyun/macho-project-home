/**
 * 
 */
package com.ibm.tivoli.bsm.service;

/**
 * @author ZhaoDongLu
 *
 */
public class DataFeedException extends RuntimeException {

  private int returnCode = 0;
  /**
   * 
   */
  public DataFeedException() {
    super();
  }

  /**
   * @param message
   */
  public DataFeedException(int returnCode, String message) {
    super(message);
    this.returnCode = returnCode;
  }

  /**
   * @param cause
   */
  public DataFeedException(int returnCode, Throwable cause) {
    super(cause);
    this.returnCode = returnCode;
  }

  /**
   * @param message
   * @param cause
   */
  public DataFeedException(int returnCode, String message, Throwable cause) {
    super(message, cause);
    this.returnCode = returnCode;
  }

  public int getReturnCode() {
    return returnCode;
  }

  public void setReturnCode(int returnCode) {
    this.returnCode = returnCode;
  }

}
