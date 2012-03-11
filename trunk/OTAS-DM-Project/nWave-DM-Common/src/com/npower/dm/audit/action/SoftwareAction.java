package com.npower.dm.audit.action;

/**
* @author chenlei
* @version $Revision: 1.1 $ $Date: 2009/02/19 09:26:24 $
*/
public enum SoftwareAction {

  View_Software("viewSoftware"),
  Delete_Software("deleteSoftware"),
  Edit_Software("editSoftware"),
  Create_Software("createSoftware");
  
  private String value = null;
  
  /**
   * @param value
   */
  private SoftwareAction(String value) {
    this.value = value;
  }
  
  /**
   * @return
   */
  public String getValue() {
    return this.value;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Enum#toString()
   */
  public String toString() {
    return this.value;
  }
  
}
