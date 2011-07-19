/**
 * 
 */
package com.ibm.tivoli.pwdnotifier.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ibm.tivoli.pwdnotifier.Checker;
import com.ibm.tivoli.pwdnotifier.UserPasswordStatus;

/**
 * @author zhaodonglu
 *
 */
public class PasswordPolicyServiceImpl implements PasswordPolicyService {

  private static Log log = LogFactory.getLog(PasswordPolicyServiceImpl.class);
  
  private Checker checker;

  /**
   * 
   */
  public PasswordPolicyServiceImpl() {
    super();
  }

  /**
   * @return the checker
   */
  public Checker getChecker() {
    return checker;
  }

  /**
   * @param checker the checker to set
   */
  public void setChecker(Checker checker) {
    this.checker = checker;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.pwdnotifier.service.PasswordPolicyService#getPasswordStatus(java.lang.String)
   */
  public GetPwdStatusResp getPasswordStatus(String userid) {
    GetPwdStatusResp resp = new GetPwdStatusResp();
    try {
      UserPasswordStatus status = checker.getPasswordStatus(userid);
      if (status != null) {
         resp.setCode("success");
         resp.setUserPwdStatus(status);
      } else {
        resp.setCode("user_not_found");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      resp.setCode("error");
      resp.setMessage(e.getMessage());
    }
    return resp;
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.pwdnotifier.service.PasswordPolicyService#checkWebNotifiyStatus(java.lang.String)
   */
  public WebNotifiyStatusResp checkWebNotifiyStatus(String userid) {
    WebNotifiyStatusResp resp = new WebNotifiyStatusResp();
    try {
      boolean needToNotify = checker.checkWebNotifiyStatus(userid);
      resp.setCode("success");
      resp.setNeedToNotify(needToNotify);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      resp.setCode("error");
      resp.setMessage(e.getMessage());
    }
    return resp;
  }
  
  public SearchPwdStatusResp searchPwdStatus(String baseDN, String filter) {
    SearchPwdStatusResp resp = new SearchPwdStatusResp();
    try {
      List<UserPasswordStatus> status = checker.searchByFilter(baseDN, filter);
      if (status != null || status.size() > 0) {
         resp.setCode("success");
         resp.setUserPwdStatus(status);
      } else {
        resp.setCode("user_not_found");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      resp.setCode("error");
      resp.setMessage(e.getMessage());
    }
    return resp;
  }

}
