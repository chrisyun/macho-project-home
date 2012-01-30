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

/**
 * Servlet Filter implementation class SAMLProfileFilter
 */
public class AuthenticationFilter implements Filter {
  
  private static Log log = LogFactory.getLog(AuthenticationFilter.class);
  
  public final static String ATTR_NAME_AUTHEN_METHOD = "__AUTHEN_METHOD__";
  
  private FilterConfig filterConfig;
  private String authenticationMethod = "urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified";

  /**
   * Default constructor.
   */
  public AuthenticationFilter() {
    super();
  }


  /**
   * @see Filter#init(FilterConfig)
   */
  public void init(FilterConfig fConfig) throws ServletException {
    this.filterConfig = fConfig;
    this.authenticationMethod = this.filterConfig.getInitParameter("AuthenticationMethod");
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
    if (session != null && session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR) != null 
        && this.authenticationMethod.equals(((SSOPrincipal)session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR)).getAuthenMethod())) {
      // Authenticated and matched authen method, do business
      chain.doFilter(request, response);
      return;
    }
    
    String uri = ((HttpServletRequest) request).getRequestURI().toString();
    String contextPath = ((HttpServletRequest) request).getContextPath();
    String requestPath = uri;
    if (!contextPath.equals("/")) {
      requestPath = requestPath.substring(contextPath.length());
    }
    if (requestPath.startsWith("/SSO")) {
      // Handled by SPProfileHandler, do business
      chain.doFilter(request, response);
      return;
    }
    
    // Un-authenticate, send saml authen request.
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
    SPProfileHandlerManager handlerManager = context.getBean("siam.sp.handler.manager", SPProfileHandlerManager.class) ;
    
    ProfileHandler<HTTPInTransport, HTTPOutTransport> profileHandler = handlerManager.getAuthenticationHandler(httpRequest);
    AbstractErrorHandler errorHandler = handlerManager.getErrorHandler();

    HTTPInTransport profileReq = new HttpServletRequestAdapter(httpRequest);
    httpRequest.setAttribute(ATTR_NAME_AUTHEN_METHOD, this.authenticationMethod);
    HTTPOutTransport profileResp = new HttpServletResponseAdapter(httpResponse, httpRequest.isSecure());
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
