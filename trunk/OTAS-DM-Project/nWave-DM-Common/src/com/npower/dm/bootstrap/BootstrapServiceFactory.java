/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/bootstrap/BootstrapServiceFactory.java,v 1.2 2008/02/11 10:52:45 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2008/02/11 10:52:45 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.bootstrap;

import java.util.Properties;

import com.npower.dm.core.DMException;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/02/11 10:52:45 $
 */
public class BootstrapServiceFactory {
  
  private Properties properties = new Properties();

  /**
   * Private constructor.
   */
  private BootstrapServiceFactory() {
    super();
  }
  
  /**
   * Instance a BootstrapServiceFactory class.
   * @param props
   * @return
   * @throws DMException
   */
  public static BootstrapServiceFactory newInstance(Properties props) throws DMException {
    BootstrapServiceFactory factory = new BootstrapServiceFactory();
    factory.setProperties(props);
    return factory;
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
   * @return
   * @throws DMException
   */
  public BootstrapService getBootstrapService() throws DMException {
    //BootstrapService service = new BootstrapServiceSettingsImpl();
    BootstrapService service = new BootstrapServiceImpl();
    if (service instanceof BaseBootstrapService) {
       ((BaseBootstrapService)service).setProfileProperties(this.getProperties()); 
    } 
    return service;
  }

}
