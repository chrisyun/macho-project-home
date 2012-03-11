/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/SoftwareCategoryTask.java,v 1.4 2008/11/28 10:26:17 chenlei Exp $
 * $Revision: 1.4 $
 * $Date: 2008/11/28 10:26:17 $
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

import com.npower.dm.core.DMException;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;
import com.npower.setup.core.SetupException;

/**
 * @author chenlei
 * @version $Revision: 1.4 $ $Date: 2008/11/28 10:26:17 $
 */

public class SoftwareCategoryTask extends DMTask {
  private List<String> filenames;
  

  /**
   * 
   */
  public SoftwareCategoryTask() {
    super();
  }
  
  
  public void copy(SoftwareCategoryItem item, SoftwareCategory softwareCategory, SoftwareCategory parentCategory) {
    softwareCategory.setName(item.getName());
    softwareCategory.setDescription(item.getDescription());
    softwareCategory.setParent(parentCategory);
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

  /* (non-Javadoc)
   * @see com.npower.setup.core.AbstractTask#process()
   */
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
            List<SoftwareCategoryItem> items = this.loadSoftwareCategoryItems(file.getAbsolutePath());
            update(items, softwareBean, null);
        }
        factory.commit();
        
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      throw new SetupException("Error in import SoftwareCategory.", ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    } 
  }

  
  protected void update(List<SoftwareCategoryItem> items, SoftwareBean softwareBean, SoftwareCategory parentCategory) throws DMException{
    for (SoftwareCategoryItem item: items) {
      SoftwareCategory softwareCategory = softwareBean.newCategoryInstance();
      this.copy(item, softwareCategory, parentCategory);
      softwareBean.update(softwareCategory);
      if (item.getSoftwareCategory().size() > 0) {
        update(item.getSoftwareCategory(), softwareBean, softwareCategory);
      }          
    }
}
  /* (non-Javadoc)
   * @see com.npower.setup.core.AbstractTask#rollback()
   */
  @Override
  protected void rollback() throws SetupException {
  }

}
