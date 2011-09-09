/**
 * 
 */
package com.ibm.tivoli.tuna.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;

import com.ibm.tivoli.tuna.dao.ILdapUserDao;
import com.ibm.tivoli.tuna.entity.UserDataResponse;
import com.ibm.tivoli.tuna.util.StringUtil;

/**
 * @author ZhaoDongLu
 *
 */
public class UserServiceImpl implements UserService {
	private static Log log = LogFactory.getLog(UserServiceImpl.class);
	
	private ILdapUserDao ldapUser;
	
	public ILdapUserDao getLdapUser() {
		return ldapUser;
	}

	public void setLdapUser(ILdapUserDao ldapUser) {
		this.ldapUser = ldapUser;
	}


	public UserServiceImpl() {
		super();
	}
	
	
	/* (non-Javadoc)
	 * @see com.ibm.tivoli.service.UserService#searchAll(int, int, java.lang.String, boolean)
	 */
	public UserDataResponse searchAll(String pageCookie, int pageSize, String sortAttributeName, boolean ascend) {
		AndFilter filter = new AndFilter();
		filter.and(new LikeFilter("uid", "*"));
		filter.and(new EqualsFilter("objectclass", "organizationalPerson"));
		
		return searchAllByFilter(filter.encode(), pageCookie, pageSize, sortAttributeName, ascend);
	}

	/* (non-Javadoc)
	 * @see com.ibm.tivoli.service.UserService#getUserByUsername(java.lang.String)
	 */
	public UserDataResponse getUserByUsername(String username) {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("uid", username));
		filter.and(new EqualsFilter("objectclass", "organizationalPerson"));
		
		return ldapUser.searchUserArrayByFiter(filter.encode(), "uid", true);
	}

	/* (non-Javadoc)
	 * @see com.ibm.tivoli.service.UserService#searchAllByFilter(java.lang.String, int, int, java.lang.String, boolean)
	 */
	public UserDataResponse searchAllByFilter(String filter, String pageCookie, 
			int pageSize, String sortAttributeName, boolean ascend) {
		
		log.debug(String.format("print user searchbyFilter cookie[%s] and filter[%s]", String.valueOf(pageCookie),filter));
		
		return ldapUser.searchUserArray(filter, StringUtil.string2byte(pageCookie), pageSize, sortAttributeName, ascend);
	}

	public UserDataResponse searchUserByOrgid(String orgid,
		String sortAttributeName, boolean ascend) {
		
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("departmentnumber", orgid));
		filter.and(new EqualsFilter("objectclass", "organizationalPerson"));
		
		return ldapUser.searchUserArrayByFiter(filter.encode(), sortAttributeName, ascend);
	}

}
