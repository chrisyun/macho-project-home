/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/SoftwareEvaluateBeanImpl.java,v 1.3 2008/09/03 09:09:13 chenlei Exp $
 * $Revision: 1.3 $
 * $Date: 2008/09/03 09:09:13 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Software;
import com.npower.dm.hibernate.entity.SoftwareEvaluateEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareEvaluateBean;

/**
 * @author chenlei
 * @version $Revision: 1.3 $ $Date: 2008/09/03 09:09:13 $
 */

public class SoftwareEvaluateBeanImpl extends AbstractBean implements SoftwareEvaluateBean {

  /**
   * 
   */
  public SoftwareEvaluateBeanImpl() {
    super();
  }

  /**
   * @param factory
   * @param hsession
   */
  public SoftwareEvaluateBeanImpl(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }  
  

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareEvaluateBean#getAllOfSoftwareEvaluate(com.npower.dm.core.Software)
   */
  public Collection<SoftwareEvaluateEntity> getAllOfSoftwareEvaluate(Software software) throws DMException {
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(SoftwareEvaluateEntity.class);
      criteria.add(Expression.eq("software", software));
      criteria.addOrder(Order.desc("createdTime"));
      Collection<SoftwareEvaluateEntity> result = criteria.list();
      return result;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareEvaluateBean#getSoftwareEvaluateByID(java.lang.Long)
   */
  public SoftwareEvaluateEntity getSoftwareEvaluateByID(Long id) throws DMException {
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(SoftwareEvaluateEntity.class);
      criteria.add(Expression.eq("Id", id));
      List<SoftwareEvaluateEntity> list = criteria.list();
      if (list.size() == 0) {
        return null;
      }
      if (list.size() == 1) {
        return (SoftwareEvaluateEntity)list.get(0);
      } else {
        throw new DMException("Result is not unique, more than one Software Evaluate have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareEvaluateBean#newSoftwareEvaluateInstance(com.npower.dm.core.Software, long, java.util.Date)
   */
  public SoftwareEvaluateEntity newSoftwareEvaluateInstance(Software software, long grade, Date createdTime)
  throws DMException {
    SoftwareEvaluateEntity softwareEvaluateEntity = new SoftwareEvaluateEntity();
    softwareEvaluateEntity.setSoftware(software);
    softwareEvaluateEntity.setGrade(grade);
    softwareEvaluateEntity.setCreatedTime(createdTime);
    return softwareEvaluateEntity;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareEvaluateBean#newSoftwareEvaluateInstance(com.npower.dm.core.Software, long)
   */
  public SoftwareEvaluateEntity newSoftwareEvaluateInstance(Software software, long grade) throws DMException {
    SoftwareEvaluateEntity softwareEvaluateEntity = new SoftwareEvaluateEntity();
    softwareEvaluateEntity.setSoftware(software);
    softwareEvaluateEntity.setGrade(grade);
    return softwareEvaluateEntity;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareEvaluateBean#newSoftwareEvaluateInstance()
   */
  public SoftwareEvaluateEntity newSoftwareEvaluateInstance() throws DMException {
    return new SoftwareEvaluateEntity();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareEvaluateBean#save(com.npower.dm.hibernate.entity.SoftwareEvaluateEntity)
   */
  public void save(SoftwareEvaluateEntity softwareEvaluateEntity) throws DMException {
    if (softwareEvaluateEntity == null) {
      throw new NullPointerException("Could not add a null software evaluate into database.");
    }
    Session session = this.getHibernateSession();
    try {
      session.saveOrUpdate(softwareEvaluateEntity);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
      if (session != null) {        
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareEvaluateBean#delete(com.npower.dm.hibernate.entity.SoftwareEvaluateEntity)
   */
  public void delete(SoftwareEvaluateEntity softwareEvaluateEntity) throws DMException {
    Session session = this.getHibernateSession();
    try {
      session.delete(softwareEvaluateEntity);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

}
