/**
 * 
 */
package com.ibm.tivoli.tuna.config;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ibm.tivoli.tuna.jaas.SpringLoginContext;
import com.ibm.tivoli.tuna.service.AuthenticationResultHandler;
import com.ibm.tivoli.tuna.service.Context;
import com.ibm.tivoli.tuna.service.ContextAware;
import com.ibm.tivoli.tuna.service.Credentials;
import com.ibm.tivoli.tuna.service.CredentialsAware;
import com.ibm.tivoli.tuna.service.LoginContextManager;
import com.ibm.tivoli.tuna.service.Requester;
import com.ibm.tivoli.tuna.service.RequesterAware;
import com.ibm.tivoli.tuna.service.TunaException;

/**
 * @author zhaodonglu
 *
 */
public class LoginContextManagerImpl implements LoginContextManager, ApplicationContextAware {
  
  private static Log log = LogFactory.getLog(LoginContextManagerImpl.class);

  private Configuration configuration = null;

  /**
   * Spring Bean Factory
   */
  private ApplicationContext applicationContext = null;

  public LoginContextManagerImpl() {
    super();
  }

  public LoginContextManagerImpl(Configuration configuration) {
    super();
    this.configuration = configuration;
  }

  /**
   * @return the configuration
   */
  public Configuration getConfiguration() {
    return configuration;
  }

  /**
   * @param configuration the configuration to set
   */
  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.tuna.service.LoginContextManager#getConfiguration()
   */
  public LoginContext getLoginContext(Requester requester, Context context, Credentials credentials) throws TunaException {
    String loginModuleName;
    try {
      loginModuleName = getLoginModuleName(context);
    } catch (Exception e1) {
      throw new TunaException("Missing LoginModuleName in Request Context");
    }

    try {
      log.debug(String.format("Loading login module: [%s]", loginModuleName));
      CallbackHandler callbackHandler = this.getCallbackHandler(loginModuleName, requester, context, credentials);
      LoginContext lc = new SpringLoginContext(loginModuleName, null, callbackHandler, this.configuration);
      if (lc instanceof ApplicationContextAware) {
         ((ApplicationContextAware)lc).setApplicationContext(this.applicationContext);
      }
      return lc;
    } catch (LoginException e) {
      throw new TunaException("Fail to load LoginContext for [" + loginModuleName + "], cause: " + e.getMessage(), e);
    }
  }

  private String getLoginModuleName(Context context) {
    String loginModuleName;
    loginModuleName = context.getParameter("LoginModuleName").getValues().get(0);
    return loginModuleName;
  }

  protected CallbackHandler getCallbackHandler(String loginModuleName, Requester requester, Context context, Credentials credentials) {
    if (this.configuration instanceof LoginContextItemFinder) {
       LoginContextItem  item = ((LoginContextItemFinder)this.configuration).findLoginContextItemByName(loginModuleName);
       Class clazz;
       log.debug(String.format("Loading login module call back handler: [%s]", item.getCallbackHandlerClass()));       
       try {
         clazz = Class.forName(item.getCallbackHandlerClass());
       } catch (ClassNotFoundException e) {
         throw new RuntimeException("Could not load class for [" + item.getCallbackHandlerClass() + "]", e);
       }
       CallbackHandler handler;
       try {
         handler = (CallbackHandler)clazz.newInstance();
       } catch (InstantiationException e) {
         throw new RuntimeException("Could not create an instance for class [" + clazz.getCanonicalName() + "]", e);
       } catch (IllegalAccessException e) {
         throw new RuntimeException("Could not create an instance for class [" + clazz.getCanonicalName() + "]", e);
       }
       if (handler != null) {
          if (handler instanceof RequesterAware) {
             ((RequesterAware)handler).setRequester(requester);
             log.debug(String.format("RequestAware, set requester: [%s]", requester));       
          }
          if (handler instanceof ContextAware) {
            ((ContextAware)handler).setContext(context);
            log.debug(String.format("ContextAware, set requester: [%s]", context));       
          }
          if (handler instanceof CredentialsAware) {
            ((CredentialsAware)handler).setCredentials(credentials);
            log.debug(String.format("CredentialsAware, set credentials: [%s]", credentials));       
          }
       }
       return handler;
    }
    throw new RuntimeException("Could not found CallbackHandler for [" + loginModuleName + "]");
  }

  public AuthenticationResultHandler getAuthenticationHandler(Requester requester, Context context, Credentials credentials) {
    String loginModuleName = this.getLoginModuleName(context);
    if (this.configuration instanceof LoginContextItemFinder) {
      LoginContextItem  item = ((LoginContextItemFinder)this.configuration).findLoginContextItemByName(loginModuleName );
      Class clazz;
      log.debug(String.format("Loading AuthenticationResultHandler: [%s]", item.getResultHandlerClass()));       
      try {
        clazz = Class.forName(item.getResultHandlerClass());
      } catch (ClassNotFoundException e) {
        throw new RuntimeException("Could not load class for [" + item.getCallbackHandlerClass() + "]", e);
      }
      AuthenticationResultHandler handler;
      try {
        handler = (AuthenticationResultHandler)clazz.newInstance();
      } catch (InstantiationException e) {
        throw new RuntimeException("Could not create an instance for class [" + clazz.getCanonicalName() + "]", e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException("Could not create an instance for class [" + clazz.getCanonicalName() + "]", e);
      }
      if (handler != null) {
         if (handler instanceof RequesterAware) {
            ((RequesterAware)handler).setRequester(requester);
            log.debug(String.format("RequestAware, set requester: [%s]", requester));       
         }
         if (handler instanceof ContextAware) {
           ((ContextAware)handler).setContext(context);
           log.debug(String.format("ContextAware, set requester: [%s]", context));       
         }
         if (handler instanceof CredentialsAware) {
           ((CredentialsAware)handler).setCredentials(credentials);
           log.debug(String.format("CredentialsAware, set credentials: [%s]", credentials));       
         }
         if (handler instanceof ApplicationContextAware) {
           ((ApplicationContextAware)handler).setApplicationContext(this.applicationContext);
           log.debug(String.format("ApplicationContextAware, set applicationContext for class: [%s]", handler.getClass().getCanonicalName()));       
         }
      }
      return handler;
   }
   throw new RuntimeException("Could not found AuthenticationResultHandler for [" + loginModuleName + "]");

  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext  = applicationContext;
  }
}
