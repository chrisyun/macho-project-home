/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ModelDDFTree.java,v 1.3 2006/06/04 14:31:57 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2006/06/04 14:31:57 $
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
package com.npower.dm.hibernate.entity;

import com.npower.dm.core.DDFTree;
import com.npower.dm.core.Model;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2006/06/04 14:31:57 $
 */
public class ModelDDFTree extends AbstractModelDDFTree implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public ModelDDFTree() {
    super();
  }

  /** full constructor */
  public ModelDDFTree(ModelDDFTreeID id, DDFTree nwDmDdfTree, Model nwDmModel) {
    super(id, nwDmDdfTree, nwDmModel);
  }

  public boolean equals(Object other) {
    if ((this == other))
      return true;

    if ((other == null))
      return false;

    if (!(other instanceof ModelDDFTree))
      return false;

    ModelDDFTree castOther = (ModelDDFTree) other;

    return (this.getID().getDeviceModelId() == castOther.getID().getDeviceModelId())
        && (this.getID().getDdfTreeId() == castOther.getID().getDdfTreeId());
  }

  public int hashCode() {
    int result = 17;

    result = 37 * result + (int) this.getID().getDeviceModelId();
    result = 37 * result + (int) this.getID().getDdfTreeId();
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(T)
   */
  public int compareTo(Object o) {
    if (o != null && o instanceof ModelDDFTree) {
       return (int)(this.getID().getDdfTreeId() - (((ModelDDFTree)o).getID().getDdfTreeId()));
    } else {
      return 1;
    }
    
  }

}
