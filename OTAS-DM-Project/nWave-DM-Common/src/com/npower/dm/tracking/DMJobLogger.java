/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/DMJobLogger.java,v 1.1 2008/08/04 02:33:23 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/08/04 02:33:23 $
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
package com.npower.dm.tracking;

import java.io.IOException;
import java.util.Date;

import sync4j.framework.engine.dm.ManagementProcessor;

import com.npower.dm.tracking.writer.DMJobLogWriter;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/08/04 02:33:23 $
 */
public interface DMJobLogger {
  /**
   * @param writer
   */
  public void setWriter(DMJobLogWriter writer);
  
  /**
   * @return
   */
  public DMJobLogWriter getWriter();
  
  /**
   * @param request
   * @param sizeOfRequestData
   * @param sizeOfResponseData
   */
  public abstract void log(String dmSessionID, 
                           long jobID, 
                           String deviceExtID, 
                           ManagementProcessor processor, 
                           Date startTime,
                           Date endTime,
                           String sessionStatus) throws IOException;

}
