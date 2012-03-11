/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/TestSoftwareTask.java,v 1.4 2008/12/16 04:19:41 chenlei Exp $
 * $Revision: 1.4 $
 * $Date: 2008/12/16 04:19:41 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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

package com.npower.dm.setup.task;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import junit.framework.TestCase;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.setup.console.ConsoleImpl;
import com.npower.setup.core.Console;
import com.npower.setup.core.Setup;
import com.npower.setup.core.SetupException;
import com.npower.setup.core.SetupImpl;

/**
 * @author chenlei
 * @version $Revision: 1.4 $ $Date: 2008/12/16 04:19:41 $
 */

public class TestSoftwareTask extends TestCase {
  private  ManagementBeanFactory factory = null;
  
  private SoftwareBean softwareBean = null;
  private static String softwareFile = "D:/Zhao/MyWorkspace/OTAS-Setup-Common/metadata/test/dm/Software/Software.default.xml";
  
  private String software_1_id = "MSN";
  private String software_1_vendor = "微软公司";
  private String software_1_category = "网络工具/网络加速";
  private String software_1_name = "MSN Live Messager";
  private String software_1_version = "3.5";
  private String software_1_status = "release";
  private String software_1_licensetype = "test";
  private String software_1_description = "天下网游加速器是针对 玩网络游戏卡,经常掉线,看电影慢,经常中断,开网页,经常无法正常浏览";
  private String software_1_package1_name = "Nokia";
  private String software_1_package1_url = "http://www.otas.mobi:8080/download/msn_mobile_3.5_for_nokia_6120_544.sisx";
  private String software_1_package1_InstallOptions =  "<InstOpts>"+
  "<StdOpt name=\"drive\" value=\"!\"/>"+
  "<StdOpt name=\"lang\" value=\"*\"/>"+
  "<StdOpt name=\"upgrade\" value=\"yes\"/>"+
  "<StdOpt name=\"kill\" value=\"yes\"/>"+
  "<StdSymOpt name=\"pkginfo\" value=\"yes\"/>"+
  "<StdSymOpt name=\"optionals\" value=\"yes\"/>"+
  "<StdSymOpt name=\"ocsp\" value=\"no\"/>"+
  "<StdSymOpt name=\"capabilities\" value=\"yes\"/>"+
  "<StdSymOpt name=\"untrusted\" value=\"yes\"/>"+
  "<StdSymOpt name=\"ignoreocspwarn\" value=\"yes\"/>"+
  "<StdSymOpt name=\"ignorewarn\" value=\"yes\"/>"+
  "<StdSymOpt name=\"fileoverwrite\" value=\"yes\"/></InstOpts>";
  private String software_1_package1_targetModel1_vendow = "Dopod";
  private String software_1_package1_targetModel1_name = "515";
  
  
  private String software_2_id = "12831393";
  private String software_2_vendor = "北京同众科技有限公司";
  private String software_2_category = "应用软件/文档管理";
  private String software_2_name = "掌讯通 SmartPhone";
  private String software_2_version = "1.40";
  private String software_2_status = "release";
  private String software_2_licensetype = "test";
  private String software_2_description = "批量文档整理器对同一文件夹内的大批量文本文件的内容进行规律性整理。";
  private String software_2_package1_name = "掌讯通 SmartPhone 2003";
  private String software_2_package1_local_MimeType = "application/x-pip";
  private String software_2_package1_local_File = "./handcn_sp2003.cab";
  private String software_2_package1_InstallOptions =  "<InstOpts>"+
  "<StdOpt name=\"drive\" value=\"!\"/>"+
  "<StdOpt name=\"lang\" value=\"*\"/>"+
  "<StdOpt name=\"upgrade\" value=\"yes\"/>"+
  "<StdOpt name=\"kill\" value=\"yes\"/>"+
  "<StdSymOpt name=\"pkginfo\" value=\"yes\"/>"+
  "<StdSymOpt name=\"optionals\" value=\"yes\"/>"+
  "<StdSymOpt name=\"ocsp\" value=\"no\"/>"+
  "<StdSymOpt name=\"capabilities\" value=\"yes\"/>"+
  "<StdSymOpt name=\"untrusted\" value=\"yes\"/>"+
  "<StdSymOpt name=\"ignoreocspwarn\" value=\"yes\"/>"+
  "<StdSymOpt name=\"ignorewarn\" value=\"yes\"/>"+
  "<StdSymOpt name=\"fileoverwrite\" value=\"yes\"/></InstOpts>";
  private String software_2_package1_targetModel1_vendow = "Dopod";
  private String software_2_package1_targetModel1_name = "515";
  private String software_2_package1_targetModel2_vendow = "Dopod";
  private String software_2_package1_targetModel2_name = "535";
  private String software_2_package2_name = "掌讯通 SP5.0";
  private String software_2_package2_local_MimeType = "application/x-pip";
  private String software_2_package2_local_File = "./handcn_sp5.0.cab";
  private String software_2_package2_InstallOptions =  "<InstOpts>"+
  "<StdOpt name=\"drive\" value=\"!\"/>"+
  "<StdOpt name=\"lang\" value=\"*\"/>"+
  "<StdOpt name=\"upgrade\" value=\"yes\"/>"+
  "<StdOpt name=\"kill\" value=\"yes\"/>"+
  "<StdSymOpt name=\"pkginfo\" value=\"yes\"/>"+
  "<StdSymOpt name=\"optionals\" value=\"yes\"/>"+
  "<StdSymOpt name=\"ocsp\" value=\"no\"/>"+
  "<StdSymOpt name=\"capabilities\" value=\"yes\"/>"+
  "<StdSymOpt name=\"untrusted\" value=\"yes\"/>"+
  "<StdSymOpt name=\"ignoreocspwarn\" value=\"yes\"/>"+
  "<StdSymOpt name=\"ignorewarn\" value=\"yes\"/>"+
  "<StdSymOpt name=\"fileoverwrite\" value=\"yes\"/></InstOpts>";
  private String software_2_package2_targetModel1_vendow = "Dopod";
  private String software_2_package2_targetModel1_name = "586";
  private String software_2_package2_targetModel2_vendow = "Dopod";
  private String software_2_package2_targetModel2_name = "586W";
  

  
  /**
   * @param name
   */
  public TestSoftwareTask(String name) {
    super(name);
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    System.setProperty("otas.dm.home", "D:/OTASDM");
    factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
    softwareBean = factory.createSoftwareBean();
  }

  public void testLoadSoftwareItems() throws SetupException {
    SoftwareTask task = new SoftwareTask();
    List<SoftwareItem> items = task.loadSoftwareItems(softwareFile);
    assertNotNull(items);
    assertTrue(items.size() == 2);
    
    //第一个软件
    SoftwareItem item = items.get(0);
    assertEquals(item.getExternalId(), this.software_1_id);
    assertEquals(item.getVendor(), this.software_1_vendor);
    assertEquals(item.getCategory(), this.software_1_category);
    assertEquals(item.getName(), this.software_1_name);
    assertEquals(item.getVersion(), this.software_1_version);
    assertEquals(item.getStatus(), this.software_1_status);
    assertEquals(item.getLicenseType(), this.software_1_licensetype);
    assertEquals(item.getDescription(), this.software_1_description);
    SoftwarePackageItem pkgItem1 = item.getSoftwarePackage().get(0);
    assertEquals(pkgItem1.getName(), this.software_1_package1_name);
    assertEquals(pkgItem1.getRemote().getUrl(), this.software_1_package1_url);
    
    for (int i = 0; i < pkgItem1.getInstallOptions().size(); i ++) {
      SoftwarePackage4InstallOptionsItem io = pkgItem1.getInstallOptions().get(i);
      StringBuffer buf = new StringBuffer();
      buf.append("<InstOpts>");
      for (int j = 0; j < io.getStdOpt().size(); j ++) {
        SoftwarePackage4StdOptItem so = io.getStdOpt().get(j);
        buf.append("<StdOpt name=");
        buf.append('"');
        buf.append(so.getName());
        buf.append('"');
        buf.append(" value=");
        buf.append('"');
        buf.append(so.getValue());
        buf.append('"');
        buf.append("/>");
      }
      for (int t = 0; t < io.getStdSymOpt().size(); t ++) {
        SoftwarePackage4StdSymOptItem ss = io.getStdSymOpt().get(t);
        buf.append("<StdSymOpt name=");
        buf.append('"');
        buf.append(ss.getName());
        buf.append('"');
        buf.append(" value=");
        buf.append('"');
        buf.append(ss.getValue());
        buf.append('"');
        buf.append("/>");
      }
      buf.append("</InstOpts>");
      assertEquals(buf.toString(), this.software_1_package1_InstallOptions);
    }
    
    SoftwarePackage4TargetModelsItem tms = pkgItem1.getModels().get(0);
    assertEquals(tms.getVendor(), this.software_1_package1_targetModel1_vendow);
    assertEquals(tms.getName(), this.software_1_package1_targetModel1_name);
    
    //第二个软件
    SoftwareItem item1 = items.get(1);
    assertEquals(item1.getExternalId(), this.software_2_id);
    assertEquals(item1.getVendor(), this.software_2_vendor);
    assertEquals(item1.getCategory(), this.software_2_category);
    assertEquals(item1.getName(), this.software_2_name);
    assertEquals(item1.getVersion(), this.software_2_version);
    assertEquals(item1.getStatus(), this.software_2_status);
    assertEquals(item1.getLicenseType(), this.software_2_licensetype);
    assertEquals(item1.getDescription(), this.software_2_description);
    SoftwarePackageItem pkgItem3 = item1.getSoftwarePackage().get(0);
    assertEquals(pkgItem3.getName(), this.software_2_package1_name);
    assertEquals(pkgItem3.getLocal().getMimeType(), this.software_2_package1_local_MimeType);
    assertEquals(pkgItem3.getLocal().getFile(), this.software_2_package1_local_File);
    
    
    for (int i = 0; i < pkgItem3.getInstallOptions().size(); i ++) {
      SoftwarePackage4InstallOptionsItem io = pkgItem1.getInstallOptions().get(i);
      StringBuffer buf = new StringBuffer();
      buf.append("<InstOpts>");
      for (int j = 0; j < io.getStdOpt().size(); j ++) {
        SoftwarePackage4StdOptItem so = io.getStdOpt().get(j);
        buf.append("<StdOpt name=");
        buf.append('"');
        buf.append(so.getName());
        buf.append('"');
        buf.append(" value=");
        buf.append('"');
        buf.append(so.getValue());
        buf.append('"');
        buf.append("/>");
      }
      for (int t = 0; t < io.getStdSymOpt().size(); t ++) {
        SoftwarePackage4StdSymOptItem ss = io.getStdSymOpt().get(t);
        buf.append("<StdSymOpt name=");
        buf.append('"');
        buf.append(ss.getName());
        buf.append('"');
        buf.append(" value=");
        buf.append('"');
        buf.append(ss.getValue());
        buf.append('"');
        buf.append("/>");
      }
      buf.append("</InstOpts>");
      assertEquals(buf.toString(), this.software_2_package1_InstallOptions);
    }
    
    SoftwarePackage4TargetModelsItem tms1 = pkgItem3.getModels().get(0);
    assertEquals(tms1.getVendor(), this.software_2_package1_targetModel1_vendow);
    assertEquals(tms1.getName(), this.software_2_package1_targetModel1_name);
    SoftwarePackage4TargetModelsItem tms2 = pkgItem3.getModels().get(1);
    assertEquals(tms2.getVendor(), this.software_2_package1_targetModel2_vendow);
    assertEquals(tms2.getName(), this.software_2_package1_targetModel2_name);
    SoftwarePackageItem pkgItem4 = item1.getSoftwarePackage().get(1);
    assertEquals(pkgItem4.getName(), this.software_2_package2_name);
    assertEquals(pkgItem4.getLocal().getMimeType(), this.software_2_package2_local_MimeType);
    assertEquals(pkgItem4.getLocal().getFile(), this.software_2_package2_local_File);
    
    
    for (int i = 0; i < pkgItem4.getInstallOptions().size(); i ++) {
      SoftwarePackage4InstallOptionsItem io = pkgItem1.getInstallOptions().get(i);
      StringBuffer buf = new StringBuffer();
      buf.append("<InstOpts>");
      for (int j = 0; j < io.getStdOpt().size(); j ++) {
        SoftwarePackage4StdOptItem so = io.getStdOpt().get(j);
        buf.append("<StdOpt name=");
        buf.append('"');
        buf.append(so.getName());
        buf.append('"');
        buf.append(" value=");
        buf.append('"');
        buf.append(so.getValue());
        buf.append('"');
        buf.append("/>");
      }
      for (int t = 0; t < io.getStdSymOpt().size(); t ++) {
        SoftwarePackage4StdSymOptItem ss = io.getStdSymOpt().get(t);
        buf.append("<StdSymOpt name=");
        buf.append('"');
        buf.append(ss.getName());
        buf.append('"');
        buf.append(" value=");
        buf.append('"');
        buf.append(ss.getValue());
        buf.append('"');
        buf.append("/>");
      }
      buf.append("</InstOpts>");
      assertEquals(buf.toString(), this.software_2_package2_InstallOptions);
    }
    
    SoftwarePackage4TargetModelsItem tms3 = pkgItem4.getModels().get(0);
    assertEquals(tms3.getVendor(), this.software_2_package2_targetModel1_vendow);
    assertEquals(tms3.getName(), this.software_2_package2_targetModel1_name);
    SoftwarePackage4TargetModelsItem tms4 = pkgItem4.getModels().get(1);
    assertEquals(tms4.getVendor(), this.software_2_package2_targetModel2_vendow);
    assertEquals(tms4.getName(), this.software_2_package2_targetModel2_name);
  }
  
  
  public void testCopy() throws Exception {
    SoftwareTask task = new SoftwareTask();
    List<SoftwareItem> items = task.loadSoftwareItems(softwareFile);
    assertNotNull(items);
    assertTrue(items.size() == 2);
    List<Software> resultList = new ArrayList<Software>();

    for (SoftwareItem item: items) {
      Software software = softwareBean.newSoftwareInstance();
      task.copy(item, software);
      resultList.add(software);
    }
    
    assertNotNull(resultList);
    assertTrue(resultList.size() == 2);
    
    
    Software s = resultList.get(0);
    assertEquals(s.getExternalId(), this.software_1_id);
    assertEquals(s.getName(), this.software_1_name);
    assertEquals(s.getVersion(), this.software_1_version);
    assertEquals(s.getStatus(), this.software_1_status);
    assertEquals(s.getLicenseType(), this.software_1_licensetype);
    assertEquals(s.getDescription(), this.software_1_description);
    
    
    Software s1 = resultList.get(1);
    assertEquals(s1.getExternalId(), this.software_2_id);
    assertEquals(s1.getName(), this.software_2_name);
    assertEquals(s1.getVersion(), this.software_2_version);
    assertEquals(s1.getStatus(), this.software_2_status);
    assertEquals(s1.getLicenseType(), this.software_2_licensetype);
    assertEquals(s1.getDescription(), this.software_2_description);

  }
  
  public void testprocessSoftware()  throws SetupException, DMException{
    Setup setup = new SetupImpl();
    Console console = new ConsoleImpl();
    setup.setConsole(console);
    
    SoftwareTask task = new SoftwareTask();
    task.setSetup(setup);
    
    List<String> filenames = new ArrayList<String>();
    filenames.add(softwareFile);
    task.setFilenames(filenames);
    task.setManagementBeanFactory(factory);  
    task.process(); 
        
    factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
    softwareBean = factory.createSoftwareBean();
    List<Software> softwares = softwareBean.getAllOfSoftwares();
    
    assertNotNull(softwares);
    assertTrue(softwares.size() == 2);
    
    //第一个软件
    
    Software s = softwares.get(0);
    assertEquals(s.getExternalId(), this.software_1_id);
    assertEquals(s.getName(), this.software_1_name);
    assertEquals(s.getVersion(), this.software_1_version);
    assertEquals(s.getStatus(), this.software_1_status);
    assertEquals(s.getLicenseType(), this.software_1_licensetype);
    assertEquals(s.getDescription(), this.software_1_description);
    Iterator<SoftwarePackage> sp = s.getPackages().iterator();
    while (sp.hasNext()) {
      SoftwarePackage pak = sp.next();
      assertEquals(pak.getName(), this.software_1_package1_name);
      assertEquals(pak.getUrl(), this.software_1_package1_url);
      assertEquals(pak.getInstallationOptions(), this.software_1_package1_InstallOptions);
      
      Model model = pak.getModels().iterator().next();
      if (model.getManufacturerModelId().equals(this.software_1_package1_targetModel1_vendow)){
        assertEquals(model.getManufacturer().getExternalId(), this.software_1_package1_targetModel1_vendow);
        assertEquals(model.getManufacturerModelId(), this.software_1_package1_targetModel1_name);
      }
    }

    
    Software s1 = softwares.get(1);
    assertEquals(s1.getExternalId(), this.software_2_id);
    assertEquals(s1.getName(), this.software_2_name);
    assertEquals(s1.getVersion(), this.software_2_version);
    assertEquals(s1.getStatus(), this.software_2_status);
    assertEquals(s1.getLicenseType(), this.software_2_licensetype);
    assertEquals(s1.getDescription(), this.software_2_description);
    Iterator<SoftwarePackage> sp1 = s1.getPackages().iterator();
    while (sp1.hasNext()) {
      SoftwarePackage pak = sp1.next();
        if (pak.getName().equals(this.software_2_package1_name) || pak.getName() == this.software_2_package1_name) {
          assertEquals(pak.getName(), this.software_2_package1_name);
          assertEquals(pak.getMimeType(), this.software_2_package1_local_MimeType);
          assertEquals(pak.getBlobFilename(), this.software_2_package1_local_File);
          assertEquals(pak.getInstallationOptions(), this.software_2_package1_InstallOptions);
          Model model = pak.getModels().iterator().next();
          if (model.getManufacturerModelId().equals(this.software_2_package1_targetModel1_name)){          
            assertEquals(model.getManufacturer().getExternalId(), this.software_2_package1_targetModel1_vendow);
            assertEquals(model.getManufacturerModelId(), this.software_2_package1_targetModel1_name);
          } else {          
            assertEquals(model.getManufacturer().getExternalId(), this.software_2_package1_targetModel2_vendow);
            assertEquals(model.getManufacturerModelId(), this.software_2_package1_targetModel2_name);
          }
        } else {
          assertEquals(pak.getName(), this.software_2_package2_name);
          assertEquals(pak.getMimeType(), this.software_2_package2_local_MimeType);
          assertEquals(pak.getBlobFilename(), this.software_2_package2_local_File);
          assertEquals(pak.getInstallationOptions(), this.software_2_package2_InstallOptions);
          Model model = pak.getModels().iterator().next();
          if (model.getManufacturerModelId().equals(this.software_2_package2_targetModel1_name)){
            assertEquals(model.getManufacturer().getExternalId(), this.software_2_package2_targetModel1_vendow);
            assertEquals(model.getManufacturerModelId(), this.software_2_package2_targetModel1_name);
          } else {          
            assertEquals(model.getManufacturer().getExternalId(), this.software_2_package2_targetModel2_vendow);
            assertEquals(model.getManufacturerModelId(), this.software_2_package2_targetModel2_name);
          }
        }       
      
    }
    

  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

}
