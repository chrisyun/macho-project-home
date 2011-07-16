/**
 * 
 */
package com.ibm.tivoli.icbc.result.icmp;

import java.util.Date;

import com.ibm.tivoli.icbc.result.Result;

/**
 * @author zhaodonglu
 * 
 */
public class ICMPResult extends Result {

  private Date timestamp = new Date();
  private String target = null;
  private long avg = 0;
  private long min = 0;
  private long max = 0;
  private int lostPercent = 0;
  
  private String failMsg = null;

  /**
   * 
   */
  public ICMPResult() {
    super();
  }

  public ICMPResult(String target, long avg, long min, long max) {
    super();
    this.target = target;
    this.avg = avg;
    this.min = min;
    this.max = max;
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
   * @return the lostPercent
   */
  public int getLostPercent() {
    return lostPercent;
  }

  /**
   * @param lostPercent the lostPercent to set
   */
  public void setLostPercent(int lostPercent) {
    this.lostPercent = lostPercent;
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
   * @return the avg
   */
  public long getAvg() {
    return avg;
  }

  /**
   * @param avg the avg to set
   */
  public void setAvg(long avg) {
    this.avg = avg;
  }

  /**
   * @return the min
   */
  public long getMin() {
    return min;
  }

  /**
   * @param min the min to set
   */
  public void setMin(long min) {
    this.min = min;
  }

  /**
   * @return the max
   */
  public long getMax() {
    return max;
  }

  /**
   * @param max the max to set
   */
  public void setMax(long max) {
    this.max = max;
  }

  /**
   * @return the failMsg
   */
  public String getFailMsg() {
    return failMsg;
  }

  /**
   * @param failMsg the failMsg to set
   */
  public void setFailMsg(String failMsg) {
    this.failMsg = failMsg;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ICMPResult [target=" + target + ", avg=" + avg + ", min=" + min + ", max=" + max + ", lostPercent=" + lostPercent + ", failMsg=" + failMsg + "]";
  }

}
