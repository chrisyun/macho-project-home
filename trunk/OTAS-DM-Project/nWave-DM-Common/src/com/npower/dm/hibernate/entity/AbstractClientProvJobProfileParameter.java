/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/AbstractClientProvJobProfileParameter.java,v 1.1 2008/02/18 10:10:22 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2008/02/18 10:10:22 $
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

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/02/18 10:10:22 $
 */
public abstract class AbstractClientProvJobProfileParameter implements java.io.Serializable {

  // Fields

  private String               name;

  private ClientProvJobProfile profile;

  private Blob                 value;

  // Constructors

  /** default constructor */
  public AbstractClientProvJobProfileParameter() {
  }

  /** full constructor */
  public AbstractClientProvJobProfileParameter(ClientProvJobProfile profile, Blob value) {
    this.profile = profile;
    this.value = value;
  }

  // Property accessors

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ClientProvJobProfile getProfile() {
    return this.profile;
  }

  public void setProfile(ClientProvJobProfile profile) {
    this.profile = profile;
  }

  public Blob getValue() {
    return this.value;
  }

  public void setValue(Blob value) {
    this.value = value;
  }

}