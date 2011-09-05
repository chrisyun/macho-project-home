/**
 * 
 */
package com.ibm.tivoli.tuna.config;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.Configuration;

import junit.framework.TestCase;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author zhaodonglu
 * 
 */
public class LoginContextConfigTest extends TestCase {

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  static XStream getXStream() {
    XStream xstream = new XStream(new DomDriver("UTF-8"));
    xstream.alias("loginContext", LoginContextItem.class);
    xstream.alias("loginModule", LoginModuleItem.class);
    xstream.alias("moduleOption", ModuleOption.class);

    return xstream;
  }

  public void testWrite() throws Exception {
    List<LoginContextItem> result = new ArrayList<LoginContextItem>();
    LoginContextItem cfg1 = new LoginContextItem();
    cfg1.setName("LDAP-Simple");
    cfg1.getLoginModules().add(
        new LoginModuleItem("SampleLoginModule", "SampleLoginCallbackHandler", "REQUIRED", new ModuleOption[] { new ModuleOption("debug", "true"),
            new ModuleOption("server", "1.2.3.4") }));
    cfg1.getLoginModules().add(
        new LoginModuleItem("LdapLoginModule", "LdapLoginCallbackHandler", "REQUIRED", new ModuleOption[] { new ModuleOption("debug", "true"),
            new ModuleOption("server", "1.2.3.4") }));
    result.add(cfg1);

    LoginContextItem cfg2 = new LoginContextItem();
    cfg2.setName("LTPA-CRED");
    cfg2.getLoginModules().add(
        new LoginModuleItem("SampleLoginModule", "SampleLoginCallbackHandler", "REQUIRED", new ModuleOption[] { new ModuleOption("debug", "true"),
            new ModuleOption("server", "1.2.3.4") }));
    cfg2.getLoginModules().add(
        new LoginModuleItem("LdapLoginModule", "LdapLoginCallbackHandler", "REQUIRED", new ModuleOption[] { new ModuleOption("debug", "true"),
            new ModuleOption("server", "1.2.3.4") }));
    result.add(cfg2);

    XStream xs = getXStream();
    xs.toXML(result, System.out);

    StringWriter out = new StringWriter();
    xs.toXML(result, out);
    List<LoginContextItem> rs = (List<LoginContextItem>) xs.fromXML(new StringReader(out.toString()));
    assertEquals(2, rs.size());
  }

  public void testLoad() throws Exception {
    InputStream cfgIn = this.getClass().getResourceAsStream("/test/login.modules.config.xml");
    XMLConfiguration config = new XMLConfiguration(cfgIn);
    assertNotNull(config.getItems());
    assertEquals(2, config.getItems().size());
    assertEquals("LDAP-Simple", config.getItems().get(0).getName());
    assertEquals(2, config.getItems().get(0).getLoginModules().size());
    assertEquals("LTPA-CRED", config.getItems().get(1).getName());
  }

  public void testConfiguration() throws Exception {
    InputStream cfgIn = this.getClass().getResourceAsStream("/test/login.modules.config.xml");
    XMLConfiguration config = new XMLConfiguration(cfgIn);
    
    Configuration jaasCfg = config.getConfiguration();
    assertNotNull(jaasCfg);
    assertEquals(2, jaasCfg.getAppConfigurationEntry("LDAP-Simple").length);
    
  }
}
