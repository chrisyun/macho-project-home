/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/dispatch/chain/DispatcherProcessorFactory.java,v 1.3 2008/08/01 06:45:39 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/08/01 06:45:39 $
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
package com.npower.dm.dispatch.chain;

import com.npower.dm.dispatch.chain.cp.DiscardCpJobProcessor;
import com.npower.dm.dispatch.chain.cp.ResentCpJobProcessor;
import com.npower.dm.dispatch.chain.dm.BootstrapDispatcherProcessor;
import com.npower.dm.dispatch.chain.dm.DiscardDmJobProcessor;
import com.npower.dm.dispatch.chain.dm.NotificationDispatcherProcessor;


/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/08/01 06:45:39 $
 */
public class DispatcherProcessorFactory {

  /**
   * 
   */
  private DispatcherProcessorFactory() {
    super();
  }
  
  /**
   * @return
   */
  public static DispatcherProcessorFactory newInstance() {
    return new DispatcherProcessorFactory();
  }

  /**
   * @return
   */
  public DispatcherProcessor getDefaultDmProcessor() {
    DispatcherProcessorChain processor = new DispatcherProcessorChain();
    processor.addProcessor(new DiscardDmJobProcessor());
    processor.addProcessor(new BootstrapDispatcherProcessor());
    processor.addProcessor(new NotificationDispatcherProcessor());
    return processor;
  }

  /**
   * @return
   */
  public DispatcherProcessor getDefaultCpProcessor() {
    DispatcherProcessorChain processor = new DispatcherProcessorChain();
    processor.addProcessor(new DiscardCpJobProcessor());
    processor.addProcessor(new ResentCpJobProcessor());
    return processor;
  }
}
