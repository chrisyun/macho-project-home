/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/device/FileSyncItemWriter4Device.java,v 1.3 2008/11/19 07:00:00 zhao Exp $
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

import com.npower.unicom.sync.FileSyncItemWriter;
import com.npower.unicom.sync.SyncItem;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.3 $ $Date: 2008/11/19 07:00:00 $
 */

public class FileSyncItemWriter4Device extends FileSyncItemWriter {

  /**
   * @param file
   */
  public FileSyncItemWriter4Device(File file, Date fromDate, Date toDate) {
    super(file, fromDate, toDate);
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.FileSyncItemWriter#convertToString(com.npower.unicom.sync.SyncItem)
   */
  @Override
  protected String convertToString(SyncItem item) {
    
    StringBuffer buf = new StringBuffer();
    if (item == null) {
       return buf.toString();
    }
    buf.append(item.getId());
    buf.append('\t');
    buf.append(item.getAction().getValue());
    if (item instanceof DeviceSyncItem) {
       buf.append('\t');
       buf.append(((DeviceSyncItem)item).getMsisdn());
       buf.append('\t');
       String imsi = ((DeviceSyncItem)item).getImsi();
       buf.append((imsi==null)?"":imsi);
       buf.append('\t');
       String imei = ((DeviceSyncItem)item).getImei();
       if (imei.toUpperCase().startsWith("IMEI:")) {
          imei = imei.substring("IMEI:".length());
       }
       buf.append(imei);
       buf.append('\t');
       buf.append(((DeviceSyncItem)item).getCompanyName());
       buf.append('\t');
       buf.append(((DeviceSyncItem)item).getTerminalModel());
       buf.append('\t');
       String version = ((DeviceSyncItem)item).getVersion();
       buf.append((version==null)?"":version);
    }
    buf.append('\n');
    return buf.toString();    
  }

}
