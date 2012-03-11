/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/audit/hibernate/DeviceAuditLoggerDAO.java,v 1.3 2007/02/08 07:11:59 zhao Exp $
  * $Revision: 1.3 $
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
package com.npower.dm.audit.hibernate;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.audit.AuditException;
import com.npower.dm.audit.DeviceAuditLogger;
import com.npower.dm.audit.action.DeviceAction;
import com.npower.dm.hibernate.entity.AuditLogAction;
import com.npower.dm.hibernate.entity.AuditLogEntity;
import com.npower.dm.hibernate.entity.AuditLogTargetEntity;
import com.npower.dm.hibernate.management.AuditLoggerFactoryImpl;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/02/08 07:11:59 $
 */
public class DeviceAuditLoggerDAO extends BaseAuditLogger implements DeviceAuditLogger {

  /**
   * Default constructor.
   */
  public DeviceAuditLoggerDAO() {
    super();
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.audit.DeviceAuditLogger#log(com.npower.dm.audit.action.DeviceAction, java.lang.String, java.lang.String, long, java.lang.String, java.lang.String)
   */
  public long log(DeviceAction action, String username, String ipAddress, long deviceID, String deviceExtID, String description) throws AuditException {
    if (!this.isEnable()) {
       return 0;
    }
    if (action == null) {
       throw new AuditException("Must specified security audit action.");
    }
    if (StringUtils.isEmpty(username)) {
       throw new AuditException("Must specified username.");
    }
    if (deviceID == 0) {
       throw new AuditException("Must specified deviceID.");
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
    target.setDeviceId("" + deviceID);
    target.setDeviceExternalId(deviceExtID);
    
    return this.save(log, target);
  }

}
