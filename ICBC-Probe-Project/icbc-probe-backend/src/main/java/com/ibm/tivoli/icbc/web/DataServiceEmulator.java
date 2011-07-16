package com.ibm.tivoli.icbc.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataServiceEmulator extends HttpServlet {

  private Log log = LogFactory.getLog(DataServiceEmulator.class);

  /**
   * Constructor of the object.
   */
  public DataServiceEmulator() {
    super();
  }

  /**
   * Destruction of the servlet. <br>
   */
  public void destroy() {
    super.destroy(); // Just puts "destroy" string in log
    // Put your code here
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

    Reader in = new InputStreamReader(request.getInputStream(), "UTF-8");

    StringWriter xmlDataMessage = new StringWriter();
    char[] buf = new char[512];
    int len = in.read(buf);
    while (len > 0) {
      xmlDataMessage.write(buf, 0, len);
      xmlDataMessage.flush();
      len = in.read(buf);
    }
    log.info("recevie probe data: " + xmlDataMessage);

    // System.out.println("recevie probe data: " + xmlDataMessage);

    File file = new File("/home/zhao/workspaces/icbc-probe-common/src/test/resources/config/tasks.config.all.xml");
    if (!file.exists()) {
      file = new File(System.getProperty("user.home"), "tasks.config.all.xml");
    }

    String taskConfigXML = "";
    if (file.exists()) {
      log.info("Using task config file: " + file.getCanonicalPath());

      taskConfigXML = FileUtils.readFileToString(file, "UTF-8");
      if (taskConfigXML.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")) {
        taskConfigXML = taskConfigXML.substring("<?xml version=\"1.0\" encoding=\"UTF-8\"?>".length());
      }
    }

    response.setContentType("text/xml;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<?xml version='1.0' encoding='utf-8'?>\n" + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
        + "  <soapenv:Body>\n" + "    <SubmitXMLDataResponse type=\"org.netm.www.ebankmonitor.data.SubmitXMLDataResponse\">\n"
        + "      <return type=\"org.netm.www.ebankmonitor.data.Return\">\n" + "        <code>0</code>\n" + taskConfigXML + "      </return>\n"
        + "    </SubmitXMLDataResponse>\n" + "  </soapenv:Body>\n" + "</soapenv:Envelope>\n");
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

  }

}
