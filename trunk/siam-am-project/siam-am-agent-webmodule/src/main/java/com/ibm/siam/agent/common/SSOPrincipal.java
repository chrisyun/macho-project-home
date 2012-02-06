/**
 * 
 */
package com.ibm.siam.agent.common;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaodonglu
 *
 */
public class SSOPrincipal implements Principal {
  
  public final static String NAME_OF_SESSION_ATTR = "_SSO_USER_PRINCIPAL_";
  
  private String authenMethod = null;
  
  private String uid = null;
  private String cn = null;

  private Map<String, Serializable> attributes = new HashMap<String, Serializable>();
  /**
   * @param uid
   * @param cn
   */
  public SSOPrincipal(String authenMethod, String uid, String cn) {
    super();
    this.authenMethod = authenMethod;
    this.uid = uid;
    this.cn = cn;
  }

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

  /**
   * @return the authenMethod
   */
  public String getAuthenMethod() {
    return authenMethod;
  }

  /**
   * @param authenMethod the authenMethod to set
   */
  public void setAuthenMethod(String authenMethod) {
    this.authenMethod = authenMethod;
  }
  
  public void setAttribute(String name, Serializable value) {
    this.attributes.put(name, value);
  }

}
