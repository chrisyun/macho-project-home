package com.ibm.tivoli.ldap.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.odm.core.OdmManager;

import com.ibm.tivoli.ldap.entity.InetOrgPerson;

public class InetOrgPersonDAOImpl implements InetOrgPersonDAO {

  private static Log log = LogFactory.getLog(InetOrgPersonDAOImpl.class);

  public class UserAttributesMapper implements AttributesMapper {

    @Override
    public InetOrgPerson mapFromAttributes(Attributes attributes) throws NamingException {

      InetOrgPerson userObject = new InetOrgPerson();

      if (attributes.get("uid") != null) {
        Attribute attribute = attributes.get("uid");
        userObject.setUid((String)attribute.get());
      } 
      if (attributes.get("cn") != null) {
        Attribute attribute = attributes.get("cn");
        String value = attribute.get().toString();
        userObject.setCn(value);
      }
      if (attributes.get("mail") != null) {
        Attribute attribute = attributes.get("mail");
        String value = attribute.get().toString();
        userObject.setEmail(value);
      }
      return userObject;
    }

  }

  private LdapTemplate ldapTemplate = null;

  private OdmManager odmManager = null;

  public InetOrgPersonDAOImpl() {
    super();
  }

  public void setLdapTemplate(LdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  public void setOdmManager(OdmManager odmManager) {
    this.odmManager = odmManager;
  }

  public List<InetOrgPerson> findByFilter(String baseDN, String filter) throws DAOException {
    SearchControls controls = new SearchControls();
    controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    UserAttributesMapper mapper = new UserAttributesMapper();
    return new ArrayList<InetOrgPerson>(new HashSet<InetOrgPerson>(ldapTemplate.search(baseDN, filter, mapper)));
  }

  public InetOrgPerson getByUserID(String uid) throws DAOException {
    String filter = "(uid=" + uid + ")";
    try {
      List<InetOrgPerson> result = findByFilter("", filter);
      if (result != null && result.size() > 0) {
        return result.get(0);
      }
    } catch (NameNotFoundException ex) {
      log.debug("could not found entry by filter: [" + filter + "]");
    }
    return null;
  }

}
