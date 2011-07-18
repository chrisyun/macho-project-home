package com.ibm.tivoli.ldap;

public abstract class GlobalIDGenerator {
  /**
   * 生成一个ErGlobalID
   * @return
   */
  public static String generate() {
    return System.currentTimeMillis() + "";
  }
}
