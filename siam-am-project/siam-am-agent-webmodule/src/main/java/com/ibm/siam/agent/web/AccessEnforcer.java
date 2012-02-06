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

import com.ibm.siam.agent.common.SSOPrincipal;
import com.ibm.siam.agent.sp.handler.SPProfileHandlerManager;

import edu.internet2.middleware.shibboleth.common.profile.AbstractErrorHandler;
import edu.internet2.middleware.shibboleth.common.profile.ProfileHandler;

/**
 * Servlet Filter implementation class SAMLProfileFilter
 */
public class AccessEnforcer implements Filter {
  
  private static Log log = LogFactory.getLog(AccessEnforcer.class);
  
  public final static String ATTR_NAME_AUTHEN_METHOD = "__AUTHEN_METHOD__";
  
  private FilterConfig filterConfig;
  private String authenticationMethod = "urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified";

  /**
   * Default constructor.
   */
  public AccessEnforcer() {
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
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    
    HttpServletRequest httpRequest = (HttpServletRequest)request;
    HttpServletResponse httpResponse = (HttpServletResponse)response;

    if (isIgnoreUrl(httpRequest)) {
      // Handled by SPProfileHandler, do business
      chain.doFilter(request, response);
      return;
    }
    
    // Need to authenticate?
    if (needToAuthenticate(httpRequest)) {
      doAuthentication(httpRequest, httpResponse);
      return;
    }
    // Authenticated and matched authen method, do business
    chain.doFilter(request, response);
    return;
  }


  /**
   * @param httpRequest
   * @param httpResponse
   */
  private void doAuthentication(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
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
      httpRequest.setAttribute(AbstractErrorHandler.ERROR_KEY, e);
      log.error("Failure to process http request, cause: " + e.getMessage(), e);
    }
    errorHandler.processRequest(profileReq, profileResp);
    return;
  }


  /**
   * 根据当前的应用本地的会话状态及认证的级别判断是否需要进行认证。
   * 
   * @param session
   * @return
   */
  protected boolean needToAuthenticate(HttpServletRequest httpRequest) {
    return this.isAuthenticated(httpRequest) && this.isMatchWithRequiredAuthenMethod(httpRequest);
  }
  
  /**
   * 判断当前用户是否已经认证
   * @param httpRequest
   * @return  true 已经认证
   */
  protected boolean isAuthenticated(HttpServletRequest httpRequest) {
    HttpSession session = httpRequest.getSession(false);
    if (session != null && session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR) != null) {
       return true;
    }
    return false;
  }

  /**
   * 判断当前用户的认证级别是否满足被保护资源要求的认证方法。
   * @param httpRequest
   * @return  true - 满足要求
   * 
   */
  protected boolean isMatchWithRequiredAuthenMethod(HttpServletRequest httpRequest) {
    HttpSession session = httpRequest.getSession(false);
    if (session == null || session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR) == null) {
       // Un-authen
       return false;
    }
    return this.authenticationMethod.equals(((SSOPrincipal)session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR)).getAuthenMethod());
  }

  /**
   * @param httpRequest
   * @return
   */
  private boolean isIgnoreUrl(HttpServletRequest httpRequest) {
    String uri = httpRequest.getRequestURI().toString();
    String contextPath = httpRequest.getContextPath();
    String requestPath = uri;
    if (!contextPath.equals("/")) {
      requestPath = requestPath.substring(contextPath.length());
    }
    return requestPath.startsWith("/SSO");
  }
}
