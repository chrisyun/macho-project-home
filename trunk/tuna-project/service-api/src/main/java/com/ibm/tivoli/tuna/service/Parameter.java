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
public class Parameter {

  private String name = null;
  private List<String> values = new ArrayList<String>();
  /**
   * 
   */
  public Parameter() {
    super();
  }
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }
  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }
  /**
   * @return the values
   */
  public List<String> getValues() {
    return values;
  }
  /**
   * @param values the values to set
   */
  public void setValues(List<String> values) {
    this.values = values;
  }

}
