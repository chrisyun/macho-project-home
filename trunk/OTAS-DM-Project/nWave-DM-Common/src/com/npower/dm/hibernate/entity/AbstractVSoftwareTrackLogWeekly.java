package com.npower.dm.hibernate.entity;

import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.core.SoftwareTopNRate;



public class AbstractVSoftwareTrackLogWeekly implements SoftwareTopNRate {

  private long id;
  private Software software;
  private SoftwarePackage softwarePackage;
  private String trackingType;
  private String weekOfYear;
  private long count;


  public AbstractVSoftwareTrackLogWeekly() {
    super();
  }

  /** minimal constructor */
  public AbstractVSoftwareTrackLogWeekly(long id, String trackingType) {
    this.trackingType = trackingType;
    this.id = id;
  }

  /**
   * @param software
   * @param softwarePackage
   * @param trackingType
   * @param weekOfYear
   */
  public AbstractVSoftwareTrackLogWeekly(long id, Software software, SoftwarePackage softwarePackage, String trackingType,
      String weekOfYear, long count) {
    super();
    this.id = id;
    this.software = software;
    this.softwarePackage = softwarePackage;
    this.trackingType = trackingType;
    this.weekOfYear = weekOfYear;
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

  public String getWeekOfYear() {
    return this.weekOfYear;
  }

  public void setWeekOfYear(String weekOfYear) {
    this.weekOfYear = weekOfYear;
  }

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

}