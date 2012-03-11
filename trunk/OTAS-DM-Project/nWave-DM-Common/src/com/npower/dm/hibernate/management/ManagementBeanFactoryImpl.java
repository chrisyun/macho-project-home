/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/ManagementBeanFactoryImpl.java,v 1.32 2008/09/17 09:25:16 zhao Exp $
 * $Revision: 1.32 $
 * $Date: 2008/09/17 09:25:16 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
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

import java.util.Properties;

import javax.security.auth.Subject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.npower.cp.OTAInventory;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ManagementBeanFactoryAware;
import com.npower.dm.hibernate.HibernateSessionAware;
import com.npower.dm.hibernate.interceptor.EntityAwareInterceptor;
import com.npower.dm.management.AutomaticProvisionJobBean;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.ClientProvAuditLogBean;
import com.npower.dm.management.ClientProvTemplateBean;
import com.npower.dm.management.CountryBean;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.DeviceLogBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ModelClassificationBean;
import com.npower.dm.management.OTAClientProvJobBean;
import com.npower.dm.management.ProfileAssignmentBean;
import com.npower.dm.management.ProfileConfigBean;
import com.npower.dm.management.ProfileMappingBean;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SearchBean;
import com.npower.dm.management.ServiceProviderBean;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.management.SoftwareEvaluateBean;
import com.npower.dm.management.SoftwareTopListBean;
import com.npower.dm.management.SoftwareTrackingBean;
import com.npower.dm.management.SubscriberBean;
import com.npower.dm.management.UpdateImageBean;
import com.npower.sms.client.SmsSender;

/**
 * Implements the ManagementBeanFactory based on the Hibernate
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.32 $ $Date: 2008/09/17 09:25:16 $
 */
public final class ManagementBeanFactoryImpl extends ManagementBeanFactory {

  private static Log  log         = LogFactory.getLog(ManagementBeanFactoryImpl.class);

  /**
   * Trnasaction Handler boundled with the factory instance.
   */
  private Transaction transaction = null;

  /**
   * Hibernate Session handler boudled with the factory instance.
   */
  private Session     session     = null;
  
  /**
   * Security subject boundled with the factory instance.
   */
  private Subject subject = null;
  
  /**
   * If true, will trace transaction
   */
  private boolean traceTransaction = false;
  
  /**
   * Counstructor for the ManagementBeanFactory
   * This constructor will create an instance of HibernateSessionFactory.
   * The initialize process will load hibernate properties by the following steps:
   * 1. Load from ${otas.dm.home}/config/otasdm/otasdm.properties"
   * 2. If not found, load hibernate.properties file from classpath
   * 3. If not found, will use System.getProperties()
   */
  public ManagementBeanFactoryImpl() throws DMException {
    super();
    initilize(null);
  }

  /**
   * Counstructor for the ManagementBeanFactory
   * This constructor will create an instance of HibernateSessionFactory using properties as Hibernate Properties.
   */
  public ManagementBeanFactoryImpl(Properties properties) throws DMException {
    super();
    initilize(properties);
  }

  /**
   * 
   */
  private void initilize(Properties properties) {
    if (log.isDebugEnabled()) {
       log.debug("Initializing ManagementBeanFactory, and initializing HibernateSessionFactory");
    }
    HibernateSessionFactory sessionFactory = null;
    if (properties == null) {
       sessionFactory = HibernateSessionFactory.newInstance();
    } else {
      sessionFactory = HibernateSessionFactory.newInstance(properties);
    }
    EntityAwareInterceptor interceptor = new EntityAwareInterceptor();
    this.session = sessionFactory.currentSession(interceptor);
    interceptor.setManagementBeanFactory(this);
    
    if (log.isDebugEnabled()) {
       log.debug("Initializing ManagementBeanFactory, the current Hibernate session boundled with factory is " + this.session);
    }
    
  }

  // private methods.
  // *****************************************************************

  /**
   * Package-friendly method. Getter of HibernateSession related with the
   * factory instance.
   * 
   * @return org.hibernate.Session return the session related with the Factory.
   */
  private synchronized Session getHibernateSession() {
    return this.session;
  }

  // public methods
  // ****************************************************************************
  /**
   * Implements Transaction Management based on the Hibernate.
   * 
   * @see com.npower.dm.management.ManagementBeanFactory#beginTransaction()
   */
  public synchronized void beginTransaction() {
    if (this.transaction == null) {
      this.transaction = this.getHibernateSession().beginTransaction();
      if (log.isDebugEnabled()) {
         log.debug("Begin transaction ...");
      }
    } else {
      // Transaction already open
      if (this.transaction.isActive()) {
         this.transaction.commit();
         if (log.isDebugEnabled()) {
           log.debug("Transaction is actived, auto committed.");
        }
      }
      this.transaction = this.getHibernateSession().beginTransaction();
      if (log.isDebugEnabled()) {
         log.debug("Begin transaction ...");
      }
      if (traceTransaction) {
         log.error("Another transaction has not been commited or rollbacked, could not begin a new Transaction.");
         throw new RuntimeException("Another transaction has not been commited or rollbacked, could not begin a new Transaction.");
      }
    }
  }

  /**
   * Implements commit() method based on the Hibernate.
   * 
   * @see com.npower.dm.management.ManagementBeanFactory#commit()
   */
  public synchronized void commit() {
    if (this.transaction != null) {
      if (log.isDebugEnabled()) {
        log.debug("Commit transaction ...");
      }
      this.transaction.commit();
      this.transaction = null;
    } else {
      log.warn("The current transaction has not been created, could not commit the Transaction.");
      //throw new RuntimeException("The current transaction has not been created, could not commit the Transaction.");
    }
  }

  /**
   * Implements releaseResource() methods. Release the session of hibernate.
   */
  public synchronized void releaseResource() {
    if (log.isDebugEnabled()) {
      log.debug("Release ManagementBeanFactoryImpl ...");
    }
    if (this.session != null && this.session.isOpen()) {
       if (log.isDebugEnabled()) {
          log.debug("Close HibernateSession ...");
       }
       this.session.close();
    } else {
      log.warn("Could not release the ManagementBeanFactory, error in state of the current HibernateSession boundled with the ManagementBeanFactory");
      //throw new RuntimeException("Could not release the ManagementBeanFactory, error in state of the current HibernateSession boundled with the ManagementBeanFactory");
    }
  }

  /**
   * implements rollback() methods based on Hibernate.
   * 
   * @see com.npower.dm.management.ManagementBeanFactory#rollback()
   */
  public synchronized void rollback() {
    if (this.transaction != null) {
       if (log.isDebugEnabled()) {
          log.debug("Rollback transaction ...");
       }
       this.transaction.rollback();
       this.transaction = null;
    } else {
      log.warn("The current transaction has not been created, could not rollback the Transaction.");
      //throw new RuntimeException("The current transaction has not been created, could not rollback the Transaction.");
    }
  }

  /**
   * @return the subject
   */
  public Subject getSubject() {
    return subject;
  }

  /**
   * @param subject the subject to set
   */
  public void setSubject(Subject subject) {
    this.subject = subject;
  }
  
  public Object createBean(Class<?> clazz) throws InstantiationException, IllegalAccessException {
    Object o = clazz.newInstance();
    if (o instanceof AbstractBean) {
       ((AbstractBean)o).setManagementBeanFactory(this);
       ((AbstractBean)o).setHibernateSession(this.getHibernateSession());
    }
    if (o instanceof HibernateSessionAware) {
       ((HibernateSessionAware)o).setHibernateSession(this.getHibernateSession());
    }
    if (o instanceof ManagementBeanFactoryAware) {
       ((ManagementBeanFactoryAware)o).setManagementBeanFactory(this);
    }
    return o;
  }

  /**
   * return a CarrierBean instance.
   * 
   * @see com.npower.dm.management.ManagementBeanFactory#createCarrierBean()
   */
  public CarrierBean createCarrierBean() {
    return new CarrierManagementBeanImpl(this, this.getHibernateSession());
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ManagementBeanFactory#createServiceProviderBean()
   */
  public ServiceProviderBean createServiceProviderBean() {
    return new ServiceProviderBeanImpl(this, this.getHibernateSession());
  }

  /**
   * Return a CountryBean instance.
   * 
   * @see com.npower.dm.management.ManagementBeanFactory#createCountryBean()
   */
  public CountryBean createCountryBean() {
    return new CountryManagementBeanImpl(this, this.getHibernateSession());
  }

  /**
   * Implements the createXXX() method.
   * 
   * @return DDFTreeBean
   */
  public DDFTreeBean createDDFTreeBean() {
    return new DDFTreeManagementBeanImpl(this, this.getHibernateSession());
  }

  /**
   * Create a ModelBean
   * 
   * @return
   */
  public ModelBean createModelBean() {
    try {
        return new ModelManagementBeanImpl(this, this.getHibernateSession());
    } catch (Exception e) {
      throw new RuntimeException("Failure create ModelBean", e);
    }
  }

  /**
   * Create a ProfileMappingBean
   * 
   * @return
   */
  public ProfileMappingBean createProfileMappingBean() {
    return new ProfileMappingBeanImpl(this, this.getHibernateSession());
  }

  /**
   * Create a ProfileTemplateBean
   * 
   * @return
   */
  public ProfileTemplateBean createProfileTemplateBean() {
    return new ProfileTemplateManagementBeanImpl(this, this.getHibernateSession());
  }

  /**
   * Return a ProfileConfigBean
   * 
   * @return
   */
  public ProfileConfigBean createProfileConfigBean() {
    return new ProfileConfigManagementBeanImp(this, this.getHibernateSession());
  }

  /**
   * Return a SubscriberBean
   * 
   * @return
   */
  public SubscriberBean createSubcriberBean() {
    return new SubscriberManagementBeanImpl(this, this.getHibernateSession());
  }

  /**
   * Create a DeviceBean
   * 
   * @return
   */
  public DeviceBean createDeviceBean() {
    return new DeviceManagementBeanImpl(this, this.getHibernateSession());
  }

  /**
   * Create a ProfileAssignmentBean.
   * 
   * @return
   */
  public ProfileAssignmentBean createProfileAssignmentBean() {
    return new ProfileAssignmentManagementBeanImpl(this, this.getHibernateSession());
  }

  /**
   * Create a UpdateImageBean.
   * 
   * @return
   */
  public UpdateImageBean createUpdateImageBean() {
    return new UpdateImageManagementBeanImpl(this, this.getHibernateSession());
  }

  public DeviceLogBean createDeviceLogBean() {
    return new DeviceLogManagementBeanImpl(this, this.getHibernateSession());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.ManagementBeanFactory#createProvisionJobBean()
   */
  @Override
  public ProvisionJobBean createProvisionJobBean() {
    return new ProvisionJobManagementBeanImpl(this, this.getHibernateSession());
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ManagementBeanFactory#createAutoProvisionJobBean()
   */
  @Override
  public AutomaticProvisionJobBean createAutoProvisionJobBean() {
    return new AutomaticProvisionJobBeanImpl(this, this.getHibernateSession());
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ManagementBeanFactory#createSearchBean(java.lang.Class)
   */
  @Override
  public SearchBean createSearchBean() {
    return new SearchManagementBeanImpl(this, this.getHibernateSession());
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ManagementBeanFactory#createClientProvTemplateBean()
   */
  @Override
  public ClientProvTemplateBean createClientProvTemplateBean() {
    return new ClientProvTemplateBeanImpl(this, this.getHibernateSession());
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ManagementBeanFactory#createOTAClientProvJobBean(com.npower.cp.OTAInventory, com.npower.sms.client.SmsSender)
   */
  @Override
  public OTAClientProvJobBean createOTAClientProvJobBean(OTAInventory otaInventory, SmsSender sender) {
    return new OTAClientProvJobBeanImpl(this, this.getHibernateSession(), otaInventory, sender);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ManagementBeanFactory#isOpen()
   */
  @Override
  public boolean isOpen() throws DMException {
    if (this.session == null) {
       return false;
    } else {
      return this.session.isOpen();
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ManagementBeanFactory#createClientProvAuditLogBean()
   */
  @Override
  public ClientProvAuditLogBean createClientProvAuditLogBean() {    
    return new ClientProvAuditLogManagementBeanImpl(this, this.getHibernateSession());
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ManagementBeanFactory#createSoftwareBean()
   */
  @Override
  public SoftwareBean createSoftwareBean() {
    return new SoftwareBeanImpl(this, this.getHibernateSession());
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ManagementBeanFactory#createSoftwareTrackingBean()
   */
  @Override
  public SoftwareTrackingBean createSoftwareTrackingBean() {
    return new SoftwareTrackingBeanImpl(this, this.getHibernateSession());
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ManagementBeanFactory#createSoftwareTopListBean()
   */
  @Override
  public SoftwareTopListBean createSoftwareTopListBean() {
    return new SoftwareTopListBeanImpl(this, this.getHibernateSession());
  }

  @Override
  public SoftwareEvaluateBean createSoftwareEvaluateBean() {
    return new SoftwareEvaluateBeanImpl(this, this.getHibernateSession());
  }

  @Override
  public ModelClassificationBean createModelClassificationBean() {
    return new ModelClassificationBeanImpl(this, this.getHibernateSession());
  }

}
