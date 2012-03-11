/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/SoftwareTask.java,v 1.4 2008/12/16 04:19:42 chenlei Exp $
 * $Revision: 1.4 $
 * $Date: 2008/12/16 04:19:42 $
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.core.Software;
import com.npower.dm.core.SoftwareCategory;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.core.SoftwareVendor;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ModelBean;
import com.npower.dm.management.SoftwareBean;
import com.npower.setup.core.SetupException;

/**
 * @author chenlei
 * @version $Revision: 1.4 $ $Date: 2008/12/16 04:19:42 $
 */

public class SoftwareTask extends DMTask {
  private List<String> filenames;
  /**
   * 
   */
  public SoftwareTask() {
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
  
  public void copy(SoftwareItem item, Software software) throws DMException {
    software.setExternalId(item.getExternalId());
    software.setName(item.getName());
    software.setVersion(item.getVersion());
    software.setStatus(item.getStatus());
    software.setLicenseType(item.getLicenseType());
    software.setDescription(item.getDescription());
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
        ModelBean modelBean = factory.createModelBean();
        factory.beginTransaction();
        for (String filename: filenames) {
            // Process the file, and import data into database.
            File file = new File(filename);
            if (!file.isAbsolute()) {
               file = new File(this.getSetup().getWorkDir(), filename);
            }
            List<SoftwareItem> items = this.loadSoftwareItems(file.getAbsolutePath());
            SoftwareVendor vendor = null;
            SoftwareCategory category = null;
            if (items != null || items.size() != 0) {
              for (SoftwareItem item: items) {
                Software software = softwareBean.newSoftwareInstance();
                this.copy(item, software);
                vendor = softwareBean.getVendorByName(item.getVendor());
                software.setVendor(vendor);
                softwareBean.update(software);
                category = softwareBean.getCategoryByPath(item.getCategory());
                softwareBean.update(software, category);
                if (item.getSoftwarePackage().size() != 0 || item.getSoftwarePackage() != null) {
                  for (SoftwarePackageItem softwarePackage: item.getSoftwarePackage()) {
                    SoftwarePackage pack = softwareBean.newPackageInstance();
                    pack.setName(softwarePackage.getName());
                    StringBuffer buf = new StringBuffer();
                    buf.append("<InstOpts>");
                    for (int i = 0; i < softwarePackage.getInstallOptions().size(); i ++) {
                      SoftwarePackage4InstallOptionsItem io = softwarePackage.getInstallOptions().get(i);
                      
                      for (int j = 0; j < io.getStdOpt().size(); j ++) {
                        SoftwarePackage4StdOptItem so = io.getStdOpt().get(j);
                        
                        buf.append("<StdOpt name=");
                        buf.append('"');
                        buf.append(so.getName());
                        buf.append('"');
                        buf.append(" value=");
                        buf.append('"');
                        buf.append(so.getValue());
                        buf.append('"');
                        buf.append("/>");
                      }
                      for (int t = 0; t < io.getStdSymOpt().size(); t ++) {
                        SoftwarePackage4StdSymOptItem ss = io.getStdSymOpt().get(t);
                        buf.append("<StdSymOpt name=");
                        buf.append('"');
                        buf.append(ss.getName());
                        buf.append('"');
                        buf.append(" value=");
                        buf.append('"');
                        buf.append(ss.getValue());
                        buf.append('"');
                        buf.append("/>");
                        
                      }
                    }
                    buf.append("</InstOpts>");
                    pack.setInstallationOptions(buf.toString());

                    if (softwarePackage.getRemote() != null) {
                       pack.setUrl(softwarePackage.getRemote().getUrl());
                    }
                    
                    if (softwarePackage.getLocal() != null) {
                      pack.setMimeType(softwarePackage.getLocal().getMimeType());
                      pack.setBlobFilename(softwarePackage.getLocal().getFile());
                    }
                    
                    pack.setSoftware(software);
                    Set<Model> setModel = null;
                    if (softwarePackage.getModels() == null || softwarePackage.getModels().size() == 0) {
                      return;
                    } else {
                      setModel = new HashSet<Model>();
                      for (int i = 0; i < softwarePackage.getModels().size(); i ++) {
                        SoftwarePackage4TargetModelsItem tm = softwarePackage.getModels().get(i);
                        Model m = modelBean.getModelByExternalId(tm.getVendor(), tm.getName());
                        setModel.add(m);
                      }
                    }
                    softwareBean.update(pack);
                    softwareBean.assign(null, setModel, null, pack);
                  }
                }
              }
            }

            
            
            
            
            
        }
        factory.commit();
        
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      throw new SetupException("Error in import Software.", ex);
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
  }

}
