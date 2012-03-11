/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/SoftwareVendor.java,v 1.2 2008/01/28 08:10:14 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/01/28 08:10:14 $
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
 * @version $Revision: 1.2 $ $Date: 2008/01/28 08:10:14 $
 */
public interface SoftwareVendor {

  /**
   * @return
   */
  public abstract long getId();

  /**
   * @param vendorId
   */
  public abstract void setId(long vendorId);

  /**
   * @return
   */
  public abstract String getName();

  /**
   * @param name
   */
  public abstract void setName(String name);

  /**
   * @return
   */
  public abstract String getDescription();

  /**
   * @param description
   */
  public abstract void setDescription(String description);

  /**
   * @return
   */
  public abstract String getWebSite();

  /**
   * @param webSite
   */
  public abstract void setWebSite(String webSite);

  /**
   * @return
   */
  public abstract Set<Software> getSoftwares();

  /**
   * @param softwares
   */
  public abstract void setSoftwares(Set<Software> softwares);

}