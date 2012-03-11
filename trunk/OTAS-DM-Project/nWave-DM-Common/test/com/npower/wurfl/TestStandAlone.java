/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/wurfl/TestStandAlone.java,v 1.3 2007/11/16 10:16:58 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2007/11/16 10:16:58 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.wurfl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/11/16 10:16:58 $
 */
public class TestStandAlone extends TestCase {
  ObjectsManager om = null;

  UAManager        uam;

  CapabilityMatrix cm;

  ListManager      lm;

  public static void main(String[] args) {
    System.out.println("Unit testing on Internals (WurflTester.class)");
    junit.textui.TestRunner.run(TestWurfl.class);

    System.out.println("Unit testing on API (StandAlone.class)");
    junit.textui.TestRunner.run(TestStandAlone.class);
  }

  public void setUp() throws Exception {
    Date d = new Date();
    System.out.println("Starting :" + d.toString());
    if (this.om == null) {
       WurflSource source = new FileWurflSource(new File("D:\\zhao\\MyWorkspace\\nWave-DM-Common\\metadata\\wurfl\\wurfl.xml"));
       this.om = ObjectsManager.newInstance(source);
    }
  }

  public void testUARetrieval() throws Exception {

    uam = om.getUAManagerInstance();
    cm = om.getCapabilityMatrixInstance();
    System.out.println("Testing UA Retrieval");
    System.out.println("1 -> test caching for device ID retrieval from UA");
    assertEquals(uam.getDeviceIDFromUA("MOT-T720/05.08.41R MIB/2.0 Profile/MIDP-1.0 Configuration/CLDC-1.0"), 
                 uam.getDeviceIDFromUA("MOT-T720/05.08.41R MIB/2.0 Profile/MIDP-1.0 Configuration/CLDC-1.0"));
    System.out.println("2 -> test for correctness");
    assertEquals("mot_t720_ver1_sub050841r", 
                 uam.getDeviceIDFromUA("MOT-T720/05.08.41R MIB/2.0 Profile/MIDP-1.0 Configuration/CLDC-1.0"));
    assertEquals("true", 
                 cm.getCapabilityForDevice("nokia_3610_ver1_sub0508", "gif"));
  }

  public void testUARetrievealLoose() throws Exception {

    uam = om.getUAManagerInstance();
    cm = om.getCapabilityMatrixInstance();
    System.out.println("1 -> test caching for device ID retrieval from UA (loose)");
    assertEquals(uam.getDeviceIDFromUALoose("MOT-T720/G_05.01.43R"), 
                 uam.getDeviceIDFromUALoose("MOT-T720/G_05.01.43R"));
  }

  public void testListManager() throws Exception {

    System.out.println("1 -> test that ListManager returns the right lists");
    lm = om.getListManagerInstance();
    HashMap<String, WurflDevice> del = lm.getDeviceElementsList();
    assertNotNull(del);

    System.out.println("2 -> test getCapabilitiesForDeviceID() method");
    String devId = "sie_slck_ver1_sub4119i";
    Map<String, String> capaList1 = lm.getCapabilitiesForDeviceID(devId);
    Map<String, String> capaList2 = lm.getCapabilitiesForDeviceID(devId);
    assertEquals(capaList1, capaList2);
    assertNotNull(capaList1);
  }

  public void testCapaByGroup() throws Exception {

    System.out.println("1 -> test capability listing by group in ListManager");
    lm = om.getListManagerInstance();

    HashMap<String, ArrayList<String>> logr = lm.getListOfGroups();
    Iterator<String> keys = logr.keySet().iterator();
    System.out.print("Groups of capabilities: ");
    while (keys.hasNext()) {
      String key = keys.next();
      System.out.print("'" + key + "',");
      ArrayList<String> cap_list = logr.get(key);
      assertNotNull(cap_list);
    }
  }

  public void testIsDescendent() throws Exception {

    System.out.println("1 -> test that isDescendant() Works");
    cm = om.getCapabilityMatrixInstance();
    // dev1,dev2 -> true
    String dev1 = "nokia_3650_ver1_sub12midp10";
    String dev2 = "nokia_generic_series60";

    // dev3,dev2 ->false
    String dev3 = "wapman_ver1_sub172";

    // dev1,dev4 -> true
    String dev4 = "generic";
    assertEquals(cm.isDescendentOf(dev1, dev2), true);
    // repeated twice to check caching
    assertEquals(cm.isDescendentOf(dev1, dev2), true);
    assertEquals(cm.isDescendentOf(dev3, dev2), false);
    assertEquals(cm.isDescendentOf(dev3, dev2), false);
    // check that generic does its job too
    assertEquals(cm.isDescendentOf(dev1, dev4), true);
  }

  public void testIsCapabilityDefinedInDevice() throws Exception {

    System.out.println("1 -> test that isCapabilityDefinedInDevice()");
    cm = om.getCapabilityMatrixInstance();
    // dev1,dev2 -> true
    String dev1 = "nokia_3650_ver1_sub12midp10";
    String dev2 = "nokia_generic_series60";

    assertEquals(cm.isCapabilityDefinedInDevice(dev1, "preferred_markup"), false);
    assertEquals(cm.isCapabilityDefinedInDevice(dev2, "preferred_markup"), true);
  }

  public void testActualDevices() throws Exception {

    System.out.println("1 -> test retrieval of actual devices");
    lm = om.getListManagerInstance();

    TreeMap<String, WurflDevice> load = lm.getActualDeviceElementsList();
    Iterator<String> keys = load.keySet().iterator();
    System.out.println("List of Actual Devices: ");
    while (keys.hasNext()) {
      String key = keys.next();
      // System.out.print("'"+key+"',");
      WurflDevice wd = (WurflDevice) load.get(key);
      System.out.print("'" + wd.getBrandName() + "',");
      System.out.println("'" + wd.getModelName() + "'");
    }

    TreeMap<String, TreeMap<String, WurflDevice>> lol = lm.getDeviceGroupedByBrand();
    ArrayList<String> al = lm.getDeviceBrandList();
    System.out.println(al.size());
    int total = 0;
    for (int i = 0; i < al.size(); i++) {
      String key = (String) al.get(i);
      System.out.println(key + ":");
      TreeMap<String, WurflDevice> hm = lol.get(key);
      Iterator<String> models = hm.keySet().iterator();
      while (models.hasNext()) {
        String model = (String) models.next();
        System.out.println(" - " + model + ", key=" + key + ",deviceID=" + hm.get(model).getId());
        //System.out.println(key + "," + model);
        total++;
      }
    }
    System.out.println("Total model: " + total);
    assertNotNull(load);
    assertNotNull(lol);
  }
  
  public void testGetCapabilitySet() throws Exception {
    System.out.println("1 -> test retrieval of actual devices");
    lm = om.getListManagerInstance();
    
    ArrayList<String> capaSet = lm.getCapabilitySet();
    assertNotNull(capaSet);

  }
  
  public void testFindWurflDeviceByBrand() throws Exception {
    String manufacturer = "Nokia";
    String model = "6681";
    
    lm = om.getListManagerInstance();
    TreeMap<String, WurflDevice> load = lm.getActualDeviceElementsList();
    Iterator<String> keys = load.keySet().iterator();
    System.out.print("List of Actual Devices: ");
    WurflDevice foundDevice = null;
    while (keys.hasNext()) {
        String key = keys.next();
        // System.out.print("'"+key+"',");
        WurflDevice wd = (WurflDevice) load.get(key);
        if (manufacturer.equalsIgnoreCase(wd.getBrandName())
            && model.equalsIgnoreCase(wd.getModelName())) {
           foundDevice = wd;
        }
    }
    
    cm = om.getCapabilityMatrixInstance();
    assertEquals("html_wi_oma_xhtmlmp_1_0", cm.getCapabilityForDevice(foundDevice.getId(), "preferred_markup"));
    
    ArrayList<String> capaSet = lm.getCapabilitySet();
    for (int i = 0; i < capaSet.size(); i++) {
        String capa = capaSet.get(i);
        System.out.println(i + ": " + capa + ": " + cm.getCapabilityForDevice(foundDevice.getId(), capa));
    }
    
    int index = 0;
    for (String capaName: lm.getCapabilitiesForDeviceID(foundDevice.getId()).keySet()) {
        String capaValue = lm.getCapabilitiesForDeviceID(foundDevice.getId()).get(capaName);
        System.out.println(index + ": " + capaName + ": " + capaValue);
        index++;
    }
  }

}
