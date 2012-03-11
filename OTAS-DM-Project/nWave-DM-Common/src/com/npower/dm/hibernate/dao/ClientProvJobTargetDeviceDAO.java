/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/dao/ClientProvJobTargetDeviceDAO.java,v 1.1 2008/02/18 10:10:22 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/02/18 10:10:22 $
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
package com.npower.dm.hibernate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.npower.dm.hibernate.entity.ClientProvJobTargetDeviceEntity;

/**
 * Data access object (DAO) for domain model class ClientProvJobTargetDeviceEntity.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/02/18 10:10:22 $
 */
public class ClientProvJobTargetDeviceDAO extends BaseHibernateDAO {
  private static final Log   log                      = LogFactory.getLog(ClientProvJobTargetDeviceDAO.class);

  // property constants
  public static final String DEVICE_ID                = "deviceId";

  public static final String PHONE_NUMBER             = "phoneNumber";

  public static final String MANUFACTURER_EXTERNAL_ID = "manufacturerExternalId";

  public static final String MODEL_EXTERNAL_ID        = "modelExternalId";

  public static final String CARRIER_EXTERNAL_ID      = "carrierExternalId";

  public static final String STATUS                   = "status";

  public static final String MESSAGE_ID               = "messageId";

  public static final String MESSAGE_TYPE             = "messageType";

  public static final String SECURITY_METHOD          = "securityMethod";

  public static final String SECURITY_PIN             = "securityPin";

  public static final String PROFILE_EXTERNAL_ID      = "profileExternalId";

  public static final String CREATED_BY               = "createdBy";

  public static final String LAST_UPDATED_BY          = "lastUpdatedBy";

  public void save(ClientProvJobTargetDeviceEntity transientInstance) {
    log.debug("saving ClientProvJobTargetDeviceEntity instance");
    try {
      getSession().save(transientInstance);
      log.debug("save successful");
    } catch (RuntimeException re) {
      log.error("save failed", re);
      throw re;
    }
  }

  public void delete(ClientProvJobTargetDeviceEntity persistentInstance) {
    log.debug("deleting ClientProvJobTargetDeviceEntity instance");
    try {
      getSession().delete(persistentInstance);
      log.debug("delete successful");
    } catch (RuntimeException re) {
      log.error("delete failed", re);
      throw re;
    }
  }

  public ClientProvJobTargetDeviceEntity findById(Long id) {
    log.debug("getting ClientProvJobTargetDeviceEntity instance with id: " + id);
    try {
      ClientProvJobTargetDeviceEntity instance = (ClientProvJobTargetDeviceEntity) getSession()
          .get("ClientProvJobTargetDeviceEntity", id);
      return instance;
    } catch (RuntimeException re) {
      log.error("get failed", re);
      throw re;
    }
  }

  public List findByExample(ClientProvJobTargetDeviceEntity instance) {
    log.debug("finding ClientProvJobTargetDeviceEntity instance by example");
    try {
      List results = getSession().createCriteria("ClientProvJobTargetDeviceEntity").add(Example.create(instance)).list();
      log.debug("find by example successful, result size: " + results.size());
      return results;
    } catch (RuntimeException re) {
      log.error("find by example failed", re);
      throw re;
    }
  }

  public List findByProperty(String propertyName, Object value) {
    log.debug("finding ClientProvJobTargetDeviceEntity instance with property: " + propertyName + ", value: " + value);
    try {
      String queryString = "from ClientProvJobTargetDeviceEntity as model where model." + propertyName + "= ?";
      Query queryObject = getSession().createQuery(queryString);
      queryObject.setParameter(0, value);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find by property name failed", re);
      throw re;
    }
  }

  public List findByDeviceId(Object deviceId) {
    return findByProperty(DEVICE_ID, deviceId);
  }

  public List findByPhoneNumber(Object phoneNumber) {
    return findByProperty(PHONE_NUMBER, phoneNumber);
  }

  public List findByManufacturerExternalId(Object manufacturerExternalId) {
    return findByProperty(MANUFACTURER_EXTERNAL_ID, manufacturerExternalId);
  }

  public List findByModelExternalId(Object modelExternalId) {
    return findByProperty(MODEL_EXTERNAL_ID, modelExternalId);
  }

  public List findByCarrierExternalId(Object carrierExternalId) {
    return findByProperty(CARRIER_EXTERNAL_ID, carrierExternalId);
  }

  public List findByStatus(Object status) {
    return findByProperty(STATUS, status);
  }

  public List findByMessageId(Object messageId) {
    return findByProperty(MESSAGE_ID, messageId);
  }

  public List findByMessageType(Object messageType) {
    return findByProperty(MESSAGE_TYPE, messageType);
  }

  public List findBySecurityMethod(Object securityMethod) {
    return findByProperty(SECURITY_METHOD, securityMethod);
  }

  public List findBySecurityPin(Object securityPin) {
    return findByProperty(SECURITY_PIN, securityPin);
  }

  public List findByProfileExternalId(Object profileExternalId) {
    return findByProperty(PROFILE_EXTERNAL_ID, profileExternalId);
  }

  public List findByCreatedBy(Object createdBy) {
    return findByProperty(CREATED_BY, createdBy);
  }

  public List findByLastUpdatedBy(Object lastUpdatedBy) {
    return findByProperty(LAST_UPDATED_BY, lastUpdatedBy);
  }

  public List findAll() {
    log.debug("finding all ClientProvJobTargetDeviceEntity instances");
    try {
      String queryString = "from ClientProvJobTargetDeviceEntity";
      Query queryObject = getSession().createQuery(queryString);
      return queryObject.list();
    } catch (RuntimeException re) {
      log.error("find all failed", re);
      throw re;
    }
  }

  public ClientProvJobTargetDeviceEntity merge(ClientProvJobTargetDeviceEntity detachedInstance) {
    log.debug("merging ClientProvJobTargetDeviceEntity instance");
    try {
      ClientProvJobTargetDeviceEntity result = (ClientProvJobTargetDeviceEntity) getSession().merge(detachedInstance);
      log.debug("merge successful");
      return result;
    } catch (RuntimeException re) {
      log.error("merge failed", re);
      throw re;
    }
  }

  public void attachDirty(ClientProvJobTargetDeviceEntity instance) {
    log.debug("attaching dirty ClientProvJobTargetDeviceEntity instance");
    try {
      getSession().saveOrUpdate(instance);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }

  public void attachClean(ClientProvJobTargetDeviceEntity instance) {
    log.debug("attaching clean ClientProvJobTargetDeviceEntity instance");
    try {
      getSession().lock(instance, LockMode.NONE);
      log.debug("attach successful");
    } catch (RuntimeException re) {
      log.error("attach failed", re);
      throw re;
    }
  }
}