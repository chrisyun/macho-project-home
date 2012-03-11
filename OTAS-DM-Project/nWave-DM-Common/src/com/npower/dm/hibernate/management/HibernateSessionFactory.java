/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/HibernateSessionFactory.java,v 1.13 2008/09/02 03:23:13 zhao Exp $
 * $Revision: 1.13 $
 * $Date: 2008/09/02 03:23:13 $
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
package com.npower.dm.hibernate.management;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import com.npower.dm.hibernate.HibernateSessionAware;
import com.npower.dm.util.DMUtil;

/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see
 * {@link http://hibernate.org/42.html}.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.13 $ $Date: 2008/09/02 03:23:13 $
 */
class HibernateSessionFactory {

  private static Log log = LogFactory.getLog(HibernateSessionFactory.class);
  /**
   * Location of hibernate.cfg.xml file. NOTICE: Location should be on the
   * classpath as Hibernate uses #resourceAsStream style lookup for its
   * configuration file. That is place the config file in a Java package - the
   * default location is the default Java package.<br>
   * <br>
   * Examples: <br>
   * <code>CONFIG_FILE_LOCATION = "/hibernate.conf.xml". 
   * CONFIG_FILE_LOCATION = "/com/foo/bar/myhiberstuff.conf.xml".</code>
   */
  private static String CONFIG_FILE_LOCATION = "/com/npower/dm/hibernate/hibernate.cfg.xml";
  
  /** Holds a single instance of Session */
  //private static final ThreadLocal threadLocal = new ThreadLocal();

  /** The single instance of hibernate configuration */
  private static final Configuration config = new Configuration();

  /** The single instance of hibernate SessionFactory */
  private static org.hibernate.SessionFactory sessionFactory;
  
  private static Boolean initialized = new Boolean(false);
  
  private Properties properties;
  
  /** Initlize the Hibernate Configuration */
  static {
    /**
     * For somecase, p6spy could not registe the Oracle Driver.
    
    try {
        if (log.isDebugEnabled()) {
           log.debug("Registe the Oracle Driver ...");
        }
        Class.forName("oracle.jdbc.OracleDriver");
    } catch (ClassNotFoundException e) {
      log.error("Could not registe the Oracle Driver.", e);
    }
    */
  }

  /**
   * Default constructor.
   */
  private HibernateSessionFactory() {
    super();
  }

  /**
   * @param properties
   */
  public HibernateSessionFactory(Properties properties) {
    if (sessionFactory != null) {
       //throw new RuntimeException("The Hibernate Session Factory had been initialized, could not change hibernate properties.");
       log.warn("The Hibernate Session Factory had been initialized, could not change hibernate properties.");
    }
    this.properties = properties;
  }

  /**
   * @return the properties
   */
  private Properties getProperties() {
    return properties;
  }

  private void initSessionFactory() throws HibernateException {
    synchronized (initialized) {
      if (initialized.booleanValue() == false) {
         try {
             config.setProperties(this.loadProperties());
             
             config.configure(CONFIG_FILE_LOCATION);
             sessionFactory = config.buildSessionFactory();
             initialized = new Boolean(true); 
         } catch (IOException e) {
           System.err.println("%%%% Error Creating SessionFactory %%%%");
           DMUtil.print(Thread.currentThread().getContextClassLoader());
           e.printStackTrace();
           log.fatal("%%%% Error Creating SessionFactory %%%%", e);
           throw new HibernateException("Error Creating SessionFactory", e);
         } catch (HibernateException e) {
           System.err.println("%%%% Error Creating SessionFactory %%%%");
           DMUtil.print(Thread.currentThread().getContextClassLoader());
           e.printStackTrace();
           log.fatal("%%%% Error Creating SessionFactory %%%%", e);
           throw e;
        } catch (Exception e) {
          System.err.println("%%%% Error Creating SessionFactory %%%%");
          DMUtil.print(Thread.currentThread().getContextClassLoader());
          e.printStackTrace();
          log.fatal("%%%% Error Creating SessionFactory %%%%", e);
          throw new HibernateException("Error Creating SessionFactory", e);
        }
      }
    }
  }

  /**
   * @return
   * @throws IOException
   */
  private Properties loadProperties() throws Exception {
    log.info("Loading hibernate properties ... ");
    if (this.getProperties() != null) {
       return this.getProperties();
    } else {
      throw new NullPointerException("DM Properties is null, please pass properties.");
    }
    
  }
  
  // public methods --------------------------------------------------------------------
  /**
   * Create an instance of HibernateSessionFactory.
   * The initialize process will load hibernate properties by the following steps:
   * 1. Load from ${otas.dm.home}/config/otasdm/otasdm.properties"
   * 2. If not found, load hibernate.properties file from classpath
   * 3. If not found, will use System.getProperties()
   * @return
   */
  static HibernateSessionFactory newInstance() {
    return new HibernateSessionFactory();
  }

  /**
   * Create an instance of HibernateSessionFactory.
   * If the Hibernate factory had been initilized, this process will throw RuntimeException.
   * @param properties
   *        Hibernate properties.
   * @return
   */
  static HibernateSessionFactory newInstance(Properties properties) {
    return new HibernateSessionFactory(properties);
  }

  /**
   * Returns the Session instance. 
   * 
   * @param interceptor
   * @return
   * @throws HibernateException
   */
  Session currentSession(Interceptor interceptor) throws HibernateException {
     synchronized (HibernateSessionFactory.class) {
         if (sessionFactory == null) {
            initSessionFactory();
         }
     }
     if (sessionFactory != null) {
        if (interceptor != null) {
        // Register a interceptor.
        Session session = sessionFactory.openSession(interceptor);
        if (interceptor instanceof HibernateSessionAware) {
           ((HibernateSessionAware)interceptor).setHibernateSession(session);
        }
        return session;
        } else {
          Session session = sessionFactory.openSession();
          return session;
        }
     } else {
       return null;
     }
  }

  /**
   * @return
   * @throws HibernateException
   */
  Session currentSession() throws HibernateException {
    return this.currentSession(null);
  }
}
