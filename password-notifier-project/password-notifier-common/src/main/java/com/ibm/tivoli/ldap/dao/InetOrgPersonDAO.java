/**
 * 
 */
package com.ibm.tivoli.ldap.dao;

import java.util.List;

import com.ibm.tivoli.ldap.entity.InetOrgPerson;


/**
 * @author ZhaoDongLu
 *
 */
public interface InetOrgPersonDAO {
  
  /**
   * 按照globalID提取资源对象. 
   * @param resourceGlobalID 资源的erGlobalId
   * @return 如果未找到返回null
   * @throws DAOException
   */
  public abstract InetOrgPerson getByUserID(String uid) throws DAOException;

  /**
   * 按照LDAP Filter查询资源
   * @param baseResourceGlobalID
   * @param filter
   * @param level 表示查询的层级
   * @return
   * @throws DAOException
   */
  public abstract List<InetOrgPerson> findByFilter(String baseDN, String filter) throws DAOException;
  
}
