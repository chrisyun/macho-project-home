/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/CarrierTask.java,v 1.2 2008/12/03 10:26:34 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2008/12/03 10:26:34 $
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

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.setup.core.SetupException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/12/03 10:26:34 $
 */
public class CarrierTask extends AbstractCarrierTask {

  /**
   * 
   */
  public CarrierTask() {
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
    try {
      factory.beginTransaction();
      Carrier carrier = carrierBean.getCarrierByExternalID(item.getExternalID());
      if (carrier == null) {
         carrier = carrierBean.newCarrierInstance();
      }
      this.copy(factory, item, carrier);
      carrierBean.update(carrier);
      factory.commit();
      // Display information
      this.getSetup().getConsole().println("\t\t[" + carrier.getExternalID() + "] imported!");                  
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
