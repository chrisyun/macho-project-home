/**
 * 
 */
package com.ibm.tivoli.ldap.dao;

/**
 * @author ZhaoDongLu
 *
 */
public class DAOException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -3919241445905703434L;

  /**
   * 
   */
  public DAOException() {
    super();
  }

  /**
   * @param message
   */
  public DAOException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public DAOException(Throwable cause) {
    super(cause);
  }

  /**
   * @param message
   * @param cause
   */
  public DAOException(String message, Throwable cause) {
    super(message, cause);
  }

}
