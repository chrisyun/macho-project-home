package com.tutucha.model.entity;

public class AreaItem {

  private String id = null;
  
  private Navigation navigation = null;
  
  public AreaItem() {
    super();
  }

  public AreaItem(String id, Navigation navigation) {
    super();
    this.id = id;
    this.navigation = navigation;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Navigation getNavigation() {
    return navigation;
  }

  public void setNavigation(Navigation navigation) {
    this.navigation = navigation;
  }

}
