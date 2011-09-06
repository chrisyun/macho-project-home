package com.ibm.tivoli.tuna.jaas.ldap.dao;

import javax.naming.directory.DirContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapUtils;

import com.ibm.tivoli.tuna.jaas.ldap.util.LocalObjToObjectContextMapper;
import com.ibm.tivoli.tuna.service.AttributeStatement;

public class LdapServiceDao {
	
	private static Log log = LogFactory.getLog(LdapServiceDao.class);
	
	private LdapTemplate ldapTemplate;
	private ContextSource contextSource;
	private String baseDN;
	private String userFields;
	
	/**
	 * authentification of user by binding type
	 * @param userDn
	 * @param pwd
	 * @throws AuthenticationException means the password is wrong
	 */
	public void authenticateUser(String userDn,char[] pwd) throws AuthenticationException {
		DirContext ctx = null;
		try {
			ctx = contextSource.getContext(userDn,String.valueOf(pwd));
		} catch (AuthenticationException e) {
			throw e;
		} finally {
			LdapUtils.closeContext(ctx);
		}
	}
	
	/**
	 * 
	 * @param userName
	 * @return user dn
	 */
	public String searchUserDNByAccount(String userName) {
		AndFilter filter = new AndFilter();
		
		filter.and(new EqualsFilter("uid", userName));
		//add other fiter,like account status
		//filter.and(new EqualsFilter("spEntryStatus", "active"));
		
		return (String) ldapTemplate.searchForObject(baseDN, filter.encode(),
				new AbstractContextMapper() {
					
					@Override
					protected Object doMapFromContext(DirContextOperations ctx) {
						return ctx.getNameInNamespace();
					}
				}
		);
	}
	
	/**
	 * find user attribute by user dn
	 * @param userDN
	 * @return
	 */
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

	public ContextSource getContextSource() {
		return contextSource;
	}

	public void setContextSource(ContextSource contextSource) {
		this.contextSource = contextSource;
	}

}
