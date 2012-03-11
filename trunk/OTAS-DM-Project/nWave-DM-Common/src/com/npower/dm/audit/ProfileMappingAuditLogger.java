/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/audit/ProfileMappingAuditLogger.java,v 1.1 2007/02/08 10:49:06 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/02/08 10:49:06 $
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

import com.npower.dm.audit.action.ProfileMappingAction;

/**
 * Logger for profile mapping audit.
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/02/08 10:49:06 $
 */
public interface ProfileMappingAuditLogger extends AuditLogger {

  /**
   * @param action
   *        Action Type of model audit log
   * @param username
   *        The operator
   * @param ipAddress
   *        The IP address of operator
   * @param modelID
   *        ID of target model.
   * @param modeName
   *        Name of target model.
   * @param templateID
   *        ID of target Profile template.
   * @param templateName
   *        External ID of target Profile template.
   * @param description
   * @return
   * @throws AuditException
   */
  public abstract long log(ProfileMappingAction action, 
      String username, 
      String ipAddress,
      long modelID, 
      String modeName,
      long templateID,
      String templateName,
      String description) throws AuditException;
}
