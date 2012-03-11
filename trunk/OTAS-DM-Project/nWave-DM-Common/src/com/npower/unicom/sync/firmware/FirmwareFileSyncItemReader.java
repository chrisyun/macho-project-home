/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/firmware/FirmwareFileSyncItemReader.java,v 1.3 2008/11/19 07:37:44 chenlei Exp $
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

import org.apache.commons.lang.StringUtils;

import com.npower.unicom.sync.FileSyncItemReader;
import com.npower.unicom.sync.ParseException;
import com.npower.unicom.sync.SyncItem;

/**
 * @author chenlei
 * @version $Revision: 1.3 $ $Date: 2008/11/19 07:37:44 $
 */

public class FirmwareFileSyncItemReader extends FileSyncItemReader{
  

  /**
   * 
   */
  public FirmwareFileSyncItemReader(File file) {
    super(file);
  }

  @Override
  protected SyncItem parse(String line) throws ParseException {
    FirmwareSyncItem item = new FirmwareSyncItem();
    String[] cols = StringUtils.splitPreserveAllTokens(line, '\t');
    if (cols == null || cols.length != 7) {
      throw new ParseException("Error data: " + line);
    }
    item.setId(cols[0]);
    item.setActionAsString(cols[1]);
    item.setManufacturer(cols[2]);
    item.setModel(cols[3]);
    item.setFromVersion(cols[4]);
    item.setToVersion(cols[5]);
    item.setFirmwareName(cols[6]);
    return item;
  }

}
