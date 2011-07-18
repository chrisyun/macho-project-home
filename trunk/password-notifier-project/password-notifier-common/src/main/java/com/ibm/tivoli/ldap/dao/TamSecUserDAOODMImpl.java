package com.ibm.tivoli.ldap.dao;

import java.util.List;

import javax.naming.directory.SearchControls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.odm.core.OdmManager;

import com.ibm.tivoli.ldap.entity.TamSecUser;

public class TamSecUserDAOODMImpl implements TamSecUserDAO {

  private static Log log = LogFactory.getLog(TamSecUserDAOODMImpl.class);

  private LdapTemplate ldapTemplate = null;

  private OdmManager odmManager = null;

  public TamSecUserDAOODMImpl() {
    super();
  }

  public void setLdapTemplate(LdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  public void setOdmManager(OdmManager odmManager) {
    this.odmManager = odmManager;
  }

  public List<TamSecUser> findByFilter(String baseSuffix, String filter) throws DAOException {
    DistinguishedName baseDN = new DistinguishedName(baseSuffix);

    SearchControls controls = new SearchControls();
    controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    return this.odmManager.search(TamSecUser.class, baseDN, filter, controls);
  }

  public TamSecUser getByUserID(String uid) throws DAOException {
    String filter = "(principalName=" + uid + ")";
    try {
      List<TamSecUser> result = findByFilter("", filter);
      if (result != null && result.size() > 0) {
         return result.get(0);
      }
    } catch (NameNotFoundException ex) {
      log.debug("could not found entry by filter: [" + filter + "]");
    }
    return null;
  }

}
