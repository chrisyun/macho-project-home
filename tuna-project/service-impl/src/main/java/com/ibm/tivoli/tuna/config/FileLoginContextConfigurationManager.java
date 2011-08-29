/**
 * 
 */
package com.ibm.tivoli.tuna.config;

import java.security.Principal;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import com.ibm.tivoli.tuna.jaas.NamePasswordCallbackHandler;
import com.ibm.tivoli.tuna.service.Attribute;
import com.ibm.tivoli.tuna.service.AttributeStatement;
import com.ibm.tivoli.tuna.service.AuthenticationResult;
import com.ibm.tivoli.tuna.service.AuthenticationResultHandler;
import com.ibm.tivoli.tuna.service.Context;
import com.ibm.tivoli.tuna.service.Credentials;
import com.ibm.tivoli.tuna.service.LoginContextManager;
import com.ibm.tivoli.tuna.service.Requester;
import com.ibm.tivoli.tuna.service.TunaException;
import com.ibm.tivoli.tuna.service.UserSubject;

/**
 * @author zhaodonglu
 *
 */
public class FileLoginContextConfigurationManager implements LoginContextManager {

  private String configFile;

  /**
   * 
   */
  public FileLoginContextConfigurationManager(String configFile) {
    this.configFile = configFile;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.tuna.service.LoginContextManager#getConfiguration()
   */
  public LoginContext getLoginContext(Requester requester, Context context, Credentials credentials) throws TunaException {
    // TODO Fix hardcode
    System.setProperty("java.security.auth.login.config", "c:/users/ibm_admin/workspace/tuna-project/service-impl/src/test/resources/sample_jaas.config");
    Configuration config = Configuration.getConfiguration();
    
    String loginModuleName;
    try {
      loginModuleName = context.getParameter("LoginModuleName").getValues().get(0);
    } catch (Exception e1) {
      throw new TunaException("Missing LoginModuleName in Request Context");
    }

    try {
      CallbackHandler callbackHandler = this.getCallbackHandler(loginModuleName, requester, context, credentials);
      LoginContext lc = new LoginContext(loginModuleName, null, callbackHandler, config);
      return lc;
    } catch (LoginException e) {
      throw new TunaException("Fail to load LoginContext for [" + loginModuleName + "], cause: " + e.getMessage(), e);
    }
  }

  /**
   * @return the configFile
   */
  public String getConfigFile() {
    return configFile;
  }

  /**
   * @param configFile the configFile to set
   */
  public void setConfigFile(String configFile) {
    this.configFile = configFile;
  }

  protected CallbackHandler getCallbackHandler(String loginModuleName, Requester requester, Context context, Credentials credentials) {
    // TODO Auto-generated method stub
    if (loginModuleName.equalsIgnoreCase("LDAP-SIMPLE")) {
       return new NamePasswordCallbackHandler(requester, context, credentials);
    }
    throw new RuntimeException("Could not found CallbackHandler for [" + loginModuleName + "]");
  }

  public AuthenticationResultHandler getAuthenticationHandler(AuthenticationResult result) {
    // TODO Implements
    return new AuthenticationResultHandler(){

      public void fullfill(LoginContext lc, AuthenticationResult result) {
        Subject lcSubject = lc.getSubject();
        for (Principal principal: lcSubject.getPrincipals()) {
            String pname = principal.getName();
            UserSubject userSubject = new UserSubject(principal.getClass().getCanonicalName(), pname);
            result.getSubjects().add(userSubject);
        }
        AttributeStatement attributeStatment = new AttributeStatement();
        attributeStatment.getAttributes().add(new Attribute("uid", "string", "testUser"));
        attributeStatment.getAttributes().add(new Attribute("email", "string", new String[] { "test@sinopec.com.cn", "testuser@gmail.com" }));
        attributeStatment.getAttributes().add(new Attribute("cn", "string", "ÕÅÈý"));
        result.setAttributeStatement(attributeStatment);       
      }};
  }
}
