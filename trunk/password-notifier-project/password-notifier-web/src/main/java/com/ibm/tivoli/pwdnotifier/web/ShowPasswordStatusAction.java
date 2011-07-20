package com.ibm.tivoli.pwdnotifier.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.common.util.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ibm.tivoli.pwdnotifier.service.GetPwdStatusResp;
import com.ibm.tivoli.pwdnotifier.service.PasswordPolicyService;

/**
 * Servlet implementation class ShowPasswordStatusAction
 */
public class ShowPasswordStatusAction extends Action {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public ShowPasswordStatusAction() {
    super();
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String uid = request.getHeader("iv-user");
    if (StringUtils.isEmpty(uid)) {
      // Not found uid, test only process
      uid = request.getParameter("__userid__");
    }

    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(this.getServlet().getServletContext());
    PasswordPolicyService service = (PasswordPolicyService) ctx.getBean("passwordPolicyService");

    GetPwdStatusResp pwdStatus = service.getPasswordStatus(uid);
    if (pwdStatus != null) {
      request.setAttribute("passwordStatus", pwdStatus);
      if (pwdStatus.getUserPwdStatus().getPasswordMaxAgeInSeconds() > 0) {
         if (pwdStatus.getUserPwdStatus().getLastPasswordChangedTime() == null || pwdStatus.getUserPwdStatus().getPasswordExpireTime().before(new Date())) {
            // Password expired!
            request.setAttribute("passwordExpired", new Boolean(true));
         }
         if (pwdStatus.getUserPwdStatus().getPasswordExpireTime().after(new Date())) {
           long timeLeft = pwdStatus.getUserPwdStatus().getPasswordExpireTime().getTime() - System.currentTimeMillis();
           long dayLeft = timeLeft / 1000 / 3600 / 24;
           long hourLeft = (timeLeft - dayLeft * 1000 * 3600 * 24) / 1000 / 3600;
           long minuteLeft = (timeLeft - dayLeft * 1000 * 3600 * 24 - hourLeft * 1000 * 3600) / 1000 / 60;
           request.setAttribute("dayLeft", dayLeft);
           request.setAttribute("hourLeft", hourLeft);
           request.setAttribute("minuteLeft", minuteLeft);
         }
      }
      return mapping.findForward("success");
    } else {
      return mapping.findForward("success");
    }
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String uid = request.getHeader("iv-user");
    if (StringUtils.isEmpty(uid)) {
      // Not found uid
      uid = "bgoldmann";
    }

    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
    PasswordPolicyService service = (PasswordPolicyService) ctx.getBean("passwordPolicyService");

    GetPwdStatusResp pwdStatus = service.getPasswordStatus(uid);
    if (pwdStatus != null) {
      request.setAttribute("passwordStatus", pwdStatus);
      request.getRequestDispatcher("/WEB-INF/jsp/show_pwd_status.jsp").include(request, response);
    } else {
      request.getRequestDispatcher("/WEB-INF/jsp/show_pwd_status.jsp").include(request, response);
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    this.doGet(request, response);
  }

}
