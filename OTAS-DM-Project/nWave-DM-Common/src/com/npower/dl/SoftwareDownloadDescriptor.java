/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dl/SoftwareDownloadDescriptor.java,v 1.3 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2008/06/16 10:11:15 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dl;

import com.npower.dm.core.DMException;
import com.npower.dm.core.SoftwarePackage;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.SoftwareBean;
import com.npower.dm.server.engine.EngineConfig;


/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/06/16 10:11:15 $
 */
public class SoftwareDownloadDescriptor extends BaseDownloadDescriptor implements DownloadDescriptor {

  /**
   * 
   */
  public SoftwareDownloadDescriptor() {
    super();
  }

  public String getContent() throws DMException {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        SoftwareBean bean = factory.createSoftwareBean();
        SoftwarePackage pkg = bean.getPackageByID(Long.parseLong(this.getDownloadID()));
        if (pkg == null) {
           throw new DMException("Could not found download package by package ID: " + this.getDownloadID());
        }
        String mimeType = pkg.getMimeType();
        String filename = pkg.getBlobFilename();
        String vendor = pkg.getSoftware().getVendor().getName();
        int size = pkg.getSize();

        String content = "";
        //content += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        content += "<media xmlns=\"http://www.openmobilealliance.org/xmlns/dd\">";
        //content += "<type>application/octet-stream</type>";
        content += "<name>" + vendor + pkg.getSoftware().getName() + "</name>";
        content += "<type>" + mimeType + "</type>";
        content += "<objectURI>" + this.getDownloadURI() + this.getDownloadID() + "/" + filename + "</objectURI>";
        content += "<size>" + size + "</size>";
        content += "<DDVersion>1.0</DDVersion>";
        content += "<vendor>" + vendor + "</vendor>";
        content += "<description>" + vendor + "</description>";
        content += "<installNotifyURI>" + this.getInstallNotifyURI() + "/" + this.getDownloadID() + "</installNotifyURI>";
        content += "</media>";
        
        return content;
    } catch (Exception ex) {
      throw new DMException(ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
}
