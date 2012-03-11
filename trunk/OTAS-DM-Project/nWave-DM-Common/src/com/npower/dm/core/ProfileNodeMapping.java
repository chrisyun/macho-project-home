/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/ProfileNodeMapping.java,v 1.7 2008/12/12 10:33:35 zhao Exp $
 * $Revision: 1.7 $
 * $Date: 2008/12/12 10:33:35 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
 *
 * This SOURCE CODE FILE, which has been provided by NPower as part
 * of a NPower product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of NPower.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 * ===============================================================================================
 */
package com.npower.dm.core;


/**
 * <p>Represent NodeMapping, mapping a attribute of template to the DDF Node.</p>
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.7 $ $Date: 2008/12/12 10:33:35 $
 */
public interface ProfileNodeMapping {

  /**
   * Constance for NODE_MAPPING_TYPE
   */
  public final static String NODE_MAPPING_TYPE_ATTRIBUTE = "attribute";
  public final static String NODE_MAPPING_TYPE_VALUE = "value";

  /**
   * Return the ID.
   * @return
   */
  public abstract long getID();

  /**
   * Return the ProfileMapping which own this NodeMapping.
   * @return
   */
  public abstract ProfileMapping getProfileMapping();

  /**
   * set a ProfileMapping for this NodeMapping.
   * @param nwDmProfileMapping
   */
  public abstract void setProfileMapping(ProfileMapping nwDmProfileMapping);

  /**
   * Return the Attribute which is template's attribute.
   * @return
   */
  public abstract ProfileAttribute getProfileAttribute();

  /**
   * Set a attribute for this NodeMapping.
   * @param profileAttribute
   */
  public abstract void setProfileAttribute(ProfileAttribute profileAttribute);

  /**
   * Return the DDF Node of this NodeMapping
   * @return
   */
  public abstract DDFNode getDdfNode();

  /**
   * Set the DDF Node for this NodeMapping
   * @param ddfNode
   */
  public abstract void setDdfNode(DDFNode ddfNode);

  /**
   * Setter of AttributeName for Digester
   * 
   * @param value
   */
  public void setNodeRelativePath(String value);

  /**
   * Setter NodeRelatvePath for Digester
   * 
   * @param value
   */
  public String getNodeRelativePath();

  /**
   * Return the type of mapping.
   * @return
   */
  public abstract String getMappingType();

  /**
   * Set a type for this mapping.
   * @param mappingType
   */
  public abstract void setMappingType(String mappingType);

  /**
   * Return the value.
   * @return
   */
  public abstract String getValue();

  /**
   * Set a value for this mapping.
   * @param value
   */
  public abstract void setValue(String value);

  /**
   * Return the display name
   * @return
   */
  public abstract String getDisplayName();

  /**
   * Set a name using for diplay in form.
   * @param displayName
   */
  public abstract void setDisplayName(String displayName);

  /**
   * Return the category's name of this mapping.
   * @return
   */
  public abstract String getCategoryName();

  /**
   * Return a category's name for this mapping.
   * 
   * @param categoryName
   */
  public abstract void setCategoryName(String categoryName);

  /**
   * Return the index of this Mapping , the index use to controll the node mapping's order when provision to a device.
   * @return
   */
  public abstract long getParamIndex();

  /**
   * Set the index of this Mapping.
   * @param paramIndex
   */
  public abstract void setParamIndex(long paramIndex);

  /**
   * Translate orginal value into new value based on translation policy.
   * If translation policy undefined and could not found original value from translation maps, 
   * will return original value.
   * @param value
   * @return
   */
  public abstract String translateValue(String value);

  /**
   * 设置执行Mapping时的DM Command.
   * 此值若为null, 将根据DDF中该节点的Access决定, Add优先于Replace 
   * @param command
   */
  public abstract void setCommand(String command);
  
  /**
   * 返回执行Mapping时的DM Command
   * @return
   */
  public abstract String getCommand();
  

  /**
   * @param format
   *        节点值类型, 例如: chr, xml等
   */
  public abstract void setValueFormat(String format);
  
  /**
   * 节点值类型, 例如: chr, xml等
   * @return
   */
  public abstract String getValueFormat();

  /**
   * 节点值得MIME Type, 如: image/jpeg, x-epoc/x-sisx-app等
   * @return
   */
  public abstract String getDefaultValueMimeType();
  
  /**
   * 节点值得MIME Type, 如: image/jpeg, x-epoc/x-sisx-app等
   * @param mimeType
   */
  public abstract void setDefaultValueMimeType(String mimeType);
  
  /**
   * 
   * @return
   */
  //public abstract Set getAttribTranslationses();

}