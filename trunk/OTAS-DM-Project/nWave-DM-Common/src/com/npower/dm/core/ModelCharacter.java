/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/core/ModelCharacter.java,v 1.2 2008/06/05 10:34:41 zhao Exp $
 * $Revision: 1.2 $
 * $Date: 2008/06/05 10:34:41 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2007 NPower Network Software Ltd.  All rights reserved.
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
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2008/06/05 10:34:41 $
 */
public interface ModelCharacter {

  /**
   * @return the iD
   */
  public abstract long getID();

  /**
   * @param id the iD to set
   */
  public abstract void setID(long id);

  /**
   * @return the category
   */
  public abstract String getCategory();

  /**
   * @param category the category to set
   */
  public abstract void setCategory(String category);

  /**
   * @return the name
   */
  public abstract String getName();

  /**
   * @param name the name to set
   */
  public abstract void setName(String name);

  /**
   * @return the value
   */
  public abstract String getValue();

  /**
   * @param value the value to set
   */
  public abstract void setValue(String value);

  /**
   * @return the model
   */
  public abstract Model getModel();

  /**
   * @param model the model to set
   */
  public abstract void setModel(Model model);

}