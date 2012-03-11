/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/writer/DaoDMSessionLogWriter.java,v 1.2 2008/08/04 10:59:30 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/08/04 10:59:30 $
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.dao.DaoFactory;
import com.npower.dm.hibernate.dao.DmTrackingLogHttpDAO;
import com.npower.dm.hibernate.entity.DmTrackingLogHttp;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.dm.tracking.DMSessionLogItem;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.2 $ $Date: 2008/08/04 10:59:30 $
 */

public class DaoDMSessionLogWriter implements DMSessionLogWriter {
  private static Log log = LogFactory.getLog(DaoAccessLogWriter.class);
  /**
   * 
   */
  public DaoDMSessionLogWriter() {
    // TODO Auto-generated constructor stub
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.writer.DMSessionLogWriter#write(com.npower.dm.tracking.DMSessionLogItem)
   */
  public void write(DMSessionLogItem item) throws IOException {
    DaoFactory factory = null;
    try {
      factory = DaoFactory.newInstance(EngineConfig.getProperties());
      DmTrackingLogHttpDAO logDao = (DmTrackingLogHttpDAO)factory.newDaoInstance(DmTrackingLogHttpDAO.class);
      
      factory.beginTransaction();
      DmTrackingLogHttp logEntity = new DmTrackingLogHttp();
      logEntity.setClientIp(item.getClientIp());
      logEntity.setDmRequestSize(item.getSizeRequestData());
      logEntity.setDmResponseSize(item.getSizeResponseData());
      logEntity.setDmSessionId(item.getDmSessionId());
      logEntity.setTimeStamp(item.getTimeStamp());
      logEntity.setUserAgent(item.getUserAgent());
      logDao.save(logEntity);
      factory.commit();
    }  catch (Exception e){
      if (factory != null) {
        factory.rollback();
     }
      log.error("Failure to save access log.", e);
    } finally {
      if (factory != null) {
        factory.release();
     }
    }
  }

}
