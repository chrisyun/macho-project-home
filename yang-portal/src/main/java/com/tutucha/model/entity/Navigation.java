package com.tutucha.model.entity;

public class Navigation {

  /**
   * 导航条目的ID
   */
  private String id = null;

  /**
   * 标题
   */
  private String label = null;

  /**
   * 目标URL
   */
  private String url = null;

  /**
   * 导航的图标，可能的形式为style, class, url. 若为url, 则为图片地址，否则为css相关的修饰方式
   */
  private String icon = null;

  /**
   * 导航的URL
   */
  private String description = null;

  /**
   * 导航的分类标识
   */
  private String categoryId = null;

  public Navigation() {
    super();
  }

  /**
   * @param id
   * @param label
   * @param url
   * @param icon
   * @param categoryId
   */
  public Navigation(String id, String label, String url, String icon, String categoryId) {
    super();
    this.id = id;
    this.label = label;
    this.url = url;
    this.icon = icon;
    this.categoryId = categoryId;
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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String iconUrl) {
    this.icon = iconUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

}
