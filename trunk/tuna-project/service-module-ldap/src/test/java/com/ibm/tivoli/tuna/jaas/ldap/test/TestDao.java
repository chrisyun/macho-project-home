package com.ibm.tivoli.tuna.jaas.ldap.test;

import java.security.Principal;

import javax.security.auth.Subject;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibm.tivoli.tuna.jaas.NamePrincipal;
import com.ibm.tivoli.tuna.jaas.ldap.UserDNPrincipal;
import com.ibm.tivoli.tuna.service.AttributeStatement;
import com.ibm.tivoli.tuna.service.UserSubject;

public class TestDao extends TestCase{
	
//	public void testSearchDnAndPwd() {
//		ApplicationContext context = new ClassPathXmlApplicationContext("/com/ibm/tivoli/tuna/spring/applicationContext-ldap.xml");
//		LdapUserDaoImpl service = (LdapUserDaoImpl) context.getBean("ldapService");
//		
////		AttributeStatement attr = service.searchUserDNandPwdByAccount("angf");
//		
//	}
	
	public void testSubject() {
		UserDNPrincipal userDNPrincipal = new UserDNPrincipal("dc=uid");
		Subject subject = new Subject();
		subject.getPrincipals().add(userDNPrincipal);
		
		NamePrincipal nameP = new NamePrincipal("username");
		subject.getPrincipals().add(nameP);
		
		for (Principal principal: subject.getPrincipals()) {
            String pname = principal.getName();
            System.out.println(pname);
            System.out.println(principal.getClass().getCanonicalName());
            
            if(UserDNPrincipal.class.getName().equals(principal.getClass().getCanonicalName())) {
            	System.out.println("dn="+pname);
            }
        }
	}
}
