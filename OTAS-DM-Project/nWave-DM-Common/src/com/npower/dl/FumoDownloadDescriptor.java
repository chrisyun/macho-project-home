/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dl/FumoDownloadDescriptor.java,v 1.6 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.6 $
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
import com.npower.dm.core.Update;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.UpdateImageBean;
import com.npower.dm.server.engine.EngineConfig;


/**
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/06/16 10:11:15 $
 */
public class FumoDownloadDescriptor extends BaseDownloadDescriptor implements DownloadDescriptor {

  /**
   * 
   */
  public FumoDownloadDescriptor() {
    super();
  }

  public String getContent() throws DMException {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        UpdateImageBean updateBean = factory.createUpdateImageBean();
        Update update = updateBean.getUpdateByID(this.getDownloadID());
        if (update == null) {
           throw new DMException("Could not found download package by update ID: " + this.getDownloadID());
        }
        int size = update.getSize();
        String vendor = update.getFromImage().getModel().getManufacturer().getExternalId();
        String content = "";
        //content += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        content += "<media xmlns=\"http://www.openmobilealliance.org/xmlns/dd\">";
        //content += "<type>application/octet-stream</type>";
        content += "<name>Firmware Update</name>";
        String mimeType = FirmwareMimeTypeHelper.getMimeType(update.getFromImage().getModel().getManufacturer().getExternalId(),
                                                             update.getFromImage().getModel().getManufacturerModelId());
        content += "<type>" + mimeType + "</type>";
        String fileSuffix = FirmwareMimeTypeHelper.getFileSuffix(update.getFromImage().getModel().getManufacturer().getExternalId(),
                                                                 update.getFromImage().getModel().getManufacturerModelId());
        content += "<objectURI>" + this.getDownloadURI() + "/" + this.getDownloadID() + fileSuffix + "</objectURI>";
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
