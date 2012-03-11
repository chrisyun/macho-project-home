/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/audit/hibernate/BaseAuditLogger.java,v 1.2 2007/02/06 10:42:21 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/02/06 10:42:21 $
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

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.npower.dm.audit.AbstractAuditLogger;
import com.npower.dm.audit.AuditException;
import com.npower.dm.audit.AuditLoggerFactory;
import com.npower.dm.hibernate.entity.AuditLogEntity;
import com.npower.dm.hibernate.entity.AuditLogTargetEntity;
import com.npower.dm.hibernate.management.AuditLoggerFactoryImpl;

/**
 * Abstract Audit Logger.
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/02/06 10:42:21 $
 */
public abstract class BaseAuditLogger extends AbstractAuditLogger {
  
  private AuditLoggerFactory factory = null;

  /**
   * Default constructor.
   */
  protected BaseAuditLogger() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLogger#getFactory()
   */
  public AuditLoggerFactory getFactory() throws AuditException {
    return this.factory;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLogger#setFactory(com.npower.dm.audit.AuditLoggerFactory)
   */
  public void setFactory(AuditLoggerFactory factory) throws AuditException {
    this.factory= factory;
  }

  protected long save(AuditLogEntity log, AuditLogTargetEntity target) throws AuditException {
    AuditLoggerFactoryImpl factoryImpl = (AuditLoggerFactoryImpl)this.getFactory();
    
    Session hsession = null;
    Transaction tx = null;
    try {
        hsession = factoryImpl.getHibernateSession();
        tx = hsession.beginTransaction();
        
        hsession.saveOrUpdate(log);
        
        if (target != null) {
           target.setActionLog(log);
           hsession.saveOrUpdate(target);
        }
        tx.commit();
        
        return log.getID();
    } catch (Exception ex) {
      if (tx != null) {
         tx.rollback();
      }
      throw new AuditException("Failure in output log.", ex);
    } finally {
      if (hsession != null) {
         factoryImpl.releaseSession(hsession);
      }
    }
  }
  
  public AuditLogEntity getAuditLogRecord(long id) throws AuditException {
    AuditLoggerFactoryImpl factoryImpl = (AuditLoggerFactoryImpl)this.getFactory();
    
    Session hsession = null;
    try {
        hsession = factoryImpl.getHibernateSession();
         
        AuditLogEntity record = (AuditLogEntity)hsession.load(AuditLogEntity.class, new Long(id));
        
        return record;
    } catch (Exception ex) {
      throw new AuditException("Failure in loading log entity.", ex);
    } finally {
      if (hsession != null) {
         factoryImpl.releaseSession(hsession);
      }
    }
  }

}
