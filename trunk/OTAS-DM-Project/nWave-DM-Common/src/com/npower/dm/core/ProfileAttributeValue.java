/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/ProfileAttributeValue.java,v 1.3 2007/04/12 08:04:13 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2007/04/12 08:04:13 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
 *
 * This SOURCE CODE FILE, which has been provided by NPower as part
 * of a NPower product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of NPower.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 * ===============================================================================================
 */
package com.npower.dm.core;

import java.sql.Blob;
import java.sql.Clob;

/**
 * <p>Represent a ProfileAttributeValue</p>
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/04/12 08:04:13 $
 */
public interface ProfileAttributeValue {

  /**
   * Item Data Type: text
   */
  public static final String ITEM_DATA_KIND_TEXT = "text";

  /**
   * Item Data Type: bin
   */
  public static final String ITEM_DATA_KIND_BIN = "bin";

  /**
   * Return the ID.
   * @return
   */
  public abstract long getID();

  /**
   * Return the ProfileAttribute which own this ProfileAttributeValue.
   * @return
   */
  public abstract ProfileAttribute getProfileAttribute();

  /**
   * Set a ProfileAttribute for this ProfileAttributeValue.
   * @param profileAttribute
   */
  public abstract void setProfileAttribute(ProfileAttribute profileAttribute);

  /**
   * If true, this attribute value is multiple.
   * 
   * @return
   */
  public abstract boolean getIsMultiValued();

  /**
   * Set true, if this attribute value is multiple.
   * @param isMultiValued
   */
  public abstract void setIsMultiValued(boolean isMultiValued);

  /**
   * Return the Data kind.
   * @return
   */
  public abstract String getItemDataKind();

  /**
   * Set the Data kind.
   * 
   * @param itemDataKind
   */
  public abstract void setItemDataKind(String itemDataKind);

  /**
   * Return the Clob data
   * @return
   */
  public abstract Clob getRawData();

  /**
   * Set a Clob data to this ProfileAttributeValue.
   * @param rawData
   */
  public abstract void setRawData(Clob rawData);

  /**
   * Return the binary data.
   * @return
   */
  public abstract Blob getBinaryData();

  /**
   * Set a Blob data into this ProfileAttributeValue.
   * 
   * @param binaryData
   */
  public abstract void setBinaryData(Blob binaryData);

  /**
   * Return MIMEFormat of this ProfileAttributeValue.
   * @return
   */
  public abstract String getMFormat();

  /**
   * Set a MIMEFormat for this ProfileAttributeValue.
   * 
   * @param MFormat
   */
  public abstract void setMFormat(String MFormat);

  /**
   * Return the MIME Type of this ProfileAttributeValue.
   * @return
   */
  public abstract String getMType();

  /**
   * Set the MIME Type for this ProfielAttributeValue.
   * 
   * @param MType
   */
  public abstract void setMType(String MType);

  /**
   * Obtain the first text value of the Attribute.
   * 
   * @return String
   * @throws DMException
   */
  public String getStringValue() throws DMException;
  
  /**
   * Obtain the first binary value of the Attribute.
   * @return
   * @throws DMException
   */
  public byte[] getBytes() throws DMException;

  /**
   * Obtain the text values. This method will return all of clob value, and
   * convert into String.
   * 
   * @return String[]
   */
  public String[] getStringValues() throws DMException;

  /**
   * A set of ProfileValueMap
   * @return
   */
  //public abstract Set getProfileValueMaps();

  /**
   * A set of ProfileAssignValues
   * @return
   */
  //public abstract Set getProfileAssignValues();

  /**
   * A set of ProfileValueItem.
   * @return
   */
  //public abstract Set getProfileValueItems();
  
  /**
   * 
   * @return
   */
  //public abstract String getUpdateId();

  /**
   * 
   * @param updateId
   */
  //public abstract void setUpdateId(String updateId);

}