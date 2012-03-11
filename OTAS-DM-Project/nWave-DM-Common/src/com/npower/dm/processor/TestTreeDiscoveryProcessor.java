/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/TestTreeDiscoveryProcessor.java,v 1.12 2008/08/04 07:13:34 zhao Exp $
  * $Revision: 1.12 $
  * $Date: 2008/08/04 07:13:34 $
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
package com.npower.dm.processor;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;
import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.GetManagementOperation;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.SessionContext;
import sync4j.framework.engine.dm.UserAlertManagementOperation;
import sync4j.framework.security.Sync4jPrincipal;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.core.DeviceTree;
import com.npower.dm.core.DeviceTreeNode;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.CarrierBean;
import com.npower.dm.management.CountryBean;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.ProvisionJobBean;
import com.npower.dm.management.SubscriberBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.12 $ $Date: 2008/08/04 07:13:34 $
 */
public class TestTreeDiscoveryProcessor extends TestCase {

  /**
   * 
   */
  private static final String CLIENT_PASSWORD = "client.password";
  /**
   * 
   */
  private static final String CLIENT_USERNAME = "client.username";

  private String manufacturerExternalID = "W32 Device Manufacturer";
  private String modelExternalID = "W32 Device Model";
  private String deviceExternalID = "w32dm";
  private String carrierExternalID = "W32 Carrier";

  
  private String serverPassword = "srvpwd";
  
  private long jobID = 0;
  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    try {
        // clear all
        this.tearDownEntities();
        
        // Create carrier, manufacturer, model, device, subscriber
        setupEntities();
        
        // Create Job
        setupDiscoveryJob();
        
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @throws DMException
   */
  private void setupEntities() throws Exception {
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    
    try {
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.newManufacturerInstance(manufacturerExternalID, manufacturerExternalID, null);
        factory.beginTransaction();
        modelBean.update(manufacturer);
        factory.commit();
        
        factory.beginTransaction();
        Model model = modelBean.newModelInstance(manufacturer, modelExternalID, modelExternalID, true, true, true, true, true);
        modelBean.update(model);
        factory.commit();
        
        CountryBean countryBean = factory.createCountryBean();
        Country country = countryBean.getCountryByISOCode("CN");
        
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.newCarrierInstance(country, carrierExternalID, carrierExternalID);
        factory.beginTransaction();
        carrierBean.update(carrier);
        factory.commit();
        
        SubscriberBean subscriberBean = factory.createSubcriberBean();
        Subscriber subscriber = subscriberBean.newSubscriberInstance(carrier, deviceExternalID, "13801356799", "password");
        factory.beginTransaction();
        subscriberBean.update(subscriber);
        factory.commit();
        
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.newDeviceInstance(subscriber, model, deviceExternalID);
        device.setOMAClientUsername(CLIENT_USERNAME);
        device.setOMAClientPassword(CLIENT_PASSWORD);
        device.setOMAServerPassword(serverPassword);
        factory.beginTransaction();
        deviceBean.update(device);
        factory.commit();
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {    
      factory.release();
    }
  }
  
  private void setupDiscoveryJob() throws Exception {
    // Testcase: add a Job
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        /*
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID(carrierExternalID);
  
        SubscriberBean subscriberBean = factory.createSubcriberBean();
        Subscriber subscriber = subscriberBean.getSubscriberByExternalID(deviceExternalID);
  
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelExternalID);
        */
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        assertNotNull(device);
        
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        
        // Create devicegroup and job ...
        factory.beginTransaction();
        // Create a device group
        DeviceGroup group = deviceBean.newDeviceGroup();
        deviceBean.add(group, device);
        deviceBean.update(group);
        
        // Create a discovery job
        String[] nodePath = {"DevDetail", "DevInfo", "SyncML"};
        ProvisionJob job = jobBean.newJob4Discovery(group, nodePath);
        jobBean.update(job);
        this.jobID = job.getID();
        assertTrue(this.jobID > 0);
        
        factory.commit();
        
        // Testcase: load a Job
        job = jobBean.loadJobByID(this.jobID);
        assertNotNull(job);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
        assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job.getTargetType());
        assertEquals(ProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
        String[] nodes = job.getNodes4Discovery();
        assertEquals(nodes.length, nodePath.length);
        // result will be sorted by rootNode
        assertEquals(nodes[0], nodePath[0]);
        assertEquals(nodes[1], nodePath[1]);
        assertEquals(nodes[2], nodePath[2]);
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {    
      factory.release();
    }
    
  }

  /*
   * @see TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
    this.tearDownEntities();
  }
  
  private void tearDownEntities() throws Exception {
    ManagementBeanFactory factory = null;
    try {
      
        factory = AllTests.getManagementBeanFactory();
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        if (device != null) {
          factory.beginTransaction();
          deviceBean.delete(device);
          factory.commit();
        }
        
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.getCarrierByExternalID(carrierExternalID);
        if (carrier != null) {
           factory.beginTransaction();
           carrierBean.delete(carrier);
           factory.commit();
        }
        
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        if (manufacturer != null) {
           factory.beginTransaction();
           modelBean.delete(manufacturer);
           factory.commit();
        }
    } catch (DMException e) {
      e.printStackTrace();
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    
  }

  public void testSetOperationResults() throws Exception {
    
    String processorName = "TreeDiscoveryProcessor";
    ManagementProcessor processor = new TreeDiscoveryProcessor();
    ((TreeDiscoveryProcessor)processor).setName(processorName);
    assertEquals(processor.getName(), processorName);
    

    // Emulate the lifecycle of ManagementProcessor.
    String sessionID = "00001";
    Principal principal = new Sync4jPrincipal(CLIENT_USERNAME, CLIENT_PASSWORD, deviceExternalID);
    int sessionType = 0;
    DevInfo devInfo = new DevInfo(deviceExternalID, manufacturerExternalID, modelExternalID, "dmV", "lang");
    DeviceDMState dmState = new DeviceDMState(deviceExternalID);
    dmState.mssid = "" + this.jobID;
    
    try {
        SessionContext session = new SessionContext(sessionID, sessionID, (Sync4jPrincipal)principal, sessionType, devInfo, dmState, null, null);
        processor.beginSession(session);
        
        // Test JobStatus
        {
          ManagementBeanFactory factory = null;
          factory = AllTests.getManagementBeanFactory();
          DeviceBean deviceBean = factory.createDeviceBean();
          Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
          ProvisionJobBean jobBean = factory.createProvisionJobBean();
          ProvisionJob job = factory.createProvisionJobBean().loadJobByID(jobID);
          ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
          assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_DOING, status.getState());

          assertNotNull(device.getInProgressDeviceProvReq());
          assertEquals(status.getID(), device.getInProgressDeviceProvReq().getID());
          factory.release();
        }
        
        ManagementOperation[] operations = processor.getNextOperations();
        assertTrue(operations.length > 0);
        
        List<ManagementOperationResult> list = new ArrayList<ManagementOperationResult>();
        for (int i = 0; i < operations.length; i++) {
            assertTrue(operations[i] instanceof GetManagementOperation || operations[i] instanceof UserAlertManagementOperation);
            if (operations[i] instanceof GetManagementOperation) {
               GetManagementOperation getOperation = (GetManagementOperation)operations[i];
               Map<String, ?> nodes = getOperation.getNodes();
               assertTrue(nodes.size() > 0);
               for (Iterator<String> nodePathIterator = nodes.keySet().iterator(); nodePathIterator.hasNext(); ) {
                   String nodePath = (String)(nodePathIterator.next());
                   assertTrue(nodePath.length() > 0);
      
                   ManagementOperationResult result = new ManagementOperationResult();
                   Map<String, String> map = new HashMap<String, String>();
                   map.put(nodePath, "value of " + nodePath);
                   result.setCommand("GET");
                   result.setStatusCode(200);
                   result.setNodes(map);
                   list.add(result);
               }
            }
        }
        assertTrue(list.size() > 0);
        ManagementOperationResult[] results = new ManagementOperationResult[list.size()];
        for (int i = 0; i < list.size(); i++) {
          results[i] = (ManagementOperationResult)list.get(i);
          
        }
        processor.setOperationResults(results);
        
        // Test JobStatus
        {
          ManagementBeanFactory factory = null;
          factory = AllTests.getManagementBeanFactory();
          DeviceBean deviceBean = factory.createDeviceBean();
          Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
          ProvisionJobBean jobBean = factory.createProvisionJobBean();
          ProvisionJob job = factory.createProvisionJobBean().loadJobByID(jobID);
          ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
          assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_DOING, status.getState());
          factory.release();
        }
    } catch (Exception e) {
      throw e;
    } finally {
      int DM_SESSION_SUCCESS = DeviceDMState.STATE_COMPLETED;
      processor.endSession(DM_SESSION_SUCCESS);
      // Test JobStatus
      {
        ManagementBeanFactory factory = null;
        factory = AllTests.getManagementBeanFactory();
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        ProvisionJob job = factory.createProvisionJobBean().loadJobByID(jobID);
        ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
        assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_DONE, status.getState());

        assertNull(device.getInProgressDeviceProvReq());

        // Load DeviceTree to checking
        DeviceTree tree = device.getDeviceTree();
        assertNotNull(tree);
        DeviceTreeNode rootNode = tree.getRootNode();
        assertNotNull(rootNode);
        assertEquals(rootNode.getName(), DeviceTreeNode.ROOT_NODE_NAME);
        
        Set<DeviceTreeNode> children = rootNode.getChildren();
        assertEquals(3, children.size());
        Iterator<DeviceTreeNode> iterator = children.iterator();
        // DevDetail Node
        assertTrue(iterator.hasNext());
        DeviceTreeNode devDetailNode = (DeviceTreeNode)iterator.next();
        assertEquals(devDetailNode.getName(), "DevDetail");
        
        // DevInfo Node
        assertTrue(iterator.hasNext());
        DeviceTreeNode devInfolNode = (DeviceTreeNode)iterator.next();
        assertEquals(devInfolNode.getName(), "DevInfo");
        
        factory.release();
      }
    }
  }
  
  
  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestTreeDiscoveryProcessor.class);
  }

}
