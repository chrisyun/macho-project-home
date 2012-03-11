/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ModelEntity.java,v 1.13 2008/06/05 10:34:41 zhao Exp $
 * $Revision: 1.13 $
 * $Date: 2008/06/05 10:34:41 $
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
package com.npower.dm.hibernate.entity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.Hibernate;

import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileTemplate;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.13 $ $Date: 2008/06/05 10:34:41 $
 */
public class ModelEntity extends AbstractModel implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public ModelEntity() {
    super();
  }

  /** minimal constructor */
  public ModelEntity(Manufacturer manufacturer, String manufacturerModelId, String name,
      boolean supportedDownloadMethods, boolean isOmaEnabled, boolean isPlainProfile, boolean isUseEncForOmaMsg,
      boolean isUseNextNoncePerPkg) {
    super(manufacturer, name, manufacturerModelId, supportedDownloadMethods, isOmaEnabled, isPlainProfile,
        isUseEncForOmaMsg, isUseNextNoncePerPkg);
    manufacturer.getModels().add(this);
  }

  /** full constructor */
  public ModelEntity(Manufacturer manufacturer, String manufacturerModelId, String name, String description,
      boolean supportedDownloadMethods, String lastUpdatedBy, Date lastUpdatedTime, boolean isOmaEnabled,
      String serverAuthType, boolean isPlainProfile, boolean isUseEncForOmaMsg, boolean isUseNextNoncePerPkg,
      String firmwareNode, String firmwareUpdateNode, String firmwareUpdateReplaceNode, String firmwareUpdateExecNode,
      String firmwareUpdateStatusNode) {
    super(manufacturer, name, description, manufacturerModelId, supportedDownloadMethods, lastUpdatedBy,
        lastUpdatedTime, isOmaEnabled, serverAuthType, isPlainProfile, isUseEncForOmaMsg, isUseNextNoncePerPkg,
        firmwareNode, firmwareUpdateNode, firmwareUpdateReplaceNode, firmwareUpdateExecNode, firmwareUpdateStatusNode);
    manufacturer.getModels().add(this);
  }

  /**
   * Overload the super method, this method will link the manufacturer to this
   * model.
   */
  public void setManufacturer(Manufacturer manu) {
    super.setManufacturer(manu);
    // Set set = manu.getModels();
    // set.add(this);
  }

  /**
   * return the Bootstrap properties related with the model.
   * 
   * @return
   * @throws DMException
   */
  public Properties getDMBootstrapProperties() throws DMException {
    Properties result = new Properties();

    Set set = this.getModelDMBootProps();
    if (!set.isEmpty()) {
      for (Iterator i = set.iterator(); i.hasNext();) {
        DMBootstrapProperty prop = (DMBootstrapProperty) i.next();
        DMBootstrapPropertyID id = prop.getID();
        String name = id.getPropName();
        String value = prop.getPropValue();
        result.setProperty(name, value);
      }
    }

    return result;
  }


  /**
   * return the DM properties related with the model.
   * 
   * @return
   * @throws DMException
   */
  public Properties getDMProperties() throws DMException {
    Properties result = new Properties();

    Set set = this.getModelDMProps();
    if (!set.isEmpty()) {
      for (Iterator i = set.iterator(); i.hasNext();) {
        ModelDMProperty prop = (ModelDMProperty) i.next();
        ModelDMPropertyID id = prop.getID();
        String name = id.getPropName();
        String value = prop.getPropValue();
        result.setProperty(name, value);
      }
    }

    return result;
  }

  
  /**
   * Implements interface com.npower.dm.core.Mode ***********************************************
   * 
   */
  /**
   * @see  com.npower.dm.core.Mode.getModelTAC()
   */
  public Set<String> getModelTAC() {
    Set<String> set = new HashSet<String>(0);
    Set tacs = this.getModelTACs();
    for (Iterator i = tacs.iterator(); i.hasNext(); ) {
        ModelTAC tac = (ModelTAC)i.next();
        ModelTACID id = tac.getID();
        set.add(id.getTAC());
    }
    return set;
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.core.Model#getProfileMappings()
   */
  public List getProfileMappings() {
    Set<ModelProfileMap> set = this.getModelProfileMaps();
    List result = new ArrayList();
    for (ModelProfileMap map: set) {
        ProfileMapping mapping = map.getProfileMapping();
        result.add(mapping);
    }
    return result;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Model#getProfileMap(com.npower.dm.core.ProfileTemplate)
   */
  public ProfileMapping getProfileMap(ProfileTemplate template) {
    List<ProfileMapping> list = this.getProfileMappings();
    for (ProfileMapping mapping: list) {
        if (mapping.getProfileTemplate().getID() == template.getID()) {
           return mapping;
        }
    }
    return null;
  }

  /**
   * @see  com.npower.dm.core.Mode.getDDFTrees()
   */
  public Set getDDFTrees() {
    Set set = new TreeSet();
    Set mtrees = this.getModelDDFTrees();
    for (Iterator i = mtrees.iterator(); i.hasNext(); ) {
        ModelDDFTree mtree = (ModelDDFTree)i.next();
        set.add(mtree.getDdfTree());
    }
    return set;
  }
  

  /**
   * Override toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append("modelID", this.getID()).append("name", this.getName()).append(
        "amnufacturerModelID", this.getManufacturerModelId()).toString();
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(T)
   */
  public int compareTo(Model o) {
    if (o == null) {
       return 1;
    }
    
    if (this.getManufacturer().compareTo(o.getManufacturer()) == 0) {
       return this.getManufacturerModelId().compareTo(o.getManufacturerModelId());
    } else {
      return this.getManufacturer().compareTo(o.getManufacturer());
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Model#getClientProvTemplates()
   */
  public Set<ClientProvTemplate> getClientProvTemplates() {
    Set<ModelClientProvMapEntity> set = this.getModelClientProvMaps();
    Set<ClientProvTemplate> result = new HashSet<ClientProvTemplate>();
    for (ModelClientProvMap mapping: set) {
        result.add(mapping.getClientProvTemplate());
    }
    return result;
  }

  // ----------------------------------------------------------------------
  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSpec#setIconBinary(java.io.InputStream)
   */
  public void setIconBinary(InputStream in) throws IOException {
    this.setIcon(Hibernate.createBlob(in));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSpec#setIconBinary(byte[])
   */
  public void setIconBinary(byte[] bytes) {
    this.setIcon(Hibernate.createBlob(bytes));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSpec#getIconInputStream()
   */
  public InputStream getIconInputStream() throws DMException {
    try {
      Blob iconBlob = this.getIcon();
      if (iconBlob != null) {
         return iconBlob.getBinaryStream();
      } else {
        return null;
      }
    } catch (SQLException e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.ModelSpec#getIconBinary()
   */
  public byte[] getIconBinary() throws DMException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
        byte[] buf = new byte[512];
        InputStream in = this.getIconInputStream();
        if (in == null) {
           return new byte[0];
        }
        int len = in.read(buf);
        while (len > 0) {
              out.write(buf, 0, len);
              len = in.read(buf);
        }
        in.close();
        return out.toByteArray();
    } catch (IOException e) {
      throw new DMException("Error in read icon bytes.", e);
    }
  }

}
