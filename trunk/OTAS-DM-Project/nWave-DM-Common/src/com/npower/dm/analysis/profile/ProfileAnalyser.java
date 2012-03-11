/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/analysis/profile/ProfileAnalyser.java,v 1.3 2009/01/21 07:53:10 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2009/01/21 07:53:10 $
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
package com.npower.dm.analysis.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;

import com.npower.dm.analysis.AbstractAnalyser;
import com.npower.dm.analysis.Report;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceTreeNode;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileConfig;
import com.npower.dm.core.ProfileMapping;
import com.npower.dm.core.ProfileNodeMapping;
import com.npower.dm.hibernate.entity.ProfileAssignmentEntity;
import com.npower.dm.hibernate.entity.ProfileParameterEntity;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2009/01/21 07:53:10 $
 */
public class ProfileAnalyser extends AbstractAnalyser {

  /**
   * 
   */
  public ProfileAnalyser() {
    super();
  }

  /**
   * @param factory
   */
  public ProfileAnalyser(ManagementBeanFactory factory) {
    super(factory);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.analysis.AbstractAnalyser#analyse(com.npower.dm.analysis.Report, java.lang.String)
   */
  @Override
  public List<ProfileAssignment> analyse(Report report, String deviceExternalId) throws DMException {
    DeviceBean deviceBean = this.getFactory().createDeviceBean();
    Device device = deviceBean.getDeviceByExternalID(deviceExternalId);
    if (device == null) {
       throw new DMException("Could not found device for analyse.");
    }
    
    DDFTreeBean ddfBean = this.getFactory().createDDFTreeBean();
    
    List<ProfileAssignment> result = new ArrayList<ProfileAssignment>();
    Model model = device.getModel();
    for (ProfileMapping mapping: model.getProfileMappings()) {
        // 提取每一个映射模板的根DDF节点
        String rootPath = mapping.getRootNodePath();
        DDFNode rootDdfNode = ddfBean.findDDFNodeByNodePath(model, rootPath);    
        for (DeviceTreeNode rootNode4Profile: deviceBean.findConcreteTreeNodes(device.getID(), rootDdfNode.getID())) {
            // 每一个节点下均包含一套配置, 对配置进行分析
            ProfileAssignment assignment = new ProfileAssignmentEntity();
            assignment.setDevice(device);
            assignment.setProfileRootNodePath(rootNode4Profile.getNodePath());
            assignment.setStatus(ProfileAssignment.STATUS_RECOGNIZED);
            for (ProfileNodeMapping nodeMapping: mapping.getProfileNodeMappings()) {
                if (StringUtils.isEmpty(nodeMapping.getCommand()) ||
                    nodeMapping.getCommand().equalsIgnoreCase("add") ||
                    nodeMapping.getCommand().equalsIgnoreCase("replace")) {
                   // 从DeviceTree中提起当前NodeMapping的值
                   DDFNode ddfNode = nodeMapping.getDdfNode();
                   // 基于Profile Root Node, 查找ddfNode对应的device tree node.
                   List<DeviceTreeNode> nodes = deviceBean.findConcreteTreeNodes(device, rootNode4Profile, ddfNode);
                   if (!nodes.isEmpty()) {
                      // Node found, and extract value
                      DeviceTreeNode node = nodes.get(0);
                      
                      ProfileParameterEntity parameter = new ProfileParameterEntity();
                      parameter.setProfileAssignment(assignment);
                      parameter.setDmTreePath(node.getNodePath());
                      parameter.setDdfNode(ddfNode);
                      parameter.setProfileAttribute(nodeMapping.getProfileAttribute());
                      String stringValue = node.getStringValue();
                      if (stringValue != null) {
                         parameter.setRawData(Hibernate.createClob(stringValue));
                      }
                      
                      assignment.getProfileParameters().add(parameter);
                   }
                }
            }
            // Got default peofile for current profile template
            Set<ProfileConfig> configs = mapping.getProfileTemplate().getProfileConfigs();
            if (!configs.isEmpty()) {
               assignment.setProfileConfig(configs.iterator().next());
            }
            result.add(assignment);
        }
    }
    return result;
  }

}
