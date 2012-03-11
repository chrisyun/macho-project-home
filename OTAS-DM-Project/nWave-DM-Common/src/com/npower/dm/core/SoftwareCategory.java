/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/SoftwareCategory.java,v 1.9 2008/08/29 15:07:34 zhao Exp $
 * $Revision: 1.9 $
 * $Date: 2008/08/29 15:07:34 $
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

import java.util.List;
import java.util.Set;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.9 $ $Date: 2008/08/29 15:07:34 $
 */
public interface SoftwareCategory {

  /**
   * @return
   */
  public abstract long getId();

  /**
   * @param categoryId
   */
  public abstract void setId(long categoryId);

  /**
   * @return
   */
  public abstract SoftwareCategory getParent();

  /**
   * @param category
   */
  public abstract void setParent(SoftwareCategory category);

  /**
   * @return
   */
  public abstract String getName();

  /**
   * @param name
   */
  public abstract void setName(String name);

  /**
   * @return
   */
  public abstract String getDescription();

  /**
   * @param description
   */
  public abstract void setDescription(String description);

  /**
   * 返回子分类
   * @return
   */
  public abstract Set<SoftwareCategory> getChildren();

  /**
   * @param children
   */
  public abstract void setChildren(Set<SoftwareCategory> children);
  
  /**
   * 返回所有子孙分类
   * @return
   */
  public abstract Set<SoftwareCategory> getDescendants();

  /**
   * 返回当前分类下的所有软件, 但不包含子分类下的软件.
   * @return
   */
  public abstract Set<Software> getSoftwares();

  /**
   * 返回当前分类下的所有软件, 包含子分类下的软件.
   * @return
   */
  public abstract Set<Software> getAllOfSoftwares();
  
  /**
   * Return depth in category tree, root node is 0
   * @return
   */
  public abstract int getTreeDepth();
  
  /**
   * Return category path combined with name of category.
   * @return
   */
  public abstract List<String> getPath();
  
  /**
   * Return a Category path combined with name of category.
   * @return
   */
  public abstract String getPathAsString(String delimit);
  
  /**
   * Return a category path, for example:
   * /A/B/C
   * @return
   */
  public abstract String getPathAsString();
  
  /**
   * Return a Category path combined with instance of category
   * @return
   */
  public abstract List<SoftwareCategory> getPathAsCategories();
}