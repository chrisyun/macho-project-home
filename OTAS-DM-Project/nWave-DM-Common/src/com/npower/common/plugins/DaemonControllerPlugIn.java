/*
 * $Id: DaemonControllerPlugIn.java,v 1.2 2008/11/09 09:25:44 zhao Exp $ 
 *
 * Copyright 2003-2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.npower.common.plugins;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import javax.servlet.ServletException;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.RequestUtils;
import org.xml.sax.SAXException;


/**
 * <p>
 * An implementation of <code>PlugIn</code> which can be configured to
 * instantiate a graph of objects using the Commons Digester and place the root
 * object of that graph into the Application context.
 * </p>
 * <pre>
 *   <plug-in className="com.npower.common.plugins.DaemonControllerPlugIn">
 *    <set-property property="push" value="false"/>
 *    <set-property property="key" value="daemons"/>
 *
 *    <set-property property="digesterSource" value="classpath"/>
 *    <set-property property="digesterPath" value="/com/npower/common/plugins/daemons-digester-rules.xml"/>
 *
 *    <!-- Source: servlet, file, classpath -->
 *    <set-property property="configSource" value="file"/>
 *    <set-property property="configPath" value="${otas.dm.home}/config/daemons/daemons.xml"/>
 *  </plug-in>
 *  
 *  File: daemons.xml
 *  
 *  <?xml version="1.0" encoding="UTF-8"?>
 *  <daemons>
 *  
 *    <plug-in className="com.npower.common.plugins.MyPlugIn">
 *      <set-property property="interval" value="1000" />
 *      <set-property property="name" value="AAA" />
 *    </plug-in>
 *    
 *    <plug-in className="com.npower.common.plugins.MyPlugIn">
 *      <set-property property="interval" value="2000" />
 *      <set-property property="name" value="BBB" />
 *    </plug-in>
 *    
 *    <plug-in className="com.npower.common.plugins.MyPlugIn">
 *      <set-property property="interval" value="3000" />
 *      <set-property property="name" value="CCC" />
 *    </plug-in>
 *    
 *  </daemons>
 *  
 * </pre>
 * @version $Rev: 164530 $
 * @see org.apache.struts.action.PlugIn
 */
public class DaemonControllerPlugIn implements PlugIn {

  /**
   * Commons Logging instance.
   */
  private static Log            log              = LogFactory.getLog(DaemonControllerPlugIn.class);

  protected static final String SOURCE_CLASSPATH = "classpath";

  protected static final String SOURCE_FILE      = "file";

  protected static final String SOURCE_SERVLET   = "servlet";

  protected String              configPath       = null;

  protected String              configSource     = SOURCE_SERVLET;

  protected String              digesterPath     = null;

  protected String              digesterSource   = SOURCE_SERVLET;

  protected String              key              = null;

  protected ModuleConfig        moduleConfig     = null;

  protected String              rulesets         = null;

  protected ActionServlet       servlet          = null;

  protected boolean             push             = false;

  /**
   * Constructor for DigestingPlugIn.
   */
  public DaemonControllerPlugIn() {
    super();
  }

  /**
   * Receive notification that our owning module is being shut down.
   */
  public void destroy() {
    Object daemonObj = this.loadGeneratedObject();
    this.destroy(daemonObj);
    this.servlet = null;
    this.moduleConfig = null;
  }

  /**
   * <p>
   * Initialize a <code>Digester</code> and use it to parse a configuration
   * file, resulting in a root object which will be placed into the
   * ServletContext.
   * </p>
   * 
   * @param servlet
   *            ActionServlet that is managing all the modules in this web
   *            application
   * @param config
   *            ModuleConfig for the module with which this plug-in is
   *            associated
   * 
   * @throws ServletException
   *             if this <code>PlugIn</code> cannot be successfully
   *             initialized
   */
  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet, org.apache.struts.config.ModuleConfig)
   */
  public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {

    this.servlet = servlet;
    this.moduleConfig = config;

    Object obj = null;

    Digester digester = this.initializeDigester();

    if (this.push) {
      log.debug("push == true; pushing plugin onto digester stack");
      digester.push(obj);
    }

    try {
      log.debug("XML data file: [path: " + this.configPath + ", source: " + this.configSource + "]");

      URL configURL = this.getConfigURL(this.configPath, this.configSource);
      if (configURL == null)
        throw new ServletException("Unable to locate XML data file at [path: " + this.configPath + ", source: "
            + this.configSource + "]");
      obj = digester.parse(configURL.openStream());
      
      // Fire up all of daemons
      this.fireUp(servlet, config, obj);

    } catch (IOException e) {
      log.error("Exception processing config", e);
      throw new ServletException(e);
    } catch (SAXException e) {
      log.error("Exception processing config", e);
      throw new ServletException(e);
    }

    this.storeGeneratedObject(obj);
  }

  private void fireUp(ActionServlet servlet, ModuleConfig config, Object obj) {
    if (obj == null) {
       return;
    }
    if (obj instanceof DisablePlugIn) {
       if (((DisablePlugIn)obj).isDisabled()) {
          log.info("Plugin: [" + obj.getClass() + "] disabled!");
          return;
       }
    }
    if (obj instanceof PlugIn) {
       try {
           ((PlugIn)obj).init(servlet, config);
       } catch (Exception e) {
         // FIXME Whenever we fail silently, we must document a valid reason
         // for doing so.  Why should we fail silently if a property can't be set on
         // the plugin?
         log.error(e.getMessage(), e);
       }
    } else if (obj instanceof Collection) {
      for (Object o: (Collection<?>)obj) {
          this.fireUp(servlet, config, o);
      }
    }
  }

  private void destroy(Object obj) {
    if (obj == null) {
       return;
    }
    if (obj instanceof PlugIn) {
       try {
           ((PlugIn)obj).destroy();
       } catch (Exception e) {
         // FIXME Whenever we fail silently, we must document a valid reason
         // for doing so.  Why should we fail silently if a property can't be set on
         // the plugin?
         log.error(e.getMessage(), e);
       }
    } else if (obj instanceof Collection) {
      for (Object o: (Collection<?>)obj) {
          this.destroy(o);
      }
    }
  }

  /**
   * Initialize the <code>Digester</code> which will be used to process the
   * main configuration.
   * 
   * @return a Digester, ready to use.
   * @throws ServletException
   */
  protected Digester initializeDigester() throws ServletException {
    Digester digester = null;

    if (this.digesterPath != null && this.digesterSource != null) {

      try {
        log
            .debug("Initialize digester from XML [path: " + this.digesterPath + "; source: " + this.digesterSource
                + "]");
        digester = this.digesterFromXml(this.digesterPath, this.digesterSource);

      } catch (IOException e) {
        // TODO Internationalize msg
        log.error("Exception instantiating digester from XML ", e);
        throw new ServletException(e);

      }

    } else {
      log.debug("No XML rules for digester; call newDigesterInstance()");
      digester = this.newDigesterInstance();
    }

    this.applyRuleSets(digester);

    return digester;
  }

  /**
   * <p>
   * Instantiate a <code>Digester</code>.
   * </p>
   * <p>
   * Subclasses may wish to override this to provide a subclass of Digester, or
   * to configure the Digester using object methods.
   * </p>
   * 
   * @return a basic instance of
   *         <code>org.apache.commons.digester.Digester</code>
   */
  protected Digester newDigesterInstance() {
    Digester digester = new Digester();
    digester.setValidating(false);
    digester.addObjectCreate("daemons", ArrayList.class);
    digester.addObjectCreate("*/plug-in", "className", PlugIn.class);
    digester.addSetProperty("*/plug-in/set-property", "property", "value");
    digester.addSetNext("*/plug-in", "add");
    return digester;
    
  }

  /**
   * <p>
   * Instantiate a Digester from an XML input stream using the Commons
   * <code>DigesterLoader</code>.
   * </p>
   * 
   * @param path
   *            the path to the digester rules XML to be found using
   *            <code>source</code>
   * @param source
   *            a string indicating the lookup method to be used with
   *            <code>path</code>
   * @return a configured Digester
   * @throws FileNotFoundException
   * @throws MalformedURLException
   * @see #getConfigURL(String, String)
   */
  protected Digester digesterFromXml(String path, String source) throws IOException {

    URL configURL = this.getConfigURL(path, source);
    if (configURL == null) {
      throw new NullPointerException("No resource '" + path + "' found in '" + source + "'");
    }
    return DigesterLoader.createDigester(configURL);
  }

  /**
   * Instantiate any <code>RuleSet</code> classes defined in the
   * <code>rulesets</code> property and use them to add rules to our
   * <code>Digester</code>.
   * 
   * @param digester
   *            the Digester instance to add RuleSet objects to.
   * @throws ServletException
   */
  protected void applyRuleSets(Digester digester) throws ServletException {

    if (this.rulesets == null || this.rulesets.trim().length() == 0) {
      return;
    }

    rulesets = rulesets.trim();
    String ruleSet = null;
    while (rulesets.length() > 0) {
      int comma = rulesets.indexOf(",");
      if (comma < 0) {
        ruleSet = rulesets.trim();
        rulesets = "";
      } else {
        ruleSet = rulesets.substring(0, comma).trim();
        rulesets = rulesets.substring(comma + 1).trim();
      }

      if (log.isDebugEnabled()) {
        // TODO Internationalize msg
        log.debug("Configuring custom Digester Ruleset of type " + ruleSet);
      }

      try {
        RuleSet instance = (RuleSet) RequestUtils.applicationInstance(ruleSet);

        digester.addRuleSet(instance);

      } catch (Exception e) {
        // TODO Internationalize msg
        log.error("Exception configuring custom Digester RuleSet", e);
        throw new ServletException(e);
      }
    }
  }

  /**
   * <p>
   * Look up a resource path using one of a set of known path resolution
   * mechanisms and return a URL to the resource.
   * </p>
   * 
   * @param path
   *            a String which is meaningful to one of the known resolution
   *            mechanisms.
   * @param source
   *            one of the known path resolution mechanisms:
   *            <ul>
   *            <li>file - the path is a fully-qualified filesystem path.</li>
   *            <li>servlet - the path is a servlet-context relative path.</li>
   *            <li>classpath - the path is a classpath-relative path.</li>
   *            </ul>
   * @return a URL pointing to the given path in the given mechanism.
   * @throws FileNotFoundException
   * @throws MalformedURLException
   */
  protected URL getConfigURL(String path, String source) throws IOException {

    if (SOURCE_CLASSPATH.equals(source)) {
      return this.getClassPathURL(path);
    }

    if (SOURCE_FILE.equals(source)) {
      return this.getFileURL(path);
    }

    if (SOURCE_SERVLET.equals(source)) {
      return this.getServletContextURL(path);
    }

    // TODO Internationalize msg
    throw new IllegalArgumentException("ConfigSource " + source + " is not recognized");
  }

  /**
   * Given a string, return a URL to a classpath resource of that name.
   * 
   * @param path
   *            a Classpath-relative string identifying a resource.
   * @return a URL identifying the resource on the classpath. TODO Do we need to
   *         be smarter about ClassLoaders?
   */
  protected URL getClassPathURL(String path) {
    return getClass().getClassLoader().getResource(path);
  }

  /**
   * Given a string, return a URL to a Servlet Context resource of that name.
   * 
   * @param path
   *            a Classpath-relative string identifying a resource.
   * @return a URL identifying the resource in the Servlet Context
   * @throws MalformedURLException
   */
  protected URL getServletContextURL(String path) throws IOException {
    return this.servlet.getServletContext().getResource(path);
  }

  /**
   * Given a string, return a URL to a Filesystem resource of that name.
   * 
   * @param path
   *            a path to a file.
   * @return a URL identifying the resource in the in the file system.
   * @throws MalformedURLException
   * @throws FileNotFoundException
   */
  protected URL getFileURL(String path) throws IOException {
    path = macro(System.getProperties(), path);
    File file = new File(path);
    return file.toURL();
  }

  /**
   * @param props
   * @param rawValue
   * @return
   */
  private static String macro(Properties props, String rawValue) {
    if (StringUtils.isEmpty(rawValue)) {
       return rawValue;
    }
    String value = rawValue;
    
    int index = value.indexOf("${");
    while (value.indexOf("${") >= 0) {
          String inlineKey = value.substring(index + 2, value.indexOf("}", index));
          String inlineValue = props.getProperty(inlineKey);
          if (inlineValue == null) {
             throw new RuntimeException("Missing property: " + inlineKey);
          }
          inlineValue = inlineValue.trim();
          value = StringUtils.replace(value, "${" + inlineKey + "}", inlineValue);
          //value = value.replaceAll("${" + inlineKey + "}", inlineValue);
          
          index = value.indexOf("${");
    }
    return value;
  }

  /**
   * @param configPath
   *            the path to configuration information for this PlugIn.
   * @see #configSource
   */
  public void setConfigPath(String configPath) {
    this.configPath = configPath;
  }

  /**
   * @return the configPath property
   * @see #configSource
   */
  public String getConfigPath() {
    return configPath;
  }

  /**
   * Set the source of the config file. Should be one of the following:
   * <ul>
   * <li> "classpath" - indicates that the configPath will be resolved by the
   * ClassLoader. </li>
   * <li> "file" - indicates that the configPath is a fully-qualified filesystem
   * path. </li>
   * <li> "servlet" - indicates that the configPath will be found by the
   * ServletContext. </li>
   * </ul>
   * 
   * @param configSource
   *            the source (lookup method) for the config file.
   * @see #configPath
   */
  public void setConfigSource(String configSource) {
    this.configSource = configSource;
  }

  /**
   * @return the string describing which access method should be used to resolve
   *         configPath.
   * @see #configPath
   */
  public String getConfigSource() {
    return configSource;
  }

  /**
   * This method is called after the Digester runs to store the generated object
   * somewhere. This implementation places the given object into the
   * ServletContext under the attribute name as defined in <code>key</code>.
   * 
   * @param obj
   *            The object to save.
   */
  protected void storeGeneratedObject(Object obj) {
    log.debug("Put [" + obj + "] into application context [key:" + this.key + "]");
    if (obj != null) {
       this.servlet.getServletContext().setAttribute(this.getKey(), obj);
    }
  }

  /**
   * This method is called after the Digester runs to store the generated object
   * somewhere. This implementation places the given object into the
   * ServletContext under the attribute name as defined in <code>key</code>.
   * 
   * @return  The loaded object.
   */
  protected Object loadGeneratedObject() {
    log.debug("Load from application context [key:" + this.key + "]");
    return this.servlet.getServletContext().getAttribute(this.getKey());
  }

  /**
   * @param key
   *            The ServletContext attribute name to store the generated object
   *            under.
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   * @return The ServletContext attribute name the generated object is stored
   *         under.
   */
  public String getKey() {
    return key;
  }

  /**
   * <p>
   * A comma-delimited list of one or more classes which implement
   * <code>org.apache.commons.digester.RuleSet</code>. (Optional)
   * </p>
   */
  public void setRulesets(String ruleSets) {
    this.rulesets = ruleSets;
  }

  /**
   * @return The configured list of <code>RuleSet</code> classes.
   */
  public String getRulesets() {
    return this.rulesets;
  }

  /**
   * <p>
   * The path to a Digester XML configuration file, relative to the
   * <code>digesterSource</code> property. (Optional)
   * </p>
   * 
   * @see #digesterSource
   * @see #getConfigURL(String, String)
   */
  public void setDigesterPath(String digesterPath) {
    this.digesterPath = digesterPath;
  }

  /**
   * @return the configured path to a Digester XML config file, or null.
   * @see #digesterSource
   * @see #getConfigURL(String, String)
   */
  public String getDigesterPath() {
    return digesterPath;
  }

  /**
   * <p>
   * The lookup mechanism to be used to resolve <code>digesterPath</code>
   * (optional).
   * </p>
   * 
   * @param digesterSource
   * @see #getConfigURL(String, String)
   */
  public void setDigesterSource(String digesterSource) {
    this.digesterSource = digesterSource;
  }

  /**
   * @return the configured lookup mechanism for resolving
   *         <code>digesterPath</code>.
   * @see #getConfigURL(String, String)
   */
  public String getDigesterSource() {
    return this.digesterSource;
  }

  /**
   * <p>
   * If set to <code>true</code>, this PlugIn will be pushed onto the
   * Digester stack before the digester <code>parse</code> method is called.
   * </p>
   * <p>
   * Defaults to <code>false</code>
   * </p>
   * 
   * @param push
   */
  public void setPush(boolean push) {
    this.push = push;
  }

  /**
   * @return Whether or not this <code>PlugIn</code> instance will be pushed
   *         onto the <code>Digester</code> stack before
   *         <code>digester.parse()</code> is called.
   */
  public boolean getPush() {
    return this.push;
  }

}
