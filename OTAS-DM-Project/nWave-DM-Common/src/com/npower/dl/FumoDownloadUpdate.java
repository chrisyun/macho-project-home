/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dl/FumoDownloadUpdate.java,v 1.4 2008/06/16 10:11:15 zhao Exp $
  * $Revision: 1.4 $
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
 * @version $Revision: 1.4 $ $Date: 2008/06/16 10:11:15 $
 */
public class FumoDownloadUpdate {

  private String downloadID = null;
  
  private String contentType = null;
  
  /**
   * Default constructor
   */
  public FumoDownloadUpdate() {
    super();
  }

  /**
   * @return the downloadID
   */
  public String getDownloadID() {
    return downloadID;
  }

  /**
   * @param downloadID the downloadID to set
   */
  public void setDownloadID(String downloadID) {
    this.downloadID = downloadID;
  }
  
  public byte[] getContent() throws DMException {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        UpdateImageBean updateBean = factory.createUpdateImageBean();
        Update update = updateBean.getUpdateByID(this.getDownloadID());
        this.contentType = FirmwareMimeTypeHelper.getMimeType(update.getFromImage().getModel().getManufacturer().getExternalId(),
                                                              update.getFromImage().getModel().getManufacturerModelId());

        if (update != null) {
           return update.getBytes();
        } else {
          return null;
        }
        
    } catch (Exception ex) {
      throw new DMException(ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * @return the contentType
   */
  public String getContentType() {
    return contentType;
  }

  /**
   * @param contentType the contentType to set
   */
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

}
