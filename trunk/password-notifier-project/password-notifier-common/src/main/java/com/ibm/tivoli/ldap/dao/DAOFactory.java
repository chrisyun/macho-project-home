/**
 * 
 */
package com.ibm.tivoli.ldap.dao;

import com.ibm.tivoli.ldap.AdminContext;



/**
 * @author ZhaoDongLu
 *
 */
public abstract class DAOFactory {

  /**
   * 
   */
  public DAOFactory() {
    super();
  }

  /**
   * Return an instance of InetOrgPersonDAO
   * @param adminContext
   * @return
   * @throws DAOException
   */
  public abstract InetOrgPersonDAO  getInetOrgPersonDAO(AdminContext adminContext) throws DAOException;

  /**
   * Return an instance of InetOrgPersonDAO
   * @param adminContext
   * @return
   * @throws DAOException
   */
  public abstract TamSecUserDAO  getTamSecUserDAO(AdminContext adminContext) throws DAOException;

}
