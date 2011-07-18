package com.ibm.tivoli.ldap.entity;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

/**
 * TamSecUser对象对应的LDAP Objectclass为secUser
 * @author ZhaoDongLu
 *
 */
@Entry(objectClasses = { "top", "ePasswordPolicy" })
public final class EPasswordPolicy {
  @Id
  private Name dn;
  
  @Attribute
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
  
}
