/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author zhaodonglu
 *
 */
public class Credentials {
  
  private List<Credential> credentials = new ArrayList<Credential>();

  /**
   * 
   */
  public Credentials() {
    super();
  }

  /**
   * @return the credentials
   */
  @XmlElement(name = "credential")
  public List<Credential> getCredentials() {
    return credentials;
  }

  /**
   * @param credentials the credentials to set
   */
  public void setCredentials(List<Credential> credentials) {
    this.credentials = credentials;
  }

}
