/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/audit/hibernate/SoftwareAuditLoggerDAO.java,v 1.1 2009/02/19 09:26:24 chenlei Exp $
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

package com.npower.dm.audit.hibernate;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.audit.AuditException;
import com.npower.dm.audit.SoftwareAuditLogger;
import com.npower.dm.audit.action.SoftwareAction;
import com.npower.dm.hibernate.entity.AuditLogAction;
import com.npower.dm.hibernate.entity.AuditLogEntity;
import com.npower.dm.hibernate.entity.AuditLogTargetEntity;
import com.npower.dm.hibernate.management.AuditLoggerFactoryImpl;

/**
 * @author chenlei
 * @version $Revision: 1.1 $ $Date: 2009/02/19 09:26:24 $
 */

public class SoftwareAuditLoggerDAO extends BaseAuditLogger implements SoftwareAuditLogger {

  /**
   * 
   */
  public SoftwareAuditLoggerDAO() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.SoftwareAuditLogger#log(com.npower.dm.audit.action.SoftwareAction, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  public long log(SoftwareAction action, String username, String ipAddress, String softwareName, String softwareVendor,
      String softwareCategory, String description) throws AuditException {
    if (!this.isEnable()) {
      return 0;
    }
    if (action == null) {
      throw new AuditException("Must specified security audit action.");
    }
    if (StringUtils.isEmpty(username)) {
      throw new AuditException("Must specified username.");
    }
    if (softwareName == null) {
      throw new AuditException("Must specified softwareName.");
    }
    AuditLoggerFactoryImpl factoryImpl = (AuditLoggerFactoryImpl)this.getFactory();
    
    AuditLogEntity log = new AuditLogEntity();
    AuditLogAction logAction = factoryImpl.getLogAction(action.getValue());
    log.setAction(logAction);
    log.setCreationDate(new Date());
    log.setUserName(username);
    log.setIpAddress(ipAddress);
    log.setDescription(description);

    AuditLogTargetEntity target = new AuditLogTargetEntity();
    target.setSoftwareName(softwareName);
    target.setSoftwareVendor(softwareVendor);
    target.setSoftwareCategory(softwareCategory);
    
    return this.save(log, target);

  }

}
