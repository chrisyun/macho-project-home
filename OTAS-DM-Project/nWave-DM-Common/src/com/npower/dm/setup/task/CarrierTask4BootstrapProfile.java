/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/CarrierTask4BootstrapProfile.java,v 1.1 2008/09/05 03:24:40 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/09/05 03:24:40 $
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
package com.npower.dm.setup.task;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileConfigBean;
import com.npower.setup.core.SetupException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/05 03:24:40 $
 */
public class CarrierTask4BootstrapProfile extends AbstractCarrierTask {

  /**
   * 
   */
  public CarrierTask4BootstrapProfile() {
    super();
  }

  /**
   * @param factory
   * @param carrierBean
   * @param item
   * @throws SetupException
   */
  protected void processCarrierItem(ManagementBeanFactory factory, CarrierItem item)
      throws SetupException {
    CarrierBean carrierBean = factory.createCarrierBean();
    ProfileConfigBean profileBean = factory.createProfileConfigBean();
    try {
      factory.beginTransaction();
      Carrier carrier = carrierBean.getCarrierByExternalID(item.getExternalID());
      if (carrier == null) {
         throw new SetupException("Could not found carrier by extID: " + item.getExternalID());
      }
      
      // Set DM Bootstrap Profile
      String dmProfileExternalID = item.getBootstrapDmProfile();
      if (StringUtils.isNotEmpty(dmProfileExternalID)) {
         // Display information
         this.getSetup().getConsole().println("\t\t[" + carrier.getExternalID() + "] setting up DM Bootstrap with [" + dmProfileExternalID + "]");                  
         ProfileConfig profile = profileBean.getProfileConfigByExternalID(dmProfileExternalID);
         if (profile != null) {
            carrier.setBootstrapDmProfile(profile);
         } else {
           throw new SetupException("Could not found DM Bootstrap Profile by extID: " + dmProfileExternalID);
         }
      }
      
      // Set NAP Bootstrap Profile
      String napProfileExternalID = item.getBootstrapNapProfile();
      if (StringUtils.isNotEmpty(napProfileExternalID)) {
         // Display information
         this.getSetup().getConsole().println("\t\t[" + carrier.getExternalID() + "] setting up NAP Bootstrap with [" + napProfileExternalID + "]");                  
         ProfileConfig profile = profileBean.getProfileConfigByExternalID(napProfileExternalID);
         if (profile != null) {
            carrier.setBootstrapNapProfile(profile);
         } else {
           throw new SetupException("Could not found NAP Bootstrap Profile by extID: " + dmProfileExternalID);
         }
      }
      
      carrierBean.update(carrier);
      factory.commit();
    } catch (DMException e) {
      if (factory != null) {
        factory.rollback();
      }
      throw new SetupException("Error in Task carriers.", e);
    } catch (Exception e) {
      if (factory != null) {
        factory.rollback();
      }
      throw new SetupException("Error in Task carriers.", e);
    } finally {
    }
  }

}
