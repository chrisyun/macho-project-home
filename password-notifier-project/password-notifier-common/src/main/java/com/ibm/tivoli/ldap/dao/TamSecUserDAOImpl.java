package com.ibm.tivoli.ldap.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.naming.NamingEnumeration;
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
import org.springframework.util.StringUtils;

import com.ibm.tivoli.ldap.entity.EPasswordPolicy;
import com.ibm.tivoli.ldap.entity.TamSecUser;

public class TamSecUserDAOImpl implements TamSecUserDAO {

  private static Log log = LogFactory.getLog(TamSecUserDAOImpl.class);

  public class EPasswordPolicyAttributesMapper implements AttributesMapper {
    public EPasswordPolicy mapFromAttributes(Attributes attributes) throws NamingException {

      EPasswordPolicy entry = new EPasswordPolicy();
      if (attributes.get("passwordMaxAge") != null) {
        Attribute attribute = attributes.get("passwordMaxAge");
        entry.setPasswordMaxAge(Integer.parseInt(attribute.get().toString()));
      }
      return entry;
    }

  }

  public class TamSecUserAttributesMapper implements AttributesMapper {

    @Override
    public TamSecUser mapFromAttributes(Attributes attributes) throws NamingException {

      TamSecUser entry = new TamSecUser();
      NamingEnumeration<String> ids = attributes.getIDs();
      if (attributes.get("secHasPolicy") != null) {
        Attribute attribute = attributes.get("secHasPolicy");
        entry.setSecHasPolicy(Boolean.parseBoolean(attribute.get().toString()));
      }
      if (attributes.get("principalName") != null) {
        Attribute attribute = attributes.get("principalName");
        entry.setPrincipalName(attribute.get().toString());
        entry.setDn("principalName=" + attribute.get().toString() + ",cn=users");
      }
      if (attributes.get("secAcctValid") != null) {
        Attribute attribute = attributes.get("secAcctValid");
        entry.setSecAcctValid(Boolean.parseBoolean(attribute.get().toString()));
      }
      if (attributes.get("secPwdValid") != null) {
        Attribute attribute = attributes.get("secPwdValid");
        entry.setSecPwdValid(Boolean.parseBoolean(attribute.get().toString()));
      }
      if (attributes.get("secPwdValid") != null) {
        Attribute attribute = attributes.get("secPwdValid");
        entry.setSecPwdValid(Boolean.parseBoolean(attribute.get().toString()));
      }
      if (attributes.get("secAuthority") != null) {
        Attribute attribute = attributes.get("secAuthority");
        entry.setSecAuthority(attribute.get().toString());
      }
      if (attributes.get("secDomainId") != null) {
        Attribute attribute = attributes.get("secDomainId");
        entry.setSecDomainId(attribute.get().toString());
      }
      if (attributes.get("secPwdLastChanged") != null) {
        try {
          Attribute attribute = attributes.get("secPwdLastChanged");
          String s = attribute.get().toString();
          s = StringUtils.replace(s, ".0Z", "+0000");
          DateFormat f = new SimpleDateFormat("yyyyMMddHHmmssZZZ");
          Date d = f.parse(s);
          entry.setSecPwdLastChanaged(d);
        } catch (ParseException e) {
          log.error(e.getMessage(), e);
        }
      }
      return entry;
    }

  }

  private LdapTemplate ldapTemplate = null;

  private OdmManager odmManager = null;

  private String baseSuffix = null;

  public TamSecUserDAOImpl() {
    super();
  }

  public String getBaseSuffix() {
    return baseSuffix;
  }

  public void setBaseSuffix(String baseSuffix) {
    this.baseSuffix = baseSuffix;
  }

  public void setLdapTemplate(LdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  public void setOdmManager(OdmManager odmManager) {
    this.odmManager = odmManager;
  }
  
  private int getDefaultPasswordMaxAge() {
    return getPolicy("cn=Default, cn=Policies");
  }

  private int getPolicy(String dn) {
    try {
      EPasswordPolicyAttributesMapper mapper = new EPasswordPolicyAttributesMapper();
      EPasswordPolicy policy = (EPasswordPolicy)this.ldapTemplate.lookup(dn, mapper);
      if (policy != null) {
        return policy.getPasswordMaxAge();
      }
    } catch (NameNotFoundException e) {
    } catch (NumberFormatException e) {
      log.error(e.getMessage(), e);
    }
    return -1;
  }

  private int getUserPasswordMaxAge(String userDN) {
    return getPolicy("cn=Policy, cn=Policies, " + userDN);
  }

  public List<TamSecUser> findByFilter(String baseDN, String filter) throws DAOException {
    SearchControls controls = new SearchControls();
    controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    TamSecUserAttributesMapper mapper = new TamSecUserAttributesMapper();
    List<TamSecUser> result = new ArrayList<TamSecUser>(new HashSet<TamSecUser>(ldapTemplate.search(baseDN, filter, mapper)));
    if (result != null) {
       for (TamSecUser entry: result) {
           int passwordMaxAge = this.getUserPasswordMaxAge(entry.getDn());
           if (passwordMaxAge < 0) {
             passwordMaxAge = getDefaultPasswordMaxAge();
           }
           entry.setPasswordMaxAge(passwordMaxAge);
       }
    }
    return result;
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
