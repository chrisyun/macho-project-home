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

  public UserSubject(String format, String nameID) {
    super();
    this.format = format;
    this.nameID = nameID;
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

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("UserSubject [format=%s, nameID=%s]", format, nameID);
  }

}
