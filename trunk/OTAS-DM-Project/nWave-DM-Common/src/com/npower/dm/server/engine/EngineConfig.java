/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/server/engine/EngineConfig.java,v 1.1 2008/06/16 10:11:15 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/06/16 10:11:15 $
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
package com.npower.dm.server.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.npower.dm.core.DMException;
import com.npower.dm.util.Constances;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/06/16 10:11:15 $
 */
public class EngineConfig {

  private static Log   log                       = LogFactory.getLog(EngineConfig.class);
  
  /**
   * Location of otasdm.properties file. NOTICE: Location should be on the
   * classpath as Hibernate uses #resourceAsStream style lookup for its
   * configuration file. That is place the config file in a Java package - the
   * default location is the default Java package.<br>
   */
  private static String PROPERTIES_FILE_LOCATION = Constances.CONFIG_PATH_PREFIX + "/" + Constances.PROPERTIES_FILE_NAME;
  
  private static Properties properties = null;

  /**
   * 
   */
  private EngineConfig() {
    super();
  }

  public synchronized static Properties getProperties() throws DMException {
    if (properties != null) {
       return properties;
    }
    
    properties = new Properties(System.getProperties());
    
    try {
      InputStream propertiesIns = null;
      String home = System.getProperty(Constances.PROPERTY_HOME);
      log.info(Constances.PROPERTY_HOME + ": " + home);
      if (home != null) {
         File homeDir = new File(home);
         File propertiesFile = null;
         if (homeDir.exists()) {
            propertiesFile = new File(homeDir, PROPERTIES_FILE_LOCATION);
         }
         if (propertiesFile != null && propertiesFile.exists()) {
            propertiesIns = new FileInputStream(propertiesFile);
            log.info("Using DM properties file: " + propertiesFile.getAbsolutePath());
         }
      }
      if (propertiesIns != null) {
         properties.load(propertiesIns);
      }
      return properties;
    } catch (FileNotFoundException e) {
      throw new DMException("Error to load management bean properties file.", e);
    } catch (IOException e) {
      throw new DMException("Error to load management bean properties file.", e);
    }
  }

}
