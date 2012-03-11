/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/ManagementBeanFactory.java,v 1.32 2008/09/04 06:33:50 zhao Exp $
 * $Revision: 1.32 $
 * $Date: 2008/09/04 06:33:50 $
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
package com.npower.dm.management;

import java.util.Properties;

import javax.security.auth.Subject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.cp.OTAInventory;
import com.npower.dm.core.DMException;
import com.npower.dm.hibernate.management.DeviceManagementBeanImpl;
import com.npower.dm.hibernate.management.ManagementBeanFactoryImpl;
import com.npower.sms.client.SmsSender;

/**
 * 
 * Factory of ManagementBean. All of instances of Management Bean will be
 * created by this factory.
 * 
 * Factory lifecycle must be manully handled. The lifecycle includes the
 * following states: 1. begineTransaction(); 2. commit() or rollback(); 3.
 * release()
 * 
 * more information, please sess the methods related with these states.
 * 
 * For Example:
 * 
 * ManagementBeanFactory factory = ManagementBeanFactory.newInstance();
 * CountryBean bean = factory.createCountryBean(); CountryEntity country = new
 * CountryEntity(....); factory.beginTransaction(); bean.add(country);
 * factory.commit(); factory.release();
 * 
 * 
 * 
 * @author Zhao DongLu
 * @param <T>
 * 
 */
public abstract class ManagementBeanFactory {

  private static Log   log                       = LogFactory.getLog(DeviceManagementBeanImpl.class);
  
  /**
   * Default construct
   */
  protected ManagementBeanFactory() {
    super();
  }

  /**
   * Return Security Subject boudled with this factory.
   * @return the subject
   */
  public abstract Subject getSubject();

  /**
   * Set Security Subject boulded with this factory.
   * @param subject 
   *        the subject to set
   */
  public abstract void setSubject(Subject subject);

  // static methods -------------------------------------------------------------
  /**
   * Return an instance of ManagementBeanFactory.
   * This constructor will create an instance of HibernateSessionFactory.
   * The initialize process will load hibernate properties by the following steps:
   * 1. Load from ${otas.dm.home}/config/otasdm/otasdm.properties"
   * 2. If not found, load hibernate.properties file from classpath
   * 3. If not found, will use System.getProperties()
   * @return instance of ManagementBeanFactory
   */
  /*
  public static ManagementBeanFactory newInstance(String none) throws DMException {
    if (log.isDebugEnabled()) {
      log.debug("new a instance of ManagementBeanFactory");
    }

    ManagementBeanFactory instance = new ManagementBeanFactoryImpl();
    return instance;
  }
  */
  
  /**
   * Return an instance of ManagementBeanFactory.
   * This constructor will create an instance of HibernateSessionFactory using properties as Hibernate Properties.
   * 
   * @return instance of ManagementBeanFactory
   */
  public static ManagementBeanFactory newInstance(Properties properties) throws DMException {
    if (log.isDebugEnabled()) {
      log.debug("new a instance of ManagementBeanFactory");
    }

    ManagementBeanFactory instance = new ManagementBeanFactoryImpl(properties);
    return instance;
  }
  
  /**
   * Factory lifecycle method. Tell the instance of factory release all of
   * resource.
   * 
   * Please call this method to release all of resource after factory is end of
   * lifecyle.
   * 
   */
  protected abstract void releaseResource();

  // public methods -------------------------------------------------------------
  /**
   * Factory lifecycle method. Tell the instance of factory release all of
   * resource.
   * 
   * Please call this method to release all of resource after factory is end of
   * lifecyle.
   * 
   */
  public void release() {
    this.releaseResource();
  }

  /**
   * Return the active status of factory.
   * @return
   * @throws DMException
   */
  public abstract boolean isOpen() throws DMException;

  /**
   * Factory lifecycle method. Begin a transaction, start-up a checkpoint.
   * If last transaction has not be commited of rollbacked, this method will automaticlly 
   * commited all of transaction before the calling and begin a new checkpoint.
   * 
   */
  public abstract void beginTransaction() throws DMException;

  /**
   * Factory lifecycle method. Commit all of transaction.
   * If the transaction has not been created, will throw DMException.
   * 
   * Commit all of transaction before this method after laster checkpoint..
   * 
   */
  public abstract void commit();

  /**
   * Factory lifecycle method.
   * Factory lifecycle method. Rollback all of transaction before this methods after last checkpoint.
   * If the transaction has not been created, will throw DMException.
   * 
   */
  public abstract void rollback();
  
  /**
   * @param clazz
   * @return
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public abstract Object createBean(Class<?> clazz) throws InstantiationException, IllegalAccessException;

  /**
   * Create a CarrierBean related with the Factory.
   * 
   * @return CarrierBean
   */
  public abstract CarrierBean createCarrierBean();

  /**
   * Create a ServiceProviderBean related with the Factory.
   * 
   * @return ServiceProviderBean
   */
  public abstract ServiceProviderBean createServiceProviderBean();

  /**
   * Create a CountryBean related with the Factory.
   * 
   * @return CountryBean
   */
  public abstract CountryBean createCountryBean();

  /**
   * Create a DDFTreeBean related with the Factory.
   * 
   * @return DDFTreeBean
   */
  public abstract DDFTreeBean createDDFTreeBean();

  /**
   * Create a ModelBean
   * 
   * @return
   */
  public abstract ModelBean createModelBean();

  /**
   * Create a ProfileMappingBean
   * 
   * @return
   */
  public abstract ProfileMappingBean createProfileMappingBean();

  /**
   * Create a ProfileTemplateBean
   * 
   * @return
   */
  public abstract ProfileTemplateBean createProfileTemplateBean();

  /**
   * Create a ProfileConfigBean
   * 
   * @return ProfileConfigBean
   */
  public abstract ProfileConfigBean createProfileConfigBean();

  /**
   * Create a SubscriberBean
   * 
   * @return
   */
  public abstract SubscriberBean createSubcriberBean();

  /**
   * Create a DeviceBean
   * 
   * @return
   */
  public abstract DeviceBean createDeviceBean();

  /**
   * Create a ProfileAssignmentBean.
   * 
   * @return
   */
  public abstract ProfileAssignmentBean createProfileAssignmentBean();

  /**
   * Create a UpdateImageBean.
   * 
   * @return
   */
  public abstract UpdateImageBean createUpdateImageBean();

  /**
   * Create a DeviceLogBean.
   * 
   * @return
   */
  public abstract DeviceLogBean createDeviceLogBean();

  /**
   * Create a ProvisionJobBean
   * 
   * @return
   */
  public abstract ProvisionJobBean createProvisionJobBean();

  /**
   * Create a SearchBean
   * 
   * @return
   */
  public abstract SearchBean createSearchBean();
  
  /**
   * Create a AutomaticProvisionJobBean.
   * 
   * @return
   */
  public abstract AutomaticProvisionJobBean createAutoProvisionJobBean();
  
  /**
   * Create a ClientProvTemplateBean
   * @return
   */
  public abstract ClientProvTemplateBean createClientProvTemplateBean();
  
  /**
   * Create a OTAClientProvJobBean
   * @param otaInventory
   * @param sender
   * @return
   */
  public abstract OTAClientProvJobBean createOTAClientProvJobBean(OTAInventory otaInventory, SmsSender sender);

  /**
   * Create a ClientProvAuditLogBean related with the Factory.
   * 
   * @return ClientProvAuditLogBean
   */
  public abstract ClientProvAuditLogBean createClientProvAuditLogBean();
  
  /**
   * Create a SoftwareBean related with the Factory.
   * 
   * @return SoftwareBean
   */
  public abstract SoftwareBean createSoftwareBean();
  
  /**
   * Create a SoftwareTrackBean.
   * @return
   */
  public abstract SoftwareTrackingBean createSoftwareTrackingBean();
  
  /**
   * Create a SoftwareTrackBean.
   * @return
   */
  public abstract SoftwareTopListBean createSoftwareTopListBean();
  
  /**
   * @return
   */
  public abstract SoftwareEvaluateBean createSoftwareEvaluateBean();
  
  /**
   * @return
   */
  public abstract ModelClassificationBean createModelClassificationBean();
  
}
