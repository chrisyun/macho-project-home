package com.ibm.lbs.ldap;

import java.util.Date;

public class PerformanceMonitor {
  
  private int success = 0;
  private int failure = 0;

  private long startTime = System.currentTimeMillis();
  
  public PerformanceMonitor() {
    super();
  }
  
  public synchronized void incSuccess() {
    if (this.success == 0) {
       this.startTime = System.currentTimeMillis();
    }
    this.success++;
  }
  
  public synchronized void incFailure() {
    this.failure++;
  }

  public synchronized int getSuccess() {
    return success;
  }

  public synchronized int getFailure() {
    return failure;
  }

  @Override
  public String toString() {
    return String.format("PerformanceMonitor [%s] [total success=%s, total failure=%s, TPS success=%s]", new Date(), success, failure, ((int)success * 1000 /(System.currentTimeMillis() - this.startTime)));
  }


}
