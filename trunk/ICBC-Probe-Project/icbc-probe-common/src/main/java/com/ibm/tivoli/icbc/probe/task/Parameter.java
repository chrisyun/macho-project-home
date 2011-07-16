/**
 * 
 */
package com.ibm.tivoli.icbc.probe.task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhao Dong Lu
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
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  public List<String> getValues() {
    return values;
  }
  
  public void setValues(List<String> value) {
    this.values = value;
  }

  public void setValue(String value) {
    this.values.add(value);
  }
}
