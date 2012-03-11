package com.npower.dm.core;


public interface SoftwareTopNRate {

  /**
   * @return the software
   */
  public abstract Software getSoftware();

  /**
   * @return the softwarePackage
   */
  public abstract SoftwarePackage getSoftwarePackage();

  public abstract String getTrackingType();

  public abstract long getId();

  public abstract long getCount();

}