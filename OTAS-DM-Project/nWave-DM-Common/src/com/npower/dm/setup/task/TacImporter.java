/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/TacImporter.java,v 1.1 2008/10/31 10:03:04 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/10/31 10:03:04 $
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.Model;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/10/31 10:03:04 $
 */
public class TacImporter {
  
  private static Log log = LogFactory.getLog(TacImporter.class);

  private File file;
  private ManagementBeanFactory factory;

  /**
   * Default constructor
   */
  public TacImporter() {
    super();
  }
  
  /**
   * Default constructor
   */
  public TacImporter(File file, ManagementBeanFactory factory) {
    super();
    this.file = file;
    this.factory = factory;
  }
  
  /**
   * @return the file
   */
  public File getFile() {
    return file;
  }

  /**
   * @param file the file to set
   */
  public void setFile(File file) {
    this.file = file;
  }

  /**
   * @return the factory
   */
  public ManagementBeanFactory getFactory() {
    return factory;
  }

  /**
   * @param factory the factory to set
   */
  public void setFactory(ManagementBeanFactory factory) {
    this.factory = factory;
  }

  public void process() throws Exception {
    TacItem item = new TacItem(0);
    try {
      ModelBean bean = this.getFactory().createModelBean();
      
      TacItemParser parser = new TacItemParser(this.getFile());
      parser.open();
      item = parser.getNext();
      while (item != null) {
            Model model = bean.getModelByExternalId(item.getHandsetBrand(), item.getHandsetModel());
            if (model != null) {
               factory.beginTransaction();
               bean.addTACInfo(model, item.getTac());
               factory.commit();
               if (log.isDebugEnabled()) {
                  log.debug("Line#" + item.getLineNumber() + ",TAC[" + item.getTac() + "] imported into [" + model.getManufacturer().getExternalId() + " " + model.getManufacturerModelId() + "]");
               }
            } else {
              if (log.isDebugEnabled()) {
                log.debug("Line#" + item.getLineNumber() + ",Model noexists!, TAC[" + item.getTac() + ", [" + item.getHandsetBrand() + " " + item.getHandsetModel() + "]");
              }
            }
            item = parser.getNext();
      }
      parser.close();
    } catch (Exception e) {
      throw new Exception("Failure to import TAC in file: " + this.getFile().getAbsolutePath() + "#" + item.getLineNumber(), e);
    }
    
  }

}
