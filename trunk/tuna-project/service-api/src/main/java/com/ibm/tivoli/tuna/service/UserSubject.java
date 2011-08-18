/**
 * 
 */
package com.ibm.tivoli.tuna.service;

/**
 * @author zhaodonglu
 * 
 */
public class UserSubject {

  private String format = null;
  private String nameID = null;

  /**
   * 
   */
  public UserSubject() {
    super();
  }

  /**
   * @return the format
   */
  public String getFormat() {
    return format;
  }

  /**
   * @param format
   *          the format to set
   */
  public void setFormat(String nameType) {
    this.format = nameType;
  }

  /**
   * @return the nameID
   */
  public String getNameID() {
    return nameID;
  }

  /**
   * @param nameID
   *          the nameID to set
   */
  public void setNameID(String nameID) {
    this.nameID = nameID;
  }

}
