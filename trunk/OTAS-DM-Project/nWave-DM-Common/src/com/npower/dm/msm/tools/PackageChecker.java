/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/tools/PackageChecker.java,v 1.2 2008/05/08 08:57:26 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/05/08 08:57:26 $
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
package com.npower.dm.msm.tools;

import com.npower.dm.core.DMException;


/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/05/08 08:57:26 $
 */
public interface PackageChecker {
  
  /**
   * Make a connection to download url, and get PackageMetaInfo
   * @param url
   * @return
   * @throws DMException
   */
  public PackageMetaInfo getPackageMetaInfo(String url) throws DMException;

}
