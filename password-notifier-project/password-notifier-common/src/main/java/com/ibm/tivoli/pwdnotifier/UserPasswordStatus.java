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
  private Date lastPasswordChangedTime = null;
  /**
   * Seconds of password max age for this user.
   * 0 means "Unset"
   */
  private long passwordMaxAgeInSeconds = 0;
  
  private Date passwordExpireTime = null;

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
   * @return the passwordMaxAgeInSeconds
   */
  public long getPasswordMaxAgeInSeconds() {
    return passwordMaxAgeInSeconds;
  }
  /**
   * @param passwordMaxAgeInSeconds the passwordMaxAgeInSeconds to set
   */
  public void setPasswordMaxAgeInSeconds(long passwordAgeInSeconds) {
    this.passwordMaxAgeInSeconds = passwordAgeInSeconds;
  }
  
  /**
   * @param passwordExpireTime the passwordExpireTime to set
   */
  public void setPasswordExpireTime(Date passwordExpireTime) {
    this.passwordExpireTime = passwordExpireTime;
  }
  /**
   * @return the passwordExpireTime
   */
  public Date getPasswordExpireTime() {
    return passwordExpireTime;
  }
}
