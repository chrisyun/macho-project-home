/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/server/engine/DMManagementEngine.java,v 1.17 2008/06/16 10:11:15 zhao Exp $
 * $Revision: 1.17 $
 * $Date: 2008/06/16 10:11:15 $
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
package com.npower.dm.server.engine;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sync4j.framework.config.Configuration;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.security.Officer;
import sync4j.framework.server.Sync4jDevice;
import sync4j.framework.server.store.NotFoundException;
import sync4j.server.engine.Sync4jEngine;

import com.npower.dm.core.Device;
import com.npower.dm.core.ProvisionJob;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.management.ProvisionJobBean;

/**
 * This class implements the Management Engine component.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.17 $ $Date: 2008/06/16 10:11:15 $
 */
public class DMManagementEngine extends Sync4jEngine {

  private static Log          log                         = LogFactory.getLog(DMManagementEngine.class);

  // Constants
  // *****************************************************************************

  /**
   * HTTP: 1 WSP: 2 OBEX: 3
   */
  @SuppressWarnings("unused")
  private static final int    DEFAULT_ADDRESS_TYPE        = 1;

  @SuppressWarnings("unused")
  private static final int    DEFAULT_PORT_NUMBER         = 80;


  // Spaceid used in getNextNotificationSessionIds
  @SuppressWarnings("unused")
  private static final String NS_MSSID                    = "mssid";

  @SuppressWarnings("unused")
  private static final String OPERATION_AFTER_BOOTSTRAP   = "GetDeviceDetail";

  // Private Data
  // ***********************************************************************

  private Configuration       config                      = null;

  // private DBUserManager dbUserManager = null;
  @SuppressWarnings("unused")
  private Officer             officer                     = null;

  
  /**
   * If true, will always return JobQueueProcessor.
   */
  private boolean alwaysQueueProcessor = true;


  /**
   * Disable default constructor, open only for TestCases.
   */
  DMManagementEngine() {
    super();
  }

  /**
   * Creates a new instance of ManagementEngine given a Configuration object.
   * 
   * @param configuration
   */
  public DMManagementEngine(Configuration configuration) {
    super(configuration);
    this.config = configuration;
    log = LogFactory.getLog(DMManagementEngine.class);

    // dbUserManager = (DBUserManager)config.getBeanInstance(CFG_USER_MANAGER);
    officer = (Officer) config.getBeanInstance(CFG_SECURITY_OFFICER);
  }

  // Setter and Getters *******************************************************************
  /**
   * @return the alwaysQueueProcessor
   */
  public boolean isAlwaysQueueProcessor() {
    return alwaysQueueProcessor;
  }

  /**
   * @param alwaysQueueProcessor the alwaysQueueProcessor to set
   */
  public void setAlwaysQueueProcessor(boolean alwaysQueueProcessor) {
    this.alwaysQueueProcessor = alwaysQueueProcessor;
  }

  // Public methods
  // ********************************************************************

  /**
   * Stores the given DeviceDMState.
   * 
   * @param d
   *          the DeviceDMState
   * 
   */
  public void storeDeviceDMState(DeviceDMState state) {
    String provisionJobID = state.mssid;
    //String deviceExternalID = state.deviceId;
    
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        ProvisionJob job = jobBean.loadJobByID(provisionJobID);
        if (job != null) {
           factory.beginTransaction();
           jobBean.copy(state, job);
           factory.commit();
        }
    } catch (Exception ex) {
      if (factory != null) {
         factory.rollback();
      }
      log.error("Error storing the device DM state " + state.toString(), ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * Reads the given DeviceDMState.
   * 
   * @param state
   *          the DeviceDMState
   * 
   * @throws NotFoundException
   *           if the DeviceDMState is not found
   */

  public void readDeviceDMState(DeviceDMState state) throws NotFoundException {
    
    String provisionJobID = state.mssid;
    
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        ProvisionJob job = jobBean.loadJobByID(provisionJobID);
        jobBean.copy(job, state);        
    } catch (Exception ex) {
      throw new NotFoundException("Error reading the device DM state " + state.toString(), ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * Deletes the given DeviceDMState.
   * 
   * @param d
   *          the DeviceDMState
   * 
   */

  public void deleteDeviceDMState(DeviceDMState d) {
    // In OTAS DM inventory, ProvisionJobStatus could not be deleted,
    // so bypass it.
    if (log.isDebugEnabled()) {
       log.debug("deleting DeviceDMState, always bypass it.");
    }
  }

  /**
   * This method tries to associate the given DeviceDMState instance to an
   * existing device management state. The algorithm is as follows:
   * <ul>
   * <li>look for a <i>notified</i> device with session id equal to dms.mssid</li>
   * <li>IF a device management session is found THEN fill dms with the
   * retrieved values and return true</li>
   * <li>ELSE look for any other state whose associated device is equal to
   * dms.deviceId</li>
   * <li>IF a device management session is found THEN fill dms with the
   * retrieved values and return true.
   * <li>return false in any other case
   * </ul>
   * 
   * @param dms
   *          the DM session state
   * @param deviceConnected
   *          the device connected
   * @return true if the device management session was resolved, false
   *         otherwise.
   */
  public boolean resolveDMState(DeviceDMState dms, Sync4jDevice deviceConnected) {
    /*
    //
    // 1. Search for notified devices first
    //
    WhereClause wc1 = new WhereClause(DeviceDMState.PROPERTY_STATE, new String[] { String
        .valueOf((char) DeviceDMState.STATE_NOTIFIED) }, WhereClause.OPT_EQ, true);

    WhereClause wc2 = new WhereClause(DeviceDMState.PROPERTY_SESSION_ID, new String[] { dms.mssid },
        WhereClause.OPT_EQ, true);

    LogicalClause select = new LogicalClause(LogicalClause.OPT_AND, new Clause[] { wc1, wc2 });

    dms.state = DeviceDMState.STATE_NOTIFIED;

    try {
      DeviceDMState[] rows = (DeviceDMState[]) getStore().read(dms, select);

      if (rows.length > 0) {
        //
        // The device id is null in the database, therefore we have to
        // overwrite it after copy
        //
        String deviceConnectedId = dms.deviceId;
        dms.copyFrom(rows[0]);

        if (!dms.deviceId.equalsIgnoreCase(deviceConnectedId)) {
          //
          // The device connected is different from the device notified
          // So, we swap the device id (SIM Swap)
          //
          Sync4jDevice deviceNotified = new Sync4jDevice(dms.deviceId);

          store.read(deviceNotified);
          store.read(deviceConnected);

          String descriptionDeviceNotified = deviceNotified.getDescription();
          String descriptionDeviceConnected = deviceConnected.getDescription();

          deviceNotified.setDescription(descriptionDeviceConnected);
          deviceConnected.setDescription(descriptionDeviceNotified);

          store.store(deviceNotified);
          store.store(deviceConnected);
        }
        dms.deviceId = deviceConnectedId;

        return true;
      }
    } catch (PersistentStoreException e) {
      if (log.isLoggable(Level.SEVERE)) {
        log.severe("Error reading the device DM state " + dms.toString() + ": " + e.getMessage());
        log.throwing(getClass().getName(), "resolveDMState", e);
      }

      return false;
    }
    //
    // 2. Search for any other association
    //
    wc1 = new WhereClause(DeviceDMState.PROPERTY_STATE, new String[] { String
        .valueOf((char) DeviceDMState.STATE_IN_PROGRESS) }, WhereClause.OPT_EQ, true);

    wc2 = new WhereClause(DeviceDMState.PROPERTY_DEVICE_ID, new String[] { dms.deviceId }, WhereClause.OPT_EQ, true);

    select = new LogicalClause(LogicalClause.OPT_AND, new Clause[] { wc1, wc2 });

    try {
      DeviceDMState[] rows = (DeviceDMState[]) getStore().read(dms, select);

      if (rows.length > 0) {
        dms.copyFrom(rows[0]);

        return true;
      }
    } catch (PersistentStoreException e) {
      if (log.isLoggable(Level.SEVERE)) {
        log.severe("Error reading the device DM state " + dms.toString() + ": " + e.getMessage());
        log.throwing(getClass().getName(), "resolveDMState", e);
      }
    }

    return false;
    */
    
    
    // Load DeviceState from DM queued.
    String deviceExternalID = dms.deviceId;
    
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        DeviceBean deviceBean = factory.createDeviceBean();
        ProvisionJobBean jobBean = factory.createProvisionJobBean();
        Device device = deviceBean.getDeviceByExternalID(deviceExternalID);
        List<ProvisionJob> jobs = jobBean.findJobsQueued(device);
        if (jobs == null || jobs.size() == 0) {
           return false;
        }
        if (jobs.size() == 1) {
           if (this.isAlwaysQueueProcessor()) {
              // Always 0, fire-up queue processor
              dms.mssid = "0";
           } else {
             //Processing the first Job.
             ProvisionJob job = (ProvisionJob)jobs.get(0);
             jobBean.copy(job, dms);
           }
        } else {
          // Job queue, reset the mssid
          dms.mssid = "0";
        }
        return true;
    } catch (NotFoundException ex) {
      return false;
    } catch (Exception ex) {
      log.error("Error resolving the device DM state " + dms.toString(), ex);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
    return false;
  }
}
