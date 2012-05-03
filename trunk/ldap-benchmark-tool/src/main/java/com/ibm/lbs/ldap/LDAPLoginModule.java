/**
 * 
 */
package com.ibm.lbs.ldap;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapUtils;

/**
 * @author zhaodonglu
 * 
 */
public class LDAPLoginModule {

  private LdapTemplate ldapTemplate = null;

  private PerformanceMonitor performanceMonitor = null;

  /**
   * 
   */
  public LDAPLoginModule() {
    super();
  }

  public LdapTemplate getLdapTemplate() {
    return ldapTemplate;
  }

  public void setLdapTemplate(LdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  public PerformanceMonitor getPerformanceMonitor() {
    return performanceMonitor;
  }

  public void setPerformanceMonitor(PerformanceMonitor performanceMonitor) {
    this.performanceMonitor = performanceMonitor;
  }

  public String searchUserDNByAccount(String userName) {
    AndFilter filter = new AndFilter();

    filter.and(new EqualsFilter("uid", userName));
    try {
      return (String) ldapTemplate.searchForObject("", filter.encode(), new AbstractContextMapper() {

        @Override
        protected Object doMapFromContext(DirContextOperations ctx) {
          return ctx.getNameInNamespace();
        }
      });
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public void login(String username, byte[] password) throws Exception {
    DirContext ctx = null;
    try {
      String userDn = this.searchUserDNByAccount(username);
      if (userDn != null) {

        SearchControls cons = new SearchControls();
        cons.setReturningAttributes(new String[0]); // Return no attrs
        cons.setSearchScope(SearchControls.OBJECT_SCOPE); // Search object only
        ctx = ldapTemplate.getContextSource().getReadOnlyContext();

        NamingEnumeration answer = ctx.search(userDn, "(userpassword={0})", new Object[] { password }, cons);
        if (answer == null || !answer.hasMoreElements()) {
          throw LdapUtils.convertLdapException(new NamingException("the password is wrong"));
        }
        answer.close();
      }
      this.performanceMonitor.incSuccess();
    } catch (NamingException e) {
      this.performanceMonitor.incFailure();
      throw LdapUtils.convertLdapException(e);
    } catch (Exception e) {
      this.performanceMonitor.incFailure();
      throw e;
    } finally {
      LdapUtils.closeContext(ctx);
    }
  }

}
