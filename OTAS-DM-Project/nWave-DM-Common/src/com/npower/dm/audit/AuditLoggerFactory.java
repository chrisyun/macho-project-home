/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/audit/AuditLoggerFactory.java,v 1.7 2009/02/23 09:47:28 hcp Exp $
  * $Revision: 1.7 $
  * $Date: 2009/02/23 09:47:28 $
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

import com.npower.dm.hibernate.management.AuditLoggerFactoryImpl;

/**
 * Audit logger factory.
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2009/02/23 09:47:28 $
 */
public abstract class AuditLoggerFactory {
  
  private boolean enable = true;

  /**
   * Private Default Constrctuor.
   */
  protected AuditLoggerFactory() {
    super();
  }
  
  /**
   * If true, will enable audit log.
   * @return the enable
   */
  public boolean isEnable() {
    return enable;
  }

  /**
   * If set true, will enable audit log, default is true.
   * If set false, will disable audit log.
   * @param enable the enable to set
   */
  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  /**
   * Return an instance of factory.
   * @return
   */
  public static AuditLoggerFactory newInstance() {
    AuditLoggerFactory factory = new AuditLoggerFactoryImpl();
    return factory;
  }
  
  /**
   * Return the logger for device audit.
   * @return
   * @throws AuditException
   */
  public abstract DeviceAuditLogger getDeviceLog() throws AuditException;
  
  /**
   * Return the logger of security audit.
   * @return
   * @throws AuditException
   */
  public abstract SecurityAuditLogger getSecurityLog() throws AuditException;
  
  /**
   * Return the logger of country audit.
   * @return
   * @throws AuditException
   */
  public abstract CountryAuditLogger getCountryLog() throws AuditException;

  /**
   * Return the logger of carrier audit.
   * @return
   * @throws AuditException
   */
  public abstract CarrierAuditLogger getCarrierLog() throws AuditException;

  /**
   * Return the logger of manufacturer audit.
   * @return
   * @throws AuditException
   */
  public abstract ManufacturerAuditLogger getManufacturerLog() throws AuditException;

  /**
   * Return the logger of model audit.
   * @return
   * @throws AuditException
   */
  public abstract ModelAuditLogger getModelLog() throws AuditException;
  
  /**
   * Return the logger of DDF audit.
   * @return
   * @throws AuditException
   */
  public abstract DDFAuditLogger getDDFLog() throws AuditException;
  
  /**
   * Return the logger of ProfileAssignment audit.
   * @return
   * @throws AuditException
   */
  public abstract ProfileAssignmentAuditLogger getProfileAssignmentLog() throws AuditException;
  
  /**
   * Return the logger of Profile audit.
   * @return
   * @throws AuditException
   */
  public abstract ProfileAuditLogger getProfileLog() throws AuditException;
  
  /**
   * Return the logger of ProfileMapping audit.
   * @return
   * @throws AuditException
   */
  public abstract ProfileMappingAuditLogger getProfileMappingLog() throws AuditException;
  
  /**
   * Return the logger of ProfileTemplate audit.
   * @return
   * @throws AuditException
   */
  public abstract ProfileTemplateAuditLogger getProfileTemplateLog() throws AuditException;
  
  /**
   * Return the logger of Provisioning audit.
   * @return
   * @throws AuditException
   */
  public abstract ProvisioningAuditLogger getProvisioningLog() throws AuditException;
  
  /**
   * Return the logger of Update audit.
   * @return
   * @throws AuditException
   */
  public abstract UpdateAuditLogger getUpdateLog() throws AuditException;
  
  /**
   * Return the logger of User audit.
   * @return
   * @throws AuditException
   */
  public abstract UserAuditLogger getUserLog() throws AuditException;
  
  /**
   * @return
   * @throws AuditException
   */
  public abstract ClientProvTemplateAuditLogger getClientProvTemplateAuditLog() throws AuditException; 

    
  /**
   * Return the logger of Software audit.
   * @return
   * @throws AuditException
   */
  public abstract SoftwareAuditLogger getSoftwareLog() throws AuditException;
  
  public abstract SoftwareCategoryAuditLogger getSoftwareCategoryLog() throws AuditException;

  /**
   * @return
   * @throws AuditException
   */
  public abstract VendorAuditLogger getVendorLog() throws AuditException;
   
}
