/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/ClientProvTemplateBeanImpl.java,v 1.6 2007/11/23 09:04:26 zhao Exp $
  * $Revision: 1.6 $
  * $Date: 2007/11/23 09:04:26 $
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
package com.npower.dm.hibernate.management;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileCategory;
import com.npower.dm.hibernate.entity.ClientProvTemplateEntity;
import com.npower.dm.hibernate.entity.ModelClientProvMap;
import com.npower.dm.hibernate.entity.ModelClientProvMapEntity;
import com.npower.dm.hibernate.entity.ModelClientProvMapId;
import com.npower.dm.hibernate.entity.ModelEntity;
import com.npower.dm.hibernate.management.digester.ClientProvTemplateEntityFactory;
import com.npower.dm.management.ClientProvTemplateBean;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2007/11/23 09:04:26 $
 */
public class ClientProvTemplateBeanImpl extends AbstractBean implements ClientProvTemplateBean {
  
  private static Log log = LogFactory.getLog(ClientProvTemplateBeanImpl.class);

  /**
   * Default constructor
   */
  protected ClientProvTemplateBeanImpl() {
    super();
  }

  /**
   * @param factory
   * @param hsession
   */
  public ClientProvTemplateBeanImpl(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ClientProvTemplateBean#findTemplate(com.npower.dm.core.Model, com.npower.dm.core.ProfileCategory)
   */
  public ClientProvTemplate findTemplate(Model model, ProfileCategory category) throws DMException {
    if (model == null) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
        Criteria criteria = session.createCriteria(ClientProvTemplate.class);
        criteria.add(Expression.eq("profileCategory", category));
        Criteria mappingCriteria = criteria.createCriteria("modelClientProvMaps");
        mappingCriteria.add(Expression.eq("model", model));
        List<ClientProvTemplate> list = criteria.list();
        if (list.isEmpty()) {
           return null;
        }
        return list.get(0);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ClientProvTemplateBean#findTemplates(com.npower.dm.core.Model)
   */
  public List<ClientProvTemplate> findTemplates(Model model) throws DMException {
    if (model == null) {
      return new ArrayList<ClientProvTemplate>(0);
    }
    Session session = this.getHibernateSession();
    try {
        Criteria criteria = session.createCriteria(ClientProvTemplate.class);
        Criteria mappingCriteria = criteria.createCriteria("modelClientProvMaps");
        mappingCriteria.add(Expression.eq("model", model));
        List<ClientProvTemplate> list = criteria.list();
        return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ClientProvTemplateBean#getTemplate(java.lang.String)
   */
  public ClientProvTemplate getTemplate(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
        Query query = session.createQuery("from ClientProvTemplateEntity where ID='" + id + "'");
        List<ClientProvTemplate> list = query.list();
  
        if (list.size() == 0) {
          return null;
        }
  
        if (list.size() == 1) {
          return list.get(0);
        } else {
          throw new DMException("Result is not unique, many tempaltes have the same ClientProvTemplate ID: " + id);
        }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ClientProvTemplateBean#newClientProvTemplateInstance()
   */
  public ClientProvTemplate newClientProvTemplateInstance() throws DMException {
    return new ClientProvTemplateEntity();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ClientProvTemplateBean#update(com.npower.dm.core.ClientProvTemplate)
   */
  public void update(ClientProvTemplate template) throws DMException {
    if (template == null) {
      throw new NullPointerException("Could not add a null ClientProvTemplate into database.");
    }
    Session session = this.getHibernateSession();
    try {
      session.saveOrUpdate(template);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ClientProvTemplateBean#delete(com.npower.dm.core.ClientProvTemplate)
   */
  public void delete(ClientProvTemplate template) throws DMException {
    Session session = this.getHibernateSession();
    try {
        session.delete(template);

    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ClientProvTemplateBean#attach(com.npower.dm.core.Model, com.npower.dm.core.ClientProvTemplate)
   */
  public void attach(Model model, ClientProvTemplate template) throws DMException {
    // Checking
    if (template == null || template.getID() == null || template.getID().longValue() == 0) {
       throw new DMException("Must specified a ClientProvTemplate to attach.");
    }

    if (model == null || model.getID() == 0) {
      throw new DMException("Missing model");
   }

    try {
        Criteria criteria = this.getHibernateSession().createCriteria(ModelClientProvMapEntity.class);
        criteria.add(Expression.eq("model", model));
        criteria.add(Expression.eq("clientProvTemplate", template));
        List<ModelClientProvMapEntity> mappings = criteria.list();
        ModelClientProvMapEntity modelMapping = null;
        if (mappings == null || mappings.size() == 0) {
           // Add new profile mapping.
           modelMapping = new ModelClientProvMapEntity();
           ModelClientProvMapId id = new ModelClientProvMapId();
           id.setTemplateId(template.getID().longValue());
           id.setModelId(model.getID());
           modelMapping.setId(id);
        } else {
          modelMapping = mappings.get(0);
        }

        modelMapping.setClientProvTemplate(template);
        modelMapping.setModel(model);
  
        Set<ModelClientProvMapEntity> set = ((ModelEntity)model).getModelClientProvMaps();
        if (!set.contains(modelMapping)) {
           // Link ModelClientProvMapEntity with ModelEntity
           set.add(modelMapping);
  
           // Link ModelClientProvMapEntity with ClientProvTemplate
           ((ClientProvTemplateEntity)template).getModelClientProvMaps().add(modelMapping);
  
           // Save into DM Inventory
           this.getHibernateSession().saveOrUpdate(modelMapping);
  
        }
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ClientProvTemplateBean#dettach(com.npower.dm.core.Model, com.npower.dm.core.ClientProvTemplate)
   */
  public void dettach(Model model, ClientProvTemplate template) throws DMException {
    // Checking
    if (template == null || template.getID() == null || template.getID().longValue() == 0) {
       throw new DMException("Must specified a ClientProvTemplate to attach.");
    }

    if (model == null || model.getID() == 0) {
      throw new DMException("Missing model");
    }
    try {
        ModelClientProvMapId id = new ModelClientProvMapId();
        id.setTemplateId(template.getID().longValue());
        id.setModelId(model.getID());
  
        ModelClientProvMap modelMapping = (ModelClientProvMap) this.getHibernateSession().get(
            "com.npower.dm.hibernate.entity.ModelClientProvMapEntity", id);

        // Remove from the ModelEntity related
        Set<ModelClientProvMapEntity> set = ((ModelEntity)model).getModelClientProvMaps();
        if (set.contains(modelMapping)) {
          set.remove(modelMapping);
        }
  
        // Remove from the ModelClientProvMapEntity related
        set = ((ClientProvTemplateEntity)template).getModelClientProvMaps();
        if (set.contains(modelMapping)) {
          set.remove(modelMapping);
        }
        
        this.getHibernateSession().delete(modelMapping);
        
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }
  
  public List<ClientProvTemplate> importClientProvTemplates(InputStream in, String relativeBaseDir) throws DMException {
    Transaction tx = null;
    try {
      List<ClientProvTemplate> templates = this.parsingClientProvTemplates(in, relativeBaseDir);
      tx = this.getHibernateSession().beginTransaction();
      for (int i = 0; i < templates.size(); i++) {
        ClientProvTemplate template = (ClientProvTemplate) templates.get(i);
        ProfileCategory category = template.getProfileCategory();
        if (category == null) {
           throw new DMException("ClientProvTemplate#" + (i + 1) + " which will be imported missing the ProfileCategory.");
        }
        log.trace("add:" + template);
        this.update(template);
      }
      tx.commit();
      return templates;
    } catch (Exception e) {
      if (tx != null && tx.isActive()) {
        tx.rollback();
      }
      throw new DMException(e);
    }
  }

  /**
   * @param in
   * @return
   */
  private List<ClientProvTemplate> parsingClientProvTemplates(InputStream in, String relativeBaseDir) throws DMException {
    // Create and execute our Digester
    Digester digester = createClientProvTemplateDigester(relativeBaseDir);
    try {
      // Push a ManagementBeanFactory into the stack.
      digester.push(this.getManagementBeanFactory());
      
      List<ClientProvTemplate> result = new ArrayList<ClientProvTemplate>();
      digester.push(result);
      digester.parse(in);
      return result;
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /**
   * @return
   */
  private Digester createClientProvTemplateDigester(String relativeBaseDir) {
    // Initialize the digester
    Digester digester = new Digester();
    digester.setValidating(false);

    // Parsing ProfileMappingEntity
    ClientProvTemplateEntityFactory factory = new ClientProvTemplateEntityFactory();
    digester.addFactoryCreate("ProfileMetaData/CPTemplates/CPTemplate", factory);
    
    digester.addBeanPropertySetter("ProfileMetaData/CPTemplates/CPTemplate/ID", "externalID");
    digester.addBeanPropertySetter("ProfileMetaData/CPTemplates/CPTemplate/Name", "name");
    digester.addBeanPropertySetter("ProfileMetaData/CPTemplates/CPTemplate/Encoder", "encoder");
    digester.addBeanPropertySetter("ProfileMetaData/CPTemplates/CPTemplate/Category", "categoryByName");
    digester.addBeanPropertySetter("ProfileMetaData/CPTemplates/CPTemplate/Description", "description");
    
    digester.addCallMethod("ProfileMetaData/CPTemplates/CPTemplate/Content", "setContentFilename", 2);
    digester.addCallParam("ProfileMetaData/CPTemplates/CPTemplate/Content", 0, "filename");
    digester.addObjectParam("ProfileMetaData/CPTemplates/CPTemplate/Content", 1, relativeBaseDir);
    
    digester.addSetNext("ProfileMetaData/CPTemplates/CPTemplate", "add");
    return (digester);
  }

}
