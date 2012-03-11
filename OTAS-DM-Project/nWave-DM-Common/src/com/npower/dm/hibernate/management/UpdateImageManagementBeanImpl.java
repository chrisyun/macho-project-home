/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/UpdateImageManagementBeanImpl.java,v 1.10 2008/11/19 07:52:20 chenlei Exp $
 * $Revision: 1.10 $
 * $Date: 2008/11/19 07:52:20 $
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Image;
import com.npower.dm.core.ImageUpdateStatus;
import com.npower.dm.core.Model;
import com.npower.dm.core.Update;
import com.npower.dm.hibernate.entity.DMBinary;
import com.npower.dm.hibernate.entity.ImageEntity;
import com.npower.dm.hibernate.entity.ImageUpdateStatusEntity;
import com.npower.dm.hibernate.entity.UpdateEntity;
import com.npower.dm.management.BaseBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.UpdateImageBean;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.10 $ $Date: 2008/11/19 07:52:20 $
 */
public class UpdateImageManagementBeanImpl extends AbstractBean implements BaseBean, UpdateImageBean {

  private static Log log = LogFactory.getLog(ProfileConfigManagementBeanImp.class);

  public Update newUpdateInstance(Image from, Image to) throws DMException {
    return new UpdateEntity(from, to);
  }

  public Update newUpdateInstance(Image from, Image to, InputStream bytes) throws DMException, IOException {
    return new UpdateEntity(from, to, bytes);
  }

  /**
   * 
   */
  protected UpdateImageManagementBeanImpl() {
    super();
  }

  /**
   * @param factory
   * @param hsession
   */
  UpdateImageManagementBeanImpl(ManagementBeanFactory factory, Session hsession) {

    super(factory, hsession);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.UpdateImageBean#update(com.npower.dm.hibernate.UpdateEntity)
   */
  public void update(Update object) throws DMException {
    Session session = this.getHibernateSession();
    if (object == null) {
      throw new NullPointerException("Could not add a null UpdateEntity into database.");
    }
    if ( ! (object instanceof UpdateEntity)) {
       throw new DMException("Update is not instance of UpdateEntity.");
    }
    UpdateEntity update = (UpdateEntity)object;
    try {
      // Initlizing the UpdateEntity status
      if (update.getStatus() == null || update.getStatus().getStatusId() == 0) {
         update.setStatus(this.getImageUpdateStatus(Image.STATUS_CREATED));
      }
      
      // Save or update the FromImage
      Image fromImage = update.getFromImage();
      if (fromImage.getStatus() == null || fromImage.getStatus().getStatusId() == 0) {
        // Inherit the image status from UpdateEntity
        fromImage.setStatus(update.getStatus());
      }
      session.saveOrUpdate(fromImage);

      // Save or update the ToImage
      Image toImage = update.getToImage();
      if (toImage.getStatus() == null || toImage.getStatus().getStatusId() == 0) {
        // Inherit the image status from UpdateEntity
        toImage.setStatus(update.getStatus());
      }
      session.saveOrUpdate(toImage);

      // Save or update the UpdateEntity
      update.setLastUpdatedTime(new Date());
      session.saveOrUpdate(update);

      // Save the DMBlob
      DMBinary blob = update.getDMBlob();
      if (blob != null) {
         session.saveOrUpdate(blob);
      }

    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.UpdateImageBean#getUpdateByID(java.lang.String)
   */
  public Update getUpdateByID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
        Update result = (Update)session.get(UpdateEntity.class, new Long(id));
        return result;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }


  /* (non-Javadoc)
   * @see com.npower.dm.management.UpdateImageBean#getUpdate(com.npower.dm.core.Model, java.lang.String, java.lang.String)
   */
  public Update getUpdate(Model model, String fromVersion, String toVersion) throws DMException {
    if (model == null || StringUtils.isEmpty(fromVersion) || StringUtils.isEmpty(toVersion)) {
       return null;
    }
    Session session = this.getHibernateSession();
    try {
      Image from = this.getImageByVersionID(model, fromVersion);
      if (from == null) {
        return null;
      }
      Image to = this.getImageByVersionID(model, toVersion);
      if (to == null) {
        return null;
      }
      Criteria criteria = session.createCriteria(Update.class);
      criteria.add(Expression.eq("fromImage", from));
      criteria.add(Expression.eq("toImage", to));
      List<Update> list = criteria.list();
      if (list != null && list.size() > 0) {
         return list.get(0);
      } else {
        return null;
      }

    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /**
   * Find all Updates which fromImage has versionID for specified model.
   * 
   * @param model
   * @param versionId
   * @return
   * @throws DMException
   */
  public List<Update> findUpdatesByFromImageVersionID(Model model, String versionId) throws DMException {
    if (model == null || versionId == null || versionId.trim().length() == 0) {
      return new ArrayList<Update>(0);
    }
    Session session = this.getHibernateSession();
    try {
      Image image = this.getImageByVersionID(model, versionId);
      if (image == null) {
        return new ArrayList<Update>(0);
      }
      Criteria criteria = session.createCriteria(Update.class);
      criteria.add(Expression.eq("fromImage", image));
      List<Update> list = criteria.list();
      return list;

    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /**
   * Find all of Updates which ToImage has versionID for specified model.
   * 
   * @param model
   * @param versionId
   * @return
   * @throws DMException
   */
  public List<Update> findUpdatesByToImageVersionID(Model model, String versionId) throws DMException {
    if (model == null || versionId == null || versionId.trim().length() == 0) {
      return new ArrayList<Update>(0);
    }
    Session session = this.getHibernateSession();
    try {
      Image image = this.getImageByVersionID(model, versionId);
      if (image == null) {
        return new ArrayList<Update>(0);
      }
      Criteria criteria = session.createCriteria(Update.class);
      criteria.add(Expression.eq("toImage", image));
      List<Update> list = criteria.list();
      return list;

    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.UpdateImageBean#delete(com.npower.dm.hibernate.UpdateEntity)
   */
  public void delete(Update update) throws DMException {
    Session session = this.getHibernateSession();
    try {
      log.trace("deleting:" + update);
      Image fromImage = update.getFromImage();
      Image toImage = update.getToImage();
      // Delete UpdateEntity, first
      session.delete(update);

      // Delete fromImage and toImage
      fromImage = this.getImageByID(new Long(fromImage.getID()).toString());
      if (fromImage != null) {
        List<Update> updatesReferenceAsFrom = this.findUpdatesByFromImageVersionID(fromImage.getModel(), fromImage
            .getVersionId());
        List<Update> updatesReferenceAsTo = this.findUpdatesByToImageVersionID(fromImage.getModel(), fromImage.getVersionId());
        if (updatesReferenceAsFrom.size() == 0 && updatesReferenceAsTo.size() == 0) {
          // No any update refernce this image, so delete it.
          session.delete(fromImage);
        }
      }
      toImage = this.getImageByID(new Long(toImage.getID()).toString());
      if (toImage != null) {
        List<Update> updatesReferenceAsFrom = this.findUpdatesByFromImageVersionID(toImage.getModel(), toImage.getVersionId());
        List<Update> updatesReferenceAsTo = this.findUpdatesByToImageVersionID(toImage.getModel(), toImage.getVersionId());
        if (updatesReferenceAsFrom.size() == 0 && updatesReferenceAsTo.size() == 0) {
          // No any update refernce this image, so delete it.
          session.delete(toImage);
        }
      }
      log.trace("deleted:" + update);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  public Image newImageInstance(Model model, String externalID, boolean applicableToAllCarriers, String description)
      throws DMException {
    return new ImageEntity(model, externalID, applicableToAllCarriers, description);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.UpdateImageBean#update(com.npower.dm.hibernate.ImageEntity)
   */
  public void update(Image image) throws DMException {
    Session session = this.getHibernateSession();
    if (image == null) {
      throw new NullPointerException("Could not add a null ImageEntity into Dm inventory.");
    }
    try {

      session.saveOrUpdate(image);

    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.UpdateImageBean#getImageByID(java.lang.String)
   */
  public Image getImageByID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from ImageEntity where ID='" + id + "'");
      List<Image> list = query.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (Image) list.get(0);
      } else {
        throw new DMException("Result is not unique, more ImageEntity have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.UpdateImageBean#getImageByVersionID(com.npower.dm.hibernate.ModelEntity,
   *      java.lang.String)
   */
  public Image getImageByVersionID(Model model, String version) throws DMException {
    if (version == null || version.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(Image.class);
      criteria.add(Expression.eq("model", model));
      criteria.add(Expression.eq("versionId", version));
      List<Image> list = criteria.list();
      if (list.size() == 0) {
        return null;
      } else if (list.size() == 1) {
        return (Image) list.get(0);
      } else {
        throw new DMException("Result is not unique, more ImageEntity have the same ID: " + version
            + "under the ModelEntity: " + model.getName());
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.UpdateImageBean#delete(com.npower.dm.hibernate.ImageEntity)
   */
  public void delete(Image image) throws DMException {
    Session session = this.getHibernateSession();
    try {
      log.trace("deleting:" + image);
      session.delete(image);
      log.trace("deleted:" + image);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.UpdateImageBean#findUpdates4Upgrade(com.npower.dm.hibernate.ModelEntity,
   *      java.lang.String)
   */
  public List<Update> findUpdates4Upgrade(Model model, String versionId) throws DMException {
    Image image = this.getImageByVersionID(model, versionId);
    if (image == null) {
      return new ArrayList<Update>(0);
    }
    Session session = this.getHibernateSession();
    long releaseStatus = Image.STATUS_RELEASED;
    try {
      ImageUpdateStatus status = getImageUpdateStatus(releaseStatus);
      Criteria criteria = session.createCriteria(Update.class);
      criteria.add(Expression.eq("fromImage", image));
      criteria.add(Expression.eq("status", status));

      Criteria upgradeCriteria = criteria.createCriteria("toImage");
      upgradeCriteria.add(Expression.gt("versionId", image.getVersionId()));
      upgradeCriteria.addOrder(Order.asc("versionId"));

      return criteria.list();
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.UpdateImageBean#findUpdates4Downgrade(com.npower.dm.hibernate.ModelEntity,
   *      java.lang.String)
   */
  public List<Update> findUpdates4Downgrade(Model model, String versionId) throws DMException {
    Image image = this.getImageByVersionID(model, versionId);
    if (image == null) {
      return new ArrayList<Update>(0);
    }
    Session session = this.getHibernateSession();
    long releaseStatus = Image.STATUS_RELEASED;
    try {
      ImageUpdateStatus status = getImageUpdateStatus(releaseStatus);
      Criteria criteria = session.createCriteria(Update.class);
      criteria.add(Expression.eq("fromImage", image));
      criteria.add(Expression.eq("status", status));

      Criteria upgradeCriteria = criteria.createCriteria("toImage");
      upgradeCriteria.addOrder(Order.desc("versionId"));
      upgradeCriteria.add(Expression.lt("versionId", image.getVersionId()));

      return criteria.list();
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.UpdateImageBean#findReleasedUpdates(com.npower.dm.hibernate.ModelEntity,
   *      java.lang.String)
   */
  public List<Update> findReleasedUpdates(Model model, String versionId) throws DMException {
    Image image = this.getImageByVersionID(model, versionId);
    if (image == null) {
      return new ArrayList<Update>(0);
    }
    Session session = this.getHibernateSession();
    long releaseStatus = Image.STATUS_RELEASED;
    try {
      ImageUpdateStatus status = getImageUpdateStatus(releaseStatus);
      Criteria criteria = session.createCriteria(UpdateEntity.class);
      criteria.add(Expression.eq("fromImage", image));
      criteria.add(Expression.eq("status", status));

      Criteria upgradeCriteria = criteria.createCriteria("toImage");
      upgradeCriteria.add(Expression.eq("model", model));
      upgradeCriteria.addOrder(Order.desc("versionId"));

      return criteria.list();
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  public List<Update> findUpdates(Model model) throws DMException {
    Session session = this.getHibernateSession();
    try {
        Criteria criteria = session.createCriteria(UpdateEntity.class);
  
        Criteria upgradeCriteria = criteria.createCriteria("toImage");
        upgradeCriteria.add(Expression.eq("model", model));
        upgradeCriteria.addOrder(Order.desc("versionId"));
  
        return criteria.list();
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.UpdateImageBean#findReleasedUpdates(com.npower.dm.core.Model)
   */
  public List<Update> findReleasedUpdates(Model model) throws DMException {
    Session session = this.getHibernateSession();
    long releaseStatus = Image.STATUS_RELEASED;
    try {
        ImageUpdateStatus status = getImageUpdateStatus(releaseStatus);
        Criteria criteria = session.createCriteria(UpdateEntity.class);
        criteria.add(Expression.eq("status", status));
  
        Criteria upgradeCriteria = criteria.createCriteria("toImage");
        upgradeCriteria.add(Expression.eq("model", model));
        upgradeCriteria.addOrder(Order.desc("versionId"));
  
        return criteria.list();
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  public List<Update> findReleasedUpdates() throws DMException {
    Session session = this.getHibernateSession();
    long releaseStatus = Image.STATUS_RELEASED;
    try {
        ImageUpdateStatus status = getImageUpdateStatus(releaseStatus);
        Criteria criteria = session.createCriteria(UpdateEntity.class);
        criteria.add(Expression.eq("status", status));
  
        Criteria upgradeCriteria = criteria.createCriteria("toImage");
        upgradeCriteria.addOrder(Order.asc("model"));
        upgradeCriteria.addOrder(Order.desc("versionId"));
  
        return criteria.list();
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.UpdateImageBean#getImageUpdateStatus(long)
   */
  public ImageUpdateStatus getImageUpdateStatus(Long releaseStatus) throws DMException {
    try {
      ImageUpdateStatus status = (ImageUpdateStatus) this.getHibernateSession().get(ImageUpdateStatusEntity.class,
          releaseStatus.longValue());
      return status;
    } catch (HibernateException e) {
      throw new DMException("Could not found ImageUpdateStatusEntity by ID: " + releaseStatus, e);
    }
  }
}
