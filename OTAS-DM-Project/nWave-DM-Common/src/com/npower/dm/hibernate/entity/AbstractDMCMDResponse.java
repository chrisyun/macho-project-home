/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractDMCMDResponse.java,v 1.2 2006/04/25 16:31:09 zhao Exp $
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

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2006/04/25 16:31:09 $
 */
public abstract class AbstractDMCMDResponse implements java.io.Serializable {

  // Fields

  private long dmCmdResponseId;

  private DMPkgRespProc nwDmDmPkgRespProc;

  private String responseType;

  private long parentCommandResponseId;

  private long childIndex;

  private String urlRef;

  private long statuscode;

  private long responseIndex;

  private Set nwDmDmResultsMaps = new HashSet(0);

  // Constructors

  /** default constructor */
  public AbstractDMCMDResponse() {
  }

  /** full constructor */
  public AbstractDMCMDResponse(DMPkgRespProc nwDmDmPkgRespProc, String responseType, long parentCommandResponseId,
      long childIndex, String urlRef, long statuscode, long responseIndex, Set nwDmDmResultsMaps) {
    this.nwDmDmPkgRespProc = nwDmDmPkgRespProc;
    this.responseType = responseType;
    this.parentCommandResponseId = parentCommandResponseId;
    this.childIndex = childIndex;
    this.urlRef = urlRef;
    this.statuscode = statuscode;
    this.responseIndex = responseIndex;
    this.nwDmDmResultsMaps = nwDmDmResultsMaps;
  }

  // Property accessors

  public long getDmCmdResponseId() {
    return this.dmCmdResponseId;
  }

  public void setDmCmdResponseId(long dmCmdResponseId) {
    this.dmCmdResponseId = dmCmdResponseId;
  }

  public DMPkgRespProc getNwDmDmPkgRespProc() {
    return this.nwDmDmPkgRespProc;
  }

  public void setNwDmDmPkgRespProc(DMPkgRespProc nwDmDmPkgRespProc) {
    this.nwDmDmPkgRespProc = nwDmDmPkgRespProc;
  }

  public String getResponseType() {
    return this.responseType;
  }

  public void setResponseType(String responseType) {
    this.responseType = responseType;
  }

  public long getParentCommandResponseId() {
    return this.parentCommandResponseId;
  }

  public void setParentCommandResponseId(long parentCommandResponseId) {
    this.parentCommandResponseId = parentCommandResponseId;
  }

  public long getChildIndex() {
    return this.childIndex;
  }

  public void setChildIndex(long childIndex) {
    this.childIndex = childIndex;
  }

  public String getUrlRef() {
    return this.urlRef;
  }

  public void setUrlRef(String urlRef) {
    this.urlRef = urlRef;
  }

  public long getStatuscode() {
    return this.statuscode;
  }

  public void setStatuscode(long statuscode) {
    this.statuscode = statuscode;
  }

  public long getResponseIndex() {
    return this.responseIndex;
  }

  public void setResponseIndex(long responseIndex) {
    this.responseIndex = responseIndex;
  }

  public Set getNwDmDmResultsMaps() {
    return this.nwDmDmResultsMaps;
  }

  public void setNwDmDmResultsMaps(Set nwDmDmResultsMaps) {
    this.nwDmDmResultsMaps = nwDmDmResultsMaps;
  }

}