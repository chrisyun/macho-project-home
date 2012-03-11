package com.npower.dm.hibernate.entity;

import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;

/**
 * AbstractSoftwareCategories entity provides the base persistence
 * definition of the SoftwareCategoriesEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractSoftwareCategories implements java.io.Serializable {

  // Fields

  private SoftwareCategoriesId id;

  private long                     priority;

  // Fields

  private Software         software;

  private SoftwareCategory category;

  // Constructors

  /** default constructor */
  public AbstractSoftwareCategories() {
  }

  /** full constructor */
  public AbstractSoftwareCategories(SoftwareCategoriesId id, long priority) {
    this.id = id;
    this.priority = priority;
  }

  // Property accessors

  public SoftwareCategoriesId getId() {
    return this.id;
  }

  public void setId(SoftwareCategoriesId id) {
    this.id = id;
  }

  public long getPriority() {
    return this.priority;
  }

  public void setPriority(long priority) {
    this.priority = priority;
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
   * @return the category
   */
  public SoftwareCategory getCategory() {
    return category;
  }

  /**
   * @param category the category to set
   */
  public void setCategory(SoftwareCategory category) {
    this.category = category;
  }

}