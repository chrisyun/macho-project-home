package com.npower.dm.hibernate.entity;

import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.core.SoftwareTopNRate;



public class AbstractVSoftwareTrackLogDaily implements SoftwareTopNRate {
  
  private long id;
  private Software software;
  private SoftwarePackage softwarePackage;
  private String trackingType;
  private String dayOfYear;
  private long count;

  public AbstractVSoftwareTrackLogDaily() {
    super();
  }
  
  /** minimal constructor */
  public AbstractVSoftwareTrackLogDaily(long id, String trackingType) {
    this.trackingType = trackingType;
    this.id = id;
  }

  /**
   * @param software
   * @param softwarePackage
   * @param trackingType
   * @param dayOfYear
   */
  public AbstractVSoftwareTrackLogDaily(long id, Software software, SoftwarePackage softwarePackage, String trackingType, String dayOfYear,long count) {
    super();
    this.id = id;
    this.software = software;
    this.softwarePackage = softwarePackage;
    this.trackingType = trackingType;
    this.dayOfYear = dayOfYear;
    this.count = count;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareTopNRate#getSoftware()
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

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareTopNRate#getSoftwarePackage()
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

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareTopNRate#getTrackingType()
   */
  public String getTrackingType() {
    return this.trackingType;
  }

  public void setTrackingType(String trackingType) {
    this.trackingType = trackingType;
  }

  /**
   * @return the dayOfYear
   */
  public String getDayOfYear() {
    return dayOfYear;
  }

  /**
   * @param dayOfYear the dayOfYear to set
   */
  public void setDayOfYear(String dayOfYear) {
    this.dayOfYear = dayOfYear;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareTopNRate#getId()
   */
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.SoftwareTopNRate#getCount()
   */
  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }

}