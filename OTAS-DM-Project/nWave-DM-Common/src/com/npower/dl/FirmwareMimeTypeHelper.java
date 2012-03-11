/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dl/FirmwareMimeTypeHelper.java,v 1.2 2007/03/12 11:32:55 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/03/12 11:32:55 $
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
package com.npower.dl;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/03/12 11:32:55 $
 */
public class FirmwareMimeTypeHelper {
  private static Log log = LogFactory.getLog(FirmwareMimeTypeHelper.class);
  
  /**
   * 
   */
  public static final String DEFAULT_MANUFACTURER = "Default";
  
  /**
   * File Path: FOTA MIME Types 
   */
  private static final String FILENAME_FOTA_FOTA_MIME_TYPES = "config/fota/FotaMimeTypes.xml";
  
  private static List<DownloadMimeType> mimeTypes = new ArrayList<DownloadMimeType>();
  
  
  static {
    // Create table for firmware MimeTypes
    //mimeTypes.put("default", "application/octet-stream");
    //mimeTypes.put("nokia", "application/vnd.nokia.swupd.dp2");
    
    // Create table for firmware file suffix
    //fileSuffixes.put("default", ".bin");
    //fileSuffixes.put("nokia", ".swupd");
    
    File file = new File(System.getProperty("otas.dm.home"), FirmwareMimeTypeHelper.FILENAME_FOTA_FOTA_MIME_TYPES);
    try {
        FileInputStream input = new FileInputStream(file);
        mimeTypes = load(input);
    } catch (FileNotFoundException e) {
      log.fatal("Could not load FOTA_MIME TYPES from: " + file.getAbsolutePath(), e);
    } catch (IOException e) {
      log.fatal("Could not load FOTA_MIME TYPES from: " + file.getAbsolutePath(), e);
    }
    
  }
  
  public static List<DownloadMimeType> load(InputStream in) throws IOException {
    XMLDecoder decoder = new XMLDecoder(in);
    List<DownloadMimeType> types = (List<DownloadMimeType>)decoder.readObject();
    return types;
  }
  
  public static void save(File file) throws IOException {
    FileOutputStream out = new FileOutputStream(file);
    XMLEncoder xmlEncoder = new XMLEncoder(out);
    xmlEncoder.writeObject(mimeTypes);
    xmlEncoder.flush();
    xmlEncoder.close();
    out.close();
  }
  
  public static DownloadMimeType findMimeType(String manufacturer, String model) {
    if (StringUtils.isEmpty(manufacturer)) {
       manufacturer = FirmwareMimeTypeHelper.DEFAULT_MANUFACTURER;
    }
    manufacturer = manufacturer.trim();
    DownloadMimeType result = null;
    if (StringUtils.isNotEmpty(model)) {
       model = model.trim();
       for (DownloadMimeType mimeType: mimeTypes) {
           if (manufacturer.equalsIgnoreCase(mimeType.getManufacturer()) && model.equalsIgnoreCase(mimeType.getModel())) {
              result = mimeType;
              break;
           }
       }
    }
    if (result == null) {
       for (DownloadMimeType mimeType: mimeTypes) {
           if (manufacturer.equalsIgnoreCase(mimeType.getManufacturer())) {
              result = mimeType;
              break;
           }
       }
    }
    if (result == null && !manufacturer.equalsIgnoreCase(FirmwareMimeTypeHelper.DEFAULT_MANUFACTURER)) {
       result = findMimeType(FirmwareMimeTypeHelper.DEFAULT_MANUFACTURER, null);
    }
    return result;
  }
  
  /**
   * Found Mime types for manufacturer
   * @param manufacturerExternalID
   * @return
   */
  public static String getMimeType(String manufacturerExternalID, String modelExternalID) {
    if (manufacturerExternalID == null) {
       manufacturerExternalID = FirmwareMimeTypeHelper.DEFAULT_MANUFACTURER;
    }
    DownloadMimeType mimeType = findMimeType(manufacturerExternalID, modelExternalID);
    return mimeType.getMimeType();
  }

  /**
   * Found file suffix for manufacturer. 
   * @param manufacturerExternalID
   * @return Return a file suffix begined with '.'
   */
  public static String getFileSuffix(String manufacturerExternalID, String modelExternalID) {
    if (manufacturerExternalID == null) {
       manufacturerExternalID = FirmwareMimeTypeHelper.DEFAULT_MANUFACTURER;
    }
    DownloadMimeType mimeType = findMimeType(manufacturerExternalID, modelExternalID);
    String result = mimeType.getSuffix();
    
    if (!result.startsWith(".")) {
       result = "." + result;
    }
    return result;
  }
  
  public static void main(String[] args) throws Exception {
    File file = new File("C:/temp/FotaMimeTypes.xml");
    {
      DownloadMimeType type = new DownloadMimeType();
      type.setManufacturer(FirmwareMimeTypeHelper.DEFAULT_MANUFACTURER);
      type.setMimeType("application/octet-stream");
      type.setSuffix(".bin");
      mimeTypes.add(type);
    }
    {
      DownloadMimeType type = new DownloadMimeType();
      type.setManufacturer("Nokia");
      type.setMimeType("application/vnd.nokia.swupd.dp2");
      type.setSuffix(".swupd");
      mimeTypes.add(type);
    }
    {
      DownloadMimeType type = new DownloadMimeType();
      type.setManufacturer("Test");
      type.setMimeType("application/test");
      type.setSuffix(".test");
      mimeTypes.add(type);
    }
    {
      DownloadMimeType type = new DownloadMimeType();
      type.setManufacturer("Test");
      type.setModel("Test1");
      type.setMimeType("application/test1");
      type.setSuffix(".test1");
      mimeTypes.add(type);
    }
    {
      DownloadMimeType type = new DownloadMimeType();
      type.setManufacturer("Test");
      type.setModel("Test2");
      type.setMimeType("application/test2");
      type.setSuffix(".test2");
      mimeTypes.add(type);
    }
    FirmwareMimeTypeHelper.save(file);
  }

}
