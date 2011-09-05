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

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final int maxLen = 100;
    return String.format("AttributeStatement [attributes=%s]", attributes != null ? attributes.subList(0, Math.min(attributes.size(), maxLen)) : null);
  }

}
