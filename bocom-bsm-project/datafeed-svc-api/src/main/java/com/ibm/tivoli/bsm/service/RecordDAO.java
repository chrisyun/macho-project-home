/**
 * 
 */
package com.ibm.tivoli.bsm.service;


/**
 * @author ZhaoDongLu
 *
 */
public interface RecordDAO<T> {

  //public void beginTransaction() throws ServiceException;
  
  
  /**
   * Save record into database
   * @param record
   * @throws ServiceException
   */
  public void save(T record) throws ServiceException;
  
  /**
   * Open connection with database
   * @throws ServiceException 
   */
  public void initialize() throws ServiceException;

  /**
   * Close database connection
   * @throws ServiceException 
   */
  public void close() throws ServiceException;
  
  /**
   * Begin transaction
   * @throws ServiceException 
   */
  public void beginTransaction() throws ServiceException;

  /**
   * Commit transaction
   * @throws ServiceException 
   */
  public void commit() throws ServiceException;

  /**
   * Rollback transaction
   * @throws ServiceException 
   */
  public void rollback() throws ServiceException;
}
