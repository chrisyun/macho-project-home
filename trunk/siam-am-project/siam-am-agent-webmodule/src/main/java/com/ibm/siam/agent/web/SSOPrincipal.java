/**
 * 
 */
package com.ibm.siam.agent.web;

import java.security.Principal;

/**
 * @author zhaodonglu
 *
 */
public class SSOPrincipal implements Principal {
  
  public final static String NAME_OF_SESSION_ATTR = "_SSO_USER_PRINCIPAL_";
  
  private String uid = null;
  private String cn = null;

  /**
   * 
   */
  public SSOPrincipal() {
    super();
  }

  /**
   * @return the uid
   */
  public String getUid() {
    return uid;
  }

  /**
   * @param uid the uid to set
   */
  public void setUid(String uid) {
    this.uid = uid;
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

  /* (non-Javadoc)
   * @see java.security.Principal#getName()
   */
  public String getName() {
    return this.uid;
  }

}
