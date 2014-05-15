package com.tutucha.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.tutucha.event.AccessEvent;
import com.tutucha.event.EventListener;
import com.tutucha.event.Target;
import com.tutucha.event.TargetType;
import com.tutucha.event.UserIdentity;
import com.tutucha.event.UserIdentityType;
import com.tutucha.model.entity.Navigation;
import com.tutucha.model.service.DataService;

/**
 * Servlet Filter implementation class NavigationController
 */
public class NavigationController implements Filter {

  private FilterConfig fConfig;

  /**
   * Default constructor.
   */
  public NavigationController() {
    super();
  }

  /**
   * @see Filter#init(FilterConfig)
   */
  public void init(FilterConfig fConfig) throws ServletException {
    this.fConfig = fConfig;
  }

  /**
   * @see Filter#destroy()
   */
  public void destroy() {

  }

  /**
   * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
   */
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;

    String cookieUserId = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("_COOKIE_TRACK_ID_".equals(cookie.getName())) {
          cookieUserId = cookie.getValue();
          break;
        }
      }
    }

    if (StringUtils.isEmpty(cookieUserId)) {
      cookieUserId = String.format("%s", System.currentTimeMillis());
      Cookie cookie = new Cookie("_COOKIE_TRACK_ID_", cookieUserId);
      response.addCookie(cookie);
    }

    AccessEvent event = new AccessEvent();
    event.getUserIdentities().add(new UserIdentity(UserIdentityType.CLIENT_IP, request.getRemoteAddr()));
    event.getUserIdentities().add(new UserIdentity(UserIdentityType.USER_AGENT, request.getHeader("User-Agent")));
    event.getUserIdentities().add(new UserIdentity(UserIdentityType.COOKIE, cookieUserId));

    String op = request.getParameter("op");
    if ("navshow".equals(op)) {
      String navigationId = request.getParameter("id");
      if (StringUtils.isNotEmpty(navigationId)) {
        DataService dataService = (DataService) this.fConfig.getServletContext().getAttribute("dataService");
        EventListener eventListener = (EventListener) this.fConfig.getServletContext().getAttribute("eventListener");
        Navigation navigation = dataService.getNavigationById(navigationId);
        if (navigation != null) {
          Target target = new Target();
          target.setNavigationId(navigationId);
          target.setType(TargetType.ExternalHttpRedirect);
          target.setUrl(navigation.getUrl());
          eventListener.fire(event);
          
          response.sendRedirect(navigation.getUrl());
          return;
        }
      }
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
      return;
    }
    chain.doFilter(request, response);
  }

}
