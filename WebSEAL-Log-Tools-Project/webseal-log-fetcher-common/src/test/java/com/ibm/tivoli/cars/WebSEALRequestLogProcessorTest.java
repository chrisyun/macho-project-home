package com.ibm.tivoli.cars;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Locale;
import java.util.UUID;

import junit.framework.TestCase;

import com.ibm.tivoli.cars.handler.SoapEventHandlerImpl;
import com.ibm.tivoli.cars.handler.W7XMLLogHandler;

public class WebSEALRequestLogProcessorTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testParse() throws Exception {
    String line = "10.17.76.4 - zhaodonglu1 [28/Oct/2010:14:27:51 +0800] \"GET /oa/Login?event=LdapToOa HTTP/1.1\" 302 261";
    LogParser parser = new LogParser(Locale.ENGLISH);
    WebSEALRequestLogEvent result = parser.parseWebSEALRequestLogEvent(line);
    assertNotNull(result);
  }

  public void testParse2() throws Exception {
    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
        "C:\\Users\\IBM_ADMIN\\workspace\\WebSEAL-Log-Tools-Project\\webseal-log-fetcher-common\\src\\test\\resources\\logs\\request.log.2011-08-21-08-58-41")));
    String line = reader.readLine();
    while (line != null) {
      LogParser parser = new LogParser(Locale.CHINESE);
      WebSEALRequestLogEvent result = parser.parseWebSEALRequestLogEvent(line);
      assertNotNull(result);
      line = reader.readLine();
      System.out.println(line);
    }
  }

  public void testProcess() throws Exception {
    String lines = "10.17.76.4 - zhaodonglu1 [26/Oct/2010:14:27:51 +0800] \"GET /oa/Login?event=LdapToOa HTTP/1.1\" 302 261\n";
    lines += "10.12.100.153 - zhaodonglu1 [26/Oct/2010:16:03:50 +0800] \"GET /oa/Login?event=LdapToOa HTTP/1.0\" 302 261\n";
    lines += "10.25.96.103 - zhaodonglu1 [26/Oct/2010:16:03:51 +0800] \"GET /oa/Login?event=LdapToOa HTTP/1.1\" 302 261\n";

    SoapEventHandlerImpl eventUploader = new SoapEventHandlerImpl();
    eventUploader.setCarsServiceURL("http://10.9.2.100:9080/CommonAuditService/services/Emitter");
    eventUploader.setWebSEALUrl("http://10.9.2.100");

    WebSEALRequestLogProcessor processor = new WebSEALRequestLogProcessor();
    processor.setEventReader(new StringReader(lines));
    processor.setEventHandler(eventUploader);
    processor.process();
  }

  public void testProcess2() throws Exception {
    String lines = "10.200.35.148 - skwaid [14/五月/2011:22:57:26 +0800] \"GET /universe/wps/themes/html/SGMUniverseSpecNav/heading_bg02.gif HTTP/1.1\" 304 0\n";

    SoapEventHandlerImpl eventUploader = new SoapEventHandlerImpl();
    eventUploader.setCarsServiceURL("http://10.9.2.100:9080/CommonAuditService/services/Emitter");
    eventUploader.setWebSEALUrl("http://10.9.2.100");

    WebSEALRequestLogProcessor processor = new WebSEALRequestLogProcessor();
    processor.setEventReader(new StringReader(lines));
    processor.setEventHandler(eventUploader);
    processor.process();
  }

  public void testProcess3() throws Exception {
    String lines = "10.200.35.148 - skwaid [14/五月/2011:22:57:26 +0800] \"GET /universe/wps/themes/html/SGMUniverseSpecNav/heading_bg02.gif HTTP/1.1\" 304 0\n";

    W7XMLLogHandler handler = new W7XMLLogHandler();
    handler.setWebSEALUrl("http://10.9.2.100");
    handler.setWebSEALInstaceId("webseal1");

    WebSEALRequestLogProcessor processor = new WebSEALRequestLogProcessor();
    processor.setEventReader(new StringReader(lines));
    processor.setEventHandler(handler);
    processor.process();
  }

  public void testUUID() throws Exception {
    UUID idOne = UUID.randomUUID();
    // assertEquals("", idOne);
  }

}
