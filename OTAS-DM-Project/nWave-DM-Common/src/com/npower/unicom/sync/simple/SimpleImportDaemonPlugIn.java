/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/simple/SimpleImportDaemonPlugIn.java,v 1.2 2008/11/19 07:37:44 chenlei Exp $
 * $Revision: 1.2 $
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
package com.npower.unicom.sync.simple;

import java.io.File;

import com.npower.unicom.sync.AbstractImportDaemonPlugIn;
import com.npower.unicom.sync.SyncItemReader;
import com.npower.unicom.sync.SyncItemWriter;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/11/19 07:37:44 $
 */
public class SimpleImportDaemonPlugIn extends AbstractImportDaemonPlugIn {

  /**
   * 返回当前任务对应的SyncItemWriter.
   * @return
   */
  protected SyncItemWriter getSyncItemWriter(File requestFile) {
    SyncItemWriter writer = null;
    return writer;
  }

  /**
   * 返回当前任务对应的SyncItemReader
   * @param requestFile
   * @return
   */
  protected SyncItemReader getSyncItemReader(File requestFile) {
    SyncItemReader reader = new FileSyncItemReaderImpl(requestFile);
    return reader;
  }
}
