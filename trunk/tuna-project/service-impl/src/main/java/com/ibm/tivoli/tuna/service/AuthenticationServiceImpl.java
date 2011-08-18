/**
 * 
 */
package com.ibm.tivoli.tuna.service;

/**
 * @author zhaodonglu
 *
 */
public class AuthenticationServiceImpl implements AuthenticationService {

  /**
   * 
   */
  public AuthenticationServiceImpl() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.tuna.service.AuthenticationService#authentication(com.ibm.tivoli.tuna.service.Requester, com.ibm.tivoli.tuna.service.Context, com.ibm.tivoli.tuna.service.Credentials)
   */
  public AuthenticationResult authentication(Requester requester, Context context, Credentials credentials) {
    UserSubject issuer = new UserSubject("url", "http://tuna.tivoli.com.cn");
    UserSubject subject = new UserSubject("dn", "uid=abc, ou=bj, dc=sinopec, dc=com, dc=cn");
    AttributeStatement attributeStatment = new AttributeStatement();
    attributeStatment.getAttributes().add(new Attribute("uid", "string", "abc"));
    attributeStatment.getAttributes().add(new Attribute("email", "string", new String[]{"abc@sinopec.com.cn", "abc@gmail.com"}));
    attributeStatment.getAttributes().add(new Attribute("cn", "string", "ÕÅÈý"));
    return new AuthenticationResult(new Status("success", "success"), issuer, subject, attributeStatment);
  }

}
