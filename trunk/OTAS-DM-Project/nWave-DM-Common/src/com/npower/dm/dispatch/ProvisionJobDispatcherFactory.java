/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/dispatch/ProvisionJobDispatcherFactory.java,v 1.3 2008/08/01 06:45:39 zhao Exp $
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
package com.npower.dm.dispatch;

import java.util.Properties;

import com.npower.dm.dispatch.chain.DispatcherProcessorFactory;
import com.npower.dm.dispatch.chain.ProvisionJobDispatcherImpl;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/08/01 06:45:39 $
 */
public class ProvisionJobDispatcherFactory {
  
  private final static String PROPERTY_CLASS_NAME = "dm.daemon.job.dispatcher.class";
  
  private Properties properties = System.getProperties();

  /**
   * 
   */
  protected ProvisionJobDispatcherFactory() {
    super();
  }
  
  public ProvisionJobDispatcherFactory(Properties properties) {
    super();
    this.properties = properties;
  }

  /**
   * @return the properties
   */
  public Properties getProperties() {
    return properties;
  }

  /**
   * @param properties the properties to set
   */
  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  /**
   * @param properties
   * @return
   */
  public static ProvisionJobDispatcherFactory newInstance(Properties properties) {
    return new ProvisionJobDispatcherFactory(properties);
  }
  
  public ProvisionJobDispatcher getProvisionJobDispatcher() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    String className = this.getProperties().getProperty(PROPERTY_CLASS_NAME);
    Class<?> clazz = Class.forName(className);
    Object instance = clazz.newInstance();
    ProvisionJobDispatcher dispatcher = (ProvisionJobDispatcher)instance;
    
    if (dispatcher instanceof ProvisionJobDispatcherImpl) {
       // Make up dispatcher processor for dispatcher
       DispatcherProcessorFactory factory = DispatcherProcessorFactory.newInstance();
       ((ProvisionJobDispatcherImpl)dispatcher).setDmProcessor(factory.getDefaultDmProcessor());
       ((ProvisionJobDispatcherImpl)dispatcher).setCpProcessor(factory.getDefaultCpProcessor());
    }
    return dispatcher;
  }

}
