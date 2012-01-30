/**
 * 
 */
package com.ibm.siam.agent.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.opensaml.ws.transport.http.HTTPInTransport;
import org.opensaml.ws.transport.http.HTTPOutTransport;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import edu.internet2.middleware.shibboleth.common.profile.ProfileException;

/**
 * @author zhaodonglu
 * 
 */
public class SAML2LogoutHandler extends BaseProfileHandler {

  /**
   * 
   */
  public SAML2LogoutHandler() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.internet2.middleware.shibboleth.common.profile.ProfileHandler#
   * processRequest(org.opensaml.ws.transport.InTransport,
   * org.opensaml.ws.transport.OutTransport)
   */
  public void processRequest(HTTPInTransport inTransport, HTTPOutTransport outTransport) throws ProfileException {
    ServletRequestAttributes servletAttrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = servletAttrs.getRequest();
    HttpSession session = request.getSession(false);

    if (session != null) {
      session.invalidate();
    }
    // TODO
    outTransport.sendRedirect(request.getContextPath());
  }

}
