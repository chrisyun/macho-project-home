/**
 * 
 */
package com.ibm.tivoli.icbc.result.dns;

/**
 * @author Zhao Dong Lu
 * 
 */
public class ResolverEntity {

  private String nameServer = null;

  /**
   * 
   */
  public ResolverEntity() {
    super();
  }

  public ResolverEntity(String nameServer) {
    this.nameServer = nameServer;
  }

  public String getNameServer() {
    return nameServer;
  }

  public void setNameServer(String nameServer) {
    this.nameServer = nameServer;
  }

}
