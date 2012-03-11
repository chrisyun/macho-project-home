/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/writer/DaoAccessLogWriter.java,v 1.1 2008/06/18 04:52:03 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/06/18 04:52:03 $
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
import com.npower.dm.hibernate.dao.AccessLogDAO;
import com.npower.dm.hibernate.dao.AccessLogHeaderDAO;
import com.npower.dm.hibernate.dao.AccessLogParameterDAO;
import com.npower.dm.hibernate.entity.AccessLogEntity;
import com.npower.dm.hibernate.entity.AccessLogHeaderEntity;
import com.npower.dm.hibernate.entity.AccessLogParameterEntity;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.dm.tracking.AccessLog;
import com.npower.dm.tracking.AccessLogHeader;
import com.npower.dm.tracking.AccessLogItem;
import com.npower.dm.tracking.AccessLogParameter;
import com.npower.dm.tracking.NameValuePair;
import com.npower.dm.tracking.NameValuesPair;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/06/18 04:52:03 $
 */
public class DaoAccessLogWriter implements AccessLogWriter {
  
  private static Log log = LogFactory.getLog(DaoAccessLogWriter.class);
  /**
   * 
   */
  public DaoAccessLogWriter() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.tracking.AccessLogWriter#write(com.npower.dm.myportal.tracking.AccessLogItem)
   */
  public void write(AccessLogItem item) throws IOException {
    // TODO Improve performance
    DaoFactory factory = null;
    try {
      factory = DaoFactory.newInstance(EngineConfig.getProperties());
      AccessLogDAO logDao = (AccessLogDAO)factory.newDaoInstance(AccessLogDAO.class);
      AccessLogHeaderDAO logHeaderDao = (AccessLogHeaderDAO)factory.newDaoInstance(AccessLogHeaderDAO.class);
      AccessLogParameterDAO logParameterDao = (AccessLogParameterDAO)factory.newDaoInstance(AccessLogParameterDAO.class);
      
      factory.beginTransaction();
      AccessLog log = new AccessLogEntity(item.getUrl(), item.getQuery(), item.getClientIP(), item.getUserAgent(), item.getSessionID());
      logDao.save(log);
      
      for (NameValuePair pair: item.getHeaders()) {
          AccessLogHeader header = new AccessLogHeaderEntity(log, pair.getName(), pair.getValue());
          logHeaderDao.save(header);
      }
      
      for (NameValuesPair pair: item.getParameters()) {
          AccessLogParameter parameter = new AccessLogParameterEntity(log, pair.getName(), pair.getValues().toString());
          logParameterDao.save(parameter);
      }
      factory.commit();
    } catch (Exception e) {
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
