package com.npower.dm.hibernate.entity;

/**
 * SoftwareCategoriesEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class SoftwareCategoriesEntity extends AbstractSoftwareCategories implements java.io.Serializable, Comparable<SoftwareCategoriesEntity> {

  // Constructors

  /**
   * 
   */
  private static final long serialVersionUID = -3693723556444027098L;

  /** default constructor */
  public SoftwareCategoriesEntity() {
  }

  /** full constructor */
  public SoftwareCategoriesEntity(SoftwareCategoriesId id, long priority) {
    super(id, priority);
  }

  public int compareTo(SoftwareCategoriesEntity o) {
    if (o == null) {
       return 1;
    }
    return (int)(this.getPriority() - o.getPriority());
  }

}
