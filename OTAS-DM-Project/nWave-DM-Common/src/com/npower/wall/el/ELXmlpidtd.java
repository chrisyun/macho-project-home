/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELXmlpidtd.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELXmlpidtd extends com.npower.wall.WallXmlpidtd {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String encodingExpr;


    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setEncoding(String e) {
	this.encodingExpr = e;
    }

    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (encodingExpr != null) {
            super.setEncoding(eval.evalString("encoding", encodingExpr));
        }
    }

    @Override
    public void release() {
        super.release();
        this.encodingExpr = null;
    }


}
