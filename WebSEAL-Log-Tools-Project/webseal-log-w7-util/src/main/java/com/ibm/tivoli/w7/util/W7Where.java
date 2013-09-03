/**
 * 
 */
package com.ibm.tivoli.w7.util;

/**
 * @author zhaodonglu
 * 
 */
public class W7Where {

  private String type = null;
  private String name = null;

  /**
   * 
   */
  public W7Where() {
    super();
  }

  /**
   * @param type
   * @param name
   */
  public W7Where(String type, String name) {
    super();
    this.type = type;
    this.name = name;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type
   *          the type to set
   */
  public void setType(String type) {
    this.type = type;
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

}
