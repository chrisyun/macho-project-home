/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/dispatch/chain/DispatcherProcessorChain.java,v 1.3 2008/12/02 08:34:05 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/12/02 08:34:05 $
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/12/02 08:34:05 $
 */
public class DispatcherProcessorChain extends BaseDispatcherProcessor {
  
  private static Log log = LogFactory.getLog(DispatcherProcessorChain.class);

  private List<DispatcherProcessor> processors = new ArrayList<DispatcherProcessor>();

  /**
   * 
   */
  public DispatcherProcessorChain() {
    super();
  }

  /**
   * @return the processors
   */
  public List<DispatcherProcessor> getProcessors() {
    return processors;
  }

  /**
   * @param processors the processors to set
   */
  public void setProcessors(List<DispatcherProcessor> processors) {
    this.processors = processors;
  }
  
  public void addProcessor(DispatcherProcessor processor) {
    this.processors.add(processor);
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.chain.BaseDispatcherProcessor#isNeedToProcess(com.npower.dm.core.ProvisionJob, com.npower.dm.core.Device)
   */
  @Override
  public boolean isNeedToProcess(ProvisionJob job, Device device) throws DMException {
    if (device == null || job == null) {
       return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.dispatch.chain.BaseDispatcherProcessor#process(com.npower.dm.core.ProvisionJob, com.npower.dm.core.Device)
   */
  @Override
  public void process(ProvisionJob job, Device device) {
    for (DispatcherProcessor processor: this.getProcessors()) {
        // Set Sender for processor in chain.
        processor.setJobNotificationSender(this.getJobNotificationSender());
        processor.setManagementBeanFactory(this.getManagementBeanFactory());
        processor.setPlanner(this.getPlanner());
        try {
            if (processor.isNeedToProcess(job, device)) {
              try {
                  processor.process(job, device);
              } catch (BreakProcessorChainException e) {
                // Catch Break exception, and break processor chain
                break;
              }
            }
        } catch (Exception ex) {
          log.error("Failure to dispatch job", ex);
        }
    }
  }

}
