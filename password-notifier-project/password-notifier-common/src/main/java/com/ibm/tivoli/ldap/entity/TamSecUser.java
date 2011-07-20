package com.ibm.tivoli.ldap.entity;

import java.util.Date;

import javax.naming.Name;

/**
 * TamSecUser对象对应的LDAP Objectclass为secUser
 * @author ZhaoDongLu
 *
 */
public final class TamSecUser {
  private String dn;
  
  private boolean secHasPolicy;
  
  // 20100913234410.0Z
  private Date secPwdLastChanaged;

  private boolean secAcctValid;
  
  private boolean secPwdValid;
  
  private String secAuthority;
  
  private String secDomainId;
  
  private int passwordMaxAge = 0;
  
  private String principalName = null;
  

  /**
   * @return the principalName
   */
  public String getPrincipalName() {
    return principalName;
  }

  /**
   * @param principalName the principalName to set
   */
  public void setPrincipalName(String principalName) {
    this.principalName = principalName;
  }

  /**
   * @return the dn
   */
  public String getDn() {
    return dn;
  }

  /**
   * @param dn the dn to set
   */
  public void setDn(String dn) {
    this.dn = dn;
  }

  /**
   * @return the secHasPolicy
   */
  public boolean isSecHasPolicy() {
    return secHasPolicy;
  }

  /**
   * @param secHasPolicy the secHasPolicy to set
   */
  public void setSecHasPolicy(boolean secHasPolicy) {
    this.secHasPolicy = secHasPolicy;
  }

  /**
   * @return the secPwdLastChanaged
   */
  public Date getSecPwdLastChanaged() {
    return secPwdLastChanaged;
  }

  /**
   * @param secPwdLastChanaged the secPwdLastChanaged to set
   */
  public void setSecPwdLastChanaged(Date secPwdLastChanaged) {
    this.secPwdLastChanaged = secPwdLastChanaged;
  }

  /**
   * @return the secAcctValid
   */
  public boolean isSecAcctValid() {
    return secAcctValid;
  }

  /**
   * @param secAcctValid the secAcctValid to set
   */
  public void setSecAcctValid(boolean secAcctValid) {
    this.secAcctValid = secAcctValid;
  }

  /**
   * @return the secPwdValid
   */
  public boolean isSecPwdValid() {
    return secPwdValid;
  }

  /**
   * @param secPwdValid the secPwdValid to set
   */
  public void setSecPwdValid(boolean secPwdValid) {
    this.secPwdValid = secPwdValid;
  }

  /**
   * @return the secAuthority
   */
  public String getSecAuthority() {
    return secAuthority;
  }

  /**
   * @param secAuthority the secAuthority to set
   */
  public void setSecAuthority(String secAuthority) {
    this.secAuthority = secAuthority;
  }

  /**
   * @return the secDomainId
   */
  public String getSecDomainId() {
    return secDomainId;
  }

  /**
   * @param secDomainId the secDomainId to set
   */
  public void setSecDomainId(String secDomainId) {
    this.secDomainId = secDomainId;
  }

  /**
   * @return the passwordMaxAge
   */
  public int getPasswordMaxAge() {
    return passwordMaxAge;
  }

  /**
   * @param passwordMaxAge the passwordMaxAge to set
   */
  public void setPasswordMaxAge(int passwordMaxAge) {
    this.passwordMaxAge = passwordMaxAge;
  }

}
