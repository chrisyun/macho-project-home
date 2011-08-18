/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaodonglu
 *
 */
public class AttributeStatement {
  
  private List<Attribute> attributes = new ArrayList<Attribute>();

  /**
   * 
   */
  public AttributeStatement() {
    super();
  }

  /**
   * @return the attributes
   */
  public List<Attribute> getAttributes() {
    return attributes;
  }

  /**
   * @param attributes the attributes to set
   */
  public void setAttributes(List<Attribute> attributes) {
    this.attributes = attributes;
  }

}
