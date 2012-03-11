/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/SoftwarePackage4LocalItem.java,v 1.1 2008/12/16 04:19:41 chenlei Exp $
 * $Revision: 1.1 $
 * $Date: 2008/12/16 04:19:41 $
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

/**
 * @author chenlei
 * @version $Revision: 1.1 $ $Date: 2008/12/16 04:19:41 $
 */

public class SoftwarePackage4LocalItem {
  private String mimeType;
  private String file;
  public String getMimeType() {
    return mimeType;
  }
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }
  public String getFile() {
    return file;
  }
  public void setFile(String file) {
    this.file = file;
  }
}
