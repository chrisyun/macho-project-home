package com.sinopec.siam.im.security.permission;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import bigbank.Person;

import com.sinopec.siam.im.security.SIAMAdminRoleGrantedAuthority;

public class PersonCreatePermissionEvaluator implements PermissionEvaluator {
  
  public PersonCreatePermissionEvaluator() {
    super();
  }

  /* (non-Javadoc)
   * @see org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication, java.lang.Object, java.lang.Object)
   */
  public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
    if (targetDomainObject instanceof Person) {
      Person person = (Person)targetDomainObject;
      if (person == null || StringUtils.isEmpty(person.getOrgNamePath())) {
        return false;
      }
      String targetOrgNamePath = person.getOrgNamePath();
      for (GrantedAuthority authority : authentication.getAuthorities()) {
        if (authority instanceof SIAMAdminRoleGrantedAuthority) {
          SIAMAdminRoleGrantedAuthority roleAuthority = (SIAMAdminRoleGrantedAuthority) authority;
          if ("spOrgUnitAccountManagerRole".equalsIgnoreCase(roleAuthority.getAuthority()) 
              && roleAuthority.getControlledOrganization() != null
              && roleAuthority.getControlledOrganization().getNamePath() != null 
              && targetOrgNamePath.startsWith(roleAuthority.getControlledOrganization().getNamePath())) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication, java.io.Serializable, java.lang.String, java.lang.Object)
   */
  public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
    // TODO Auto-generated method stub
    return true;
  }

}
