package com.npower.dm.core;


import java.util.Date;


/**
* @author chenlei
* @version $Revision: 1.1 $ $Date: 2008/09/02 07:11:36 $
*/
public interface SoftwareEvaluate {

  /**
   * @return
   */
  public abstract long getId();

  /**
   * @return
   */
  public abstract Software getSoftware();

  /**
   * @param software
   */
  public abstract void setSoftware(Software software);

  /**
   * @return
   */
  public abstract String getRemark();

  /**
   * @param remark
   */
  public abstract void setRemark(String remark);

  /**
   * @return
   */
  public abstract long getGrade();

  /**
   * @param grade
   */
  public abstract void setGrade(long grade);

  /**
   * @return
   */
  public abstract String getUserName();

  /**
   * @param userName
   */
  public abstract void setUserName(String userName);

  /**
   * @return
   */
  public abstract String getUserIp();

  /**
   * @param userIp
   */
  public abstract void setUserIp(String userIp);

  /**
   * @return
   */
  public abstract Date getCreatedTime();

}