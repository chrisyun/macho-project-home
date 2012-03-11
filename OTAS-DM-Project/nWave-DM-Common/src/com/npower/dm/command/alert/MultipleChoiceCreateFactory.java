/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/command/alert/MultipleChoiceCreateFactory.java,v 1.1 2007/03/29 08:16:33 zhao Exp $
  * $Revision: 1.1 $
  * $Date: 2007/03/29 08:16:33 $
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
package com.npower.dm.command.alert;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ObjectCreationFactory;
import org.xml.sax.Attributes;

import sync4j.framework.core.AlertCode;
import sync4j.framework.engine.dm.UserAlertManagementOperation;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2007/03/29 08:16:33 $
 */
public class MultipleChoiceCreateFactory implements ObjectCreationFactory {

  private Digester digester = null;
  /**
   * 
   */
  public MultipleChoiceCreateFactory() {
    super();
  }

  /* (non-Javadoc)
   * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
   */
  public Object createObject(Attributes attributes) throws Exception {
    UserAlertManagementOperation object = new UserAlertManagementOperation();
    object.setAlertCode(AlertCode.MULTIPLE_CHOICE);
    object.setMaxDisplayTime(-1);
    object.setMinDisplayTime(-1);
    object.setDefaultResponse(null);
    object.setMaxLength(-1);
    object.setEchoType(' ');
    object.setInputType(' ');
    return object;
  }

  /* (non-Javadoc)
   * @see org.apache.commons.digester.ObjectCreationFactory#getDigester()
   */
  public Digester getDigester() {
    return this.digester;
  }

  /* (non-Javadoc)
   * @see org.apache.commons.digester.ObjectCreationFactory#setDigester(org.apache.commons.digester.Digester)
   */
  public void setDigester(Digester digester) {
    this.digester = digester;
  }

}
