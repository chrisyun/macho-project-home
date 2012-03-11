/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/server/engine/TestDMManagementEngine.java,v 1.9 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.9 $
  * $Date: 2008/06/16 10:11:15 $
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
package com.npower.dm.server.engine;

import java.security.Principal;

import junit.framework.TestCase;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.security.Sync4jPrincipal;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProvisionJob;
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
 * @version $Revision: 1.9 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestDMManagementEngine extends TestCase {

  /**
   * 
   */
  private static final String CLIENT_PASSWORD = "client.password";
  /**
   * 
   */
  private static final String CLIENT_USERNAME = "client.username";

  private String manufacturerExternalID = "W32 Device Manufacturer"  + TestDMManagementEngine.class.getName();
  private String modelExternalID = "W32 Device Model " + TestDMManagementEngine.class.getName();
  private String deviceExternalID = "w32dm." + TestDMManagementEngine.class.getName();
  private String carrierExternalID = "W32 Carrier ";

  
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
      Subscriber subscriber = subscriberBean.newSubscriberInstance(carrier, "" + System.currentTimeMillis(), "" + System.currentTimeMillis(), "password");
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
        
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        factory.beginTransaction();
        jobBean.delete(this.jobID);
        factory.commit();
        
    } catch (DMException e) {
      e.printStackTrace();
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    
  }

  public void testResolveDMStateNoneQueueProcessor() throws Exception {
    
    Principal principal = new Sync4jPrincipal(CLIENT_USERNAME, CLIENT_PASSWORD, deviceExternalID);
    assertNotNull(principal);
    try {
        DMManagementEngine engine = new DMManagementEngine();
        engine.setAlwaysQueueProcessor(false);
        DeviceDMState dmState = new DeviceDMState(deviceExternalID);
        boolean resolved = engine.resolveDMState(dmState, null);
        assertTrue(resolved);
        assertEquals(deviceExternalID, dmState.deviceId);
        assertEquals(ProvisionJob.JOB_TYPE_DISCOVERY, dmState.operation);
        assertEquals(new Long(this.jobID), new Long(dmState.mssid));
    } catch (Exception e) {
      e.printStackTrace();
      assertTrue(false);
    } finally {
    }
  }

  public void testResolveDMStateQueueProcessor() throws Exception {
    
    Principal principal = new Sync4jPrincipal(CLIENT_USERNAME, CLIENT_PASSWORD, deviceExternalID);
    assertNotNull(principal);
    try {
        DMManagementEngine engine = new DMManagementEngine();
        // Default is queue mode.
        assertTrue(engine.isAlwaysQueueProcessor());
        
        DeviceDMState dmState = new DeviceDMState(deviceExternalID);
        boolean resolved = engine.resolveDMState(dmState, null);
        assertTrue(resolved);
        assertEquals(deviceExternalID, dmState.deviceId);
        assertEquals(null, dmState.operation);
        assertEquals(new Long(0), new Long(dmState.mssid));
    } catch (Exception e) {
      e.printStackTrace();
      assertTrue(false);
    } finally {
    }
  }

  public void testStoreDMState() throws Exception {

    Principal principal = new Sync4jPrincipal(CLIENT_USERNAME, CLIENT_PASSWORD, deviceExternalID);
    assertNotNull(principal);
    try {
        DMManagementEngine engine = new DMManagementEngine();
        engine.setAlwaysQueueProcessor(false);
        DeviceDMState dmState = new DeviceDMState(deviceExternalID);
        boolean resolved = engine.resolveDMState(dmState, null);
        assertTrue(resolved);
        assertEquals(deviceExternalID, dmState.deviceId);
        assertEquals(ProvisionJob.JOB_TYPE_DISCOVERY, dmState.operation);
        assertEquals(new Long(this.jobID), new Long(dmState.mssid));
        assertEquals(DeviceDMState.STATE_MANAGEABLE, dmState.state);
        
        // Change status
        dmState.state = DeviceDMState.STATE_IN_PROGRESS;
        engine.storeDeviceDMState(dmState);
        
        // Checking status
        {
          DeviceDMState dmState1 = new DeviceDMState(deviceExternalID);
          dmState1.mssid = dmState.mssid;
          engine.readDeviceDMState(dmState1);
          assertTrue(resolved);
          assertEquals(DeviceDMState.STATE_IN_PROGRESS, dmState1.state);
        }
        
        // Change status
        dmState.state = DeviceDMState.STATE_COMPLETED;
        engine.storeDeviceDMState(dmState);
        
        {
          DeviceDMState dmState1 = new DeviceDMState(deviceExternalID);
          dmState1.mssid = dmState.mssid;
          engine.readDeviceDMState(dmState1);
          assertEquals(DeviceDMState.STATE_COMPLETED, dmState1.state);
        }
        
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
    }
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestDMManagementEngine.class);
  }

}
