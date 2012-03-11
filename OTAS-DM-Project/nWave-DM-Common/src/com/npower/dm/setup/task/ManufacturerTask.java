/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/ManufacturerTask.java,v 1.2 2008/11/24 10:46:56 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2008/11/24 10:46:56 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.setup.task;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.Manufacturer;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.setup.core.SetupException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/11/24 10:46:56 $
 */
public class ManufacturerTask extends DMTask {
  private Log log = LogFactory.getLog(ManufacturerTask.class);

  /**
   * Default constructor
   */
  public ManufacturerTask() {
    super();
  }
  
  private List<String> getFilenames() {
    return this.getPropertyValues("import.files");
  }

  /**
   * @param item
   * @param manufactuer
   */
  private void copy(ManufacturerItem item, Manufacturer manufactuer) {
    manufactuer.setExternalId(item.getExternalID());
    manufactuer.setName(item.getName());
    manufactuer.setDescription(item.getDescription());
  }

  /* (non-Javadoc)
   * @see com.npower.setup.core.AbstractTask#process()
   */
  @Override
  protected void process() throws SetupException {
    List<String> filenames = this.getFilenames();
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory();
        ModelBean modelBean = factory.createModelBean();
        
        factory.beginTransaction();
        for (String filename: filenames) {
            // Process the file, and import data into database.
            File file = new File(filename);
            if (!file.isAbsolute()) {
               file = new File(this.getSetup().getWorkDir(), filename);
            }
            List<ManufacturerItem> items = this.loadManufacturerItems(file.getAbsolutePath());
            for (ManufacturerItem item: items) {
                Manufacturer manufacturer = modelBean.getManufacturerByExternalID(item.getExternalID());
                if (manufacturer == null) {
                   // Create a new manufacturer.
                   manufacturer = modelBean.newManufacturerInstance();
                   log.info("Create a new manufacturer: " + item.getExternalID());
                } else {
                  log.info("Modify a manufacturer: " + item.getExternalID());
                }
                this.copy(item, manufacturer);
                modelBean.update(manufacturer);
            }
        }
        factory.commit();
        
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      throw new SetupException("Error in import manufacturers.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /* (non-Javadoc)
   * @see com.npower.setup.core.AbstractTask#rollback()
   */
  @Override
  protected void rollback() throws SetupException {
    // TODO Auto-generated method stub

  }

}
