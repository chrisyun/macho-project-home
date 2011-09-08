package com.ibm.tivoli.tuna.dao;

import java.util.List;

import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;
import javax.naming.ldap.SortControl;
import javax.naming.ldap.SortKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.control.PagedResultsCookie;
import org.springframework.ldap.control.PagedResultsDirContextProcessor;
import org.springframework.ldap.control.SortControlDirContextProcessor;
import org.springframework.ldap.core.CollectingNameClassPairCallbackHandler;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.ContextMapperCallbackHandler;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.core.support.AggregateDirContextProcessor;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapUtils;

import com.ibm.tivoli.tuna.entity.UserData;
import com.ibm.tivoli.tuna.entity.UserDataResponse;
import com.ibm.tivoli.tuna.service.AttributeStatement;
import com.ibm.tivoli.tuna.service.Status;
import com.ibm.tivoli.tuna.util.LdapConst;
import com.ibm.tivoli.tuna.util.StringUtil;

public class LdapUserDaoImpl implements ILdapUserDao {
	
	private static boolean is_server_page = false;
	
	private static Log log = LogFactory.getLog(LdapUserDaoImpl.class);
	
	private LdapTemplate ldapTemplate;
	private ContextSource contextSource;
	private String baseDN;
	private String userFields;
	
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
	
	public AttributeStatement searchUserEntityByDN(String userDN) {
		ContextMapper mapper = new LoginContextMapper(userFields.split(","));
		return (AttributeStatement) ldapTemplate.lookup(userDN,mapper);
	}

	
	public UserDataResponse searchUserArrayByFiter(String filter, 
			String sortAttributeName, boolean ascend) {
		UserDataResponse response = new UserDataResponse();
		
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchControls.setReturningObjFlag(true);
		searchControls.setCountLimit(LdapConst.maxSearchRow);
		
		ContextMapper mapper = new SearchContextMapper(userFields.split(","), UserData.class);
		CollectingNameClassPairCallbackHandler handler = new ContextMapperCallbackHandler(mapper);
		
		FreeSortControlDirContextProcessor sortControl = new FreeSortControlDirContextProcessor(sortAttributeName,ascend);
		
		try {
			ldapTemplate.search(baseDN,filter,searchControls,handler,sortControl);
		} catch (org.springframework.ldap.SizeLimitExceededException e) {
		}
		
		response.setCountSize(handler.getList().size());
		
		List<UserData> resList = (List<UserData>) handler.getList();
		
		UserData[] dataArray = new UserData[resList.size()];
		for (int i = 0; i < resList.size(); i++) {
			dataArray[i] = (UserData)resList.get(i);
		}
		resList.clear();
		response.setResult(dataArray);
		
		return response;
	}
	

	public UserDataResponse searchUserArray(String filter, int startPage,
			int pageSize, String sortAttributeName, boolean ascend) {
		
		UserDataResponse response = new UserDataResponse();
		UserData[] userArray = null;
		
		
		if(pageSize > LdapConst.maxRowCount) {
			response.getStatus().setCode(Status.FAILURE);
			response.getStatus().setMessage("人员查询的数据超过最大数据限制！");
			
			return response;
	    }
		
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		searchControls.setReturningObjFlag(true);
		
		ContextMapper mapper = new SearchContextMapper(userFields.split(","), UserData.class);
		CollectingNameClassPairCallbackHandler handler = new ContextMapperCallbackHandler(mapper);
		
		FreeSortControlDirContextProcessor sortControl = new FreeSortControlDirContextProcessor(sortAttributeName,ascend);
		AggregateDirContextProcessor processor = new AggregateDirContextProcessor();
		processor.addDirContextProcessor(sortControl);
		
		try {
			if(is_server_page) {
				PagedResultsCookie cookie = null;
				
				int tempPageSize = startPage*pageSize;
				int temp = 1;
				
				do {
					if(temp == 1 && tempPageSize == 0) {
						tempPageSize = pageSize;
					} 
					
					PagedResultsDirContextProcessor pageControl = new PagedResultsDirContextProcessor(tempPageSize,cookie);
					
					processor.addDirContextProcessor(pageControl);
					
					try {
						ldapTemplate.search(baseDN, filter,searchControls,handler,processor);
					} catch (org.springframework.ldap.SizeLimitExceededException e) {
					}
					
					cookie = pageControl.getCookie();
					int maxNum = pageControl.getResultSize();
					
				} while (cookie != null);
				
				
			} else {
				searchControls.setCountLimit(LdapConst.maxSearchRow);
				
				try {
					ldapTemplate.search(baseDN, filter,searchControls,handler,processor);
				} catch (org.springframework.ldap.SizeLimitExceededException e) {
				}
				
				response.setCountSize(handler.getList().size());
				
				 //对数组内容进行分页，start:startPage*pageSize   end:(startPage+1)*pageSize-1
		        AttributeStatement[] tempData = StringUtil.splitPage(handler.getList(), startPage, pageSize);
		        userArray = new UserData[tempData.length];
		        for (int i = 0; i < tempData.length; i++) {
		        	userArray[i] = (UserData) tempData[i];
				}
		        response.setResult(userArray);
			}
		} catch (Exception e) {
			log.error("人员查询出现异常，错误原因：", e);
			response.getStatus().setCode(Status.FAILURE);
			response.getStatus().setMessage("人员查询出现异常，错误原因："+e.getMessage());
		}
		
		return response;
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
	
	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}
	
}
