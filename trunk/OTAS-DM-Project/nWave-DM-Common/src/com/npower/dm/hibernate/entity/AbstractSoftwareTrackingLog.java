package com.npower.dm.hibernate.entity;

import java.util.Date;

import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwarePackage;

public class AbstractSoftwareTrackingLog {

  private long trackingLogId;
  protected SoftwarePackage softwarePackage;
  protected Software software;
  protected String trackingType;
  protected Date timeStamp;
  protected String clientIp;
  protected String phoneNumber;

  public AbstractSoftwareTrackingLog() {
    super();
  }


  /** minimal constructor */
  public AbstractSoftwareTrackingLog(String trackingType, Date timeStamp) {
    this.trackingType = trackingType;
    this.timeStamp = timeStamp;
  }

  /** full constructor */
  public AbstractSoftwareTrackingLog(SoftwarePackage softwarePackage, Software software,
      String trackingType, Date timeStamp, String clientIp, String phoneNumber) {
    this.softwarePackage = softwarePackage;
    this.software = software;
    this.trackingType = trackingType;
    this.timeStamp = timeStamp;
    this.clientIp = clientIp;
    this.phoneNumber = phoneNumber;
  }
  public long getTrackingLogId() {
    return this.trackingLogId;
  }

  public void setTrackingLogId(long trackingLogId) {
    this.trackingLogId = trackingLogId;
  }

  public SoftwarePackage getSoftwarePackage() {
    return this.softwarePackage;
  }

  public void setSoftwarePackage(SoftwarePackage nwDmSoftwarePackage) {
    this.softwarePackage = nwDmSoftwarePackage;
  }

  public Software getSoftware() {
    return this.software;
  }

  public void setSoftware(Software software) {
    this.software = software;
  }

  public String getTrackingType() {
    return this.trackingType;
  }

  public void setTrackingType(String trackingType) {
    this.trackingType = trackingType;
  }

  public Date getTimeStamp() {
    return this.timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getClientIp() {
    return this.clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

}