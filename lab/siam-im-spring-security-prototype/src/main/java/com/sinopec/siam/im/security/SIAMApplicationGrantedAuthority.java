/**
 * 
 */
package com.sinopec.siam.im.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author zhaodonglu
 * 
 */
public class SIAMApplicationGrantedAuthority implements GrantedAuthority {

  /**
   * 
   */
  private static final long serialVersionUID = 6550798722867682214L;
  /**
   * Application DN
   */
  private String authority = null;

  /**
   * Application name
   */
  private String name = null;

  /**
   * 
   */
  public SIAMApplicationGrantedAuthority() {
    super();
  }

  public SIAMApplicationGrantedAuthority(String authority, String name) {
    super();
    this.authority = authority;
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
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
    SIAMApplicationGrantedAuthority other = (SIAMApplicationGrantedAuthority) obj;
    if (authority == null) {
      if (other.authority != null)
        return false;
    } else if (!authority.equals(other.authority))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "SIAMApplicationGrantedAuthority [authority=" + authority + ", name=" + name + "]";
  }

}
