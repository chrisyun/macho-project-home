/**
 * $Header: /home/master/nWave-DM-Common/test/com/npower/dm/util/MToneDeviceDataReaderV2.java,v 1.1 2008/06/16 10:11:15 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/06/16 10:11:15 $
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
package com.npower.dm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/06/16 10:11:15 $
 */
public class MToneDeviceDataReaderV2 implements DeviceDataReader {
  
  private BufferedReader in = null;
  
  private int currentLineNumber = 0;

  /**
   * 
   */
  public MToneDeviceDataReaderV2() {
    super();
  }

  /**
   * 
   */
  public MToneDeviceDataReaderV2(File file) throws IOException {
    super();
    
    this.in = new BufferedReader(new FileReader(file));
  }

  /* (non-Javadoc)
   * @see com.npower.dm.util.DeviceDataReader#close()
   */
  public void close() throws IOException {
    this.in.close();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.util.DeviceDataReader#open()
   */
  public void open() throws IOException {
  }

  /* (non-Javadoc)
   * @see com.npower.dm.util.DeviceDataReader#read()
   */
  public DeviceData read() throws IOException {
    String line = readLine();
    while (StringUtils.isNotEmpty(line) && line.startsWith("#")) {
       line = this.readLine();
    }
    if (StringUtils.isEmpty(line)) {
       return null;
    }
    
    line = line.trim();
    String[] items = StringUtils.split(line, ',');
    if (items == null || items.length < 2) {
       return null;
    }
    String phone = items[0];
    String model = items[1];
    String imei_imsi = items[2];
    String imei = imei_imsi.substring(0, 15);
    String imsi = imei_imsi.substring(15, 30);
    String brand = (items.length < 4)?"NOKIA":items[3];
    
    DeviceData data = new DeviceData();
    data.setManufacturer(brand.trim());
    data.setModel(model.trim());
    data.setPhoneNumber(phone.trim());
    data.setLineNumber(this.currentLineNumber);
    data.setImei("IMEI:" + imei);
    data.setImsi(imsi);
    return data;
  }

  /**
   * @return
   * @throws IOException
   */
  private String readLine() throws IOException {
    String line = this.in.readLine();
    this.currentLineNumber++;
    return line;
  }

}
