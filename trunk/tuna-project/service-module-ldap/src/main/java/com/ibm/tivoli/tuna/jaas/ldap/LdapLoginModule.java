/*
 * @(#)LdapLoginModule.java 1.18 00/01/11
 *
 * Copyright 2000-2002 Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following 
 * conditions are met:
 * 
 * -Redistributions of source code must retain the above copyright  
 * notice, this  list of conditions and the following disclaimer.
 * 
 * -Redistribution in binary form must reproduct the above copyright 
 * notice, this list of conditions and the following disclaimer in 
 * the documentation and/or other materials provided with the 
 * distribution.
 * 
 * Neither the name of Oracle and/or its affiliates. or the names of 
 * contributors may be used to endorse or promote products derived 
 * from this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any 
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND 
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY 
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY 
 * DAMAGES OR LIABILITIES  SUFFERED BY LICENSEE AS A RESULT OF  OR 
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THE SOFTWARE OR 
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE 
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, 
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER 
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF 
 * THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that Software is not designed, licensed or 
 * intended for use in the design, construction, operation or 
 * maintenance of any nuclear facility. 
 */

package com.ibm.tivoli.tuna.jaas.ldap;

import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import com.ibm.tivoli.tuna.jaas.NamePrincipal;
import com.ibm.tivoli.tuna.jaas.ldap.dao.LdapServiceDao;
import com.ibm.tivoli.tuna.service.Attribute;
import com.ibm.tivoli.tuna.service.AttributeStatement;

/**
 * <p>
 * This sample LoginModule authenticates users with a password.
 * 
 * <p>
 * This LoginModule only recognizes one user: testUser
 * <p>
 * testUser's password is: testPassword
 * 
 * <p>
 * If testUser successfully authenticates itself, a <code>NamePrincipal</code>
 * with the testUser's user name is added to the Subject.
 * 
 * <p>
 * This LoginModule recognizes the debug option. If set to true in the login
 * Configuration, debug messages will be output to the output stream,
 * System.out.
 * 
 * @version 1.18, 01/11/00
 * @param <E>
 */
public class LdapLoginModule<E> implements LoginModule, ApplicationContextAware {
  
  private static Log log = LogFactory.getLog(LdapLoginModule.class);

  // initial state
  private Subject subject;
  private CallbackHandler callbackHandler;
  private Map sharedState;
  private Map options;

  // configurable option
  private boolean debug = false;
  private String ldapDaoBeanName = null;

  // the authentication status
  private boolean succeeded = false;
  private boolean commitSucceeded = false;

  // username and password
  private String username;
  private char[] password;

  // testUser's NamePrincipal
  private NamePrincipal userPrincipal;

  /**
   * Spring Bean factory
   */
  private ApplicationContext applicationContext = null;

  /**
   * Initialize this <code>LoginModule</code>.
   * 
   * <p>
   * 
   * @param subject
   *          the <code>Subject</code> to be authenticated.
   *          <p>
   * 
   * @param callbackHandler
   *          a <code>CallbackHandler</code> for communicating with the end user
   *          (prompting for user names and passwords, for example).
   *          <p>
   * 
   * @param sharedState
   *          shared <code>LoginModule</code> state.
   *          <p>
   * 
   * @param options
   *          options specified in the login <code>Configuration</code> for this
   *          particular <code>LoginModule</code>.
   */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {

    this.subject = subject;
    this.callbackHandler = callbackHandler;
    this.sharedState = sharedState;
    this.options = options;

    // initialize any configured options
    this.debug = "true".equalsIgnoreCase((String) options.get("debug"));
    this.ldapDaoBeanName = (String)options.get("ldapDaoBeanName");
  }

  /**
   * Authenticate the user by prompting for a user name and password.
   * 
   * <p>
   * 
   * @return true in all cases since this <code>LoginModule</code> should not be
   *         ignored.
   * 
   * @exception FailedLoginException
   *              if the authentication fails.
   *              <p>
   * 
   * @exception LoginException
   *              if this <code>LoginModule</code> is unable to perform the
   *              authentication.
   */
  public boolean login() throws LoginException {

    // prompt for a user name and password
    if (callbackHandler == null)
      throw new LoginException("Error: no CallbackHandler available " + "to garner authentication information from the user");

    Callback[] callbacks = new Callback[2];
    callbacks[0] = new NameCallback("user name: ");
    callbacks[1] = new PasswordCallback("password: ", false);

    try {
      callbackHandler.handle(callbacks);
      username = ((NameCallback) callbacks[0]).getName();
      char[] tmpPassword = ((PasswordCallback) callbacks[1]).getPassword();
      if (tmpPassword == null) {
        // treat a NULL password as an empty password
        tmpPassword = new char[0];
      }
      password = new char[tmpPassword.length];
      System.arraycopy(tmpPassword, 0, password, 0, tmpPassword.length);
      ((PasswordCallback) callbacks[1]).clearPassword();

    } catch (java.io.IOException ioe) {
      throw new LoginException(ioe.toString());
    } catch (UnsupportedCallbackException uce) {
      throw new LoginException("Error: " + uce.getCallback().toString() + " not available to garner authentication information " + "from the user");
    }

    // print debugging information
    if (debug) {
      log.info("\t\t[LdapLoginModule] " + "user entered user name: " + username);
      log.info("\t\t[LdapLoginModule] " + "user entered password: ");
    }

    // verify the username/password
    //LdapServiceDao ldapService = new LdapServiceDao();
    boolean usernameCorrect = false;
    boolean passwordCorrect = false;
    
    try {
      LdapServiceDao ldapService = (LdapServiceDao)this.applicationContext.getBean(this.ldapDaoBeanName);
    	AttributeStatement attrState = ldapService.searchUserDNandPwdByAccount(username);
    	List<Attribute> attrList = attrState.getAttributes();
    	for (Attribute attr : attrList) {
    		if("userpassword".equals(attr.getName().toLowerCase()) 
    				&& password.toString().equals(attr.getValues().get(0))) {
    			passwordCorrect = true;
    			
    			if (debug)
    		        log.info("\t\t[LdapLoginModule] " + "authentication succeeded");
    			
    		} else if("userdn".equals(attr.getName().toLowerCase())) {
    			usernameCorrect = true;
    			
    			UserDNPrincipal userDNPrincipal = new UserDNPrincipal(attr.getValues().get(0));
    		    if (!subject.getPrincipals().contains(userDNPrincipal))
    		        subject.getPrincipals().add(userDNPrincipal);
    		}
    		
    	}
    	
    	if(!usernameCorrect || !passwordCorrect) {
       	 	if (debug)
       	        log.info("\t\t[LdapLoginModule] " + "authentication failed");
       	 	succeeded = false;
       	 	username = null;
       	 	for (int i = 0; i < password.length; i++)
       	 		password[i] = ' ';
       	 	password = null;
       }
       
       if(!usernameCorrect) {
    	   throw new FailedLoginException("UserName Incorrect");
       } else if(!passwordCorrect) {
    	   throw new FailedLoginException("Password Incorrect");
       } else {
    	   succeeded = true;
    	   return true;
       }
    	
	} catch(EmptyResultDataAccessException e) {
		succeeded = false;
		throw new FailedLoginException("user isnot found");
	} catch(IncorrectResultSizeDataAccessException e){
		succeeded = false;
		throw new FailedLoginException("user found multi");
	}
    
    
  }

  /**
   * <p>
   * This method is called if the LoginContext's overall authentication
   * succeeded (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL
   * LoginModules succeeded).
   * 
   * <p>
   * If this LoginModule's own authentication attempt succeeded (checked by
   * retrieving the private state saved by the <code>login</code> method), then
   * this method associates a <code>NamePrincipal</code> with the
   * <code>Subject</code> located in the <code>LoginModule</code>. If this
   * LoginModule's own authentication attempted failed, then this method removes
   * any state that was originally saved.
   * 
   * <p>
   * 
   * @exception LoginException
   *              if the commit fails.
   * 
   * @return true if this LoginModule's own login and commit attempts succeeded,
   *         or false otherwise.
   */
  public boolean commit() throws LoginException {
    if (succeeded == false) {
      return false;
    } else {
      // add a Principal (authenticated identity)
      // to the Subject

      // assume the user we authenticated is the NamePrincipal
      userPrincipal = new NamePrincipal(username);
      if (!subject.getPrincipals().contains(userPrincipal))
        subject.getPrincipals().add(userPrincipal);

      if (debug) {
        log.info("\t\t[LdapLoginModule] " + "added NamePrincipal to Subject");
      }

      // in any case, clean out state
      username = null;
      for (int i = 0; i < password.length; i++)
        password[i] = ' ';
      password = null;

      commitSucceeded = true;
      return true;
    }
  }

  /**
   * <p>
   * This method is called if the LoginContext's overall authentication failed.
   * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules did
   * not succeed).
   * 
   * <p>
   * If this LoginModule's own authentication attempt succeeded (checked by
   * retrieving the private state saved by the <code>login</code> and
   * <code>commit</code> methods), then this method cleans up any state that was
   * originally saved.
   * 
   * <p>
   * 
   * @exception LoginException
   *              if the abort fails.
   * 
   * @return false if this LoginModule's own login and/or commit attempts
   *         failed, and true otherwise.
   */
  public boolean abort() throws LoginException {
    if (succeeded == false) {
      return false;
    } else if (succeeded == true && commitSucceeded == false) {
      // login succeeded but overall authentication failed
      succeeded = false;
      username = null;
      if (password != null) {
        for (int i = 0; i < password.length; i++)
          password[i] = ' ';
        password = null;
      }
      userPrincipal = null;
    } else {
      // overall authentication succeeded and commit succeeded,
      // but someone else's commit failed
      logout();
    }
    return true;
  }

  /**
   * Logout the user.
   * 
   * <p>
   * This method removes the <code>NamePrincipal</code> that was added by the
   * <code>commit</code> method.
   * 
   * <p>
   * 
   * @exception LoginException
   *              if the logout fails.
   * 
   * @return true in all cases since this <code>LoginModule</code> should not be
   *         ignored.
   */
  public boolean logout() throws LoginException {

    subject.getPrincipals().remove(userPrincipal);
    succeeded = false;
    succeeded = commitSucceeded;
    username = null;
    if (password != null) {
      for (int i = 0; i < password.length; i++)
        password[i] = ' ';
      password = null;
    }
    userPrincipal = null;
    return true;
  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext  = applicationContext;    
  }

}
