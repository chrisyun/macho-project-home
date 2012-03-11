/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/DeviceManagementBeanImpl.java,v 1.33 2009/02/23 07:26:11 zhao Exp $
 * $Revision: 1.33 $
 * $Date: 2009/02/23 07:26:11 $
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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import sync4j.framework.engine.dm.TreeNode;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.core.DeviceTree;
import com.npower.dm.core.DeviceTreeNode;
import com.npower.dm.core.Image;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ServiceProvider;
import com.npower.dm.core.Subscriber;
import com.npower.dm.hibernate.entity.Device4DeviceGroup;
import com.npower.dm.hibernate.entity.Device4DeviceGroupID;
import com.npower.dm.hibernate.entity.DeviceEntity;
import com.npower.dm.hibernate.entity.DeviceGroupEntity;
import com.npower.dm.hibernate.entity.DeviceTreeEntity;
import com.npower.dm.hibernate.entity.DeviceTreeNodeEntity;
import com.npower.dm.management.BaseBean;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.SubscriberBean;
import com.npower.dm.util.IMEIUtil;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.33 $ $Date: 2009/02/23 07:26:11 $
 */
public class DeviceManagementBeanImpl extends AbstractBean implements BaseBean, DeviceBean {

  private static Log log = LogFactory.getLog(DeviceManagementBeanImpl.class);
  /**
   * 
   */
  protected DeviceManagementBeanImpl() {
    super();
  }

  /**
   * @param factory
   * @param hsession
   */
  DeviceManagementBeanImpl(ManagementBeanFactory factory, Session hsession) {
    super(factory, hsession);
  }
  
  // Private methods **************************************************************
  /**
   * Find node in the set of nodes by the node path and set the value into it.
   * This method will create missing node with a null value.
   * 
   * For example:
   *   node and children:
   *     ABC-- A -- A11 -- A111
   *           B -- B11 -- B111
   *           C -- C11 
   *        
   *    NodePath: C/C11/C111
   *    
   *    will create C111 node and return C111 node.
   * 
   * 
   * @param nodes
   * @param nodePath
   * @return
   * @throws HibernateException
   * @throws DMException
   */
  private DeviceTreeNode findorCreateDeviceTreeNode(Session session, DeviceTreeNode parentNode, String nodePath) throws HibernateException, DMException {
    // erase the "/" prefix
    if (nodePath.startsWith("/")) {
       nodePath = nodePath.substring(1, nodePath.length());
    }
    Set<DeviceTreeNode> nodes = parentNode.getChildren();
    for (Iterator<DeviceTreeNode> i = nodes.iterator(); i.hasNext(); ) {
        DeviceTreeNode node = i.next();
        if (nodePath.equals(node.getName())) {
           return node;
        }
        if (nodePath.startsWith(node.getName() + "/")) {
           return this.findorCreateDeviceTreeNode(session, node, nodePath.substring(nodePath.indexOf("/"), nodePath.length()));
        }
    }
    int index = nodePath.indexOf("/");
    if (index < 0) {
       String nodeName = nodePath;
       DeviceTreeNodeEntity node = new DeviceTreeNodeEntity(nodeName, parentNode, null);
       parentNode.getChildren().add(node);
       session.saveOrUpdate(node);
       return node;
    } else {
      String nodeName = nodePath.substring(0, index);
      DeviceTreeNodeEntity node = new DeviceTreeNodeEntity(nodeName, parentNode, null);
      parentNode.getChildren().add(node);
      session.saveOrUpdate(node);
      return this.findorCreateDeviceTreeNode(session, node, nodePath.substring(index, nodePath.length()));
    }
  }

  /**
   * Find node in the set of nodes by the node path
   * This method will return null, if the node none-exists.
   * 
   * For example:
   *   node and children:
   *     ABC-- A -- A11 -- A111
   *           B -- B11 -- B111
   *           C -- C11 
   *        
   *    NodePath: C/C11/C111
   *    
   *    will return C111 null.
   * 
   * 
   * @param nodes
   * @param nodePath
   * @return
   * @throws HibernateException
   * @throws DMException
   */
  private DeviceTreeNode findDeviceTreeNode(Session session, DeviceTreeNode parentNode, String nodePath) throws HibernateException, DMException {
    // erase the "/" prefix
    if (nodePath.startsWith("/")) {
       nodePath = nodePath.substring(1, nodePath.length());
    }
    Set<DeviceTreeNode> nodes = parentNode.getChildren();
    for (Iterator<DeviceTreeNode> i = nodes.iterator(); i.hasNext(); ) {
        DeviceTreeNode node = i.next();
        if (nodePath.equals(node.getName())) {
           return node;
        }
        if (nodePath.startsWith(node.getName() + "/")) {
           return this.findDeviceTreeNode(session, node, nodePath.substring(nodePath.indexOf("/"), nodePath.length()));
        }
    }
    return null;
  }

  // Implements interface DeviceBean Methods
  // ******************************************************************************

  /**
   * Create a Device instance
   */
  public Device newDeviceInstance() throws DMException {
    return new DeviceEntity();
  }

  /**
   * Create a Device instance
   */
  public Device newDeviceInstance(Subscriber subscriber, Model model, String externalID) throws DMException {
    return new DeviceEntity(subscriber, model, externalID);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.DeviceBean#update(com.npower.dm.hibernate.DeviceEntity)
   */
  public void update(Device device) throws DMException {
    if (device == null) {
      throw new NullPointerException("Could not add or update a null device into DM inventory.");
    }
    Session session = this.getHibernateSession();
    try {
      ((DeviceEntity)device).setLastUpdatedTime(new Date());
      session.saveOrUpdate(device);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.DeviceBean#getDeviceByID(java.lang.String)
   */
  public Device getDeviceByID(String id) throws DMException {
    if (StringUtils.isEmpty(id)) {
       return null;
    }
    return this.getDeviceByID(Long.parseLong(id));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#getDeviceByID(long)
   */
  public Device getDeviceByID(long id) throws DMException {
    if (id == 0) {
      return null;
    }

    Session session = this.getHibernateSession();
    try {
      return (Device) session.get(DeviceEntity.class, new Long(id));
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.DeviceBean#getDeviceByExternalID(java.lang.String)
   */
  public Device getDeviceByExternalID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }

    Session session = this.getHibernateSession();
    try {
      Criteria criteria = session.createCriteria(Device.class);
      criteria.add(Expression.eq("externalId", id));
      List<Device> list = criteria.list();
      if (list.size() > 0) {
        return (Device) list.get(0);
      } else {
        return null;
      }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.DeviceBean#delete(com.npower.dm.hibernate.DeviceEntity)
   */
  public void delete(Device device) throws DMException {
    Session session = this.getHibernateSession();
    try {
        // Delete the DeviceTree boundled with this device.
        DeviceTree tree = device.getDeviceTree();
        if (tree != null && tree.getID() > 0) {
           this.delete(tree);
        }
        device.setDeviceTree(null);
        session.delete(device);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.DeviceBean#findDevice(java.lang.String)
   */
  public List<Device> findDevice(String whereClause) throws DMException {
    Session session = this.getHibernateSession();
    try {
      // session = HibernateSessionFactory.currentSession();
      Query query = session.createQuery(whereClause);
      List<Device> list = query.list();
      return list;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }

  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#bind(java.lang.String, java.lang.String)
   */
  public Device bind(String deviceExternalID, String msisdn) throws DMException {
    return this.bindVersion3(deviceExternalID, msisdn, false);
  }
  
  public Device bind(String deviceExternalID, String msisdn, boolean creation) throws DMException {
    return this.bindVersion3(deviceExternalID, msisdn, creation);
  }
  
  private Device bindVersion3(String imei, String msisdn, boolean creation) throws DMException {
    if (StringUtils.isEmpty(msisdn) || StringUtils.isEmpty(imei)) {
       return null;
    }
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    SubscriberBean subscriberBean = factory.createSubcriberBean();
    
    Device device = this.getDeviceByExternalID(imei);
    Subscriber subscriber = subscriberBean.getSubscriberByPhoneNumber(msisdn);
    if (!creation && device == null && subscriber == null) {
       // Case#5: 均不存在
       return null;
    }
    if (subscriber == null) {
       CarrierBean carrierBean = factory.createCarrierBean();
       Carrier carrier = carrierBean.findCarrierByPhoneNumberPolicy(msisdn);
       if (carrier == null) {
          throw new DMException("Could not found carrier by phone number policy for msisdn: " + msisdn);
       }
       subscriber = subscriberBean.newSubscriberInstance(carrier, msisdn, msisdn, msisdn);
    }
    
    if (device == null) {
       ModelBean modelBean = factory.createModelBean();
       Model model = modelBean.getModelbyTAC(imei);
       if (model == null) {
          throw new DMException("Could not found model by TAC info for IMEI: " + imei);
       }
       device = this.newDeviceInstance(subscriber, model, imei);
    }
    
    if (!subscriber.getDevices().contains(device)) {
       device.setSubscriber(subscriber);
       device.setSubscriberCarrierName(subscriber.getCarrier().getExternalID());
       device.setSubscriberPhoneNumber(subscriber.getPhoneNumber());
    }
    
    subscriberBean.update(subscriber);
    this.update(device);
    return device;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#bind(java.lang.String, java.lang.String)
   */
  private Device bindVersion2(String deviceExternalID, String msisdn) throws DMException {
    if (StringUtils.isEmpty(msisdn) || StringUtils.isEmpty(deviceExternalID)) {
       return null;
    }
    String newExternalID = deviceExternalID;
    if (!deviceExternalID.toUpperCase().startsWith("IMEI:")) {
       newExternalID = "IMEI:" + deviceExternalID;
    }
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    ModelBean modelBean = factory.createModelBean();
    SubscriberBean subscriberBean = factory.createSubcriberBean();
    
    Device device4NewExternalID = this.getDeviceByExternalID(newExternalID);

    List<Device> devices = this.findDevice("from DeviceEntity where subscriberPhoneNumber like '%" + msisdn + "%'");
    Device device4PhoneNumer = null;
    if (devices != null && devices.size() > 0) {
       device4PhoneNumer = (Device) devices.get(0);
    }
    
    if (device4NewExternalID != null && device4PhoneNumer != null) {
       if (device4NewExternalID.getSubscriberPhoneNumber().equals(msisdn)) {
          // Case#1: 设备存在,无变化,不做任何操作 
          // matched, no changed, nothing to do.
          return device4NewExternalID;
       } else {
         // Case#2: msisdn和ExternalID对应与不同的设备，均存在
         Carrier carrier = device4PhoneNumer.getSubscriber().getCarrier();
         Model model = device4NewExternalID.getModel();
         Subscriber subscriber4PhoneNumer = device4PhoneNumer.getSubscriber();
         this.delete(device4NewExternalID);
         this.delete(device4PhoneNumer);
         
         subscriberBean.delete(device4NewExternalID.getSubscriber());
         subscriberBean.delete(device4PhoneNumer.getSubscriber());
         
         // Create this device
         // Create a subscriber
         Subscriber subscriber = subscriberBean.getSubscriberByExternalID(msisdn);
         if (subscriber != null) {
            subscriberBean.delete(subscriber);
         }
         subscriber = subscriberBean.getSubscriberByPhoneNumber(msisdn);
         if (subscriber != null) {
            subscriberBean.delete(subscriber);
         }
         subscriber = subscriberBean.newSubscriberInstance(carrier, msisdn, msisdn, subscriber4PhoneNumer.getPassword());
         subscriberBean.update(subscriber);
         
         // Copy and Create a device.
         Device device = this.newDeviceInstance(subscriber, model, newExternalID);
         device.setOMAClientAuthScheme(device4NewExternalID.getOMAClientAuthScheme());
         device.setOMAClientUsername(device4NewExternalID.getOMAClientUsername());
         device.setOMAClientPassword(device4NewExternalID.getOMAClientPassword());
         device.setOMAServerPassword(device4NewExternalID.getOMAServerPassword());
         device.setIsActivated(true);
         device.setSubscriberPhoneNumber(msisdn);
         this.update(device);
         return device;
       }
    } else if (device4NewExternalID != null) {
      // Case#3: ExternalID 对应的设备存在,但msisdn发生变化,修改存在设备的msisdn
      device4NewExternalID.setSubscriberPhoneNumber(msisdn);
      Subscriber subscriber = device4NewExternalID.getSubscriber();
      
      String oldPhoneNumber = subscriber.getPhoneNumber();
      if (StringUtils.isNotEmpty(oldPhoneNumber) && StringUtils.isNotEmpty(msisdn)
            && !oldPhoneNumber.equals(msisdn)) {
         // PhoneNumber had been changed, relink with new carrier
         CarrierBean carrierBean = factory.createCarrierBean();
         Carrier carrier = carrierBean.findCarrierByPhoneNumberPolicy(msisdn);
         if (carrier != null && carrier.getID() != subscriber.getCarrier().getID()) {
            // Carrier changed!
            subscriber.setCarrier(carrier);
         }
      }
      subscriber.setPhoneNumber(msisdn);
      
      subscriberBean.update(subscriber);
      this.update(device4NewExternalID);
      
      return device4NewExternalID;
    } else if (device4PhoneNumer != null) { 
      // Case#4: msisdn 对应的设备存在, 但该设备的externalID发生变化, 修改存在设备的externalID
      device4PhoneNumer.setExternalId(newExternalID);
      Model model = modelBean.getModelbyTAC(deviceExternalID);
      if (model == null) {
         throw new DMException("Could not found matched model by TAC: " + deviceExternalID);
      }
      device4PhoneNumer.setModel(model);
      // clear device Tree
      if (device4PhoneNumer.getDeviceTree() != null) {
        this.delete(device4PhoneNumer.getDeviceTree());
      }
      this.update(device4PhoneNumer);
      
      return device4PhoneNumer;
    } else {
      // Case#5: 均不存在
      return null;
    }
  }


  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#newDeviceGroup()
   */
  public DeviceGroup newDeviceGroup() throws DMException {
    DeviceGroup group = new DeviceGroupEntity();
    return group;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#getDeviceGroupByID()
   */
  public DeviceGroup getDeviceGroupByID(String id) throws DMException {
    if (id == null || id.trim().length() == 0) {
      return null;
    }

    Session session = this.getHibernateSession();
    try {
        return (DeviceGroup) session.get(DeviceGroupEntity.class, new Long(id));
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#delete(com.npower.dm.core.DeviceGroup)
   */
  public void delete(DeviceGroup group) throws DMException {
    Session session = this.getHibernateSession();
    try {
        session.delete(group);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#update(com.npower.dm.core.DeviceGroup)
   */
  public void update(DeviceGroup group) throws DMException {
    Session session = this.getHibernateSession();
    try {
        session.saveOrUpdate(group);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }
  
  /**
   * Add a device into this DeviceGroup.
   * 
   * @param group
   * @param device
   * @throws DMException
   */
  public void add(DeviceGroup group, Device device) throws DMException {
    if (device == null) {
       throw new DMException("Could not insert a NULL device into DeviceGroup");
    }
    if (device.getID() == 0) {
       throw new DMException("The device is un-saved, please save the device into DMinventory, before add into DeviceGroup!");
    }
    Session session = null;
    try {
        session = this.getHibernateSession();
        if (group.getID() == 0) {
           // If this unsaved, saveOrUpdate first. make sure the DeviceGroup's ID was generated!
           session.saveOrUpdate(group);
        }
        Device4DeviceGroupID id = new Device4DeviceGroupID();
        id.setDeviceGroupId(group.getID());
        id.setDeviceId(device.getID());
        Device4DeviceGroup device4DeviceGroup = new Device4DeviceGroup(id, group, device);
        session.saveOrUpdate(device4DeviceGroup);
        
        // Link the device4DeviceGroup  with this device group.
        ((DeviceGroupEntity)group).getDevicesForDeviceGroup().add(device4DeviceGroup);
        
        // Link the device4DeviceGroup to device
        ((DeviceEntity)device).getDeviceGroupDevices().add(device4DeviceGroup);
        
    } catch (HibernateException ex) {
      throw new DMException(ex);
    }
  }

  /**
   * Remove a device from this DeviceGroup.
   * 
   * @param group
   * @param device
   * @throws DMException
   */
  public void remove(DeviceGroup group, Device device) throws DMException {
    if (device == null) {
      throw new DMException("Could not delete a NULL device from DeviceGroup");
   }
   if (device.getID() == 0) {
      throw new DMException("The device is un-saved, please save the device into DMinventory, before delet it from DeviceGroup!");
   }
   Session session = null;
   try {
       session = this.getHibernateSession();
       if (group.getID() == 0) {
          // If this unsaved, saveOrUpdate first. make sure the DeviceGroup's ID was generated!
          session.saveOrUpdate(group);
       }
       Device4DeviceGroupID id = new Device4DeviceGroupID();
       id.setDeviceGroupId(group.getID());
       id.setDeviceId(device.getID());
       Set<Device4DeviceGroup> set = ((DeviceGroupEntity)group).getDevicesForDeviceGroup();
       for (Iterator<Device4DeviceGroup> i = set.iterator(); i.hasNext(); ) {
           Device4DeviceGroup device4DeviceGroup = (Device4DeviceGroup)i.next();
           if (id.equals(device4DeviceGroup.getID())) {
              // Remove from DM inventory.
              session.delete(device4DeviceGroup);
              // Remove the link the device4DeviceGroup with this device group.
              set.remove(device4DeviceGroup);
              break;
           }
       }
       
   } catch (HibernateException ex) {
     throw new DMException(ex);
   }
  }

  /**
   * Delete a DeviceTree.
   * 
   * All deviceTreeID of Device reference this DeviceTree will be set a NULL.
   * 
   * @param tree
   */
  public void delete(DeviceTree tree) throws DMException {
    Session session = this.getHibernateSession();
    try {
        Set<DeviceTreeEntity> devices = ((DeviceTreeEntity)tree).getDevices();
        for (Iterator<DeviceTreeEntity> i = devices.iterator(); i.hasNext(); ) {
            Device device = (Device)i.next();
            device.setDeviceTree(null);
        }
        // Delete Root Node
        DeviceTreeNode root = tree.getRootNode();
        // Delete Tree
        session.delete(tree);
        // Delete Root Node and all of nodes.
        session.delete(root);
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#delete(com.npower.dm.core.DeviceTreeNode)
   */
  public void delete(DeviceTreeNode node) throws DMException {
    Session session = this.getHibernateSession();
    try {
        // Delete children node first
        Set<DeviceTreeNode> children = node.getChildren();
        if (children != null) {
           for (Iterator<DeviceTreeNode> i = children.iterator(); i.hasNext();) {
               DeviceTreeNode child = i.next();
               this.deleteDeviceTreeNode(session, child);
           }
        }
        
        if (node != null && node.getID() > 0 && node.getParentNode() != null && node.getParentNode().getChildren() != null) {
           node.getParentNode().getChildren().remove(node);
           session.delete(node);
        }
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
    
  }

  private void deleteDeviceTreeNode(Session session, DeviceTreeNode node) throws DMException {
    // Delete children node first
    Set<DeviceTreeNode> children = node.getChildren();
    if (children != null) {
       for (Iterator<DeviceTreeNode> i = children.iterator(); i.hasNext();) {
           DeviceTreeNode child = i.next();
           this.deleteDeviceTreeNode(session, child);
       }
    }
    // Delete self
    session.delete(node);
  }
  /**
   * Store the node into DeviceTree related with the device.
   * For exmaple: 
   * The nodePath is "./DevInfo/Mod", nodeValue is "W32 dm client"
   * 
   * @param nodePath
   * @param nodeValue
   * @throws DMException
   */
  public DeviceTreeNode updateDeviceTreeNode(String deviceID, String nodePath, Object nodeValue) throws DMException {
    Session session = this.getHibernateSession();
    try {
        Device device = this.getDeviceByID(deviceID);
        if (device == null) {
           throw new DMException("Could not found the device: " + deviceID);
        }
        if (log.isDebugEnabled()) {
           log.debug("saving node: " + nodePath + "=" + nodeValue);
        }
        
        // Update booted flag for device
        device.setBooted(true);
        this.update(device);
        
        // Save node into inventory
        DeviceTree dTree = device.getDeviceTree();
        if (dTree == null) {
           DeviceTreeNodeEntity rootNode = new DeviceTreeNodeEntity(".", null);
           session.saveOrUpdate(rootNode);
           
           dTree = new DeviceTreeEntity(rootNode);
           session.saveOrUpdate(dTree);
           
           device.setDeviceTree(dTree);
           session.saveOrUpdate(device);
        }
        // erase the "." prefix
        if (nodePath.startsWith(".")) {
           nodePath = nodePath.substring(1, nodePath.length());
        }
        
        // erase the "/" prefix
        if (nodePath.startsWith("/")) {
           nodePath = nodePath.substring(1, nodePath.length());
        }
        
        DeviceTreeNode node = this.findorCreateDeviceTreeNode(session, dTree.getRootNode(), nodePath);
        
        if (nodeValue == null) {
           node.setStringValue(null);
           node.setFormat(DDFNode.DDF_FORMAT_NULL);
           node.setType(DDFNode.DEFAULT_MIMETYPE);
           
        } else if (nodeValue instanceof String ) {
          // String
          node.setStringValue((String)nodeValue);
          node.setFormat(DDFNode.DDF_FORMAT_CHR);
          node.setType(DDFNode.DEFAULT_MIMETYPE);
          
        } else if (nodeValue instanceof Boolean) {
          // Boolean
          node.setStringValue(((Boolean)nodeValue).toString());
          node.setFormat(DDFNode.DDF_FORMAT_BOOL);
          node.setType(DDFNode.DEFAULT_MIMETYPE);
          
        } else if (nodeValue instanceof Long) {
          // Long or Integer
          node.setStringValue(((Long)nodeValue).toString());
          node.setFormat(DDFNode.DDF_FORMAT_CHR);
          node.setType(DDFNode.DEFAULT_MIMETYPE);
          
        } else if (nodeValue instanceof byte[]) {
          // Byte[]
          node.setBinary((byte[])nodeValue);
          node.setFormat(DDFNode.DDF_FORMAT_BIN);
          node.setType(DDFNode.MIMETYPE_BINARY);
          
        } else if ( nodeValue instanceof TreeNode) {
          // TreeNode
          TreeNode tNode = (TreeNode)nodeValue;
          if (TreeNode.FORMAT_NODE.equals(tNode.getFormat())) { 
             // Node
             node.setStringValue(null);
             node.setFormat(tNode.getFormat());
             node.setType(tNode.getType());
          } else {
            // Leaf
            node.setFormat(tNode.getFormat());
            node.setType(tNode.getType());
            nodeValue = tNode.getValue();
            if (nodeValue == null) {
               node.setStringValue(null);
            } else if (nodeValue instanceof String ) {
              // String
              node.setStringValue((String)nodeValue);
            } else if (nodeValue instanceof Boolean) {
              // Boolean
              node.setStringValue(((Boolean)nodeValue).toString());
            } else if (nodeValue instanceof Long) {
              // Long or Integer
              node.setStringValue(((Long)nodeValue).toString());
            } else if (nodeValue instanceof byte[]) {
              // Byte[]
              node.setFormat(DDFNode.DDF_FORMAT_BIN);
              node.setBinary((byte[])nodeValue);
            } else {
              String v = (nodeValue == null)?"":nodeValue.toString();
              node.setStringValue(v);
            }
          }
        } else {
          String v = (nodeValue == null)?"":nodeValue.toString();
          node.setStringValue(v);
        }
        session.saveOrUpdate(node);
        
        if (log.isDebugEnabled()) {
           log.debug("saved node: " + nodePath + "=" + nodeValue);
        }
        return node;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#findDeviceTreeNode(com.npower.dm.core.Device, java.lang.String)
   */
  public DeviceTreeNode findDeviceTreeNode(String deviceID, String nodePath) throws DMException {
    Session session = this.getHibernateSession();
    try {
        Device device = this.getDeviceByID(deviceID);
        if (device == null) {
           throw new DMException("Could not found the device: " + deviceID);
        }
        DeviceTree dTree = device.getDeviceTree();
        if (dTree == null) {
           DeviceTreeNodeEntity rootNode = new DeviceTreeNodeEntity(".", null);
           session.saveOrUpdate(rootNode);
           
           dTree = new DeviceTreeEntity(rootNode);
           session.saveOrUpdate(dTree);
           
           device.setDeviceTree(dTree);
           session.saveOrUpdate(device);
        }
        // erase the "." prefix
        if (nodePath.startsWith(".")) {
           nodePath = nodePath.substring(1, nodePath.length());
        }
        
        // erase the "/" prefix
        if (nodePath.startsWith("/")) {
           nodePath = nodePath.substring(1, nodePath.length());
        }
        
        DeviceTreeNode node = this.findDeviceTreeNode(session, dTree.getRootNode(), nodePath);
        return node;
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#findDeviceTreeNode(long, java.lang.String)
   */
  public DeviceTreeNode findDeviceTreeNode(long deviceID, String nodePath) throws DMException {
    return this.findDeviceTreeNode("" + deviceID, nodePath);
  }


  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#findConcreteTreeNodes(long, long)
   */
  public List<DeviceTreeNode> findConcreteTreeNodes(long deviceID, long ddfNodeID) throws DMException {
    List<DeviceTreeNode> result = new ArrayList<DeviceTreeNode>();
    Device device = this.getDeviceByID(deviceID);
    if (device == null) {
       return result;
    }
    DeviceTree tree = device.getDeviceTree();
    DeviceTreeNode concreteNode = tree.getRootNode();
    if (tree == null || concreteNode == null) {
       return result;
    }
    
    DDFTreeBean ddfBean = this.getManagementBeanFactory().createDDFTreeBean();
    DDFNode targetDdfNode = ddfBean.getDDFNodeByID(ddfNodeID);
    if (targetDdfNode == null) {
       return result;
    }
    
    // Retrieve concrete nodes
    List<DDFNode> ddfNodeVector = targetDdfNode.getVector();
    
    this.ergod(result, 0, ddfNodeVector, concreteNode);
    return result;
  }

  /**
   * @param result
   * @param deepth
   * @param ddfNodeVector
   * @param concreteNode
   * @return
   */
  private void ergod(List<DeviceTreeNode> result, int deepth, List<DDFNode> ddfNodeVector, DeviceTreeNode concreteNode) {
    for (DeviceTreeNode child: concreteNode.getChildren()) {
        if (deepth < ddfNodeVector.size()) {
          DDFNode ddfNode = ddfNodeVector.get(deepth);
          
          if (ddfNode.getName() == null || child.getName().equals(ddfNode.getName())) {
             if (ddfNodeVector.size() == deepth + 1) {
                // 最后一层
                result.add(child);
             } else {
               // 中间层, 继续遍历
               ergod(result, deepth + 1, ddfNodeVector, child);
             }
          }
        }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#findConcreteTreeNodes(com.npower.dm.core.Device, com.npower.dm.core.DeviceTreeNode, com.npower.dm.core.DDFNode)
   */
  public List<DeviceTreeNode> findConcreteTreeNodes(Device device, DeviceTreeNode baseNode, DDFNode ddfNode) throws DMException {
    List<DeviceTreeNode> result = new ArrayList<DeviceTreeNode>();
    for (DeviceTreeNode node: this.findConcreteTreeNodes(device.getID(), ddfNode.getID())) {
        if (node.getNodePath().startsWith(baseNode.getNodePath())) {
          result.add(node);
        }
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#getDeviceTreeNodeByID(java.lang.String)
   */
  public DeviceTreeNode getDeviceTreeNodeByID(String nodeID) throws DMException {
    if (nodeID == null || nodeID.trim().length() == 0) {
      return null;
    }

    Session session = this.getHibernateSession();
    try {
      return (DeviceTreeNode) session.get(DeviceTreeNodeEntity.class, new Long(nodeID));
    } catch (HibernateException e) {
      throw new DMException(e);
    } finally {
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#getCurrentFirmwareVersionId(long)
   */
  public String getCurrentFirmwareVersionId(long deviceID) throws DMException {
    Device device = this.getDeviceByID(Long.toString(deviceID));
    String currentVersionId = null;
    Image currentImage = device.getCuurentImage();
    if (currentImage != null) {
       currentVersionId = currentImage.getVersionId();
    }
    if (StringUtils.isEmpty(currentVersionId)) {
       Model model = device.getModel();
       String firmwareVersionNode = model.getFirmwareVersionNode();
       if (StringUtils.isEmpty(firmwareVersionNode)) {
          firmwareVersionNode = Model.DEFAULT_FIRMWARE_VERSION_NODE_PATH;
       }
       DeviceTreeNode firmwareNode = this.findDeviceTreeNode(device.getID(), firmwareVersionNode);
       if (firmwareNode != null) {
          currentVersionId = firmwareNode.getStringValue();
       }
    } 
    return currentVersionId;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#enroll(java.lang.String, com.npower.dm.core.Model, com.npower.dm.core.Carrier, com.npower.dm.core.ServiceProvider)
   */
  public Device enroll(String phoneNumber, Model model, Carrier carrier, ServiceProvider serviceProvider) throws DMException {
    if (log.isDebugEnabled()) {
       log.debug("Enroll a device and subscriber.");
    }
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    SubscriberBean subscriberBean = factory.createSubcriberBean();
    try {
      // Create Subscriber
      String subscriberExtID = phoneNumber;
      // 查找Subscriber
      Subscriber subscriber = subscriberBean .getSubscriberByExternalID(subscriberExtID);
      String deviceExternalID = null;
      if (subscriber == null) {
         if (log.isDebugEnabled()) {
            log.debug("Subscriber none-exists, create a subscriber with ext ID: " + subscriberExtID);
         }
         // 未找到Subscriber, 创建Subscriber
         String subscriberPassword = phoneNumber;
         subscriber = subscriberBean.newSubscriberInstance(carrier, subscriberExtID, phoneNumber, subscriberPassword);
         subscriber.setServiceProvider(serviceProvider);
         subscriberBean.update(subscriber);
      } else {
        // 找到Subscriber, 寻找该Subscriber下是否有同一型号的设备
        if (log.isDebugEnabled()) {
           log.debug("Subscriber exists, found device.");
        }
        for (Device device: subscriber.getDevices()) {
           if (device.getModel().getID() == model.getID() 
               //&& device.getSubscriberPhoneNumber().equals(phoneNumber)
               //&& device.getSubscriberCarrierId() == carrier.getID()
               ) {
              deviceExternalID = device.getExternalId();
           }
        }
      }
      
      // Create Device
      if (StringUtils.isEmpty(deviceExternalID)) {
         // 设备不存在，生成临时IMEI
         deviceExternalID = "IMEI:" + IMEIUtil.generateIMEI();
         if (log.isDebugEnabled()) {
           log.debug("Could not found device, generate imei: " + deviceExternalID);
         }
         Device device = this.getDeviceByExternalID(deviceExternalID);
         while (device != null) {
           deviceExternalID = "IMEI:" + IMEIUtil.generateIMEI();
           if (log.isDebugEnabled()) {
             log.debug("Could not found device, generate imei: " + deviceExternalID);
           }
           device = this.getDeviceByExternalID(deviceExternalID);
         }
      }
      Device device = this.getDeviceByExternalID(deviceExternalID);
      if (device == null) {
         // 创建设备
         device = this.newDeviceInstance(subscriber, model, deviceExternalID);
         if (log.isDebugEnabled()) {
           log.debug("Create a device with imei: " + deviceExternalID);
         }
         ProfileConfig dmProfile = carrier.getBootstrapDmProfile();
         if (dmProfile != null) {
            device.setOMAClientUsername(dmProfile.getProfileAttributeValue("ClientId").getStringValue());
            device.setOMAClientPassword(dmProfile.getProfileAttributeValue("ClientPW").getStringValue());
            device.setOMAServerPassword(dmProfile.getProfileAttributeValue("ServerPW").getStringValue());
         }
         this.update(device);
      }
      return device;
    } catch (Exception ex) {
      throw new DMException(ex.getMessage(), ex);
    } finally {
      
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#enroll(java.lang.String, java.lang.String, java.lang.String, com.npower.dm.core.Model, com.npower.dm.core.Carrier, com.npower.dm.core.ServiceProvider)
   */
  public Device enroll(String imei, String msisdn, String imsi, Model model, Carrier carrier, ServiceProvider serviceProvider) throws DMException {
    if (log.isDebugEnabled()) {
       log.debug("Enroll a device and subscriber.");
    }
    
    if (StringUtils.isEmpty(imei)) {
       throw new DMException("Failure to enroll a device, imei is EMPTY.");
    }
    
    if (StringUtils.isEmpty(msisdn)) {
       throw new DMException("Failure to enroll a device, msisdn is EMPTY.");
    }
   
    if (model == null) {
       throw new DMException("Failure to enroll a device, Model is NULL.");
    }
  
    if (carrier == null) {
       throw new DMException("Failure to enroll a device, Carrier is NULL.");
    }
 
    ManagementBeanFactory factory = this.getManagementBeanFactory();
    SubscriberBean subscriberBean = factory.createSubcriberBean();
    
    // Get subscriber by msisdn
    Subscriber subscriber = subscriberBean.getSubscriberByExternalID(msisdn);
    if (subscriber == null) {
       // 未找到Subscriber, 创建Subscriber
       if (log.isDebugEnabled()) {
         log.debug("Create a subscriber with msisdn: " + msisdn + ",imsi: " + imsi);
       }
       String subscriberPassword = msisdn;
       String subscriberExtID = msisdn;
       subscriber = subscriberBean.newSubscriberInstance(carrier, subscriberExtID , msisdn, subscriberPassword);
       subscriber.setServiceProvider(serviceProvider);
       subscriberBean.update(subscriber);
    } else {
      if (log.isDebugEnabled()) {
        log.debug("Modify a subscriber with msisdn: " + msisdn);
      }
      if (imsi != null &&!imsi.equalsIgnoreCase(subscriber.getIMSI())) {
         subscriber.setIMSI(imsi);
      }
      subscriber.setCarrier(carrier);
      subscriber.setServiceProvider(serviceProvider);
      subscriberBean.update(subscriber);
    }
    
    Device device = this.getDeviceByExternalID(imei);
    if (device == null) {
       // 创建设备
       device = this.newDeviceInstance(subscriber, model, imei);
       if (log.isDebugEnabled()) {
         log.debug("Create a device with imei: " + imei);
       }
       ProfileConfig dmProfile = carrier.getBootstrapDmProfile();
       if (dmProfile != null) {
          device.setOMAClientUsername(dmProfile.getProfileAttributeValue("ClientId").getStringValue());
          device.setOMAClientPassword(dmProfile.getProfileAttributeValue("ClientPW").getStringValue());
          device.setOMAServerPassword(dmProfile.getProfileAttributeValue("ServerPW").getStringValue());
       }
      this.update(device);
    } else {
      if (log.isDebugEnabled()) {
        log.debug("Modify a device with imei: " + imei);
      }
      device.setModel(model);
      device.setSubscriber(subscriber);
      this.update(device);
    }
    return device;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.management.DeviceBean#reset(com.npower.dm.core.Device)
   */
  public void reset(Device device) {
    if (device.isBooted() && device.getLastBootstrapTime() != null) {
       device.setBootstrapAskCounter(0);
       device.setLastBootstrapTime(null);
       Subscriber  subscriber = device.getSubscriber();
       if (subscriber != null) {
          subscriber.setNoSMSRetries(0);
          subscriber.setNotificationTime(null);
       }
    }
  }
}
