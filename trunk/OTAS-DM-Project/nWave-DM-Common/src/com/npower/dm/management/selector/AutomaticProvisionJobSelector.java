/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/selector/AutomaticProvisionJobSelector.java,v 1.1 2007/02/02 03:30:25 zhao Exp $
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

import sync4j.framework.core.dm.ddf.DevInfo;

import com.npower.dm.core.AutomaticProvisionJob;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/02 03:30:25 $
 */
public interface AutomaticProvisionJobSelector {
  
  /**
   * Return name of selector.
   * @return
   */
  public String getName();
  
  /**
   * Set name of selector.
   * @param name
   */
  public void setName(String name);
  
  /**
   * Return description of selector.
   * @return
   */
  public String getDescription();

  /**
   * Set description of selector.
   * @param description
   */
  public void setDescription(String description);

  /**
   * Test the device matching the automatic job.
   * @param job
   *        Automatic Job
   * @param device
   *        Device
   * @return
   *        true   matched!
   * @throws DMException
   */
  public boolean matched(AutomaticProvisionJob job, Device device) throws DMException;
  
  /**
   * Test the device matching the automatic job.
   * @param job
   *        Automatic Job
   * @param devInfo
   *        Device information
   * @return
   *        true   matched!
   * @throws DMException
   */
  public boolean matched(AutomaticProvisionJob job, DevInfo devInfo) throws DMException;
  
}
