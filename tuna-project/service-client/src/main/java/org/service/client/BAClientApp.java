package org.service.client;

import javax.net.ssl.TrustManager;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibm.tivoli.tuna.service.AuthenticationResult;
import com.ibm.tivoli.tuna.service.AuthenticationService;
import com.ibm.tivoli.tuna.service.Context;
import com.ibm.tivoli.tuna.service.Credential;
import com.ibm.tivoli.tuna.service.Credentials;
import com.ibm.tivoli.tuna.service.Parameter;
import com.ibm.tivoli.tuna.service.Requester;
import com.ibm.tivoli.tuna.service.ReuqestPrincipal;

/**
 * Hello world!
 * 
 */
public class BAClientApp {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-cxf-client.xml");
		AuthenticationService client = (AuthenticationService)context.getBean("BAClientApp");
		
		Requester requester = new Requester();
		ReuqestPrincipal principal = new ReuqestPrincipal();
		principal.setName("protal");
		principal.setType("application");
		requester.getPrincipals().add(principal);
		
		
		Context contexter = new Context();
		Parameter parameter = new Parameter();
		parameter.setName("LoginModuleName");
		parameter.getValues().add("LDAP-SIMPLE");
		contexter.getParameters().add(parameter);
		
		Credentials credentials = new Credentials();
		Credential username = new Credential();
		username.setType("username");
		username.setValueAsString("00163929");
		
		Credential password = new Credential();
		password.setType("password");
		password.setValueAsString("wwwwww");
		credentials.getCredentials().add(username);
		credentials.getCredentials().add(password);
		
		//---------------------设置SSL需要测试-----------------------------
		Client proxy = ClientProxy.getClient(client);   
		HTTPConduit conduit = (HTTPConduit) proxy.getConduit();    
		TLSClientParameters tls= new TLSClientParameters();    
		//添加TrustAllX509TrustManager所在类jar包
		tls.setTrustManagers( new TrustManager[]{ new TrustAllX509TrustManager()});
		tls.setDisableCNCheck(true);//接受服务器端的认证   
		conduit.setTlsClientParameters(tls);    
		//---------------------设置SSL-----------------------------
		
		AuthenticationResult ar = client.authenticate(requester, contexter, credentials);
		
		System.out.println(ar.toString());
	}
}
