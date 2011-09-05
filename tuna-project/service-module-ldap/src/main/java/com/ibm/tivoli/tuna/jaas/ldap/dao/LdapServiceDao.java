package com.ibm.tivoli.tuna.jaas.ldap.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import com.ibm.tivoli.tuna.jaas.ldap.util.LocalObjToObjectContextMapper;
import com.ibm.tivoli.tuna.service.AttributeStatement;

public class LdapServiceDao {
	
	private static Log log = LogFactory.getLog(LdapServiceDao.class);
	
	private LdapTemplate ldapTemplate;
	private String baseDN;
	private String userFields;
	
	public AttributeStatement searchUserDNandPwdByAccount(String userName) {
		
		AttributeStatement attrState = null;
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("uid", userName));
		ContextMapper mapper = new LocalObjToObjectContextMapper(new String[]{"userdn","userpassword"});
		
		attrState = (AttributeStatement) ldapTemplate.searchForObject(baseDN, filter.encode(), mapper);
		
		return attrState;
	}
	
	public AttributeStatement searchUserEntityByDN(String userDN) {
		ContextMapper mapper = new LocalObjToObjectContextMapper(userFields.split(","));
		
		return (AttributeStatement) ldapTemplate.lookup(userDN,mapper);
	}

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public String getBaseDN() {
		return baseDN;
	}

	public void setBaseDN(String baseDN) {
		this.baseDN = baseDN;
	}

	public String getUserFields() {
		return userFields;
	}

	public void setUserFields(String userFields) {
		this.userFields = userFields;
	}

	
	
}
