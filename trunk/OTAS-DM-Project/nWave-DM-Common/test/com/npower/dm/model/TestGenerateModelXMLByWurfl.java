/**
  * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/model/TestGenerateModelXMLByWurfl.java,v 1.2 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.2 $
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
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
import com.npower.wurfl.FileWurflSource;
import com.npower.wurfl.ListManager;
import com.npower.wurfl.ObjectsManager;
import com.npower.wurfl.WurflDevice;
import com.npower.wurfl.WurflSource;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/06/16 10:11:15 $
 */
public class TestGenerateModelXMLByWurfl extends TestCase {
  
  private List<String> manufacturerFiles = new ArrayList<String>();
  private List<String> manufacturerNames = new ArrayList<String>();
  private List<String> modelFiles = new ArrayList<String>();
  private List<String> modelNames = new ArrayList<String>();

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    manufacturerFiles.clear();
    manufacturerNames.clear();
    modelFiles.clear();
    modelNames.clear();
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
    result.append("    <IsOmaCpEnabled>false</IsOmaCpEnabled>\n");
    result.append("    <OmaCpVersion></OmaCpVersion>\n");
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
    
    WurflSource source = new FileWurflSource(new File("D:\\zhao\\MyWorkspace\\nWave-DM-Common\\metadata\\wurfl\\wurfl.xml"));
    ObjectsManager om = ObjectsManager.newInstance(source);
    ListManager lm = om.getListManagerInstance();
    TreeMap<String, WurflDevice> load = lm.getActualDeviceElementsList();
    Set<String> sortedBrands = new TreeSet<String>();
    sortedBrands.addAll(load.keySet());
    
    Map<String, Set<String>> maps = new TreeMap<String, Set<String>>();
    Map<String, String> brandNames = new TreeMap<String, String>();
    
    for (String brand: sortedBrands) {
      // System.out.print("'"+key+"',");
      WurflDevice wd = (WurflDevice) load.get(brand);
      System.out.print("'" + brand + "',");
      System.out.print("'" + wd.getBrandName() + "',");
      System.out.println("'" + wd.getModelName() + "'");
      if (maps.get(wd.getBrandName().toLowerCase()) == null) {
         maps.put(wd.getBrandName().toLowerCase(), new TreeSet<String>());
      }
      maps.get(wd.getBrandName().toLowerCase()).add(wd.getModelName());
      if (brandNames.get(wd.getBrandName().toLowerCase()) == null) {
         brandNames.put(wd.getBrandName().toLowerCase(), wd.getBrandName());
      }
    }
    
    System.out.println(maps);
    
    //String outputBaseDir = "D:/Zhao/MyWorkspace/nWave-DM-Common/metadata/setup";
    String outputBaseDir = "C:/temp/setup";
    
    outputManufacturersXML(maps, brandNames, outputBaseDir);
    outputModelsXML(maps, brandNames, outputBaseDir);
    
    File setupFile = new File(outputBaseDir, "dmsetup.2nd.xml");
    BufferedWriter out = new BufferedWriter(new FileWriter(setupFile));

    out.write("<Setup>\n");
    out.write("  <Name>OTAS DM Setup</Name>\n");
    out.write("  <Description>OTAS DM Setup</Description>\n");
    out.write("  <Properties>\n");
    out.write("    <!-- Please customize the following properties -->\n");
    out.write("    <Property key=\"jdbc.driver.class\"\n");
    out.write("              value=\"oracle.jdbc.driver.OracleDriver\" />\n");
    out.write("    <Property key=\"jdbc.url\"\n");
    out.write("              value=\"jdbc:oracle:thin:@192.168.0.4:1521:orcl\" />\n");
    out.write("    <Property key=\"jdbc.autoCommit\"\n");
    out.write("              value=\"false\" />\n");
    out.write("    <Property key=\"jdbc.super.user\"\n");
    out.write("              value=\"system\" />\n");
    out.write("    <Property key=\"jdbc.super.password\"\n");
    out.write("              value=\"admin123\" />\n");
    out.write("    <Property key=\"jdbc.dmuser.user\"\n");
    out.write("              value=\"otasdm\" />\n");
    out.write("    <Property key=\"jdbc.dmuser.password\"\n");
    out.write("              value=\"otasdm\" />\n");
    out.write("              \n");
    out.write("    <!-- Do not modified the following properties -->\n");
    out.write("    <!-- Database connection parameters -->\n");
    out.write("    <Property key=\"hibernate.dialect\" value=\"org.hibernate.dialect.Oracle9Dialect\"/>\n");
    out.write("    <Property key=\"hibernate.connection.driver_class\" value=\"${jdbc.driver.class}\"/>\n");
    out.write("    <Property key=\"hibernate.connection.url\" value=\"${jdbc.url}\"/>\n");
    out.write("    <Property key=\"hibernate.connection.username\" value=\"${jdbc.dmuser.user}\"/>\n");
    out.write("    <Property key=\"hibernate.connection.password\" value=\"${jdbc.dmuser.password}\"/>\n");
    out.write("    \n");
    out.write("    <!-- Model library parameters -->\n");
    out.write("    <Property key=\"model.default.icon.file\" value=\"./models/default_model.jpg\"/>\n");
    out.write("    \n");
    out.write("  </Properties>\n");
    out.write(" \n");
    out.write("  <Console class=\"com.npower.setup.console.ConsoleImpl\"></Console>\n");
    out.write(" \n");
    out.write("  <Tasks>\n");
    out.write("    <!-- Step#9: Import Manufacturers and Models -->\n");
    out.write("    <Task disable=\"false\">\n");
    out.write("      <Name>Setup DM Manufacturers and Models (2nd stage)</Name>\n");
    out.write("      <Description>Setup DM Manufacturers and Models (2nd stage)</Description>\n");
    out.write("\n");
    
    out.write("      <!-- Import Manufacturers -->\n");
    out.write("      <Task class=\"com.npower.dm.setup.task.ManufacturerTask\">\n");
    out.write("        <Name>Import Manufacturers</Name>\n");
    out.write("        <Description>Import Manufacturers</Description>\n");
    out.write("        <Properties>\n");
    String files = "";
    for (int i = 0; i < this.manufacturerFiles.size(); i++) {
        if ( i < this.manufacturerFiles.size() - 1) {
           files += "./manufacturers/" + this.manufacturerFiles.get(i) + ",\n";
        } else {
          files += "./manufacturers/" + this.manufacturerFiles.get(i) + "\n";
        }
    }
    out.write("          <Property key=\"import.files\" value=\"" + files + "\" />\n");
    out.write("        </Properties>\n");
    out.write("      </Task>\n");
    out.write("      \n");
    
    for (int i = 0; i < this.modelFiles.size(); i++) { 
        out.write("      <!-- Import " + this.modelNames.get(i) + " Models -->\n");
        out.write("      <Task class=\"com.npower.dm.setup.task.ModelTask\">\n");
        out.write("        <Name>Import " + this.modelNames.get(i) + " Models</Name>\n");
        out.write("        <Description>Import " + this.modelNames.get(i) + " Models</Description>\n");
        out.write("        <Properties>\n");
        out.write("          <Property key=\"import.files\" value=\"" + this.modelFiles.get(i) + "\" />\n");
        out.write("        </Properties>\n");
        out.write("      </Task>\n");
        out.write("\n");
    }
    out.write("    </Task>\n");
    out.write("  </Tasks>\n");
    out.write("</Setup>\n");
    out.close();
  }

  /**
   * @param maps
   * @param outputBaseDir
   * @throws DMException
   * @throws Exception
   */
  private void outputManufacturersXML(Map<String, Set<String>> maps, Map<String, String> brandNames, String outputBaseDir) throws DMException, Exception {
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
              outputFile = new File(outputDir, folderName + ".2nd.xml");

              this.modelNames.add(brandNames.get(manufacturerExtID.trim().toLowerCase()));
              this.modelFiles.add("./manufacturers/" + folderName + ".2nd.xml");
            } else {
              outputFile = new File(outputDir, folderName + ".xml");
              
              this.manufacturerNames.add(manufacturerExtID);
              this.manufacturerFiles.add(folderName + ".xml");

              this.modelNames.add(brandNames.get(manufacturerExtID.trim().toLowerCase()));
              this.modelFiles.add("./manufacturers/" + folderName + ".xml");
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            out.write("<Setup>\n");
            out.write("<Manufacturers>\n");
            out.write("\n");
            out.write("<Manufacturer>\n");
            out.write("<Name>" + brandNames.get(manufacturerExtID.trim().toLowerCase()) + "</Name>\n");
            out.write("<ExternalID>" + brandNames.get(manufacturerExtID.trim().toLowerCase()) + "</ExternalID>\n");
            out.write("<Description>" + brandNames.get(manufacturerExtID.trim().toLowerCase()) + "</Description>\n");
            out.write("<Models filename=\"./models/" + folderName.trim() + "/models.2nd.xml\"/>\n");
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
  private void outputModelsXML(Map<String, Set<String>> maps, Map<String, String> brandNames, String outputBaseDir) throws DMException, Exception {
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
            File outputFile = new File(outputDir, "models.2nd.xml");
            BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            out.write("<Setup>\n");
            out.write("<!--  Models: " + brandNames.get(manufacturerExtID.trim().toLowerCase()) + " -->\n");
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

}
