package com.ibm.tivoli.tuna.module.ltpa;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.tuna.service.Context;
import com.ibm.tivoli.tuna.service.ContextAware;
import com.ibm.tivoli.tuna.service.Credential;
import com.ibm.tivoli.tuna.service.Credentials;
import com.ibm.tivoli.tuna.service.CredentialsAware;
import com.ibm.tivoli.tuna.service.Requester;
import com.ibm.tivoli.tuna.service.RequesterAware;

public class LtpaTokenCallbackHandler implements CallbackHandler, RequesterAware, ContextAware, CredentialsAware {
  
  private static Log log = LogFactory.getLog(LtpaTokenCallbackHandler.class);
  
  private Requester requester = null;
  private Context context = null;
  private Credentials credentials = null;

  public LtpaTokenCallbackHandler() {
    super();
  }

  public LtpaTokenCallbackHandler(Requester requester, Context context, Credentials credentials) {
    super();
    this.requester = requester;
    this.context = context;
    this.credentials = credentials;
  }

  /**
   * @return the requester
   */
  public Requester getRequester() {
    return requester;
  }

  /**
   * @param requester the requester to set
   */
  public void setRequester(Requester requester) {
    this.requester = requester;
  }

  /**
   * @return the context
   */
  public Context getContext() {
    return context;
  }

  /**
   * @param context the context to set
   */
  public void setContext(Context context) {
    this.context = context;
  }

  /**
   * @return the credentials
   */
  public Credentials getCredentials() {
    return credentials;
  }

  /**
   * @param credentials the credentials to set
   */
  public void setCredentials(Credentials credentials) {
    this.credentials = credentials;
  }

  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    for (int i = 0; i < callbacks.length; i++) {
      if (callbacks[i] instanceof LtpaTokenCallback) {
        // prompt the user for a username
        LtpaTokenCallback nc = (LtpaTokenCallback) callbacks[i];
        // Extract uid from credential
        for (Credential cred: this.credentials.getCredentials()) {
            if (cred.getType() != null && cred.getType().equalsIgnoreCase("token")) {
               nc.setToken(cred.getValueAsString());
               break;
            }
        }
      } else {
        throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
      }
    }
  }

}
