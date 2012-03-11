/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractImage4Carrier.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2006/04/25 16:31:09 $
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
package com.npower.dm.hibernate.entity;

import com.npower.dm.core.Carrier;
import com.npower.dm.core.Image;
import com.npower.dm.core.ImageUpdateStatus;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractImage4Carrier implements java.io.Serializable {

  // Fields

  private long imageCarriersId;

  private Image nwDmImage;

  private ImageUpdateStatus nwDmImageUpdateStatus;

  private Carrier nwDmCarrier;

  private long changeVersion;

  // Constructors

  /** default constructor */
  public AbstractImage4Carrier() {
  }

  /** minimal constructor */
  public AbstractImage4Carrier(ImageUpdateStatus nwDmImageUpdateStatus, Carrier nwDmCarrier, long changeVersion) {
    this.nwDmImageUpdateStatus = nwDmImageUpdateStatus;
    this.nwDmCarrier = nwDmCarrier;
    this.changeVersion = changeVersion;
  }

  /** full constructor */
  public AbstractImage4Carrier(Image nwDmImage, ImageUpdateStatus nwDmImageUpdateStatus, Carrier nwDmCarrier,
      long changeVersion) {
    this.nwDmImage = nwDmImage;
    this.nwDmImageUpdateStatus = nwDmImageUpdateStatus;
    this.nwDmCarrier = nwDmCarrier;
    this.changeVersion = changeVersion;
  }

  // Property accessors

  public long getImageCarriersId() {
    return this.imageCarriersId;
  }

  public void setImageCarriersId(long imageCarriersId) {
    this.imageCarriersId = imageCarriersId;
  }

  public Image getNwDmImage() {
    return this.nwDmImage;
  }

  public void setNwDmImage(Image nwDmImage) {
    this.nwDmImage = nwDmImage;
  }

  public ImageUpdateStatus getNwDmImageUpdateStatus() {
    return this.nwDmImageUpdateStatus;
  }

  public void setNwDmImageUpdateStatus(ImageUpdateStatus nwDmImageUpdateStatus) {
    this.nwDmImageUpdateStatus = nwDmImageUpdateStatus;
  }

  public Carrier getNwDmCarrier() {
    return this.nwDmCarrier;
  }

  public void setNwDmCarrier(Carrier nwDmCarrier) {
    this.nwDmCarrier = nwDmCarrier;
  }

  public long getChangeVersion() {
    return this.changeVersion;
  }

  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

}