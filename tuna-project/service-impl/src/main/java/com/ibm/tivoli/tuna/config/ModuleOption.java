/**
 * 
 */
package com.ibm.tivoli.tuna.config;

/**
 * @author zhaodonglu
 * 
 */
public class ModuleOption {

  private String name = null;
  private String value = null;

  /**
   * 
   */
  public ModuleOption() {
    super();
  }

  public ModuleOption(String name, String value) {
    super();
    this.name = name;
    this.value = value;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /**
   * @param value
   *          the value to set
   */
  public void setValue(String value) {
    this.value = value;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("ModuleOption [name=%s, value=%s]", name, value);
  }

}
