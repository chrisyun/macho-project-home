/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

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
  
  
  public Parameter(String name, List<String> values) {
    super();
    this.name = name;
    this.values = values;
  }


  public Parameter(String name, String value) {
    super();
    this.name = name;
    this.values.add(value);
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
  @XmlElement(name = "value")
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
