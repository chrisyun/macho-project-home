/**
 * 
 */
package com.sinopec.siam.im.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author zhaodonglu
 *
 */
public class SIAMAdminRoleGrantedAuthority implements GrantedAuthority {
  
  /**
   * 
   */
  private static final long serialVersionUID = -6725223322001369802L;

  /**
   * 角色类型名称
   */
  private String authority = null;
  
  private SIAMApplicationGrantedAuthority controlledApplication = null;
  
  private SIAMOrganizationGrantedAuthority controlledOrganization = null;

  /**
   * 
   */
  public SIAMAdminRoleGrantedAuthority() {
    super();
  }

  public SIAMAdminRoleGrantedAuthority(String authority, SIAMApplicationGrantedAuthority controlledApplication, SIAMOrganizationGrantedAuthority controlledOrganization) {
    super();
    this.authority = authority;
    this.controlledApplication = controlledApplication;
    this.controlledOrganization = controlledOrganization;
  }

  public String getAuthority() {
    return this.authority;
  }

  public SIAMApplicationGrantedAuthority getControlledApplication() {
    return controlledApplication;
  }

  public void setControlledApplication(SIAMApplicationGrantedAuthority controlledApplication) {
    this.controlledApplication = controlledApplication;
  }

  public SIAMOrganizationGrantedAuthority getControlledOrganization() {
    return controlledOrganization;
  }

  public void setControlledOrganization(SIAMOrganizationGrantedAuthority controlledOrganization) {
    this.controlledOrganization = controlledOrganization;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((authority == null) ? 0 : authority.hashCode());
    result = prime * result + ((controlledApplication == null) ? 0 : controlledApplication.hashCode());
    result = prime * result + ((controlledOrganization == null) ? 0 : controlledOrganization.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SIAMAdminRoleGrantedAuthority other = (SIAMAdminRoleGrantedAuthority) obj;
    if (authority == null) {
      if (other.authority != null)
        return false;
    } else if (!authority.equals(other.authority))
      return false;
    if (controlledApplication == null) {
      if (other.controlledApplication != null)
        return false;
    } else if (!controlledApplication.equals(other.controlledApplication))
      return false;
    if (controlledOrganization == null) {
      if (other.controlledOrganization != null)
        return false;
    } else if (!controlledOrganization.equals(other.controlledOrganization))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "SIAMAdminRoleGrantedAuthority [authority=" + authority + ", controlledApplication=" + controlledApplication + ", controlledOrganization=" + controlledOrganization
        + "]";
  }

}
