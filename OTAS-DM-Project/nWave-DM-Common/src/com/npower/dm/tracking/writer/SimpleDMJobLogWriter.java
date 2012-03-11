/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/writer/SimpleDMJobLogWriter.java,v 1.3 2008/08/04 05:48:00 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/08/04 05:48:00 $
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.npower.dm.tracking.DMJobLogItem;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/08/04 05:48:00 $
 */
public class SimpleDMJobLogWriter implements DMJobLogWriter {

  /**
   * 
   */
  private OutputStream output = System.out;

  /**
   * 
   */
  public SimpleDMJobLogWriter() {
    super();
  }

  /**
   * Provider a OutputStream to output log information. Default is System.out.
   * @param output
   */
  public SimpleDMJobLogWriter(OutputStream output) {
    super();
    this.output = output;
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
   * @see com.npower.dm.tracking.writer.DMJobLogWriter#write(com.npower.dm.tracking.DMSessionLogItem)
   */
  public void write(DMJobLogItem item) throws IOException {
    StringBuffer buf = new StringBuffer();
    
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    buf.append('[');
    buf.append(this.getClass().getCanonicalName());
    buf.append(']');
    buf.append(":[start=");
    buf.append(dateFormat.format(item.getStartTimeStamp()));
    buf.append(",end=");
    Date endTimeStamp = (item.getEndTimeStamp() == null)?new Date():item.getEndTimeStamp();
    buf.append(dateFormat.format(endTimeStamp));
    buf.append("]:sessionid=");
    buf.append(item.getDmSessionId());
    buf.append(":jobid=");
    buf.append(item.getJobId());
    buf.append(":deviceid=");
    buf.append(item.getDeviceExternalId());
    buf.append(":processor=");
    buf.append(item.getProcessor());
    buf.append(":status=");
    buf.append(item.getStatusCode());
    buf.append('\n');
    
    this.getOutput().write(buf.toString().getBytes());
    this.getOutput().flush();
  }

}
