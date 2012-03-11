/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/firmware/FirmwareExportDaemonPlugIn.java,v 1.3 2008/11/19 07:37:44 chenlei Exp $
 * $Revision: 1.3 $
 * $Date: 2008/11/19 07:37:44 $
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

package com.npower.unicom.sync.firmware;


import java.io.File;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Update;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.unicom.sync.AbstractExportDaemonPlugIn;
import com.npower.unicom.sync.DBSyncItemReader;
import com.npower.unicom.sync.SyncItemWriter;

/**
 * @author chenlei
 * @version $Revision: 1.3 $ $Date: 2008/11/19 07:37:44 $
 */

public class FirmwareExportDaemonPlugIn extends AbstractExportDaemonPlugIn{

  private static Log log = LogFactory.getLog(FirmwareExportDaemonPlugIn.class);
  
  @Override
  protected DBSyncItemReader<Update> getSyncItemReader() {
    FirmwareDBSyncItemReader dbReader = null;
    try {
      dbReader = new FirmwareDBSyncItemReader(EngineConfig.getProperties());
    } catch (DMException e) {
      log.error(e.getMessage(), e);
    }
    return dbReader;
  }

  @Override
  protected SyncItemWriter getSyncItemWriter(File requestFile, Date fromTime, Date toTime) {
    FirmwareFileSyncItemWriter fileWriter = new FirmwareFileSyncItemWriter(requestFile, fromTime, toTime);
    return fileWriter;
  }

}
