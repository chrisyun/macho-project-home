package com.ibm.tivoli.tuna.dao;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;

import com.ibm.tivoli.tuna.service.AttributeStatement;
import com.ibm.tivoli.tuna.util.StringUtil;


public class LoginContextMapper implements ContextMapper {
  private static Log log = LogFactory.getLog(LoginContextMapper.class);
  
  //所需要查找的属性，若为空则查找全部
  private String[] arrShowField = new String[0];

  
  /**
   * 
   * @param multivalued
   * @param appClass
   */
  public LoginContextMapper(String[] arrShowField) {
    super();
    this.arrShowField = arrShowField;
  }
  
  public Object mapFromContext(Object ctx) {
    try{
      AttributeStatement obj = new AttributeStatement();
      if(ctx == null) {
        return obj;
      } 
      
      DirContextAdapter context = (DirContextAdapter) ctx;
      Attributes attrs = context.getAttributes();
      NamingEnumeration results = attrs.getAll();
      
      while(results.hasMoreElements()) {
        try {
          Attribute attr = (Attribute) results.nextElement();
          String name = attr.getID().toLowerCase();
          
          if(StringUtil.isNullArray(arrShowField) || StringUtil.isExistElement(name, arrShowField)) {
            //不区分单值和多值，统一按照多值去取
            List<String> buteValues=new ArrayList<String>();
            NamingEnumeration repeatEnumer = attr.getAll();
            while (repeatEnumer.hasMoreElements()) {
              String value = (String) repeatEnumer.nextElement();
              buteValues.add(value);
            }
            obj.getAttributes().add(
                new com.ibm.tivoli.tuna.service.Attribute(name, "string", buteValues));
          } 
        } catch(Exception ex) {
          log.error("Ldap属性转换出现异常：", ex);
        }
      }
      com.ibm.tivoli.tuna.service.Attribute userDN = 
        new com.ibm.tivoli.tuna.service.Attribute("userdn", "String", context.getNameInNamespace());
      obj.getAttributes().add(userDN);
      return obj;
    }catch(Exception e){
      log.error("Ldap对象转换出现异常：", e);
    }
    return null;
  }
  
}
