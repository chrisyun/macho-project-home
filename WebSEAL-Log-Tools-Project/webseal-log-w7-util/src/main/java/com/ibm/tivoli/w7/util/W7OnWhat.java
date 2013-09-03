/**
 * 
 */
package com.ibm.tivoli.w7.util;

/**
 * @author zhaodonglu
 * 
 */
public class W7OnWhat {

  private String type = null;
  private String path = null;
  private String name = null;

  /**
   * 
   */
  public W7OnWhat() {
    super();
  }

  /**
   * @param type
   * @param path
   * @param name
   */
  public W7OnWhat(String type, String path, String name) {
    super();
    this.type = type;
    this.path = path;
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
   * @return the path
   */
  public String getPath() {
    return path;
  }

  /**
   * @param path
   *          the path to set
   */
  public void setPath(String path) {
    this.path = path;
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
