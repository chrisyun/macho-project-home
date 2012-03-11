/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/SoftwareTrackingType.java,v 1.1 2008/08/14 08:08:45 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/08/14 08:08:45 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.management;

import java.io.Serializable;

import com.npower.dm.core.SoftwareRating;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/08/14 08:08:45 $
 */
public enum SoftwareTrackingType implements Serializable {
  VIEW(SoftwareRating.NAME_VIEW),
  DOWNLOAD(SoftwareRating.NAME_DOWNLOAD),
  INSTALLED(SoftwareRating.NAME_INSTALLED),
  ;
  
  private String value = null;
  
  /**
   * @param ordinal
   */
  private SoftwareTrackingType(String value) {
    this.value = value;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Enum#toString()
   */
  public String toString() {
    return this.value;
  }

}
