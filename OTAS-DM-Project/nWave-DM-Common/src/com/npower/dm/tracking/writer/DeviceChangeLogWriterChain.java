/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/writer/DeviceChangeLogWriterChain.java,v 1.1 2008/08/04 09:42:42 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/08/04 09:42:42 $
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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.tracking.DeviceChangeLogItem;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/08/04 09:42:42 $
 */
public class DeviceChangeLogWriterChain implements DeviceChangeLogWriter {

  private static Log log = LogFactory.getLog(DeviceChangeLogWriterChain.class);
  
  /**
   * List of chain elements.
   */
  private List<DeviceChangeLogWriter> chain = new ArrayList<DeviceChangeLogWriter>();

  /**
   * 
   */
  public DeviceChangeLogWriterChain() {
    super();
  }

  /**
   * @return
   */
  public List<DeviceChangeLogWriter> getChain() {
    return chain;
  }

  /**
   * @param chain
   */
  public void setChain(List<DeviceChangeLogWriter> chain) {
    this.chain = chain;
  }
  
  /**
   * Add and append a writer into chain.
   * @param writer
   * @return
   */
  public List<DeviceChangeLogWriter> add(DeviceChangeLogWriter writer) {
    this.chain.add(writer);
    return this.chain;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.writer.DeviceChangeLogWriter#write(com.npower.dm.tracking.DeviceChangeLogItem)
   */
  public void write(DeviceChangeLogItem item) throws IOException {
    if (this.chain == null) {
       return;
    }
    for (DeviceChangeLogWriter writer: this.chain) {
       try {
         writer.write(item);
       } catch (Exception e) {
         log.error("Error to write device change log item.", e);
       }
    }
  }

}
