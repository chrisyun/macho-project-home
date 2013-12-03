package com.npower.dm.hibernate.entity;

import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwarePackage;

/**
 * VSoftwareTrackLogAll generated by MyEclipse Persistence Tools
 */

public class VSoftwareTrackLogAll extends AbstractVSoftwareTrackLogAll implements java.io.Serializable {

  // Fields

  /** default constructor */
  public VSoftwareTrackLogAll() {
  }

  /** minimal constructor */
  public VSoftwareTrackLogAll(long id, String trackingType) {
    super(id,trackingType);
  }

  /** full constructor */
  public VSoftwareTrackLogAll(long id, Software software, SoftwarePackage softwarePackage, String trackingType ,long count) {
    super(id,software, softwarePackage, trackingType, count);
  }

}