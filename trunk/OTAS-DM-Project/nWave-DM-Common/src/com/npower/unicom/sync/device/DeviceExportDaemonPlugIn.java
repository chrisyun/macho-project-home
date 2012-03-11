/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/device/DeviceExportDaemonPlugIn.java,v 1.3 2008/11/19 07:00:00 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/11/19 07:00:00 $
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

package com.npower.unicom.sync.device;

import java.io.File;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.unicom.sync.AbstractExportDaemonPlugIn;
import com.npower.unicom.sync.DBSyncItemReader;
import com.npower.unicom.sync.SyncItemWriter;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.3 $ $Date: 2008/11/19 07:00:00 $
 */

public class DeviceExportDaemonPlugIn extends AbstractExportDaemonPlugIn {
  private static Log log = LogFactory.getLog(DeviceExportDaemonPlugIn.class);

  @Override
  protected DBSyncItemReader<Device> getSyncItemReader() {
    try {
      DBSyncItemReader4Device dbReader = new DBSyncItemReader4Device(EngineConfig.getProperties());
      return dbReader;
    } catch (DMException e) {
      log.error("failure to create a SyncItemReader", e);
    }
    return null;
  }

  @Override
  protected SyncItemWriter getSyncItemWriter(File requestFile, Date fromTime, Date toTime) {
    FileSyncItemWriter4Device writer = new FileSyncItemWriter4Device(requestFile, fromTime, toTime);
    return writer;
  }



}
