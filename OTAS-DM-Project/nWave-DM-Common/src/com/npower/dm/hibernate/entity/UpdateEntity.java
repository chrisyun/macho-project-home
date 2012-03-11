/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/entity/UpdateEntity.java,v 1.6 2008/11/24 10:46:56 zhao Exp $
 * $Revision: 1.6 $
 * $Date: 2008/11/24 10:46:56 $
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Image;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.6 $ $Date: 2008/11/24 10:46:56 $
 */
public class UpdateEntity extends AbstractUpdate implements java.io.Serializable {

  // Constructors

  /** default constructor */
  public UpdateEntity() {
    super();
  }

  /** minimal constructor */
  public UpdateEntity(Image from, Image to) {
    super(from, to);
  }

  /** constructor */
  public UpdateEntity(Image from, Image to, InputStream bytes) throws IOException {
    super(from, to, null, null);
    DMBinary binary = new DMBinary(bytes);
    super.setDMBlob(binary);
  }

  /** full constructor */
  public UpdateEntity(Image from, Image to, DMBinary binary, String description) {
    super(from, to, binary, description);
  }

  public InputStream getInputStream() throws DMException {
    try {
      return this.getDMBlob().getBinaryBlob().getBinaryStream();
    } catch (SQLException e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Update#getSize()
   */
  public int getSize() throws Exception {
    int size = 0;
    
    try {
      InputStream ins = this.getInputStream();
      int b = ins.read();
      while (b >= 0) {
            size++;
            b = ins.read();
      }
      ins.close();
    } catch (DMException e) {
      throw e;
    } catch (IOException e) {
      throw e;
    }
    return size;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Update#getBytes()
   */
  public byte[] getBytes() throws DMException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
        byte[] buf = new byte[512];
        InputStream in = this.getInputStream();
        int len = in.read(buf);
        while (len > 0) {
              out.write(buf, 0, len);
              len = in.read(buf);
        }
        in.close();
        return out.toByteArray();
    } catch (IOException e) {
      throw new DMException("Error in read update bytes.", e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.Update#setBinary(java.io.InputStream)
   */
  public void setBinary(InputStream in) throws DMException, IOException {
    DMBinary binary = new DMBinary(in);
    super.setDMBlob(binary);
  }

}
