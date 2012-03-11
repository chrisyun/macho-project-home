/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/server/synclet/SimpleCarrierDetector.java,v 1.2 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2008/06/16 10:11:15 $
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
package com.npower.dm.server.synclet;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.core.SyncML;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DMException;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.server.engine.EngineConfig;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/06/16 10:11:15 $
 */
public class SimpleCarrierDetector implements Serializable, CarrierDetector {

  protected static Log log = LogFactory.getLog(SimpleCarrierDetector.class);
  
  /**
   * Carrier ExternalID
   */
  private String carrierExternalID = null;
  /**
   * 
   */
  public SimpleCarrierDetector() {
    super();
  }

  /**
   * @return the carrierExternalID
   */
  public String getCarrierExternalID() {
    return carrierExternalID;
  }

  /**
   * @param carrierExternalID the carrierExternalID to set
   */
  public void setCarrierExternalID(String carrierExternalID) {
    this.carrierExternalID = carrierExternalID;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.server.synclet.CarrierDetector#getCarrierID(java.lang.String, javax.servlet.http.HttpServletRequest, sync4j.framework.core.SyncML)
   */
  public String getCarrierID(String deviceExternalID, String phoneNumber, HttpServletRequest httpRequest, SyncML message) throws DMException {
    if (StringUtils.isEmpty(this.getCarrierExternalID())) {
      
    }
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID(this.getCarrierExternalID());
        if (carrier != null) {
           return "" + carrier.getID();
        } else {
          return null;
        }
    } catch (Exception ex) {
      throw new DMException("Failure in detect carriero.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

}
