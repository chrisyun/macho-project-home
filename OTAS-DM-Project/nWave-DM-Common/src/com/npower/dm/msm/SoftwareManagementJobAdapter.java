/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/SoftwareManagementJobAdapter.java,v 1.4 2008/11/10 14:30:38 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2008/11/10 14:30:38 $
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

import java.util.List;

import sync4j.framework.engine.dm.ManagementProcessor;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;

/**
 * 软件管理类型任务的适配器, 用于控制针对不同类型的任务和型号, 选择不同的DM Processor
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/11/10 14:30:38 $
 */
public interface SoftwareManagementJobAdapter {
  
  /**
   * 返回一个ManagementProcessor, 用于提供软件管理
   * @param jobType
   * @param deviceExtID
   * @return
   * @throws DMException
   */
  public abstract ManagementProcessor getProcessor(String jobType, String deviceExtID) throws DMException;
  
  
  /**
   * 返回一个ManagementProcessor, 用于提供软件管理. 一般可用于检查型号是否支持相应的软件管理任务类型.
   * @param model
   * @return
   */
  public abstract ManagementProcessor getProcessor(String jobType, Model model) throws DMException;
  
  /**
   * 返回终端设备的已部署的软件信息.
   * 
   * 如果设备类型为Nokia S60 3rd, NodeInfo中仅SoftwareExternalID有效;
   * 如果设备类型为SonyEriccson DM Client V3.0, NodeInfo中将包含较多的信息
   * @param deviceExtID
   * @return
   * @throws DMException
   */
  public List<SoftwareNodeInfo> getDeployedSoftwares(String deviceExtID) throws DMException;
  
  /**
   * 返回软件的安装状态
   * @param deviceExtID
   * @param softwareExtID
   * @return
   * @throws DMException
   */
  public String getDeployedSoftwareState(String deviceExtID, String softwareExtID) throws DMException;

  /**
   * 检查此型号是否支持软件管理
   * @param model
   * @return
   */
  public boolean isSupported(Model model) throws DMException;
  
  /**
   * 检查此手机生产厂商是否有支持软件管理的手机型号
   * @param model
   * @return
   */
  public boolean isSupported(Manufacturer manufacturer) throws DMException;
  
}
