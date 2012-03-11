package com.npower.dm.hibernate.entity;

import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;

/**
 * SoftwareCategoriesId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class SoftwareCategoriesId extends AbstractSoftwareCategoriesId implements java.io.Serializable {

  // Constructors

  /**
   * 
   */
  private static final long serialVersionUID = -8691532765144390698L;

  /** default constructor */
  public SoftwareCategoriesId() {
  }

  /** full constructor */
  public SoftwareCategoriesId(Software softwareEntity, SoftwareCategory softwareCategoryEntity) {
    super(softwareEntity, softwareCategoryEntity);
  }

}
