package com.ibm.tivoli.lab;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.HasControls;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;
import javax.naming.ldap.SortControl;
import javax.naming.ldap.SortResponseControl;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import junit.framework.TestCase;

public class ServerSideSortExtTest extends TestCase {

  private static class DefaultTrustManager implements X509TrustManager {

    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
      return null;
    }
  }

  static {
    try {
      SSLContext ctx = SSLContext.getInstance("TLS");
      ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
      //SSLContext.setDefault(ctx);
    } catch (Throwable tx) {
      System.out.println("Could not reset TLS TrustManager, cause: " + tx.getMessage());
      tx.printStackTrace();
    }
  }

  private Hashtable<String, Object> ldapEnv;

  protected void setUp() throws Exception {
    super.setUp();
    ldapEnv = new Hashtable<String, Object>(11);
    ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    ldapEnv.put(Context.PROVIDER_URL, "ldap://10.110.21.58:389/DC=HLJCMCC");
    //ldapEnv.put(Context.PROVIDER_URL, "ldaps://192.168.211.130:6360/DC=JKE,DC=COM");
    ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
    ldapEnv.put(Context.SECURITY_PRINCIPAL, "cn=root");
    ldapEnv.put(Context.SECURITY_CREDENTIALS, "passw0rd");
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testSupportControls() throws Exception {
    // Open an LDAP association
    LdapContext jndiContext = new InitialLdapContext(ldapEnv, null);
    Attributes attrs = jndiContext.getAttributes("", new String[] { "supportedcontrol" });

    Attribute attr = attrs.get("supportedcontrol");
    for (int i = 0; i < attr.size(); ++i) {
      System.out.println(attr.getID() + "=" + attr.get(i));
    }
    // Close the LDAP association
    jndiContext.close();

  }

  public void testServerSideSort() throws Exception {

    // Open an LDAP association
    LdapContext jndiContext = new InitialLdapContext(ldapEnv, null);

    // Activate sorting
    String sortKey = "cn";
    jndiContext.setRequestControls(new Control[] { new SortControl(sortKey, Control.CRITICAL) });

    // Perform a search
    SearchControls searchControls = new SearchControls();
    searchControls.setCountLimit(100);
    searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    searchControls.setReturningAttributes(new String[] { "cn" });
    NamingEnumeration results = jndiContext.search("", "(objectclass=inetorgperson)", searchControls);

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
    // Examine the sort control response
    Control[] controls = jndiContext.getResponseControls();
    if (controls != null) {
      for (int i = 0; i < controls.length; i++) {
        if (controls[i] instanceof SortResponseControl) {
          SortResponseControl src = (SortResponseControl) controls[i];
          if (!src.isSorted()) {
            throw src.getException();
          }
        } else {
          // Handle other response controls (if any)
        }
      }
    }

    // Close the LDAP association
    jndiContext.close();

  }

  public void testServerPagingAndSort() throws Exception {
    // Open an LDAP association
    LdapContext jndiContext = new InitialLdapContext(ldapEnv, null);

    // Activate paged results
    int pageSize = 20; // 20 entries per page
    byte[] cookie = null;
    int total;
    // Activate sorting and paging
    String sortKey = "cn";
    jndiContext.setRequestControls(new Control[] { new SortControl(sortKey, Control.CRITICAL), new PagedResultsControl(pageSize, Control.CRITICAL) });

    SearchControls searchControls = new SearchControls();
    //searchControls.setCountLimit(100);
    searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    searchControls.setReturningAttributes(new String[] { "cn" });
    int count = 0;
    int page = 0;
    do {
      // Perform the search
      NamingEnumeration results = jndiContext.search("cn=user", "(objectclass=inetorgperson)", searchControls);

      // Iterate over a batch of search results
      while (results != null && results.hasMore()) {
        // Display an entry
        SearchResult entry = (SearchResult) results.next();
        System.out.print(count++ + ":");
        System.out.print(entry.getName());
        System.out.println(entry.getAttributes());

        // Handle the entry's response controls (if any)
        if (entry instanceof HasControls) {
          // ((HasControls)entry).getControls();
        }
      }
      System.out.println("===================== Page: " + (++page) + "=======================");
      // Examine the paged results control response
      Control[] controls = jndiContext.getResponseControls();
      if (controls != null) {
        for (int i = 0; i < controls.length; i++) {
          if (controls[i] instanceof PagedResultsResponseControl) {
            PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
            total = prrc.getResultSize();
            cookie = prrc.getCookie();
          } else {
            // Handle other response controls (if any)
          }
        }
      }

      // Re-activate paged results
      jndiContext.setRequestControls(new Control[] {new SortControl(sortKey, Control.CRITICAL),  new PagedResultsControl(pageSize, cookie, Control.CRITICAL) });
    } while (cookie != null);

    // Close the LDAP association
    jndiContext.close();
  }

}
