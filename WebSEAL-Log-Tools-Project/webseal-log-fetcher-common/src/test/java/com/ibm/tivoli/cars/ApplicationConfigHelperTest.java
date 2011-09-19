package com.ibm.tivoli.cars;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;

import com.ibm.tivoli.cars.entity.Action;
import com.ibm.tivoli.cars.entity.Application;
import com.ibm.tivoli.cars.entity.ApplicationConfigHelper;
import com.ibm.tivoli.cars.entity.ApplicationConfigHelper.ApplicationAndJunction;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ApplicationConfigHelperTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testOutputConfig() throws Exception {
    List<Application> apps = new ArrayList<Application>();
    Application app = new Application("DSM", "/dsm");
    app.getActions().add(new Action("Login", new String[] { "/dsm/login.do", "/dsm/ssologin.do" }));
    app.getActions().add(new Action("Add", "/dsm/login.do"));
    apps.add(app);

    app = new Application("MyGod", "/mygod");
    app.getActions().add(new Action("Login", new String[] { "/mygod/abc/login.do", "/mygod/abc/ssologin.do" }));
    app.getActions().add(new Action("Add", "/mygod/abc/Add.do"));
    apps.add(app);

    XStream xs = new XStream(new DomDriver("UTF-8"));
    xs.alias("Application", Application.class);
    xs.alias("Action", Action.class);
    System.out.println(xs.toXML(apps));
  }

  public void testOutputConfig4SGM() throws Exception {
    List<Application> apps = new ArrayList<Application>();
    Application app = new Application("ITMS", "/ITMS");
    apps.add(app);
    app = new Application("DMECandidateWeb", "/DMECandidateWeb");
    apps.add(app);
    app = new Application("DMEDealerWeb", "/DMEDealerWeb");
    apps.add(app);
    app = new Application("DMEWeb", "/DMEWeb");
    apps.add(app);
    app = new Application("DMSUC", "/DMSUC");
    apps.add(app);
    app = new Application("DMSUCimg", "/DMSUCimg");
    apps.add(app);
    app = new Application("EAM", "/EAM");
    apps.add(app);
    app = new Application("EAMRPT", "/EAMRPT");
    apps.add(app);
    app = new Application("PkgmsSts", "/PkgmsSts");
    apps.add(app);
    app = new Application("QILINCP", "/QILINCP");
    apps.add(app);
    app = new Application("QILINBBS", "/QILINBBS");
    apps.add(app);
    app = new Application("QILINBRM", "/QILINBRM");
    apps.add(app);
    app = new Application("QILINOIS", "/QILINOIS");
    apps.add(app);
    app = new Application("QILINMMS", "/QILINMMS");
    apps.add(app);
    app = new Application("QILINSITE", "/QILINSITE");
    apps.add(app);
    app = new Application("QILINWP", "/QILINWP");
    apps.add(app);
    app = new Application("SGMBRMWeb", "/SGMBRMWeb");
    apps.add(app);
    app = new Application("SGMPM", "/SGMPM");
    apps.add(app);
    app = new Application("mytrade", "/mytrade");
    apps.add(app);
    app = new Application("mygod", "/mygod");
    apps.add(app);
    app = new Application("authorwps", "/authorwps");
    apps.add(app);
    app = new Application("bpma", "/bpma");
    apps.add(app);
    app = new Application("brandfamily", "/brandfamily");
    apps.add(app);
    app = new Application("itim", "/itim");
    apps.add(app);
    app = new Application("ccpvote", "/ccpvote");
    apps.add(app);
    app = new Application("dfsmdms", "/dfsmdms");
    apps.add(app);
    app = new Application("dsm", "/dsm");
    apps.add(app);
    app = new Application("ets", "/ets");
    apps.add(app);
    app = new Application("edw", "/edw");
    apps.add(app);
    app = new Application("eproc", "/eproc");
    apps.add(app);
    app = new Application("eprpt", "/eprpt");
    apps.add(app);
    app = new Application("pdi", "/pdi");
    apps.add(app);
    app = new Application("pms", "/pms");
    apps.add(app);
    app = new Application("pricing", "/pricing");
    apps.add(app);
    app = new Application("wcmauth", "/wcmauth");
    apps.add(app);
    app = new Application("wcmpub", "/wcmpub");
    apps.add(app);
    app = new Application("webmail", "/webmail");
    apps.add(app);
    app = new Application("qllc", "/qllc");
    apps.add(app);
    app = new Application("qlparty", "/qlparty");
    apps.add(app);
    app = new Application("qlwps", "/qlwps");
    apps.add(app);
    app = new Application("qledge", "/qledge");
    apps.add(app);
    app = new Application("rtpms", "/rtpms");
    apps.add(app);
    app = new Application("rol", "/rol");
    apps.add(app);
    app = new Application("sdac", "/sdac");
    apps.add(app);
    app = new Application("sgmci", "/sgmci");
    apps.add(app);
    app = new Application("spsts", "/spsts");
    apps.add(app);
    app = new Application("tdi", "/tdi");
    apps.add(app);
    app = new Application("tim/itim_expi", "/tim/itim_expi");
    apps.add(app);
    app = new Application("universe", "/universe");
    apps.add(app);
    app = new Application("vas", "/vas");
    apps.add(app);
    app = new Application("Root", "/");
    apps.add(app);

    XStream xs = new XStream(new DomDriver("UTF-8"));
    xs.alias("Application", Application.class);
    xs.alias("Action", Action.class);
    System.out.println(xs.toXML(apps));

  }

  public void testLoadConfig() throws Exception {
    ApplicationConfigHelper configHelper = new ApplicationConfigHelper(this.getClass().getResourceAsStream("/jmt.sgm.intranet.conf"), this.getClass()
        .getResourceAsStream("/app.config.sgm.intranet.xml"));
    List<Application> apps = configHelper.getApplications();
    assertEquals(35, apps.size());

    BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/logs/sgm/intranet/request.log.2011-05-15-02-17-56"),
        "GBK"));
    String line = reader.readLine();
    // String line =
    // "10.203.4.252 - Unauth [14/浜旀湀/2011:22:57:29 +0800] \"GET /lbtest.html HTTP/1.0\" 200 17";
    LogParser parser = new LogParser();
    while (line != null) {
      WebSEALRequestLogEvent event = parser.parseWebSEALRequestLogEvent(line);
      String resourceUrl = event.getResourceUrl();
      ApplicationAndJunction appAndJunction = configHelper.getMatchedApplication(resourceUrl);
      System.out.println(resourceUrl);
      assertNotNull(appAndJunction);
      System.out.println(appAndJunction);
      assertNotSame("/", appAndJunction.getApplication().getJunctions().get(0));

      line = reader.readLine();
    }
  }
  
  public void testSplit() throws Exception {
    String[] s = StringUtils.split("a\tb", " \t");
    assertEquals(2, s.length);
  }
  
  public void testRegexp() throws Exception {
    assertTrue(Pattern.matches("a*b", "aaaaab"));
    Pattern p = Pattern.compile("/universe/.*\\.swf");
    Matcher m = p.matcher("/universe/wps/themes/html/SGMUniverseSpecNav/universe_flash.swf");
    assertTrue(m.matches());
  }

  public void testGetMatchedAction() throws Exception {
    ApplicationConfigHelper configHelper = new ApplicationConfigHelper(this.getClass().getResourceAsStream("/jmt.sgm.intranet.conf"), this.getClass()
        .getResourceAsStream("/app.config.sgm.intranet.xml"));
    List<Application> apps = configHelper.getApplications();
    assertEquals(35, apps.size());

    String line = "10.200.35.148 - skwaid [14/五月/2011:22:57:54 +0800] \"GET /universe/wps/themes/html/SGMUniverseSpecNav/heading_bg02.gif HTTP/1.1\" 304 0";
    LogParser parser = new LogParser();
    WebSEALRequestLogEvent event = parser.parseWebSEALRequestLogEvent(line);
    String resourceUrl = event.getResourceUrl();
    ApplicationAndJunction appAndJunction = configHelper.getMatchedApplication(resourceUrl);
    assertNotNull(appAndJunction);
    assertNotSame("/", appAndJunction.getApplication().getJunctions().get(0));
    Action action = configHelper.getMatchedAction(appAndJunction, resourceUrl);
    assertNotNull(action);
  }

  public void testGetMatchedAction4OA() throws Exception {
    ApplicationConfigHelper configHelper = new ApplicationConfigHelper(this.getClass().getResourceAsStream("/jmt.sgm.intranet.conf"), this.getClass()
        .getResourceAsStream("/app.config.sgm.hrms.xml"));
    List<Application> apps = configHelper.getApplications();
    assertEquals(3, apps.size());

    String line = "10.200.35.148 - skwaid [14/五月/2011:22:57:54 +0800] \"GET /oa/Login?event=LdapToOa HTTP/1.1\" 304 0";
    LogParser parser = new LogParser();
    WebSEALRequestLogEvent event = parser.parseWebSEALRequestLogEvent(line);
    String resourceUrl = event.getResourceUrl();
    ApplicationAndJunction appAndJunction = configHelper.getMatchedApplication(resourceUrl);
    assertNotNull(appAndJunction);
    assertNotSame("/", appAndJunction.getApplication().getJunctions().get(0));
    Action action = configHelper.getMatchedAction(appAndJunction, resourceUrl);
    assertNotNull(action);
  }

  public void testGetMatchedAction4HRMS() throws Exception {
    ApplicationConfigHelper configHelper = new ApplicationConfigHelper(this.getClass().getResourceAsStream("/jmt.sgm.intranet.conf"), this.getClass()
        .getResourceAsStream("/app.config.sgm.hrms.xml"));
    List<Application> apps = configHelper.getApplications();
    assertEquals(3, apps.size());

    String line = "10.200.35.148 - skwaid [14/五月/2011:22:57:54 +0800] \"GET /hrms/psp/ps/?cmd=start&languageCd=ZHS HTTP/1.1\" 304 0";
    LogParser parser = new LogParser();
    WebSEALRequestLogEvent event = parser.parseWebSEALRequestLogEvent(line);
    String resourceUrl = event.getResourceUrl();
    ApplicationAndJunction appAndJunction = configHelper.getMatchedApplication(resourceUrl);
    assertNotNull(appAndJunction);
    assertNotSame("/", appAndJunction.getApplication().getJunctions().get(0));
    Action action = configHelper.getMatchedAction(appAndJunction, resourceUrl);
    assertNotNull(action);
  }
}
