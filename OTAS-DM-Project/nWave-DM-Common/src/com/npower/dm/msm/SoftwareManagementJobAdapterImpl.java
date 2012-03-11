/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/SoftwareManagementJobAdapterImpl.java,v 1.14 2008/11/10 14:52:59 zhao Exp $
  * $Revision: 1.14 $
  * $Date: 2008/11/10 14:52:59 $
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
package com.npower.dm.msm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import sync4j.framework.engine.dm.ManagementProcessor;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceTreeNode;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.Software;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.14 $ $Date: 2008/11/10 14:52:59 $
 */
public class SoftwareManagementJobAdapterImpl implements SoftwareManagementJobAdapter {
  
  private ManagementBeanFactory factory = null;
  
  /**
   * 
   */
  private SoftwareManagementJobAdapterImpl() {
    super();
  }

  /**
   * 
   */
  public SoftwareManagementJobAdapterImpl(ManagementBeanFactory factory) {
    this();
    this.factory = factory;
  }

  /**
   * @param platform
   * @return
   */
  private boolean isNokiaS60MSM(Model model) {
    String platform = model.getCharacterValue("general", "developer.platform");
    if (StringUtils.isEmpty(platform)) {
      return false;
    }
    if (platform.toLowerCase().indexOf("S60 3rd Edition".toLowerCase()) >= 0) {
      // 目前已知S60 3rd 平台支持MSM的手机型号为：
      // 1. S60 3rd Edition (initial release) E系列, 除了E系列，此版本的终端不支持MSM
      // 2. Nokia S60 3rd
      // 3. Nokia S60 3rd FP1
      if (platform.indexOf("initial release") > 0 
          && !model.getManufacturerModelId().toUpperCase().startsWith("E")) {
         // 是initial release, 并且不是E系列的手机，均不支持MSM
         return false;
      }
      return true;
    }
    return false;
  }

  /**
   * 判断是否是 SonyEricsson DM Client Version 3.0
   * @param platform
   * @return
   */
  private boolean isSonyEricssonDMV3(Model model) {
    String platform = model.getCharacterValue("general", "developer.platform");
    if (StringUtils.isEmpty(platform)) {
       return false;
    }
    return platform.toLowerCase().indexOf("DM Client Version 3".toLowerCase()) >= 0 
              || platform.toLowerCase().indexOf("DM Client Version 4".toLowerCase()) >= 0
              || platform.toLowerCase().indexOf("DM Client Version 5".toLowerCase()) >= 0;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.msm.SoftwareManagementJobAdapter#getProcessor(java.lang.String, com.npower.dm.core.Model)
   */
  public ManagementProcessor getProcessor(String jobType, Model model) throws DMException {
    try {
        String platform = model.getCharacterValue("general", "developer.platform");
        if (platform == null) {
           return null;
        }
        if (isNokiaS60MSM(model)) {
           if (ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL.equalsIgnoreCase(jobType)) {
              return new S60V3InstallProcessor4DL();
           } else if (ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL_STAGE_2.equalsIgnoreCase(jobType)) {
             return new S60V3InstallProcessor4DLStage2();
           } else if (ProvisionJob.JOB_TYPE_SOFTWARE_DISCOVERY.equalsIgnoreCase(jobType)) {
             return new S60V3DiscoveryProcessor();
           } else if (ProvisionJob.JOB_TYPE_SOFTWARE_ACTIVE.equalsIgnoreCase(jobType)) {
             return new S60V3ActiveProcessor();
           } else if (ProvisionJob.JOB_TYPE_SOFTWARE_DEACTIVE.equalsIgnoreCase(jobType)) {
             return new S60V3DeactiveProcessor();
           } else if (ProvisionJob.JOB_TYPE_SOFTWARE_UN_INSTALL.equalsIgnoreCase(jobType)) {
             return new S60V3UninstallProcessor();
           } else if (ProvisionJob.JOB_TYPE_SOFTWARE_UPGRADE.equalsIgnoreCase(jobType)) {
             return new S60V3InstallProcessor4DL();
           }
        } else if ( isSonyEricssonDMV3(model)) {
          // 目前已知SonyEricsson平台支持MSM的手机型号为：
          // 1. DM Client 3.x
          if (ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL.equalsIgnoreCase(jobType)) {
             return new SonyEricssonDMV3InstallProcessor4DL();
          } else if (ProvisionJob.JOB_TYPE_SOFTWARE_DISCOVERY.equalsIgnoreCase(jobType)) {
            return new SonyEricssonDMV3DiscoveryProcessor();
          } else if (ProvisionJob.JOB_TYPE_SOFTWARE_UN_INSTALL.equalsIgnoreCase(jobType)) {
            return new SonyEricssonDMV3UnInstallProcessor();
          }
        }
        return null;
    } catch (Exception ex) {
      throw new DMException("Error in find a processor for software management model: " + model.getID() + " for job type: " + jobType, ex);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.processor.msm.SoftwareManagementJobAdapter#getProcessor(java.lang.String, java.lang.String)
   */
  public ManagementProcessor getProcessor(String jobType, String deviceExtID) throws DMException {
    try {
        DeviceBean bean = factory.createDeviceBean();
        Device device = bean.getDeviceByExternalID(deviceExtID);
        if (device == null) {
           throw new DMException("Could not found device by device Ext ID: " + deviceExtID);
        }
        Model model = device.getModel();
        ManagementProcessor processor = getProcessor(jobType, model);
        if (processor != null) {
           return processor;
        }
        throw new DMException("Could not found a software management processor for deviceExtID: " + deviceExtID + ", job type: " + jobType);
    } catch (Exception ex) {
      throw new DMException("Error in find a processor for software management job.", ex);
    }
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.msm.SoftwareManagementJobAdapter#getDeployedSoftwares(java.lang.String)
   */
  public List<SoftwareNodeInfo> getDeployedSoftwares(String deviceExtID) throws DMException {
    try {
      DeviceBean bean = factory.createDeviceBean();
      Device device = bean.getDeviceByExternalID(deviceExtID);
      if (device == null) {
         throw new DMException("Could not found device by device Ext ID: " + deviceExtID);
      }
      Model model = device.getModel();
      if (this.isNokiaS60MSM(model)) {
         // Nokia Mode
         return this.getDeployedSoftwares4NokiaS60(deviceExtID);
      } else if (this.isSonyEricssonDMV3(model)) {
        // SonyEricsson Mode
        return this.getDeployedSoftwares4SonyEricssonDMVersion3(deviceExtID);
      }
      return new ArrayList<SoftwareNodeInfo>();
    } catch (Exception ex) {
      throw new DMException("Error in find deployed softwares for device: " + deviceExtID, ex);
    }
  }
  
  /**
   * 返回当前设备中安装的"已知"软件, "已知"表示目前软件数据库中存在的软件. 对于未知的软件, 不返回在列表中.
   * @param deviceExtID
   * @return Software External ID
   *     
   * @throws DMException
   */
  private List<SoftwareNodeInfo> getDeployedSoftwares4SonyEricssonDMVersion3(String deviceExtID) throws DMException {
    try {
        List<SoftwareNodeInfo> result = new ArrayList<SoftwareNodeInfo>();
  
        DeviceBean bean = factory.createDeviceBean();
        Device device = bean.getDeviceByExternalID(deviceExtID);
        if (device == null) {
           throw new DMException("Could not found device by device Ext ID: " + deviceExtID);
        }
        DeviceTreeNode baseNode = bean.findDeviceTreeNode(device.getID(), "./Com.SonyEricsson/Content/JavaApplications/Installed");
        if (baseNode != null && !baseNode.getChildren().isEmpty()) { 
           for (DeviceTreeNode appNode: baseNode.getChildren()) {
               String appName = null;
               String vendorName = null;
               String version = null;
               boolean deletable = false;
               SoftwareNodeInfo info = new SoftwareNodeInfo();
               for (DeviceTreeNode item: appNode.getChildren()) {
                   if ("Name".equalsIgnoreCase(item.getName())) {
                      appName = item.getStringValue();
                   } else if ("Vendor".equalsIgnoreCase(item.getName())) {
                     vendorName = item.getStringValue();
                   } else if ("Version".equalsIgnoreCase(item.getName())) {
                     version = item.getStringValue();
                   } else if ("Deletable".equalsIgnoreCase(item.getName())) {
                     String v = item.getStringValue();
                     if (StringUtils.isNotEmpty(v) && v.equalsIgnoreCase("true")) {
                       deletable = true;
                     }
                   }
               }
               info.setVendorName(vendorName);
               info.setSoftwareName(appName);
               info.setSoftwareVersion(version);
               info.setDeletable(deletable);
               info.setNodePath(appNode.getNodePath());
               
               SoftwareBean softwareBean = factory.createSoftwareBean();
               Software software = softwareBean.getSoftware(vendorName, appName, version);
               if (software != null) {
                  String softwareExtID = software.getExternalId();
                  if (StringUtils.isNotEmpty(softwareExtID)) {
                     info.setSoftwareExternalId(softwareExtID);
                  }
               }
               
               result.add(info);
           }
        }
        return result;
    } catch (Exception ex) {
      throw new DMException("Error in find deployed softwares for device: " + deviceExtID, ex);
    }
  }

  /**
   * @param deviceExtID
   * @return
   * @throws DMException
   */
  private List<SoftwareNodeInfo> getDeployedSoftwares4NokiaS60(String deviceExtID) throws DMException {
    try {
        List<SoftwareNodeInfo> result = new ArrayList<SoftwareNodeInfo>();
        SoftwareBean softwareBean = factory.createSoftwareBean();
        DeviceBean bean = factory.createDeviceBean();
        Device device = bean.getDeviceByExternalID(deviceExtID);
        if (device == null) {
           throw new DMException("Could not found device by device Ext ID: " + deviceExtID);
        }
        Model model = device.getModel();
        ManagementProcessor processor = getProcessor(ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL, model);
        if (processor instanceof BaseS60V3Processor) {
           // Symbian S60 3rd
           BaseS60V3Processor prc = (BaseS60V3Processor)processor;
           DeviceTreeNode baseNode = bean.findDeviceTreeNode(device.getID(), prc.getBaseScomoDeployedNode());
           if (baseNode != null && !baseNode.getChildren().isEmpty()) { 
              for (DeviceTreeNode node: baseNode.getChildren()) {
                  String softwareExtID = node.getName();
                  if (StringUtils.isNotEmpty(softwareExtID)) {
                     SoftwareNodeInfo info = new SoftwareNodeInfo();
                     info.setSoftwareExternalId(softwareExtID);
                     Software software = softwareBean.getSoftwareByExternalID(softwareExtID);
                     if (software != null) {
                        info.setSoftwareName(software.getName());
                        info.setSoftwareVersion(software.getVersion());
                        info.setVendorName(software.getVendor().getName());
                     }
                     result.add(info);
                  }
              }
           }
        }
        return result;
    } catch (Exception ex) {
      throw new DMException("Error in find deployed softwares for device: " + deviceExtID, ex);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.msm.SoftwareManagementJobAdapter#getDeployedSoftwareState(java.lang.String, java.lang.String)
   */
  public String getDeployedSoftwareState(String deviceExtID, String softwareExtID) throws DMException {
    try {
        DeviceBean bean = factory.createDeviceBean();
        Device device = bean.getDeviceByExternalID(deviceExtID);
        if (device == null) {
           throw new DMException("Could not found device by device Ext ID: " + deviceExtID);
        }
        Model model = device.getModel();
        ManagementProcessor processor = getProcessor(ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL, model);
        if (processor instanceof BaseS60V3Processor) {
           // Symbian S60 3rd
           BaseS60V3Processor prc = (BaseS60V3Processor)processor;
           DeviceTreeNode node = bean.findDeviceTreeNode(device.getID(), prc.getSoftwareDeployedStateNodePath(softwareExtID));
           if (node != null && node.getStringValue() != null) { 
              return node.getStringValue();
           }
        }
        return null;
    } catch (Exception ex) {
      throw new DMException("Error in find deployed softwares for device: " + deviceExtID, ex);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.msm.SoftwareManagementJobAdapter#isSupported(com.npower.dm.core.Model)
   */
  public boolean isSupported(Model model) throws DMException {
    if (model == null) {
       return false;
    }
    ManagementProcessor processor = this.getProcessor(ProvisionJob.JOB_TYPE_SOFTWARE_INSTALL, model);
    if (processor != null) {
       return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.msm.SoftwareManagementJobAdapter#isSupported(com.npower.dm.core.Manufacturer)
   */
  public boolean isSupported(Manufacturer manufacturer) throws DMException {
    if (manufacturer == null) {
       return false;
    }
    for (Model model: manufacturer.getModels()) {
        if (this.isSupported(model)) {
           return true;
        }
    }
    return false;
  }
}
