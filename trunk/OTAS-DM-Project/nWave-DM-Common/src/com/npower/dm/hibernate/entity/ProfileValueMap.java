/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ProfileValueMap.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2006/04/25 16:31:09 $
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

import com.npower.dm.core.ProfileAttributeValue;
import com.npower.dm.core.ProfileConfig;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public class ProfileValueMap extends AbstractProfileValueMap implements java.io.Serializable, Comparable {

  // Constructors

  /** default constructor */
  public ProfileValueMap() {
  }

  /** minimal constructor */
  public ProfileValueMap(ProfileValueMapID id, ProfileAttributeValue nwDmProfileAttribValue,
      ProfileConfig nwDmProfileConfig) {
    super(id, nwDmProfileAttribValue, nwDmProfileConfig);
  }

  /** full constructor */
  public ProfileValueMap(ProfileValueMapID id, ProfileAttributeValue nwDmProfileAttribValue,
      ProfileConfig nwDmProfileConfig, long valueIndex) {
    super(id, nwDmProfileAttribValue, nwDmProfileConfig, valueIndex);
  }

  /**
   * Comparable interface method
   */
  public int compareTo(Object o) {
    if (o instanceof ProfileValueMap) {
      long index = ((ProfileValueMap) o).getValueIndex();
      return (int) (this.getValueIndex() - index);
    } else {
      return 1;
    }
  }

}
