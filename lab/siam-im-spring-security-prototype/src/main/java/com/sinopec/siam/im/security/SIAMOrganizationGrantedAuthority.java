/**
 * 
 */
package com.sinopec.siam.im.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author zhaodonglu
 *
 */
public class SIAMOrganizationGrantedAuthority implements GrantedAuthority {

  /**
   * 
   */
  private static final long serialVersionUID = -2855507172285525062L;

  /**
   * Org DN
   */
  private String authority = null;

  /**
   * Org name
   */
  private String name = null;
  
  /**
   * Org name path
   */
  private String namePath = null;
  
  
  /**
   * 
   */
  public SIAMOrganizationGrantedAuthority() {
    super();
  }

  public SIAMOrganizationGrantedAuthority(String authority, String name, String namePath) {
    super();
    this.authority = authority;
    this.name = name;
    this.namePath = namePath;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.GrantedAuthority#getAuthority()
   */
  public String getAuthority() {
    return this.authority;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNamePath() {
    return namePath;
  }

  public void setNamePath(String namePath) {
    this.namePath = namePath;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((authority == null) ? 0 : authority.hashCode());
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
    SIAMOrganizationGrantedAuthority other = (SIAMOrganizationGrantedAuthority) obj;
    if (authority == null) {
      if (other.authority != null)
        return false;
    } else if (!authority.equals(other.authority))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "SIAMOrganizationGrantedAuthority [authority=" + authority + ", namePath=" + namePath + "]";
  }

}
