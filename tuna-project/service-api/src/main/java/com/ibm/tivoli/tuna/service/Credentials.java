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

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final int maxLen = 100;
    return String.format("Credentials [credentials=%s]", credentials != null ? credentials.subList(0, Math.min(credentials.size(), maxLen)) : null);
  }

}
