/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/SoftwareCategoryItem.java,v 1.1 2008/11/17 07:06:35 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/11/17 07:06:35 $
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
 * @version $Revision: 1.1 $ $Date: 2008/11/17 07:06:35 $
 */

public class SoftwareCategoryItem {

  private String name = null;
  private String description = null;
  private List<SoftwareCategoryItem> softwareCategoryItem = new ArrayList<SoftwareCategoryItem>();
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public List<SoftwareCategoryItem> getSoftwareCategory() {
    return softwareCategoryItem;
  }
  public void addSoftwareCategory(SoftwareCategoryItem softwareCategoryItem) {
    this.softwareCategoryItem.add(softwareCategoryItem);
  }
  
}
