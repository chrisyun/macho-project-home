/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/server/synclet/CarrierDetectorChain.java,v 1.2 2007/05/16 07:17:57 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/05/16 07:17:57 $
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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.core.SyncML;

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/05/16 07:17:57 $
 */
public class CarrierDetectorChain implements CarrierDetector {
  
  protected static Log log = LogFactory.getLog(CarrierDetectorChain.class);
  
  private List<CarrierDetector> chain = new ArrayList<CarrierDetector>();

  /**
   * Default constructor.
   */
  public CarrierDetectorChain() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.server.synclet.CarrierDetector#getCarrierID(java.lang.String, javax.servlet.http.HttpServletRequest, sync4j.framework.core.SyncML)
   */
  public String getCarrierID(String deviceExternalID, String phoneNumber, HttpServletRequest httpRequest, SyncML message) throws DMException {
    
    for (CarrierDetector detector: this.getChain()) {
        String carrierID = detector.getCarrierID(deviceExternalID, phoneNumber, httpRequest, message);
        if (StringUtils.isNotEmpty(carrierID)) {
           return carrierID;
        }
    }
    return null;
  }

  /**
   * @return the chain
   */
  public List<CarrierDetector> getChain() {
    return chain;
  }

  /**
   * @param chain the chain to set
   */
  public void setChain(List<CarrierDetector> chain) {
    this.chain = chain;
  }

  /**
   * @param chain the chain to set
   */
  public void setChain(CarrierDetector[] detectors) {
    for (CarrierDetector detector: detectors) {
        this.chain.add(detector);
    }
  }

}
