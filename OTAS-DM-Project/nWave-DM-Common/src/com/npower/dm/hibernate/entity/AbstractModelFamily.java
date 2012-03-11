package com.npower.dm.hibernate.entity;

import com.npower.dm.core.ModelFamily;


public abstract class AbstractModelFamily implements ModelFamily {

  private String externalID = null;
  
  //private String name = null;

  public AbstractModelFamily() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ModelFamily#getExternalID()
   */
  public String getExternalID() {
    return externalID;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.hibernate.entity.ModelFamily#setExternalID(java.lang.String)
   */
  public void setExternalID(String externalID) {
    this.externalID = externalID;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelFamily#getName()
   */
  public String getName() {
    return externalID;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelFamily#setName(java.lang.String)
   */
  public void setName(String name) {
    this.externalID = name;
  }

}