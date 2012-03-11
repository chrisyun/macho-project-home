/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ClientProvTemplateEntity.java,v 1.6 2008/10/29 03:19:41 zhao Exp $
  * $Revision: 1.6 $
  * $Date: 2008/10/29 03:19:41 $
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
package com.npower.dm.hibernate.entity;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Date;
import java.util.Set;

import com.npower.cp.OTAException;
import com.npower.cp.ProvisioningDoc;
import com.npower.cp.TemplateMerger;
import com.npower.cp.TemplateMergerFactory;
import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProfileTemplateBean;
import com.npower.wap.nokia.NokiaOTASettings;
import com.npower.wap.omacp.OMAClientProvSettings;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/10/29 03:19:41 $
 */
public class ClientProvTemplateEntity extends AbstractClientProvTemplate implements java.io.Serializable {
  
  // Constructors

  /** default constructor */
  public ClientProvTemplateEntity() {
  }

  /** minimal constructor */
  public ClientProvTemplateEntity(Long templateId, String externalID, String content) {
    super(templateId, externalID, content);
  }

  /** full constructor */
  public ClientProvTemplateEntity(Long templateId, ProfileCategory profileCategory, String name, String description,
      String content, String lastUpdatedBy, Date lastUpdatedTime, Long changeVersion, Set nwDmModelClientProvMaps) {
    super(templateId, profileCategory, name, description, content, lastUpdatedBy, lastUpdatedTime, changeVersion,
        nwDmModelClientProvMaps);
  }

  private ManagementBeanFactory factory = null;
  public void setManagementBeanFactory(ManagementBeanFactory factory) {
    this.factory = factory;
  }

  public ManagementBeanFactory getManagementBeanFactory() {
    return this.factory;
  }

  /**
   * Load ProfileCategoryEntity by name from DM Inventory. The method will use
   * HibernateSession from thread pool
   * 
   * @param name
   * @throws DMException
   */
  public void setCategoryByName(String name) throws DMException {
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory();
        ProfileTemplateBean bean = factory.createProfileTemplateBean();
        ProfileCategory category = bean.getProfileCategoryByName(name);
        if (category != null) {
           this.setProfileCategory(category);
        } else {
          throw new DMException("Could not found category: " + name + " for ProfileTemplateEntity:" + this.getName());
        }
    } catch (DMException e) {
      throw e;
    } finally {
      //if (factory != null) {
      //   factory.release();
      //}
    }
  }
  
  public void setContentFilename(String filename, String relativeBaseDir) throws IOException {
    File file = new File(filename);
    if (!file.isAbsolute()) {
       file = new File(relativeBaseDir, filename);
    }
    Reader reader = new FileReader(file);
    StringWriter writer = new StringWriter();
    int c = reader.read();
    while (c > 0) {
          writer.write(c);
          c = reader.read();
    }
    this.setContent(writer.toString());
    reader.close();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#merge(com.npower.wap.omacp.OMAClientProvSettings)
   */
  public ProvisioningDoc merge(OMAClientProvSettings settings) throws OTAException {
    TemplateMergerFactory mergerFactory = TemplateMergerFactory.newInstance();
    TemplateMerger merger = mergerFactory.getTemplateMerger();
    return merger.merge(this, settings);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#merge(com.npower.wap.nokia.NokiaOTASettings)
   */
  public ProvisioningDoc merge(NokiaOTASettings settings) throws OTAException {
    TemplateMergerFactory mergerFactory = TemplateMergerFactory.newInstance();
    TemplateMerger merger = mergerFactory.getTemplateMerger();
    return merger.merge(this, settings);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ClientProvTemplate#merge(com.npower.dm.core.ProfileConfig)
   */
  public ProvisioningDoc merge(ProfileConfig profile) throws OTAException {
    TemplateMergerFactory mergerFactory = TemplateMergerFactory.newInstance();
    TemplateMerger merger = mergerFactory.getTemplateMerger();
    return merger.merge(this, profile);
  }

}
