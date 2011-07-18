package com.ibm.tivoli.ldap.dao;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ibm.tivoli.ldap.AdminContext;
import com.ibm.tivoli.ldap.AdminContextAware;

public class SpringBasedDAOFactory extends DAOFactory implements ApplicationContextAware {
  private ApplicationContext applicationContext = null;

  public SpringBasedDAOFactory() {
    super();
  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Override
  public InetOrgPersonDAO getInetOrgPersonDAO(AdminContext adminContext) throws DAOException {
    InetOrgPersonDAO dao = (InetOrgPersonDAO)this.applicationContext.getBean("inetOrgPersonDAO");
    if (dao instanceof AdminContextAware) {
       ((AdminContextAware)dao).setAdminContext(adminContext);
    }
    return dao;
  }

  @Override
  public TamSecUserDAO getTamSecUserDAO(AdminContext adminContext) throws DAOException {
    TamSecUserDAO dao = (TamSecUserDAO)this.applicationContext.getBean("tamSecUserDAO");
    if (dao instanceof AdminContextAware) {
       ((AdminContextAware)dao).setAdminContext(adminContext);
    }
    return dao;
  }

}
