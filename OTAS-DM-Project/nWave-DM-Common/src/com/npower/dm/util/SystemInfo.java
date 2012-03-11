package com.npower.dm.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** @noinspection StringContatenationInLoop,MagicNumber */
public class SystemInfo {
  private static transient Log log = LogFactory.getLog(SystemInfo.class);

  static final long           MEGABYTE                 = 1048576L;

  private static final String dmCommonPropsFilename    = "otas-dm-common-release.properties";

  private static final String dmFrameworkPropsFilename = "otas-dm-framework-release.properties";

  private static final String dmServerPropsFilename    = "otas-dm-server-release.properties";

  private static final String dmWebAdminPropsFilename  = "otas-dm-webadmin-release.properties";

  private ServletContext      servletContext;

  private static Properties loadProperties(String filename) {
    Properties properties = new Properties();

    try {
        InputStream in = SystemInfo.class.getClassLoader().getResourceAsStream(filename);
        if (in != null) {
           properties.load(in);
           in.close();
        } else {
          log.error("Can't load " + filename + "!!!");
        }
    } catch (IOException ex) {
      log.error("error during " + filename + " property loading");
    }
    return properties;
  }

  public static Map<String, String> getSystemProperties() {
    Properties sysProps = System.getProperties();
    Map<String, String> props = new ListOrderedMap();
    props.put("System Date", DateFormat.getDateInstance().format(new Date()));
    props.put("System Time", DateFormat.getTimeInstance().format(new Date()));
    props.put("Current directory", getCurrentDirectory());

    props.put("Java Version", sysProps.getProperty("java.version"));
    props.put("Java Vendor", sysProps.getProperty("java.vendor"));
    props.put("JVM Version", sysProps.getProperty("java.vm.specification.version"));
    props.put("JVM Vendor", sysProps.getProperty("java.vm.specification.vendor"));
    props.put("JVM Implementation Version", sysProps.getProperty("java.vm.version"));
    props.put("Java Runtime", sysProps.getProperty("java.runtime.name"));
    props.put("Java VM", sysProps.getProperty("java.vm.name"));

    props.put("User Name", sysProps.getProperty("user.name"));
    props.put("User Timezone", sysProps.getProperty("user.timezone"));
    props.put("Charset", sysProps.getProperty("file.encoding"));

    props.put("Operating System", sysProps.getProperty("os.name") + " " + sysProps.getProperty("os.version"));
    props.put("OS Architecture", sysProps.getProperty("os.arch"));

    return props;
  }

  private static String getCurrentDirectory() {
    try {
      return new File(".").getCanonicalPath();
    } catch (IOException e) {
      // Should not happen
      return "<undefined>";
    }
  }

  public Map<String, String> getJVMStatistics() {
    Map<String, String> stats = new ListOrderedMap();
    stats.put("Total Memory", "" + getTotalMemory() + "MB");
    stats.put("Free Memory", "" + getFreeMemory() + "MB");
    stats.put("Used Memory", "" + getUsedMemory() + "MB");

    return stats;
  }

  public Map<String, String> getDMWebAdminBuildInfo() {
    Map<String, String> stats = new ListOrderedMap();
    Properties props = loadProperties(dmWebAdminPropsFilename);
    stats.put("Version", props.getProperty("Version"));
    stats.put("Build Date", props.getProperty("BuildDate"));
    stats.put("Build Time", props.getProperty("BuildTime"));
    stats.put("Build Revision", props.getProperty("BuildNumber"));

    return stats;
  }

  public Map<String, String> getDMCommonBuildInfo() {
    Map<String, String> stats = new ListOrderedMap();
    Properties props = loadProperties(dmCommonPropsFilename);
    stats.put("Version", props.getProperty("Version"));
    stats.put("Build Date", props.getProperty("BuildDate"));
    stats.put("Build Time", props.getProperty("BuildTime"));
    stats.put("Build Revision", props.getProperty("BuildNumber"));

    return stats;
  }

  public Map<String, String> getDMFrameworkBuildInfo() {
    Map<String, String> stats = new ListOrderedMap();
    Properties props = loadProperties(dmFrameworkPropsFilename);
    stats.put("Version", props.getProperty("Version"));
    stats.put("Build Date", props.getProperty("BuildDate"));
    stats.put("Build Time", props.getProperty("BuildTime"));
    stats.put("Build Revision", props.getProperty("BuildNumber"));

    return stats;
  }

  public Map<String, String> getDMServerBuildInfo() {
    Map<String, String> stats = new ListOrderedMap();
    Properties props = loadProperties(dmServerPropsFilename);
    stats.put("Version", props.getProperty("Version"));
    stats.put("Build Date", props.getProperty("BuildDate"));
    stats.put("Build Time", props.getProperty("BuildTime"));
    stats.put("Build Revision", props.getProperty("BuildNumber"));

    return stats;
  }
  
  public static String getVersionInfo() {
    Properties props = loadProperties(dmWebAdminPropsFilename);
    StringBuffer buffer = new StringBuffer();
    buffer.append("Version ");    
    buffer.append(props.getProperty("Version"));    
    buffer.append(" build ");    
    buffer.append(props.getProperty("BuildDate"));    
    buffer.append(" (rev ");    
    buffer.append(props.getProperty("BuildNumber"));    
    buffer.append(")");
    return buffer.toString();
  }

  public Map<String, String> getDatabaseInfo() {
    Map<String, String> props = new ListOrderedMap();
    //props.put("Dialect", properties.getProperty("hibernate.dialect"));
    //props.put("Driver", properties.getProperty("hibernate.connection.driver_class"));
    // props.put("Driver Version", getDriverVersion());
    // props.put("Database Vendor", getDatabaseVendor());
    // props.put("Database Version", getDatabaseVersion());
    //props.put("Database Name", properties.getProperty("hibernate.connection.dbname"));
    props.put("Database Url", "");
    //props.put("Database User Name", properties.getProperty("hibernate.connection.username"));
    //props.put("Database User Password", isEmpty(properties.getProperty("hibernate.connection.password")) ? "[not set]": "******");

    // props.put("Database Patch Level", getDatabasePatchLevel());
    return props;
  }

  /*
   * private String getDatabaseVendor() {
   * 
   * return (String)new HibernateTemplate(sessionFactory).execute(new
   * HibernateCallback() { public Object doInHibernate(Session session) throws
   * HibernateException, SQLException { return
   * session.connection().getMetaData().getDatabaseProductName(); } }); }
   * 
   * private String getDatabaseVersion() { return (String)new
   * HibernateTemplate(sessionFactory).execute(new HibernateCallback() { public
   * Object doInHibernate(Session session) throws HibernateException,
   * SQLException { return
   * session.connection().getMetaData().getDatabaseProductVersion(); } }); }
   * 
   * private String getDriverVersion() { return (String)new
   * HibernateTemplate(sessionFactory).execute(new HibernateCallback() { public
   * Object doInHibernate(Session session) throws HibernateException,
   * SQLException { return
   * session.connection().getMetaData().getDriverVersion(); } }); }
   * 
   * private static String getDatabasePatchLevel() { //noinspection
   * CatchGenericClass,OverlyBroadCatchBlock try { AutopatchSupport
   * autopatchSupport = new AutopatchSupport(new
   * XPlannerMigrationLauncherFactory(),
   * XPlannerMigrationLauncherFactory.SYSTEM_NAME); return "" +
   * autopatchSupport.getPatchLevel(); } catch (Exception e) { return "Unknown
   * (Exception during retrieval: " + e.getMessage() + ")"; } }
   */
  public Map<String, String> getAppServerInfo() {
    Map<String, String> props = new ListOrderedMap();
    props.put("Application Server", servletContext.getServerInfo());
    props.put("Servlet Version", servletContext.getMajorVersion() + "." + servletContext.getMinorVersion());

    return props;
  }

  public long getTotalMemory() {
    return Runtime.getRuntime().totalMemory() / MEGABYTE;
  }

  public long getFreeMemory() {
    return Runtime.getRuntime().freeMemory() / MEGABYTE;
  }

  public long getUsedMemory() {
    return getTotalMemory() - getFreeMemory();
  }

  public void setServletContext(ServletContext servletContext) {
    this.servletContext = servletContext;
    log.info("*********************** OTAS DM INFO ************************\n" + toString());
  }

  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append(propertiesMapToString("Build", getDMWebAdminBuildInfo()));
    buf.append(propertiesMapToString("Database", getDatabaseInfo()));
    buf.append(propertiesMapToString("App Server", getAppServerInfo()));
    buf.append(propertiesMapToString("System", getSystemProperties()));

    return buf.toString();
  }

  private static String propertiesMapToString(String mapName, Map<String, String> properties) {
    StringBuffer buf = new StringBuffer();
    buf.append(mapName);
    buf.append(":\n");

    Iterator<String> iterator = properties.keySet().iterator();

    while (iterator.hasNext()) {
      String name = (String) iterator.next();
      String value = (String) properties.get(name);
      buf.append("   ");
      buf.append(StringUtils.rightPad(name + ":", 30));
      buf.append(value).append("\n");
    }

    buf.append("\n");

    return buf.toString();
  }
}
