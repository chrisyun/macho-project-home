/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/SoftwareTrackingBean.java,v 1.3 2008/08/15 09:01:12 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/08/15 09:01:12 $
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

import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwarePackage;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/08/15 09:01:12 $
 */
public interface SoftwareTrackingBean {

  /**
   * Tracking a event for Software Package.
   * @param pkg
   *        Software package
   * @param event
   *        Event
   */
  public void track(SoftwarePackage pkg, SoftwareTrackingEvent event);
  
  
  /**
   * Tracking a event for Software Package.
   * @param pkgID
   *        ID of softwre package
   * @param event 
   *        Event
   */
  public void trackPackage(long pkgID, SoftwareTrackingEvent event);
  
  /**
   * Tracking a event for Software.
   * @param software
   *        Software
   * @param event
   *        Event
   */
  public void track(Software software, SoftwareTrackingEvent event);
  
  
  /**
   * Tracking a event for Software.
   * @param pkgID
   *        ID of softwre
   * @param event 
   *        Event
   */
  public void trackSoftware(long softwareID, SoftwareTrackingEvent event);
  
}
