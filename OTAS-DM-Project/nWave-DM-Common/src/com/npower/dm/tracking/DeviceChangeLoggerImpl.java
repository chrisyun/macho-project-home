/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/DeviceChangeLoggerImpl.java,v 1.2 2008/08/06 07:59:23 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/08/06 07:59:23 $
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
package com.npower.dm.tracking;

import java.io.IOException;

import com.npower.dm.tracking.writer.DeviceChangeLogWriter;
import com.npower.dm.tracking.writer.SimpleDeviceChangeLogWriter;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/08/06 07:59:23 $
 */
public class DeviceChangeLoggerImpl implements DeviceChangeLogger {

  private DeviceChangeLogWriter writer = new SimpleDeviceChangeLogWriter();

  /**
   * 
   */
  public DeviceChangeLoggerImpl() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.DeviceChangeLogger#getWriter()
   */
  public DeviceChangeLogWriter getWriter() {
    return this.writer;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.DeviceChangeLogger#setWriter(com.npower.dm.tracking.writer.DeviceChangeLogWriter)
   */
  public void setWriter(DeviceChangeLogWriter writer) {
    this.writer = writer;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.DeviceChangeLogger#log(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  public void log(String imei, String msisdn, String imsi, String brandName, String modelName, String softwareVersion) throws IOException {
    DeviceChangeLogItem item = new DeviceChangeLogItem();
    item.setImei(imei);
    item.setMsisdn(msisdn);
    item.setImsi(imsi);
    item.setBrandName(brandName);
    item.setModelName(modelName);
    item.setSoftwareVersion(softwareVersion);
    
    if (this.writer != null) {
       this.writer.write(item);
    }
    item.setModelName(modelName);
  }

}
