/**
 * 
 */
package com.ibm.siam.agent.sp.handler;

import java.util.ArrayList;
import java.util.List;

import org.opensaml.ws.transport.http.HTTPInTransport;
import org.opensaml.ws.transport.http.HTTPOutTransport;

import edu.internet2.middleware.shibboleth.common.profile.ProfileException;
import edu.internet2.middleware.shibboleth.common.profile.provider.AbstractRequestURIMappedProfileHandler;

/**
 * @author zhaodonglu
 * 
 */
public abstract class BaseProfileHandler extends AbstractRequestURIMappedProfileHandler<HTTPInTransport, HTTPOutTransport> {

  /** Request paths that to which this profile handler will respond. */
  private List<String> requestPaths = new ArrayList<String>();
  

  /** Constructor. */
  protected BaseProfileHandler() {
    super();
  }

  /**
   * Gets the request paths that to which this profile handler will respond.
   * 
   * @return request paths that to which this profile handler will respond
   */
  public List<String> getRequestPaths() {
      return requestPaths;
  }

  /**
   * Sets the request paths that to which this profile handler will respond.
   * 
   * @param paths request paths that to which this profile handler will respond
   */
  public void setRequestPaths(List<String> paths) {
      requestPaths = paths;
  }

  /* (non-Javadoc)
   * @see edu.internet2.middleware.shibboleth.common.profile.ProfileHandler#processRequest(org.opensaml.ws.transport.InTransport, org.opensaml.ws.transport.OutTransport)
   */
  public abstract void processRequest(HTTPInTransport inTransport, HTTPOutTransport outTransport) throws ProfileException;

}
