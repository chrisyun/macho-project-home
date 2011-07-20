package com.ibm.tivoli.ldap.entity;

import javax.naming.Name;

/**
 * TamSecUser对象对应的LDAP Objectclass为secUser
 * @author ZhaoDongLu
 *
 */
public final class EPasswordPolicy {
  private Name dn;
  
  private int passwordMaxAge;

  /**
   * @return the dn
   */
  public Name getDn() {
    return dn;
  }

  /**
   * @param dn the dn to set
   */
  public void setDn(Name dn) {
    this.dn = dn;
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

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("EPasswordPolicy [dn=%s, passwordMaxAge=%s]", dn, passwordMaxAge);
  }
  
}
