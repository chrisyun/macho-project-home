/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/writer/DMSessionLogWriterChain.java,v 1.1 2008/08/04 02:33:23 zhao Exp $
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
package com.npower.dm.tracking.writer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.tracking.DMSessionLogItem;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/08/04 02:33:23 $
 */
public class DMSessionLogWriterChain implements DMSessionLogWriter {
  
  private static Log log = LogFactory.getLog(DMSessionLogWriterChain.class);
  
  /**
   * List of chain elements.
   */
  private List<DMSessionLogWriter> chain = new ArrayList<DMSessionLogWriter>();

  /**
   * Constructor
   */
  public DMSessionLogWriterChain() {
    super();
  }

  /**
   * @return
   */
  public List<DMSessionLogWriter> getChain() {
    return chain;
  }

  /**
   * @param chain
   */
  public void setChain(List<DMSessionLogWriter> chain) {
    this.chain = chain;
  }
  
  /**
   * Add and append a writer into chain.
   * @param writer
   * @return
   */
  public List<DMSessionLogWriter> add(DMSessionLogWriter writer) {
    this.chain.add(writer);
    return this.chain;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.tracking.writer.DMSessionLogWriter#write(com.npower.dm.tracking.DMSessionLogItem)
   */
  public void write(DMSessionLogItem item) throws IOException {
    if (this.chain == null) {
       return;
    }
    for (DMSessionLogWriter writer: this.chain) {
        try {
          writer.write(item);
        } catch (Exception e) {
          log.error("Error to write log item.", e);
        }
    }
  }

}
