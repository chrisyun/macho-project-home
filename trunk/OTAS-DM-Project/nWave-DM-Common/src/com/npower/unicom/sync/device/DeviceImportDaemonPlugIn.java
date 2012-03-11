/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/device/DeviceImportDaemonPlugIn.java,v 1.4 2008/11/19 07:39:13 chenlei Exp $
 * $Revision: 1.4 $
 * $Date: 2008/11/19 07:39:13 $
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.npower.dm.core.DMException;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.unicom.sync.AbstractImportDaemonPlugIn;
import com.npower.unicom.sync.SyncItemReader;
import com.npower.unicom.sync.SyncItemWriter;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.4 $ $Date: 2008/11/19 07:39:13 $
 */

public class DeviceImportDaemonPlugIn extends AbstractImportDaemonPlugIn {

  private static Log log = LogFactory.getLog(DeviceImportDaemonPlugIn.class);

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.AbstractImportDaemonPlugIn#getSyncItemReader(java.io.File)
   */
  @Override
  protected SyncItemReader getSyncItemReader(File requestFile) {
    FileSyncItemReader4Device fileReader = new FileSyncItemReader4Device(requestFile);
    return fileReader;
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.AbstractImportDaemonPlugIn#getSyncItemWriter()
   */
  @Override
  protected SyncItemWriter getSyncItemWriter(File requestFile) {
    DBSyncItemWriter4Device dbWriter = null;
    try {
      dbWriter = new DBSyncItemWriter4Device(EngineConfig.getProperties());
    } catch (DMException e) {
      log.error("failure to connect DataBase", e);
    }
    return dbWriter;
  }

}
