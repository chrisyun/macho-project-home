/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author zhaodonglu
 *
 */
public class Attribute {
  
  private String name = null;
  private String format = null;
  private List<String> values = new ArrayList<String>();

  /**
   * 
   */
  public Attribute() {
    super();
  }

  public Attribute(String name, String format, List<String> values) {
    super();
    this.name = name;
    this.format = format;
    this.values = values;
  }

  public Attribute(String name, String format, String value) {
    super();
    this.name = name;
    this.format = format;
    this.values.add(value);
  }

  public Attribute(String name, String format, String[] values) {
    super();
    this.name = name;
    this.format = format;
    this.values.addAll(Arrays.asList(values));
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
   * @return the format
   */
  public String getFormat() {
    return format;
  }

  /**
   * @param format the format to set
   */
  public void setFormat(String format) {
    this.format = format;
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

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    final int maxLen = 100;
    return String.format("Attribute [name=%s, format=%s, values=%s]", name, format, values != null ? values.subList(0, Math.min(values.size(), maxLen)) : null);
  }

}
