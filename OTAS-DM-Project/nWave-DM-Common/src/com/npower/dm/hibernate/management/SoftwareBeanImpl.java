/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/SoftwareBeanImpl.java,v 1.27 2009/01/23 03:16:17 zhao Exp $
  * $Revision: 1.27 $
  * $Date: 2009/01/23 03:16:17 $
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelClassification;
import com.npower.dm.core.ModelFamily;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwareLicenseType;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.core.SoftwareScreenShot;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.hibernate.entity.DMBinary;
import com.npower.dm.hibernate.entity.SoftwareCategoriesEntity;
import com.npower.dm.hibernate.entity.SoftwareCategoriesId;
import com.npower.dm.hibernate.entity.SoftwareCategoryEntity;
import com.npower.dm.hibernate.entity.SoftwareEntity;
import com.npower.dm.hibernate.entity.SoftwarePackageEntity;
import com.npower.dm.hibernate.entity.SoftwarePackageModelEntity;
import com.npower.dm.hibernate.entity.SoftwareScreenShotEntity;
import com.npower.dm.hibernate.entity.SoftwareVendorEntity;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ModelClassificationBean;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.27 $ $Date: 2009/01/23 03:16:17 $
 */
public class SoftwareBeanImpl extends AbstractBean implements SoftwareBean {
  
 
  /**
   * 
   */
  protected SoftwareBeanImpl() {
    super();
  }

  /**
   * Constructor
   */
  SoftwareBeanImpl(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }

  // Methods related with ManufacturerEntity
  // ***********************************************************************

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#newCategoryInstance()
   */
  public SoftwareCategory newCategoryInstance() throws DMException {
    return new SoftwareCategoryEntity();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#newCategoryInstance(com.npower.dm.core.SoftwareCategory, java.lang.String, java.lang.String)
   */
  public SoftwareCategory newCategoryInstance(SoftwareCategory parent, String name, String description)
      throws DMException {
    SoftwareCategoryEntity instance = new SoftwareCategoryEntity();
    instance.setName(name);
    instance.setDescription(description);
    instance.setParent(parent);
    return instance;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#update(com.npower.dm.core.SoftwareCategory)
   */
  public void update(SoftwareCategory category) throws DMException {
    if (category == null) {
      throw new NullPointerException("Could not add a null software category into database.");
    }
    Session session = this.getHibernateSession();
    try {
        if (category.getParent() != null) {
            category.getParent().getChildren().add(category);
        }
        session.saveOrUpdate(category);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
      if (session != null) {
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#delete(com.npower.dm.core.SoftwareCategory)
   */
  public void delete(SoftwareCategory category) throws DMException {
    Session session = this.getHibernateSession();
    try {
        session.delete(category);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getCategoryByID(long)
   */
  public SoftwareCategory getCategoryByID(long id) throws DMException {
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(SoftwareCategoryEntity.class);
      criteria.add(Expression.eq("id", new Long(id)));
      List<SoftwareCategory> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (SoftwareCategory) list.get(0);
      } else {
        throw new DMException("Result is not unique, more than one SoftwareCategories have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getAllOfCategories()
   */
  public List<SoftwareCategory> getAllOfCategories() throws DMException {
    Session session = this.getHibernateSession();
    try {
      Query query = session.createQuery("from SoftwareCategoryEntity order by id");
      List<SoftwareCategory> list = query.list();

      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getAllOfRootCategories()
   */
  public List<SoftwareCategory> getAllOfRootCategories() throws DMException {
    Session session = this.getHibernateSession();
    try {
      Query query = session.createQuery("from SoftwareCategoryEntity where parent is null order by id");
      List<SoftwareCategory> list = query.list();

      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#newVendorInstance()
   */
  public SoftwareVendor newVendorInstance() throws DMException {
    return new SoftwareVendorEntity();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#newVendorInstance(java.lang.String, java.lang.String)
   */
  public SoftwareVendor newVendorInstance(String name, String description)
      throws DMException {
    SoftwareVendorEntity instance = new SoftwareVendorEntity();
    instance.setName(name);
    instance.setDescription(description);
    return instance;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#update(com.npower.dm.core.SoftwareVendor)
   */
  public void update(SoftwareVendor vendor) throws DMException {
    if (vendor == null) {
      throw new NullPointerException("Could not add a null software vendor into database.");
    }
    Session session = this.getHibernateSession();
    try {
        session.saveOrUpdate(vendor);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
      if (session != null) {
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#delete(com.npower.dm.core.SoftwareVendor)
   */
  public void delete(SoftwareVendor vendor) throws DMException {
    Session session = this.getHibernateSession();
    try {
        session.delete(vendor);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getVendorByID(long)
   */
  public SoftwareVendor getVendorByID(long id) throws DMException {
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(SoftwareVendorEntity.class);
      criteria.add(Expression.eq("id", new Long(id)));
      List<SoftwareVendor> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (SoftwareVendor) list.get(0);
      } else {
        throw new DMException("Result is not unique, more than one SoftwareVendorEntities have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getVendorByName(long)
   */
  public SoftwareVendor getVendorByName(String name) throws DMException {
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(SoftwareVendorEntity.class);
      criteria.add(Expression.eq("name", name));
      List<SoftwareVendor> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (SoftwareVendor) list.get(0);
      } else {
        throw new DMException("Result is not unique, more than one SoftwareVendorEntities have the same name: " + name);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getAllOfCategories()
   */
  public List<SoftwareVendor> getAllOfVendors() throws DMException {
    Session session = this.getHibernateSession();
    try {
      Query query = session.createQuery("from SoftwareVendorEntity order by id");
      List<SoftwareVendor> list = query.list();

      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#newSoftwareInstance()
   */
  public Software newSoftwareInstance() throws DMException {
    return new SoftwareEntity();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#newSoftwareInstance(com.npower.dm.core.SoftwareVendor, com.npower.dm.core.SoftwareCategory, java.lang.String, java.lang.String, java.lang.String, com.npower.dm.core.SoftwareLicenseType)
   */
  public Software newSoftwareInstance(SoftwareVendor vendor, SoftwareCategory category, String externalId, String name,
      String version, SoftwareLicenseType licenseType) throws DMException {
    List<SoftwareCategory> categories = new ArrayList<SoftwareCategory>();
    categories.add(category);
    return this.newSoftwareInstance(vendor, categories, externalId, name, version, licenseType);
  }

  public Software newSoftwareInstance(SoftwareVendor vendor, List<SoftwareCategory> categories, String externalId, String name,
      String version, SoftwareLicenseType licenseType) throws DMException {
    
    SoftwareEntity instance = new SoftwareEntity();
    instance.setVendor(vendor);
    instance.setExternalId(externalId);
    instance.setName(name);
    instance.setVersion(version);
    instance.setLicenseType(licenseType.toString());
    
    long priority = 0;
    for (SoftwareCategory category: categories) {
        SoftwareCategoriesEntity e = new SoftwareCategoriesEntity(new SoftwareCategoriesId(instance, category), priority);
        instance.getSoftwareCategoriesSet().add(e);
        priority++;
    }
    
    return instance;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#update(com.npower.dm.core.Software)
   */
  public void update(Software software) throws DMException {
    if (software == null) {
      throw new NullPointerException("Could not add a null software into database.");
    }
    Session session = this.getHibernateSession();
    try {
        SoftwareVendor vendor = software.getVendor();
        vendor.getSoftwares().add(software);
        
        if (software instanceof SoftwareEntity) {
           ((SoftwareEntity)software).setLastUpdatedTime(new Date());
        }
        session.saveOrUpdate(software);
        // Update category
        for (SoftwareCategoriesEntity e: ((SoftwareEntity)software).getSoftwareCategoriesSet()) {
            session.saveOrUpdate(e);
        }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
      if (session != null) {
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#delete(com.npower.dm.core.Software)
   */
  public void delete(Software software) throws DMException {
    Session session = this.getHibernateSession();
    try {
        session.delete(software);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getSoftwareByID(long)
   */
  public Software getSoftwareByID(long id) throws DMException {
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(SoftwareEntity.class);
      criteria.add(Expression.eq("id", new Long(id)));
      List<Software> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (Software) list.get(0);
      } else {
        throw new DMException("Result is not unique, more than one SoftwareEntities have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  public Software getSoftwareByExternalID(String extID) throws DMException {
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(SoftwareEntity.class);
      criteria.add(Expression.eq("externalId", extID));
      List<Software> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (Software) list.get(0);
      } else {
        throw new DMException("Result is not unique, more than one SoftwareEntities have the same ext ID: " + extID);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getSoftware(java.lang.String, java.lang.String, java.lang.String)
   */
  public Software getSoftware(String vendorName, String softwareName, String version) throws DMException {
    if (StringUtils.isEmpty(vendorName) || StringUtils.isEmpty(softwareName) || StringUtils.isEmpty(version)) {
       return null;
    }
    Session session = this.getHibernateSession();
    try {
      SoftwareVendor vendor = this.getVendorByName(vendorName);
      if (vendor == null) {
         return null;
      }
      Criteria criteria = session.createCriteria(SoftwareEntity.class);
      criteria.add(Expression.eq("name", softwareName));
      criteria.add(Expression.eq("version", version));
      criteria.add(Expression.eq("vendor", vendor));
      List<Software> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (Software) list.get(0);
      } else {
        throw new DMException("Result is not unique, more than one SoftwareEntities have the same vendor: " + vendorName + ", software name:" + softwareName + ", version: " + version);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getAllOfSoftwares()
   */
  public List<Software> getAllOfSoftwares() throws DMException {
    Session session = this.getHibernateSession();
    try {
      Query query = session.createQuery("from SoftwareEntity order by id");
      List<Software> list = query.list();

      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#newPackageInstance()
   */
  public SoftwarePackage newPackageInstance() throws DMException {
    return new SoftwarePackageEntity();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#newPackageInstance(com.npower.dm.core.Software, java.util.Set, java.util.Set, java.lang.String)
   */
  public SoftwarePackage newPackageInstance(Software software, Set<ModelClassification> classifications, Set<ModelFamily> families, Set<Model> models, String downloadURL) throws DMException {
    SoftwarePackageEntity instance = new SoftwarePackageEntity();
    instance.setSoftware(software);
    instance.setUrl(downloadURL);
    
    this.getHibernateSession().saveOrUpdate(instance);
    
    this.assign(classifications, models, families, instance);
    return instance;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#newPackageInstance(com.npower.dm.core.Software, java.util.Set, java.util.Set, java.lang.String, java.io.InputStream, java.lang.String)
   */
  public SoftwarePackage newPackageInstance(Software software, Set<ModelClassification> classifications, Set<ModelFamily> families, Set<Model> models, String mimeType, InputStream bytes,
      String filename) throws DMException {
    try {
      SoftwarePackageEntity instance = new SoftwarePackageEntity();
      instance.setSoftware(software);
      instance.setMimeType(mimeType);
      instance.setBlobFilename(filename);
      DMBinary binary = new DMBinary(bytes);
      instance.setBinary(binary);

      this.getHibernateSession().saveOrUpdate(instance);
      this.assign(classifications, models, families, instance);
      
      return instance;
    } catch (IOException e) {
      throw new DMException("Failured to create a package instance.", e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#update(com.npower.dm.core.SoftwarePackage)
   */
  public void update(SoftwarePackage pkg) throws DMException {
    if (pkg == null) {
      throw new NullPointerException("Could not add a null software package into database.");
    }
    Session session = this.getHibernateSession();
    try {
        Software software = pkg.getSoftware();
        software.getPackages().add(pkg);
        
        if (pkg instanceof SoftwarePackageEntity) {
          ((SoftwarePackageEntity)pkg).setLastUpdatedTime(new Date());
        }
        
        if (pkg.getBinary() != null) {
           session.saveOrUpdate(pkg.getBinary());
        }
       
        session.saveOrUpdate(pkg);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
      if (session != null) {
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getPackageByID(long)
   */
  public SoftwarePackage getPackageByID(long id) throws DMException {
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(SoftwarePackageEntity.class);
      criteria.add(Expression.eq("id", new Long(id)));
      List<SoftwarePackage> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (SoftwarePackage) list.get(0);
      } else {
        throw new DMException("Result is not unique, more than one SoftwarePackageEntities have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getPackage(com.npower.dm.core.Software, com.npower.dm.core.Model)
   */
  public SoftwarePackage getPackage(Software software, Model model) throws DMException {
    Session session = this.getHibernateSession();
    try {
        Criteria criteria = session.createCriteria(SoftwarePackageEntity.class);
        criteria.add(Expression.eq("software", software));
        Criteria modelCrit = criteria.createCriteria("packageModels");
        String familyExternalID = model.getFamilyExternalID();
        
        // 构造"OR"条件, 查询ModelFamily, Model, ModelClassification
        Disjunction disjunction = Expression.disjunction();
        disjunction.add(Expression.eq("model", model));
        
        if (StringUtils.isNotEmpty(familyExternalID)) {
           disjunction.add(Expression.eq("modelFamilyExtID", familyExternalID));
        }
        
        ModelClassificationBean mcBean = this.getManagementBeanFactory().createModelClassificationBean();
        List<ModelClassification> modelClasses = mcBean.getModelClassByModel(model);
        for (ModelClassification modelClass: modelClasses) {
            disjunction.add(Expression.eq("modelClassification", modelClass));
        }
        
        modelCrit.add(disjunction);

        List<SoftwarePackage> list = criteria.list();
  
        if (list.size() == 0) {
          return null;
        }
        return (SoftwarePackage) list.get(0);
        /*
        if (list.size() == 1) {
          return (SoftwarePackage) list.get(0);
        } else {
          throw new DMException("Result is not unique, more than one SoftwarePackageEntities have the same model and software: softwareID" + software.getId() + ", modelID: " + model.getID());
        }
        */
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }


  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#delete(com.npower.dm.core.SoftwarePackage)
   */
  public void delete(SoftwarePackage pkg) throws DMException {
    Session session = this.getHibernateSession();
    try {
        session.delete(pkg);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getAllOfPackages()
   */
  public List<SoftwarePackage> getAllOfPackages() throws DMException {
    Session session = this.getHibernateSession();
    try {
      Query query = session.createQuery("from SoftwarePackageEntity order by id");
      List<SoftwarePackage> list = query.list();

      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#newScreenShotInstance()
   */
  public SoftwareScreenShot newScreenShotInstance() throws DMException {
    return new SoftwareScreenShotEntity();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#newScreenShotInstance(com.npower.dm.core.Software, java.io.InputStream, java.lang.String)
   */
  public SoftwareScreenShot newScreenShotInstance(Software software, InputStream bytes, String description)
      throws DMException {
    try {
      SoftwareScreenShot instance = new SoftwareScreenShotEntity();
      instance.setSoftware(software);
      instance.setDescription(description);
      DMBinary binary = new DMBinary(bytes);
      instance.setBinary(binary);
      return instance;
    } catch (IOException e) {
      throw new DMException("Failured to create a package instance.", e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#update(com.npower.dm.core.SoftwareScreenShot)
   */
  public void update(SoftwareScreenShot screen) throws DMException {
    if (screen == null) {
      throw new NullPointerException("Could not add a null software screen shot into database.");
    }
    Session session = this.getHibernateSession();
    try {
        if (screen.getBinary() != null) {
           session.saveOrUpdate(screen.getBinary());
        }
       
        session.saveOrUpdate(screen);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
      if (session != null) {
      }
    }
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getScreenShotByID(long)
   */
  public SoftwareScreenShot getScreenShotByID(long id) throws DMException {
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(SoftwareScreenShotEntity.class);
      criteria.add(Expression.eq("id", new Long(id)));
      List<SoftwareScreenShot> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (SoftwareScreenShot) list.get(0);
      } else {
        throw new DMException("Result is not unique, more than one SoftwareScreenShots have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#delete(com.npower.dm.core.SoftwareScreenShot)
   */
  public void delete(SoftwareScreenShot screen) throws DMException {
    Session session = this.getHibernateSession();
    
    try {
        session.delete(screen);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getAllOfScreenShots()
   */
  public List<SoftwareScreenShot> getAllOfScreenShots() throws DMException {
    Session session = this.getHibernateSession();
    try {
      Query query = session.createQuery("from SoftwareScreenShotEntity order by id");
      List<SoftwareScreenShot> list = query.list();
      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /**
   * 为package指定安装的目标型号族. 本操作将查询所有已经存在的目标型号族, 仅添加不存在的目标型号族.
   * @param families
   * @param pkg
   * @throws DMException
   */
  private void assignTargetModelClassifications(Set<ModelClassification> classifications, SoftwarePackage pkg) throws DMException {
    if (classifications ==  null || classifications.isEmpty()) {
      return;
    }
   
    SoftwarePackageEntity p = (SoftwarePackageEntity)pkg;
    Set<SoftwarePackageModelEntity> add = new HashSet<SoftwarePackageModelEntity>();
    for (ModelClassification classification: classifications) {
        boolean exists = false;
        for (SoftwarePackageModelEntity e: (Set<SoftwarePackageModelEntity>)p.getPackageModels()) {
            if (classification != null && e.getModelClassification() != null && classification.getId() == e.getModelClassification().getId()) {
               exists = true;
               break;
            }
        }
        if (!exists) {
          SoftwarePackageModelEntity e = new SoftwarePackageModelEntity();
          e.setModelClassification(classification);
          e.setSoftwarePackage(pkg);
          add.add(e);
          this.getHibernateSession().saveOrUpdate(e);
        }
    }
    p.getPackageModels().addAll(add);
    this.getHibernateSession().saveOrUpdate(p);
  }

  /**
   * 为package指定安装的目标型号族. 本操作将查询所有已经存在的目标型号族, 仅添加不存在的目标型号族.
   * @param families
   * @param pkg
   * @throws DMException
   */
  private void assignTargetModelFamilies(Set<ModelFamily> families, SoftwarePackage pkg) throws DMException {
    if (families ==  null || families.isEmpty()) {
       return;
    }
   
    SoftwarePackageEntity p = (SoftwarePackageEntity)pkg;
    Set<SoftwarePackageModelEntity> add = new HashSet<SoftwarePackageModelEntity>();
    for (ModelFamily family: families) {
        String familyExtID = family.getExternalID();
        boolean exists = false;
        for (SoftwarePackageModelEntity e: (Set<SoftwarePackageModelEntity>)p.getPackageModels()) {
            if (StringUtils.isNotEmpty(familyExtID) && familyExtID.equalsIgnoreCase(e.getModelFamilyExtID())) {
               exists = true;
               break;
            }
        }
        if (!exists) {
          SoftwarePackageModelEntity e = new SoftwarePackageModelEntity();
          e.setModelFamilyExtID(familyExtID);
          e.setSoftwarePackage(pkg);
          add.add(e);
          this.getHibernateSession().saveOrUpdate(e);
        }
    }
    p.getPackageModels().addAll(add);
    this.getHibernateSession().saveOrUpdate(p);
  }

  /**
   * 为package指定安装的目标型号. 本操作将查询所有已经存在的目标型号, 仅添加不存在的目标型号.
   * @param models
   * @param pkg
   * @throws DMException
   */
  private void assignTargetModels(Set<Model> models, SoftwarePackage pkg) throws DMException {
    if (models ==  null || models.isEmpty()) {
       return;
    }
    
    SoftwarePackageEntity p = (SoftwarePackageEntity)pkg;
    Set<SoftwarePackageModelEntity> add = new HashSet<SoftwarePackageModelEntity>();
    for (Model model: models) {
        boolean exists = false;
        for (SoftwarePackageModelEntity e: (Set<SoftwarePackageModelEntity>)p.getPackageModels()) {
            if (model != null && e.getModel() != null && model.getID() == e.getModel().getID()) {
               exists = true;
               break;
            }
        }
        if (!exists) {
          SoftwarePackageModelEntity e = new SoftwarePackageModelEntity();
          e.setModel(model);
          e.setSoftwarePackage(pkg);
          add.add(e);
          this.getHibernateSession().saveOrUpdate(e);
        }
    }
    p.getPackageModels().addAll(add);
    this.getHibernateSession().saveOrUpdate(p);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#assignTargetModels(java.util.Set, java.util.Set, com.npower.dm.core.SoftwarePackage)
   */
  public void assign(Set<ModelClassification> classifications, Set<Model> models, Set<ModelFamily> families, SoftwarePackage pkg) throws DMException {
    this.assignTargetModelClassifications(classifications, pkg);
    this.assignTargetModelFamilies(families, pkg);
    this.assignTargetModels(models, pkg);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#reassignTargetModels(java.util.Set, java.util.Set, com.npower.dm.core.SoftwarePackage)
   */
  public void reassign(Set<ModelClassification> classifications, Set<Model> models, Set<ModelFamily> families, SoftwarePackage pkg) throws DMException {
    SoftwarePackageEntity p = (SoftwarePackageEntity)pkg;
    // Clean all
    for (SoftwarePackageModelEntity e: (Set<SoftwarePackageModelEntity>)p.getPackageModels()) {
        this.getHibernateSession().delete(e);
    }
    p.getPackageModels().clear();
    
    // Assign
    this.assign(classifications, models, families, pkg);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getTargetModels(com.npower.dm.core.SoftwarePackage)
   */
  public Set<Model> getTargetModels(SoftwarePackage p) throws DMException {
    ModelBean modelBean = this.getManagementBeanFactory().createModelBean();
    SoftwarePackageEntity pkg = (SoftwarePackageEntity)p;
    
    Set<SoftwarePackageModelEntity> setOfModel = pkg.getPackageModels();
    Map<Long, Model> result = new HashMap<Long, Model>();
    for (SoftwarePackageModelEntity e: setOfModel) {
        Model model = e.getModel();
        String familyExtID = e.getModelFamilyExtID();
        ModelClassification classification = e.getModelClassification();
        if (model != null) {
           result.put(new Long(model.getID()), model);
        } else if (StringUtils.isNotEmpty(familyExtID)) {
          // Load from Model Family
          List<Model> models = modelBean.findModelsByFamilyExtID(familyExtID);
          for (Model m: models) {
            result.put(new Long(m.getID()), m);
          }
        } else if (classification != null) {
          // Load from Classification
          for (Model m: classification.getModelSelector().getModels()) {
            result.put(new Long(m.getID()), m);
          }
        }
    }
    return new HashSet<Model>(result.values());
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getSoftwareDownloadURL(com.npower.dm.core.Software, com.npower.dm.core.Model, java.lang.String)
   */
  public String getSoftwareDownloadURL(Software software, Model model, String serverDownlaodURIPattern) throws DMException {
    SoftwarePackage pkg = this.getPackage(software, model);
    if (pkg != null) {
       return getSoftwareDownloadURL(pkg, serverDownlaodURIPattern);
    } else {
      return null;
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#getSoftwareDownloadURL(com.npower.dm.core.SoftwarePackage, java.lang.String)
   */
  public String getSoftwareDownloadURL(SoftwarePackage pkg, String serverDownlaodURIPattern) throws DMException {
    if (pkg == null) {
       throw new DMException("Software Pakage is null"); 
    }
    if (StringUtils.isEmpty(pkg.getUrl())) {
       String url = serverDownlaodURIPattern.replaceAll("\\$\\{package.id\\}", "" + pkg.getId());
       url = url.replaceAll("\\$\\{package.filename\\}", "" + pkg.getBlobFilename());
       return url;
    }
    return pkg.getUrl();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#update(com.npower.dm.core.Software, java.util.List)
   */
  public void update(Software software, List<SoftwareCategory> categories) throws DMException {
    if (software == null) {
       return;
    }
    if (categories == null || categories.isEmpty()) {
       throw new DMException("Must specify at lease one SoftwareCategory.");
    }
    
    // Delete old reference
    SoftwareEntity se = (SoftwareEntity)software;
    for (SoftwareCategoriesEntity scEntity: se.getSoftwareCategoriesSet()) {
        this.getHibernateSession().delete(scEntity);
    }
    se.getSoftwareCategoriesSet().clear();
    
    long priority = 0;
    for (SoftwareCategory category: categories) {
       SoftwareCategoriesEntity e = new SoftwareCategoriesEntity(new SoftwareCategoriesId(se, category), priority);
       se.getSoftwareCategoriesSet().add(e);
       priority++;
       this.getHibernateSession().save(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.SoftwareBean#update(com.npower.dm.core.Software, com.npower.dm.core.SoftwareCategory)
   */
  public void update(Software software, SoftwareCategory category) throws DMException {
    List<SoftwareCategory> categories = new ArrayList<SoftwareCategory>();
    categories.add(category);
    this.update(software, categories);
  }

  public SoftwareCategory getCategoryByPath(String path) throws DMException {
    if (StringUtils.isEmpty(path) || path.trim().equals("/")) {
       return null;
    }
    path = path.trim();
    SoftwareCategory  parentCategory = null;    
    StringTokenizer stringTokenizer = new StringTokenizer(path, "/"); 
      if (stringTokenizer.hasMoreTokens()) {        
        SoftwareCategory category = this.findCategory(null, stringTokenizer);                      
        if (stringTokenizer.hasMoreTokens()) {
          if (category != null && !(category.getChildren()).isEmpty()) {             
             return  this.findCategory(category, stringTokenizer);
          } else {
            return null; 
          }            
        }else {
          return category;
        }        
      }
      return parentCategory;          
  } 
  
  
  private SoftwareCategory  findCategory(SoftwareCategory parentCategory, StringTokenizer stringTokenizer) {
    Session session = this.getHibernateSession();
    if ( parentCategory == null) {             
        Criteria criteria = session.createCriteria(SoftwareCategoryEntity.class);        
        criteria.add(Expression.isNull("parent"));
        criteria.add(Expression.eq("name", stringTokenizer.nextToken()));
        List<SoftwareCategory> categoryList = criteria.list();        
        if (!categoryList.isEmpty()) {
          return parentCategory = categoryList.get(0);        
        }else {          
          return parentCategory;
        }      
    } else {      
        Criteria criteria = session.createCriteria(SoftwareCategoryEntity.class);        
        criteria.add(Expression.eq("parent", parentCategory));
        criteria.add(Expression.eq("name", stringTokenizer.nextToken()));
        List<SoftwareCategory> categoryList = criteria.list();        
        if (!categoryList.isEmpty()) {
          parentCategory = categoryList.get(0);        
        }else {          
          return null;
        }        
        if (stringTokenizer.hasMoreTokens()) {
          if (parentCategory != null && !(parentCategory.getChildren()).isEmpty()) {            
            return this.findCategory(parentCategory, stringTokenizer);
          }else {
            return null;
          }
        } else {
          return parentCategory; 
        }
     }        
  }
  
}
