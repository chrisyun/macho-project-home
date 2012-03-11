package com.npower.dm;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.cp.TestTemplateMerge;
import com.npower.cp.TestVelocityTemplateMerger;
import com.npower.cp.TestXMLInventoryDB;
import com.npower.cp.TestXmlOtaInventory;
import com.npower.cp.convertor.TestProfileConvertor4DM;
import com.npower.cp.convertor.TestProfileConvertor4Email;
import com.npower.cp.convertor.TestProfileConvertor4MMS;
import com.npower.cp.convertor.TestProfileConvertor4NAP;
import com.npower.cp.convertor.TestProfileConvertor4Proxy;
import com.npower.cp.convertor.TestProfileConvertor4SyncDS;
import com.npower.cp.convertor.TestProfileConvertorFactory;
import com.npower.cp.template.TestDMTemplate;
import com.npower.cp.template.TestEmailTemplate;
import com.npower.cp.template.TestImpsTemplate;
import com.npower.cp.template.TestMMSTemplate;
import com.npower.cp.template.TestNAPemplate;
import com.npower.cp.template.TestPocTemplate;
import com.npower.cp.template.TestProxyTemplate;
import com.npower.cp.template.TestSyncDSTemplate;
import com.npower.dl.TestDownloadURLCaculatorImpl;
import com.npower.dl.TestParser;
import com.npower.dl.TestSoftwareDownloadServlet;
import com.npower.dm.audit.TestDeviceLogger;
import com.npower.dm.audit.TestSecurityLogger;
import com.npower.dm.client.TestDMClientEmulatorImpl;
import com.npower.dm.command.TestCompiler4CommandScript;
import com.npower.dm.core.DMException;
import com.npower.dm.decorator.TestCountryDecorator;
import com.npower.dm.hibernate.entity.TestManufacturerIdentifierGenerator;
import com.npower.dm.hibernate.entity.TestModelIdentifierGenerator;
import com.npower.dm.hibernate.management.TestDMBinary;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.TestAutomaticProvisionJobBean;
import com.npower.dm.management.TestCarrierManagementBean;
import com.npower.dm.management.TestClientProvTemplateBean;
import com.npower.dm.management.TestCountryManagementBean;
import com.npower.dm.management.TestDDFTreeManagementBean;
import com.npower.dm.management.TestDeviceBean;
import com.npower.dm.management.TestDeviceBeanBind;
import com.npower.dm.management.TestHibernateAutoCommit;
import com.npower.dm.management.TestModelManagementBean;
import com.npower.dm.management.TestModelSpecManagementBean;
import com.npower.dm.management.TestOTAClientProvJobBean;
import com.npower.dm.management.TestPhoneNumberPolicy;
import com.npower.dm.management.TestProfileAssignmentBean;
import com.npower.dm.management.TestProfileConfigBean;
import com.npower.dm.management.TestProfileMapping;
import com.npower.dm.management.TestProfileTemplateManagementBean;
import com.npower.dm.management.TestProvisionJobBean;
import com.npower.dm.management.TestProvisionJobBean4ProfileAssignment;
import com.npower.dm.management.TestProvisionJobBean4SoftwareManagement;
import com.npower.dm.management.TestRegularExpress;
import com.npower.dm.management.TestServiceProviderBean;
import com.npower.dm.management.TestSoftwareBean4Category;
import com.npower.dm.management.TestSoftwareBean4CategoryCase2;
import com.npower.dm.management.TestSoftwareBean4Package;
import com.npower.dm.management.TestSoftwareBean4ScreenShot;
import com.npower.dm.management.TestSoftwareBean4Software;
import com.npower.dm.management.TestSoftwareBean4Vendor;
import com.npower.dm.management.TestSoftwareTopListBean4Recommend;
import com.npower.dm.management.TestSoftwareTrackingBean;
import com.npower.dm.management.TestSubscriberBean;
import com.npower.dm.management.TestUpdateImageBean;
import com.npower.dm.msm.TestS60V3InstallProcessor4DL;
import com.npower.dm.msm.TestSoftwareManagementJobAdapter;
import com.npower.dm.processor.TestCommandProcessor;
import com.npower.dm.processor.TestFumoProcessor;
import com.npower.dm.processor.TestGenericProcessor;
import com.npower.dm.processor.TestJobProcessSelector;
import com.npower.dm.processor.TestOracleClob;
import com.npower.dm.processor.TestProfileAssignmentProcessor;
import com.npower.dm.processor.TestProfileReAssignmentProcessor;
import com.npower.dm.processor.TestRegistry;
import com.npower.dm.processor.TestSmartProfileAssignmentProcessor;
import com.npower.dm.processor.TestSmartProfileAssignmentProcessor4Motorola;
import com.npower.dm.processor.TestSmartProfileReAssignmentProcessor;
import com.npower.dm.processor.TestTreeDiscoveryProcessor;
import com.npower.dm.security.TestSecurityOfficer;
import com.npower.dm.server.engine.TestDMManagementEngine;
import com.npower.dm.server.store.TestDevicePersistentStore;
import com.npower.dm.server.synclet.TestAutoRegDeviceSynclet;
import com.npower.dm.server.synclet.TestCarrierDetector;
import com.npower.dm.server.synclet.TestHttpHeaderPhoneNumberParser;
import com.npower.dm.server.synclet.TestSimplePhoneNumberParser;
import com.npower.dm.server.synclet.TestTacInfoSynclet;
import com.npower.dm.tracking.TestAccessLogger;
import com.npower.dm.util.TestDDFTreeHelper;
import com.npower.dm.util.TestIMEIUtil;
import com.npower.dm.util.TestNumberGenerator;
import com.npower.dm.util.TestPropertyMessageResources;
import com.npower.dm.util.TestUtil;
import com.npower.dm.xml.TestLocalEntityResolver;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.44 $ $Date: 2008/12/15 03:24:14 $
 */
public class AllTests {

  private static Log   log                       = LogFactory.getLog(AllTests.class);
  
  /**
   * Location of hibernate.properties file. NOTICE: Location should be on the
   * classpath as Hibernate uses #resourceAsStream style lookup for its
   * configuration file. That is place the config file in a Java package - the
   * default location is the default Java package.<br>
   */
  private static String DEFAULT_PROPERTIES_FILE_LOCATION = "com/npower/dm/hibernate/hibernate.properties";

  private static Properties properties = null;

  public static String BASE_DIR = "D:/Zhao/MyWorkspace/nWave-DM-Common";
  
  public static ManagementBeanFactory getManagementBeanFactory() throws DMException {
    ManagementBeanFactory factory = ManagementBeanFactory.newInstance(getProperties4ManagementBeanFactory());
    return factory;
  }

  /**
   * @return
   * @throws DMException
   */
  private synchronized static Properties getProperties4ManagementBeanFactory() throws DMException {
    if (properties != null) {
       return properties;
    }
    
    properties = new Properties(System.getProperties());
    try {
      log.info("Using default hibernate properties: " + DEFAULT_PROPERTIES_FILE_LOCATION);
      InputStream propertiesIns = null;
      propertiesIns = AllTests.class.getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES_FILE_LOCATION);
      if (propertiesIns == null) {
         throw new DMException("Could not found default hibernate properties: " + DEFAULT_PROPERTIES_FILE_LOCATION);
      }
      properties.load(propertiesIns);
      return properties;
    } catch (IOException e) {
      throw new DMException("Could not load properties.", e);
    }
  }
  
  /**
   * 
   * @return
   */
  public static Test suite() {
    TestSuite suite = new TestSuite("All Tests for com.npower.dm.management");
    // $JUnit-BEGIN$
    
    suite.addTestSuite(TestHibernateAutoCommit.class);
    
    // Test IdentifierGenerator for Hibernate
    suite.addTestSuite(TestModelIdentifierGenerator.class);
    suite.addTestSuite(TestManufacturerIdentifierGenerator.class);

    // TestSuite for com.npower.dm.management.Test*
    suite.addTestSuite(TestCountryManagementBean.class);
    suite.addTestSuite(TestCarrierManagementBean.class);
    suite.addTestSuite(TestServiceProviderBean.class);
    suite.addTestSuite(TestPhoneNumberPolicy.class);
    suite.addTestSuite(TestProfileTemplateManagementBean.class);
    suite.addTestSuite(TestModelManagementBean.class);
    suite.addTestSuite(TestModelSpecManagementBean.class);
    suite.addTestSuite(TestDDFTreeManagementBean.class);
    suite.addTestSuite(TestDDFTreeHelper.class);
    suite.addTestSuite(TestRegistry.class);
    suite.addTestSuite(TestProfileMapping.class);
    suite.addTestSuite(TestProfileConfigBean.class);
    suite.addTestSuite(TestSubscriberBean.class);
    suite.addTestSuite(TestDeviceBean.class);
    suite.addTestSuite(TestDeviceBeanBind.class);
    suite.addTestSuite(TestProfileAssignmentBean.class);
    suite.addTestSuite(TestUpdateImageBean.class);
    //suite.addTestSuite(TestDeviceLogManagementBean.class);
    suite.addTestSuite(TestProvisionJobBean.class);
    suite.addTestSuite(TestProvisionJobBean4ProfileAssignment.class);
    suite.addTestSuite(TestAutomaticProvisionJobBean.class);
    suite.addTestSuite(TestClientProvTemplateBean.class);
    suite.addTestSuite(TestSoftwareBean4Category.class);
    suite.addTestSuite(TestSoftwareBean4CategoryCase2.class);
    suite.addTestSuite(TestSoftwareBean4Vendor.class);
    suite.addTestSuite(TestSoftwareBean4Software.class);
    suite.addTestSuite(TestSoftwareBean4Package.class);
    suite.addTestSuite(TestSoftwareBean4ScreenShot.class);
    suite.addTestSuite(TestSoftwareTrackingBean.class);
    suite.addTestSuite(TestSoftwareTopListBean4Recommend.class);
    suite.addTestSuite(TestOTAClientProvJobBean.class);
    suite.addTestSuite(TestProvisionJobBean4SoftwareManagement.class);
    
    // TestSuite for com.npower.dm.server.store.Test*
    suite.addTestSuite(TestDevicePersistentStore.class);
    
    // TestSuite for com.npower.dm.security.Test*
    suite.addTestSuite(TestSecurityOfficer.class);
    
    // TestSuite for com.npower.dm.processor.Test*
    suite.addTestSuite(TestGenericProcessor.class);
    suite.addTestSuite(TestTreeDiscoveryProcessor.class);
    suite.addTestSuite(TestCommandProcessor.class);
    suite.addTestSuite(TestJobProcessSelector.class);
    suite.addTestSuite(TestProfileAssignmentProcessor.class);
    suite.addTestSuite(TestProfileReAssignmentProcessor.class);
    suite.addTestSuite(TestSmartProfileAssignmentProcessor.class);
    suite.addTestSuite(TestSmartProfileReAssignmentProcessor.class);
    suite.addTestSuite(TestSmartProfileAssignmentProcessor4Motorola.class);
    suite.addTestSuite(TestSoftwareManagementJobAdapter.class);
    suite.addTestSuite(TestS60V3InstallProcessor4DL.class);
    
    suite.addTestSuite(TestOracleClob.class);
    
    // TestSuite for com.npower.dm.commdn.Test*
    suite.addTestSuite(TestCompiler4CommandScript.class);
    
    // TestSuite for com.npower.dm.server.engine.Test*
    suite.addTestSuite(TestDMManagementEngine.class);
    
    // TestSuite for com.npower.dm.client.Test*
    suite.addTestSuite(TestDMClientEmulatorImpl.class);
    
    // Test Case for Blob.
    suite.addTestSuite(TestDMBinary.class);
    
    // Test Case for Firmware Job.
    //suite.addTestSuite(TestJob4Firmware.class);
    
    // Test Case for SimplePhoneNumberParser
    suite.addTestSuite(TestSimplePhoneNumberParser.class);
    suite.addTestSuite(TestHttpHeaderPhoneNumberParser.class);

    // Test Case for TacInfoSynclet
    suite.addTestSuite(TestTacInfoSynclet.class);
    
    // Test Case for CarrierDetector
    suite.addTestSuite(TestCarrierDetector.class);

    // Test Case for AutoRegDeviceSynclet
    suite.addTestSuite(TestAutoRegDeviceSynclet.class);
    
    // Test Cases for CP Inventory
    suite.addTestSuite(TestTemplateMerge.class);
    suite.addTestSuite(TestXmlOtaInventory.class);
    suite.addTestSuite(TestXMLInventoryDB.class);
    suite.addTestSuite(TestVelocityTemplateMerger.class);
    
    // Test Cases for CP Template
    suite.addTestSuite(TestDMTemplate.class);
    suite.addTestSuite(TestEmailTemplate.class);
    suite.addTestSuite(TestImpsTemplate.class);
    suite.addTestSuite(TestMMSTemplate.class);
    suite.addTestSuite(TestNAPemplate.class);
    suite.addTestSuite(TestPocTemplate.class);
    suite.addTestSuite(TestProxyTemplate.class);
    suite.addTestSuite(TestSyncDSTemplate.class);
   
    // Test cases for Number Generator
    suite.addTestSuite(TestNumberGenerator.class);
    suite.addTestSuite(TestIMEIUtil.class);
    suite.addTestSuite(TestRegularExpress.class);
    suite.addTestSuite(TestUtil.class);

    // Test cases for Audit Log
    suite.addTestSuite(TestDeviceLogger.class);
    suite.addTestSuite(TestSecurityLogger.class);

    // Test cases for OMA Download
    suite.addTestSuite(TestParser.class);
    suite.addTestSuite(TestSoftwareDownloadServlet.class);
    
    suite.addTestSuite(TestDownloadURLCaculatorImpl.class);
    suite.addTestSuite(TestFumoProcessor.class);
    
    // Test cases for convertor from ProfileConfig to OTAProvSettings
    suite.addTestSuite(TestProfileConvertorFactory.class);
    suite.addTestSuite(TestProfileConvertor4NAP.class);
    suite.addTestSuite(TestProfileConvertor4Proxy.class);
    suite.addTestSuite(TestProfileConvertor4MMS.class);
    suite.addTestSuite(TestProfileConvertor4Email.class);
    suite.addTestSuite(TestProfileConvertor4SyncDS.class);
    suite.addTestSuite(TestProfileConvertor4DM.class);
    
    suite.addTestSuite(TestPropertyMessageResources.class);
    
    suite.addTestSuite(TestCountryDecorator.class);
    
    // Access Log
    suite.addTestSuite(TestAccessLogger.class);
    
    //suite.addTestSuite(TestValidationXml.class);
    suite.addTestSuite(TestLocalEntityResolver.class);
    
    // $JUnit-END$
    return suite;
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(AllTests.suite());
  }

}
