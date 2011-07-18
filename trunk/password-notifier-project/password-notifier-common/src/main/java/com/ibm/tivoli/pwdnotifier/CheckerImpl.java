/**
 * 
 */
package com.ibm.tivoli.pwdnotifier;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.ldap.dao.DAOException;
import com.ibm.tivoli.ldap.dao.InetOrgPersonDAO;
import com.ibm.tivoli.ldap.dao.TamSecUserDAO;
import com.ibm.tivoli.ldap.entity.InetOrgPerson;
import com.ibm.tivoli.ldap.entity.TamSecUser;

/**
 * @author zhaodonglu
 * 
 */
public class CheckerImpl implements Checker {
  
  private static Log log = LogFactory.getLog(CheckerImpl.class);
  

  private InetOrgPersonDAO personDAO = null;

  private TamSecUserDAO tamSecUserDAO = null;
  /**
   * 
   */
  public CheckerImpl() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.pwdnotifier.Checker#check(java.lang.String)
   */
  public UserPasswordStatus check(String userid) throws Exception {
    // Retrieve User information(userid, email, cn)
    UserPasswordStatus status = new UserPasswordStatus();
    InetOrgPerson person = this.findUserInfo(userid);
    if (person == null) {
      return null;
    }
    status.setCn(person.getCn());
    status.setEmail(person.getEmail());
    status.setUserid(person.getUid());
    
    TamSecUser tamSecUser = this.getTamUserInfo(userid);
    if (tamSecUser == null) {
       return null;
    }
    // Load last pwd changed Time
    /*
    Date lastPasswordChangedTime = tamSecUser.getSecPwdLastChanaged();
    status.setLastPasswordChangedTime(lastPasswordChangedTime);
    
    long passwordAgeInSeconds = this.getPwdAgeInSeconds(userid);
    status.setPasswordAgeInSeconds(passwordAgeInSeconds);
    
    Date lastEmailNotifiedTime = this.getLastEmailNotifiedTime(userid);
    status.setLastPasswordNotifiedTime(lastEmailNotifiedTime);
    */
    return status;
  }

  private Date getLastEmailNotifiedTime(String userid) {
    // TODO Auto-generated method stub
    return null;
  }

  private long getPwdAgeInSeconds(String userid) {
    // TODO Auto-generated method stub
    return 0;
  }

  private Date getLastPasswordChanged(String userid) {
    // TODO Auto-generated method stub
    return null;
  }

  private InetOrgPerson findUserInfo(String userid) {
    try {
      return this.personDAO.getByUserID(userid);
    } catch (DAOException e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }


  private TamSecUser getTamUserInfo(String userid) {
    try {
      return this.tamSecUserDAO.getByUserID(userid);
    } catch (DAOException e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }
}
