package com.ibm.tivoli.utils;
/**
 * 
 */


import java.io.IOException;
import java.util.Properties;

/**
 * @author zhaodonglu
 *
 */
public class SpringPropertiesWrapper extends Properties {

  /**
   * 
   */
  private static final long serialVersionUID = -4715916391272849372L;

  /**
   * 
   */
  protected SpringPropertiesWrapper() {
    super();
  }

  /**
   * @param defaults
   */
  protected SpringPropertiesWrapper(Properties defaults) {
    super(defaults);
  }
  
  /**
   * @param defaults
   * @throws IOException 
   */
  protected SpringPropertiesWrapper(SpringPropertyPlaceholderConfigurer propertyPlaceholderConfigurer) throws IOException {
    super(propertyPlaceholderConfigurer.getProperties());
  }

}
