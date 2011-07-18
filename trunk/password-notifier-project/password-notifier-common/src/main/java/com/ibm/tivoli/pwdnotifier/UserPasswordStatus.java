/**
 * 
 */
package com.ibm.tivoli.pwdnotifier;

import java.util.Date;

/**
 * @author zhaodonglu
 *
 */
public class UserPasswordStatus {

  /**
   * User ID
   */
  private String userid = null;
  
  /**
   * Full name of user
   */
  private String cn = null;
  
  /**
   * Email of user
   */
  private String email = null;
  /**
   * Time of last password modified
   */
  private Date lastPasswordChangedTime = new Date();
  /**
   * Seconds of password age for this user.
   */
  private long passwordAgeInSeconds = 0;
  /**
   * Time of last notified time 
   */
  private Date lastPasswordNotifiedTime = new Date(0);
  /**
   * 
   */
  public UserPasswordStatus() {
    super();
  }
  /**
   * @return the userid
   */
  public String getUserid() {
    return userid;
  }
  /**
   * @param userid the userid to set
   */
  public void setUserid(String userid) {
    this.userid = userid;
  }
  /**
   * @return the cn
   */
  public String getCn() {
    return cn;
  }
  /**
   * @param cn the cn to set
   */
  public void setCn(String cn) {
    this.cn = cn;
  }
  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }
  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }
  /**
   * @return the lastPasswordChangedTime
   */
  public Date getLastPasswordChangedTime() {
    return lastPasswordChangedTime;
  }
  /**
   * @param lastPasswordChangedTime the lastPasswordChangedTime to set
   */
  public void setLastPasswordChangedTime(Date lastPasswordChangedTime) {
    this.lastPasswordChangedTime = lastPasswordChangedTime;
  }
  /**
   * @return the passwordAgeInSeconds
   */
  public long getPasswordAgeInSeconds() {
    return passwordAgeInSeconds;
  }
  /**
   * @param passwordAgeInSeconds the passwordAgeInSeconds to set
   */
  public void setPasswordAgeInSeconds(long passwordAgeInSeconds) {
    this.passwordAgeInSeconds = passwordAgeInSeconds;
  }
  /**
   * @return the lastPasswordNotifiedTime
   */
  public Date getLastPasswordNotifiedTime() {
    return lastPasswordNotifiedTime;
  }
  /**
   * @param lastPasswordNotifiedTime the lastPasswordNotifiedTime to set
   */
  public void setLastPasswordNotifiedTime(Date lastPasswordNotifiedTime) {
    this.lastPasswordNotifiedTime = lastPasswordNotifiedTime;
  }

}
