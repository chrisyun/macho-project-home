package com.ibm.tivoli.tuna.dao;

import org.springframework.ldap.AuthenticationException;

import com.ibm.tivoli.tuna.service.AttributeStatement;

public interface ILdapUserDao {
	public String searchUserDNByAccount(String userName);
	
	public AttributeStatement searchUserEntityByDN(String userDN);
	
	public void authenticateUser(String userDn,char[] pwd) throws AuthenticationException;
}
