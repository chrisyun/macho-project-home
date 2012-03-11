package com.npower.dm.setup.task;

import java.util.ArrayList;
import java.util.List;
import com.npower.dm.core.DMException;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.setup.console.ConsoleImpl;
import com.npower.setup.core.Console;
import com.npower.setup.core.Setup;
import com.npower.setup.core.SetupException;
import com.npower.setup.core.SetupImpl;
import junit.framework.TestCase;

public class TestSoftwareVenderTask extends TestCase {

  private  ManagementBeanFactory factory = null;
 
  private SoftwareBean softwareBean = null;
  
  private static String softwareVenderDataFile = "D:/Zhao/MyWorkspace/OTAS-Setup-Common/metadata/test/dm/softwareVenders/softwareVender.xml";
  
  private String softwareVender_name_1 = "诺基亚";
  
  private String softwareVender_description_1 ="诺基亚是全球最大的手机厂商。";
  
  private String softwareVender_name_2 = "摩托罗拉";
  
  private String softwareVender_description_2 ="摩托罗拉是全球第二的手机厂商。";
  
  private String softwareVender_name_3 = "三星";
  
  private String softwareVender_description_3 ="三星是韩国最大的手机厂商。";
  
    
  public TestSoftwareVenderTask(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
    System.setProperty("otas.dm.home", "D:/OTASDM");
    factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
    softwareBean = factory.createSoftwareBean();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testLoadSoftwareVenderItems() throws SetupException {
    SoftwareVenderTask task = new SoftwareVenderTask();
    List<SoftwareVenderItem> softwareVenders  = task.loadSoftwareVenderImporter(softwareVenderDataFile);
    
    assertNotNull(softwareVenders);
    assertTrue(softwareVenders.size() == 3);
    
    SoftwareVenderItem sv = (SoftwareVenderItem)softwareVenders.get(0);
    assertEquals(sv.getName(), softwareVender_name_1);
    assertEquals(sv.getDescription(), softwareVender_description_1);
    
    sv = (SoftwareVenderItem)softwareVenders.get(1);
    assertEquals(sv.getName(), softwareVender_name_2);
    assertEquals(sv.getDescription(), softwareVender_description_2);
    
    sv = (SoftwareVenderItem)softwareVenders.get(2);
    assertEquals(sv.getName(), softwareVender_name_3);
    assertEquals(sv.getDescription(), softwareVender_description_3);
     
  }
  
  public void testCopy() throws SetupException, DMException{
    SoftwareVenderTask task = new SoftwareVenderTask();
    List<SoftwareVenderItem> softwareVenderItems = task.loadSoftwareVenderImporter(softwareVenderDataFile);    
    List<SoftwareVendor> resultList = new ArrayList<SoftwareVendor>();
 
    assertNotNull(softwareVenderItems);
    assertTrue(softwareVenderItems.size() == 3);
       
    for (SoftwareVenderItem item: softwareVenderItems) {
      SoftwareVendor softwareVender = softwareBean.newVendorInstance();
      task.copy(item, softwareVender);
      resultList.add(softwareVender);
    }
    
    assertNotNull(resultList);
    assertTrue(resultList.size() == 3); 
      
    SoftwareVendor sv = (SoftwareVendor)resultList.get(0);
    assertEquals(sv.getName(), softwareVender_name_1);
    assertEquals(sv.getDescription(), softwareVender_description_1);
    
    sv = (SoftwareVendor)resultList.get(1);
    assertEquals(sv.getName(), softwareVender_name_2);
    assertEquals(sv.getDescription(), softwareVender_description_2);
    
    sv = (SoftwareVendor)resultList.get(2);
    assertEquals(sv.getName(), softwareVender_name_3);
    assertEquals(sv.getDescription(), softwareVender_description_3);
    
  }
  
  public void testProcessSoftwareVenders() throws SetupException, DMException {
    Setup setup = new SetupImpl();
    Console console = new ConsoleImpl();
    setup.setConsole(console);
    
    SoftwareVenderTask task = new SoftwareVenderTask();
    task.setSetup(setup);
    
    List<String> filenames = new ArrayList<String>();
    filenames.add(softwareVenderDataFile);
    task.setFilenames(filenames);
    task.setManagementBeanFactory(factory);  
    task.process();    
    
    factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
    softwareBean = factory.createSoftwareBean();
    List<SoftwareVendor> softwareVendorsList = softwareBean.getAllOfVendors();
    assertNotNull(softwareVendorsList);
    assertTrue(softwareVendorsList.size() == 3); 

    SoftwareVendor sv = (SoftwareVendor)softwareVendorsList.get(0);
    assertEquals(sv.getName(), softwareVender_name_1);
    assertEquals(sv.getDescription(), softwareVender_description_1);
    
    sv = (SoftwareVendor)softwareVendorsList.get(1);
    assertEquals(sv.getName(), softwareVender_name_2);
    assertEquals(sv.getDescription(), softwareVender_description_2);
    
    sv = (SoftwareVendor)softwareVendorsList.get(2);
    assertEquals(sv.getName(), softwareVender_name_3);
    assertEquals(sv.getDescription(), softwareVender_description_3);
    
  }
}
