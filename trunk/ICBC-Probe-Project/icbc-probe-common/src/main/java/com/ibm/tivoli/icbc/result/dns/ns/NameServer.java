/**
 * 
 */
package com.ibm.tivoli.icbc.result.dns.ns;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhao Dong Lu
 *
 */
public class NameServer {
  
  private String name = null;
  private List<String> inetAddresses = new ArrayList<String>();

  /**
   * 
   */
  public NameServer() {
    super();
  }

  public NameServer(String nameServerName) {
    this.name = nameServerName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getInetAddresses() {
    return inetAddresses;
  }

  public void setInetAddresses(List<String> inetAddresses) {
    this.inetAddresses = inetAddresses;
  }

}
