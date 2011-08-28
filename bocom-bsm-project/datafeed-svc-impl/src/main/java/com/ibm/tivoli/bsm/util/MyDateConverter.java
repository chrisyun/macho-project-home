/**
 * 
 */
package com.ibm.tivoli.bsm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

import com.ibm.tivoli.bsm.service.DataFeedException;

/**
 * @author ZhaoDongLu
 * 
 */
public class MyDateConverter implements Converter {

  /**
   * 
   */
  public MyDateConverter() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class,
   * java.lang.Object)
   */
  public Object convert(Class clazz, Object value) {
    if (clazz.equals(Date.class)) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
      
      try {
        String s = (String) value;
        if (s.endsWith(":00")) {
           s = s.substring(0, s.lastIndexOf(":00")) + "00";
        }
        return sdf.parse(s);
      } catch (ParseException pe) {
        throw new DataFeedException(10, pe);
      } catch (ClassCastException cce) {
        throw new DataFeedException(10, cce);
      }
    } else {
      throw new DataFeedException(10, "Expected Date class");
    }

  }

}
