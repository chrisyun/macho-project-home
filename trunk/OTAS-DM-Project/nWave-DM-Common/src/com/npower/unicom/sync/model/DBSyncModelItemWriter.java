/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/model/DBSyncModelItemWriter.java,v 1.3 2008/11/19 08:12:12 hcp Exp $
 * $Revision: 1.3 $
 * $Date: 2008/11/19 08:12:12 $
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

import java.io.IOException;
import java.util.Properties;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;
import com.npower.dm.management.ModelBean;
import com.npower.unicom.sync.DBSyncItemWriter;
import com.npower.unicom.sync.SyncItem;

/**
 * @author Huang ChunPing
 * @version $Revision: 1.3 $ ${date}6:36:25 PM$
 * com.npower.unicom.sync.model
 * nWave-DM-Common
 * DBSyncModelItemWriter.java
 */
public class DBSyncModelItemWriter extends DBSyncItemWriter {

  /**
   * @param props
   */
  public DBSyncModelItemWriter(Properties props) {
    super(props);
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.DBSyncItemWriter#write(com.npower.unicom.sync.SyncItem)
   */
  @Override
  public void write(SyncItem item) throws IOException, DMException {
    if (!(item instanceof SyncModelItem)) {
       throw new DMException("Unexptected SymcItem Type: " + item.getClass().getCanonicalName());
    }
    
    SyncModelItem modelItem = (SyncModelItem) item;      
    ModelBean modelBean = this.getFactory().createModelBean();
    try {
      this.getFactory().beginTransaction();
      Manufacturer manufacturer = modelBean.getManufacturerByExternalID(modelItem.getCompanyName());
      if (manufacturer == null) {
         manufacturer = modelBean.newManufacturerInstance(modelItem.getCompanyName(), modelItem.getCompanyName(), modelItem.getCompanyName());
         modelBean.update(manufacturer);
      }
      Model model = modelBean.getModelByManufacturerModelID(manufacturer, modelItem.getTerminalModel());
      if (model == null) {
         model = modelBean.newModelInstance(manufacturer, modelItem.getTerminalModel(), modelItem.getTerminalModel(), true, false, false, false, false);
         modelBean.update(model);
      }
      this.getFactory().commit();
    } catch (Exception e) {
      this.getFactory().rollback();
      throw new DMException(e.getMessage(), e);
    } finally {
    }
  }

}
