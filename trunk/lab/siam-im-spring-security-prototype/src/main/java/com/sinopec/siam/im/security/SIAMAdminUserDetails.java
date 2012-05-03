/**
 * 
 */
package com.sinopec.siam.im.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author zhaodonglu
 * 
 */
public class SIAMAdminUserDetails implements UserDetails {

  /**
   * 
   */
  private static final long serialVersionUID = 6790155700613758098L;
  /**
   * User DN (Person DN)
   */
  private String dn = null;
  private Collection<? extends GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
  private String username = null;
  private boolean accountNonExpired = false;
  private boolean accountNonLocked = false;
  private boolean credentialsNonExpired = false;
  private boolean enabled = false;

  /**
   * 
   */
  public SIAMAdminUserDetails() {
    super();
  }

  public String getDn() {
    return dn;
  }

  public void setDn(String dn) {
    this.dn = dn;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
   */
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
   */
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
   */
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  public void setAccountNonExpired(boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
   */
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  public void setAccountNonLocked(boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
   */
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
   */
  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
   */
  public String getPassword() {
    // Always return null
    return null;
  }

}
