/**
 * 
 */
package com.sinopec.siam.im.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author zhaodonglu
 * 
 */
public class SimpleAuthenticationProvider extends AbstractAdminAuthenticationProvider {

  /**
   * 
   */
  public SimpleAuthenticationProvider() {
    super();
  }

  public SimpleAuthenticationProvider(UserDetailsService userDetailsService) {
    super(userDetailsService);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.sinopec.siam.im.security.AbstractAuthenticationManager#doLogin(org.
   * springframework.security.core.Authentication)
   */
  @Override
  protected void doLogin(Authentication authentication) throws AuthenticationException {
    if (!authentication.getPrincipal().equals(authentication.getCredentials())) {
      throw new AuthenticationCredentialsNotFoundException("Login failure!");
    }
  }

  public boolean supports(Class<? extends Object> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }

}
