/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/tracking/DeviceChangeLoggerFactory.java,v 1.2 2008/08/06 07:59:23 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/08/06 07:59:23 $
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

import com.npower.dm.tracking.writer.DaoDMDeviceChangeLogWriter;
import com.npower.dm.tracking.writer.DeviceChangeLogWriterChain;
import com.npower.dm.tracking.writer.SimpleDeviceChangeLogWriter;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/08/06 07:59:23 $
 */
public class DeviceChangeLoggerFactory {

  /**
   * 
   */
  private DeviceChangeLoggerFactory() {
    super();
  }
  
  /**
   * @return
   */
  public static DeviceChangeLoggerFactory newInstance() {
    return new DeviceChangeLoggerFactory();
  }
  
  /**
   * @return
   */
  public DeviceChangeLogger getLogger() {
    DeviceChangeLogger logger = new DeviceChangeLoggerImpl();
    DeviceChangeLogWriterChain writerChain = new DeviceChangeLogWriterChain();
    writerChain.add(new SimpleDeviceChangeLogWriter());
    writerChain.add(new DaoDMDeviceChangeLogWriter());
    logger.setWriter(writerChain);
    return logger;
  }

}
