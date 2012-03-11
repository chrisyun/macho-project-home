/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/processor/ScriptManagementProcessor.java,v 1.9 2008/08/04 04:25:28 zhao Exp $
 * $Revision: 1.9 $
 * $Date: 2008/08/04 04:25:28 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
 *
 * This SOURCE CODE FILE, which has been provided by NPower as part
 * of a NPower product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of NPower.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 * ===============================================================================================
 */
package com.npower.dm.processor;

/**
 */
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

import sync4j.framework.config.ConfigClassLoader;
import sync4j.framework.config.Configuration;
import sync4j.framework.core.Alert;
import sync4j.framework.core.dm.ddf.DevInfo;
import sync4j.framework.engine.dm.DeviceDMState;
import sync4j.framework.engine.dm.ManagementException;
import sync4j.framework.engine.dm.ManagementOperation;
import sync4j.framework.engine.dm.ManagementOperationResult;
import sync4j.framework.engine.dm.ManagementProcessor;
import sync4j.framework.engine.dm.SessionContext;
import sync4j.framework.logging.Sync4jLogger;
import sync4j.framework.logging.Sync4jLoggerName;
import bsh.BshClassManager;
import bsh.Interpreter;

/**
 * This is a concrete implementation of the ManagementProcessor interface.
 * It uses a scripting language to carry on the required management logic.
 * <p>
 * Currently supported scripting language is BeanShell. More scripting languages
 * can be added in the future.
 * <p>
 * The interpreter is created once in the ManagementProcessor's beginSession()
 * method and is initialized with the scripting variable listed below.
 * In addition, the script specified in the scriptFile property is called.
 * Scripts are located under the Funambol config path. Details for the BeanShell
 * engine are given later.
 * <p>
 * The script specified in scriptFile must have three entry points:
 * init(), getNextOperations() and setOperationResults(). In order to keep the
 * interaction between ScriptManagementProcessor and the the underlying
 * scripting engine, input and output values are passed by variables and not as
 * input parameters and return velues.
 * <p>
 * <g>Scripting Variables</g>
 * The following scripting variables are set just after scripting engine
 * creation:
 * <ul>
 * <li>processor - the ManagementProcessor instance reference</li>
 * <li>principal - user principal who is going to be managed</li>
 * <li>devInfo - device info of the device which is going to be managed</li>
 * <li>managementType - value given by the device when starting the management
 *     session (such as server or client initiated management session)</li>
 * <li>config - the Configuration object used to get server side beans</li>
 * <li>sessionId - The current session identifier</li>
 * <li>log - The FunambolLogger to use for logging</li>
 * <li>dmstate - The DeviceDMState object associated to the session</li>
 *
 * </ul>
 * The following scripting variables are input/output variables that the
 * management script and the management processor exchange:
 * <ul>
 * <li>operations (OUT) - ManagementOperation[] to be sent to the device
 *                       management engine
 * <li>results (IN) - ManagementResult[] returned by the device management
 *                     engine</li>
 * </ul>
 *
 * @version $Id: ScriptManagementProcessor.java,v 1.9 2008/08/04 04:25:28 zhao Exp $
 */
public class ScriptManagementProcessor extends BaseProcessor
implements ManagementProcessor, java.io.Serializable {

    // --------------------------------------------------------------- Constants

    public static final String VAR_PROCESSOR       = "processor"     ;
    public static final String VAR_SESSIONID       = "sessionId"     ;
    public static final String VAR_PRINCIPAL       = "principal"     ;
    public static final String VAR_MANAGEMENT_TYPE = "managementType";
    public static final String VAR_DEVINFO         = "devInfo"       ;
    public static final String VAR_CONFIG          = "config"        ;
    public static final String VAR_OPERATIONS      = "operations"    ;
    public static final String VAR_RESULTS         = "results"       ;
    public static final String VAR_LOG             = "log"           ;
    public static final String VAR_DM_STATE        = "dmstate"       ;
    public static final String VAR_GENERIC_ALERTS  = "genericAlerts" ;
    public static final String VAR_SESSION_CONTEXT = "context"       ;

    public static final String SCRIPT_INIT           = "init()"               ;
    public static final String SCRIPT_NEXTOPERATIONS = "getNextOperations()"  ;
    public static final String SCRIPT_SETRESULTS     = "setOperationResults()";
    public static final String SCRIPT_SETGENERICALERTS = "setGenericAlerts()"   ;
    // endSession not endSession(): (int i) will be added in the method definition
    public static final String SCRIPT_ENDSESSION     = "endSession";



    // ------------------------------------------------------------ Private data

    private Interpreter interpreter;
    
    private SessionContext sessionContext = null;

    private final Logger log = Sync4jLogger.getLogger(Sync4jLoggerName.HANDLER);

    // -------------------------------------------------------------- Properties

    /**
     * Management processor name
     */
    private String name;

    /**
     * Sets management processor name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @see ManagementProcessor
     */
    public String getName() {
        return name;
    }

    /**
     * Script file. This path is relative to the config path
     */
    private String scriptFile;

    /**
     * Sets scriptFile
     *
     * @param scriptFile the new value
     */
    public void setScriptFile(String scriptFile) {
        this.scriptFile = scriptFile;
    }

    /**
     * Returns scriptFile
     *
     * @returns scriptFile property value
     */
    public String getScriptFile() {
        return scriptFile;
    }

    // ------------------------------------------------------------ Constructors

    /** Creates a new instance of ScriptMAnagementProcessor */
    public ScriptManagementProcessor() {
    }

    // ----------------------------------------------------- ManagementProcessor
    /**
     * Creates and initializes a new BeanShell interpreter.
     *
     * @see ManagementProcessor
     */
    public void beginSession(SessionContext context)
    throws ManagementException {
        
        this.sessionContext = context;
        String        sessionId = context.getSessionId();
        Principal     p         = context.getPrincipal();
        int           type      = context.getType();
        DevInfo       devInfo   = context.getDevInfo();
        DeviceDMState dmstate   = context.getDmstate();
        
        if (log.isLoggable(Level.FINEST)) {
            log.finest("Starting a new management session");
            log.finest("sessionContext: " + context         );
        }
        Configuration c = Configuration.getConfiguration();

        ConfigClassLoader cl = (ConfigClassLoader)c.getClassLoader();

        URL[] urls = cl.getURLs();

        interpreter = new Interpreter();

        //
        // 1. We add all the configuration paths to the BeanShell path
        //
        if (urls != null) {
            BshClassManager cm = interpreter.getClassManager();
            for (int i=0; i<urls.length; ++i) {
                //
                // NOTE: wrong path will be ignored
                //
                try {
                    cm.addClassPath(urls[i]);
                } catch (Exception e) {
                    if (log.isLoggable(Level.INFO)) {
                        log.info( "Wrong path "
                                + urls[i]
                                + " will not be added to the interpreter classpath"
                                );
                    }
                }
            }
        }

        //
        // 2. We set global objects and variables
        //
        try {
            interpreter.set(VAR_PROCESSOR,       this     );
            interpreter.set(VAR_SESSIONID,       sessionId);
            interpreter.set(VAR_PRINCIPAL,       p        );
            interpreter.set(VAR_MANAGEMENT_TYPE, type     );
            interpreter.set(VAR_DEVINFO,         devInfo  );
            interpreter.set(VAR_CONFIG,          c        );
            interpreter.set(VAR_LOG,             log      );
            interpreter.set(VAR_DM_STATE,        dmstate  );
            interpreter.set(VAR_OPERATIONS,      null     );
            interpreter.set(VAR_RESULTS,         null     );
            interpreter.set(VAR_GENERIC_ALERTS,  null     );
            interpreter.set(VAR_SESSION_CONTEXT, context  );
        } catch (Exception e) {
            throw new ManagementException(e.getMessage(), e);
        }

        //
        // 3. We evaluate the script file if any and run the init() function
        //
        if ((scriptFile != null) && (scriptFile.trim().length()>0)) {
            InputStream is = cl.getResourceAsStream(scriptFile);

            if (is == null) {
                if (log.isLoggable(Level.INFO)) {
                    log.info( "Initialization script file "
                            + scriptFile
                            + " not found in config path"
                            );
                }
            } else {
                try {
                    interpreter.eval(new InputStreamReader(is));
                    interpreter.eval(SCRIPT_INIT);
                } catch (Exception e) {
                    throw new ManagementException(e.getMessage(), e);
                } finally {
                    try {
                        is.close();
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    /**
     * @see ManagementProcessor
     */
    public void endSession(int completionCode) throws ManagementException {
        String script = SCRIPT_ENDSESSION + "(" + completionCode + ")";
        try {
            interpreter.eval(script);
        } catch (Exception e) {
            throw new ManagementException(e.getMessage(), e);
        } finally {
          interpreter = null;

          // Tracking Job
          this.trackJobLog(this.sessionContext);
        }

    }

    /**
     * @see ManagementProcessor
     */
    public ManagementOperation[] getNextOperations()
    throws ManagementException {
        try {
            interpreter.eval(SCRIPT_NEXTOPERATIONS);

            return (ManagementOperation[])interpreter.get(VAR_OPERATIONS);
        } catch (Exception e) {
            throw new ManagementException(e.getMessage(), e);
        }
    }

    /**
     * @see ManagementProcessor#setOperationResults(ManagementOperationResult[])
     */
    public void setOperationResults(ManagementOperationResult[] results)
    throws ManagementException {
        try {
            
            interpreter.set(VAR_RESULTS, results);
            interpreter.eval(SCRIPT_SETRESULTS);
        } catch (Exception e) {
            throw new ManagementException(e.getMessage(), e);
        }
    }


    /**
     * @see ManagementProcessor#setGenericAlert(com.funambol.framework.core.Alert[])
     */
    public void setGenericAlert(Alert[] genericAlerts) 
    throws ManagementException {
        
        try {
            interpreter.set(VAR_GENERIC_ALERTS, genericAlerts);
            interpreter.eval(SCRIPT_SETGENERICALERTS);
        } catch (Exception e) {
            throw new ManagementException(e.getMessage(), e);
        }
    }

    // ------------------------------------------------------------ Private data

}
