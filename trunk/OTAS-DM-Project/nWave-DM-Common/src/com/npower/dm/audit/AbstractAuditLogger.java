/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/audit/AbstractAuditLogger.java,v 1.2 2007/02/08 07:11:59 zhao Exp $
  * $Revision: 1.2 $
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

/**
 * Abstract Audit Logger.
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/02/08 07:11:59 $
 */
public abstract class AbstractAuditLogger implements AuditLogger {
  
  private AuditLoggerFactory factory = null;

  private boolean enable = true;
  
  /**
   * Default constructor.
   */
  protected AbstractAuditLogger() {
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

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLogger#isEnable()
   */
  public boolean isEnable() {
    return enable;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLogger#setEnable(boolean)
   */
  public void setEnable(boolean enable) {
    this.enable = enable;
  }

}
