/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/DMBinary.java,v 1.3 2008/04/02 06:24:24 zhao Exp $
 * $Revision: 1.3 $
 * $Date: 2008/04/02 06:24:24 $
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

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

import org.hibernate.Hibernate;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/04/02 06:24:24 $
 */
public class DMBinary extends AbstractDMBinary implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public DMBinary() {
    super();
  }

  /** minimal constructor */
  public DMBinary(Blob binary) {
    super(binary);
  }

  /**
   * Constructor
   * 
   * @param in
   * @throws IOException
   */
  public DMBinary(InputStream in) throws IOException {
    this();
    this.setBinary(in);
  }

  /**
   * Constructor
   * 
   * @param bytes
   */
  public DMBinary(byte[] bytes) {
    this();
    this.setBinary(bytes);
  }

  /**
   * Assign InputStream as content of Binary.
   * 
   * @param in
   * @throws IOException
   */
  public void setBinary(InputStream in) throws IOException {
    this.setBinaryBlob(Hibernate.createBlob(in));
  }

  /**
   * Assign bytes as content of Binary.
   * 
   * @param bytes
   */
  public void setBinary(byte[] bytes) {
    this.setBinaryBlob(Hibernate.createBlob(bytes));
  }

}
