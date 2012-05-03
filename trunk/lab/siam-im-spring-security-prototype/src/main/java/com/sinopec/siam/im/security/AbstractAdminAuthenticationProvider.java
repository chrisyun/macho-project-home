/**
 * 
 */
package com.sinopec.siam.im.security;

import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 认证服务抽象类, 用于完成用户认证服务，并组装Authentication对象.
 * @author zhaodonglu
 * 
 */
public abstract class AbstractAdminAuthenticationProvider implements  AuthenticationProvider {
  
  private UserDetailsService userDetailsService = null;

  /**
   * 
   */
  /**
   * 
   */
  public AbstractAdminAuthenticationProvider() {
    super();
  }

  /**
   * @param userDetailsService
   */
  public AbstractAdminAuthenticationProvider(UserDetailsService userDetailsService) {
    super();
    this.userDetailsService = userDetailsService;
  }

  public UserDetailsService getUserDetailsService() {
    return userDetailsService;
  }

  public void setUserDetailsService(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.springframework.security.authentication.AuthenticationManager#authenticate
   * (org.springframework.security.core.Authentication)
   */
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    
    // 1. Check username and password
    doLogin(authentication);
    
    // 2. Get UserDetails
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(authentication.getName());
    
    // 3. Check and get all of admin roles and contexts.
    Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) userDetails.getAuthorities();
    if (authorities != null && !authorities.isEmpty()) {
      return new AdminAuthenticationToken(authentication.getName(), authentication.getCredentials(), authorities);
    }
    throw new BadCredentialsException("Bad Credentials and no admin permission");
  }

  /**
   * Check username and password. When passed, return UserDetails.
   * 
   * @param authentication
   * @return 
   * @throws AuthenticationException  Failure to login
   */
  protected abstract void doLogin(Authentication authentication) throws AuthenticationException;

}
