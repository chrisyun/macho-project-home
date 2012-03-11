/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dl/DownloadMimeType.java,v 1.1 2007/03/12 11:32:55 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/03/12 11:32:55 $
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

import java.io.Serializable;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/03/12 11:32:55 $
 */
public class DownloadMimeType implements Serializable {
  
  private String manufacturer = null;
  private String model = null;
  private String mimeType = null;
  private String suffix = null;

  /**
   * 
   */
  public DownloadMimeType() {
    super();
  }

  /**
   * @return the manufacturer
   */
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * @param manufacturer the manufacturer to set
   */
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * @return the mimeType
   */
  public String getMimeType() {
    return mimeType;
  }

  /**
   * @param mimeType the mimeType to set
   */
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  /**
   * @return the model
   */
  public String getModel() {
    return model;
  }

  /**
   * @param model the model to set
   */
  public void setModel(String model) {
    this.model = model;
  }

  /**
   * @return the suffix
   */
  public String getSuffix() {
    return suffix;
  }

  /**
   * @param suffix the suffix to set
   */
  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

}
