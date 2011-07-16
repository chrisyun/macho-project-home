/**
 * 
 */
package com.ibm.tivoli.icbc.probe.http;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.helpers.IOUtils;

import com.ibm.tivoli.icbc.result.dns.LookupEntity;
import com.ibm.tivoli.icbc.result.dns.cname.DNSCNameResult;
import com.ibm.tivoli.icbc.result.dns.ns.DNSResult4NS;
import com.ibm.tivoli.icbc.result.dns.ns.Domain;
import com.ibm.tivoli.icbc.result.dns.ns.NameServer;
import com.ibm.tivoli.icbc.util.InetAddressConvertor;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author zhaodonglu
 *
 */
public class CmdLineBrowserExecutorImpl implements BrowserExecutor {
  
  private String javaHome = System.getProperty("java.home");

  private static Log log = LogFactory.getLog(CmdLineBrowserExecutorImpl.class);
  /**
   * 
   */
  public CmdLineBrowserExecutorImpl() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.icbc.probe.http.BrowserExecutor#navigate(java.lang.String)
   */
  @Override
  public BrowserResult navigate(String url) {
    String command = "";
    BrowserResult rs = null;
    try {
      String classpath = this.getClasspath();
      command = this.javaHome + "/bin/java -classpath " + classpath + " " + JxBrowserExecutorImpl.class.getCanonicalName() + " " + url;
      log.info("Execute cmd: [" + command + "]");
      Process p = Runtime.getRuntime().exec(command );
      InputStream cmdOutput = p.getInputStream();
      String cmdOut = IOUtils.readStringFromStream(cmdOutput);
      log.info("Execute cmd: [" + command + "] with output: [" + cmdOut + "]");
      XStream xs = getXStream();
      rs = (BrowserResult)xs.fromXML(cmdOut);
    } catch (IOException e) {
      log.error("Fail to execute cmd: [" + command + "]", e);
    }
    return rs;
  }

  private String getClasspath() throws IOException {
    String classpath = "c:/icbc-probe/webapps/icbc-probe/WEB-INF/classes";
    File libDir = new File("c:/icbc-probe/webapps/icbc-probe/WEB-INF/lib");
    File[] jarFiles = libDir.listFiles(new FileFilter(){

      @Override
      public boolean accept(File name) {
        return ((name.getName().toLowerCase().endsWith(".jar"))?true:false);
      }});
    
    if (jarFiles != null) {
       for (File jar: jarFiles) {
           classpath += File.pathSeparator + jar.getCanonicalPath();
       }
    }
    return classpath;
  }

  /**
   * Create a XStream instance.
   * @return
   */
  static XStream getXStream() {
    XStream xstream = new XStream(new DomDriver("UTF-8"));
    xstream.alias("DNSResult", DNSCNameResult.class);
    xstream.alias("LookupEntity", LookupEntity.class);

    xstream.alias("DNSResult4NS", DNSResult4NS.class);
    xstream.alias("Domain", Domain.class);
    xstream.alias("NameServer", NameServer.class);
    xstream.alias("InetAddress", InetAddress.class);
    xstream.registerConverter(new InetAddressConvertor());

    return xstream;
  }

  public static void main(String[] args) throws Exception {
    CmdLineBrowserExecutorImpl exec = new CmdLineBrowserExecutorImpl();
    BrowserResult r = exec.navigate("http://www.icbc.com.cn");
    XStream xs = getXStream();
    String xml = xs.toXML(r);
    System.out.println(xml);
  }
}