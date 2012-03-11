/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/Image.java,v 1.3 2007/01/17 04:31:00 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2007/01/17 04:31:00 $
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
package com.npower.dm.core;

import java.util.Set;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2007/01/17 04:31:00 $
 */
public interface Image {

  /**
   * ImageEntity Status code:
   */
  public static final long STATUS_NEEDS_CREATION = 1000L;

  public static final long STATUS_IN_PROGRESS = 1001L;

  public static final long STATUS_CREATED = 1002L;

  public static final long STATUS_FAILED_CREATION = 1003L;

  public static final long STATUS_NEEDS_TESTING = 1004L;

  public static final long STATUS_FAILED_TESTING = 1005L;

  public static final long STATUS_TESTED = 1006L;

  public static final long STATUS_RELEASED = 1007L;

  public static final long STATUS_BROKEN = 1008L;

  public static final long STATUS_PLACEHOLDER = 1009L;

  public static final long STATUS_UNRECOGNIZED = 1010L;

  /**
   * Return ID
   * @return
   */
  public abstract long getID();

  /**
   * Return status of his image.
   * @return
   */
  public abstract ImageUpdateStatus getStatus();

  /**
   * Set a status for this image.
   * @param status
   */
  public abstract void setStatus(ImageUpdateStatus status);

  /**
   * Return the model of image.
   * @return
   */
  public abstract Model getModel();

  /**
   * Set a model for this Image.
   * @param model
   */
  public abstract void setModel(Model model);

  /**
   * Return the versionID of this Image.
   * @return
   */
  public abstract String getVersionId();

  /**
   * Set an versionID for this image.
   * @param imageExternalId
   */
  public abstract void setVersionId(String imageExternalId);

  /**
   * If true, this image will be applicable to all of carriers.
   * @return
   */
  public abstract boolean getApplicableToAllCarriers();

  /**
   * Set true , if this image will be applicabel to all of carriers.
   * 
   * @param applicableToAllCarriers
   */
  public abstract void setApplicableToAllCarriers(boolean applicableToAllCarriers);

  /**
   * Return the description.
   * @return
   */
  public abstract String getDescription();

  /**
   * Set a description.
   * @param description
   */
  public abstract void setDescription(String description);

  /**
   * Return a set of devices which device's firmware version is equals with this Image's versionID.
   * 
   * @return Return a <code>Set</code> of {@com.npower.dm.core.Device} objects.
   */
  public abstract Set<Device> getDevices();

  /**
   * Return a set of Updates which reference this Images as it's ToImage.
   * @return Return a <code>Set</code> of {@com.npower.dm.core.Update} objects.
   */
  public abstract Set<Update> getUpdatesForToImageId();

  /**
   * Return a set of Updates which reference this Images as it's FromImage.
   * @return Return a <code>Set</code> of {@com.npower.dm.core.Update} objects.
   */
  public abstract Set<Update> getUpdatesForFromImageId();

  //public abstract Set getImageCarrierses();

  //public abstract Set getProvReqs();

  //public abstract Set getJobStatesForInstallingImage();

  //public abstract Set getPrElements();

  //public abstract Set getJobStatesForToImageId();

  //public abstract Set getDeviceProvReqsForOldCurrentImageId();

  //public abstract Set getJobStatesForOldCurrentImageId();

  //public abstract Set getDeviceProvReqsForToImageId();

  //public abstract Set getDeviceProvReqsForInstallingImage();

  /**
   * 
   * @return
   */
  public abstract long getChangeVersion();

}