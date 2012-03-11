/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/wurfl/TestWURFLObjectManager.java,v 1.3 2007/11/14 09:52:17 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2007/11/14 09:52:17 $
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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import junit.framework.TestCase;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/11/14 09:52:17 $
 */
public class TestWURFLObjectManager extends TestCase {
  
  private ObjectsManager om = null;

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    System.setProperty("otas.dm.home", "D:/OTASDM");
    
    if (this.om == null) {
      WurflSource source = new OTASDMWurflSource();
       this.om = ObjectsManager.newInstance(source);
    }
    
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testActualDevices() throws Exception {

    System.out.println("1 -> test retrieval of actual devices");
    ListManager lm = this.om.getListManagerInstance();

    TreeMap<String, WurflDevice> load = lm.getActualDeviceElementsList();
    Iterator<String> keys = load.keySet().iterator();
    System.out.print("List of Actual Devices: ");
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
        total++;
      }
    }
    System.out.println("Total model: " + total);
    assertNotNull(load);
    assertNotNull(lol);
    assertEquals(2548, total);
  }
  
  public void testFindWurflDeviceByBrand() throws Exception {
    String manufacturer = "Nokia";
    String model = "6681";
    
    ListManager lm = this.om.getListManagerInstance();
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
    
    CapabilityMatrix cm = this.om.getCapabilityMatrixInstance();
    assertEquals("html_wi_oma_xhtmlmp_1_0", cm.getCapabilityForDevice(foundDevice.getId(), "preferred_markup"));
    
    ArrayList<String> capaSet = lm.getCapabilitySet();
    for (int i = 0; i < capaSet.size(); i++) {
        String capa = capaSet.get(i);
        System.out.println(i + ": " + capa + ": " + cm.getCapabilityForDevice(foundDevice.getId(), capa));
    }
  }

}
