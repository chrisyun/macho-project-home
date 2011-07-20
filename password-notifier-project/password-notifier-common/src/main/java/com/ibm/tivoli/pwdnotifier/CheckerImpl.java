/**
 * 
 */
package com.ibm.tivoli.pwdnotifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
   * 7 days
   */
  private int defaultDaysOfWebNotification = 7;
  /**
   * 
   */
  public CheckerImpl() {
    super();
  }

  /**
   * @return the defaultDaysOfWebNotification
   */
  public int getDefaultDaysOfWebNotification() {
    return defaultDaysOfWebNotification;
  }

  /**
   * @param defaultDaysOfWebNotification the defaultDaysOfWebNotification to set
   */
  public void setDefaultDaysOfWebNotification(int defaultDaysOfWebNotification) {
    this.defaultDaysOfWebNotification = defaultDaysOfWebNotification;
  }

  /**
   * @return the personDAO
   */
  public InetOrgPersonDAO getPersonDAO() {
    return personDAO;
  }

  /**
   * @param personDAO the personDAO to set
   */
  public void setPersonDAO(InetOrgPersonDAO personDAO) {
    this.personDAO = personDAO;
  }

  /**
   * @return the tamSecUserDAO
   */
  public TamSecUserDAO getTamSecUserDAO() {
    return tamSecUserDAO;
  }

  /**
   * @param tamSecUserDAO the tamSecUserDAO to set
   */
  public void setTamSecUserDAO(TamSecUserDAO tamSecUserDAO) {
    this.tamSecUserDAO = tamSecUserDAO;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.pwdnotifier.Checker#check(java.lang.String)
   */
  public UserPasswordStatus getPasswordStatus(String userid) throws Exception {
    InetOrgPerson person = this.findUserInfo(userid);
    return getPasswordStatus(person);
  }

  private UserPasswordStatus getPasswordStatus(InetOrgPerson person) {
    // Retrieve User information(userid, email, cn)
    UserPasswordStatus status = new UserPasswordStatus();
    if (person == null) {
      return null;
    }
    status.setCn(person.getCn());
    status.setEmail(person.getEmail());
    status.setUserid(person.getUid());
    
    TamSecUser tamSecUser = this.getTamUserInfo(person.getUid());
    if (tamSecUser == null) {
       return status;
    }
    // Load last pwd changed Time
    
    Date lastPasswordChangedTime = tamSecUser.getSecPwdLastChanaged();
    status.setLastPasswordChangedTime(lastPasswordChangedTime);
    
    long passwordAgeInSeconds = tamSecUser.getPasswordMaxAge();
    status.setPasswordMaxAgeInSeconds(passwordAgeInSeconds);
    
    if (lastPasswordChangedTime != null && passwordAgeInSeconds > 0) {
       Date passwordExpireTime = new Date(lastPasswordChangedTime.getTime() + passwordAgeInSeconds * 1000);
       status.setPasswordExpireTime(passwordExpireTime);
    } else if (passwordAgeInSeconds > 0) {
      // Never set password and set password max age
      status.setPasswordExpireTime(new Date());
    }
    
    return status;
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

  public boolean checkWebNotifiyStatus(String userid, int daysOfNotification) throws Exception {
    UserPasswordStatus status = this.getPasswordStatus(userid);
    if (status != null && status.getPasswordMaxAgeInSeconds() > 0 && status.getLastPasswordChangedTime() != null) {
       return (status.getPasswordExpireTime().getTime() - System.currentTimeMillis() >= defaultDaysOfWebNotification * 24 * 3600 * 1000)?false:true; 
    }
    return false;
  }
  
  public boolean checkWebNotifiyStatus(String userid) throws Exception {
    return this.checkWebNotifiyStatus(userid, this.defaultDaysOfWebNotification );
  }
  
  public List<UserPasswordStatus> searchByFilter(String baseDN, String filter) throws Exception {
    List<UserPasswordStatus> result = new ArrayList<UserPasswordStatus>();
    List<InetOrgPerson> persons = personDAO.findByFilter(baseDN, filter);
    for (InetOrgPerson person: persons) {
      result.add(getPasswordStatus(person));
    }
    return result;
  }
}
