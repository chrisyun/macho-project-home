/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/audit/ModelAuditLogger.java,v 1.1 2007/02/08 07:11:59 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/02/08 07:11:59 $
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
package com.npower.dm.audit;

import com.npower.dm.audit.action.ModelAction;

/**
 * Logger for model audit.
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/08 07:11:59 $
 */
public interface ModelAuditLogger extends AuditLogger {

  /**
   * @param action
   *        Action Type of model audit log
   * @param username
   *        The operator
   * @param ipAddress
   *        The IP address of operator
   * @param modelID
   *        ID of target model.
   * @param modelExtID
   *        External ID of target model.
   * @param description
   *        Description
   * @return
   * @throws AuditException
   */
  public abstract long log(ModelAction action, 
      String username, 
      String ipAddress,
      long modelID, 
      String modelExtID,
      String description) throws AuditException;
}
