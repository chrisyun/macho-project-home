/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/DMJobLoggerImpl.java,v 1.3 2008/08/04 05:48:00 zhao Exp $
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
package com.npower.dm.tracking;

import java.io.IOException;
import java.util.Date;

import sync4j.framework.engine.dm.ManagementProcessor;

import com.npower.dm.tracking.writer.DMJobLogWriter;
import com.npower.dm.tracking.writer.SimpleDMJobLogWriter;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/08/04 05:48:00 $
 */
public class DMJobLoggerImpl implements DMJobLogger {
  
  private DMJobLogWriter writer = new SimpleDMJobLogWriter();

  /**
   * 
   */
  public DMJobLoggerImpl() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.DMJobLogger#getWriter()
   */
  public DMJobLogWriter getWriter() {
    return this.writer;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.DMJobLogger#setWriter(com.npower.dm.tracking.writer.DMJobLogWriter)
   */
  public void setWriter(DMJobLogWriter writer) {
    this.writer = writer;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.DMJobLogger#log(java.lang.String, long, java.lang.String, sync4j.framework.engine.dm.ManagementProcessor, java.util.Date, java.util.Date, java.lang.String)
   */
  public void log(String dmSessionID, long jobID, String deviceExtID, ManagementProcessor processor, Date startTime,
      Date endTime, String sessionStatus) throws IOException {
    DMJobLogItem item = new DMJobLogItem();
    item.setDeviceExternalId(deviceExtID);
    item.setDmSessionId(dmSessionID);
    item.setJobId(Long.toString(jobID));
    item.setStartTimeStamp(startTime);
    item.setEndTimeStamp((endTime == null)?new Date():endTime);
    item.setProcessor(processor.getClass().getName());
    item.setStatusCode(sessionStatus);
    if (this.writer != null) {
       this.writer.write(item);
    }
  }

}
