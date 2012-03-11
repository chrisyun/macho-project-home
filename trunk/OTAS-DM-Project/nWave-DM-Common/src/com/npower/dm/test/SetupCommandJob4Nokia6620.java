/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/test/SetupCommandJob4Nokia6620.java,v 1.12 2008/08/04 07:13:34 zhao Exp $
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
package com.npower.dm.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.SessionContext;
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
import com.npower.dm.processor.CommandProcessor;
import com.npower.dm.processor.GenericProcessor;
import com.npower.dm.processor.JobProcessSelector;
import com.npower.dm.processor.ScriptManagementProcessor;

/**
 * Setup a command job for Nokia 6620 real testing.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.12 $ $Date: 2008/08/04 07:13:34 $
 */
public class SetupCommandJob4Nokia6620 extends TestCase {
  
  private static Log log = LogFactory.getLog(SetupCommandJob4Nokia6620.class);

  //private String FILENAME_SCRIPT = "/metadata/command/test/nokia/test.replace.xml";
  private String FILENAME_SCRIPT = "/metadata/command/test/nokia/6620.email.profile.xml";

  /**
   * 
   */
  private static final String CLIENT_PASSWORD = "sync4j";
  /**
   * 
   */
  private static final String CLIENT_USERNAME = "sync4j";

  private String manufacturerExternalID = "NOKIA";
  private String modelExternalID = "6620";
  private String deviceExternalID = "IMEI:353755000569915";
  private String carrierExternalID = "TestCarrier";

  private String serverPassword = "srvpwd";

  private long jobID4CommandJob = 0;

  private JobProcessSelector selector = null;  
  /**
   * 
   */
  public SetupCommandJob4Nokia6620() {
    super();
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
        
        CarrierBean carrierBean = factory.createCarrierBean();
        Carrier carrier = carrierBean.newCarrierInstance(country, carrierExternalID, carrierExternalID);
        factory.beginTransaction();
        carrierBean.update(carrier);
        factory.commit();
        
        SubscriberBean subscriberBean = factory.createSubcriberBean();
        Subscriber subscriber = subscriberBean.newSubscriberInstance(carrier, deviceExternalID, "" + System.currentTimeMillis(), "password");
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
    } catch (RuntimeException e) {
      factory.rollback();
      throw e;
    } finally {    
      factory.release();
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

  private void setupCommandJob() throws Exception {
    // Testcase: add a Job
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();

    try {
        DeviceBean deviceBean = factory.createDeviceBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        
        // Create devicegroup and job ...
        factory.beginTransaction();
        // Create a device group
        DeviceGroup group = deviceBean.newDeviceGroup();
        deviceBean.add(group, device);
        deviceBean.update(group);
        
        // Create a command job
        String script = readScript(new File(AllTests.BASE_DIR, FILENAME_SCRIPT));
        ProvisionJob job = jobBean.newJob4Command(group, script);
        jobBean.update(job);
        this.jobID4CommandJob = job.getID();
        assertTrue(this.jobID4CommandJob > 0);
        
        factory.commit();
        
        // Testcase: load a Job
        job = jobBean.loadJobByID(this.jobID4CommandJob);
        assertNotNull(job);
        assertEquals(ProvisionJob.JOB_STATE_APPLIED, job.getState());
        assertEquals(ProvisionJob.JOB_ELEMENT_TYPE_MULTIPLE, job.getTargetType());
        assertEquals(ProvisionJob.JOB_TYPE_SCRIPT, job.getJobType());
        assertEquals(script, job.getScriptString());
    } catch (RuntimeException e) {
      factory.rollback();
      throw e;
    } finally {    
      factory.release();
    }
    
    
  }

  private void setupProcessorSelector() throws Exception {
    selector = new JobProcessSelector();
    selector.setDefaultProcessor(GenericProcessor.class.getName());
    selector.setErrorProcessor(ScriptManagementProcessor.class.getName());
    //selector.setJobProcessor();
  }
  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    
    try {
        this.clearEntities();
        // Create carrier, manufacturer, model, device, subscriber
        this.setupEntities();
        this.setupCommandJob();
        this.setupProcessorSelector();
     } catch (Exception e) {
       log.info("SetupCommandJob4Nokia6620:" + e);
    }
  }
  
  private void clearEntities() throws Exception {
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
      log.info("forbidden:" + e);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    
  }
  
  public void test() throws Exception {
    // Test TreeDiscoveryProcessor
    DevInfo devInfo = new DevInfo(deviceExternalID, manufacturerExternalID, modelExternalID, "dmV", "lang");
    DeviceDMState dmState = new DeviceDMState(deviceExternalID);
    ManagementProcessor processor = this.selector.getProcessor(dmState, devInfo, null);
    assertTrue(processor instanceof CommandProcessor);
    assertEquals(this.jobID4CommandJob, Long.parseLong(dmState.mssid));
    
    String sessionID = "00001";
    Principal principal = new Sync4jPrincipal(CLIENT_USERNAME, CLIENT_PASSWORD, deviceExternalID);
    int sessionType = 0;
    SessionContext session = new SessionContext(sessionID, sessionID, (Sync4jPrincipal)principal, sessionType, devInfo, dmState, null, null);
    processor.beginSession(session);
    ManagementOperation[] operations = processor.getNextOperations();
    assertTrue(operations.length > 1);
    
  }

}
