package com.ibm.tivoli.icbc.boot;

import java.security.BasicPermission;

/**
 * Java SecurityManager Permission class for JNDI name based file resources
 * <p>
 * The JndiPermission extends the BasicPermission. The permission name is a full
 * or partial jndi resource name. An * can be used at the end of the name to
 * match all named resources that start with name. There are no actions.
 * </p>
 * <p>
 * Example that grants permission to read all JNDI file based resources:
 * <li>permission org.apache.naming.JndiPermission "*";</li>
 * </p>
 * 
 * @author Glenn Nielsen
 * @version $Revision: 1.4 $ $Date: 2005/01/26 00:31:57 $
 */

public final class JndiPermission extends BasicPermission {

  // ----------------------------------------------------------- Constructors

  /**
   * Creates a new JndiPermission with no actions
   * 
   * @param String
   *          - JNDI resource path name
   */
  public JndiPermission(String name) {
    super(name);
  }

  /**
   * Creates a new JndiPermission with actions
   * 
   * @param String
   *          - JNDI resource path name
   * @param String
   *          - JNDI actions (none defined)
   */
  public JndiPermission(String name, String actions) {
    super(name, actions);
  }
}