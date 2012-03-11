/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/ProfileMapping.java,v 1.7 2008/12/12 04:16:10 zhao Exp $
 * $Revision: 1.7 $
 * $Date: 2008/12/12 04:16:10 $
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
package com.npower.dm.core;

import java.util.Date;
import java.util.Set;

/**
 * <p>Represent a Profile mapping. The profile mapping mean map a ProfileTemplate into the Model's DDF tree. </p>
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/12/12 04:16:10 $
 */
public interface ProfileMapping {

  /**
   * Return the ID
   * @return
   */
  public abstract long getID();

  /**
   * Return the DDFNode , and Profile will be save below this DDFNode. 
   * @return
   */
  public abstract DDFNode getRootDDFNode();

  /**
   * Set the DDFNode.
   * @param ddfNode
   */
  public abstract void setRootDDFNode(DDFNode ddfNode);

  /**
   * Return the Root Node Path.
   * @return 
   *        the rootNodePath
   */
  public String getRootNodePath();

  /**
   * Set root node path.
   * @param rootNodePath 
   *        the rootNodePath to set
   */
  public void setRootNodePath(String rootNodePath);

  /**
   * Return the template of this ProfileMapping.
   * @return
   */
  public abstract ProfileTemplate getProfileTemplate();

  /**
   * Set a ProfileTemplate for this ProfileMapping.
   * @param profileTemplate
   */
  public abstract void setProfileTemplate(ProfileTemplate profileTemplate);

  /**
   * If true, will share root node.
   * @return
   */
  public abstract boolean getShareRootNode();

  /**
   * Set true, if will share the root node.
   * @param shareRootNode
   */
  public abstract void setShareRootNode(boolean shareRootNode);

  /**
   * Return true, if this mapping is ready to assign to devices.
   * @return
   */
  public abstract boolean getAssignToDevice();

  /**
   * Set true, if this mapping is ready to assign to devices.
   * @param assignToDevice
   */
  public abstract void setAssignToDevice(boolean assignToDevice);

  /**
   * Return the type of profile which will be linked.
   * @return
   */
  public abstract String getLinkedProfileType();

  /**
   * Set a type of profile which will be linked.
   * @param linkedProfileType
   */
  public abstract void setLinkedProfileType(String linkedProfileType);
  
  /**
   * Return a set of NodeMappings.
   * @return Return a <code>Set</code> of {@link com.npower.dm.core.ProfileNodeMapping} objects.
   */
  public abstract Set<ProfileNodeMapping> getProfileNodeMappings();

  /**
   * Getter of the ModelID of this ProfileMappingEntity represented by a
   * ProfileMappingEntity XML.
   * 
   * @return String ModelID of the ProfileMappingEntity in the
   *         ProfileMappingEntity
   */
  public String getModelExternalID();

  /**
   * Getter of the ManufacturerExternal ID of the ProfileMappingEntity
   * represented by a ProfileMappingEntity XML.
   * 
   * @return
   */
  public String getManufacturerExternalID();


  /**
   * 表示当与此Mapping对应Assignment任务运行结束时,是否还需要自动采集一些节点. 缺省为true
   * @return
   */
  public abstract boolean isNeedToDiscovery();
  
  /**
   * 表示当与此Mapping对应Assignment任务运行结束时,是否还需要自动采集一些节点. 缺省为true
   * @param needToDiscovery
   */
  public abstract void setNeedToDiscovery(boolean needToDiscovery);

  /**
   * 当NEED_TO_DISCOVERY为1时, 表示任务结束后需要Discovery节点, 此字段指定需要discovery的节点路径, 
   * 路径可以是多个, 但需要以,分隔, 另外路径必须为绝对路径，并不包含dynamic节点。如果need_to_discovery为1, 
   * 但本字段为null, 则自动计算需要discovery的路径.
   * @return
   */
  public abstract String getDiscoveryNodePaths();
  
  /**
   * 当NEED_TO_DISCOVERY为1时, 表示任务结束后需要Discovery节点, 此字段指定需要discovery的节点路径, 
   * 路径可以是多个, 但需要以,分隔, 另外路径必须为绝对路径，并不包含dynamic节点。如果need_to_discovery为1, 
   * 但本字段为null, 则自动计算需要discovery的路径.
   * 
   * @param nodePaths
   */
  public abstract void setDiscoveryNodePaths(String nodePaths);

  /**
   * Return the models which has been assign this mapping.
   * @return
   */
  public abstract Set<Model> getModels();

  /**
   * 
   * @return
   */
  //public abstract Set getMappingNodeNames();

  /**
   * Getter of the LastUpdatedBy
   * @return 
   */
  public abstract String getLastUpdatedBy();

  /**
   * Setter of the LastUpdatedBy
   * @param lastUpdatedBy
   */
  public abstract void setLastUpdatedBy(String lastUpdatedBy);

  /**
   * Getter of the LastUpdatedTime
   * @return
   */
  public abstract Date getLastUpdatedTime();

  /**
   * Getter of the ChangeVersion
   * @return
   */
  public abstract long getChangeVersion();

}