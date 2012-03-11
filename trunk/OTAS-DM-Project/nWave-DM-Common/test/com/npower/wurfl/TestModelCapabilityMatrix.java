/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/wurfl/TestModelCapabilityMatrix.java,v 1.6 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.6 $
  * $Date: 2008/06/16 10:11:15 $
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import junit.framework.TestCase;

import com.npower.dm.AllTests;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestModelCapabilityMatrix extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    System.setProperty("otas.dm.home", "D:/OTASDM");
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void ttestGetAllOfModels() throws Exception {
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelBean bean = factory.createModelBean();
        
        List<Model> models = bean.getAllModel();
        assertNotNull(models);
        assertTrue(models.size() > 0);
        int total4Found = 0;
        int total4NotFound = 0;
        for (Model model: models) {
            if (!model.getManufacturerModelId().equalsIgnoreCase("Default")) {
              Map<String, String> capabilities = bean.getCapabilityMatrix(model);
              if (capabilities.size() > 0) {
                 total4Found++;
                 System.out.println(model.getManufacturer().getExternalId() + " " + model.getManufacturerModelId() + ": found!");
                 continue;
              }               
            }
            total4NotFound++;
            System.err.println(model.getManufacturer().getExternalId() + " " + model.getManufacturerModelId() + ": not found!");
        }
        
        System.out.println("************************************************");
        System.out.println(" Total             : " + models.size());
        System.out.println(" Total (Found)     : " + total4Found);
        System.out.println(" Total (Not Found) : " + total4NotFound);
        System.out.println("************************************************");
        
        assertEquals(1045, models.size());
        assertEquals(772, total4Found);
        assertEquals(273, total4NotFound);
        
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }
  
  public void testAdditionalModels() throws Exception {
    WurflSource source = new FileWurflSource(new File("D:\\zhao\\MyWorkspace\\nWave-DM-Common\\metadata\\wurfl\\wurfl.xml"));
    ObjectsManager om = ObjectsManager.newInstance(source);
    ListManager lm = om.getListManagerInstance();
    TreeMap<String, WurflDevice> load = lm.getActualDeviceElementsList();
    Set<String> sortedBrands = new TreeSet<String>();
    sortedBrands.addAll(load.keySet());
    
    Map<String, Set<String>> result = new TreeMap<String, Set<String>>();
    
    for (String brand: sortedBrands) {
      // System.out.print("'"+key+"',");
      WurflDevice wd = (WurflDevice) load.get(brand);
      System.out.print("'" + brand + "',");
      System.out.print("'" + wd.getBrandName() + "',");
      System.out.println("'" + wd.getModelName() + "'");
      if (result.get(wd.getBrandName()) == null) {
         result.put(wd.getBrandName(), new TreeSet<String>());
      }
      result.get(wd.getBrandName()).add(wd.getModelName());
    }
    
    for (String brand: result.keySet()) {
        for (String model: result.get(brand)) {
            System.err.println(brand + "," + model);
        }
    }
    
    ManagementBeanFactory factory = null;
    try {
        factory = AllTests.getManagementBeanFactory();
        ModelBean bean = factory.createModelBean();
        
        int total4Found = 0;
        int total4NotFound = 0;
        int total = 0;
        for (String brand: result.keySet()) {
            for (String model: result.get(brand)) {
                total++;
                Manufacturer manu = bean.getManufacturerByExternalID(brand);
                if (manu != null) {
                   Model m = bean.getModelByManufacturerModelID(manu, model);
                   if (m != null) {
                      // Found!
                      total4Found++;
                      System.out.println(brand + "," + model + ": founded!");
                      continue;
                   }
                }
                System.err.println(brand + "," + model + ": not founded!");
                total4NotFound++;
            }
        }
        
        System.out.println("************************************************");
        System.out.println(" Total             : " + total);
        System.out.println(" Total (Found)     : " + total4Found);
        System.out.println(" Total (Not Found) : " + total4NotFound);
        System.out.println("************************************************");
        
        assertEquals(2548, total);
        assertEquals(669, total4Found);
        assertEquals(1879, total4NotFound);
        
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }
  }

}
