/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/simple/FileSyncItemReaderImpl.java,v 1.2 2008/11/18 09:26:56 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/11/18 09:26:56 $
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

import org.apache.commons.lang.StringUtils;

import com.npower.unicom.sync.FileSyncItemReader;
import com.npower.unicom.sync.ParseException;
import com.npower.unicom.sync.SyncAction;
import com.npower.unicom.sync.SyncItem;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/11/18 09:26:56 $
 */
public class FileSyncItemReaderImpl extends FileSyncItemReader {

  /**
   * @param file
   */
  public FileSyncItemReaderImpl(File file) {
    super(file);
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.FileSyncItemReader#parse(java.lang.String)
   */
  @Override
  protected SyncItem parse(String line) throws ParseException {
    if (StringUtils.isEmpty(line)) {
       return null;
    }
    String cols[] = StringUtils.splitPreserveAllTokens("\t");
    if (cols == null || cols.length != 4) {
       throw new ParseException("Error data: " + line);
    }
    SimpleSyncItem item = new SimpleSyncItem(cols[0], SyncAction.valueOfByString(cols[1]));
    item.setBrand(cols[2]);
    item.setModel(cols[4]);
    return item;
  }

}
