/**
 * 
 */
package com.ibm.tivoli.ldap;

/**
 * @author ZhaoDongLu
 *
 */
public interface AdminContextAware {
  
  public abstract void setAdminContext(AdminContext adminContext);
  
  public abstract AdminContext getAdminContext();

}
