/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/DMSessionLoggerImpl.java,v 1.4 2008/08/04 11:01:33 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/08/04 11:01:33 $
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

import javax.servlet.http.HttpServletRequest;

import com.npower.dm.tracking.writer.DMSessionLogWriter;
import com.npower.dm.tracking.writer.SimpleDMSessoinLogWriter;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.4 $ $Date: 2008/08/04 11:01:33 $
 */
public class DMSessionLoggerImpl implements DMSessionLogger {
  
  private DMSessionLogWriter writer = new SimpleDMSessoinLogWriter();

  /**
   * 
   */
  public DMSessionLoggerImpl() {
    super();
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.DMSessionLogger#getWriter()
   */
  public DMSessionLogWriter getWriter() {
    return this.writer;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.DMSessionLogger#setWriter(com.npower.dm.tracking.writer.DMSessionLogWriter)
   */
  public void setWriter(DMSessionLogWriter writer) {
    this.writer = writer;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.DMSessionLogger#log(javax.servlet.http.HttpServletRequest, int, int)
   */
  public void log(String dmSessionId, HttpServletRequest request, int sizeOfRequestData, int sizeOfResponseData) throws IOException {
    DMSessionLogItem item = new DMSessionLogItem();
    
    item.setDmSessionId(dmSessionId);

    item.setClientIp(request.getRemoteAddr());
    String userAgent = request.getHeader("user-agent");
    item.setUserAgent(userAgent);
    item.setSizeRequestData(sizeOfRequestData);
    item.setSizeResponseData(sizeOfResponseData);
    item.setTimeStamp(new Date());
    
    this.writer.write(item);
  }

}
