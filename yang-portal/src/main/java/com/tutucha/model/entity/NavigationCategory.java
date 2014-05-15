package com.tutucha.model.entity;

public class NavigationCategory {
  
  /**
   * 类别标识
   */
  private String id = null;
  
  /**
   * 类别名称
   */
  private String label = null;
  
  /**
   * 父类别的标识
   */
  private String parentId = null;

  /**
   * 
   */
  public NavigationCategory() {
    super();
  }

  public NavigationCategory(String id) {
    super();
    this.id = id;
  }

  /**
   * @param id
   * @param label
   * @param parentId
   */
  public NavigationCategory(String id, String label, String parentId) {
    super();
    this.id = id;
    this.label = label;
    this.parentId = parentId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    NavigationCategory other = (NavigationCategory) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
