/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dl/DownloadURLCaculatorImpl.java,v 1.2 2007/03/23 08:02:06 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/03/23 08:02:06 $
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
package com.npower.dl;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/03/23 08:02:06 $
 */
public class DownloadURLCaculatorImpl implements DownloadURLCaculator {

  private String downloadServerURL = null;

  /**
   * 
   */
  public DownloadURLCaculatorImpl() {
    super();
  }

  /**
   * @return the downloadServerURL
   */
  public String getDownloadServerURL() {
    return downloadServerURL;
  }

  /**
   * @param downloadServerURL the downloadServerURL to set
   */
  public void setDownloadServerURL(String downloadServerURL) {
    this.downloadServerURL = downloadServerURL;
  }

  /* (non-Javadoc)
   * @see com.npower.dl.DownloadURLCaculator#getDownloadDescriptorURL(java.lang.String)
   */
  public String getDownloadDescriptorURL(String updateID) throws DMException {
    String serverURL = this.getDownloadServerURL();
    if (StringUtils.isEmpty(serverURL)) {
       throw new DMException("Missing download server URL, Could not caculate Download Descriptor for ID: " + updateID);
    }
    if (StringUtils.isEmpty(updateID)) {
       throw new DMException("Update ID is null, could not caculate download descriptor.");
    }
    
    serverURL = serverURL.trim();
    updateID = updateID.trim();
    String url = serverURL.replaceAll("\\$\\{update.id\\}", updateID);
    return url;
  }

}
