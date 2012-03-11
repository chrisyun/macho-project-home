/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/ModelManagementBeanImpl.java,v 1.29 2008/11/19 04:27:49 zhao Exp $
 * $Revision: 1.29 $
 * $Date: 2008/11/19 04:27:49 $
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;

import com.npower.dm.core.DDFTree;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ModelCharacter;
import com.npower.dm.core.ModelFamily;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.hibernate.entity.DDFTreeEntity;
import com.npower.dm.hibernate.entity.DMBootstrapProperty;
import com.npower.dm.hibernate.entity.DMBootstrapPropertyID;
import com.npower.dm.hibernate.entity.ManufacturerEntity;
import com.npower.dm.hibernate.entity.ModelCharacterEntity;
import com.npower.dm.hibernate.entity.ModelDDFTree;
import com.npower.dm.hibernate.entity.ModelDDFTreeID;
import com.npower.dm.hibernate.entity.ModelDMProperty;
import com.npower.dm.hibernate.entity.ModelDMPropertyID;
import com.npower.dm.hibernate.entity.ModelEntity;
import com.npower.dm.hibernate.entity.ModelFamilyEntity;
import com.npower.dm.hibernate.entity.ModelProfileMap;
import com.npower.dm.hibernate.entity.ModelProfileMapID;
import com.npower.dm.hibernate.entity.ModelTAC;
import com.npower.dm.hibernate.entity.ModelTACID;
import com.npower.dm.hibernate.entity.ProfileMappingEntity;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProfileMappingBean;
import com.npower.wurfl.CapabilityMatrix;
import com.npower.wurfl.ListManager;
import com.npower.wurfl.OTASDMWurflSource;
import com.npower.wurfl.ObjectsManager;
import com.npower.wurfl.UAManager;
import com.npower.wurfl.WurflDevice;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.29 $ $Date: 2008/11/19 04:27:49 $
 */
public class ModelManagementBeanImpl extends AbstractBean implements ModelBean {

  // private static Log log = LogFactory.getLog(ModelManagementBeanImpl.class);

  private ObjectsManager objectsManager;

  /**
   * 
   */
  protected ModelManagementBeanImpl() throws Exception {
    super();
    initialize();
  }

  /**
   * Constructor
   */
  ModelManagementBeanImpl(ManagementBeanFactory factory, Session hsession) throws Exception {
    super(factory, hsession);
    initialize();
  }

  /**
   * @throws IOException
   * @throws Exception
   */
  private void initialize() throws IOException, Exception {
    objectsManager = ObjectsManager.newInstance(new OTASDMWurflSource());
  }

  // Methods related with ManufacturerEntity
  // ***********************************************************************

  public Manufacturer newManufacturerInstance() throws DMException {
    return new ManufacturerEntity();
  }

  public Manufacturer newManufacturerInstance(String name, String manufacturerExternalId, String description)
      throws DMException {
    return new ManufacturerEntity(name, manufacturerExternalId, description);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#getManufacturerByID(java.lang.String)
   */
  public Manufacturer getManufacturerByID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }

    Session session = this.getHibernateSession();
    try {
      return (Manufacturer) session.load(ManufacturerEntity.class, new Long(id));
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#getManufacturerByExternalID(java.lang.String)
   */
  public Manufacturer getManufacturerByExternalID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(Manufacturer.class);
      criteria.add(Expression.ilike("externalId", id));
      List<Manufacturer> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (Manufacturer) list.get(0);
      } else {
        throw new DMException("Result is not unique, many Manufacturers have the same External ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#add(com.npower.dm.hibernate.ManufacturerEntity)
   */
  public void update(Manufacturer manufacturer) throws DMException {
    if (manufacturer == null) {
      throw new NullPointerException("Could not add a null manufacturer into database.");
    }
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      // Transaction tx = session.beginTransaction();
      session.save(manufacturer);
      // tx.commit();
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#delete(com.npower.dm.hibernate.ManufacturerEntity)
   */
  public void delete(Manufacturer manufacturer) throws DMException {
    Session session = this.getHibernateSession();
    try {
      Set<Model> models = manufacturer.getModels();
      for (Iterator<Model> i = models.iterator(); i.hasNext();) {
        this.delete((Model) i.next());
      }
      session.delete(manufacturer);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#getAllManufacturers()
   */
  public List<Manufacturer> getAllManufacturers() throws DMException {
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from ManufacturerEntity order by ID");
      List<Manufacturer> list = query.list();

      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#findManufacturers(java.lang.String)
   */
  public List<Manufacturer> findManufacturers(String whereClause) throws DMException {
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery(whereClause);
      List<Manufacturer> list = query.list();
      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  // Methods related with Models
  // ****************************************************************

  public Model newModelInstance() {
    return new ModelEntity();
  }

  public Model newModelInstance(Manufacturer manufacturer, String manufacturerModelId, String name,
      boolean supportedDownloadMethods, boolean isOmaEnabled, boolean isPlainProfile, boolean isUseEncForOmaMsg,
      boolean isUseNextNoncePerPkg) {

    return new ModelEntity(manufacturer, manufacturerModelId, name, supportedDownloadMethods, isOmaEnabled,
        isPlainProfile, isUseEncForOmaMsg, isUseNextNoncePerPkg);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#getModelByID(java.lang.String)
   */
  public Model getModelByID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }

    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(Model.class);
      criteria.add(Expression.eq("ID", new Long(id)));
      List<Model> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (Model) list.get(0);
      } else {
        throw new DMException("Result is not unique, many ModelEntity have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#getModelByManufacturerModelID(com.npower.dm.hibernate.ManufacturerEntity,
   *      java.lang.String)
   */
  public Model getModelByManufacturerModelID(Manufacturer manufacturer, String id) throws DMException {
    if (id == null || id.trim().length() == 0 || manufacturer == null) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(Model.class);
      Criteria manuCriteria = criteria.createCriteria("manufacturer");
      manuCriteria.add(Expression.eq("ID", new Long(manufacturer.getID())));
      criteria.add(Expression.ilike("manufacturerModelId", id));
      List<Model> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (Model) list.get(0);
      } else {
        throw new DMException("Result is not unique, many Models have the same External ID: " + id
            + " below manufacturer ID: " + manufacturer.getID());
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  public Model getModelByName(Manufacturer manufacturer, String name) throws DMException {
    if (name == null || name.trim().length() == 0 || manufacturer == null) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(Model.class);
      criteria.add(Expression.eq("manufacturer", manufacturer));
      criteria.add(Expression.eq("name", name));
      List<Model> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (Model) list.get(0);
      } else {
        throw new DMException("Result is not unique, many Models have the same name: " + name
            + " below manufacturer ID: " + manufacturer.getID());
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ModelBean#getModelByExternalId(java.lang.String, java.lang.String)
   */
  public Model getModelByExternalId(String manufacturerExtId, String modelExtId) throws DMException {
    if (StringUtils.isEmpty(manufacturerExtId) || StringUtils.isEmpty(modelExtId)) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(Model.class);
      criteria.createAlias("manufacturer", "manufacturer");
      criteria.add(Expression.ilike("manufacturer.externalId", manufacturerExtId, MatchMode.EXACT));
      criteria.add(Expression.or(Expression.ilike("manufacturerModelId", modelExtId, MatchMode.EXACT), 
                                 Expression.ilike("name", modelExtId, MatchMode.EXACT)));
      List<Model> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (Model) list.get(0);
      } else {
        throw new DMException("Result is not unique, many Models have the same name: " + modelExtId
            + " below manufacturer ID: " +manufacturerExtId);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#add(com.npower.dm.hibernate.ModelEntity)
   */
  public void update(Model model) throws DMException {
    if (model == null) {
      throw new NullPointerException("Could not add a null model into database.");
    }
    Manufacturer manufacturer = model.getManufacturer();
    if (manufacturer == null) {
      throw new DMException("add a model with a null manufacturer.");
    }
    Session session = this.getHibernateSession();
    try {
      // Link the model to manufactuer
      boolean isNew = (model.getID() == 0) ? true : false;
      if (isNew) {
        Set<Model> set = manufacturer.getModels();
        set.add(model);
      }
      ((ModelEntity)model).setLastUpdatedTime(new Date());
      session.saveOrUpdate(model);

    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
      if (session != null) {
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#getAllModel()
   */
  public List<Model> getAllModel() throws DMException {
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery("from ModelEntity order by ID");
      List<Model> list = query.list();

      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#getAllModel(com.npower.dm.hibernate.ManufacturerEntity)
   */
  public List<Model> getAllModel(Manufacturer manufacturer) throws DMException {
    if (manufacturer == null) {
      return new ArrayList<Model>(0);
    }
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(Model.class);
      Criteria manuCriteria = criteria.createCriteria("manufacturer");
      manuCriteria.add(Expression.eq("ID", new Long(manufacturer.getID())));
      List<Model> list = criteria.list();
      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#findModel(org.hibernate.Criteria)
   */
  public List<Model> findModel(Criteria criteria) throws DMException {
    try {
      List<Model> list = criteria.list();
      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ModelBean#findModelsByFamilyExtID(java.lang.String)
   */
  public List<Model> findModelsByFamilyExtID(String familyExtID) throws DMException {
    Session session = this.getHibernateSession();
    Criteria criteria = session.createCriteria(Model.class);
    criteria.add(Expression.eq("familyExternalID", familyExtID));
    return this.findModel(criteria);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#getModelbyTAC(java.lang.String)
   */
  public Model getModelbyTAC(String tacString) throws DMException {
    Model model = getModelByTAC(tacString, 8);
    if (model == null) {
       model = this.getModelByTAC(tacString, 6);
    }
    return model;
  }

  /**
   * 按长度匹配TAC信息.
   * @param tacString
   * @param length
   * @return
   * @throws DMException
   */
  private Model getModelByTAC(String tacString, int length) throws DMException {
    if (StringUtils.isEmpty(tacString)) {
      return null;
    }
    String tacInfo = tacString;
    if (tacString.toUpperCase().startsWith("IMEI:")) {
      tacInfo = tacString.substring(5, tacString.length());
    }
    tacInfo = tacInfo.substring(0, length);

    Session session = this.getHibernateSession();
    Criteria criteria = session.createCriteria(Model.class);
    Criteria tacCriteria = criteria.createCriteria("modelTACs");
    tacCriteria.add(Expression.eq("id.TAC", tacInfo));
    List<Model> list = this.findModel(criteria);
    if (list != null && list.size() > 0) {
      return (Model) list.get(0);
    } else {
      return null;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#delete(com.npower.dm.hibernate.ModelEntity)
   */
  public void delete(Model model) throws DMException {
    Session session = this.getHibernateSession();
    try {
      // delete reference to DDFTreeEntity
      /*
       * Set set = ((ModelEntity)model).getModelDDFTrees(); for (Iterator i =
       * set.iterator(); i.hasNext();) { this.dettachDDFTree(model,
       * ((ModelDDFTree) i.next()).getDdfTree().getID()); }
       */
      Set<DDFTree> trees = model.getDDFTrees();
      for (DDFTree tree : trees) {
        this.getHibernateSession().delete(tree);
      }
      session.delete(model);

    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#addTACInfo(com.npower.dm.hibernate.ModelEntity,
   *      java.lang.String)
   */
  public void addTACInfo(Model model, String tacInfo) throws DMException {
    if (tacInfo.toUpperCase().startsWith("IMEI:")) {
      tacInfo = tacInfo.substring(5, tacInfo.length());
    }
    if (StringUtils.isEmpty(tacInfo) || (tacInfo.length() != 8 && tacInfo.length() != 6)) {
      throw new DMException("Invalidate TAC info, tac: " + tacInfo);
    }
    tacInfo = (tacInfo.length() > 8) ? tacInfo.substring(0, 8) : tacInfo;

    Set<ModelTAC> tacSet = ((ModelEntity) model).getModelTACs();
    boolean exists = false;
    for (Iterator<ModelTAC> i = tacSet.iterator(); i.hasNext();) {
      ModelTAC tac = (ModelTAC) i.next();
      ModelTACID id = tac.getID();
      String modelTacInfo = id.getTAC();
      if (tacInfo.equals(modelTacInfo)) {
        exists = true;
        break;
      }
    }
    if (!exists) {
      ModelTACID id = new ModelTACID();
      id.setModelId(model.getID());
      id.setTAC(tacInfo);
      ModelTAC tac = new ModelTAC(id, model);
      this.getHibernateSession().save(tac);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.ModelBean#setTACInfos(com.npower.dm.core.Model,
   *      java.util.List)
   */
  public void setTACInfos(Model model, List<String> tacInfos) throws DMException {
    Set<ModelTAC> tacSet = ((ModelEntity) model).getModelTACs();
    if (tacSet != null) {
      for (Iterator<ModelTAC> i = tacSet.iterator(); i.hasNext();) {
        ModelTAC tac = (ModelTAC) i.next();
        this.getHibernateSession().delete(tac);
      }
    }
    ((ModelEntity) model).getModelTACs().clear();
    for (String tac : tacInfos) {
      this.addTACInfo(model, tac);
    }
  }

  /**
   * Store the properties into ModelEntity's DMBootstrap Properties. Old
   * properties will be replaced by the props.
   * 
   * @param props
   *            Properties；
   * @throws DMException
   */
  public void setDMBootstrapProperties(Model model, Properties props) throws DMException {
    Properties newProps = props;
    Set<DMBootstrapProperty> set = ((ModelEntity) model).getModelDMBootProps();
    Session session = this.getHibernateSession();
    if (!set.isEmpty()) {
      for (Iterator<DMBootstrapProperty> i = set.iterator(); i.hasNext();) {
        session.delete(i.next());
      }
    }
    set.clear();
    Enumeration<?> names = newProps.propertyNames();
    while (names.hasMoreElements()) {
      String name = (String) names.nextElement();
      String value = newProps.getProperty(name);
      DMBootstrapPropertyID id = new DMBootstrapPropertyID();
      id.setModelId(model.getID());
      id.setPropName(name);
      DMBootstrapProperty prop = new DMBootstrapProperty(id, model, value);
      session.save(prop);
      set.add(prop);

    }
  }

  /**
   * Store the properties into ModelEntity's DM Properties. Old properties will
   * be replaced by the props.
   * 
   * @param props
   *            Properties；
   * @throws DMException
   */
  public void setDMProperties(Model model, Properties props) throws DMException {
    Properties newProps = props;
    Set<ModelDMProperty> set = ((ModelEntity) model).getModelDMProps();
    Session session = this.getHibernateSession();
    if (!set.isEmpty()) {
      for (Iterator<ModelDMProperty> i = set.iterator(); i.hasNext();) {
        session.delete(i.next());
      }
    }
    set.clear();
    Enumeration<?> names = newProps.propertyNames();
    while (names.hasMoreElements()) {
      String name = (String) names.nextElement();
      String value = newProps.getProperty(name);
      ModelDMPropertyID id = new ModelDMPropertyID();
      id.setModelId(model.getID());
      id.setPropName(name);
      ModelDMProperty prop = new ModelDMProperty(id, model, value);
      session.save(prop);
      set.add(prop);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#attachDDFTree(com.npower.dm.hibernate.ModelEntity,
   *      long)
   */
  public void attachDDFTree(Model model, long ddfTreeID) throws DMException {

    // Check treeID in DM Inventory
    DDFTreeBean bean = this.getManagementBeanFactory().createDDFTreeBean();
    DDFTree tree;
    try {
      tree = bean.getDDFTreeByID(ddfTreeID + "");
    } catch (DMException e) {
      throw new DMException("Failure in load DDFTreeEntity by treeID: " + ddfTreeID + " from DM inventory.", e);
    }
    if (tree == null) {
      throw new DMException("Could not found the DDFTreeEntity by treeID: " + ddfTreeID);
    }

    try {
      ModelDDFTreeID id = new ModelDDFTreeID();
      id.setDdfTreeId(ddfTreeID);
      id.setDeviceModelId(model.getID());

      ModelDDFTree modelDDFTree = new ModelDDFTree();
      modelDDFTree.setID(id);
      modelDDFTree.setDdfTree(tree);
      modelDDFTree.setModel(model);

      Set<ModelDDFTree> set = ((ModelEntity) model).getModelDDFTrees();
      if (!set.contains(modelDDFTree)) {
        // Link ModelDDFTree with DDFTree
        set.add(modelDDFTree);

        // Link ModelDDFTree with Model
        ((DDFTreeEntity) tree).getModelDDFTrees().add(modelDDFTree);

        // Save into DM Inventory
        this.getHibernateSession().save(modelDDFTree);

      }
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.ModelBean#dettachDDFTree(com.npower.dm.hibernate.ModelEntity,
   *      long)
   */
  public void dettachDDFTree(Model model, long ddfTreeID) throws DMException {
    // Check treeID in DM Inventory
    DDFTreeBean bean = this.getManagementBeanFactory().createDDFTreeBean();
    DDFTree tree;
    try {
      tree = bean.getDDFTreeByID(ddfTreeID + "");
    } catch (DMException e) {
      throw new DMException("Failure in load DDFTreeEntity by treeID: " + ddfTreeID + " from DM inventory.", e);
    }
    if (tree == null) {
      throw new DMException("Could not found the DDFTreeEntity by treeID: " + ddfTreeID);
    }

    try {
      ModelDDFTreeID id = new ModelDDFTreeID();
      id.setDdfTreeId(ddfTreeID);
      id.setDeviceModelId(model.getID());

      ModelDDFTree modelDDFTree = new ModelDDFTree();
      modelDDFTree.setID(id);
      modelDDFTree.setDdfTree(tree);
      modelDDFTree.setModel(model);

      // Remove from the DDFTreeEntity related
      Set<ModelDDFTree> set = ((ModelEntity) model).getModelDDFTrees();
      Object[] mts = set.toArray();
      if (set.contains(modelDDFTree)) {
        set.remove(modelDDFTree);
      }

      // Remove from the DDFTreeEntity related
      set = ((DDFTreeEntity) tree).getModelDDFTrees();
      if (set.contains(modelDDFTree)) {
        set.remove(modelDDFTree);
      }

      // Remove the ModelDDFTree from DM inventory
      for (int i = 0; i < mts.length; i++) {
        ModelDDFTree m = (ModelDDFTree) mts[i];
        if (m.equals(modelDDFTree)) {
          DDFTree ddfTree = m.getDdfTree();
          DDFTreeBean ddfBean = this.getManagementBeanFactory().createDDFTreeBean();
          ddfBean.delete(ddfTree);
          this.getHibernateSession().delete(mts[i]);
        }
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /**
   * <pre>
   * Attache a ProfileMappingEntity to ModelEntity. 
   * Notes:
   * 1. This method will dettach all of old profile mapping and delete all of old profile mapping.
   * 2. The ProfileMappingEntity must save into DM inventory before call this method.
   * 
   * </pre>
   * 
   * @param model
   * @param profileMappingID
   * @throws DMException
   * @see com.npower.dm.management.hb.ModelBean#attachProfileMapping(ModelEntity
   *      model, long profileMappingID)
   */
  public void attachProfileMapping(Model model, long profileMappingID) throws DMException {
    // Check ProfileMappingEntity in DM Inventory
    ProfileMappingBean bean = this.getManagementBeanFactory().createProfileMappingBean();
    ProfileMappingEntity mapping;
    try {
      mapping = (ProfileMappingEntity) bean.getProfileMappingByID(profileMappingID);
    } catch (DMException e) {
      throw new DMException("Failure in load ProfileMappingEntity by ID: " + profileMappingID + " from DM inventory.",
          e);
    }
    if (mapping == null) {
      throw new DMException("Could not found the ProfileMappingEntity by ID: " + profileMappingID);
    }

    try {
      // Clear exists ProfileMapping
      ProfileMapping another = model.getProfileMap(mapping.getProfileTemplate());
      while (another != null) {
        this.dettachProfileMapping(model, another.getID());
        bean.delete(another);
        another = model.getProfileMap(mapping.getProfileTemplate());
      }

      // Add new profile mapping.
      ModelProfileMapID id = new ModelProfileMapID();
      id.setDeviceModelId(model.getID());
      id.setProfileMappingId(profileMappingID);

      ModelProfileMap modelMapping = new ModelProfileMap();
      modelMapping.setID(id);
      modelMapping.setProfileMapping(mapping);
      modelMapping.setModel(model);

      Set<ModelProfileMap> set = ((ModelEntity) model).getModelProfileMaps();
      if (!set.contains(modelMapping)) {
        // Link ModelProfileMapping with ModelEntity
        set.add(modelMapping);

        // Link ModelProfileMapping with ProfileMappingEntity
        mapping.getModelProfileMaps().add(modelMapping);

        // Save into DM Inventory
        this.getHibernateSession().save(modelMapping);

      }
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /**
   * Detach a ProfileMappingEntity from ModelEntity. The method doesn't delete
   * the ProfileMappingEntity from DM inventory.
   * 
   * @param model
   * @param profileMappingID
   * @throws DMException
   * @see com.npower.dm.management.hb.ModelBean#dettachProfileMapping(ModelEntity
   *      model, long profileMappingID)
   */
  public void dettachProfileMapping(Model model, long profileMappingID) throws DMException {
    // Check treeID in DM Inventory
    ProfileMappingBean bean = this.getManagementBeanFactory().createProfileMappingBean();
    ProfileMappingEntity mapping;
    try {
      mapping = (ProfileMappingEntity) bean.getProfileMappingByID(profileMappingID);
    } catch (DMException e) {
      throw new DMException("Failure in load ProfileMappingEntity by ID: " + profileMappingID + " from DM inventory.",
          e);
    }
    if (mapping == null) {
      throw new DMException("Could not found the ProfileMappingEntity by ID: " + profileMappingID);
    }

    try {
      ModelProfileMapID id = new ModelProfileMapID();
      id.setDeviceModelId(model.getID());
      id.setProfileMappingId(profileMappingID);

      ModelProfileMap modelMapping = new ModelProfileMap();
      modelMapping.setID(id);
      modelMapping.setProfileMapping(mapping);
      modelMapping.setModel(model);

      // Remove from the ModelEntity related
      Set<ModelProfileMap> set = ((ModelEntity) model).getModelProfileMaps();
      Object[] mts = set.toArray();

      if (set.contains(modelMapping)) {
        set.remove(modelMapping);
      }

      // Remove from the ProfileMappingEntity related
      set = mapping.getModelProfileMaps();
      if (set.contains(modelMapping)) {
        set.remove(modelMapping);
      }

      // Remove the ModelDDFTree from DM inventory
      for (int i = 0; i < mts.length; i++) {
        ModelProfileMap m = (ModelProfileMap) mts[i];
        if (m.equals(modelMapping)) {
          this.getHibernateSession().delete(mts[i]);
        }
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    }
  }

  /**
   * 
   * <pre>
   * Import the TAC list of models form file.
   * &lt;pre&gt;
   * 
   * @param xmlFile
   * @throws DMException
   * 
   */
  public void importModelTAC(File xmlFile) throws DMException {

    try {

      ManagementBeanFactory factory = this.getManagementBeanFactory();
      ModelBean modelBean = factory.createModelBean();
      factory.beginTransaction();

      SAXReader reader = new SAXReader();
      Document confDoc = reader.read(xmlFile);
      Element root = confDoc.getRootElement();

      for (Iterator<Element> i = root.elementIterator("Manufacturer"); i.hasNext();) {

        String manufacturerExternalID = "";
        Element manElement = i.next();
        manufacturerExternalID = manElement.elementText("ExternalID");
        Manufacturer manufacturerBean = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        if (manufacturerBean == null) {
          System.err.println("Could not find this manufacturer: " + manufacturerExternalID);
          continue;
        }

        for (Iterator<Element> j = manElement.elementIterator("Model"); j.hasNext();) {
          String modelExternalID = "";
          Element modelElement = j.next();
          modelExternalID = modelElement.elementText("ExternalID");
          Model model = modelBean.getModelByManufacturerModelID(manufacturerBean, modelExternalID);
          if (model == null) {
            System.err.println("Could not find this model: " + modelExternalID);
            continue;
          }

          List<Element> TACsList = modelElement.elements("TACs");
          List<String> TACInfos = new ArrayList<String>();
          for (int k = 0; k < TACsList.size(); k++) {
            List<Element> TACList = ((TACsList.get(k))).elements("TAC");
            for (int m = 0; m < TACList.size(); m++) {
              Element tacElement = TACList.get(m);
              String tac = tacElement.getText();
              if (StringUtils.isNotEmpty(tac) && tac.length() >= 8) {
                TACInfos.add(tac);
              }
            }

          }
          modelBean.update(model);
          modelBean.setTACInfos(model, TACInfos);
        }
      }
      factory.commit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  /**
   * 
   * <pre>
   * Export the TAC form database by Model.
   * &lt;pre&gt;
   * 
   * @param model
   * @return
   * @throws DMException
   * 
   */
  public void exportModelTAC(Model model, String outFile) throws DMException {

    Document document = org.dom4j.DocumentHelper.createDocument();
    Element rootElement = document.addElement("Manufacturers");
    Element manElement = rootElement.addElement("Manufacturer");
    Element manNameElement = manElement.addElement("Name");
    manNameElement.setText(model.getManufacturer().getName());
    Element manexterIDElement = manElement.addElement("ExternalID");
    manexterIDElement.setText(model.getManufacturer().getExternalId());
    Element modelElement = manElement.addElement("Model");
    Element modelNameElement = modelElement.addElement("Name");
    modelNameElement.setText(model.getName());
    Element modelexterIDElement = modelElement.addElement("ExternalID");
    modelexterIDElement.setText(model.getManufacturerModelId());
    Element TACSElement = modelElement.addElement("TACS");

    Set<String> tacSet = model.getModelTAC();
    for (String tac : tacSet) {
      Element TACElement = TACSElement.addElement("TAC");
      TACElement.setText(tac);
    }

    try {
      XMLWriter writer = new XMLWriter(new FileWriter(new File(outFile)));
      writer.write(document);
      writer.close();
      this.formatXMLFile(outFile);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

  }

  /**
   * 
   * <pre>
   * Export the TAC form database by Model.
   * &lt;pre&gt;
   * 
   * @param manufacturer
   * @return
   * @throws DMException
   * 
   */
  public void exportModelTAC(Manufacturer manufacturer, String outFile) throws DMException {

    Set<Model> modelSet = manufacturer.getModels();

    Document document = org.dom4j.DocumentHelper.createDocument();
    Element rootElement = document.addElement("Manufacturers");
    Element manElement = rootElement.addElement("Manufacturer");
    Element manNameElement = manElement.addElement("Name");
    manNameElement.setText(manufacturer.getName());
    Element manexterIDElement = manElement.addElement("ExternalID");
    manexterIDElement.setText(manufacturer.getExternalId());

    for (Model model : modelSet) {

      Set<String> tacSet = model.getModelTAC();
      if (tacSet.size() > 0) {
        Element modelElement = manElement.addElement("Model");
        Element modelNameElement = modelElement.addElement("Name");
        modelNameElement.setText(model.getName());
        Element modelexterIDElement = modelElement.addElement("ExternalID");
        modelexterIDElement.setText(model.getManufacturerModelId());
        Element TACSElement = modelElement.addElement("TACS");

        for (String tac : tacSet) {
          Element TACElement = TACSElement.addElement("TAC");
          TACElement.setText(tac);
        }
      } else {
        continue;
      }
    }

    try {

      XMLWriter writer = new XMLWriter(new FileWriter(new File(outFile)));
      writer.write(document);
      writer.close();
      this.formatXMLFile(outFile);

    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

  }

  /**
   * 
   * @param filename
   * @return
   */
  public int formatXMLFile(String filename) {

    int returnValue = 0;

    try {

      SAXReader saxReader = new SAXReader();
      Document document = saxReader.read(new File(filename));
      XMLWriter writer = null;
      OutputFormat format = OutputFormat.createPrettyPrint();
      format.setEncoding("UTF-8");
      writer = new XMLWriter(new FileWriter(new File(filename)), format);
      writer.write(document);
      writer.close();
      returnValue = 1;

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return returnValue;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ModelBean#getAllModelFamily()
   */
  public List<ModelFamily> getAllModelFamily() throws DMException {
    Session session = this.getHibernateSession();
    try {
        Query query = session.createQuery("from ModelFamilyEntity order by externalID");
        List<ModelFamily> list = query.list();
  
        return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.ModelBean#getModelFamilyByExtID(java.lang.String)
   */
  public ModelFamily getModelFamilyByExtID(String extID) throws DMException {
    if (StringUtils.isEmpty(extID)) {
      return null;
    }
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(ModelFamilyEntity.class);
      criteria.add(Expression.ilike("externalID", extID));
      List<ModelFamily> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }

      if (list.size() == 1) {
        return (ModelFamily) list.get(0);
      } else {
        throw new DMException("Result is not unique, many ModelFamily have the same External ID: " + extID);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  public void delete(ModelCharacter character) throws DMException {
    Session session = this.getHibernateSession();
    try {
        session.delete(character);
        Set<ModelCharacter> set = character.getModel().getCharacters();
        set.remove(character);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
      if (session != null) {
      }
    }
  }

  public List<ModelCharacter> findModelCharacters(Model model, String category, String name) throws DMException {
    Session session = this.getHibernateSession();
    try {
        Criteria criteria = session.createCriteria(ModelCharacterEntity.class);
        criteria.add(Expression.eq("model", model));
        if (StringUtils.isEmpty(category)) {
           criteria.add(Expression.isNull("category"));
        } else {
          criteria.add(Expression.eq("category", category));
        }
        criteria.add(Expression.eq("name", name));
        List<ModelCharacter> list = criteria.list();
        return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  public Map<String, String> getCapabilityMatrix(Model model) throws Exception {
    String manufacturer = model.getManufacturer().getExternalId();
    String modelExtID = model.getManufacturerModelId();

    ListManager lm = objectsManager.getListManagerInstance();
    WurflDevice foundDevice = lm.getDeviceByBrand(manufacturer, modelExtID);
    if (foundDevice != null) {
       return lm.getCapabilitiesForDeviceID(foundDevice.getId());
    } else {
      return new HashMap<String, String>();
    }
  }

  public String getCapability(Model model, String capability) throws Exception {
    String manufacturer = model.getManufacturer().getExternalId();
    String modelExtID = model.getManufacturerModelId();

    ListManager lm = objectsManager.getListManagerInstance();
    WurflDevice foundDevice = lm.getDeviceByBrand(manufacturer, modelExtID);
    if (foundDevice != null) {
       return objectsManager.getCapabilityMatrixInstance().getCapabilityForDevice(foundDevice.getId(), capability);
    } else {
      return null;
    }
  }

  public ModelCharacter getModelCharacterByID(long id) throws DMException {
    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(ModelCharacterEntity.class);
      criteria.add(Expression.eq("ID", id));
      List<ModelCharacter> list = criteria.list();

      if (list.size() == 0) {
        return null;
      }
      if (list.size() == 1) {
        return (ModelCharacter) list.get(0);
      } else {
        throw new DMException("Result is not unique, many ModelCharacter have the same ID: " + id);
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  public Model getModelFromUA(String ua) throws DMException {
    if (StringUtils.isEmpty(ua)) {
       return null;
    }
    UAManager um = objectsManager.getUAManagerInstance();
    //String deviceID = um.getDeviceIDFromUA(ua);
    String deviceID = um.getDeviceIDFromUALoose(ua);
    ListManager lm = objectsManager.getListManagerInstance();
    WurflDevice foundDevice = lm.getDeviceElementsList().get(deviceID);
    CapabilityMatrix cm = objectsManager.getCapabilityMatrixInstance();
    if (foundDevice != null) {
       String modelName = cm.getCapabilityForDevice(deviceID, "model_name");
       String brand = cm.getCapabilityForDevice(deviceID, "brand_name");
       if (StringUtils.isNotEmpty(brand) && StringUtils.isNotEmpty(modelName)) {
          ModelBean modelBean = this.getManagementBeanFactory().createModelBean();
          Manufacturer manufacturer = modelBean.getManufacturerByExternalID(brand);
          if (manufacturer != null) {
             Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelName);
             return model;
          }
       }
    }
    return null;
  }

  public ModelCharacter newModelCharacterInstance(Model model, String category, String name) throws DMException {
    ModelCharacterEntity entity = new ModelCharacterEntity();
    entity.setCategory(category);
    entity.setName(name);
    entity.setModel(model);
    return entity;
  }

  public void update(ModelCharacter character) throws DMException {
    if (character == null) {
      throw new NullPointerException("Could not add a null ModelCharacter into database.");
    }
    Session session = this.getHibernateSession();
    try {
        session.saveOrUpdate(character);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
      if (session != null) {
      }
    }
  }

}
