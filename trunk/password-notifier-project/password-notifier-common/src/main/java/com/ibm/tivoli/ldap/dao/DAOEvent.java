/**
 * 
 */
package com.ibm.tivoli.ldap.dao;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ibm.tivoli.ldap.AdminContext;

/**
 * @author ZhaoDongLu
 * 
 */
public class DAOEvent<T> {
  // TODO 补充足够的事件属性信息

  /**
   * DAO的事件类型
   */
  private String action;

  /**
   * DAO事件的操作人
   */
  private AdminContext actor;

  /**
   * DAO事件发生的目标对象
   */
  private T target;

  /**
   * 操作的完成情况（如：失败、成功等）
   */
  private String status;

  /**
   * 事件的进一步描述信息
   */
  private String description;

  /**
   * 
   */
  public DAOEvent() {
    super();
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public AdminContext getActor() {
    return actor;
  }

  public void setActor(AdminContext actor) {
    this.actor = actor;
  }

  public T getTarget() {
    return target;
  }

  public void setTarget(T target) {
    this.target = target;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
