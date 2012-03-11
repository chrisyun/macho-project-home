/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/firmware/FirmwareDBSyncItemWriter.java,v 1.5 2008/11/20 09:17:03 zhao Exp $
 * $Revision: 1.5 $
 * $Date: 2008/11/20 09:17:03 $
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Image;
import com.npower.dm.core.Model;
import com.npower.dm.core.Update;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.UpdateImageBean;
import com.npower.unicom.sync.DBSyncItemWriter;
import com.npower.unicom.sync.SyncItem;

/**
 * @author chenlei
 * @version $Revision: 1.5 $ $Date: 2008/11/20 09:17:03 $
 */

public class FirmwareDBSyncItemWriter extends DBSyncItemWriter{

  private File requestFile;

  public FirmwareDBSyncItemWriter(Properties props, File requestFile) {
    super(props);
    this.requestFile = requestFile;
  }

  @Override
  public void write(SyncItem item) throws IOException, DMException {
    UpdateImageBean updateBean = this.getFactory().createUpdateImageBean();
      
    if (item instanceof FirmwareSyncItem) {
      ModelBean modelBean = this.getFactory().createModelBean();
      
      FirmwareSyncItem firmwareSyncItem = (FirmwareSyncItem)item;
      Model model = modelBean.getModelByExternalId(firmwareSyncItem.getManufacturer(), firmwareSyncItem.getModel());
      
      Image fromImage = updateBean.getImageByVersionID(model, firmwareSyncItem.getFromVersion());
      if (fromImage == null) {
        fromImage = updateBean.newImageInstance(model, firmwareSyncItem.getFromVersion(), true, firmwareSyncItem.getFromVersion());
      }
      
      Image toImage = updateBean.getImageByVersionID(model, firmwareSyncItem.getToVersion());
      if (toImage == null) {
        toImage = updateBean.newImageInstance(model, firmwareSyncItem.getToVersion(), true, firmwareSyncItem.getToVersion());
      }
      
      Update update = updateBean.getUpdate(model, firmwareSyncItem.getFromVersion(), firmwareSyncItem.getToVersion());
      if (update != null) {
         this.getFactory().beginTransaction();
         updateBean.delete(update);
         this.getFactory().commit();
      }
      
      InputStream in = new FileInputStream(new File(requestFile.getParent(), firmwareSyncItem.getFirmwareName()));
      this.getFactory().beginTransaction();
      update = updateBean.newUpdateInstance(fromImage, toImage, in);
      
      update.setFromImage(fromImage);
      update.setToImage(toImage);
      update.setStatus(updateBean.getImageUpdateStatus(Image.STATUS_RELEASED));
      updateBean.update(update);
      
      this.getFactory().commit();
    }
  }
}
