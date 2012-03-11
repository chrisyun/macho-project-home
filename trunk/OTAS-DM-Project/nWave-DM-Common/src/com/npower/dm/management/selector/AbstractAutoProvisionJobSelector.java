/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/selector/AbstractAutoProvisionJobSelector.java,v 1.1 2007/02/02 03:30:25 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/02/02 03:30:25 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.management.selector;

import java.io.Serializable;

import sync4j.framework.core.dm.ddf.DevInfo;

import com.npower.dm.core.AutomaticProvisionJob;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/02 03:30:25 $
 */
public abstract class AbstractAutoProvisionJobSelector implements Serializable, AutomaticProvisionJobSelector {

  private String name = null;
  private String description = null;
  /**
   * 
   */
  public AbstractAutoProvisionJobSelector() {
    super();
  }
  
  // Public methods ----------------------------------------------------------------------------------

  // Implements AutomaticProvisionJobSelector interface ----------------------------------------------

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobSelector#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobSelector#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobSelector#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobSelector#getName()
   */
  public String getName() {
    return this.name;
  }
  
  // Abstract methods -------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobSelector#matched(com.npower.dm.core.AutomaticProvisionJob, com.npower.dm.core.Device)
   */
  public abstract boolean matched(AutomaticProvisionJob job, Device device) throws DMException;

  /* (non-Javadoc)
   * @see com.npower.dm.management.AutomaticProvisionJobSelector#matched(com.npower.dm.core.AutomaticProvisionJob, sync4j.framework.core.dm.ddf.DevInfo)
   */
  public abstract boolean matched(AutomaticProvisionJob job, DevInfo devInfo) throws DMException;

}
