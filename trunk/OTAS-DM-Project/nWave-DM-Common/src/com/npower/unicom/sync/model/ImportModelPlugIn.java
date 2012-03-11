/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/model/ImportModelPlugIn.java,v 1.2 2008/11/19 07:39:13 chenlei Exp $
 * $Revision: 1.2 $
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

package com.npower.unicom.sync.model;

import java.io.File;

import com.npower.dm.server.engine.EngineConfig;
import com.npower.unicom.sync.AbstractImportDaemonPlugIn;
import com.npower.unicom.sync.SyncItemReader;
import com.npower.unicom.sync.SyncItemWriter;

/**
 * @author Huang ChunPing
 * @version $Revision: 1.2 $ ${date}10:16:41 AM$
 * com.npower.unicom.sync.model
 * nWave-DM-Common
 * ImportModelPlugIn.java
 */
public class ImportModelPlugIn extends AbstractImportDaemonPlugIn {

  File file = new File("");
  
  /**
   * 
   */
  public ImportModelPlugIn() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.AbstractImportDaemonPlugIn#getSyncItemReader(java.io.File)
   */
  @Override
  protected SyncItemReader getSyncItemReader(File requestFile) {
      return new FileSyncModelItemReader(requestFile);
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.AbstractImportDaemonPlugIn#getSyncItemWriter()
   */
  @Override
  protected SyncItemWriter getSyncItemWriter(File requestFile) {
    try {
      return new DBSyncModelItemWriter(EngineConfig.getProperties());
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

}
