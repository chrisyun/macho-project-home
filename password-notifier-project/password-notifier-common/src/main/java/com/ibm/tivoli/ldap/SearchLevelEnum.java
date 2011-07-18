/**
 * 
 */
package com.ibm.tivoli.ldap;

/**
 * @author ZhaoDongLu
 *
 */
public enum SearchLevelEnum {
  
  /**
   * 表示查询所有层级
   */
  SUBTREE_SCOPE,
  /**
   * 表示查询自己节点
   */
  OBJECT_SCOPE,
  /**
   * 表示查询一级
   */
  ONELEVEL_SCOPE,
  /**
   * 表示查询2级节点
   */
  TWOLEVEL_SCOPE;

}
