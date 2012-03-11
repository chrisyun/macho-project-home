/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/audit/hibernate/VendorAuditLoggerDAO.java,v 1.1 2009/02/23 09:47:28 hcp Exp $
 * $Revision: 1.1 $
 * $Date: 2009/02/23 09:47:28 $
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
import com.npower.dm.audit.VendorAuditLogger;
import com.npower.dm.audit.action.VendorAction;
import com.npower.dm.hibernate.entity.AuditLogAction;
import com.npower.dm.hibernate.entity.AuditLogEntity;
import com.npower.dm.hibernate.entity.AuditLogTargetEntity;
import com.npower.dm.hibernate.management.AuditLoggerFactoryImpl;

/**
 * @author Huang ChunPing
 * @version $Revision: 1.1 $ ${date}3:02:05 AM$ com.npower.dm.audit.hibernate
 *          nWave-DM-Common VendorAuditLoggerDAO.java
 */
public class VendorAuditLoggerDAO extends BaseAuditLogger implements VendorAuditLogger {

  /**
   * 
   */
  public VendorAuditLoggerDAO() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.audit.VendorAuditLogger#log(com.npower.dm.audit.action.VendorAction,
   *      java.lang.String, java.lang.String, long, java.lang.String,
   *      java.lang.String)
   */
  public long log(VendorAction action, String username, String ipAddress, long vendorID, String vendorExtID,
      String description) throws AuditException {
    if (!this.isEnable()) {
      return 0;
    }
    if (action == null) {
      throw new AuditException("Must specified security audit action.");
    }
    if (StringUtils.isEmpty(username)) {
      throw new AuditException("Must specified username.");
    }
    if (vendorID == 0) {
      throw new AuditException("Must specified manufacturerID.");
    }
    AuditLoggerFactoryImpl factoryImpl = (AuditLoggerFactoryImpl) this.getFactory();

    AuditLogEntity log = new AuditLogEntity();
    AuditLogAction logAction = factoryImpl.getLogAction(action.getValue());
    log.setAction(logAction);
    log.setCreationDate(new Date());
    log.setUserName(username);
    log.setIpAddress(ipAddress);
    log.setDescription(description);

    AuditLogTargetEntity target = new AuditLogTargetEntity();
    target.setVendorId("" + vendorID);
    target.setVendorExtId(vendorExtID);
    return this.save(log, target);
  }

}
