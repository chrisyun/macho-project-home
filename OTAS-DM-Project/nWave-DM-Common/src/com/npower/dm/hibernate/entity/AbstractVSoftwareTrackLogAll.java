package com.npower.dm.hibernate.entity;

import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.core.SoftwareTopNRate;


public class AbstractVSoftwareTrackLogAll implements SoftwareTopNRate {

  private long id;
  private Software software;
  private long count;
  private SoftwarePackage softwarePackage;
  private String trackingType;

  public AbstractVSoftwareTrackLogAll() {
    super();
  }


  /** minimal constructor */
  public AbstractVSoftwareTrackLogAll(long id, String trackingType) {
    this.id = id;
    this.trackingType = trackingType;
  }

  /**
   * @param software
   * @param softwarePackage
   * @param trackingType
   */
  public AbstractVSoftwareTrackLogAll(long id, Software software, SoftwarePackage softwarePackage, String trackingType,long count) {
    super();
    this.id = id;
    this.software = software;
    this.softwarePackage = softwarePackage;
    this.trackingType = trackingType;
    this.count = count;
  }


  public String getTrackingType() {
    return this.trackingType;
  }

  public void setTrackingType(String trackingType) {
    this.trackingType = trackingType;
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


  public long getCount() {
    return count;
  }


  public void setCount(long count) {
    this.count = count;
  }


  public long getId() {
    return id;
  }


  public void setId(long id) {
    this.id = id;
  }

}