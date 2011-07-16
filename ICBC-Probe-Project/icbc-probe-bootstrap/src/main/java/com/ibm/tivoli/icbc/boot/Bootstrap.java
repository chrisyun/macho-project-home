/**
 * 
 */
package com.ibm.tivoli.icbc.boot;

import java.io.File;
import java.lang.reflect.Method;

/**
 * @author Zhao Dong Lu
 * 
 */
public class Bootstrap {

  private static final String ICBC_PROBE_HOME = "icbc.probe.home";

  /**
   * 
   */
  public Bootstrap() {
    super();
  }

  public static String getIcbcHome() {
    return System.getProperty(ICBC_PROBE_HOME, System.getProperty("user.dir"));
  }


  public static void main(String[] args) throws Exception {
    // Configure tachyon.base from tachyon.home if not yet set
    if (System.getProperty(ICBC_PROBE_HOME) == null) {
      System.setProperty(ICBC_PROBE_HOME, getIcbcHome());
    }

    File probeHome = new File(System.getProperty(ICBC_PROBE_HOME));
    
    System.out.println("Using " + ICBC_PROBE_HOME + ": " + System.getProperty(ICBC_PROBE_HOME));
    
    File[] unpacked = new File[]{new File(probeHome, "config")};;
    File[] packed = new File[] { new File(probeHome, "lib"), new File(probeHome, "httpd/webapps/icbc-probe/WEB-INF/lib") };
    ClassLoader parentClassLoader = Bootstrap.class.getClassLoader();
    ClassLoader classLoader = ClassLoaderFactory.createClassLoader(unpacked, packed, parentClassLoader);

    Class clazz = classLoader.loadClass("com.ibm.tivoli.icbc.probe.server.ProbeServer");
    Object server = clazz.newInstance();
    
    Method startMethod = clazz.getMethod("start");
    startMethod.invoke(server);
  }

}
