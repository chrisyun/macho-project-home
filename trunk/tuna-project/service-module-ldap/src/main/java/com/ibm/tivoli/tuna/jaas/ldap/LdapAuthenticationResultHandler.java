package com.ibm.tivoli.tuna.jaas.ldap;

import java.security.Principal;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ibm.tivoli.tuna.dao.ILdapUserDao;
import com.ibm.tivoli.tuna.service.AttributeStatement;
import com.ibm.tivoli.tuna.service.AuthenticationResult;
import com.ibm.tivoli.tuna.service.AuthenticationResultHandler;
import com.ibm.tivoli.tuna.service.UserSubject;

public class LdapAuthenticationResultHandler implements AuthenticationResultHandler, ApplicationContextAware {

  /**
   * 
   */
  private String ldapDaoBeanName = "ldapUserService";

  public LdapAuthenticationResultHandler() {
    super();
  }
  
  

  /**
   * @return the ldapDaoBeanName
   */
  public String getLdapDaoBeanName() {
    return ldapDaoBeanName;
  }

  /**
   * @param ldapDaoBeanName the ldapDaoBeanName to set
   */
  public void setLdapDaoBeanName(String ldapDaoBeanName) {
    this.ldapDaoBeanName = ldapDaoBeanName;
  }



  /**
   * Spring Bean factory
   */
  private ApplicationContext applicationContext = null;

  /* (non-Javadoc)
   * @see com.ibm.tivoli.tuna.service.AuthenticationResultHandler#fullfill(javax.security.auth.login.LoginContext, com.ibm.tivoli.tuna.service.AuthenticationResult)
   */
  public void fullfill(LoginContext lc, AuthenticationResult result) {
    Subject lcSubject = lc.getSubject();
    String userDN = "";

    for (Principal principal : lcSubject.getPrincipals()) {
      String pname = principal.getName();
      UserSubject userSubject = new UserSubject(principal.getClass().getCanonicalName(), pname);
      result.getSubjects().add(userSubject);

      if (UserDNPrincipal.class.getName().equals(principal.getClass().getCanonicalName())) {
        userDN = pname;
      }
    }

    //LdapServiceDao ldapService = new LdapServiceDao();
    ILdapUserDao ldapService = (ILdapUserDao)this.applicationContext.getBean(this.ldapDaoBeanName);

    AttributeStatement attributeStatment = ldapService.searchUserEntityByDN(userDN);
    result.setAttributeStatement(attributeStatment);
  }

  /* (non-Javadoc)
   * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
   */
  public void setApplicationContext(ApplicationContext arg0) throws BeansException {
    this.applicationContext  = arg0;    
  }

}
