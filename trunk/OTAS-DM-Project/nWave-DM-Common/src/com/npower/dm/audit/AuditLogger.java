/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/audit/AuditLogger.java,v 1.3 2007/02/08 07:11:59 zhao Exp $
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
package com.npower.dm.audit;

import com.npower.dm.hibernate.entity.AuditLogEntity;

/**
 * Audit Logger
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/02/08 07:11:59 $
 */
public interface AuditLogger {

  /**
   * Return the factory of this logger.
   * @return
   * @throws AuditException
   */
  public abstract AuditLoggerFactory getFactory() throws AuditException;

  /**
   * Set the factory for the logger.
   * @param factory
   * @throws AuditException
   */
  public abstract void setFactory(AuditLoggerFactory factory) throws AuditException;
  
  /**
   * If true, will enable audit log.
   * @return the enable
   */
  public abstract boolean isEnable();

  /**
   * If set true, will enable audit log, default is true.
   * If set false, will disable audit log.
   * @param enable the enable to set
   */
  public abstract void setEnable(boolean enable);

  /**
   * Load a record.
   * @param id
   * @return
   * @throws AuditException
   */
  public abstract AuditLogEntity getAuditLogRecord(long id) throws AuditException;
  

}
