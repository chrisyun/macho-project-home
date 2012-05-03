/**
 * 
 */
package com.sinopec.siam.im.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author zhaodonglu
 * 
 */
public class SimpleAdminUserDetailsService extends AbstractAdminUserDetailsService {

  /**
   * 
   */
  public SimpleAdminUserDetailsService() {
    super();
  }

  /* (non-Javadoc)
   * @see com.sinopec.siam.im.security.AbstractAdminUserDetailsService#loadAuthoritiesIntoUserDetails(java.lang.String, com.sinopec.siam.im.security.SIAMAdminUserDetails)
   */
  @Override
  protected void loadAuthoritiesIntoUserDetails(String username, SIAMAdminUserDetails userDetails) throws UsernameNotFoundException {
    if (username.equals("appadmin")) {
      Collection<SIAMAdminRoleGrantedAuthority> authorities = new HashSet<SIAMAdminRoleGrantedAuthority>();
      authorities.add(new SIAMAdminRoleGrantedAuthority("spOrgUnitAccountManagerRole", new SIAMApplicationGrantedAuthority(
          "ERGLOBALID=1237867635971381002,OU=SERVICES,ERGLOBALID=00000000000000000000,OU=SINOPEC,DC=COM", ""), new SIAMOrganizationGrantedAuthority(
          "ERGLOBALID=3100112875204009617,OU=ORGCHART,ERGLOBALID=00000000000000000000,OU=SINOPEC,DC=COM", "西安分公司", "/中国石油化工集团公司/西安分公司")));
      authorities.add(new SIAMAdminRoleGrantedAuthority("spOrgUnitAccountManagerRole", new SIAMApplicationGrantedAuthority(
          "ERGLOBALID=1237867635971381002,OU=SERVICES,ERGLOBALID=00000000000000000000,OU=SINOPEC,DC=COM", ""), new SIAMOrganizationGrantedAuthority(
          "ERGLOBALID=3100112875203000001,OU=ORGCHART,ERGLOBALID=00000000000000000000,OU=SINOPEC,DC=COM", "中原油田", "/中国石油化工集团公司/中原油田")));

      userDetails.setAuthorities(authorities);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.sinopec.siam.im.security.AbstractAdminUserDetailsService#
   * loadBasicInfoIntoUserDetails(java.lang.String,
   * com.sinopec.siam.im.security.SIAMAdminUserDetails)
   */
  @Override
  protected void loadBasicInfoIntoUserDetails(String username, SIAMAdminUserDetails userDetails) throws UsernameNotFoundException {
    userDetails.setDn("ERGLOBALID=1450555340113988235,OU=0,OU=PEOPLE,ERGLOBALID=00000000000000000000,OU=SINOPEC,DC=COM");
    userDetails.setEnabled(true);
    userDetails.setAccountNonExpired(true);
    userDetails.setAccountNonLocked(true);
    userDetails.setCredentialsNonExpired(true);
    userDetails.setEnabled(true);
  }

}
