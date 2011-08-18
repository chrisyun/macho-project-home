/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaodonglu
 *
 */
public class Requester {
  
  private List<ReuqestPrincipal> principals = new ArrayList<ReuqestPrincipal>();
  /**
   * 
   */
  public Requester() {
    super();
  }
  /**
   * @return the principals
   */
  public List<ReuqestPrincipal> getPrincipals() {
    return principals;
  }
  /**
   * @param principals the principals to set
   */
  public void setPrincipals(List<ReuqestPrincipal> principals) {
    this.principals = principals;
  }

}
