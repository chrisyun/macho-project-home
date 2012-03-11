package com.npower.dm.core;

public interface ModelFamily {

  /**
   * @return Returns the externalID.
   */
  public abstract String getExternalID();

  /**
   * @param externalID The externalID to set.
   */
  public abstract void setExternalID(String externalID);
  
  /**
   * @return Returns the name.
   */
  public abstract String getName();
  
  /**
   * @param name The name to set.
   */
  public abstract void setName(String name);

}