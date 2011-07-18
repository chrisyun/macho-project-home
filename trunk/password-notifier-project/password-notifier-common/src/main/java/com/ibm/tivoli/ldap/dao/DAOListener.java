/**
 * 
 */
package com.ibm.tivoli.ldap.dao;

/**
 * @author ZhaoDongLu
 *
 */
public interface DAOListener<T> {
  
  /**
   * 处理一个DAO事件 
   * @param event
   * @throws DAOException
   */
  public void fireEvent(T event) throws DAOException;

}
