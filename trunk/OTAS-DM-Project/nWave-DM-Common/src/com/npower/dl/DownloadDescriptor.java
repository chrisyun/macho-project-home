/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dl/DownloadDescriptor.java,v 1.2 2006/11/29 13:12:50 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2006/11/29 13:12:50 $
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

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/11/29 13:12:50 $
 */
public interface DownloadDescriptor {
  
  public static final String OMA_DOWNLOAD_DESCRIPTOR_CONTENT_TYPE = "application/vnd.oma.dd+xml";
  
  //public static final String NOKIA_FIRMWARE_MIME_TYPE = "application/vnd.nokia.swupd.dp2";
  
  public String getContentType() throws DMException;
  
  public String getContent() throws DMException;
  
  public void setDownloadID(String downloadID);
  
  public String getDownloadID();

  /**
   * @return the downloadURL
   */
  public String getDownloadURI();

  /**
   * @param downloadURL the downloadURL to set
   */
  public void setDownloadURI(String downloadURL);
  /**
   * @return the installNotifyURI
   */
  public String getInstallNotifyURI();

  /**
   * @param installNotifyURI the installNotifyURI to set
   */
  public void setInstallNotifyURI(String installNotifyURI);
}
