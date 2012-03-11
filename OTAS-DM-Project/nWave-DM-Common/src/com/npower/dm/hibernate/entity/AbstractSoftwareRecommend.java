package com.npower.dm.hibernate.entity;

import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;

/**
 * AbstractSoftwareRecommend entity provides the base persistence definition
 * of the SoftwareRecommendEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractSoftwareRecommend implements java.io.Serializable {

  // Fields
  private long id = 0;

  private SoftwareCategory category;

  private Software         software;

  private long                    priority;

  private String description;
  
  // Constructors

  /** default constructor */
  public AbstractSoftwareRecommend() {
    super();
  }

  // Property accessors

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getPriority() {
    return this.priority;
  }

  public void setPriority(long priority) {
    this.priority = priority;
  }

  public SoftwareCategory getCategory() {
    return category;
  }

  public void setCategory(SoftwareCategory category) {
    this.category = category;
  }

  public Software getSoftware() {
    return software;
  }

  public void setSoftware(Software software) {
    this.software = software;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}