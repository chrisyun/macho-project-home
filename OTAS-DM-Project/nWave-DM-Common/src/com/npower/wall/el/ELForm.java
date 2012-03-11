/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELForm.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELForm extends com.npower.wall.WallForm {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String enable_wmlExpr;
    private String actionExpr;
    private String methodExpr;

    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setEnable_wml(String enable_wml) {
	this.enable_wmlExpr = enable_wml;
    }
    @Override
    public void setAction(String action) {
	this.actionExpr = action;
    }
    @Override
    public void setMethod(String method) {
	this.methodExpr = method;
    }


    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (enable_wmlExpr != null) {
            super.setEnable_wml(eval.evalBoolean("enable_wml", enable_wmlExpr));
        }
        if (actionExpr != null) {
            super.setAction(eval.evalString("action", actionExpr));
        }
        if (methodExpr != null) {
            super.setMethod(eval.evalString("method", methodExpr));
        }
    }

    @Override
    public void release() {
        super.release();
        this.enable_wmlExpr = null;
        this.actionExpr = null;
        this.methodExpr = null;
    }

}
