/**
 * 
 */
package com.ibm.siam.agent.web;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import edu.internet2.middleware.shibboleth.common.config.BaseReloadableService;
import edu.internet2.middleware.shibboleth.common.profile.AbstractErrorHandler;
import edu.internet2.middleware.shibboleth.common.profile.ProfileHandler;
import edu.internet2.middleware.shibboleth.common.profile.ProfileHandlerManager;
import edu.internet2.middleware.shibboleth.common.profile.provider.AbstractRequestURIMappedProfileHandler;
import edu.internet2.middleware.shibboleth.common.service.ServiceException;

/**
 * @author zhaodonglu
 * 
 */
public class SPProfileHandlerManager extends BaseReloadableService implements ProfileHandlerManager {

  private static Log log = LogFactory.getLog(SPProfileHandlerManager.class);

  /** Map of request paths to profile handlers. */
  private Map<String, AbstractRequestURIMappedProfileHandler> profileHandlers = new HashMap<String, AbstractRequestURIMappedProfileHandler>();

  /** Handler used for errors. */
  private AbstractErrorHandler errorHandler;

  private ProfileHandler authenticationHandler;

  /**
   * 
   */
  public SPProfileHandlerManager() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * edu.internet2.middleware.shibboleth.common.profile.ProfileHandlerManager
   * #getErrorHandler()
   */
  public AbstractErrorHandler getErrorHandler() {
    return errorHandler;
  }

  /**
   * @return
   */
  public ProfileHandler getAuthenticationHandler(HttpServletRequest httpRequest) {
    return authenticationHandler;
  }

  /**
   * Gets the registered profile handlers.
   * 
   * @return registered profile handlers
   */
  public Map<String, AbstractRequestURIMappedProfileHandler> getProfileHandlers() {
    return profileHandlers;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * edu.internet2.middleware.shibboleth.common.profile.ProfileHandlerManager
   * #getProfileHandler(javax.servlet.ServletRequest)
   */
  public ProfileHandler getProfileHandler(ServletRequest request) {
    String uri = ((HttpServletRequest) request).getRequestURI().toString();
    String contextPath = ((HttpServletRequest) request).getContextPath();
    String requestPath = uri;
    if (!contextPath.equals("/")) {
      requestPath = requestPath.substring(contextPath.length());
    }
    
    log.debug(String.format("%s: Looking up profile handler for request path: %s", getId(), requestPath));

    Lock readLock = getReadWriteLock().readLock();
    readLock.lock();
    ProfileHandler handler = null;
    try {
      handler = profileHandlers.get(requestPath);
    } finally {
      readLock.unlock();
    }

    if (handler != null) {
      log.debug(String.format("%s: Located profile handler of the following type for the request path: %s", getId(),
          handler.getClass().getName()));
    } else {
      log.debug(String.format("%s: No profile handler registered for request path %s", getId(), requestPath));
    }
    return handler;
  }

  /**
   * Sets the error handler.
   * 
   * @param handler error handler
   */
  public void setErrorHandler(AbstractErrorHandler handler) {
      if (handler == null) {
          throw new IllegalArgumentException("Error handler may not be null");
      }
      errorHandler = handler;
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.internet2.middleware.shibboleth.common.config.BaseService#
   * onNewContextCreated(org.springframework.context.ApplicationContext)
   */
  @Override
  protected void onNewContextCreated(ApplicationContext newServiceContext) throws ServiceException {
    log.debug(String.format("%s: Loading new configuration into service", getId()));
    AbstractErrorHandler oldErrorHandler = errorHandler;
    Map<String, AbstractRequestURIMappedProfileHandler> oldProfileHandlers = profileHandlers;

    try {
      loadNewErrorHandler(newServiceContext);
      loadNewProfileHandlers(newServiceContext);
      loadNewAuthenticationHandlers(newServiceContext);
    } catch (Exception e) {
      errorHandler = oldErrorHandler;
      profileHandlers = oldProfileHandlers;
      throw new ServiceException(getId() + " configuration is not valid, retaining old configuration", e);
    }
  }

  /**
   * Reads the new error handler from the newly created application context and loads it into this manager.
   * 
   * @param newServiceContext newly created application context
   */
  protected void loadNewErrorHandler(ApplicationContext newServiceContext) {
      String[] errorBeanNames = newServiceContext.getBeanNamesForType(AbstractErrorHandler.class);
      log.debug(String.format("%s: Loading %s new error handler.", getId(), errorBeanNames.length));

      errorHandler = (AbstractErrorHandler) newServiceContext.getBean(errorBeanNames[0]);
      log.debug(String.format("%s: Loaded new error handler of type: %s", getId(), errorHandler.getClass().getName()));
  }

  /**
   * Reads the new error handler from the newly created application context and loads it into this manager.
   * 
   * @param newServiceContext newly created application context
   */
  protected void loadNewAuthenticationHandlers(ApplicationContext newServiceContext) {
      String[] beanNames = newServiceContext.getBeanNamesForType(SPAuthenticationHandler.class);
      log.debug(String.format("%s: Loading %s new authentication handler.", getId(), beanNames.length));

      this.authenticationHandler = (ProfileHandler) newServiceContext.getBean(beanNames[0]);
      log.debug(String.format("%s: Loaded new authenticationHandler handler of type: %s", getId(), authenticationHandler.getClass().getName()));
  }

  /**
   * Reads the new profile handlers from the newly created application context and loads it into this manager.
   * 
   * @param newServiceContext newly created application context
   */
  protected void loadNewProfileHandlers(ApplicationContext newServiceContext) {
      String[] profileBeanNames = newServiceContext.getBeanNamesForType(AbstractRequestURIMappedProfileHandler.class);
      log.debug(String.format("%s: Loading %s new profile handlers.", getId(), profileBeanNames.length));

      Map<String, AbstractRequestURIMappedProfileHandler> newProfileHandlers = new HashMap<String, AbstractRequestURIMappedProfileHandler>();
      AbstractRequestURIMappedProfileHandler<?, ?> profileHandler;
      for (String profileBeanName : profileBeanNames) {
          profileHandler = (AbstractRequestURIMappedProfileHandler) newServiceContext.getBean(profileBeanName);
          for (String requestPath : profileHandler.getRequestPaths()) {
              newProfileHandlers.put(requestPath, profileHandler);
              log.debug(String.format("%s: Loaded profile handler for handling requests to request path %s", getId(), requestPath));
          }
      }
      profileHandlers = newProfileHandlers;
  }
}
