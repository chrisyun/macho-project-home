/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/audit/SoftwareAuditLogger.java,v 1.1 2009/02/19 09:26:24 chenlei Exp $
 * $Revision: 1.1 $
 * $Date: 2009/02/19 09:26:24 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2009 NPower Network Software Ltd.  All rights reserved.
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

package com.npower.dm.audit;

import com.npower.dm.audit.action.SoftwareAction;

/**
 * @author chenlei
 * @version $Revision: 1.1 $ $Date: 2009/02/19 09:26:24 $
 */

public interface SoftwareAuditLogger extends AuditLogger {
  
  /**
   * @param action
   * @param username
   * @param ipAddress
   * @param softwareName
   * @param softwareVendor
   * @param softwareCategory
   * @param description
   * @return
   * @throws AuditException
   */
  public abstract long log(SoftwareAction action,
                           String username,
                           String ipAddress,
                           String softwareName,
                           String softwareVendor,
                           String softwareCategory,
                           String description) throws AuditException;
  
}
