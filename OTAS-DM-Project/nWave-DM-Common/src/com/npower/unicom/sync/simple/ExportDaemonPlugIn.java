/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/simple/ExportDaemonPlugIn.java,v 1.2 2008/11/19 07:00:00 zhao Exp $
 * $Revision: 1.2 $
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
package com.npower.unicom.sync.simple;

import java.io.File;
import java.util.Date;

import com.npower.dm.core.DMException;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.unicom.sync.AbstractExportDaemonPlugIn;
import com.npower.unicom.sync.DBSyncItemReader;
import com.npower.unicom.sync.SyncItemWriter;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/11/19 07:00:00 $
 */
public class ExportDaemonPlugIn extends AbstractExportDaemonPlugIn {

  /**
   * 
   */
  public ExportDaemonPlugIn() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.AbstractExportDaemonPlugIn#getSyncItemReader()
   */
  @Override
  protected DBSyncItemReader<?> getSyncItemReader() {
    try {
      return new DBSyncItemReaderImpl(EngineConfig.getProperties());
    } catch (DMException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.AbstractExportDaemonPlugIn#getSyncItemWriter(java.io.File)
   */
  @Override
  protected SyncItemWriter getSyncItemWriter(File requestFile, Date fromTime, Date toTime) {
    return new FileSyncItemWriterImpl(requestFile, fromTime, toTime);
  }

}
