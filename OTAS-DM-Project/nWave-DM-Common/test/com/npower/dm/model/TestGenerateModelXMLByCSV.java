/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/model/TestGenerateModelXMLByCSV.java,v 1.3 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.3 $
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
package com.npower.dm.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import com.npower.dm.AllTests;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestGenerateModelXMLByCSV extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  private String writeXML(String manufacturer, String model, String folderName) {
    StringBuffer result = new StringBuffer();
    result.append("  <!--  Model: " + model.trim() + " -->\n");
    result.append("  <Model>\n");
    result.append("    <Name>" + model.trim() + "</Name>\n");
    result.append("    <ExternalID>" + model.trim() + "</ExternalID>\n");
    result.append("    <IsOmaDmEnabled>false</IsOmaDmEnabled>\n");
    result.append("    <OmaDmVersion></OmaDmVersion>\n");
    result.append("    <IsOmaCpEnabled>true</IsOmaCpEnabled>\n");
    result.append("    <OmaCpVersion>1.1</OmaCpVersion>\n");
    result.append("    <IsNokiaOtaEnabled>false</IsNokiaOtaEnabled>\n");
    result.append("    <NokiaOtaVersion></NokiaOtaVersion>\n");
    result.append("    <Description></Description>\n");
    result.append("    <IconFile>./models/" + folderName + "/icons/" + model.trim().toLowerCase() + ".jpg</IconFile>\n");
    result.append("    <DDFs/>\n");
    result.append("    <ProfileMappings/>\n");
    result.append("    <TACs>\n");
    result.append("    </TACs>\n");
    result.append("  </Model>\n");
    return result.toString();
  }
  
  public void testStringUtils() throws Exception {
    {
      String line1 = "0,1,2,3,4,5,6,7,8,9";
      String[] result = StringUtils.splitPreserveAllTokens(line1, ',');
      assertEquals(10, result.length);
    }
    {
      String line1 = ",1,,3,,5,,7,,9";
      String[] result = StringUtils.splitPreserveAllTokens(line1, ',');
      assertEquals(10, result.length);
      assertEquals("", result[0]);
      assertEquals("1", result[1]);
      assertEquals("", result[2]);
      assertEquals("3", result[3]);
      assertEquals("", result[4]);
      assertEquals("5", result[5]);
      assertEquals("", result[6]);
      assertEquals("7", result[7]);
      assertEquals("", result[8]);
      assertEquals("9", result[9]);
    }
    
    {
      String src = "break_list_of_links_with_br_element_recommended ";
      String result = WordUtils.capitalize(src, new char[]{'_', ' '});
      assertEquals("Break_List_Of_Links_With_Br_Element_Recommended ", result);
    }
  }
  
  public void testOutputXML() throws Exception {
    
    String filename = "D:/Zhao/MyWorkspace/nWave-DM-Common/test/com/npower/dm/model/bjmcc.csv";
    Map<String, TreeSet<String>> maps = loadData(filename);
    
    System.out.println(maps);
    
    //String outputBaseDir = "D:/Zhao/MyWorkspace/nWave-DM-Common/metadata/setup";
    String outputBaseDir = "C:/temp/setup";
    
    outputManufacturersXML(maps, outputBaseDir);
    outputModelsXML(maps, outputBaseDir);
    
  }

  /**
   * @param maps
   * @param outputBaseDir
   * @throws DMException
   * @throws Exception
   */
  private void outputManufacturersXML(Map<String, TreeSet<String>> maps, String outputBaseDir) throws DMException, Exception {
    // Generate XML
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean bean = factory.createModelBean();
    int total = 0;
    int total4New = 0;
    int total4Exists = 0;
    try {
        for (String manufacturerExtID: maps.keySet()) {
            Manufacturer manufacturer = bean.getManufacturerByExternalID(manufacturerExtID);
            String folderName = manufacturerExtID.trim().toLowerCase();
            folderName = StringUtils.replaceChars(folderName, '-', '_');
            folderName = StringUtils.replaceChars(folderName, ' ', '_');
            File outputDir = new File(outputBaseDir, "manufacturers");
            if (!outputDir.exists()) {
               outputDir.mkdirs();
            }
            File outputFile = null;
            if (manufacturer != null) {
              outputFile = new File(outputDir, folderName + ".additional.xml");
            } else {
              outputFile = new File(outputDir, folderName + ".xml");
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            out.write("<Setup>\n");
            out.write("<Manufacturers>\n");
            out.write("\n");
            out.write("<Manufacturer>\n");
            out.write("<Name>" + manufacturerExtID.trim() + "</Name>\n");
            out.write("<ExternalID>" + manufacturerExtID.trim() + "</ExternalID>\n");
            out.write("<Description>" + manufacturerExtID.trim() + "</Description>\n");
            out.write("<Models filename=\"./models/" + folderName.trim() + "/models.additional.xml\"/>\n");
            out.write("</Manufacturer>\n");
            out.write("\n");
            out.write("</Manufacturers>\n");
            out.write("</Setup>\n");
            
            out.close();
        }
        
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }

    System.out.println("*********************************************************************");
    System.out.println(" Total         : " + total);
    System.out.println(" Total (Exists): " + total4Exists);
    System.out.println(" Total (New)   : " + total4New);
    System.out.println("*********************************************************************");
  }

  /**
   * @param maps
   * @param outputBaseDir
   * @throws DMException
   * @throws Exception
   */
  private void outputModelsXML(Map<String, TreeSet<String>> maps, String outputBaseDir) throws DMException, Exception {
    // Generate XML
    ManagementBeanFactory factory = AllTests.getManagementBeanFactory();
    ModelBean bean = factory.createModelBean();
    int total = 0;
    int total4New = 0;
    int total4Exists = 0;
    try {
        for (String manufacturerExtID: maps.keySet()) {
            String folderName = manufacturerExtID.trim().toLowerCase();
            folderName = StringUtils.replaceChars(folderName, '-', '_');
            folderName = StringUtils.replaceChars(folderName, ' ', '_');
            File outputDir = new File(outputBaseDir, "models/" + folderName);
            if (!outputDir.exists()) {
               outputDir.mkdirs();
               File iconDir = new File(outputDir, "icons");
               if (!iconDir.exists()) {
                  iconDir.mkdirs();
               }
            }
            File outputFile = new File(outputDir, "models.additional.xml");
            BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            out.write("<Setup>\n");
            out.write("<!--  Models: " + manufacturerExtID.trim() + " -->\n");
            out.write("<Models>\n");
            out.write("\n");
            
            for (String modelExtID: maps.get(manufacturerExtID)) {
                total++;
                Manufacturer manufacturer = bean.getManufacturerByExternalID(manufacturerExtID);
                Model model = bean.getModelByManufacturerModelID(manufacturer, modelExtID);
                if (model != null) {
                   // Exists
                   total4Exists++;
                   System.err.println(manufacturerExtID + " " + modelExtID + " exists!");
                } else {
                  total4New++;
                  out.write(this.writeXML(manufacturerExtID, modelExtID, folderName));
                  out.write("\n");
                  
                }
            }
            
            out.write("\n");
            out.write("</Models>\n");
            out.write("</Setup>\n");
            
            out.close();
        }
        
    } catch (Exception e) {
      throw e;
    } finally {
      factory.release();
    }

    System.out.println("*********************************************************************");
    System.out.println(" Total         : " + total);
    System.out.println(" Total (Exists): " + total4Exists);
    System.out.println(" Total (New)   : " + total4New);
    System.out.println("*********************************************************************");
  }

  /**
   * @param filename
   * @return
   * @throws FileNotFoundException
   * @throws IOException
   */
  private Map<String, TreeSet<String>> loadData(String filename) throws FileNotFoundException, IOException {
    // Load Manufacturers
    Map<String, TreeSet<String>> maps = new HashMap<String, TreeSet<String>>();
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line = reader.readLine();
    String[] manufacturers = StringUtils.splitPreserveAllTokens(line, ',');
    for (int i = 0; i < manufacturers.length; i++) {
        maps.put(manufacturers[i].trim(), new TreeSet<String>());
    }
    
    // Load models
    line = reader.readLine();
    while (line != null) {
          String[] models = StringUtils.splitPreserveAllTokens(line, ',');
          for (int i= 0; i < models.length; i++) {
              if (StringUtils.isNotEmpty(models[i])) {
                 maps.get(manufacturers[i].trim()).add(models[i]);
              }
          }
          line = reader.readLine();
    }
    
    reader.close();
    return maps;
  }

}
