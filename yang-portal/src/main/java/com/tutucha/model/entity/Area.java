package com.tutucha.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Area {
  
  private String id = null;
  private String label = null;

  private List<AreaItem> items = new ArrayList<AreaItem>();
  
  public Area() {
    super();
  }

  public Area(String id, String label) {
    super();
    this.id = id;
    this.label = label;
  }

  public Area(String id, String label, List<AreaItem> items) {
    super();
    this.id = id;
    this.label = label;
    this.items = items;
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

  public List<AreaItem> getItems() {
    return items;
  }

  public void setItems(List<AreaItem> items) {
    this.items = items;
  }

}
