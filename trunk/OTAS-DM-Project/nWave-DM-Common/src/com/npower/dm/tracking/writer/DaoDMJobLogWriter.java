/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/writer/DaoDMJobLogWriter.java,v 1.1 2008/08/04 10:21:31 chenlei Exp $
 * $Revision: 1.1 $
 * $Date: 2008/08/04 10:21:31 $
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
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DMException;
import com.npower.dm.dao.DaoFactory;
import com.npower.dm.hibernate.dao.DMTrackingLogJobDAO;
import com.npower.dm.hibernate.entity.DmTrackingLogJob;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.dm.tracking.DMJobLogItem;

/**
 * @author chenlei
 * @version $Revision: 1.1 $ $Date: 2008/08/04 10:21:31 $
 */

public class DaoDMJobLogWriter implements DMJobLogWriter {

  private static Log log = LogFactory.getLog(DaoDMJobLogWriter.class);
  
  /**
   * 
   */
  public DaoDMJobLogWriter() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.writer.AccessLogWriter#write(com.npower.dm.tracking.AccessLogItem)
   */
  public void write(DMJobLogItem item) throws IOException {
    DaoFactory factory = null;
    try {
      factory = DaoFactory.newInstance(EngineConfig.getProperties());
      DMTrackingLogJobDAO trackingdao = (DMTrackingLogJobDAO)factory.newDaoInstance(DMTrackingLogJobDAO.class);
      factory.beginTransaction();
      DmTrackingLogJob dmtrackinglogjob = new DmTrackingLogJob();
      dmtrackinglogjob.setDmSessionId(item.getDmSessionId());
      dmtrackinglogjob.setDeviceExternalId(item.getDeviceExternalId());
      dmtrackinglogjob.setProcessor(item.getProcessor());
      dmtrackinglogjob.setStatusCode(item.getStatusCode());
      dmtrackinglogjob.setBeginTimeStamp(item.getStartTimeStamp());
      dmtrackinglogjob.setEndTimeStamp(item.getEndTimeStamp());
      long jobId = Long.parseLong(item.getJobId());
      dmtrackinglogjob.setJobId(jobId);
      dmtrackinglogjob.setUpdatedTime(new Date());
      trackingdao.save(dmtrackinglogjob);
      factory.commit();
    } catch (DMException e) {
      if (factory != null) {
        factory.rollback();
      }
      log.error("Failure to save DM Tracking JOB log.", e);
    } finally {
      if (factory != null) {
        factory.release();
      }
    }
  }

}
