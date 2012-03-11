/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/ModelClassificationBeanImpl.java,v 1.3 2008/09/05 02:12:42 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/09/05 02:12:42 $
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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ManagementBeanFactoryAware;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelClassification;
import com.npower.dm.core.ModelSelector;
import com.npower.dm.hibernate.HibernateSessionAware;
import com.npower.dm.hibernate.entity.AbstractModelClassification;
import com.npower.dm.hibernate.entity.AbstractPredefinedModelSelector;
import com.npower.dm.hibernate.entity.ModelClassificationEntity;
import com.npower.dm.hibernate.entity.PredefinedModelSelectorEntity;
import com.npower.dm.management.BaseBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelClassificationBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/09/05 02:12:42 $
 */
public class ModelClassificationBeanImpl extends AbstractBean implements ModelClassificationBean, BaseBean {

  /**
   * Private Default Constructor
   */
  protected ModelClassificationBeanImpl() {
    super();
  }

  /**
   * @param factory
   * @param session
   */
  public ModelClassificationBeanImpl(ManagementBeanFactory factory, Session session) {
    super(factory, session);
  }


  /* (non-Javadoc)
   * @see com.npower.dm.management.ModelClassificationBean#getModelClassByModel(com.npower.dm.core.Model)
   */
  public List<ModelClassification> getModelClassByModel(Model model) throws DMException {
    Set<ModelClassification> result = new LinkedHashSet<ModelClassification>();
    for (ModelClassification classification: this.getAllOfModelClassifications()) {
        if (classification.isMemeber(model)) {
           result.add(classification);
        }
    }
    return new ArrayList<ModelClassification>(result);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ModelClassificationBean#isMemeber(com.npower.dm.core.ModelClassification, com.npower.dm.core.Model)
   */
  public boolean isMemeber(ModelClassification modelClassification, Model model) throws DMException {
    return modelClassification.isMemeber(model);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ModelClassificationBean#delete(com.npower.dm.core.ModelClassification)
   */
  public void delete(ModelClassification modelClassification) throws DMException {
    Session session = this.getHibernateSession();
    try {
        session.delete(modelClassification);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ModelClassificationBean#getAllOfModelClassifications()
   */
  public List<ModelClassification> getAllOfModelClassifications() throws DMException {
    Session session = this.getHibernateSession();
    try {
      Query query = session.createQuery("from ModelClassificationEntity order by name asc");
      List<ModelClassification> list = query.list();

      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ModelClassificationBean#newInstance(java.lang.String, java.lang.String, com.npower.dm.core.ModelSelector, java.util.List)
   */
  public ModelClassification newInstance(String extID, String name, ModelSelector selector, List<Model> models) throws DMException {
    Session session = this.getHibernateSession();
    ModelClassificationEntity entity = new ModelClassificationEntity(extID, name);
    entity.setModelSelector(selector);
    session.save(entity);
    if (models != null) {
       for (Model model: models) {
           PredefinedModelSelectorEntity pe = new PredefinedModelSelectorEntity();
           pe.setModel(model);
           pe.setModelClassification(entity);
           session.save(pe);
       }
    }
    if (entity instanceof HibernateSessionAware) {
       ((HibernateSessionAware)entity).setHibernateSession(session);
    }
    if (selector instanceof ManagementBeanFactoryAware) {
       ((ManagementBeanFactoryAware)selector).setManagementBeanFactory(this.getManagementBeanFactory());
    }
    return entity;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ModelClassificationBean#newInstance(java.lang.String, java.lang.String, com.npower.dm.core.ModelSelector)
   */
  public ModelClassification newInstance(String extID, String name, ModelSelector selector) throws DMException {
    return this.newInstance(extID, name, selector, null);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ModelClassificationBean#newInstance(java.lang.String, java.lang.String, java.util.List)
   */
  public ModelClassification newInstance(String extID, String name, List<Model> models) throws DMException {
    return this.newInstance(extID, name, null, models);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ModelClassificationBean#update(com.npower.dm.core.ModelClassification)
   */
  public void update(ModelClassification modelClassification) throws DMException {
    Session session = this.getHibernateSession();
    session.saveOrUpdate(modelClassification);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ModelClassificationBean#update(com.npower.dm.core.ModelClassification, java.util.List)
   */
  public void update(ModelClassification modelClassification, List<Model> models) throws DMException {
    Session session = this.getHibernateSession();
    session.saveOrUpdate(modelClassification);
    for (AbstractPredefinedModelSelector pe: ((AbstractModelClassification)modelClassification).getPredefinedModelSelectors()) {
        session.delete(pe);
    }
    if (models != null) {
      for (Model model: models) {
          PredefinedModelSelectorEntity pe = new PredefinedModelSelectorEntity();
          pe.setModel(model);
          pe.setModelClassification((ModelClassificationEntity)modelClassification);
          session.save(pe);
      }
   }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ModelClassificationBean#getModelClassificationByExtID(java.lang.String)
   */
  public ModelClassification getModelClassificationByExtID(String extID) throws DMException {
    if (StringUtils.isEmpty(extID)) {
      return null;
    }

    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(ModelClassificationEntity.class);
      criteria.add(Expression.eq("externalID", extID));
      List<Model> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (ModelClassification) list.get(0);
      } else {
        throw new DMException("Result is not unique, many ModelClassificationEntity have the same extID: " + extID);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ModelClassificationBean#getModelClassificationByID(long)
   */
  public ModelClassification getModelClassificationByID(long id) throws DMException {
    if (id == 0) {
      return null;
    }

    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(ModelClassificationEntity.class);
      criteria.add(Expression.eq("id", new Long(id)));
      List<Model> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (ModelClassification) list.get(0);
      } else {
        throw new DMException("Result is not unique, many ModelClassificationEntity have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

}
