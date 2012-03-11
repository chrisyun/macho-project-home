/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/analysis/Report.java,v 1.1 2008/12/10 05:24:20 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/12/10 05:24:20 $
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
package com.npower.dm.analysis;

import java.util.Set;

/**
 * 分析器分析设备后, 提供的诊断报告
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/12/10 05:24:20 $
 */
public interface Report {
  
  /**
   * 分析报告对应的目标设备IMEI
   * @return
   */
  public abstract String getTargetDeviceExternalId();
  
  /**
   * 添加一个问题报告.
   * @param promblem
   */
  public abstract void addProblem(Problem promblem);
  
  /**
   * 返回所有的问题.
   * @return
   */
  public abstract Set<Problem> getProblem();

}
