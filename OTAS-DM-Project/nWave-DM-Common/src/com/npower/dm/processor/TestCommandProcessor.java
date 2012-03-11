/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/TestCommandProcessor.java,v 1.17 2008/08/04 07:13:34 zhao Exp $
 * $Revision: 1.17 $
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;

import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.SessionContext;
import sync4j.framework.security.Sync4jPrincipal;

import com.npower.dm.AllTests;
import com.npower.dm.client.DMClientEmulator;
import com.npower.dm.client.SimpleEmulator;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.DeviceGroup;
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
 * @version $Revision: 1.17 $ $Date: 2008/08/04 07:13:34 $
 */
public class TestCommandProcessor extends TestCase {

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

  private List<Long> jobIDs = new ArrayList<Long>();

  private String serverPassword = "srvpwd";

  /**
   * Constructor for TestCommandProcessor.
   * 
   * @param arg0
   */
  public TestCommandProcessor(String arg0) {
    super(arg0);
  }

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
  
        // Remove jobs
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        for (int i = 0; i < this.jobIDs.size(); i++) {
          factory.beginTransaction();
          Long jobID = (Long) this.jobIDs.get(i);
          jobBean.delete(jobID);
          factory.commit();
        }
  
    } catch (DMException e) {
      factory.rollback();
      throw e;
    } finally {
      if (factory != null) {
        factory.release();
      }
    }
  }

  /**
   * Read script from a script file.
   * 
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

  private long createJob(File scriptFile) throws Exception {
    // Testcase: add a Job
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    long jobID;
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
  
        // Create a command job
        String script = readScript(scriptFile);
        ProvisionJob job = jobBean.newJob4Command(group, script);
        jobBean.update(job);
        jobID = job.getID();
        assertTrue(jobID > 0);
  
        factory.commit();
  
        // Testcase: load a Job
        job = jobBean.loadJobByID(jobID);
        assertNotNull(job);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
        assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job.getTargetType());
        assertEquals(ProvisionJob.JOB_TYPE_SCRIPT, job.getJobType());
        assertEquals(script, job.getScriptString());
    
        this.jobIDs.add(jobID);
        return jobID;

    } catch (Exception e) {
      factory.rollback();
      throw e;
    } finally {
      factory.release();
    }
  }

  /**
   * @param commandFile
   * @param resultFile
   * @throws IOException
   * @throws Exception
   */
  private void emulate(File commandFile, File resultFile) throws IOException, Exception {
    DMClientEmulator emulator = new SimpleEmulator(resultFile);

    long jobID = createJob(commandFile);


    String processorName = "CommandProcessor";
    BaseProcessor processor = new CommandProcessor();
    processor.setName(processorName);
    assertEquals(processor.getName(), processorName);

    // Emulate the lifecycle of ManagementProcessor.
    String sessionID = "00001";
    Principal principal = new Sync4jPrincipal(CLIENT_USERNAME, CLIENT_PASSWORD, deviceExternalID);
    int sessionType = 0;
    DevInfo devInfo = new DevInfo(deviceExternalID, manufacturerExternalID, modelExternalID, "dmV", "lang");
    DeviceDMState dmState = new DeviceDMState(deviceExternalID);
    dmState.mssid = "" + jobID;

    try {
        SessionContext session = new SessionContext(sessionID, sessionID, (Sync4jPrincipal)principal, sessionType, devInfo, dmState, null, null);
        processor.beginSession(session);
        
        // Test JobStatus
        {
          ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
          DeviceBean deviceBean = factory.createDeviceBean();
          Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
          ProvisionJobBean jobBean = factory.createProvisionJobBean();
          ProvisionJob job = jobBean.loadJobByID(jobID);
          ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
          assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_DOING, status.getState());
          
          assertNotNull(device.getInProgressDeviceProvReq());
          assertEquals(status.getID(), device.getInProgressDeviceProvReq().getID());
          factory.release();
        }
        
        ManagementOperation[] operations = processor.getNextOperations();
        while (operations != null && operations.length > 0) {
              ManagementOperationResult[] result = emulator.process(operations);
              processor.setOperationResults(result);
              operations = processor.getNextOperations();
        }
        // Test JobStatus
        {
          ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
          DeviceBean deviceBean = factory.createDeviceBean();
          Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
          ProvisionJobBean jobBean = factory.createProvisionJobBean();
          ProvisionJob job = jobBean.loadJobByID(jobID);
          ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
          assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_DOING, status.getState());
          factory.release();
        }
        
    } catch (Exception ex) {
      throw ex;
    } finally {
      int DM_SESSION_SUCCESS = DeviceDMState.STATE_COMPLETED;
      processor.endSession(DM_SESSION_SUCCESS);

      // Test JobStatus
      {
        ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        ProvisionJob job = jobBean.loadJobByID(jobID);
        ProvisionJobStatus status = jobBean.getProvisionJobStatus(job, device);
        status = jobBean.getProvisionJobStatus(job, device);
        assertEquals(ProvisionJobStatus.DEVICE_JOB_STATE_DONE, status.getState());
        
        assertNull(device.getInProgressDeviceProvReq());
        
        factory.release();
      }

    }
  }

  public void testProcessorCase1() throws Exception {
    File commandFile = new File(AllTests.BASE_DIR, "metadata/processor/test/test.1.command.xml");
    File resultFile = new File(AllTests.BASE_DIR, "metadata/processor/test/test.1.results.xml");

    // Emulate the the process between OMAClient and Processor.
    emulate(commandFile, resultFile);

    // Assert result & DM inventory
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    try {
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        {
          DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./Email/MyEmail/Name");
          String value = node.getStringValue();
          assertEquals("MyPushMail", value);
        }
        {
          DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./Email/MyEmail/SMTPSrv");
          String value = node.getStringValue();
          assertEquals("smtp.mail.com", value);
        }
        {
          DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./Email/MyEmail/POPSrv");
          String value = node.getStringValue();
          assertEquals("pop3.mail.com", value);
        }
        {
          DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./Email/MyEmail/Username");
          String value = node.getStringValue();
          assertEquals("zhaodonglu", value);
        }
        {
          DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./Email/MyEmail/Password");
          String value = node.getStringValue();
          assertEquals("guessit", value);
        }
        {
          DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./Email/MyEmail/EmailAddress");
          assertNull(node);
        }
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

  public void testProcessorCase2() throws Exception {

    {
      // Test Adding
      File commandFile = new File(AllTests.BASE_DIR, "metadata/processor/test/test.2.command.xml");
      File resultFile = new File(AllTests.BASE_DIR, "metadata/processor/test/test.2.results.xml");

      // Emulate the the process between OMAClient and Processor.
      emulate(commandFile, resultFile);

      // Assert result & DM inventory
      ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
      DeviceBean deviceBean = factory.createDeviceBean();
      Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
      {
        DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./Email/MyPushEmail");
        assertNotNull(node);
        assertTrue(StringUtils.isEmpty(node.getStringValue()));
      }
      {
        DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./Email/MyPushEmail/AAA");
        String value = node.getStringValue();
        assertEquals("aaa", value);
      }
      {
        DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./EmailXXX/MyPushEmail/BBB");
        assertNull(node);
      }
      {
        DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./Email/MyPushEmail/CCC");
        String value = node.getStringValue();
        assertEquals("ccc", value);
      }
      {
        DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./Email/MyPushEmail/DDD");
        assertNull(node);
      }
      factory.release();
    }

    // Test Replace
    {
      File commandFile = new File(AllTests.BASE_DIR, "metadata/processor/test/test.3.command.xml");
      File resultFile = new File(AllTests.BASE_DIR, "metadata/processor/test/test.3.results.xml");

      // Emulate the the process between OMAClient and Processor.
      emulate(commandFile, resultFile);

      // Assert result & DM inventory
      ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
      DeviceBean deviceBean = factory.createDeviceBean();
      Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
      {
        {
          DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./Email/MyPushEmail/AAA");
          String value = node.getStringValue();
          assertEquals("aaaaaa", value);
        }
        {
          DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./EmailXXX/MyPushEmail/BBB");
          assertNull(node);
        }
        {
          DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./Email/MyPushEmail/CCC");
          String value = node.getStringValue();
          assertEquals("cccccc", value);
        }
        {
          DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./Email/MyPushEmail/DDD");
          String value = node.getStringValue();
          assertEquals("dddddd", value);
        }
      }
      factory.release();
    }

    // Test Delete
    {
      File commandFile = new File(AllTests.BASE_DIR, "metadata/processor/test/test.4.command.xml");
      File resultFile = new File(AllTests.BASE_DIR, "metadata/processor/test/test.4.results.xml");

      // Emulate the the process between OMAClient and Processor.
      emulate(commandFile, resultFile);

      // Assert result & DM inventory
      ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
      DeviceBean deviceBean = factory.createDeviceBean();
      Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
      {
        {
          DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./Email/MyPushEmail/AAA");
          assertNull(node);
        }
        {
          DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./Email/MyPushEmail/CCC");
          String value = node.getStringValue();
          assertEquals("cccccc", value);
        }
        {
          DeviceTreeNode node = deviceBean.findDeviceTreeNode(device.getID(), "./Email/MyPushEmail/DDD");
          assertNull(node);
        }
      }
      factory.release();
    }
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(TestCommandProcessor.class);
  }

}
