/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/wurfl/ListManager.java,v 1.5 2007/11/16 10:16:58 zhao Exp $
  * $Revision: 1.5 $
  * $Date: 2007/11/16 10:16:58 $
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import nu.xom.Element;

import org.apache.commons.lang.StringUtils;

/**
 * @author <b>Luca Passani</b>
 *         <br>
 *         This class is useful to avoid exposing the Wurfl class externally. It
 *         just returns lists of devices, lists of capabilities and lists of
 *         capability name/values.
 * 
 * @version $Revision: 1.5 $ $Date: 2007/11/16 10:16:58 $
 */

public class ListManager {
  
  //private static Log log = LogFactory.getLog(ListManager.class);

  private ObjectsManager objectsManager = null;

  private Wurfl                                         wu                                = null;

  private HashSet<String>                               deviceIdSet                       = null;

  private ArrayList<String>                             deviceIdSetSorted                 = null;

  private ArrayList<String>                             capabilitySet                     = null;

  private HashMap<String, ArrayList<String>>            listOfGroups                      = null;

  private HashMap<String, Element>                      deviceElementsListXOM             = null;

  private HashMap<String, WurflDevice>                  deviceElementsList                = null;                                               // for

  // fast
  // access
  // to
  // device
  // elements

  // lists of actual devices
  private TreeMap<String, WurflDevice>                  actualDeviceElementsList          = new TreeMap<String, WurflDevice>();                 // for

  // fast
  // access
  // to
  // actual
  // device
  // elements

  private TreeMap<String, TreeMap<String, WurflDevice>> actualDevicesByBrand              = new TreeMap<String, TreeMap<String, WurflDevice>>(); // Actual

  // Devices
  // by
  // brand

  private ArrayList<String>                             brandList                         = new ArrayList<String>(40);                          // List

  // of
  // device
  // brands

  private HashMap<String, String>                       listOfUAWithDeviceID              = null;                                               // associate

  // UA
  // string
  // with
  // device
  // ID

  // Map of HashMaps with the list of capabilities/values. Key is a device ID
  // useful to avoid regenerating a new big HashMap every time
  // getCapabilitiesForDeviceID()
  // is invoked
  private Map<String, HashMap<String, String>>          listOfListsOfCapabilityValuePairs = (new HashMap<String, HashMap<String, String>>(
                                                                                              200, 0.75f));
  
  /**
   * Brand Hash Map, for retrieve wurfl device ID by brand
   */
  private Map<String, Map<String, WurflDevice>> modelBrandMap = new HashMap<String, Map<String, WurflDevice>>();

  public ListManager(Wurfl _wu) {

    wu = _wu;
    capabilitySet = wu.getListOfCapabilities();
    listOfGroups = wu.getListOfGroups();

    deviceIdSet = wu.getDeviceIdSet();
    deviceIdSetSorted = new ArrayList<String>(deviceIdSet);
    Collections.sort(deviceIdSetSorted);

    listOfUAWithDeviceID = wu.getListOfUAWithDeviceID();

    deviceElementsListXOM = wu.getDeviceElementsList();

    // Turn the HashMap of devices from one of nu.xom.Element to WurflDevice
    Iterator<String> keys = deviceElementsListXOM.keySet().iterator();
    String key;
    deviceElementsList = new HashMap<String, WurflDevice>(2053, 0.75f);
    while (keys.hasNext()) {
      key = keys.next();
      WurflDevice wd = new WurflDevice(deviceElementsListXOM.get(key));
      deviceElementsList.put(key, wd);
    }

  }

  /**
   * @return the objectsManager
   */
  public ObjectsManager getObjectsManager() {
    return objectsManager;
  }

  /**
   * @param objectsManager the objectsManager to set
   */
  public void setObjectsManager(ObjectsManager objectsManager) {
    this.objectsManager = objectsManager;
  }

  /**
   * Return HashSet of device IDs (all device IDs, optimized for fast look-up on
   * existence)
   * 
   */

  public HashSet<String> getDeviceIdSet() {

    return deviceIdSet;
  }

  /**
   * Return ArrayList of device IDs to WurflDevices
   * 
   */

  public ArrayList<String> getDeviceIdSetSorted() {
    return deviceIdSetSorted;
  }

  /**
   * Return ArrayList of Capabilities (i.e. name of all capabilities)
   * 
   */

  public ArrayList<String> getCapabilitySet() {
    return capabilitySet;
  }

  /**
   * Return HashMap of Arraylists of Capabilities (i.e. name of all
   * capabilities)
   * 
   */

  public HashMap<String, ArrayList<String>> getListOfGroups() {
    return listOfGroups;
  }

  /**
   * Return HashMap of device IDs to WurflDevices
   * 
   */

  public HashMap<String, WurflDevice> getDeviceElementsList() {
    return deviceElementsList;
  }

  /**
   * Return HashMap (from UA Strings to Device Ids)
   * 
   */

  public HashMap<String, String> getListOfUAWithDeviceID() {
    return listOfUAWithDeviceID;
  }

  /**
   * Given a device ID returns HashMap with association of all capabilities and
   * their value
   * 
   */

  public Map<String, String> getCapabilitiesForDeviceID(String device_id) {

    HashMap<String, String> obj = listOfListsOfCapabilityValuePairs.get(device_id);
    if (obj != null) { // HashMap exists from the past
      return obj;
    } else { // we need to generate it and store for the next time
      HashMap<String, String> hm = new HashMap<String, String>(capabilitySet.size());
      CapabilityMatrix cm = this.getObjectsManager().getCapabilityMatrixInstance();

      for (int i = 0; i < capabilitySet.size(); i++) {
        hm.put(capabilitySet.get(i), cm.getCapabilityForDevice(device_id, capabilitySet.get(i)));
      }
      listOfListsOfCapabilityValuePairs.put(device_id, hm);
      return new TreeMap<String, String>(hm);
    }
  }

  /**
   * Return TreeMap of device IDs to WurflDevices representing actual devices
   * (i.e. this device element represents a real device and a bunch of
   * subdevices with similar software subversions.
   * 
   */

  public synchronized TreeMap<String, WurflDevice> getActualDeviceElementsList() {

    if (actualDeviceElementsList.isEmpty()) {
      CapabilityMatrix cm = this.getObjectsManager().getCapabilityMatrixInstance();
      TreeMap<String, Element> actualXOMDevices = wu.getActualDeviceElementsList();
      Iterator<String> keys = actualXOMDevices.keySet().iterator();
      while (keys.hasNext()) {

        String key = keys.next();
        Element el = actualXOMDevices.get(key);
        WurflDevice wd = new WurflDevice(el);
        String bn = cm.getCapabilityForDevice(key, "brand_name");
        String mn = cm.getCapabilityForDevice(key, "model_name");
        // only devices with name and brand defined in the WURFL please
        if (!bn.equals("") && !mn.equals("")) {
          wd.setBrandName(bn);
          wd.setModelName(mn);
          actualDeviceElementsList.put(key, wd);
        }
        // else { //just for debugging purposes
        // log.debug("Discarding actual device: "+wd.getId());
        // }
      }
    }
    return actualDeviceElementsList;
  }

  /*
   * Just for debugging purposes. Returns list of devices also for devices with
   * no brand or no model name
   */

  public TreeMap<String, WurflDevice> getSpecialActualDeviceElementsList() {

    TreeMap<String, WurflDevice> specialActualDeviceElementsList = new TreeMap<String, WurflDevice>();

    CapabilityMatrix cm = this.getObjectsManager().getCapabilityMatrixInstance();
    TreeMap<String, Element> actualXOMDevices = wu.getActualDeviceElementsList();
    Iterator<String> keys = actualXOMDevices.keySet().iterator();
    while (keys.hasNext()) {

      String key = keys.next();
      Element el = actualXOMDevices.get(key);
      WurflDevice wd = new WurflDevice(el);
      String bn = cm.getCapabilityForDevice(key, "brand_name");
      String mn = cm.getCapabilityForDevice(key, "model_name");
      wd.setBrandName(bn);
      wd.setModelName(mn);
      specialActualDeviceElementsList.put(key, wd);
    }

    return specialActualDeviceElementsList;
  }

  /**
   * Return HashMap of HashMaps brand-&gt;modelname-&gt;WurflDevice
   * 
   */
  public TreeMap<String, TreeMap<String, WurflDevice>> getDeviceGroupedByBrand() {

    if (actualDevicesByBrand.isEmpty()) {
      TreeMap<String, WurflDevice> act_devices = getActualDeviceElementsList();
      Iterator<String> keys = act_devices.keySet().iterator();
      while (keys.hasNext()) {
        String key = keys.next();
        WurflDevice wd = act_devices.get(key);
        String bn = wd.getBrandName();
        if (actualDevicesByBrand.get(bn) == null) {
          // new brand
          TreeMap<String, WurflDevice> hm = new TreeMap<String, WurflDevice>();
          hm.put(wd.getModelName(), wd);
          actualDevicesByBrand.put(bn, hm);
        } else {
          // add to existing brand
          TreeMap<String, WurflDevice> hm = actualDevicesByBrand.get(bn);
          hm.put(wd.getModelName(), wd);
        }
      }
    }
    return actualDevicesByBrand;
  }

  /**
   * Return Ordered ArrayList of Brand Name
   * 
   */
  public ArrayList<String> getDeviceBrandList() {

    if (brandList.isEmpty()) {
      TreeMap<String, TreeMap<String, WurflDevice>> lol = getDeviceGroupedByBrand();
      brandList = new ArrayList<String>(lol.keySet());
      Collections.sort(brandList);
    }
    return brandList;
  }

  /**
   * Find WurflDevice by brand
   * @param manufacturer
   * @param modelExtID
   * @param lm
   * @return
   */
  public WurflDevice getDeviceByBrand(String manufacturer, String modelExtID) {
    
    if (StringUtils.isEmpty(manufacturer) || StringUtils.isEmpty(modelExtID)) {
       return null;
    }
    
    // Translate
    manufacturer = translateBrandName(manufacturer);
    modelExtID = translateModelName(manufacturer, modelExtID);
    
    // Initializing
    if (modelBrandMap.isEmpty()) {
       // Generate brand Map
      TreeMap<String, WurflDevice> load = this.getActualDeviceElementsList();
      Iterator<String> keys = load.keySet().iterator();
      while (keys.hasNext()) {
            String key = keys.next();
            WurflDevice wd = load.get(key);
            
            if (!modelBrandMap.containsKey(wd.getBrandName().toLowerCase())) {
              modelBrandMap.put(wd.getBrandName().toLowerCase(), new HashMap<String, WurflDevice>());
            }
            Map<String, WurflDevice> modelOfBrandMap = modelBrandMap.get(wd.getBrandName().toLowerCase());
            
            if (!modelOfBrandMap.containsKey(wd.getModelName().toLowerCase())) {
               modelOfBrandMap.put(wd.getModelName().toLowerCase(), wd);
            }
      }
    }
    
    // Retrieve
    if (modelBrandMap.containsKey(manufacturer.trim().toLowerCase())) {
       Map<String, WurflDevice> modelOfBrandMap = modelBrandMap.get(manufacturer.trim().toLowerCase());
       WurflDevice device = modelOfBrandMap.get(modelExtID.trim().toLowerCase());
       return device;
    }
    return null;
    /*
    TreeMap<String, WurflDevice> load = this.getActualDeviceElementsList();
    Iterator<String> keys = load.keySet().iterator();
    WurflDevice foundDevice = null;
    while (keys.hasNext()) {
        String key = keys.next();
        WurflDevice wd = load.get(key);
        if (manufacturer.equalsIgnoreCase(wd.getBrandName())
            && modelExtID.equalsIgnoreCase(wd.getModelName())) {
           foundDevice = wd;
        }
    }
    return foundDevice;
    */
  }
  
  private static String translateBrandName(String manufacturer) {
    return manufacturer;
  }
  
  private static String translateModelName(String manufacturer, String model) {
    if (manufacturer.equalsIgnoreCase("Samsung")) {
      if (model.toUpperCase().startsWith("C200")) {
         model = "C200";
      } else if (model.toUpperCase().startsWith("E100A")) {
        model = "E100";
      } else if (model.toUpperCase().startsWith("E340E")) {
        model = "E340";
      } else if (model.toUpperCase().startsWith("E570v")) {
        model = "E570";
      } else if (model.toUpperCase().startsWith("E610C")) {
        model = "E610";
      } else if (model.toUpperCase().startsWith("E820")) {
        model = "E820";
      } else if (model.toUpperCase().startsWith("E860V")) {
        model = "E860";
      } else if (model.toUpperCase().startsWith("X100A")) {
        model = "X100";
      } else if (model.toUpperCase().startsWith("X640")) {
        model = "X640";
      } else if (model.toUpperCase().startsWith("X660V")) {
        model = "X660";
      } else if (model.toUpperCase().startsWith("Z140")) {
        model = "Z140";
      } else if (model.toUpperCase().startsWith("Z400V")) {
        model = "Z400";
      } else if (model.toUpperCase().startsWith("Z500V")) {
        model = "Z500";
      } else if (model.toUpperCase().startsWith("Z520V")) {
        model = "Z520";
      }
      return model;
    } else if (manufacturer.equalsIgnoreCase("Alcatel")) {
      model = StringUtils.replace(model, "OT", "One Touch ");
      if (model.toUpperCase().startsWith("One Touch 331".toUpperCase())) {
         return "One Touch 331/525/526/531";
      } else if (model.toUpperCase().startsWith("One Touch 525".toUpperCase())) {
        return "One Touch 331/525/526/531";
      } else if (model.toUpperCase().startsWith("One Touch 526".toUpperCase())) {
        return "One Touch 331/525/526/531";
      } else if (model.toUpperCase().startsWith("One Touch 531".toUpperCase())) {
        return "One Touch 331/525/526/531";
      } else if (model.toUpperCase().startsWith("OneTouch331".toUpperCase())) {
        return "One Touch 331/525/526/531";
      } else if (model.toUpperCase().startsWith("OneTouch331P".toUpperCase())) {
        return "One Touch 331/525/526/531";
      } else if (model.toUpperCase().startsWith("One Touch 556".toUpperCase())) {
        return "One Touch 556/557/565";
      } else if (model.toUpperCase().startsWith("One Touch 557".toUpperCase())) {
        return "One Touch 556/557/565";
      } else if (model.toUpperCase().startsWith("One Touch 565".toUpperCase())) {
        return "One Touch 556/557/565";
      } else if (model.toUpperCase().startsWith("One Touch 756".toUpperCase())) {
        return "One Touch 756/757";
      } else if (model.toUpperCase().startsWith("One Touch 757".toUpperCase())) {
        return "One Touch 756/757";
      } else if (model.toUpperCase().startsWith("One Touch 332a".toUpperCase())) {
        return "One Touch 332";
      }
      return model;
    } else if (manufacturer.equalsIgnoreCase("Ericsson")) {
      if (model.toUpperCase().startsWith("A2628")) {
         return "A2628";
      } else if (model.toUpperCase().startsWith("A2628")) {
        return "A2638";
      } else if (model.toUpperCase().startsWith("R320")) {
        return "R320";
      } else if (model.toUpperCase().startsWith("R380")) {
        return "R380e";
      } else if (model.toUpperCase().startsWith("R520")) {
        return "R520";
      } else if (model.toUpperCase().startsWith("T20e".toUpperCase())) {
        return "T20e";
      } else if (model.toUpperCase().startsWith("T20sc".toUpperCase())) {
        return "T20";
      } else if (model.toUpperCase().startsWith("T29")) {
        return "T29s";
      } else if (model.toUpperCase().startsWith("T39")) {
        return "T39m";
      } else if (model.toUpperCase().startsWith("T65")) {
        return "T65";
      }
    } else if (manufacturer.equalsIgnoreCase("Nokia")) {
      if (model.toUpperCase().startsWith("5500d".toUpperCase())) {
         return "5500";
      } else if (model.toUpperCase().startsWith("6020b".toUpperCase())) {
        return "6020";
      } else if (model.toUpperCase().startsWith("6102i".toUpperCase())) {
        return "6102";
      } else if (model.toUpperCase().startsWith("6170b".toUpperCase())) {
        return "6170";
      } else if (model.toUpperCase().startsWith("6200IM")) {
        return "6200";
      } else if (model.toUpperCase().startsWith("6500")) {
        return "6500";
      } else if (model.toUpperCase().startsWith("6670b".toUpperCase())) {
        return "6670";
      } else if (model.toUpperCase().startsWith("6820")) {
        return "6820";
      } else if (model.toUpperCase().startsWith("6822")) {
        return "6822";
      } else if (model.toUpperCase().startsWith("8800")) {
        return "8800";
      } else if (model.toUpperCase().startsWith("9110I")) {
        return "9110";
      } else if (model.toUpperCase().startsWith("9300b".toUpperCase())) {
        return "9300";
      } else if (model.toUpperCase().startsWith("Ngage".toUpperCase())) {
        return "N-Gage";
      } else if (model.toUpperCase().startsWith("NgageQD".toUpperCase())) {
        return "N-Gage QD";
      } else if (model.toUpperCase().startsWith("E61i".toUpperCase())) {
        return "E61";
      } else if (model.toUpperCase().startsWith("N93i".toUpperCase())) {
        return "N93";
      }
    } else if (manufacturer.equalsIgnoreCase("RIM")) {
      if (model.toUpperCase().startsWith("BlackBerry 7100g".toUpperCase())) {
         return "BlackBerry 7100";
      } else if (model.toUpperCase().startsWith("BlackBerry 7130c".toUpperCase())) {
           return "BlackBerry 7130";
      } else if (model.toUpperCase().startsWith("BlackBerry 8700c".toUpperCase())) {
        return "BlackBerry 8700";
      } else if (model.toUpperCase().startsWith("BlackBerry 8700r".toUpperCase())) {
        return "BlackBerry 8700";
      }
    } else if (manufacturer.equalsIgnoreCase("SonyEricsson")) {
      if (model.toUpperCase().startsWith("W550c".toUpperCase())) {
         return "W550i";
      } else if (model.toUpperCase().startsWith("W900c".toUpperCase())) {
        return "W900i";
      } else if (model.toUpperCase().startsWith("W810a".toUpperCase())) {
        return "W810i";
      } else if (model.toUpperCase().startsWith("Z310c".toUpperCase())) {
        return "Z310i";
      } else if (model.toUpperCase().startsWith("Z550i".toUpperCase())) {
        return "Z550";
      } else if (model.toUpperCase().startsWith("Z550c".toUpperCase())) {
        return "Z550";
      } else if (model.toUpperCase().startsWith("W610c".toUpperCase())) {
        return "W610i";
      } else if (model.toUpperCase().startsWith("W710i".toUpperCase())) {
        return "W710";
      } else if (model.toUpperCase().startsWith("W710c".toUpperCase())) {
        return "W710";
      } else if (model.toUpperCase().startsWith("W850c".toUpperCase())) {
        return "W850i";
      } else if (model.toUpperCase().startsWith("W880c".toUpperCase())) {
        return "W880i";
      } else if (model.toUpperCase().startsWith("Z610c".toUpperCase())) {
        return "Z610i";
      } else if (model.toUpperCase().startsWith("Z710i".toUpperCase())) {
        return "Z710";
      } else if (model.toUpperCase().startsWith("Z710c".toUpperCase())) {
        return "Z710";
      } else if (model.toUpperCase().startsWith("K750a".toUpperCase())) {
        return "K750c";
      } else if (model.toUpperCase().startsWith("K550c".toUpperCase())) {
        return "K550i";
      } else if (model.toUpperCase().startsWith("S700a".toUpperCase())) {
        return "S700";
      } else if (model.toUpperCase().startsWith("T226s".toUpperCase())) {
        return "T226";
      } else if (model.toUpperCase().startsWith("T61z".toUpperCase())) {
        return "T61";
      } else if (model.toUpperCase().startsWith("T62u".toUpperCase())) {
        return "T62";
      } else if (model.toUpperCase().startsWith("T68m".toUpperCase())) {
        return "T68i";
      } else if (model.toUpperCase().startsWith("V800i".toUpperCase())) {
        return "V800";
      } else if (model.toUpperCase().startsWith("W710a".toUpperCase())) {
        return "W710";
      } else if (model.toUpperCase().startsWith("W850a".toUpperCase())) {
        return "W850i";
      } else if (model.toUpperCase().startsWith("Z300c".toUpperCase())) {
        return "Z300i";
      } else if (model.toUpperCase().startsWith("Z500c".toUpperCase())) {
        return "Z500";
      } else if (model.toUpperCase().startsWith("Z500i".toUpperCase())) {
        return "Z500";
      } else if (model.toUpperCase().startsWith("Z550a".toUpperCase())) {
        return "Z550";
      }
    } else if (manufacturer.equalsIgnoreCase("Siemens")) {
      if (model.toUpperCase().startsWith("M65v".toUpperCase())) {
         return "M65";
      } else if (model.toUpperCase().startsWith("S35I".toUpperCase())) {
         return "S35";
      } else if (model.toUpperCase().startsWith("S65V".toUpperCase())) {
        return "S65";
      } else if (model.toUpperCase().startsWith("SL65Escada".toUpperCase())) {
        return "SL65";
      } else if (model.toUpperCase().startsWith("SX1McLaren".toUpperCase())) {
        return "SX1";
      }
    } else if (manufacturer.equalsIgnoreCase("Motorola")) {
      if (model.toUpperCase().startsWith("V360v".toUpperCase())) {
         return "V360";
      } else if (model.toUpperCase().startsWith("V557p".toUpperCase())) {
         return "V557";
      } else if (model.toUpperCase().startsWith("V8".toUpperCase())) {
        return "SLVR V8";
      } else if (model.toUpperCase().startsWith("V8v".toUpperCase())) {
        return "SLVR V8";
      } else if (model.toUpperCase().startsWith("V8 iTunes".toUpperCase())) {
        return "SLVR V8";
      } else if (model.toUpperCase().startsWith("V6".toUpperCase())) {
        return "RAZR V6";
      } else if (model.toUpperCase().startsWith("RAZRV6".toUpperCase())) {
        return "RAZR V6";
      } else if (model.toUpperCase().startsWith("V3i".toUpperCase())) {
        return "RAZR V3i";
      } else if (model.toUpperCase().startsWith("V3".toUpperCase())) {
        return "RAZR V3";
      } else if (model.toUpperCase().startsWith("E770v".toUpperCase())) {
        return "E770-Vodafone";
      } else if (model.toUpperCase().startsWith("SLVRL7".toUpperCase())) {
        return "SLVR L7";
      } else if (model.toUpperCase().startsWith("V3Razr".toUpperCase())) {
        return "RAZR V3";
      } else if (model.toUpperCase().startsWith("V3x".toUpperCase())) {
        return "RAZR V3x";
      } else if (model.toUpperCase().startsWith("V3xx".toUpperCase())) {
        return "RAZR V3x";
      } else if (model.toUpperCase().startsWith("V400p".toUpperCase())) {
        return "V400";
      } else if (model.toUpperCase().startsWith("V6PEBL".toUpperCase())) {
        return "PEBL V6";
      }
    } else if (manufacturer.equalsIgnoreCase("Sagem")) {
      if (model.toUpperCase().startsWith("MY400V".toUpperCase())) {
         return "my400X";
      } else if (model.toUpperCase().startsWith("MY401C".toUpperCase())) {
        return "my401X";
      } else if (model.toUpperCase().startsWith("MY401V".toUpperCase())) {
        return "my401X";
      } else if (model.toUpperCase().startsWith("MYX52".toUpperCase())) {
        return "myX-5-2T";
      }
    }

    return model;
  }

}
