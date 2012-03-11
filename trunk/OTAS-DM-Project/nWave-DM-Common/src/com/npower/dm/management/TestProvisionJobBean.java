/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/management/TestProvisionJobBean.java,v 1.17 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.17 $
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
package com.npower.dm.management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.core.ProvisionJobStatus;
import com.npower.dm.core.Subscriber;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.17 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestProvisionJobBean extends TestCase {

  private String FILENAME_GET_SCRIPT = "/metadata/command/test/test.get.xml";
  private String FILENAME_BUG_318_SCRIPT = "/metadata/command/test/nokia/bug318.xml";
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
  
  private String carrierExternalID_Prefix = "W32 Carrier ";
  private String deviceExternalID_Prefix = "w32dm ";
  
  private int Total_Devices = 3;
  private int Total_Carriers = 3;
  private int Total_Jobs_Per_Device = 3;

  
  private String serverPassword = "srvpwd";
  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    try {
        // clear all
        this.tearDownEntities();
    
        setupEntities();
        
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * @throws DMException
   */
  private void setupEntities() throws DMException {
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
        
        // Create 10 x Carriers, each carrier include 10 devices.
        for (int carrierIndex = 0; carrierIndex < Total_Carriers; carrierIndex++) {
            CarrierBean carrierBean = factory.createCarrierBean();
            Carrier carrier = carrierBean.newCarrierInstance(country, getCarrierExternalID(carrierIndex), getCarrierExternalID(carrierIndex));
            factory.beginTransaction();
            carrierBean.update(carrier);
            factory.commit();
            
            DeviceBean deviceBean = factory.createDeviceBean();
            for (int i = 0; i < Total_Devices; i++) {
                SubscriberBean subscriberBean = factory.createSubcriberBean();
                String deviceExternalID = getDeviceExternalID(carrier, i);
                String subscriberExtID = deviceExternalID;
                String phoneNumber = "" + deviceExternalID.hashCode();
                Subscriber subscriber = subscriberBean.newSubscriberInstance(carrier, subscriberExtID, phoneNumber, "password");
                factory.beginTransaction();
                subscriberBean.update(subscriber);
                factory.commit();
              
                Device device = deviceBean.newDeviceInstance(subscriber, model, deviceExternalID);
                device.setOMAClientUsername(CLIENT_USERNAME + i);
                device.setOMAClientPassword(CLIENT_PASSWORD + i);
                device.setOMAServerPassword(serverPassword);
                factory.beginTransaction();
                deviceBean.update(device);
                factory.commit();
            }
        }
    } catch (RuntimeException e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
        
  }

  /**
   * @param i
   * @return
   */
  private String getDeviceExternalID(Carrier carrier, int i) {
    return deviceExternalID_Prefix + i + "(" + carrier.getExternalID() + ")";
  }

  /**
   * @param i
   * @return
   */
  private String getCarrierExternalID(int i) {
    return carrierExternalID_Prefix + i;
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
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID_Prefix);
        if (device != null) {
          factory.beginTransaction();
          deviceBean.delete(device);
          factory.commit();
        }
        
        CarrierBean carrierBean = factory.createCarrierBean();
        for (int carrierIndex = 0; carrierIndex < Total_Carriers; carrierIndex++) {
            String carrierExternalID = getCarrierExternalID(carrierIndex);
            Carrier carrier = carrierBean.getCarrierByExternalID(carrierExternalID);
            if (carrier != null) {
               factory.beginTransaction();
               carrierBean.delete(carrier);
               factory.commit();
            }
        }
        
        ModelBean modelBean = factory.createModelBean();
        Manufacturer manufacturer = modelBean.getManufacturerByExternalID(manufacturerExternalID);
        if (manufacturer != null) {
          factory.beginTransaction();
          modelBean.delete(manufacturer);
          factory.commit();
        }
    } catch (DMException e) {
      throw e;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    
  }

  public void testBasicDiscoveryJob() throws Exception {
    assertTrue(true);
    
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        DeviceBean deviceBean = factory.createDeviceBean();
        
        CarrierBean carrierBean = factory.createCarrierBean();
        for (int carrierIndex = 0; carrierIndex < Total_Carriers; carrierIndex++) {
            Carrier carrier = carrierBean.getCarrierByExternalID(this.getCarrierExternalID(carrierIndex));
            assertNotNull(carrier);
            
            // Testcase: add a Job
            factory.beginTransaction();
    
            DeviceGroup group = deviceBean.newDeviceGroup();
            for (int i = 0; i < Total_Devices; i++ ) {
                Device device = deviceBean.getDeviceByExternalID(getDeviceExternalID(carrier, i));
                deviceBean.add(group, device);
            }
            deviceBean.update(group);
            
            String[] nodePath = {".", "DevDetail", "SyncML"};
            ProvisionJob job = jobBean.newJob4Discovery(group, nodePath);
            jobBean.update(job);
            long jobID = job.getID();
            assertTrue(jobID > 0);
            
            factory.commit();
            
            // Testcase: load a Job
            job = jobBean.loadJobByID(jobID);
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
            
            // Testcase: disable a job
            jobBean.disable(jobID);
            assertEquals(ProvisionJob.JOB_STATE_DISABLE, job.getState());
            job = jobBean.loadJobByID(jobID);
            assertNotNull(job);
            assertEquals(ProvisionJob.JOB_STATE_DISABLE, job.getState());
           
            // Testcase: cancel a job
            jobBean.cancel(jobID);
            assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job.getState());
            job = jobBean.loadJobByID(jobID);
            assertNotNull(job);
            assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job.getState());
            
            // Testcase: delete a Job
            factory.beginTransaction();
            jobBean.delete(jobID);
            factory.commit();
            
            job = jobBean.loadJobByID(jobID);
            assertNull(job);
        }
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    
  }
  
  public void testFindDiscoveryJob() throws Exception {
    assertTrue(true);
    
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        DeviceBean deviceBean = factory.createDeviceBean();
        
        CarrierBean carrierBean = factory.createCarrierBean();
        for (int carrierIndex = 0; carrierIndex < Total_Carriers; carrierIndex++) {
            Carrier carrier = carrierBean.getCarrierByExternalID(this.getCarrierExternalID(carrierIndex));
            assertNotNull(carrier);
            
            // Testcase: add a Job
            factory.beginTransaction();
    
            DeviceGroup group = deviceBean.newDeviceGroup();
            for (int i = 0; i < Total_Devices; i++ ) {
                Device device = deviceBean.getDeviceByExternalID(getDeviceExternalID(carrier, i));
                deviceBean.add(group, device);
            }
            deviceBean.update(group);
            factory.commit();
            
            // Create jobs: STATE is Applied
            for (int jobIndex = 0; jobIndex < Total_Jobs_Per_Device; jobIndex++) {
                String[] nodePath = {".", "DevDetail", "SyncML"};
                ProvisionJob job = jobBean.newJob4Discovery(group, nodePath);

                factory.beginTransaction();
                jobBean.update(job);
                long jobID = job.getID();
                assertTrue(jobID > 0);
                
                factory.commit();
                
                // Testcase: load a Job
                job = jobBean.loadJobByID(jobID);
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
                
                // Testcase: load DeviceJobStatus
                for (int i = 0; i < Total_Devices; i++ ) {
                    Device device = deviceBean.getDeviceByExternalID(getDeviceExternalID(carrier, i));
                    ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
                    assertNotNull(status);
                    assertTrue(status.getID() > 0);
                    assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_READY, status.getState());
                }
                // Testcase: delete a Job
                //factory.beginTransaction();
                //jobBean.delete(jobID);
                //factory.commit();
                
                //job = jobBean.loadJobByID(jobID);
                //assertNull(job);
            }
            
            // Create jobs: STATE is Disable
            for (int jobIndex = 0; jobIndex < Total_Jobs_Per_Device; jobIndex++) {
                String[] nodePath = {".", "DevDetail", "SyncML"};
                ProvisionJob job = jobBean.newJob4Discovery(group, nodePath);

                factory.beginTransaction();
                job.setState(ProvisionJob.JOB_STATE_DISABLE);
                jobBean.update(job);
                long jobID = job.getID();
                assertTrue(jobID > 0);
                
                factory.commit();
                
                // Testcase: load a Job
                job = jobBean.loadJobByID(jobID);
                assertNotNull(job);
                assertEquals(ProvisionJob.JOB_STATE_DISABLE, job.getState());
                assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job.getTargetType());
                assertEquals(ProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
                String[] nodes = job.getNodes4Discovery();
                assertEquals(nodes.length, nodePath.length);
                // result will be sorted by rootNode
                assertEquals(nodes[0], nodePath[0]);
                assertEquals(nodes[1], nodePath[1]);
                assertEquals(nodes[2], nodePath[2]);
                
                // Testcase: load DeviceJobStatus
                for (int i = 0; i < Total_Devices; i++ ) {
                    Device device = deviceBean.getDeviceByExternalID(getDeviceExternalID(carrier, i));
                    ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
                    assertNotNull(status);
                    assertTrue(status.getID() > 0);
                    assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED, status.getState());
                }
                
                // Testcase: delete a Job
                //factory.beginTransaction();
                //jobBean.delete(jobID);
                //factory.commit();
                
                //job = jobBean.loadJobByID(jobID);
                //assertNull(job);
            }
            
            // Create jobs: STATE is Cancelled
            for (int jobIndex = 0; jobIndex < Total_Jobs_Per_Device; jobIndex++) {
                String[] nodePath = {".", "DevDetail", "SyncML"};
                ProvisionJob job = jobBean.newJob4Discovery(group, nodePath);

                factory.beginTransaction();
                job.setState(ProvisionJob.JOB_STATE_CANCELLED);
                jobBean.update(job);
                long jobID = job.getID();
                assertTrue(jobID > 0);
                
                factory.commit();
                
                // Testcase: load a Job
                job = jobBean.loadJobByID(jobID);
                assertNotNull(job);
                assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job.getState());
                assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job.getTargetType());
                assertEquals(ProvisionJob.JOB_TYPE_DISCOVERY, job.getJobType());
                String[] nodes = job.getNodes4Discovery();
                assertEquals(nodes.length, nodePath.length);
                // result will be sorted by rootNode
                assertEquals(nodes[0], nodePath[0]);
                assertEquals(nodes[1], nodePath[1]);
                assertEquals(nodes[2], nodePath[2]);
                
                // Testcase: load DeviceJobStatus
                for (int i = 0; i < Total_Devices; i++ ) {
                    Device device = deviceBean.getDeviceByExternalID(getDeviceExternalID(carrier, i));
                    ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
                    assertNotNull(status);
                    assertTrue(status.getID() > 0);
                    assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED, status.getState());
                }
                // Testcase: delete a Job
                //factory.beginTransaction();
                //jobBean.delete(jobID);
                //factory.commit();
                
                //job = jobBean.loadJobByID(jobID);
                //assertNull(job);
            }

            // Check job state: Applied
            {
              Device device = deviceBean.getDeviceByExternalID(getDeviceExternalID(carrier, 0));
              List<ProvisionJob> jobs = jobBean.findJobs(ProvisionJob.JOB_TYPE_DISCOVERY, ProvisionJobStatus.DEVICE_JOB_STATE_READY, device);
              assertEquals(jobs.size(), Total_Jobs_Per_Device);
            }
            
            // Check job state: Applied
            {
              Device device = deviceBean.getDeviceByExternalID(getDeviceExternalID(carrier, 0));
              List<ProvisionJob> jobs = jobBean.findJobsQueued(ProvisionJob.JOB_TYPE_DISCOVERY, device);
              assertEquals(jobs.size(), Total_Jobs_Per_Device);
            }
            
            // Check job state: Disable
            {
              Device device = deviceBean.getDeviceByExternalID(getDeviceExternalID(carrier, 0));
              List<ProvisionJob> jobs = jobBean.findJobs(ProvisionJob.JOB_TYPE_DISCOVERY, ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED, device);
              assertEquals(jobs.size(), 2* Total_Jobs_Per_Device);
            }
            // Check job state: Cancelled
            {
              Device device = deviceBean.getDeviceByExternalID(getDeviceExternalID(carrier, 0));
              List<ProvisionJob> jobs = jobBean.findJobs(ProvisionJob.JOB_TYPE_DISCOVERY, ProvisionJobStatus.DEVICE_JOB_STATE_CANCELLED, device);
              assertEquals(jobs.size(), 2* Total_Jobs_Per_Device);
            }
        }
    } catch (Exception e) {
      //factory.rollback();
      throw e;
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  
  public void testBasicCommandJob() throws Exception {
    assertTrue(true);
    
    ManagementBeanFactory factory = null;
    try {
        for (int carrierIndex = 0; carrierIndex < Total_Carriers; carrierIndex++) {
            factory = AllTests.getManagementBeanFactory();
            ProvisionJobBean jobBean = factory.createProvisionJobBean();
            DeviceBean deviceBean = factory.createDeviceBean();
            
            CarrierBean carrierBean = factory.createCarrierBean();
            Carrier carrier = carrierBean.getCarrierByExternalID(this.getCarrierExternalID(carrierIndex));
            assertNotNull(carrier);
            
            // Testcase: add a Job
            factory.beginTransaction();
    
            DeviceGroup group = deviceBean.newDeviceGroup();
            for (int i = 0; i < Total_Devices; i++ ) {
                Device device = deviceBean.getDeviceByExternalID(getDeviceExternalID(carrier, i));
                deviceBean.add(group, device);
            }
            deviceBean.update(group);
            
            String script = readScript(new File(AllTests.BASE_DIR, FILENAME_GET_SCRIPT));
            ProvisionJob job = jobBean.newJob4Command(group, script);
            jobBean.update(job);
            long jobID = job.getID();
            assertTrue(jobID > 0);
            
            factory.commit();
            
            // Access Clob must re-connect to database, otherwise will throw SQLException
            factory.release();

            factory = AllTests.getManagementBeanFactory();
            jobBean = factory.createProvisionJobBean();
            // Testcase: load a Job
            job = jobBean.loadJobByID(jobID);
            assertNotNull(job);
            assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
            assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job.getTargetType());
            assertEquals(ProvisionJob.JOB_TYPE_SCRIPT, job.getJobType());
            String text = job.getScriptString();
            assertNotNull(text);
            assertTrue(text.length() > 0);
            assertEquals(script, text);
            
            // Testcase: disable a job
            factory.beginTransaction();
            jobBean.disable(jobID);
            factory.commit();
            assertEquals(ProvisionJob.JOB_STATE_DISABLE, job.getState());
            job = jobBean.loadJobByID(jobID);
            assertNotNull(job);
            assertEquals(ProvisionJob.JOB_STATE_DISABLE, job.getState());
           
            // Testcase: cancel a job
            factory.beginTransaction();
            jobBean.cancel(jobID);
            factory.commit();
            assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job.getState());
            job = jobBean.loadJobByID(jobID);
            assertNotNull(job);
            assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job.getState());
            
            // Testcase: delete a Job
            factory.beginTransaction();
            jobBean.delete(jobID);
            factory.commit();
            
            factory.release();
        }
    } catch (Exception e) {
      //factory.rollback();
      throw e;
    } finally {
      if (factory != null) {
      }
    }
  }

  public void testCommandJob4Bug318() throws Exception {
    assertTrue(true);
    
    ManagementBeanFactory factory = null;
    try {
        for (int carrierIndex = 0; carrierIndex < Total_Carriers; carrierIndex++) {
            factory = AllTests.getManagementBeanFactory();
            ProvisionJobBean jobBean = factory.createProvisionJobBean();
            DeviceBean deviceBean = factory.createDeviceBean();
            
            CarrierBean carrierBean = factory.createCarrierBean();
            Carrier carrier = carrierBean.getCarrierByExternalID(this.getCarrierExternalID(carrierIndex));
            assertNotNull(carrier);
            
            // Testcase: add a Job
            factory.beginTransaction();
    
            DeviceGroup group = deviceBean.newDeviceGroup();
            for (int i = 0; i < Total_Devices; i++ ) {
                Device device = deviceBean.getDeviceByExternalID(getDeviceExternalID(carrier, i));
                deviceBean.add(group, device);
            }
            deviceBean.update(group);
            
            factory.commit();

            factory.beginTransaction();
            String script = readScript(new File(AllTests.BASE_DIR, FILENAME_BUG_318_SCRIPT));
            ProvisionJob job = jobBean.newJob4Command(group, script);
            
            // 如果不设置name, description, scheduleTime, 则不出现Bug318所提交的Exception,
            // 但如果增加如下设置， 将出现Bug318所提交的Exception
            //job.setName("AAAA");
            //job.setDescription("BBBBB");
            //job.setScheduledTime(new java.util.Date());
            
            jobBean.update(job);
            long jobID = job.getID();
            assertTrue(jobID > 0);
            
            factory.commit();
            factory.release();
            
            // 为了纠正Bug318, 重新创建连接, 滞后修改name, description等信息, 否则Hibernate抛出异常
            factory = AllTests.getManagementBeanFactory();
            factory.beginTransaction();
            jobBean = factory.createProvisionJobBean();
            job = jobBean.loadJobByID(job.getID());
            job.setName("AAAA");
            job.setDescription("BBBBB");
            job.setScheduledTime(new java.util.Date());
            jobBean.update(job);
            factory.commit();

            // Access Clob must re-connect to database, otherwise will throw SQLException
            factory.release();

            factory = AllTests.getManagementBeanFactory();
            jobBean = factory.createProvisionJobBean();
            // Testcase: load a Job
            job = jobBean.loadJobByID(jobID);
            assertNotNull(job);
            assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
            assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job.getTargetType());
            assertEquals(ProvisionJob.JOB_TYPE_SCRIPT, job.getJobType());
            String text = job.getScriptString();
            assertNotNull(text);
            assertTrue(text.length() > 0);
            assertEquals(script, text);
            
            // Testcase: disable a job
            factory.beginTransaction();
            jobBean.disable(jobID);
            factory.commit();
            assertEquals(ProvisionJob.JOB_STATE_DISABLE, job.getState());
            job = jobBean.loadJobByID(jobID);
            assertNotNull(job);
            assertEquals(ProvisionJob.JOB_STATE_DISABLE, job.getState());
           
            // Testcase: cancel a job
            factory.beginTransaction();
            jobBean.cancel(jobID);
            factory.commit();
            assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job.getState());
            job = jobBean.loadJobByID(jobID);
            assertNotNull(job);
            assertEquals(ProvisionJob.JOB_STATE_CANCELLED, job.getState());
            
            // Testcase: delete a Job
            factory.beginTransaction();
            jobBean.delete(jobID);
            factory.commit();
            
            factory.release();
        }
    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      if (factory != null) {
      }
    }
  }

  /**
   * Read script from a script file.
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
  private String readScript(File file) throws FileNotFoundException, IOException {
    BufferedReader scriptFile = new BufferedReader(new FileReader(file));
    String line = scriptFile.readLine();
    String script = "";
    while (line != null) {
          script += line;
          line = scriptFile.readLine();
    }
    return script;
  }
  
  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestProvisionJobBean.class);
  }

}
