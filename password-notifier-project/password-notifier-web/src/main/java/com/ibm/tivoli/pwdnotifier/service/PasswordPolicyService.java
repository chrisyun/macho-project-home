/**
 * 
 */
package com.ibm.tivoli.pwdnotifier.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;


/**
 * @author zhaodonglu
 *
 */
@WebService(targetNamespace="http://service.sgm.com/")
public interface PasswordPolicyService {
  
  @WebMethod(operationName = "getPasswordStatus")
  @WebResult(name = "GetPwdStatusResp")
  public GetPwdStatusResp getPasswordStatus(@WebParam(name = "userid")String userid);
  
  @WebMethod(operationName = "checkWebNotifiyStatus")
  @WebResult(name = "WebNotifiyStatusResp")
  public WebNotifiyStatusResp checkWebNotifiyStatus(@WebParam(name = "userid")String userid);
  
  @WebMethod(operationName = "searchPwdStatus")
  @WebResult(name = "SearchPwdStatusResp")
  public SearchPwdStatusResp searchPwdStatus(@WebParam(name = "baseDN")String baseDN, @WebParam(name = "filter")String filter);
}
