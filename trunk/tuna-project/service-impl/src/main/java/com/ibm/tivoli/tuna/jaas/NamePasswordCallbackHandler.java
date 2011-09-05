package com.ibm.tivoli.tuna.jaas;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextOutputCallback;
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

public class NamePasswordCallbackHandler implements CallbackHandler, RequesterAware, ContextAware, CredentialsAware {
  
  private static Log log = LogFactory.getLog(NamePasswordCallbackHandler.class);
  
  private Requester requester = null;
  private Context context = null;
  private Credentials credentials = null;

  public NamePasswordCallbackHandler() {
    super();
  }

  public NamePasswordCallbackHandler(Requester requester, Context context, Credentials credentials) {
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
      if (callbacks[i] instanceof TextOutputCallback) {

        // display a message according to a specified type
        // display the message according to the specified type
        TextOutputCallback toc = (TextOutputCallback) callbacks[i];
        switch (toc.getMessageType()) {
        case TextOutputCallback.INFORMATION:
          log.info(toc.getMessage());
          break;
        case TextOutputCallback.ERROR:
          log.error(toc.getMessage());
          break;
        case TextOutputCallback.WARNING:
          log.warn(toc.getMessage());
          break;
        default:
          throw new IOException("Unsupported message type: " + toc.getMessageType());
        }
      } else if (callbacks[i] instanceof NameCallback) {
        // prompt the user for a username
        NameCallback nc = (NameCallback) callbacks[i];
        // Extract uid from credential
        for (Credential cred: this.credentials.getCredentials()) {
            if (cred.getType() != null && cred.getType().equalsIgnoreCase("username")) {
               nc.setName(cred.getValueAsString());
               break;
            }
        }
      } else if (callbacks[i] instanceof PasswordCallback) {

        // Extract password from credential
        PasswordCallback pc = (PasswordCallback) callbacks[i];
        for (Credential cred: this.credentials.getCredentials()) {
            if (cred.getType() != null && cred.getType().equalsIgnoreCase("password")) {
               pc.setPassword(cred.getValueAsString().toCharArray());
               break;
            }
        }
      } else {
        throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
      }
    }
  }

}
