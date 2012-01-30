package com.ibm.siam.agent.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensaml.ws.transport.http.HTTPInTransport;
import org.opensaml.ws.transport.http.HTTPOutTransport;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.opensaml.ws.transport.http.HttpServletResponseAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.internet2.middleware.shibboleth.common.profile.AbstractErrorHandler;
import edu.internet2.middleware.shibboleth.common.profile.ProfileHandler;
import edu.internet2.middleware.shibboleth.common.profile.ProfileHandlerManager;

/**
 * Servlet Filter implementation class SAMLProfileFilter
 */
public class SAMLProfileFilter implements Filter {
  
  private static Log log = LogFactory.getLog(SAMLProfileFilter.class);
  private FilterConfig filterConfig;

  /**
   * Default constructor.
   */
  public SAMLProfileFilter() {
    super();
  }


  /**
   * @see Filter#init(FilterConfig)
   */
  public void init(FilterConfig fConfig) throws ServletException {
    this.filterConfig = fConfig;
  }

  /**
   * @see Filter#destroy()
   */
  public void destroy() {
  }

  /**
   * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
   */
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {
    
    HttpServletRequest httpRequest = (HttpServletRequest)request;
    HttpServletResponse httpResponse = (HttpServletResponse)response;

    HttpSession session = httpRequest.getSession();
    if (session != null && session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR) != null) {
      // Authenticated, do business
      chain.doFilter(request, response);
      return;
    }
    
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
    ProfileHandlerManager handlerManager = context.getBean("siam.sp.handler.manager", ProfileHandlerManager.class) ;
    ProfileHandler<HTTPInTransport, HTTPOutTransport> profileHandler = handlerManager.getProfileHandler(request);
    if (profileHandler == null) {
      chain.doFilter(request, response);
      return;
    }
    HTTPInTransport profileReq = new HttpServletRequestAdapter(httpRequest);
    HTTPOutTransport profileResp = new HttpServletResponseAdapter(httpResponse, httpRequest.isSecure());

    AbstractErrorHandler errorHandler = handlerManager.getErrorHandler();
    try {
      profileHandler.processRequest(profileReq, profileResp);
      return;
    } catch (Exception e) {
      //throw new IOException(e);
      request.setAttribute(AbstractErrorHandler.ERROR_KEY, e);
      log.error("Failure to process http request, cause: " + e.getMessage(), e);
    }
    errorHandler.processRequest(profileReq, profileResp);
    return;
  }
}
