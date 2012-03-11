/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/SoftwareTopListBean4Recommend.java,v 1.7 2008/09/11 08:35:10 hcp Exp $
 * $Revision: 1.7 $
 * $Date: 2008/09/11 08:35:10 $
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
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.hibernate.entity.AbstractSoftwareRecommend;
import com.npower.dm.hibernate.entity.SoftwareRecommendEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SearchBean;


/**
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/09/11 08:35:10 $
 */
public class SoftwareTopListBean4Recommend extends AbstractBean {

  /**
   * 
   */
  public SoftwareTopListBean4Recommend() {
    super();
  }

  /**
   * @param factory
   * @param hsession
   */
  public SoftwareTopListBean4Recommend(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }

  /**
   * @param software
   * @param category
   * @return
   * @throws DMException
   */
  public int getRecommendedPriority(Software software, SoftwareCategory category) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    SearchBean searchBean = factory.createSearchBean();
    if (category != null) {
      Criteria criteria = searchBean.newCriteriaInstance(AbstractSoftwareRecommend.class);
      criteria.add(Expression.eq("category", category));
      criteria.add(Expression.eq("software", software));
      List<AbstractSoftwareRecommend> list = criteria.list();
      AbstractSoftwareRecommend recommend = null;
      if (list != null && !list.isEmpty()) {
        recommend = list.get(0);
      }
      if (recommend != null){
        return (int) recommend.getPriority();
      } else {
        return -1;
      }
    } else {
      Criteria criteria = searchBean.newCriteriaInstance(AbstractSoftwareRecommend.class);
      criteria.add(Expression.isNull("category"));
      criteria.add(Expression.eq("software", software));
      List<AbstractSoftwareRecommend> list = criteria.list();
      AbstractSoftwareRecommend recommend = null;
      if (list != null && !list.isEmpty()) {
        recommend = list.get(0);
      }
      if (recommend != null){
        return (int) recommend.getPriority();
      }else {
        return -1;
      }
    }
    
  }

  /**
   * @param category
   * @return
   * @throws DMException
   */
  public Collection<Software> getRecommendedSoftwares(SoftwareCategory category) throws DMException {
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    SearchBean searchBean = factory.createSearchBean();
    if (category != null) {
      Criteria criteria = searchBean.newCriteriaInstance(AbstractSoftwareRecommend.class);
      criteria.add(Expression.eq("category", category));
      criteria.addOrder(Order.asc("priority"));
      List<AbstractSoftwareRecommend> list = criteria.list();
    
      if (!list.isEmpty()) {
        Collection<Software> softList = new ArrayList<Software>();
        for (AbstractSoftwareRecommend recommend : list) {
          softList.add(recommend.getSoftware());
        }
        return softList;
      }
    }else {
      Criteria criteria = searchBean.newCriteriaInstance(AbstractSoftwareRecommend.class);
      criteria.add(Expression.isNull("category"));
      criteria.addOrder(Order.asc("priority"));
      List<AbstractSoftwareRecommend> list = criteria.list();
    
      if (!list.isEmpty()) {
        Collection<Software> softList = new ArrayList<Software>();
        for (AbstractSoftwareRecommend recommend : list) {
          softList.add(recommend.getSoftware());
        }
        return softList;
      }
    }
    return new ArrayList<Software>();
  }

  /**
   * @param software
   * @param category
   * @param priority
   * @throws DMException
   */
  public void setRecommendedPriority(Software software, SoftwareCategory category, int priority) throws DMException {
    
    try { 
      ManagementBeanFactory factory = this.getManagementBeanFactory();
      SearchBean searchBean = factory.createSearchBean();
                              
      AbstractSoftwareRecommend recommend = loadRecommendedItem(searchBean, category, software);
      Session session = this.getHibernateSession();
      if (priority < 0 && recommend != null) {  
        // 存在, 且优先级小于0, 删除
        session.delete(recommend);   
        return;
      } else if(priority >= 0 && recommend != null) {
        // 存在, 且优先级大于0 ，先删除 
        session.delete(recommend);
      } if (priority >= 0 && recommend == null) {
        // 不存在, 且优先级大于0 
      }
      
      List<AbstractSoftwareRecommend> list1 = getItems4Increasement(category, priority, searchBean);    
      if (!list1.isEmpty()) {                  
        this.addSoftwareRecommendPriority(session, list1);
      }
      recommend = new SoftwareRecommendEntity();
      recommend.setCategory(category);
      recommend.setSoftware(software);
      recommend.setPriority(new Long(priority));
      session.saveOrUpdate(recommend);
    } catch (Exception e) {
      throw new DMException("Failure to set priority for software extID: " + software.getExternalId() + ", category: " + category.getPathAsString(), e);
    }            
  }

  /**
   * 提取Recommended Item
   * @param searchBean
   * @param category
   * @param software
   * @return
   * @throws DMException
   * @throws HibernateException
   */
  private AbstractSoftwareRecommend loadRecommendedItem(SearchBean searchBean, SoftwareCategory category,
      Software software) throws DMException, HibernateException {
    Criteria criteria = searchBean.newCriteriaInstance(AbstractSoftwareRecommend.class);
    criteria.add(Expression.eq("software", software));
    if (category != null) {
      criteria.add(Expression.eq("category", category));
    } else {
      criteria.add(Expression.isNull("category"));
    }
    List<AbstractSoftwareRecommend> list = criteria.list();
    AbstractSoftwareRecommend  recommend = null;
    
    if (!list.isEmpty()) {
      recommend = list.get(0);
    }
    return recommend;
  }

  /**
   * 找出所有需要挪出空间降低优先级的Item
   * @param category
   * @param priority
   * @param searchBean
   * @return
   * @throws DMException
   * @throws HibernateException
   */
  private List<AbstractSoftwareRecommend> getItems4Increasement(SoftwareCategory category, int priority, SearchBean searchBean)
      throws DMException, HibernateException {
    Criteria criteria = searchBean.newCriteriaInstance(AbstractSoftwareRecommend.class);
    criteria.add(Expression.ge("priority", new Long(priority)));
    criteria.add(Expression.eq("category", category));
    List<AbstractSoftwareRecommend> list = criteria.list();
    return list;
  }
  
  /**
   * 将 List中的priority加1
   * @param session
   * @param list
   * @throws DMException
   */
  private void addSoftwareRecommendPriority(Session session, List<AbstractSoftwareRecommend> list) throws DMException {
    for (AbstractSoftwareRecommend recommendn: list) {
      recommendn.setPriority(new Long(recommendn.getPriority()+1));
      session.update(recommendn);
    }
  }
  
}
