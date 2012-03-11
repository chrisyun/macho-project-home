/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractModelClientProvMapId.java,v 1.2 2007/05/24 06:01:02 zhao Exp $
  * $Revision: 1.2 $
  * $Date: 2007/05/24 06:01:02 $
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
package com.npower.dm.hibernate.entity;

import com.npower.dm.core.ClientProvTemplate;
import com.npower.dm.core.Model;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.2 $ $Date: 2007/05/24 06:01:02 $
 */
public abstract class AbstractModelClientProvMapId implements java.io.Serializable {

  // Fields

  private long              modelId;

  private long templateId;

  // Constructors

  /** default constructor */
  public AbstractModelClientProvMapId() {
  }

  /** full constructor */
  public AbstractModelClientProvMapId(Model model, ClientProvTemplate clientProvTemplateEntity) {
    this.modelId = model.getID();
    this.templateId = clientProvTemplateEntity.getID().longValue();
  }

  // Property accessors

  public long getModelId() {
    return this.modelId;
  }

  public void setModelId(long modelId) {
    this.modelId = modelId;
  }

  public long getTemplateId() {
    return this.templateId;
  }

  public void setTemplateId(long id) {
    this.templateId = id;
  }

  public boolean equals(Object other) {
    if ((this == other))
      return true;
    if ((other == null))
      return false;
    if (!(other instanceof AbstractModelClientProvMapId))
      return false;
    AbstractModelClientProvMapId castOther = (AbstractModelClientProvMapId) other;

    return ((this.getModelId() == castOther.getModelId()) || (this.getModelId() != 0
        && castOther.getModelId() != 0 && this.getModelId() == castOther.getModelId()))
        && ((this.getTemplateId() == castOther.getTemplateId()) || (this
            .getTemplateId() != 0
            && castOther.getTemplateId() != 0 && this.getTemplateId() == 
            castOther.getTemplateId()));
  }

  public int hashCode() {
    long result = 17;

    result = 37 * result + (getModelId() == 0 ? 0 : this.getModelId());
    result = 37 * result + (getTemplateId() == 0 ? 0 : this.getTemplateId());
    return (int)result;
  }

}