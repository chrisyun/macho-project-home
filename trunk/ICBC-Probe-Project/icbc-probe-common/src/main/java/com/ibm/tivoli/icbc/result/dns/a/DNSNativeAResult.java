/**
 * 
 */
package com.ibm.tivoli.icbc.result.dns.a;

import java.util.Date;

import com.ibm.tivoli.icbc.result.Result;

/**
 * @author zhaodonglu
 * 
 */
public class DNSNativeAResult extends Result {

  private Date timestamp = new Date();
  private String target = null;
  private String ipAddress = null;
  private int elapseTime = 0;

  /**
   * 
   */
  public DNSNativeAResult() {
    super();
  }

  /**
   * @return the timestamp
   */
  public Date getTimestamp() {
    return timestamp;
  }

  /**
   * @param timestamp the timestamp to set
   */
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * @return the target
   */
  public String getTarget() {
    return target;
  }

  /**
   * @param target the target to set
   */
  public void setTarget(String target) {
    this.target = target;
  }

  /**
   * @return the ipAddress
   */
  public String getIpAddress() {
    return ipAddress;
  }

  /**
   * @param ipAddress the ipAddress to set
   */
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  /**
   * @return the elapseTime
   */
  public int getElapseTime() {
    return elapseTime;
  }

  /**
   * @param elapseTime the elapseTime to set
   */
  public void setElapseTime(int elapseTime) {
    this.elapseTime = elapseTime;
  }

}
