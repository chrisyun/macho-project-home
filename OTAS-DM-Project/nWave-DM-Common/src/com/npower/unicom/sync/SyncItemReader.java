/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/SyncItemReader.java,v 1.1 2008/11/18 04:25:28 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/11/18 04:25:28 $
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
package com.npower.unicom.sync;

import java.io.IOException;

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/11/18 04:25:28 $
 */
public interface SyncItemReader {
  
  /**
   * 打开Writer, 准备读入数据
   * @throws IOException
   * @throws DMException 
   */
  public void open() throws IOException, DMException;
  
  /**
   * 关闭Reader
   * @throws IOException
   */
  public void close() throws IOException, DMException;
  
  /**
   * 读入一个Item, 返回为null时, 表示已读入到数据结尾.
   * @param item
   * @throws IOException
   */
  public SyncItem read() throws IOException, ParseException, DMException;

}
