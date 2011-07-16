/**
 * 
 */
package com.ibm.tivoli.icbc.result.dns.a;

/**
 * @author Zhao Dong Lu
 * 
 */
public class AItem {

  private String targetHostname = null;
  private String serverIPAddress = null;
  private String targetHostIPAddress = null;
  private String error = null;
  private int elapseTime = 0;

  /**
   * 
   */
  public AItem() {
    super();
  }

  public AItem(String targetHostname, String serverIPAddress) {
    super();
    this.targetHostname = targetHostname;
    this.serverIPAddress = serverIPAddress;
  }

  public AItem(String targetHostname, String serverIPAddress, String targetHostIPAddress) {
    super();
    this.targetHostname = targetHostname;
    this.serverIPAddress = serverIPAddress;
    this.targetHostIPAddress = targetHostIPAddress;
  }

  public AItem(String targetHostname, String targetHostIPAddress, int elapseTime) {
    super();
    this.targetHostname = targetHostname;
    this.elapseTime = elapseTime;
    this.targetHostIPAddress = targetHostIPAddress;
  }

  public String getTargetHostname() {
    return targetHostname;
  }

  public void setTargetHostname(String targetHostname) {
    this.targetHostname = targetHostname;
  }

  public String getServerIPAddress() {
    return serverIPAddress;
  }

  public void setServerIPAddress(String serverIPAddress) {
    this.serverIPAddress = serverIPAddress;
  }

  public String getTargetHostIPAddress() {
    return targetHostIPAddress;
  }

  public void setTargetHostIPAddress(String targetHostIPAddress) {
    this.targetHostIPAddress = targetHostIPAddress;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

}
