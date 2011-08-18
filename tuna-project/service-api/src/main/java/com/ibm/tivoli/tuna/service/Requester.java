/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author zhaodonglu
 *
 */
@XmlRootElement(name = "requester")
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
  @XmlElement(name = "principal")
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
