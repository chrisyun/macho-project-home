/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/AccessLogger.java,v 1.2 2008/06/18 04:52:03 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/06/18 04:52:03 $
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

import javax.servlet.http.HttpServletRequest;

import com.npower.dm.tracking.writer.AccessLogWriter;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/06/18 04:52:03 $
 */
public interface AccessLogger {
  
  /**
   * @param writer
   */
  public void setWriter(AccessLogWriter writer);
  
  /**
   * @return
   */
  public AccessLogWriter getWriter();
  
  /**
   * Log access information into tracking system.
   * @param req
   */
  public void log(HttpServletRequest req) throws IOException;
}
