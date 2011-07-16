package com.ibm.tivoli.icbc.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AgentSoftwareServlet extends HttpServlet {
  
  private static Log log = LogFactory.getLog(AgentSoftwareServlet.class);

  private String agentSoftwareFile = "packages/icbc-probe-1.0.0-SNAPSHOT-bin.zip";
  /**
   * 
   */
  private static final long serialVersionUID = -8876147131005203826L;

  /**
   * Constructor of the object.
   */
  public AgentSoftwareServlet() {
    super();
  }

  /**
   * Destruction of the servlet. <br>
   */
  public void destroy() {
    super.destroy(); // Just puts "destroy" string in log
    // Put your code here
  }

  private String getVersion() throws Exception  {
    String agentFile = getAgentSoftwareFile();
    log.info("Checking version from package: [" + agentFile + "]");
    ZipFile zf = new ZipFile(agentFile);
    InputStream inputStream = zf.getInputStream(new ZipEntry("icbc-probe/webapps/icbc-probe/META-INF/MANIFEST.MF"));
    Manifest manifest = new Manifest(inputStream);
    Attributes attributes = manifest.getMainAttributes();
    String version = attributes.getValue("Application-Version");
    String builtTime = attributes.getValue("HudsonBuildId");
    String revision = attributes.getValue("HudsonBuildNumber");
    if (version == null || revision == null) {
      return null;
    }

    String localVersion = version + "." + revision;
    log.info("Package: [" + agentFile + "] version: localVersion");
    return localVersion;
  }

  /**
   * The doGet method of the servlet. <br>
   * 
   * This method is called when a form has its tag value method equals to get.
   * 
   * @param request
   *          the request send by the client to the server
   * @param response
   *          the response send by the server to the client
   * @throws ServletException
   *           if an error occurred
   * @throws IOException
   *           if an error occurred
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String requestURI = request.getRequestURI();
    if (requestURI.indexOf("/version") > 0 ) {
       doVersion(request, response);
    } else if (requestURI.indexOf("/download") > 0) {
      doDownload(response);
    }
  }

  private void doDownload(HttpServletResponse response) {
    try {
      String agentFile = getAgentSoftwareFile();
      FileInputStream fileIn = new FileInputStream(agentFile);
      log.info("Download package: [" + agentFile + "]");
      OutputStream out = response.getOutputStream();
      
      byte[] buf = new byte[512];
      int len = fileIn.read(buf);
      while (len > 0) {
            out.write(buf, 0, len);
            out.flush();
            len = fileIn.read(buf);
      }
      fileIn.close();
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void doVersion(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String version = null;
    try {
      version = this.getVersion();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    response.setContentType("text/plain");
    PrintWriter out = response.getWriter();
    //out.println("version: 0.0.1-SNAPSHOT.99");
    out.println("version: " + version);
    
    String downloadURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/services/agent/software/download";
    //out.println("download-url: http://127.0.0.1:9090/job/ICBC%20Probe%20Project/ws/ICBC-Probe-Project/icbc-probe-assembly/target/icbc-probe-0.0.1-SNAPSHOT-bin.zip");
    out.println("download-url: " + downloadURL);
    out.flush();
    out.close();
  }

  /**
   * The doPost method of the servlet. <br>
   * 
   * This method is called when a form has its tag value method equals to post.
   * 
   * @param request
   *          the request send by the client to the server
   * @param response
   *          the response send by the server to the client
   * @throws ServletException
   *           if an error occurred
   * @throws IOException
   *           if an error occurred
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.doGet(request, response);
  }

  /**
   * Initialization of the servlet. <br>
   * 
   * @throws ServletException
   *           if an error occurs
   */
  public void init() throws ServletException {
    this.setAgentSoftwareFile(this.getServletConfig().getInitParameter("agentSoftwareFile"));
  }

  private void setAgentSoftwareFile(String agentSoftwareFile) {
    this.agentSoftwareFile = agentSoftwareFile;
  }

  private String getAgentSoftwareFile() throws IOException {
    File file = new File(this.agentSoftwareFile);
    if (!file.isAbsolute()) {
       file = new File(this.getServletContext().getRealPath(this.agentSoftwareFile));
    }
    return file.getCanonicalPath();
  }

}
