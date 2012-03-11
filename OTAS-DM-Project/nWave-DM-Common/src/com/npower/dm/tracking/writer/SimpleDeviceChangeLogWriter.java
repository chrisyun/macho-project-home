/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/writer/SimpleDeviceChangeLogWriter.java,v 1.2 2008/08/06 07:59:23 zhao Exp $
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
package com.npower.dm.tracking.writer;

import java.io.IOException;
import java.io.OutputStream;

import com.npower.dm.tracking.DeviceChangeLogItem;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/08/06 07:59:23 $
 */
public class SimpleDeviceChangeLogWriter implements DeviceChangeLogWriter {

  private OutputStream output = System.out;

  /**
   * 
   */
  public SimpleDeviceChangeLogWriter() {
    super();
  }

  /**
   * Provider a OutputStream to output log information. Default is System.out.
   * @param output
   */
  public SimpleDeviceChangeLogWriter(OutputStream output) {
    super();
    this.output  = output;
  }

  /**
   * OutputStream to output log information.
   * @return
   */
  public OutputStream getOutput() {
    return output;
  }

  /**
   * OutputStream to output log information.
   * @param output
   */
  public void setOutput(OutputStream output) {
    this.output = output;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.writer.DeviceChangeLogWriter#write(com.npower.dm.tracking.DeviceChangeLogItem)
   */
  public void write(DeviceChangeLogItem item) throws IOException {
    StringBuffer buf = new StringBuffer();
    
    buf.append('[');
    buf.append(this.getClass().getCanonicalName());
    buf.append(']');
    buf.append(":imei=");
    buf.append(item.getImei());
    buf.append(":imsi=");
    buf.append(item.getImsi());
    buf.append(":msisdn=");
    buf.append(item.getMsisdn());
    buf.append(":brand=");
    buf.append(item.getBrandName());
    buf.append(":model=");
    buf.append(item.getModelName());
    buf.append(":swV=");
    buf.append(item.getSoftwareVersion());
    buf.append('\n');
    
    this.getOutput().write(buf.toString().getBytes());
    this.getOutput().flush();
  }

}
