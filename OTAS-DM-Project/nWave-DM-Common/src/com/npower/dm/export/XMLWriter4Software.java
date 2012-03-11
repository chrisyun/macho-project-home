/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/export/XMLWriter4Software.java,v 1.8 2008/12/17 02:14:48 zhaowx Exp $
 * $Revision: 1.8 $
 * $Date: 2008/12/17 02:14:48 $
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

package com.npower.dm.export;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.sql.Blob;
import java.sql.SQLException;
import org.apache.commons.lang.StringUtils;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwarePackage;



/**
 * @author Zhao wanxiang
 * @version $Revision: 1.8 $ $Date: 2008/12/17 02:14:48 $
 */

public class XMLWriter4Software extends BaseXMLWriter {


  /**
   * 
   */
  public XMLWriter4Software() {
    super();
  }

  public XMLWriter4Software(Writer writer) {
    super(writer);
  }

  public void writeBody(Software software) throws IOException, SQLException, DMException {

    if (writer != null && software != null) {
      writeBlank(2);
      writer.write("<Software>"); 
      writer.write("\n");
      
      writeBlank(3);      
      writer.write("<ExternalId>");
      String externalID = software.getExternalId();
      write(externalID);
      writer.write("</ExternalId>");
      writer.write("\n");
      writeBlank(3);      
      writer.write("<Vendor>");
      String vendor = software.getVendor().getName();
      write(vendor);
      writer.write("</Vendor>");
      writer.write("\n");
      writeBlank(3);      
      writer.write("<Category>");
      String category = software.getCategory().getPathAsString("/");
      write(category);
      writer.write("</Category>");
      writer.write("\n");        
      writeBlank(3);      
      writer.write("<Name>");
      String name = software.getName();
      write(name);
      writer.write("</Name>");
      writer.write("\n");
      writeBlank(3);      
      writer.write("<Version>");
      String version = software.getVersion();      
      write(version);      
      writer.write("</Version>");
      writer.write("\n");
      writeBlank(3);      
      writer.write("<Status>");
      String status = software.getStatus();
      write(status);
      writer.write("</Status>");
      writer.write("\n");
      writeBlank(3);      
      writer.write("<LicenseType>");
      String license_type = software.getLicenseType();   
      write(license_type);      
      writer.write("</LicenseType>");
      writer.write("\n");           
      writeBlank(3);
      writer.write("<Description>");
      String description = software.getDescription();       
      write(description);      
      writer.write("</Description>");
      writer.write("\n");
      
      writeHeader4Package();
      if (software.getPackages().size() != 0) {
        for(SoftwarePackage softwarePackage : software.getPackages()) {
          writeBody4Package(softwarePackage);          
        }
      }      
      writeEnd4Package();
      
      writeBlank(2);
      writer.write("</Software>"); 
      writer.write("\n");
      
      writer.flush();
    }
  }
  
  public void writeHeader() throws IOException{
    if (writer != null) {
      writer.write("\n");  
      writeBlank(1);
      writer.write("<Softwares>"); 
      writer.write("\n");      
    }
  }
  
  public void writeEnd() throws IOException{
    if(writer != null) {
      writeBlank(1);
      writer.write("</Softwares>");
      writer.write("\n");
    
    }
  }
  
  private void writeBlank(int number) throws IOException {
    if (writer != null) {
      for (int i = 1; i <= number; i++) {
        writer.write("\t");
      }
    }
  }
  
  private void  writeBody4Package(SoftwarePackage softwarePackage) throws IOException, SQLException, DMException {

    if (writer != null && softwarePackage != null) {
      writeBlank(4);
      writer.write("<Package>"); 
      writer.write("\n");

      writeBlank(5);
      writer.write("<Name>");
      String name = softwarePackage.getName();
      write(name);
      writer.write("</Name>");
      writer.write("\n");

      if (StringUtils.isEmpty(softwarePackage.getUrl())) {
       //写本地的Package 
        writeLocalPackage(softwarePackage);
      }else {
      //写远程的Package 
        writeRemotePackage(softwarePackage);
      }

      writeBlank(5);
      writer.write("<InstallOptions>");
      writer.write("\n");
      writeBlank(6);
      write(softwarePackage.getInstallationOptions());
      writer.write("\n");
      writeBlank(5);
      writer.write("</InstallOptions>");
      writer.write("\n");
      
      writeBlank(5);
      writer.write("<TargetModels>");
      writer.write("\n");
                
      for(Model model : softwarePackage.getModels()) {
       String manufacturer = model.getManufacturer().getExternalId();
       String manufacturerModel = model.getManufacturerModelId();
       writeBlank(6);
       writer.write("<Model>");
       writer.write("\n");
       writeBlank(7);
       writer.write("<Vendor>");
       write(manufacturer);
       writer.write("</Vendor>");
       writer.write("\n");
       writeBlank(7);
       writer.write("<Name>");
       write(manufacturerModel);
       writer.write("</Name>");
       writer.write("\n");
       writeBlank(6);
       writer.write("</Model>");
       writer.write("\n");       
      }
      
      writeBlank(5);
      writer.write("</TargetModels>");
      writer.write("\n");

      writeBlank(4);
      writer.write("</Package>"); 
      writer.write("\n");
    }
  }
  
  private void writeHeader4Package() throws IOException{
    if (writer != null) {
      writeBlank(3);      
      writer.write("<Packages>");
      writer.write("\n");
    }
  }
  
  private void writeEnd4Package() throws IOException{
    if(writer != null) {
      writeBlank(3);
      writer.write("</Packages>");
      writer.write("\n");
    }
  }

  private void writeLocalPackage(SoftwarePackage softwarePackage) throws IOException, SQLException, DMException {
    if(writer != null) {
      writeBlank(5);
      writer.write("<Local>");
      writer.write("\n");
      writeBlank(6);
      writer.write("<MimeType>");
      write(softwarePackage.getMimeType());
      writer.write("</MimeType>");
      writer.write("\n");
      writeBlank(6);
      writer.write("<File>");
      if(StringUtils.isNotEmpty(softwarePackage.getBlobFilename())) {
        writeFile4Package(softwarePackage);
        writer.write("./packages/" + softwarePackage.getBlobFilename());
      } else {
        writer.write("");
      }
      writer.write("</File>");
      writer.write("\n");
      writeBlank(5);
      writer.write("</Local>");
      writer.write("\n");
    }
    
  }

  private void writeRemotePackage(SoftwarePackage softwarePackage) throws IOException {
    if(writer != null) {
      writeBlank(5);
      writer.write("<Remote>");
      writer.write("\n");
      writeBlank(6);
      writer.write("<Url>");
      write(softwarePackage.getUrl());
      writer.write("</Url>");
      writer.write("\n");
      writeBlank(5);
      writer.write("</Remote>");
      writer.write("\n");
    }
    
  }

  private void writeFile4Package(SoftwarePackage softwarePackage) throws IOException, SQLException ,DMException{
    if (softwarePackage.getBinary() != null ){
      String oldfileName = softwarePackage.getBlobFilename();
      String[] str = StringUtils.splitPreserveAllTokens(oldfileName, '.');
      String newfileName = str[0] + "." + softwarePackage.getId() + "." + str[1];
      File file = new File(System.getProperty("otas.dm.home"),"./output/softwares/packages");
      if (!file.exists()) {
        file.mkdirs();
      }
      file = new File(file,newfileName);
      FileOutputStream fos = new FileOutputStream(file);
      Blob binaryBlob = softwarePackage.getBinary().getBinaryBlob();
      fos.write(binaryBlob.getBytes(1, (int) binaryBlob.length()));
      fos.close();
    }else {
      throw new DMException("file of softwarePackage is null");
    }
  }
  
}
