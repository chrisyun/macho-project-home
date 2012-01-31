/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.internet2.middleware.shibboleth.wayf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opensaml.DefaultBootstrap;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.util.DatatypeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.internet2.middleware.shibboleth.common.ShibbolethConfigurationException;
import edu.internet2.middleware.shibboleth.wayf.plugins.Plugin;

/**
 * A servlet implementation of the Shibboleth WAYF service. Allows a browser user to select from among a group of origin
 * sites. User selection is optionally cached and the user is forwarded to the HandleService appropriate to his
 * selection.
 */
public class WayfService extends HttpServlet {

    /** Required constant for serialization. */
    private static final long serialVersionUID = 5244503011625804940L;

    /** Handle for outputting error and other messages. */
    private static final Logger LOG = LoggerFactory.getLogger(WayfService.class.getName());

    /** Where to get the configuration. */
    private String wayfConfigFileLocation;

    /** Logging service. */
    @SuppressWarnings("unused")
    private LogbackLoggingService logService;

    /** All the different Discovery Services we deal with. */
    private List<DiscoveryServiceHandler> discoveryServices = new ArrayList<DiscoveryServiceHandler>();

    /**
     * Initialize the Discovery Service.
     * 
     * @throws javax.servlet.ServletException in the case of something bad happening
     * 
     */
    public void init() throws ServletException {

        super.init();

        wayfConfigFileLocation = getServletContext().getInitParameter("WAYFConfigFileLocation");
        if (wayfConfigFileLocation == null) {
            wayfConfigFileLocation = getServletConfig().getInitParameter("WAYFConfigFileLocation");
        }
        if (wayfConfigFileLocation == null) {
            wayfConfigFileLocation = "/wayfconfig.xml";
        }

        try {
            //
            // Initialize logging
            //
            String wayfLogfile = getServletContext().getInitParameter("WAYFLogConfig");
            if (null == wayfLogfile) {
                wayfLogfile = getServletConfig().getInitParameter("WAYFLogConfig");
            }
            long pollingFrequency = 1000 * 60 * 5;

            String wayfLogfilePollFrequency = getServletContext().getInitParameter("WAYFLogConfigPollFrequency");
            if (null == wayfLogfilePollFrequency) {
                wayfLogfilePollFrequency = getServletConfig().getInitParameter("WAYFLogConfigPollFrequency");
            }
            if (!DatatypeHelper.isEmpty(wayfLogfilePollFrequency)) {
                pollingFrequency = Long.parseLong(wayfLogfilePollFrequency);
            }
            if (wayfLogfile != null) {
                logService = new LogbackLoggingService(wayfLogfile, pollingFrequency);
            }

            LOG.info("Logging initiated");

            //
            // Initialize OpenSAML 2 library
            //
            DefaultBootstrap.bootstrap();

            BasicParserPool parser = new BasicParserPool();
            parser.setNamespaceAware(true);
            Document doc;
            try {
                doc = parser.parse(new FileInputStream(wayfConfigFileLocation));
            } catch (FileNotFoundException e) {
                LOG.error("Could not parse " + wayfConfigFileLocation, e);
                throw new ShibbolethConfigurationException("Could not parse " + wayfConfigFileLocation, e);
            }
            NodeList itemElements = doc.getDocumentElement().getElementsByTagNameNS(XMLConstants.CONFIG_NS, "Default");

            HandlerConfig defaultHandlerConfig;

            if (itemElements.getLength() == 1) {

                Element element = (Element) itemElements.item(0);
                String attribute = element.getAttribute("location");

                if (attribute != null && !attribute.isEmpty()) {

                    LOG.error("<Default> element cannot contain a location attribute");
                    throw new ShibbolethConfigurationException("<Default> element cannot contain a location attribute");

                }

                attribute = element.getAttribute("default");

                if (attribute != null && !attribute.isEmpty()) {

                    LOG.error("<Default> element cannot contain a default attribute");
                    throw new ShibbolethConfigurationException("<Default> element cannot contain a default attribute");

                }

                itemElements = element.getElementsByTagName("Federation");

                if (itemElements.getLength() != 0) {

                    LOG.error("<Default> element cannot contain <Federation> elements");
                    throw new ShibbolethConfigurationException("<Default> element cannot contain "
                            + "<Federation> elements");

                }

                defaultHandlerConfig = new HandlerConfig(element, new HandlerConfig());

            } else if (itemElements.getLength() == 0) {

                defaultHandlerConfig = new HandlerConfig();

            } else {
                LOG.error("Must specify exactly one <Default> element");
                throw new ShibbolethConfigurationException("Must specify exactly one <Default> element");
            }

            //
            // Load metadata
            //
            Map<String, IdPSiteSet> siteSets = new HashMap<String, IdPSiteSet>();

            itemElements = doc.getDocumentElement().getElementsByTagNameNS(XMLConstants.CONFIG_NS, "MetadataProvider");

            for (int i = 0; i < itemElements.getLength(); i++) {

                Element element = (Element) itemElements.item(i);

                IdPSiteSet siteSet = new IdPSiteSet(element, parser, defaultHandlerConfig.getWarnOnBadBinding());
                
                if (siteSets.containsKey(siteSet.getIdentifier())) {
                    LOG.warn("Duplicate Metadata provider: " + siteSet.getIdentifier());
                } else {
                    siteSets.put(siteSet.getIdentifier(), siteSet);
                }
            }
            if (siteSets.size() < 1) {
                LOG.error("No Metadata Provider metadata loaded.");
                throw new ShibbolethConfigurationException("Could not load SAML metadata.");
            }
            //
            // Load plugins
            //

            Map<String, Plugin> plugins = new HashMap<String, Plugin>();

            itemElements = doc.getDocumentElement().getElementsByTagNameNS(XMLConstants.CONFIG_NS, "Plugin");

            for (int i = 0; i < itemElements.getLength(); i++) {

                Plugin plugin;

                Element element = (Element) itemElements.item(i);

                String identifier = element.getAttribute("identifier");

                if (null == identifier || identifier.isEmpty()) {
                    LOG.error("Could not load plugin with no identifier");
                    continue;
                }

                String className = element.getAttribute("type");
                if (null == className || className.isEmpty()) {
                    LOG.error("Plugin " + identifier + " did not have a valid type");
                }
                //
                // So try to get hold of the plugin
                //
                try {
                    Class<Plugin> pluginClass = (Class<Plugin>) Class.forName(className);
                    Class[] classParams = {Element.class};
                    Constructor<Plugin> pluginConstructor = pluginClass.getConstructor(classParams);
                    Object[] constructorParams = {element};

                    plugin = pluginConstructor.newInstance(constructorParams);

                } catch (Exception e) {
                    LOG.error("Plugin " + identifier + " could not be loaded ", e);
                    continue;
                }

                plugins.put(identifier, plugin);
            }

            //
            // Load service handlers
            //
            itemElements =
                    doc.getDocumentElement().getElementsByTagNameNS(XMLConstants.CONFIG_NS, "DiscoveryServiceHandler");

            for (int i = 0; i < itemElements.getLength(); i++) {

                discoveryServices.add(new DiscoveryServiceHandler((Element) itemElements.item(i), siteSets, plugins,
                        defaultHandlerConfig));

            }

        } catch (Exception e) {
            //
            // All other exceptions are from the parsing
            //
            if (LOG != null) {
                LOG.error("Error parsing DS configuration file.", e);
            }
            throw new ServletException("Error parsing DS configuration file.", e);
        }

        LOG.info("DS initialization completed.");
    }

    /**
     * Handle an HTTP GET. Just passes out to the appropriate handler.
     * 
     * @param req described the request.
     * @param res contains the response.
     * @see HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res) {

        LOG.info("Handling DS request.");
        // Tell the browser not to cache the WAYF page
        res.setHeader("Cache-Control", "no-cache");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);

        DiscoveryServiceHandler serviceHandler = lookupServiceHandler(req);

        serviceHandler.doGet(req, res);

    }

    /**
     * Given a request (an HTTP Get) find the apropriate DiscoveryService (from the name).
     * 
     * @param req described the request
     * @return the appropriate DiscoveryService.
     */
    private DiscoveryServiceHandler lookupServiceHandler(HttpServletRequest req) {

        Iterator<DiscoveryServiceHandler> it = discoveryServices.iterator();
        String requestURL = req.getRequestURL().toString();
        DiscoveryServiceHandler defaultHandler = null;

        while (it.hasNext()) {
            DiscoveryServiceHandler handler = it.next();

            if (requestURL.matches(handler.getLocation())) {
                return handler;
            }
            if (defaultHandler == null || handler.isDefault()) {
                defaultHandler = handler;
            }
        }
        LOG.warn("Could not find Discovery service Handler for " + requestURL);
        return defaultHandler;
    }
}
