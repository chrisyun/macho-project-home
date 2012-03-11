/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/selector/CharacterExpression.java,v 1.1 2008/09/04 06:01:19 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/09/04 06:01:19 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
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
package com.npower.dm.core.selector;

import java.util.Collection;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;

/**
 * The <tt>CharacterExpression</tt> may be used by applications as a framework for building
 * new kinds of <tt>Criterion</tt>. However, it is intended that most applications will
 * simply use the built-in criterion types via the static factory methods of this class.
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/04 06:01:19 $
 */
public class CharacterExpression {
  //private static final String CHARACTER_ALIAS_NAME = "character";

  /**
   * 
   */
  public CharacterExpression() {
    super();
  }
  
  /**
   * @param property
   */
  private static String getCategory(String property) {
    String category = property.substring(0, property.indexOf('.'));
    return category;
  }
  
  /**
   * @param property
   * @return
   */
  private static String getName(String property) {
    String name = property.substring(property.indexOf('.') + 1);
    return name;
  }

  /**
   * Return a expression for character.
   * For Examples: 
   *   general.developer.platform='S60 3rd Edition, Feature Pack 1'
   *   eq("general.developer.platform", "S60 3rd Edition, Feature Pack 1");
   * @param property
   * @param value
   * @return
   */
  public static Criterion eq(String property, String value) {
    String category = getCategory(property);
    String name = getName(property);
    Criterion exp = Expression.conjunction()
                              .add(Expression.eq("character.category", category))
                              .add(Expression.eq("character.name", name))
                              .add(Expression.eq("character.value", value));
    return exp;
  }

  /**
   * Apply a "not equal" constraint to the named property
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion ne(String property, String value) {
    String category = getCategory(property);
    String name = getName(property);
    Criterion exp = Expression.conjunction()
                              .add(Expression.eq("character.category", category))
                              .add(Expression.eq("character.name", name))
                              .add(Expression.ne("character.value", value));
    return exp;
  }
  /**
   * Apply a "like" constraint to the named property
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion like(String property, String value) {
    String category = getCategory(property);
    String name = getName(property);
    Criterion exp = Expression.conjunction()
                              .add(Expression.eq("character.category", category))
                              .add(Expression.eq("character.name", name))
                              .add(Expression.like("character.value", value));
    return exp;
  }
  /**
   * Apply a "like" constraint to the named property
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion like(String property, String value, MatchMode matchMode) {
    String category = getCategory(property);
    String name = getName(property);
    Criterion exp = Expression.conjunction()
                              .add(Expression.eq("character.category", category))
                              .add(Expression.eq("character.name", name))
                              .add(Expression.like("character.value", value, matchMode));
    return exp;
  }
  /**
   * A case-insensitive "like", similar to Postgres <tt>ilike</tt>
   * operator
   *
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion ilike(String property, String value, MatchMode matchMode) {
    String category = getCategory(property);
    String name = getName(property);
    Criterion exp = Expression.conjunction()
                              .add(Expression.eq("character.category", category))
                              .add(Expression.eq("character.name", name))
                              .add(Expression.ilike("character.value", value, matchMode));
    return exp;
  }
  /**
   * A case-insensitive "like", similar to Postgres <tt>ilike</tt>
   * operator
   *
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion ilike(String property, String value) {
    String category = getCategory(property);
    String name = getName(property);
    Criterion exp = Expression.conjunction()
                              .add(Expression.eq("character.category", category))
                              .add(Expression.eq("character.name", name))
                              .add(Expression.ilike("character.value", value));
    return exp;
  }
  /**
   * Apply a "greater than" constraint to the named property
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion gt(String property, String value) {
    String category = getCategory(property);
    String name = getName(property);
    Criterion exp = Expression.conjunction()
                              .add(Expression.eq("character.category", category))
                              .add(Expression.eq("character.name", name))
                              .add(Expression.gt("character.value", value));
    return exp;
  }
  /**
   * Apply a "less than" constraint to the named property
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion lt(String property, String value) {
    String category = getCategory(property);
    String name = getName(property);
    Criterion exp = Expression.conjunction()
                              .add(Expression.eq("character.category", category))
                              .add(Expression.eq("character.name", name))
                              .add(Expression.lt("character.value", value));
    return exp;
  }
  /**
   * Apply a "less than or equal" constraint to the named property
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion le(String property, String value) {
    String category = getCategory(property);
    String name = getName(property);
    Criterion exp = Expression.conjunction()
                              .add(Expression.eq("character.category", category))
                              .add(Expression.eq("character.name", name))
                              .add(Expression.le("character.value", value));
    return exp;
  }
  /**
   * Apply a "greater than or equal" constraint to the named property
   * @param propertyName
   * @param value
   * @return Criterion
   */
  public static Criterion ge(String property, String value) {
    String category = getCategory(property);
    String name = getName(property);
    Criterion exp = Expression.conjunction()
                              .add(Expression.eq("character.category", category))
                              .add(Expression.eq("character.name", name))
                              .add(Expression.ge("character.value", value));
    return exp;
  }
  /**
   * Apply a "between" constraint to the named property
   * @param propertyName
   * @param lo value
   * @param hi value
   * @return Criterion
   */
  public static Criterion between(String property, String lv, String hv) {
    String category = getCategory(property);
    String name = getName(property);
    Criterion exp = Expression.conjunction()
                              .add(Expression.eq("character.category", category))
                              .add(Expression.eq("character.name", name))
                              .add(Expression.between("character.value", lv, hv));
    return exp;
  }
  /**
   * Apply an "in" constraint to the named property
   * @param propertyName
   * @param values
   * @return Criterion
   */
  public static Criterion in(String property, String[] values) {
    String category = getCategory(property);
    String name = getName(property);
    Criterion exp = Expression.conjunction()
                              .add(Expression.eq("character.category", category))
                              .add(Expression.eq("character.name", name))
                              .add(Expression.in("character.value", values));
    return exp;
  }
  /**
   * Apply an "in" constraint to the named property
   * @param propertyName
   * @param values
   * @return Criterion
   */
  public static Criterion in(String property, Collection<String> values) {
    String category = getCategory(property);
    String name = getName(property);
    Criterion exp = Expression.conjunction()
                              .add(Expression.eq("character.category", category))
                              .add(Expression.eq("character.name", name))
                              .add(Expression.in("character.value", values));
    return exp;
  }
  /**
   * Apply an "is null" constraint to the named property
   * @return Criterion
   */
  public static Criterion isNull(String property) {
    String category = getCategory(property);
    String name = getName(property);
    Criterion exp = Expression.conjunction()
                              .add(Expression.eq("character.category", category))
                              .add(Expression.eq("character.name", name))
                              .add(Expression.isNull("character.value"));
    return exp;
  }

  /**
   * Apply an "is not null" constraint to the named property
   * @return Criterion
   */
  public static Criterion isNotNull(String property) {
    String category = getCategory(property);
    String name = getName(property);
    Criterion exp = Expression.conjunction()
                              .add(Expression.eq("character.category", category))
                              .add(Expression.eq("character.name", name))
                              .add(Expression.isNotNull("character.value"));
    return exp;
  }
}
