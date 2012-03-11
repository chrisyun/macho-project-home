/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/writer/SimpleAccessLogWriter.java,v 1.1 2008/06/18 04:52:03 zhao Exp $
 * $Revision: 1.1 $
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
package com.npower.dm.tracking.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.npower.dm.tracking.AccessLogItem;
import com.npower.dm.tracking.NameValuePair;
import com.npower.dm.tracking.NameValuesPair;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/06/18 04:52:03 $
 */
public class SimpleAccessLogWriter implements AccessLogWriter {
  
  /**
   * 
   */
  private OutputStream output = System.out;

  /**
   * Default constructor
   */
  public SimpleAccessLogWriter() {
    super();
  }

  /**
   * Provider a OutputStream to output log information. Default is System.out.
   * @param output
   */
  public SimpleAccessLogWriter(OutputStream output) {
    super();
    this.output = output;
  }

  /**
   * OutputStream to output log information.
   * @return
   */
  public OutputStream getOutput() {
    return output;
  }

  /**
   * OutputStream to output log information.
   * @param output
   */
  public void setOutput(OutputStream output) {
    this.output = output;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.myportal.tracking.AccessLogWriter#write(com.npower.dm.myportal.tracking.AccessLogItem)
   */
  public void write(AccessLogItem item) throws IOException {
    StringBuffer buf = new StringBuffer();
    
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    buf.append('[');
    buf.append(dateFormat.format(item.getTimeStamp()));
    buf.append("]:");
    buf.append(item.getSessionID());
    buf.append(':');
    buf.append(item.getClientIP());
    buf.append(':');
    buf.append(item.getUrl());
    buf.append(':');
    buf.append(item.getQuery());
    buf.append(':');
    buf.append(item.getUserAgent());
    buf.append('\n');
    
    for (NameValuePair header: item.getHeaders()) {
        buf.append('[');
        buf.append(dateFormat.format(item.getTimeStamp()));
        buf.append("]:");
        buf.append(item.getSessionID());
        buf.append(":header:");
        buf.append(header.getName());
        buf.append(':');
        buf.append(header.getValue());
        buf.append('\n');
    }
    
    for (NameValuesPair param: item.getParameters()) {
        buf.append('[');
        buf.append(dateFormat.format(item.getTimeStamp()));
        buf.append("]:");
        buf.append(item.getSessionID());
        buf.append(":params:");
        buf.append(param.getName());
        buf.append(':');
        for (String v: param.getValues()) {
            buf.append(v);
            buf.append(',');
        }
        buf.append('\n');
    }
    
    this.getOutput().write(buf.toString().getBytes());
    this.getOutput().flush();
  
  }

}
