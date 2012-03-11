/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/analysis/Analyser.java,v 1.2 2009/01/21 07:53:10 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2009/01/21 07:53:10 $
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

import java.util.List;

import com.npower.dm.core.DMException;
import com.npower.dm.core.ProfileAssignment;

/**
 * 设备分析器, 为设备提供分析报告.
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2009/01/21 07:53:10 $
 */
public interface Analyser {
  
  /**
   * 分析设备信息, 提供分析报告
   * @param report
   *        分析报告句柄, 将分析情况存放到该报告中.
   * @param deviceId
   *        被分析的设备IMEI
   * @return
   *        分析后符合设备端存在的配置信息
   * @throws DMException
   */
  public abstract List<ProfileAssignment> analyse(Report report, String deviceExternalId) throws DMException;

}
