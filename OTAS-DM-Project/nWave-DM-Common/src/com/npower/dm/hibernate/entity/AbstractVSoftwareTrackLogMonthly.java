package com.npower.dm.hibernate.entity;

import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.core.SoftwareTopNRate;



public class AbstractVSoftwareTrackLogMonthly implements SoftwareTopNRate {

  private long id;
  private Software software;
  private SoftwarePackage softwarePackage;
  private String monthOfYear;
  private String trackingType;
  private long count;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }

  public AbstractVSoftwareTrackLogMonthly() {
    super();
  }

  /** minimal constructor */
  public AbstractVSoftwareTrackLogMonthly(long id, String trackingType) {
    this.trackingType = trackingType;
    this.id = id;
  }

  /**
   * @param software
   * @param softwarePackage
   * @param trackingType
   * @param monthOfYear
   */
  public AbstractVSoftwareTrackLogMonthly(long id,Software software, SoftwarePackage softwarePackage, String trackingType, String monthOfYear, long count) {
    super();
    this.id = id;
    this.software = software;
    this.softwarePackage = softwarePackage;
    this.trackingType = trackingType;
    this.monthOfYear = monthOfYear;
    this.count = count;
  }

  /**
   * @return the software
   */
  public Software getSoftware() {
    return software;
  }

  /**
   * @param software the software to set
   */
  public void setSoftware(Software software) {
    this.software = software;
  }

  /**
   * @return the softwarePackage
   */
  public SoftwarePackage getSoftwarePackage() {
    return softwarePackage;
  }

  /**
   * @param softwarePackage the softwarePackage to set
   */
  public void setSoftwarePackage(SoftwarePackage softwarePackage) {
    this.softwarePackage = softwarePackage;
  }

  public String getTrackingType() {
    return this.trackingType;
  }

  public void setTrackingType(String trackingType) {
    this.trackingType = trackingType;
  }

  /**
   * @return the monthOfYear
   */
  public String getMonthOfYear() {
    return monthOfYear;
  }

  /**
   * @param monthOfYear the monthOfYear to set
   */
  public void setMonthOfYear(String monthOfYear) {
    this.monthOfYear = monthOfYear;
  }

}