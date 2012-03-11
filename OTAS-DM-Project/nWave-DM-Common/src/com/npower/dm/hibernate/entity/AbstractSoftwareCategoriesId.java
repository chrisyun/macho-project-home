package com.npower.dm.hibernate.entity;

import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;

/**
 * AbstractSoftwareCategoriesId entity provides the base persistence
 * definition of the SoftwareCategoriesId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractSoftwareCategoriesId implements java.io.Serializable {

  // Fields

  private Software         software;

  private SoftwareCategory category;

  // Constructors

  /** default constructor */
  public AbstractSoftwareCategoriesId() {
  }

  /** full constructor */
  public AbstractSoftwareCategoriesId(Software softwareEntity, SoftwareCategory softwareCategoryEntity) {
    this.software = softwareEntity;
    this.category = softwareCategoryEntity;
  }

  // Property accessors

  public Software getSoftware() {
    return this.software;
  }

  public void setSoftware(Software softwareEntity) {
    this.software = softwareEntity;
  }

  public SoftwareCategory getCategory() {
    return this.category;
  }

  public void setCategory(SoftwareCategoryEntity softwareCategory) {
    this.category = softwareCategory;
  }

  @Override
  public boolean equals(Object other) {
    if ((this == other))
      return true;
    if ((other == null))
      return false;
    if (!(other instanceof AbstractSoftwareCategoriesId))
      return false;
    AbstractSoftwareCategoriesId castOther = (AbstractSoftwareCategoriesId) other;

    return ((this.getSoftware() == castOther.getSoftware()) || (this.getSoftware() != null
        && castOther.getSoftware() != null && this.getSoftware().equals(castOther.getSoftware())))
        && ((this.getCategory() == castOther.getCategory()) || (this.getCategory() != null
            && castOther.getCategory() != null && this.getCategory().equals(
            castOther.getCategory())));
  }

  @Override
  public int hashCode() {
    int result = 17;

    result = 37 * result + (getSoftware() == null ? 0 : this.getSoftware().hashCode());
    result = 37 * result + (getCategory() == null ? 0 : this.getCategory().hashCode());
    return result;
  }

}