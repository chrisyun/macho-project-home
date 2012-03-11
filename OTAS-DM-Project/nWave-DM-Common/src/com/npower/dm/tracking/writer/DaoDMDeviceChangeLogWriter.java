/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/writer/DaoDMDeviceChangeLogWriter.java,v 1.2 2008/08/06 07:59:23 zhao Exp $
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
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DMException;
import com.npower.dm.dao.DaoFactory;
import com.npower.dm.hibernate.dao.DMDeviceChangeLogDAO;
import com.npower.dm.hibernate.entity.DmDeviceChangeLog;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.dm.tracking.DeviceChangeLogItem;

/**
 * @author chenlei
 * @version $Revision: 1.2 $ $Date: 2008/08/06 07:59:23 $
 */

public class DaoDMDeviceChangeLogWriter implements DeviceChangeLogWriter {

  private static Log log = LogFactory.getLog(DaoDMDeviceChangeLogWriter.class);
  
  /**
   * 
   */
  public DaoDMDeviceChangeLogWriter() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.writer.DeviceChangeLogWriter#write(com.npower.dm.tracking.DeviceChangeLogItem)
   */
  public void write(DeviceChangeLogItem item) throws IOException {
    DaoFactory factory = null;
    try {
      factory = DaoFactory.newInstance(EngineConfig.getProperties());
      DMDeviceChangeLogDAO devicedao = (DMDeviceChangeLogDAO)factory.newDaoInstance(DMDeviceChangeLogDAO.class);
      factory.beginTransaction();
      DmDeviceChangeLog dmdevicechangelog = new DmDeviceChangeLog();
      dmdevicechangelog.setImei(item.getImei());
      dmdevicechangelog.setPhoneNumber(item.getMsisdn());
      dmdevicechangelog.setImsi(item.getImsi());
      dmdevicechangelog.setBrand(item.getBrandName());
      dmdevicechangelog.setModel(item.getModelName());
      dmdevicechangelog.setLastUpdate(new Date());
      dmdevicechangelog.setSoftwareVersion(item.getSoftwareVersion());
      devicedao.save(dmdevicechangelog);
      factory.commit();
    } catch (DMException e) {
      if (factory != null) {
        factory.rollback();
      }
      log.error("Failure to save DM Device Change log.", e);
    } finally {
      if (factory != null) {
        factory.release();
      }
    }
  }

}
