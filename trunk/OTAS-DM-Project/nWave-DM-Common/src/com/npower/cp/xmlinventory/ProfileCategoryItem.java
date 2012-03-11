/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/cp/xmlinventory/ProfileCategoryItem.java,v 1.1 2007/05/24 08:48:48 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/05/24 08:48:48 $
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
package com.npower.cp.xmlinventory;

import java.util.Date;
import java.util.Set;

import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileTemplate;


/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/05/24 08:48:48 $
 */
public class ProfileCategoryItem implements ProfileCategory {

  private String name = null;
  private String description = null;
  
  /**
   * Default constructor
   */
  public ProfileCategoryItem() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTAProfileCategory#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTAProfileCategory#getName()
   */
  public String getName() {
    return this.name;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTAProfileCategory#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /* (non-Javadoc)
   * @see com.npower.cp.OTAProfileCategory#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileCategory#getChangeVersion()
   */
  public long getChangeVersion() {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileCategory#getID()
   */
  public long getID() {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileCategory#getLastUpdatedBy()
   */
  public String getLastUpdatedBy() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileCategory#getLastUpdatedTime()
   */
  public Date getLastUpdatedTime() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileCategory#getProfileTemplates()
   */
  public Set<ProfileTemplate> getProfileTemplates() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ProfileCategory#setLastUpdatedBy(java.lang.String)
   */
  public void setLastUpdatedBy(String lastUpdatedBy) {
    // TODO Auto-generated method stub
    
  }

}
