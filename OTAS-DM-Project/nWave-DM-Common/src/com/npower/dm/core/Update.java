/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/Update.java,v 1.7 2008/11/24 10:46:56 zhao Exp $
 * $Revision: 1.7 $
 * $Date: 2008/11/24 10:46:56 $
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Set;

/**
 * Represent a Update
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/11/24 10:46:56 $
 */
public interface Update {

  /**
   * Return ID.
   * @return
   */
  public abstract long getID();

  /**
   * Return From image
   * @return
   */
  public abstract Image getFromImage();

  /**
   * Set an image for update as fromImage.
   * @param fromImage
   */
  public abstract void setFromImage(Image fromImage);

  /**
   * Return toImage.
   * @return
   */
  public abstract Image getToImage();

  /**
   * Set an image for update as ToImage.
   * @param toImage
   */
  public abstract void setToImage(Image toImage);
  
  /**
   * Return the status of this Update.
   * @return
   */
  public abstract ImageUpdateStatus getStatus();

  /**
   * Set a status for this update.
   * @param status
   */
  public abstract void setStatus(ImageUpdateStatus status);

  /**
   * Return InputStream for fetch the image data.
   * 
   * @return
   * @throws DMException
   */
  public InputStream getInputStream() throws DMException;

  /**
   * Return the bytes of the update.
   * @return
   * @throws Exception
   */
  public byte[] getBytes() throws DMException;
  
  /**
   * @throws DMException
   * @throws IOException 
   */
  public void setBinary(InputStream in) throws DMException, IOException;
  
  /**
   * Return the description.
   * @return
   */
  public abstract String getDescription();

  /**
   * Set an description.
   * @param description
   */
  public abstract void setDescription(String description);

  /**
   * Return a set of devices which firmware version number equals with this update's fromImage.
   * 
   * @return Return a <code>Set</code> of {@com.npower.dm.core.device} objects.
   */
  public abstract Set<Device> getDevices();
  

  //public abstract long getWorkflowEntryId();

  //public abstract void setWorkflowEntryId(long workflowEntryId);

  //public abstract long getWorkflowId();

  //public abstract void setWorkflowId(long workflowId);

  //public abstract String getWorkflowSteps();

  //public abstract void setWorkflowSteps(String workflowSteps);

  //public abstract Set getUpdateCarrierses();

  //public abstract Set getPathElements();

  //public abstract Set getJobUpdatePaths();
  
  /**
   * Return the size of update in bytes.
   * @return
   */
  public int getSize() throws Exception;

  /**
   * 
   * @return
   */
  public abstract long getChangeVersion();

  /**
   * 
   * @return
   */
  public abstract String getLastUpdatedBy();

  /**
   * 
   * @param lastUpdatedBy
   */
  public abstract void setLastUpdatedBy(String lastUpdatedBy);

  /**
   * 
   * @return
   */
  public abstract Date getLastUpdatedTime();

  /**
   * @return the createdTime
   */
  public Date getCreatedTime();
}