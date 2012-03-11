/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/server/store/DevicePersistentStore.java,v 1.13 2008/06/16 10:11:14 zhao Exp $
  * $Revision: 1.13 $
  * $Date: 2008/06/16 10:11:14 $
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
package com.npower.dm.server.store;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.security.Sync4jPrincipal;
import sync4j.framework.server.Sync4jDevice;
import sync4j.framework.server.store.Clause;
import sync4j.framework.server.store.ConfigPersistentStoreException;
import sync4j.framework.server.store.PersistentStore;
import sync4j.framework.server.store.PersistentStoreException;
import sync4j.framework.tools.Base64;
import sync4j.framework.tools.MD5;

import com.npower.dm.core.Device;
import com.npower.dm.management.DeviceBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.server.engine.EngineConfig;
;
/**
 * @author Zhao DongLu
 * @version $Revision: 1.13 $ $Date: 2008/06/16 10:11:14 $
 */
public class DevicePersistentStore implements PersistentStore, Serializable {
  
  //private static Log log = LogFactory.getLog(DevicePersistentStore.class);

  /**
   * 
   */
  public DevicePersistentStore() {
    super();
  }
  
  // private methods *******************************************************************
  
  /**
   * Checks if the given configuration parameters contain all required
   * parameters. If not a <i>ConfigPersistentStoreException</i> is thrown.
   *
   * @param config the <i>Map</i> containing the configuration parameters
   *
   * @throws ConfigPersistentStoreException in case of missing parameters
   */
  private void checkConfigParams(Map config) throws ConfigPersistentStoreException {
  }
  
  public void storeDevice(Sync4jDevice sync4jDevice) throws PersistentStoreException {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        String externalID = sync4jDevice.getDeviceId();
        if (externalID == null || externalID.trim().length() == 0) {
           throw new PersistentStoreException("Could not load the Sync4JDevice from DM inventory, the device's externalID is NULL");
        }
        
        DeviceBean bean = factory.createDeviceBean();
        // Load Device from DM inventory by externalID
        Device device = bean.getDeviceByExternalID(externalID.trim());
        if (device == null) {
           throw new PersistentStoreException("Could not load the Device from DMInventory by externalID: " + externalID);
        }
        // Exchange the properties to Device
        device.setDescription(sync4jDevice.getDescription());
        //device.setOMAClientPassword(sync4jDevice.getDigest());
        device.setOMAServerPassword(sync4jDevice.getServerPassword());
        //device.setDescription(sync4jDevice.getType());
        
        // Encode nonce by Base64
        byte[] clientNonce = sync4jDevice.getClientNonce();
        device.setOMAClientNonce(((clientNonce != null) ? new String(Base64.encode(clientNonce)) : null));
        byte[] serverNonce = sync4jDevice.getServerNonce();
        device.setOMAServerNonce(((serverNonce != null) ? new String(Base64.encode(serverNonce)) : null));

        // Update and commit
        factory.beginTransaction();
        bean.update((Device)device);
        factory.commit();
        
    } catch (Exception e) {
      throw new PersistentStoreException("Unable store the device into DMInventory", e);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  
  private void storePrincipal(Sync4jPrincipal principal) throws PersistentStoreException {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        String externalID = principal.getDeviceId();
        if (externalID == null || externalID.trim().length() == 0) {
           throw new PersistentStoreException("Could not load the Sync4JDevice from DM inventory, the device's externalID is NULL");
        }
        
        DeviceBean bean = factory.createDeviceBean();
        // Load Device from DM inventory by externalID
        Device device = bean.getDeviceByExternalID(externalID.trim());
        if (device == null) {
           throw new PersistentStoreException("Could not load the Device from DMInventory by externalID: " + externalID);
        }
        // Exchange the properties to Device
        device.setOMAClientUsername(principal.getUsername());

        // Update and commit
        factory.beginTransaction();
        bean.update((Device)device);
        factory.commit();
        
    } catch (Exception e) {
      throw new PersistentStoreException("Unable store the principal into DMInventory", e);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  public void readDevice(Sync4jDevice sync4jDevice) throws PersistentStoreException {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        String externalID = sync4jDevice.getDeviceId();
        if (externalID == null || externalID.trim().length() == 0) {
           throw new PersistentStoreException("Could not load the Sync4JDevice from DM inventory, the device's externalID is NULL");
        }
        
        DeviceBean bean = factory.createDeviceBean();
        // Load Device from DM inventory by externalID
        Device device = bean.getDeviceByExternalID(externalID.trim());
        if (device == null) {
           throw new PersistentStoreException("Could not load the Device from DMInventory by externalID: " + externalID);
        }
        // Copy information into Sync4jDevice
        copy(device, sync4jDevice);
        
    } catch (Exception e) {
      throw new PersistentStoreException("Unable load the device from DMInventory", e);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }

  /**
   * Copy device into Sync4jDevice
   * 
   * @param sync4jDevice
   * @param device
   */
  public static void copy(Device device, Sync4jDevice sync4jDevice) {
    // Fetch the properties from Device
    sync4jDevice.setDescription(device.getDescription());
    // Cacluate Digest for Sync4Device
    // b64(H(username:password)
    String digest = new String(Base64.encode(MD5.digest( (device.getOMAClientUsername() + ":" + device.getOMAClientPassword()).getBytes())));

    sync4jDevice.setDigest(digest);
    sync4jDevice.setServerPassword(device.getOMAServerPassword());
    
    // Decode nonce by Base64
    String clientNonceString = device.getOMAClientNonce();
    byte[] clientNonce = null;
    clientNonce = ((clientNonceString != null && clientNonceString.trim().length() != 0 ) 
                          ? Base64.decode(clientNonceString) : null);
    sync4jDevice.setClientNonce(clientNonce);
    
    String serverNonceString = device.getOMAServerNonce();
    byte[] serverNonce = null;
    serverNonce = ((serverNonceString != null && serverNonceString.trim().length() != 0 ) 
                          ? Base64.decode(serverNonceString) : null);
    sync4jDevice.setServerNonce(serverNonce);
    //sync4jDevice.setType("phone");
  }
  
  public void readPrincipal(Sync4jPrincipal principal) throws PersistentStoreException {
    ManagementBeanFactory factory = null;
    try {
        factory = ManagementBeanFactory.newInstance(EngineConfig.getProperties());
        String externalID = principal.getDeviceId();
        if (externalID == null || externalID.trim().length() == 0) {
           throw new PersistentStoreException("Could not load the Sync4jPrincipal from DM inventory, the device's externalID is NULL");
        }
        
        DeviceBean bean = factory.createDeviceBean();
        // Load Device from DM inventory by externalID
        Device device = bean.getDeviceByExternalID(externalID.trim());
        if (device == null) {
           throw new PersistentStoreException("Could not load the Device from DMInventory by externalID: " + externalID);
        }
        // Fetch the properties from Device
        principal.setUsername(device.getOMAClientUsername());
        principal.setId("" + device.getID());
        
        
    } catch (Exception e) {
      throw new PersistentStoreException("Unable load the device from DMInventory", e);
    } finally {
      if (factory != null) {
         factory.release();
      }
    }
  }
  
  private void readDeviceDMState(DeviceDMState state) {
    state.mssid     = "0000";
    state.state     = (byte)"p".charAt(0);  // Pending
    state.start     = new Date();
    state.end       = null;
    state.operation = "TreeDiscoveryProcessor";
    state.info      = "";
  }
  
  // public methods ********************************************************************

  /* (non-Javadoc)
   * @see sync4j.framework.server.store.PersistentStore#configure(java.util.Map)
   */
  public void configure(Map config) throws ConfigPersistentStoreException {
    checkConfigParams(config);
  }

  /* (non-Javadoc)
   * @see sync4j.framework.server.store.PersistentStore#store(java.lang.Object)
   */
  public boolean store(Object o) throws PersistentStoreException {
    if (o instanceof Sync4jDevice) {
       storeDevice((Sync4jDevice) o);
       return true;
    } else if (o instanceof Sync4jPrincipal) {
      storePrincipal((Sync4jPrincipal)o);
    } else if (o instanceof DeviceDMState) {
      // TODO implements it
    }
    return false;
  }

  /* (non-Javadoc)
   * @see sync4j.framework.server.store.PersistentStore#read(java.lang.Object)
   */
  public boolean read(Object o) throws PersistentStoreException {
    if (o instanceof Sync4jDevice) {
       readDevice((Sync4jDevice) o);
       return true;
    } else if (o instanceof Sync4jPrincipal) {
      readPrincipal((Sync4jPrincipal)o);
    } else if (o instanceof DeviceDMState) {
      // TODO implements it
      readDeviceDMState((DeviceDMState)o);
    }
    return false;
  }

  /* (non-Javadoc)
   * @see sync4j.framework.server.store.PersistentStore#read(java.lang.Class)
   */
  public Object[] read(Class arg0) throws PersistentStoreException {
    //throw new UnsupportedOperationException("Method not implements");
    return null;
  }

  /* (non-Javadoc)
   * @see sync4j.framework.server.store.PersistentStore#delete(java.lang.Object)
   */
  public boolean delete(Object arg0) throws PersistentStoreException {
    return false;
  }

  /* (non-Javadoc)
   * @see sync4j.framework.server.store.PersistentStore#read(java.lang.Object, sync4j.framework.server.store.Clause)
   */
  public Object[] read(Object o, Clause clause) throws PersistentStoreException {
    if (o instanceof Sync4jDevice) {
       return readDevices((Sync4jDevice) o, clause);
    }
    return null;
  }

  /**
   * @param device
   * @param clause
   * @return
   */
  private Object[] readDevices(Sync4jDevice device, Clause clause) {
    return null;
  }

  /* (non-Javadoc)
   * @see sync4j.framework.server.store.PersistentStore#store(java.lang.String, java.lang.Object, java.lang.String)
   */
  public boolean store(String arg0, Object arg1, String arg2) throws PersistentStoreException {
    return false;
  }

  /* (non-Javadoc)
   * @see sync4j.framework.server.store.PersistentStore#readCounter(java.lang.String, int)
   */
  public int readCounter(String arg0, int arg1) throws PersistentStoreException {
    return 0;
  }

}
