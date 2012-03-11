/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/server/synclet/PhoneNumberDetectorChain.java,v 1.1 2006/12/13 07:16:41 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2006/12/13 07:16:41 $
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

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2006/12/13 07:16:41 $
 */
public class PhoneNumberDetectorChain implements PhoneNumberDetector {
  
  protected static Log log = LogFactory.getLog(PhoneNumberDetectorChain.class);
  
  private List<PhoneNumberDetector> chain = new ArrayList<PhoneNumberDetector>();

  /**
   * Default constructor.
   */
  public PhoneNumberDetectorChain() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.server.synclet.PhoneNumberParser#getPhoneNumber(java.lang.String, javax.servlet.http.HttpServletRequest, sync4j.framework.core.SyncML)
   */
  public String getPhoneNumber(String deviceExternalID, HttpServletRequest httpRequest, SyncML message) {
    
    for (PhoneNumberDetector parser: this.getChain()) {
        String msisdn = parser.getPhoneNumber(deviceExternalID, httpRequest, message);
        if (StringUtils.isNotEmpty(msisdn)) {
           return msisdn;
        }
    }
    return null;
  }

  /**
   * @return the chain
   */
  public List<PhoneNumberDetector> getChain() {
    return chain;
  }

  /**
   * @param chain the chain to set
   */
  public void setChain(List<PhoneNumberDetector> chain) {
    this.chain = chain;
  }

  /**
   * @param chain the chain to set
   */
  public void setChain(PhoneNumberDetector[] parsers) {
    for (PhoneNumberDetector parser: parsers) {
        this.chain.add(parser);
    }
  }

}
