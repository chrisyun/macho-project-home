/**
 * 
 */
package com.ibm.tivoli.bsm.service;

/**
 * 业务异常基础类.
 * @author dongluz@cn.ibm.com
 * @version 1.0.0
 *
 */
public class ServiceException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -5862423832095011979L;

  /**
   * 
   */
  public ServiceException() {
    super();
  }

  /**
   * @param message
   */
  public ServiceException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public ServiceException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   * @param cause
   */
  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }

}
