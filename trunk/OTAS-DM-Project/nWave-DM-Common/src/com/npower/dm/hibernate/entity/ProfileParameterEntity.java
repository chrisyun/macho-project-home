/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/ProfileParameterEntity.java,v 1.1 2008/12/10 05:24:20 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/12/10 05:24:20 $
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

import java.sql.Blob;
import java.sql.Clob;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.ProfileAssignment;
import com.npower.dm.core.ProfileAttribute;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/12/10 05:24:20 $
 */
public class ProfileParameterEntity extends AbstractProfileParameter implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public ProfileParameterEntity() {
    super();
  }

  /** minimal constructor */
  public ProfileParameterEntity(DDFNode ddfNode, String parameterType, String dmTreePath) {
    super(ddfNode, parameterType, dmTreePath);
  }

  /** full constructor */
  public ProfileParameterEntity(ProfileAssignment profileAssignment, DDFNode ddfNode,
      ProfileAttribute profileAttribute, String parameterType, String dmTreePath, long paramIndex,
      String itemDataKind, String updateId, Clob rawData, Blob binaryData, String MFormat, String MType,
      String paramName) {
    super(profileAssignment, ddfNode, profileAttribute, parameterType, dmTreePath, paramIndex,
        itemDataKind, updateId, rawData, binaryData, MFormat, MType, paramName);
  }

}
