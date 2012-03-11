/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/wurfl/Wurfl.java,v 1.3 2007/11/14 06:18:55 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2007/11/14 06:18:55 $
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Comment;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.ParsingException;
import nu.xom.Serializer;
import nu.xom.ValidityException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Luca Passani (passani@eunet.no)
 * 
 * Wurfl class initializes by parsing the WURFL and capturing a bunch of info
 * about capabilities, devices and UA strings into convenience HashMaps,
 * Arraylists and so on. THIS ASSUMES WURFL is CORRECT. Uncorrect Wurfl will
 * produce unpredictable results. Use utility CheckWURFL.java to validate your
 * WURFL file
 * 
 * @version $Revision: 1.3 $ $Date: 2007/11/14 06:18:55 $
 */
public class Wurfl {

  private static Log log = LogFactory.getLog(Wurfl.class);
  
  // root parsed WURFL
  Document                                   wurflDocument               = null;

  // Hastables and lists used internally by the WURFL
  private Elements                           devices                     = null;                            // array

  // of all
  // devices

  int                                        numberOfDevices             = 0;

  private HashSet<String>                    deviceIdSet                 = null;                            // for

  // fast
  // Device
  // ID
  // lookup

  private HashMap<String, Element>           deviceElementsList          = null;                            // for

  // fast
  // access
  // to
  // device
  // elements

  private TreeMap<String, Element>           actualDeviceElementsList    = null;                            // for

  // fast
  // access
  // to
  // device
  // elements

  private HashSet<String>                    wurfl_actual_device_roots;                                     // Actual

  // Device
  // Roots

  private HashMap<String, String>            listOfUAWithDeviceID        = null;                            // associate

  // UA
  // string
  // with
  // device
  // ID

  private HashMap<String, ArrayList<String>> listOfGroups                = null;                            // HashMap

  // of
  // Arraylist
  // (capabilities
  // grouped
  // by
  // 'group')

  private ArrayList<String>                  listOfCapabilities          = new ArrayList<String>(350);

  private HashSet<String>                    setOfCapabilityNames        = null;

  int                                        numberOfCapabilities        = 0;

  private HashMap<String, String>            genericCapabilityNameValues = new HashMap<String, String>(350);

  // patch file
  // private boolean patchfile_present = false;

  // private boolean patchfile_found = false;

  // private Element patch_root = null;

  /**
   * Initializes the WURFL fields.
   * 
   * @param wurflStream
   *            InputStream containing WURFL
   * @param patchStream
   *            InputStream containing the patch file (can be null)
   * 
   */
  private void init(InputStream wurflStream, InputStream patchStream) {

    try {
      Builder parser = new Builder();
      wurflDocument = parser.build(wurflStream);

      if (patchStream != null) {
        boolean patch_successful = true;
        Document patchedWurflDocument = (Document) wurflDocument.copy();
        try {
          // Builder patch_parser = new Builder();
          Document patchDocument = parser.build(patchStream);
          Element patch_root = patchDocument.getRootElement();
          Element wurfl_root = patchedWurflDocument.getRootElement();

          String localname = patch_root.getLocalName();
          if (!localname.equals("wurfl_patch")) {
            throw new WurflException("patch file must have <wurfl_patch> as root tag");
          }

          // if there is a patch file, we parse the WURFL normally
          // then we modify the object model.

          ArrayList<String> wurfl_listOfCapabilities = new ArrayList<String>(250);
          HashMap<String, String> wurfl_genericCapabilityNameValues = new HashMap<String, String>(250);
          // HashSet wurfl_setOfCapabilityNames = null;
          HashSet<String> wurfl_actual_device_roots = new HashSet<String>();

          int wurfl_numberOfCapabilities = 0;
          Element devices_elem = wurfl_root.getFirstChildElement("devices");
          Elements wurfl_devices = devices_elem.getChildElements("device");
          int wurfl_numberOfDevices = wurfl_devices.size();
          // initialize arrayLists and HashMaps
          HashSet<String> wurfl_deviceIdSet = new HashSet<String>(wurfl_numberOfDevices);
          HashMap<String, Element> wurfl_deviceElementsList = new HashMap<String, Element>(6000, 0.75f);
          HashMap<String, String> wurfl_listOfUAWithDeviceID = new HashMap<String, String>(6000, 0.75f);

          for (int j = 0; j < wurfl_numberOfDevices; j++) {
            String _devID = wurfl_devices.get(j).getAttributeValue("id");
            // String _devFallBack =
            // wurfl_devices.get(j).getAttributeValue("fall_back");
            String _devUA = wurfl_devices.get(j).getAttributeValue("user_agent");
            wurfl_deviceElementsList.put(_devID, wurfl_devices.get(j));
            wurfl_deviceIdSet.add(_devID);
            if ("true".equals(wurfl_devices.get(j).getAttributeValue("actual_device_root")))
              wurfl_actual_device_roots.add(_devID);
            wurfl_listOfUAWithDeviceID.put(_devUA, _devID);
          }

          // find capabilities (no checks because of basic assumption that WURFL
          // is correct)
          Element wurfl_genericElement = (Element) wurfl_deviceElementsList.get("generic");
          Elements wurfl_groups = wurfl_genericElement.getChildElements("group");

          // extra list to keep capabilities grouped by group
          HashMap<String, ArrayList<String>> wurfl_listOfGroups = new HashMap<String, ArrayList<String>>(wurfl_groups
              .size(), 1);

          for (int i = 0; i < wurfl_groups.size(); i++) {
            Elements wurfl_capaList = wurfl_groups.get(i).getChildElements("capability");
            // group by group function
            ArrayList<String> tmp_capalist = new ArrayList<String>(wurfl_capaList.size());

            wurfl_numberOfCapabilities += wurfl_capaList.size();
            for (int j = 0; j < wurfl_capaList.size(); j++) {
              Element capa = wurfl_capaList.get(j);
              wurfl_listOfCapabilities.add(capa.getAttributeValue("name"));
              tmp_capalist.add(capa.getAttributeValue("name"));
              wurfl_genericCapabilityNameValues.put(capa.getAttributeValue("name"), capa.getAttributeValue("value"));
            }
            wurfl_listOfGroups.put(wurfl_groups.get(i).getAttributeValue("id"), tmp_capalist);
          }

          // optimization to speed things up later: look-up in O(1) NOT O(n)
          // wurfl_setOfCapabilityNames = new HashSet(listOfCapabilities);

          /*
           * Start analyzing the patch file The object model of wurfl-patch.xml
           * is traversed to enrich the existing object model in wurfl.xml We
           * need to do a bunch of checks here because the patch is not
           * validated as the WURFL
           */
          Element devices_tag_patch = patch_root.getFirstChildElement("devices");
          if (devices_tag_patch == null) {
            String msg = "Illegal syntax patch file: <devices> tag is missing!";
            patch_parse_error(msg);
          }
          Elements patch_devices = devices_tag_patch.getChildElements("device");
          // loop through all devices in patch file
          for (int j = 0; j < patch_devices.size(); j++) {
            // log.debug("--------------------------------------");
            Element current_patch_device = patch_devices.get(j);
            String devID = current_patch_device.getAttributeValue("id");
            // log.debug("analyzing device "+devID);
            Element current_wurfl_device = (Element) wurfl_deviceElementsList.get(devID);

            String fallback = current_patch_device.getAttributeValue("fall_back");
            // soma basic checks on the consistency of each device
            if (fallback == null || fallback.equals("")) {
              String msg = "device " + devID + " in patch file does not have" + " a valid fallback value";
              patch_parse_error(msg);
            }
            String ua = current_patch_device.getAttributeValue("user_agent");
            if (ua == null || (ua.equals("") && !devID.equals("generic"))) {
              String msg = "device " + devID + " in patch file does not have" + " a valid user_agent string";
              patch_parse_error(msg);
            }

            // existing device or NEW Device?
            if (wurfl_deviceIdSet.contains(devID)) {
              log.debug("existing device.");
              // modify existing device
              if (!devID.equals("generic")) {
                // first a bunch of basic checks
                // Disallow modification of UA (but not fall_back)
                String local_fallback = current_wurfl_device.getAttributeValue("fall_back");
                if (!fallback.equals(local_fallback)) {
                  Attribute fb = new Attribute("fall_back", local_fallback);
                  current_wurfl_device.addAttribute(fb);
                }
                String local_ua = current_wurfl_device.getAttributeValue("user_agent");
                if (!ua.equals(local_ua)) {
                  String msg = "device " + devID + ". Sorry. Patch file devices are not allowed"
                      + " to override user-agent. If you need to do that, please define a new device"
                      + " in the patch file.";
                  patch_parse_error(msg);
                }

                if ("true".equals(current_patch_device.getAttributeValue("actual_device_root"))) {
                  wurfl_actual_device_roots.add(devID);
                }

              }

              // need an array to test if group exists from before or not
              Elements wurfl_element_groups = current_wurfl_device.getChildElements("group");
              ArrayList<String> wurfl_element_groups_set = new ArrayList<String>(wurfl_element_groups.size());
              for (int k = 0; k < wurfl_element_groups.size(); k++) {
                wurfl_element_groups_set.add(wurfl_element_groups.get(k).getAttributeValue("id"));
              }

              // loop through all the groups
              log.debug("Need to merge groups ");
              Elements local_groups = current_patch_device.getChildElements("group");
              for (int k = 0; k < local_groups.size(); k++) {
                Element local_group = local_groups.get(k);
                String local_group_id = local_group.getAttributeValue("id");
                log.debug("group "+local_group_id);
                if (local_group_id == null || local_group_id.equals("")) {
                  String msg = "patch file, device " + devID + ": group without ID";
                  patch_parse_error(msg);
                }
                if (!wurfl_element_groups_set.contains(local_group_id)) {
                  log.debug("group "+local_group_id+" is not in the "+devID+" device.");
                  // new group, attach as is
                  Node tmp_group = local_group.copy();
                  current_wurfl_device.appendChild(tmp_group);
                } else {
                  // existing group. Merge capabilities
                  log.debug("group "+local_group_id+" IS in the " + devID + " device.");
                  log.debug("We need to merge...");
                  Element wurfl_group = null;
                  for (int h = 0; h < wurfl_element_groups.size(); h++) {
                    log.debug("Comparing "+wurfl_element_groups.get(h).getAttributeValue("id")
                     +" and "+local_group.getAttributeValue("id"));

                    if (wurfl_element_groups.get(h).getAttributeValue("id").equals(local_group_id)) {
                      wurfl_group = wurfl_element_groups.get(h);
                      log.debug("Found"+wurfl_element_groups.get(h).getAttributeValue("id"));
                    }
                  }
                  log.debug("Merging "+wurfl_group.getAttributeValue("id")
                     +" and "+local_group.getAttributeValue("id"));
                  merge_group_capabilities(wurfl_group, local_group);
                }
              } // end for loop

            } else {
              log.debug("New device...just copy");
              Element dev_copy = (Element) current_patch_device.copy();
              devices_elem.appendChild(dev_copy);
            }

          }

        } catch (Exception e) {
          log.error(e.getMessage());
          patch_successful = false;
        }
        if (patch_successful) {
          log.info("Patching OK. Applied");
          // try {toPrettyXML(patchedWurflDocument,System.out);} catch
          // (Exception e) {}
          wurflDocument = patchedWurflDocument;
        } else {
          log.info("Patching failed; reverting to origional wurfl");
        }
      } // end of 'if (patchstream != null)'

      Element devices_elem = wurflDocument.getRootElement().getFirstChildElement("devices");

      // At this point, patchfile or not, we have a complete object model
      // of the wurfl.xml file. Let's build the ArrayLst and HashMaps for fast
      // look-up
      // by the API

      // Now it's time to re-visit the modified object model
      devices = devices_elem.getChildElements("device");
      numberOfDevices = devices.size();
      // initialize arrayLists and HashMaps
      deviceIdSet = new HashSet<String>(numberOfDevices);
      deviceElementsList = new HashMap<String, Element>(6000, 0.75f);
      listOfUAWithDeviceID = new HashMap<String, String>(6000, 0.75f);
      wurfl_actual_device_roots = new HashSet<String>(numberOfDevices);
      for (int j = 0; j < numberOfDevices; j++) {
        String _devID = devices.get(j).getAttributeValue("id");
        // String _devFallBack = devices.get(j).getAttributeValue("fall_back");
        String _devUA = devices.get(j).getAttributeValue("user_agent");
        deviceElementsList.put(_devID, devices.get(j));
        deviceIdSet.add(_devID);
        listOfUAWithDeviceID.put(_devUA, _devID);
        if ("true".equals(devices.get(j).getAttributeValue("actual_device_root")))
          wurfl_actual_device_roots.add(_devID);
      }

      // find capabilities (no checks because of basic assumption that WURFL is
      // correct)
      Element genericElement = (Element) deviceElementsList.get("generic");

      Elements groups = genericElement.getChildElements("group");

      // extra list to keep capabilities grouped by group
      listOfGroups = new HashMap<String, ArrayList<String>>(groups.size(), 1);
      listOfCapabilities = new ArrayList<String>(350);
      genericCapabilityNameValues = new HashMap<String, String>(350);
      for (int i = 0; i < groups.size(); i++) {
        Elements capaList = groups.get(i).getChildElements("capability");
        // group by group function
        ArrayList<String> tmp_capalist = new ArrayList<String>(capaList.size());

        numberOfCapabilities += capaList.size();
        for (int j = 0; j < capaList.size(); j++) {
          Element capa = capaList.get(j);
          listOfCapabilities.add(capa.getAttributeValue("name"));
          tmp_capalist.add(capa.getAttributeValue("name"));
          genericCapabilityNameValues.put(capa.getAttributeValue("name"), capa.getAttributeValue("value"));
        }
        listOfGroups.put(groups.get(i).getAttributeValue("id"), tmp_capalist);
      }

      // Tidy up our Lists.
      listOfCapabilities.trimToSize();

      // optimization to speed things up later: look-up in O(1) NOT O(n)
      setOfCapabilityNames = new HashSet<String>(listOfCapabilities);
      log.info("WURFL has been initialized");

    } catch (ValidityException ex) {
      System.err.println("WURFL is not valid");
      ex.printStackTrace();
      throw new WurflException("WURFL is not valid");
    } catch (ParsingException ex) {
      System.err.println("cannot parse the wurfl");
      ex.printStackTrace();
      throw new WurflException("cannot parse WURFL");
    } catch (IOException ioe) {
      System.err.println("problems reading the stream");
      ioe.printStackTrace();
      throw new WurflException("problems reading the stream");
    }
  }

  /**
   * Constructor with two InputStreams
   * 
   * @param wurflStream
   * @param patchStream
   * 
   */

  public Wurfl(InputStream wurflStream, InputStream patchStream) {
    init(wurflStream, patchStream);
  }

  /**
   * Constructor with two Files representing WURFL and patch
   * 
   * @param wurfl
   * @param patch
   */
  public Wurfl(File wurflFile, File patchFile) {
    try {
      InputStream wurfl = new FileInputStream(wurflFile);
      InputStream patch = new FileInputStream(patchFile);
      init(wurfl, patch);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new WurflException(e.getMessage());
    }
  }

  /**
   * Constructor with two Strings representing locations of WURFL and patch
   * 
   * @param fileName
   * @param patchfile
   * 
   */
  public Wurfl(String fileName, String patchfile) {
    try {
      // in case patchfile is empty, have a look if wurfl_patch.xml
      // is in the same directory as wurfl.xml
      if (patchfile == null || patchfile.equals("")) {
        log.info("trying to see if we can figure out the patch file");
        if (fileName.matches("(.*)wurfl\\.xml$")) {
          int fc = fileName.indexOf("wurfl.xml");
          String dir = fileName.substring(0, fc);
          String _patchfile = dir + "wurfl_patch.xml";

          File f = new File(_patchfile);
          if (f.exists() && f.canRead()) {
            log.info("potential patchfile: " + _patchfile);
            patchfile = _patchfile;
          } else {
            log.info("potential patchfile: " + _patchfile + " does not exist, or is not readable");
          }
        }
      }
      InputStream wurfl = new FileInputStream(new File(fileName));
      InputStream patch = null;
      if (patchfile != null && !patchfile.equals("")) {
        patch = new FileInputStream(new File(patchfile));
      }
      init(wurfl, patch);

    } catch (Exception e) {
      log.error("Unable to prepare WURFL", e);
    }
  }

  /**
   * Constructor taking a single filename
   * 
   * @param filename
   */
  public Wurfl(String filename) {
    this(filename, "");
  }

  // some utilities
  boolean isDeviceIn(String devID) {
    return deviceIdSet.contains(devID);
  }

  boolean isCapabilityIn(String capaName) {
    return setOfCapabilityNames.contains(capaName);
  }

  String getFallBackForDevice(String devID) {

    if (!isDeviceIn(devID)) {
      log.error("ERROR: device *" + devID + "* does not exist in WURFL");
      return "generic";
    } else {
      if (devID.equals("generic")) {
        return "generic";
      } else {
        String fb_str = ((Element) deviceElementsList.get(devID)).getAttributeValue("fall_back");
        if (fb_str == null || fb_str.equals("")) {
          log.error("ERROR: wurfl data is not valid. \"fall_back\" for device *" + devID
              + "* non-existent or not found.");
          return "generic";
        }
        return fb_str;
      }
    }
  }

  /*
   * Added to make Tom Hume from Future Platforms happy My suggestion is to use
   * capabilities and patch file for operations that one thinks might require a
   * query of the WURFL hierarchy
   */
  boolean isDescendentOf(String descendent, String ancestor) {
    String fb = getFallBackForDevice(descendent);
    if (fb.equals(ancestor)) {
      return true;
    } else {
      if (fb.equals("generic") || fb.equals("")) {
        return false;
      }
      return isDescendentOf(fb, ancestor);
    }
  }

  ArrayList<String> getFallBackPathToRoot(String devID) {

    ArrayList<String> al = new ArrayList<String>(10);
    if (devID.equals("generic")) {
      al.add("generic");
      return al;
    }

    if (!isDeviceIn(devID)) {
      log.error("ERROR: Device ID " + devID + " not defined in WURFL (getFallBackPathToRoot()).");
      return al;
    }

    String looper = devID;
    while (!looper.equals("generic")) {
      al.add(looper);
      looper = getFallBackForDevice(looper);
    }
    al.add("generic");
    return al;
  }

  boolean isCapabilityDefinedInDevice(String devID, String capaName) {

    if (!isDeviceIn(devID)) {
      log.error("ERROR: Device ID " + devID + " not defined in WURFL.(isCapabilityDefinedInDevice())");
      return false;
    }
    if (!isCapabilityIn(capaName)) {
      log.error("ERROR: capability " + capaName + " not defined in WURFL.(isCapabilityDefinedInDevice())");
      return false;
    }

    Element el = (Element) deviceElementsList.get(devID);
    Elements groups = el.getChildElements("group");
    if (groups.size() == 0) {
      return false;
    }

    Elements capaList = null;
    for (int i = 0; i < groups.size(); i++) {
      capaList = groups.get(i).getChildElements("capability");
      if (capaList.size() == 0) {
        continue;
      }
      for (int j = 0; j < capaList.size(); j++) {
        if (capaList.get(j).getAttributeValue("name").equals(capaName)) {
          return true;
        }
      }
    }
    return false;
  }

  String getDeviceWhereCapabilityIsDefined(String devID, String capaName) {

    if (!isDeviceIn(devID)) {
      log.error("ERROR: Device ID " + devID + " not defined in WURFL.(getDeviceWhereCapabilityIsDefined())");
      return "";
    }
    if (!isCapabilityIn(capaName)) {
      log.error("ERROR: capability " + capaName
          + " not defined in WURFL.(getDeviceWhereCapabilityIsDefined())");
      return "";
    }

    if (isCapabilityDefinedInDevice(devID, capaName)) {
      return devID;
    }

    // I know the application assumes that the WURFL is valid,
    // but since a little glitch can go a long way, we do what we can to avoid
    // infinite loops
    String looper = devID;
    int i = 0;
    ArrayList<String> fb_path = new ArrayList<String>();
    while (!isCapabilityDefinedInDevice(looper, capaName)) {
      looper = getFallBackForDevice(looper);
      i++;
      fb_path.add(looper);
      if (i > 100) {
        log.error("ERROR: WURFL file is probably not valid. Infinite-loop detected:");
        log.error("loop of device IDs: " + fb_path.toString());
        return "generic";
      }
    }

    return looper; // of course, it assumes that generic has all the
    // capabilities
  }

  String getCapabilityValueForDeviceAndCapability(String devID, String capaName) {

    if (!isDeviceIn(devID)) {
      log.error("ERROR: Device ID " + devID + " not defined in WURFL.(isCapabilityDefinedInDevice())");
      return "";
    }
    if (!isCapabilityIn(capaName)) {
      log.error("ERROR: capability " + capaName + " not defined in WURFL.(isCapabilityDefinedInDevice())");
      return "";
    }

    // this is just to speed things up
    if (devID.equals("generic")) {
      return (String) genericCapabilityNameValues.get(capaName);
    }

    String devWithCapabilityDefinition = getDeviceWhereCapabilityIsDefined(devID, capaName);
    Element el = (Element) deviceElementsList.get(devWithCapabilityDefinition);

    // we need to scan all the capas
    Elements groups = el.getChildElements("group");
    Elements capaList = null;
    for (int i = 0; i < groups.size(); i++) {
      capaList = groups.get(i).getChildElements("capability");
      if (capaList.size() == 0) {
        continue;
      } // necessary?
      for (int j = 0; j < capaList.size(); j++) {
        if (capaList.get(j).getAttributeValue("name").equals(capaName)) {
          return capaList.get(j).getAttributeValue("value");
        }
      }
    }

    // we should never get there if WURFL is correct
    System.out
        .println("WURFL is not VALID! Unfortunately, the library was unable to determine the origin of the error.");
    return "";
  }

  // Retrieve Device ID through UA (strict: only exact matches)
  public String getDeviceIDFromUA(String ua) {

    String devID = listOfUAWithDeviceID.get(ua);
    if (devID == null) {
      return "generic";
    } else {
      return devID;
    }
  }

  // Retrieve Device ID through UA (loose: longest match)
  // for ex: "MOT-T720/G_05.01.43R" will match
  // "MOT-T720/G_05.01.43R MIB/2.0 Profile/MIDP-1.0 Configuration/CLDC-1.0"
  // and the "mot_t720_ver1_subg050143r" Device ID will be returned
  // This is more than a substring match. The UA string is made
  // progressively shorter and a new match is attempted each time.
  // The purpose is to match devices which are not available in the WURFL
  // but may have user agents (and capabilities) similar to the ones
  // of WURFL devices.
  public String getDeviceIDFromUALoose(String ua) {

    String key;

    if (ua.length() == 0) {
      return "generic";
    }

    // giving it a try ;)
    Object strictUA = listOfUAWithDeviceID.get(ua);
    if (strictUA != null) {
      return (String) strictUA;
    }

    String uaSub = ua;

    // let's try to match on progressively shorter UAs
    while (uaSub.length() > 5) {
      Iterator<String> keys = listOfUAWithDeviceID.keySet().iterator();
      while (keys.hasNext()) {
        key = (String) keys.next();
        if (key.indexOf(uaSub) != -1) {
          return (String) listOfUAWithDeviceID.get(key);
        }
      }
      uaSub = uaSub.substring(0, uaSub.length() - 1);
    }

    // Nasty habit by Vodafone to add their f***ing brand
    // everwhere. Fix it and make sure that sys-admins know that
    // Voda sucks
    if (ua.startsWith("Vodafone/")) {
      log.info("Voda added their f***ing brand to the " + "following user-agent string: " + ua);
      // log.error("***** Vodafone sucks! *******");
      String clean_ua = ua.substring(9, ua.length());
      String temp_res = getDeviceIDFromUALoose(clean_ua);
      if (!"generic".equals(temp_res)) {
        return temp_res;
      }
    }

    // before we give up and return generic, one last
    // attempt to catch well-behaved Nokia and Openwave browsers!
    if (ua.indexOf("UP.Browser/7") != -1) {
      return "opwv_v7_generic";
    }
    if (ua.indexOf("UP.Browser/6") != -1) {
      return "opwv_v6_generic";
    }
    if (ua.indexOf("UP.Browser/5") != -1) {
      return "upgui_generic";
    }
    if (ua.indexOf("UP.Browser/4") != -1) {
      return "uptext_generic";
    }
    if (ua.indexOf("Series60") != -1) {
      return "nokia_generic_series60";
    }
    // web browsers?
    if (ua.indexOf("Mozilla/4.0") != -1) {
      return "generic_web_browser";
    }
    if (ua.indexOf("Mozilla/5.0") != -1) {
      return "generic_web_browser";
    }
    if (ua.indexOf("Mozilla/6.0") != -1) {
      return "generic_web_browser";
    }
    return "generic";
  }

  // patch utility methods
  private void patch_parse_error(String msg) {
    log.error("Fatal error in parsing wurfl patch file: " + msg);
    throw new WurflException(msg);
  }

  private void merge_group_capabilities(Element wurfl_el, Element patch_el) {

    Elements patch_capas = patch_el.getChildElements("capability");
    Elements wurfl_capas = wurfl_el.getChildElements("capability");
    for (int k = 0; k < patch_capas.size(); k++) {
      Element patch_capa = patch_capas.get(k);
      String patch_capaname = patch_capa.getAttributeValue("name");
      boolean found = false;
      Element wurfl_capa = null;
      for (int i = 0; i < wurfl_capas.size(); i++) {
        wurfl_capa = wurfl_capas.get(i);
        String wurfl_capaname = wurfl_capa.getAttributeValue("name");
        if (wurfl_capaname.equals(patch_capaname)) {
          log.debug("Found "+wurfl_capaname+" in both groups "+wurfl_el.getAttributeValue("id"));
          found = true;
          break;
        }
      }
      if (found) {
        // capa exists from before. Remove and replace with one in patch
        log.debug("need to replace...remove "+wurfl_capa.toXML());
        wurfl_el.removeChild(wurfl_capa);
        log.debug("add "+patch_capa.toXML());
        wurfl_el.appendChild(patch_capa.copy());
      } else {
        // capa not found. Simply append
        log.debug("No need to replace. Just add "+patch_capa.toXML());
        wurfl_el.appendChild(patch_capa.copy());
      }

    }
  }

  public String toXML() {
    if (wurflDocument != null) {
      try {
        return toPrettyXML(wurflDocument);
      } catch (Exception e) {
        return "<sorry>WURFL has not been parsed yet!</sorry>";
      }
    } else {
      return "<sorry>WURFL has not been parsed yet!</sorry>";
    }
  }

  public static void toPrettyXML(Document doc, OutputStream out) throws Exception {
    Serializer serializer = new Serializer(out);
    serializer.setIndent(2);
    serializer.setMaxLength(200);
    serializer.setPreserveBaseURI(false);
    serializer.write(doc);
    serializer.flush();
    out.close();
  }

  public static String toPrettyXML(Document doc) throws Exception {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    toPrettyXML(doc, out);
    return out.toString();
  }

  /* Filter out capabilities that are not required */
  public void filterCapabilities(HashSet<String> capaAcceptedList, OutputStream myout) {

    if (wurflDocument == null) {
      return;
    }
    Document tmpWurflDocument = (Document) wurflDocument.copy();
    // cleaning. remove version element
    Element version = tmpWurflDocument.getRootElement().getFirstChildElement("version");
    if (version != null) {
      tmpWurflDocument.getRootElement().removeChild(version);
    }

    Element devices_elem = tmpWurflDocument.getRootElement().getFirstChildElement("devices");

    // more cleaning. remove all comments
    for (int h = 0; h < devices_elem.getChildCount(); h++) {
      Node child = devices_elem.getChild(h);
      if (child instanceof Comment) {
        devices_elem.removeChild(child);
      }
    }

    Elements devices = devices_elem.getChildElements("device");
    // int numberOfDevices = devices.size();

    for (int k = 0; k < devices.size(); k++) {
      Element device = devices.get(k);
      // some cleaning. remove all comments
      for (int h = 0; h < device.getChildCount(); h++) {
        Node child = device.getChild(h);
        if (child instanceof Comment) {
          device.removeChild(child);
        }
      }
      Elements groups = device.getChildElements("group");

      // extra list to keep capabilities grouped by group
      for (int i = 0; i < groups.size(); i++) {
        Element group = groups.get(i);

        // some cleaning. remove all comments
        for (int h = 0; h < group.getChildCount(); h++) {
          Node child = group.getChild(h);
          if (child instanceof Comment) {
            group.removeChild(child);
          }
        }
        Elements capaList = group.getChildElements("capability");
        for (int j = 0; j < capaList.size(); j++) {
          Element capa = capaList.get(j);
          String capa_name = capa.getAttributeValue("name");
          if (!capaAcceptedList.contains(capa_name)) {
            // remove capability
            group.removeChild(capa);
          }
        }
        // if group has no capability, remove group too
        if (0 == group.getChildElements("capability").size()) {
          device.removeChild(group);
        }
      }
    }
    try {
      toPrettyXML(tmpWurflDocument, myout);
    } catch (Exception e) {
      new WurflException("Error producing filtered WURFL:" + e.getMessage());
    }
  }

  // GETTERs list

  /**
   * Returns the deviceIdSet.
   * 
   * @return HashSet
   */
  public HashSet<String> getDeviceIdSet() {
    return deviceIdSet;
  }

  /**
   * Returns the listOfUAWithDevice.
   * 
   * @return HashMap
   */

  public HashMap<String, String> getListOfUAWithDeviceID() {
    return listOfUAWithDeviceID;
  }

  /**
   * Returns the numberOfDevices.
   * 
   * @return int
   */
  public int getNumberOfDevices() {
    return numberOfDevices;
  }

  /**
   * Returns the deviceElementsList.
   * 
   * @return HashMap
   */
  public HashMap<String, Element> getDeviceElementsList() {
    return deviceElementsList;
  }

  /**
   * Returns the actualDeviceElementsList.
   * 
   * @return TreeMap
   */
  public TreeMap<String, Element> getActualDeviceElementsList() {

    if (actualDeviceElementsList == null) {

      actualDeviceElementsList = new TreeMap<String, Element>();
      // we need to find the list of devices which represent actual devices
      Iterator<String> keys = deviceElementsList.keySet().iterator();
      while (keys.hasNext()) {
        String key = keys.next();
        Element el = deviceElementsList.get(key);
        String act_dev = el.getAttributeValue("actual_device_root");
        if (act_dev != null && act_dev.equals("true")) {
          log.debug("Trovato device: "+key);
          actualDeviceElementsList.put(key, el);
        }
      }
    }
    return actualDeviceElementsList;
  }

  /**
   * Returns the listOfCapabilities.
   * 
   * @return ArrayList
   */
  public ArrayList<String> getListOfCapabilities() {
    return listOfCapabilities;
  }

  /**
   * Returns the numberOfCapabilities.
   * 
   * @return int
   */
  public int getNumberOfCapabilities() {
    return numberOfCapabilities;
  }

  /**
   * Returns HashMap of ArrayList (capabilities grouped by group name (id)).
   * 
   * @return HashMap
   */
  public HashMap<String, ArrayList<String>> getListOfGroups() {
    return listOfGroups;
  }

  /**
   * Returns the devices.
   * 
   * @return Elements
   */
  public Elements getDevices() {
    return devices;
  }

  /**
   * Returns the setOfCapabilityNames.
   * 
   * @return HashSet
   */
  public HashSet<String> getSetOfCapabilityNames() {
    return setOfCapabilityNames;
  }

}
