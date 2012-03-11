/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/wurfl/ObjectsManager.java,v 1.4 2008/04/24 11:08:21 zhao Exp $
  * $Revision: 1.4 $
  * $Date: 2008/04/24 11:08:21 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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

package com.npower.wurfl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <b>Luca Passani</b>, passani at eunet dot no <br>
 *         <br>
 *         Rather than initializing a new CapabilityMatrix and UAManager each
 *         time you need one, you should request ObjectsManager to give you the
 *         instance of an existing one.<br>
 *         The advantage of this approach are huge in terms of performance and
 *         memory usage, particularly in the contaxt of web applications.
 * 
 * @version $Revision: 1.4 $ $Date: 2008/04/24 11:08:21 $
 */
public class ObjectsManager {
  
  private static Log log = LogFactory.getLog(ObjectsManager.class);

  private static Object           lock                     = new Object();

  private static Wurfl            wurflInstance            = null;

  private static CapabilityMatrix capabilityMatrixInstance = null;

  private static UAManager        UAManagerInstance        = null;

  private static ListManager      ListManagerInstance      = null;
  
  private static ObjectsManager instance = null;

  /**
   * Default constructor
   */
  private ObjectsManager() {
    super();
  }

  /**
   * Return an instance of ObjectsManager
   * 
   * @param file
   * @return
   */
  public static ObjectsManager newInstance(WurflSource ws) throws IOException {
    synchronized (lock) {
      instance = new ObjectsManager();
      instance.initialize(ws);
      return instance;
    }
  }

  /**
   * This method lets you initialize the WURFL by providing an object which
   * knows how to get to the input streams
   */

  public synchronized void initialize(WurflSource ws) throws IOException {
    if (wurflInstance == null) {

      InputStream in1 = ws.getWurflInputStream();
      InputStream in2 = ws.getWurflPatchInputStream();
      if (in1 != null) {
        log.info("Initializing WURFL database from stream with InputStream.");
        wurflInstance = new Wurfl(in1, in2);
      } else {
        log.fatal("\nCannot initialize Wurfl. InputStream is empty!");
      }
      // Initialize cache.
      //wurflInstance.getActualDeviceElementsList();
      ListManager lm = this.getListManagerInstance();
      lm.getDeviceBrandList();
      lm.getDeviceGroupedByBrand();

    } else {
      log.debug("WARNING: Wurfl database was already initialized ");
    }
  }

  /**
   * Use this method to understand if the WURFL is already initialized or not
   */

  public synchronized boolean isInitialized() {
    if (wurflInstance == null) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Use this method to force the library to reload the WURFL again
   */

  public synchronized void reload() {
    wurflInstance = null;
    capabilityMatrixInstance = null;
    UAManagerInstance = null;
    ListManagerInstance = null;
    System.gc();
  }

  /**
   * @return
   */
  public Wurfl getWurflInstance() {
    return wurflInstance;
  }

  /**
   * Use this method to retrieve the existing instance of the CapabilityMatrix
   * (or get one initialized for you). Similar to a Singleton in a way.
   */

  public synchronized CapabilityMatrix getCapabilityMatrixInstance() {
    if (capabilityMatrixInstance == null)
      capabilityMatrixInstance = new CapabilityMatrix(this.getWurflInstance());
    return capabilityMatrixInstance;
  }

  /**
   * Use this method to retrieve the existing instance of the UAManager (or get
   * one initialized for you).
   */

  public synchronized UAManager getUAManagerInstance() {
    if (UAManagerInstance == null)
      UAManagerInstance = new UAManager(this.getWurflInstance());
    return UAManagerInstance;
  }

  /**
   * Use this method to retrieve the existing instance of the ListManager (or
   * get one initialized for you).
   */

  public synchronized ListManager getListManagerInstance() {
    if (ListManagerInstance == null) {
      ListManagerInstance = new ListManager(this.getWurflInstance());
      ListManagerInstance.setObjectsManager(this);
    }
    return ListManagerInstance;
  }

  /**
   * Get an XMLized version of the WURFL (WURFL+patch Object Model turned into
   * an XML file)
   */

  public String getWURFLAsXML() {
    return this.getWurflInstance().toXML();
  }

  public void getFilteredWurfl(HashSet<String> capaList, OutputStream out) {

    if (this.getWurflInstance() == null) {
      return;
    } else {
      this.getWurflInstance().filterCapabilities(capaList, out);
    }
  }

}
