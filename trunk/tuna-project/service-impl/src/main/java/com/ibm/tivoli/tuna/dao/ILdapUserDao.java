package com.ibm.tivoli.tuna.dao;

import org.springframework.ldap.AuthenticationException;

import com.ibm.tivoli.tuna.entity.UserData;
import com.ibm.tivoli.tuna.entity.UserDataResponse;
import com.ibm.tivoli.tuna.service.AttributeStatement;

public interface ILdapUserDao {
	/**
	 * 
	 * @param userName
	 * @return
	 */
	public String searchUserDNByAccount(String userName);
	
	/**
	 * 
	 * @param userDN
	 * @return
	 */
	public AttributeStatement searchUserEntityByDN(String userDN);
	
	/**
	 * 
	 * @param userDn
	 * @param pwd
	 * @throws AuthenticationException
	 */
	public void authenticateUser(String userDn,char[] pwd) throws AuthenticationException;
	
	/**
	 * 
	 * @param filter
	 * @param startPage
	 * @param pageSize
	 * @param sortAttributeName
	 * @param ascend
	 * @return
	 */
	public UserDataResponse searchUserArray(String filter, byte[] pageCookie, int pageSize, String sortAttributeName, boolean ascend);
	
	/**
	 * 根据过滤条件查找满足条件的用户对象，查找作了大小限制
	 * @param filter
	 * @param sortAttributeName	排序字段
	 * @param ascend 是否升序
	 * @return
	 */
	public UserDataResponse searchUserArrayByFiter(String filter, String sortAttributeName, boolean ascend);
	
}
