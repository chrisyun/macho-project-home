/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/AuditLoggerFactoryImpl.java,v 1.7 2009/02/23 09:47:28 hcp Exp $
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
package com.npower.dm.hibernate.management;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.npower.dm.audit.AuditException;
import com.npower.dm.audit.AuditLoggerFactory;
import com.npower.dm.audit.CarrierAuditLogger;
import com.npower.dm.audit.ClientProvTemplateAuditLogger;
import com.npower.dm.audit.CountryAuditLogger;
import com.npower.dm.audit.DDFAuditLogger;
import com.npower.dm.audit.DeviceAuditLogger;
import com.npower.dm.audit.ManufacturerAuditLogger;
import com.npower.dm.audit.ModelAuditLogger;
import com.npower.dm.audit.ProfileAssignmentAuditLogger;
import com.npower.dm.audit.ProfileAuditLogger;
import com.npower.dm.audit.ProfileMappingAuditLogger;
import com.npower.dm.audit.ProfileTemplateAuditLogger;
import com.npower.dm.audit.ProvisioningAuditLogger;
import com.npower.dm.audit.SecurityAuditLogger;
import com.npower.dm.audit.SoftwareAuditLogger;
import com.npower.dm.audit.SoftwareCategoryAuditLogger;
import com.npower.dm.audit.UpdateAuditLogger;
import com.npower.dm.audit.UserAuditLogger;
import com.npower.dm.audit.VendorAuditLogger;
import com.npower.dm.audit.hibernate.AuditLogActionDAO;
import com.npower.dm.audit.hibernate.CarrierAuditLoggerDAO;
import com.npower.dm.audit.hibernate.ClientProvTemplateAuditLoggerDAO;
import com.npower.dm.audit.hibernate.CountryAuditLoggerDAO;
import com.npower.dm.audit.hibernate.DDFAuditLoggerDAO;
import com.npower.dm.audit.hibernate.DeviceAuditLoggerDAO;
import com.npower.dm.audit.hibernate.ManufacturerAuditLoggerDAO;
import com.npower.dm.audit.hibernate.ModelAuditLoggerDAO;
import com.npower.dm.audit.hibernate.ProfileAssignmentAuditLoggerDAO;
import com.npower.dm.audit.hibernate.ProfileAuditLoggerDAO;
import com.npower.dm.audit.hibernate.ProfileMappingAuditLoggerDAO;
import com.npower.dm.audit.hibernate.ProfileTemplateAuditLoggerDAO;
import com.npower.dm.audit.hibernate.ProvisioningAuditLoggerDAO;
import com.npower.dm.audit.hibernate.SecurityAuditLoggerDAO;
import com.npower.dm.audit.hibernate.SoftwareAuditLoggerDAO;
import com.npower.dm.audit.hibernate.SoftwareCategoryAuditLoggerDAO;
import com.npower.dm.audit.hibernate.UpdateAuditLoggerDAO;
import com.npower.dm.audit.hibernate.UserAuditLoggerDAO;
import com.npower.dm.audit.hibernate.VendorAuditLoggerDAO;
import com.npower.dm.hibernate.entity.AuditLogAction;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2009/02/23 09:47:28 $
 */
public class AuditLoggerFactoryImpl extends AuditLoggerFactory {

  private static final Log   log         = LogFactory.getLog(AuditLoggerFactoryImpl.class);
  /**
   * 
   */
  public AuditLoggerFactoryImpl() {
    super();
  }
  
  /**
   * Return a Hibernate session
   * @return
   * @throws AuditException
   */
  public Session getHibernateSession() throws AuditException {
    if (log.isDebugEnabled()) {
       log.debug("Initializing ManagementBeanFactory, and initializing HibernateSessionFactory");
    }
    HibernateSessionFactory sessionFactory = HibernateSessionFactory.newInstance();
    Session session = sessionFactory.currentSession();
    if (log.isDebugEnabled()) {
       log.debug("Initializing ManagementBeanFactory, the current Hibernate session boundled with factory is " + session);
    }
    return session;
  }
  
  /**
   * Release a Hibernate Session.
   * @param session
   * @throws AuditException
   */
  public void releaseSession(Session session) throws AuditException {
    if (log.isDebugEnabled()) {
      log.debug("Release ManagementBeanFactoryImpl ...");
    }
    if (session != null && session.isOpen()) {
       if (log.isDebugEnabled()) {
          log.debug("Close HibernateSession ...");
       }
       session.close();
    } else {
      log.warn("Could not release the ManagementBeanFactory, error in state of the current HibernateSession boundled with the ManagementBeanFactory");
      //throw new RuntimeException("Could not release the ManagementBeanFactory, error in state of the current HibernateSession boundled with the ManagementBeanFactory");
    }
  }
  
  /**
   * Retrieve AuditLogAction by name.
   * @param actionName
   *        action name.
   * @return
   * @throws AuditException
   */
  public AuditLogAction getLogAction(String actionName) throws AuditException {
    
    Session hsession = null;
    try {
        hsession = this.getHibernateSession();
        AuditLogActionDAO actionDAO = new AuditLogActionDAO(hsession);
        return actionDAO.findById(actionName);
    } catch (Exception ex) {
      throw new AuditException("Failure in loading Audit Action.", ex);
    } finally {
      if (hsession != null) {
         hsession.close();
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLoggerFactory#getDeviceLog()
   */
  @Override
  public DeviceAuditLogger getDeviceLog() throws AuditException {
    DeviceAuditLogger logger = new DeviceAuditLoggerDAO();
    logger.setFactory(this);
    logger.setEnable(this.isEnable());
    return logger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLoggerFactory#getSecurityLog()
   */
  @Override
  public SecurityAuditLogger getSecurityLog() throws AuditException {
    SecurityAuditLogger logger = new SecurityAuditLoggerDAO();
    logger.setFactory(this);
    logger.setEnable(this.isEnable());
    return logger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLoggerFactory#getCountryLog()
   */
  @Override
  public CountryAuditLogger getCountryLog() throws AuditException {
    CountryAuditLogger logger = new CountryAuditLoggerDAO();
    logger.setFactory(this);
    logger.setEnable(this.isEnable());
    return logger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLoggerFactory#getCarrierLog()
   */
  @Override
  public CarrierAuditLogger getCarrierLog() throws AuditException {
    CarrierAuditLogger logger = new CarrierAuditLoggerDAO();
    logger.setFactory(this);
    logger.setEnable(this.isEnable());
    return logger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLoggerFactory#getManufacturerLog()
   */
  @Override
  public ManufacturerAuditLogger getManufacturerLog() throws AuditException {
    ManufacturerAuditLogger logger = new ManufacturerAuditLoggerDAO();
    logger.setFactory(this);
    logger.setEnable(this.isEnable());
    return logger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLoggerFactory#getModelLog()
   */
  @Override
  public ModelAuditLogger getModelLog() throws AuditException {
    ModelAuditLogger logger = new ModelAuditLoggerDAO();
    logger.setFactory(this);
    logger.setEnable(this.isEnable());
    return logger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLoggerFactory#getDDFLog()
   */
  @Override
  public DDFAuditLogger getDDFLog() throws AuditException {
    DDFAuditLogger logger = new DDFAuditLoggerDAO();
    logger.setFactory(this);
    logger.setEnable(this.isEnable());
    return logger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLoggerFactory#getProfileAssignmentLog()
   */
  @Override
  public ProfileAssignmentAuditLogger getProfileAssignmentLog() throws AuditException {
    ProfileAssignmentAuditLogger logger = new ProfileAssignmentAuditLoggerDAO();
    logger.setFactory(this);
    logger.setEnable(this.isEnable());
    return logger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLoggerFactory#getProfileLog()
   */
  @Override
  public ProfileAuditLogger getProfileLog() throws AuditException {
    ProfileAuditLogger logger = new ProfileAuditLoggerDAO();
    logger.setFactory(this);
    logger.setEnable(this.isEnable());
    return logger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLoggerFactory#getProfileMappingLog()
   */
  @Override
  public ProfileMappingAuditLogger getProfileMappingLog() throws AuditException {
    ProfileMappingAuditLogger logger = new ProfileMappingAuditLoggerDAO();
    logger.setFactory(this);
    logger.setEnable(this.isEnable());
    return logger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLoggerFactory#getProfileTemplateLog()
   */
  @Override
  public ProfileTemplateAuditLogger getProfileTemplateLog() throws AuditException {
    ProfileTemplateAuditLogger logger = new ProfileTemplateAuditLoggerDAO();
    logger.setFactory(this);
    logger.setEnable(this.isEnable());
    return logger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLoggerFactory#getProvisioningLog()
   */
  @Override
  public ProvisioningAuditLogger getProvisioningLog() throws AuditException {
    ProvisioningAuditLogger logger = new ProvisioningAuditLoggerDAO();
    logger.setFactory(this);
    logger.setEnable(this.isEnable());
    return logger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLoggerFactory#getUpdateLog()
   */
  @Override
  public UpdateAuditLogger getUpdateLog() throws AuditException {
    UpdateAuditLogger logger = new UpdateAuditLoggerDAO();
    logger.setFactory(this);
    logger.setEnable(this.isEnable());
    return logger;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.audit.AuditLoggerFactory#getUserLog()
   */
  @Override
  public UserAuditLogger getUserLog() throws AuditException {
    UserAuditLogger logger = new UserAuditLoggerDAO();
    logger.setFactory(this);
    logger.setEnable(this.isEnable());
    return logger;
  }

  @Override
  public ClientProvTemplateAuditLogger getClientProvTemplateAuditLog() throws AuditException {
    ClientProvTemplateAuditLogger logger = new ClientProvTemplateAuditLoggerDAO();
    logger.setFactory(this);
    logger.setEnable(this.isEnable());
    return logger;
  }

    @Override
  public SoftwareAuditLogger getSoftwareLog() throws AuditException {
    SoftwareAuditLogger logger = new SoftwareAuditLoggerDAO();
    logger.setFactory(this);
    logger.setEnable(this.isEnable());
    return logger;
  }

    @Override
    public SoftwareCategoryAuditLogger getSoftwareCategoryLog() throws AuditException {
      SoftwareCategoryAuditLogger logger = new SoftwareCategoryAuditLoggerDAO();
      logger.setFactory(this);
      logger.setEnable(this.isEnable());
      return logger;
    }

    @Override
    public VendorAuditLogger getVendorLog() throws AuditException {
      VendorAuditLogger logger = new VendorAuditLoggerDAO();
      logger.setFactory(this);
      logger.setEnable(this.isEnable());
      return logger;
    }

}
