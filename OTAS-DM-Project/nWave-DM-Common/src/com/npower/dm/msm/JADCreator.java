/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/msm/JADCreator.java,v 1.2 2009/02/26 09:14:20 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2009/02/26 09:14:20 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2009 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.msm;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.apache.commons.lang.StringUtils;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2009/02/26 09:14:20 $
 */
public class JADCreator {
  
  public class SoftwareInformation {
    String vendor = null;
    String name = null;
    String version = null;
    boolean midp2 = false;
    /**
     * 
     */
    public SoftwareInformation() {
      super();
    }
    /**
     * @param vendor
     * @param name
     * @param version
     * @param midp2
     */
    public SoftwareInformation(String vendor, String name, String version, boolean midp2) {
      super();
      this.vendor = vendor;
      this.name = name;
      this.version = version;
      this.midp2 = midp2;
    }
    /**
     * @return the vendor
     */
    public String getVendor() {
      return vendor;
    }
    /**
     * @param vendor the vendor to set
     */
    public void setVendor(String vendor) {
      this.vendor = vendor;
    }
    /**
     * @return the name
     */
    public String getName() {
      return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
      this.name = name;
    }
    /**
     * @return the version
     */
    public String getVersion() {
      return version;
    }
    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
      this.version = version;
    }
    /**
     * @return the midp2
     */
    public boolean isMidp2() {
      return midp2;
    }
    /**
     * @param midp2 the midp2 to set
     */
    public void setMidp2(boolean midp2) {
      this.midp2 = midp2;
    }
  }

  /**
   * 
   */
  public JADCreator() {
    super();
  }
  
  public Manifest getJADManufest(File file, String jarURL) throws IOException {
    //JarInputStream jarIn = new JarInputStream(in);
    JarFile jar = new JarFile(file);
    Manifest srcManifest = jar.getManifest();
    /*
    {
      Attributes attrs = srcManifest.getMainAttributes();
      for (Object akey: attrs.keySet()) {
          Object ov = attrs.get(akey);
          System.out.println(akey + ": " + ov);
      }
    }
    */
    Manifest manifest = new Manifest(srcManifest);
    manifest.getMainAttributes().put(new Attributes.Name("MIDlet-Jar-URL"), jarURL);
    manifest.getMainAttributes().put(new Attributes.Name("MIDlet-Jar-Size"), Long.toString(file.length()));
    return manifest;
  }
  
  public SoftwareInformation getSoftwareInformation(File file) throws IOException {
    JarFile jar = new JarFile(file);
    Manifest manifest = jar.getManifest();
    SoftwareInformation info = new SoftwareInformation();
    info.setVendor(manifest.getMainAttributes().getValue("MIDlet-Vendor"));
    info.setName(manifest.getMainAttributes().getValue("MIDlet-Name"));
    info.setVersion(manifest.getMainAttributes().getValue("MIDlet-Version"));
    String midpVersion = manifest.getMainAttributes().getValue("MicroEdition-Profile");
    if (StringUtils.isNotEmpty(midpVersion) && midpVersion.trim().equalsIgnoreCase("MIDP-2.0")) {
       info.setMidp2(true);
    }
    return info;
  }

}
