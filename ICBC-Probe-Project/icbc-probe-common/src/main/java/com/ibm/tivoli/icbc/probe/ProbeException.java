/**
 * 
 */
package com.ibm.tivoli.icbc.probe;

/**
 * @author Zhao Dong Lu
 *
 */
public class ProbeException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -8540378449491727928L;

  /**
   * 
   */
  public ProbeException() {
    super();
  }

  /**
   * @param message
   */
  public ProbeException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public ProbeException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   * @param cause
   */
  public ProbeException(String message, Throwable cause) {
    super(message, cause);
  }

}
