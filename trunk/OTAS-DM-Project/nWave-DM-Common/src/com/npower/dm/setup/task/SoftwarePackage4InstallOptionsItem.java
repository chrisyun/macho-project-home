/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/SoftwarePackage4InstallOptionsItem.java,v 1.1 2008/12/16 04:19:42 chenlei Exp $
 * $Revision: 1.1 $
 * $Date: 2008/12/16 04:19:42 $
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenlei
 * @version $Revision: 1.1 $ $Date: 2008/12/16 04:19:42 $
 */

public class SoftwarePackage4InstallOptionsItem {
  private List<SoftwarePackage4StdOptItem> stdOpt= new ArrayList<SoftwarePackage4StdOptItem>();
  private List<SoftwarePackage4StdSymOptItem> stdSymOpt= new ArrayList<SoftwarePackage4StdSymOptItem>();
  public List<SoftwarePackage4StdOptItem> getStdOpt() {
    return stdOpt;
  }
  public void addStdOpt(SoftwarePackage4StdOptItem stdOpt) {
    this.stdOpt.add(stdOpt);
  }
  public List<SoftwarePackage4StdSymOptItem> getStdSymOpt() {
    return stdSymOpt;
  }
  public void addStdSymOpt(SoftwarePackage4StdSymOptItem stdSymOpt) {
    this.stdSymOpt.add(stdSymOpt);
  }
  
}
