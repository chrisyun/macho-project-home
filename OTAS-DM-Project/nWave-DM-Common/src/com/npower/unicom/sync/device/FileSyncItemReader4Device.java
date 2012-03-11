/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/device/FileSyncItemReader4Device.java,v 1.2 2008/11/19 05:58:51 zhaowx Exp $
 * $Revision: 1.2 $
 * $Date: 2008/11/19 05:58:51 $
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

import org.apache.commons.lang.StringUtils;
import com.npower.unicom.sync.FileSyncItemReader;
import com.npower.unicom.sync.ParseException;
import com.npower.unicom.sync.SyncAction;
import com.npower.unicom.sync.SyncItem;


/**
 * @author Zhao wanxiang
 * @version $Revision: 1.2 $ $Date: 2008/11/19 05:58:51 $
 */

public class FileSyncItemReader4Device extends FileSyncItemReader {

  
  
  /**
   * @param file
   */
  public FileSyncItemReader4Device(File file) {
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
   String cols[] = StringUtils.splitPreserveAllTokens(line, '\t');
   if (cols == null || cols.length != 8) {
      throw new ParseException("Error data: " + line);
   }
   DeviceSyncItem item = new DeviceSyncItem(cols[0], SyncAction.valueOfByString(cols[1]));
   item.setMsisdn(cols[2]);
   item.setImsi(cols[3]);
   item.setImei(cols[4]);
   item.setCompanyName(cols[5]);
   item.setTerminalModel(cols[6]);
   item.setVersion(cols[7]);
   return item;
  }

}
