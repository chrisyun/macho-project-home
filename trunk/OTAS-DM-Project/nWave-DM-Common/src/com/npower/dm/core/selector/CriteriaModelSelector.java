/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/selector/CriteriaModelSelector.java,v 1.1 2008/09/04 06:01:19 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/09/04 06:01:19 $
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
package com.npower.dm.core.selector;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import com.npower.dm.core.Model;
import com.npower.dm.core.ModelSelector;
import com.npower.dm.hibernate.entity.ModelEntity;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/04 06:01:19 $
 */
public class CriteriaModelSelector extends AbstractModelSelector implements ModelSelector {
  
  private DetachedCriteria detachedCriteria = null;
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public CriteriaModelSelector() {
    super();
    this.detachedCriteria = DetachedCriteria.forClass(ModelEntity.class);
    this.detachedCriteria.createAlias("characters", "character");
  }

  /**
   * @param criteria
   */
  public CriteriaModelSelector(Criterion criterion) {
    this();
    this.detachedCriteria.add(criterion);
  }

  /**
   * @return the criteria
   */
  public DetachedCriteria getDetachedCriteria() {
    return detachedCriteria;
  }

  /**
   * @param criteria the criteria to set
   */
  public void setDetachedCriteria(DetachedCriteria criteria) {
    this.detachedCriteria = criteria;
  }
  
  /**
   * Add an expression into criteria.
   * @param criterion
   * @return
   */
  public DetachedCriteria addExpression(Criterion criterion) {
    this.detachedCriteria.add(criterion);
    return this.detachedCriteria;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSelector#getModels()
   */
  public Set<Model> getModels() {
    Set<Model> result = new LinkedHashSet<Model>();
    if (this.detachedCriteria == null) {
       return result;
    } else {
      result.addAll((List<Model>)this.detachedCriteria.getExecutableCriteria(this.getHibernateSession()).list());
      return result;
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSelector#isSelected(com.npower.dm.core.Model)
   */
  public boolean isSelected(Model model) {
    Criteria criteria = this.detachedCriteria.getExecutableCriteria(this.getHibernateSession()).add(Expression.eq("ID", new Long(model.getID())));
    List<Model> list = criteria.list();
    return !list.isEmpty();
  }

}
