/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/firmware/FirmwareFileSyncItemWriter.java,v 1.8 2008/11/28 10:26:34 chenlei Exp $
 * $Revision: 1.8 $
 * $Date: 2008/11/28 10:26:34 $
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.unicom.sync.FileSyncItemWriter;
import com.npower.unicom.sync.SyncItem;

/**
 * @author chenlei
 * @version $Revision: 1.8 $ $Date: 2008/11/28 10:26:34 $
 */

public class FirmwareFileSyncItemWriter extends FileSyncItemWriter {
  private static  Log log = LogFactory.getLog(FirmwareDBSyncItemReader.class);

  public FirmwareFileSyncItemWriter(File file, Date fromDate, Date toDate) {
    super(file, fromDate, toDate);
  }

  @Override
  protected String convertToString(SyncItem item) {
    StringBuffer buf = new StringBuffer();
    if (item == null) {
      return buf.toString();
    }
    buf.append(item.getId());
    buf.append('\t');
    buf.append(item.getAction().getValue());
    buf.append('\t');
    if (item instanceof FirmwareSyncItem) {
      FirmwareSyncItem newitem = (FirmwareSyncItem) item;
      
      buf.append(newitem.getManufacturer());
      buf.append('\t');
      buf.append(newitem.getModel());
      buf.append('\t');
      buf.append(newitem.getFromVersion());
      buf.append('\t');
      buf.append(newitem.getToVersion());
      buf.append('\t');
      buf.append(newitem.getFirmwareName());
      buf.append(".fw");
      buf.append('\n');
      File fwFile = new File(this.getFile().getParent(), newitem.getId() + ".fw");
      try {
        FileOutputStream out = new FileOutputStream(fwFile);
        out.write(newitem.getBytes());
        out.close();
      } catch (FileNotFoundException e) {
        log.error(e.getMessage(), e);
      } catch (IOException e) {
        log.error(e.getMessage(), e);
      }
    }
    return buf.toString();
  }

}
