/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestDeviceBean4DeviceTree.java,v 1.1 2008/12/11 05:21:29 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/12/11 05:21:29 $
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
package com.npower.dm.management;

import java.util.List;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.DDFNode;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceTreeNode;
import com.npower.dm.core.Model;

/**
 * @author Zhao DongLu
 * 
 */
public class TestDeviceBean4DeviceTree extends TestCase {

  //private static Log log = LogFactory.getLog(TestProfileConfigBean.class);

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();

  }
  
  /**
   * 查找存在的节点
   * @throws Exception
   */
  public void testFindConcreteTreeNodesCase1() throws Exception {
    String deviceExtId = "IMEI:004400701707588";
    
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    DeviceBean bean = factory.createDeviceBean();
    DDFTreeBean ddfBean = factory.createDDFTreeBean();

    try {
        Device device = bean.getDeviceByExternalID(deviceExtId);
        assertNotNull(device);
        
        Model model = device.getModel();
        DDFNode ddfNode = ddfBean.findDDFNodeByNodePath(model, "./AP/${NodeName}");
        assertNotNull(ddfNode);
        
        List<DeviceTreeNode> result = bean.findConcreteTreeNodes(device.getID(), ddfNode.getID());
        
        assertNotNull(result);
        assertEquals(6, result.size());
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }
  
  /**
   * 查找存在的节点
   * @throws Exception
   */
  public void testFindConcreteTreeNodesCase2() throws Exception {
    String deviceExtId = "IMEI:004400701707588";
    
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    DeviceBean bean = factory.createDeviceBean();
    DDFTreeBean ddfBean = factory.createDDFTreeBean();

    try {
        Device device = bean.getDeviceByExternalID(deviceExtId);
        assertNotNull(device);
        
        Model model = device.getModel();
        DDFNode ddfNode = ddfBean.findDDFNodeByNodePath(model, "./AP/${NodeName}/NAPDef/${NodeName}/DefGW");
        assertNotNull(ddfNode);
        
        List<DeviceTreeNode> result = bean.findConcreteTreeNodes(device.getID(), ddfNode.getID());
        
        assertNotNull(result);
        assertEquals(6, result.size());
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  /**
   * 查找不存在的节点
   * @throws Exception
   */
  public void testFindConcreteTreeNodesCase3() throws Exception {
    String deviceExtId = "IMEI:004400701707588";
    
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    DeviceBean bean = factory.createDeviceBean();
    DDFTreeBean ddfBean = factory.createDDFTreeBean();

    try {
        Device device = bean.getDeviceByExternalID(deviceExtId);
        assertNotNull(device);
        
        Model model = device.getModel();
        DDFNode ddfNode = ddfBean.findDDFNodeByNodePath(model, "./Email/${NodeName}");
        assertNotNull(ddfNode);
        
        List<DeviceTreeNode> result = bean.findConcreteTreeNodes(device.getID(), ddfNode.getID());
        
        assertNotNull(result);
        assertEquals(0, result.size());
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }
}
