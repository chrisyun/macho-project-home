package com.npower.dm.hibernate.entity;


import java.util.Date;

import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareEvaluate;

public class AbstractSoftwareEvaluateEntity implements SoftwareEvaluate {

  private long Id;
  protected Software Software;
  protected String remark;
  protected long grade = 0;
  protected String userName;
  protected String userIp;
  protected Date createdTime = new Date();

  public AbstractSoftwareEvaluateEntity() {
    super();
  }

  public AbstractSoftwareEvaluateEntity(Software Software, long grade, Date createdTime) {
    this.Software = Software;
    this.grade = grade;
    this.createdTime = createdTime;
  }

  /** full constructor */
  public AbstractSoftwareEvaluateEntity(Software Software, String remark, long grade, String userName, String userIp,
      Date createdTime) {
    this.Software = Software;
    this.remark = remark;
    this.grade = grade;
    this.userName = userName;
    this.userIp = userIp;
    this.createdTime = createdTime;
  }
  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareEvaluate#getId()
   */
  public long getId() {
    return this.Id;
  }

  public void setId(long Id) {
    this.Id = Id;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareEvaluate#getSoftware()
   */
  public Software getSoftware() {
    return Software;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareEvaluate#setSoftware(com.npower.dm.core.Software)
   */
  public void setSoftware(Software software) {
    Software = software;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareEvaluate#getRemark()
   */
  public String getRemark() {
    return this.remark;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareEvaluate#setRemark(java.sql.Clob)
   */
  public void setRemark(String remark) {
    this.remark = remark;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareEvaluate#getGrade()
   */
  public long getGrade() {
    return this.grade;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareEvaluate#setGrade(long)
   */
  public void setGrade(long grade) {
    this.grade = grade;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareEvaluate#getUserName()
   */
  public String getUserName() {
    return this.userName;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareEvaluate#setUserName(java.lang.String)
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareEvaluate#getUserIp()
   */
  public String getUserIp() {
    return this.userIp;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareEvaluate#setUserIp(java.lang.String)
   */
  public void setUserIp(String userIp) {
    this.userIp = userIp;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareEvaluate#getCreatedTime()
   */
  public Date getCreatedTime() {
    return this.createdTime;
  }

  public void setCreatedTime(Date createdTime) {
    this.createdTime = createdTime;
  }

}