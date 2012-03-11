/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/SoftwareVenderTask.java,v 1.2 2008/11/24 10:46:34 zhaowx Exp $
 * $Revision: 1.2 $
 * $Date: 2008/11/24 10:46:34 $
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

package com.npower.dm.setup.task;

import java.io.File;
import java.util.List;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;
import com.npower.setup.core.SetupException;

/**
 * @author Zhao wanxiang
 * @version $Revision: 1.2 $ $Date: 2008/11/24 10:46:34 $
 */

public class SoftwareVenderTask extends DMTask{

  private List<String> filenames = null;
  /**
   * 
   */
  public SoftwareVenderTask() {
    super();
  }

  public List<String> getFilenames() {
    if (this.filenames == null || this.filenames.size() == 0) {
      return this.getPropertyValues("import.files");
    }
    return this.filenames;
  }

  public void setFilenames(List<String> filenames) {
    this.filenames = filenames;
  }
  
  
  /**
   * @param item
   * @param manufactuer
   */
  public void copy(SoftwareVenderItem item, SoftwareVendor softwareVendor) {
    softwareVendor.setName(item.getName());
    softwareVendor.setDescription(item.getDescription());
  }
  
  
  @Override
  protected void process() throws SetupException {
    filenames = this.getFilenames();
    ManagementBeanFactory factory = null;
    try {
        factory = this.getManagementBeanFactory();
        SoftwareBean softwareBean = factory.createSoftwareBean();
        
        factory.beginTransaction();
        for (String filename: filenames) {
            // Process the file, and import data into database.
            File file = new File(filename);
            if (!file.isAbsolute()) {
               file = new File(this.getSetup().getWorkDir(), filename);
            }
            List<SoftwareVenderItem> items = this.loadSoftwareVenderImporter(file.getAbsolutePath());
            for (SoftwareVenderItem item: items) {
              SoftwareVendor softwareVender = softwareBean.newVendorInstance();
                this.copy(item, softwareVender);
                softwareBean.update(softwareVender);
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


  @Override
  protected void rollback() throws SetupException {
    // TODO Auto-generated method stub
    
  }
  
}
