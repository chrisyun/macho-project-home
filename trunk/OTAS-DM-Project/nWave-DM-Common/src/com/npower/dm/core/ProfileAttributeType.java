/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/ProfileAttributeType.java,v 1.8 2008/11/05 08:04:02 zhao Exp $
 * $Revision: 1.8 $
 * $Date: 2008/11/05 08:04:02 $
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

import java.util.Date;
import java.util.Set;

/**
 * <p>Represent a ProfileAttributeType.</p>
 * @author Zhao DongLu
 * @version $Revision: 1.8 $ $Date: 2008/11/05 08:04:02 $
 * 
 */
public interface ProfileAttributeType {
  
  /**
   * Attribute Type: APLink
   * Return AP Node Path
   */
  public final static String TYPE_APLINK = "APLink";
  
  /**
   * Attribute Type: APName
   * Return AP Node name
   */
  public final static String TYPE_APNAME = "APName";

  /**
   * Attribute Type: SelfLink
   * Return Self Root Node Path
   */
  public final static String TYPE_SELF_LINK = "SelfLink";
  
  /**
   * Attribute Type: SelfName
   * Return Self Root Node name
   */
  public final static String TYPE_SELF_NAME = "SelfName";

  /**
   * Attribute Type: String
   */
  public final static String TYPE_STRING = "String";

  /**
   * Attribute Type: Boolean
   */
  public final static String TYPE_BOOLEAN = "Boolean";

  /**
   * Attribute Type: Integer
   */
  public final static String TYPE_INTEGER = "Integer";

  /**
   * Attribute Type: Double
   */
  public final static String TYPE_DOUBLE = "Double";

  /**
   * Attribute Type: Binary
   */
  public final static String TYPE_BINARY = "Binary";

  /**
   * Attribute Type: Password
   */
  public final static String TYPE_PASSWORD = "Password";

  /**
   * Attribute Type: Email
   */
  public final static String TYPE_EMAIL = "Email";

  /**
   * Return the ID
   * @return
   */
  public abstract long getID();

  /**
   * Return the name of this type.
   * @return
   */
  public abstract String getName();

  /**
   * Set a name for this type.
   * @param name
   */
  public abstract void setName(String name);

  /**
   * Return the description.
   * @return
   */
  public abstract String getDescription();

  /**
   * Set a description for this type.
   * @param description
   */
  public abstract void setDescription(String description);


  /**
   * Return a set of values for this type.
   * @return  Return a <code>Set</code> of {@link com.npower.core.AttributeTypeValue} objects.
   */
  public abstract Set<AttributeTypeValue> getAttribTypeValues();

  /**
   * Getter of changeVersion
   * @return
   */
  public abstract long getChangeVersion();

  /**
   * Return a set of ProfileAttribute which reference this ProfileAttributeType.
   * @return  Return a <code>Set</code> of {@link com.npower.dm.core.ProfileAttribute} objects. 
   */
  //public abstract Set getProfileAttributes();

  /**
   * Getter LastUpdatedBy.
   * @return
   */
  public abstract String getLastUpdatedBy();

  /**
   * Setter LastUpdatedBy
   * @param lastUpdatedBy
   */
  public abstract void setLastUpdatedBy(String lastUpdatedBy);

  /**
   * Get LastUpdatedTime
   * @return
   */
  public abstract Date getLastUpdatedTime();
  
}