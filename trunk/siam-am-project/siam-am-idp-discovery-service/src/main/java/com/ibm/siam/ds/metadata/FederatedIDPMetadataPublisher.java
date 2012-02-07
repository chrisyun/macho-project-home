/**
 * 
 */
package com.ibm.siam.ds.metadata;

import org.opensaml.saml2.metadata.EntitiesDescriptor;

/**
 * Discovery服务包含一个完整的IDP清单，所有的这些 IDP共同构成一个IDP服务云. 此接口定义向外发布IDP清单的服务.
 * @author zhaodonglu
 *
 */
public interface FederatedIDPMetadataPublisher {
  
  /**
   * Return all IdP metadata which defined in configuration.
   * @return
   */
  public abstract EntitiesDescriptor getAllIdPMetadata();
}
