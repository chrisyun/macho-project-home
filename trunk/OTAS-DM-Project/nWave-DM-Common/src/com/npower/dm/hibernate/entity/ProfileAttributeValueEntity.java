/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ProfileAttributeValueEntity.java,v 1.4 2007/04/12 08:04:13 zhao Exp $
 * $Revision: 1.4 $
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
package com.npower.dm.hibernate.entity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Hibernate;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAttribute;
import com.npower.dm.util.DMUtil;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2007/04/12 08:04:13 $
 */
public class ProfileAttributeValueEntity extends AbstractProfileAttributeValue implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public ProfileAttributeValueEntity() {
  }

  /** minimal constructor */
  public ProfileAttributeValueEntity(ProfileAttribute profileAttribute, boolean isMultiValued) {
    super(profileAttribute, isMultiValued);
  }

  /**
   * Constructor for Single Value of Clob
   * 
   * @param profileAttribute
   * @param isMultiValued
   * @param rawData
   */
  public ProfileAttributeValueEntity(ProfileAttribute profileAttribute, Clob rawData) {
    this();
    boolean multiValue = false;
    this.setIsMultiValued(multiValue);
    this.setProfileAttribute(profileAttribute);
    this.setRawData(rawData);

    // this.setItemDataKind();
    // this.setBinaryData();
    // this.setUpdateId();
    // this.setMFormat();
    // this.setMType();
  }

  /**
   * Constructor for Single Value of String.
   * 
   * @param profileAttribute
   * @param isMultiValued
   * @param rawData
   */
  public ProfileAttributeValueEntity(ProfileAttribute profileAttribute, String rawData) {
    this();
    boolean multiValue = false;
    this.setIsMultiValued(multiValue);
    this.setProfileAttribute(profileAttribute);
    this.setRawData(Hibernate.createClob(rawData));
    this.setItemDataKind(ITEM_DATA_KIND_TEXT);

  }

  // public methods for public services
  // ****************************************************************************

  /**
   * Obtain the first text value of the Attribute.
   * 
   * @return String
   * @throws DMException
   */
  public String getStringValue() throws DMException {
    String[] result = this.getStringValues();
    if (result != null && result.length > 0) {
      return result[0];
    } else {
      return null;
    }
  }

  /**
   * Obtain the text values. This method will return all of clob value, and
   * convert into String.
   * 
   * @return String[]
   */
  public String[] getStringValues() throws DMException {
    try {
      if (this.getIsMultiValued() == false) {
        // Single Value
        Clob clob = this.getRawData();
        String result = DMUtil.convertClob2String(clob);
        if (result != null) {
          return new String[] { result };
        } else {
          return new String[0];
        }
      } else {
        // Multi-value
        Set itemSet = this.getProfileValueItems();
        String[] result = new String[itemSet.size()];
        int j = 0;
        for (Iterator items = itemSet.iterator(); items.hasNext();) {
          ProfileValueItem item = (ProfileValueItem) items.next();
          Clob clob = item.getRawData();
          result[j++] = DMUtil.convertClob2String(clob);
        }
        return result;
      }
    } catch (SQLException e) {
      throw new DMException(e);
    } catch (IOException e) {
      throw new DMException(e);
    }
  }

  /**
   * Obtain the first binary value of the Attribute.
   * 
   * @return Blob
   * @throws DMException
   */
  // TODO Compose Testcase
  public Blob getBlobValue() throws DMException {
    Blob[] result = this.getBlobValues();
    if (result != null && result.length > 0) {
      return result[0];
    } else {
      return null;
    }
  }

  /**
   * Obtain the binary values. T his method will return all of binary value, and
   * convert into Blob.
   * 
   * @return Blob[]
   * @throws DMException
   */
  // TODO Compose Testcase
  public Blob[] getBlobValues() throws DMException {
    if (this.getIsMultiValued() == false) {
      // Single Value
      Blob blob = this.getBinaryData();
      if (blob != null) {
        return new Blob[] { blob };
      } else {
        return new Blob[0];
      }
    } else {
      // Multi-value
      Set itemSet = this.getProfileValueItems();
      Blob[] result = new Blob[itemSet.size()];
      int j = 0;
      for (Iterator items = itemSet.iterator(); items.hasNext();) {
        ProfileValueItem item = (ProfileValueItem) items.next();
        result[j++] = item.getBinaryData();
      }
      return result;
    }
  }

  public byte[] getBytes() throws DMException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
        Blob blob = this.getBlobValue();
        if (blob == null) {
           return new byte[0];
        }
        InputStream in = blob.getBinaryStream();
        if (in == null) {
           return new byte[0];
        }
        byte[] buf = new byte[512];
        int len = in.read(buf);
        while (len > 0) {
              out.write(buf, 0, len);
              len = in.read(buf);
        }
        in.close();
        return out.toByteArray();
    } catch (IOException e) {
      throw new DMException("Error in read attribute value bytes in binary mode.", e);
    } catch (SQLException e) {
      throw new DMException("Error in read attribute value bytes in binary mode.", e);
    }
  }
}
