/**
 * 
 */
package com.ibm.tivoli.tuna.jaas.sample;

import java.security.Principal;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;

import com.ibm.tivoli.tuna.service.Attribute;
import com.ibm.tivoli.tuna.service.AttributeStatement;
import com.ibm.tivoli.tuna.service.AuthenticationResult;
import com.ibm.tivoli.tuna.service.AuthenticationResultHandler;
import com.ibm.tivoli.tuna.service.UserSubject;

/**
 * @author zhaodonglu
 * 
 */
public class SampleResultHandler implements AuthenticationResultHandler {

  /**
   * 
   */
  public SampleResultHandler() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.tuna.service.AuthenticationResultHandler#fullfill(javax.
   * security.auth.login.LoginContext,
   * com.ibm.tivoli.tuna.service.AuthenticationResult)
   */
  public void fullfill(LoginContext lc, AuthenticationResult result) {
    Subject lcSubject = lc.getSubject();
    for (Principal principal : lcSubject.getPrincipals()) {
      String pname = principal.getName();
      UserSubject userSubject = new UserSubject(principal.getClass().getCanonicalName(), pname);
      result.getSubjects().add(userSubject);
    }
    AttributeStatement attributeStatment = new AttributeStatement();
    attributeStatment.getAttributes().add(new Attribute("uid", "string", "testUser"));
    attributeStatment.getAttributes().add(new Attribute("email", "string", new String[] { "test@sinopec.com.cn", "testuser@gmail.com" }));
    attributeStatment.getAttributes().add(new Attribute("cn", "string", "ÕÅÈý"));
    result.setAttributeStatement(attributeStatment);
  }

}
