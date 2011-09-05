/**
 * 
 */
package com.ibm.tivoli.tuna.service;

/**
 * @author zhaodonglu
 *
 */
public class ReuqestPrincipal {
  
  private String type = null;
  private String name = null;

  /**
   * 
   */
  public ReuqestPrincipal() {
    super();
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type the type to set
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
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("ReuqestPrincipal [type=%s, name=%s]", type, name);
  }
  

}
