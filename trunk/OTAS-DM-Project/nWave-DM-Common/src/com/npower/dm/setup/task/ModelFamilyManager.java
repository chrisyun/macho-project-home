/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/ModelFamilyManager.java,v 1.1 2008/09/05 03:24:40 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/09/05 03:24:40 $
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
package com.npower.dm.setup.task;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/09/05 03:24:40 $
 */
public class ModelFamilyManager {
  
  private static ModelFamilyManager instance = null;
  
  private Map<String, ModelFamilyItem> families = new HashMap<String, ModelFamilyItem>();

  /**
   * Private constructor
   */
  private ModelFamilyManager() {
    super();
  }
  
  /**
   * Return a instance of ModelFamilyManager
   * @return
   */
  public static ModelFamilyManager getInstance() {
    synchronized (ModelFamilyManager.class) {
      if (instance == null) {
        instance = new ModelFamilyManager();
      }
      return instance;
    }
  }
  
  /**
   * Find family for the model
   * @param model
   * @return
   */
  public ModelFamilyItem findModelFamily(ModelItem model) {
    if (model == null) {
       return null;
    }
    return this.findModelFamily(model.getFamilyID());
  }
  
  /**
   * Get family of familyID
   * @param familyID
   * @return
   */
  public ModelFamilyItem findModelFamily(String familyID) {
    if (familyID == null) {
       return null;
    }
    return this.families.get(familyID.trim());
  }
  
  /**
   * Add a family into repository.
   * @param item
   */
  public void addModelFamily(ModelFamilyItem item) {
    if (item == null || StringUtils.isEmpty(item.getExternalID())) {
       return;
    }
    this.families.put(item.getExternalID().trim(), item);
  }
  
  /**
   * Copy family information into modelItem.
   * 仅仅从src中复制那些在dest中没有值得属性（为Empty）.
   * @param modelItem
   * @param familyItem
   * 
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws NoSuchMethodException
   */
  @SuppressWarnings("unchecked")
  private void copy(Object src, Object dest) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    Map<String, ?> props = PropertyUtils.describe(dest);
    for (String name: props.keySet()) {
        Object value = PropertyUtils.getProperty(dest, name);
        try {
            if (value == null) {
               Object valueOfFamily = PropertyUtils.getProperty(src, name);
               PropertyUtils.setProperty(dest, name, valueOfFamily);
            } else if (value instanceof List) {
              if (((List<?>)value).size() == 0) {
                 Object valueOfFamily = PropertyUtils.getProperty(src, name);
                 PropertyUtils.setProperty(dest, name, valueOfFamily);
              }
            }
        } catch (NoSuchMethodException e) {
          
        }
    }
  }

  /**
   * Merge family informations into ModelItem
   * @param item
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws NoSuchMethodException
   */
  public void merge(ModelItem item) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    ModelFamilyItem familyItem = this.findModelFamily(item);
    List<ModelFamilyItem> familyItems = new ArrayList<ModelFamilyItem>();
    while (familyItem != null) {
          familyItems.add(0, familyItem);
          familyItem = this.findModelFamily(familyItem.getParentID());
    }
    familyItem = new ModelFamilyItem();
    for (ModelFamilyItem family: familyItems) {
        PropertyUtils.copyProperties(familyItem, family);
    }
    this.copy(familyItem, item);
  }
}
