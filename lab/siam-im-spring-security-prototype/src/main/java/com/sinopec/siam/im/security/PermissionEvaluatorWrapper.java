package com.sinopec.siam.im.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

public class PermissionEvaluatorWrapper implements PermissionEvaluator {
  
  private Map<String, PermissionEvaluator> permissionEvaluators = new HashMap<String, PermissionEvaluator>();

  public PermissionEvaluatorWrapper() {
    super();
  }

  public PermissionEvaluatorWrapper(Map<String, PermissionEvaluator> permissionEvaluators) {
    super();
    this.permissionEvaluators = permissionEvaluators;
  }

  public Map<String, PermissionEvaluator> getPermissionEvaluators() {
    return permissionEvaluators;
  }

  public void setPermissionEvaluators(Map<String, PermissionEvaluator> permissionEvaluators) {
    this.permissionEvaluators = permissionEvaluators;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication, java.lang.Object, java.lang.Object)
   */
  public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
    if (permission != null && this.permissionEvaluators.containsKey(permission)) {
       return this.permissionEvaluators.get(permission).hasPermission(authentication, targetDomainObject, permission);
    }
    // TODO set default evaluator 
    return true;
  }

  /* (non-Javadoc)
   * @see org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication, java.io.Serializable, java.lang.String, java.lang.Object)
   */
  public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
    if (permission != null && this.permissionEvaluators.containsKey(permission)) {
      return this.permissionEvaluators.get(permission).hasPermission(authentication, targetId, targetType, permission);
    }
    // TODO set default evaluator 
    return true;
  }

}
