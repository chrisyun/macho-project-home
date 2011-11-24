package com.ibm.tivoli.benchmark;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.HasControls;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import junit.framework.TestCase;

public class LdapTest extends TestCase {

  private Hashtable<String, Object> ldapEnv;
  private LdapContext jndiContext;

  protected void setUp() throws Exception {
    super.setUp();
    ldapEnv = new Hashtable<String, Object>(11);
    ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    ldapEnv.put(Context.PROVIDER_URL, "ldap://10.192.40.2:389/DC=UNICOM");
    ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
    ldapEnv.put(Context.SECURITY_PRINCIPAL, "cn=root");
    ldapEnv.put(Context.SECURITY_CREDENTIALS, "passw0rd");
    
    jndiContext = new InitialLdapContext(ldapEnv, null);
  }

  protected void tearDown() throws Exception {
    super.tearDown();
    // Close the LDAP association
    jndiContext.close();
  }
  
  public void testUidSearch() throws Exception {
    // Open an LDAP association
    LdapContext jndiContext = new InitialLdapContext(ldapEnv, null);

    // Perform a search
    SearchControls searchControls = new SearchControls();
    searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    searchControls.setReturningAttributes(new String[] { "uid, cn" });
    NamingEnumeration results = jndiContext.search("", "(&(objectclass=inetorgperson)(uid=sd-zb-81yys))", searchControls);

    // Iterate over search results
    while (results != null && results.hasMore()) {
      // Display an entry
      SearchResult entry = (SearchResult) results.next();
      System.out.println(entry.getName());
      System.out.println(entry.getAttributes());

      // Handle the entry's response controls (if any)
      if (entry instanceof HasControls) {
        // ((HasControls)entry).getControls();
      }
    }
  }
}
