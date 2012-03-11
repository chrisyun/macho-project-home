/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELOption.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELOption extends com.npower.wall.WallOption {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String valueExpr;
    private String selectedExpr;

    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setValue(String value) {
	this.valueExpr = value;
    }
    @Override
    public void setSelected(String selected) {
	this.selectedExpr = selected;
    }

    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (valueExpr != null) {
            super.setValue(eval.evalString("value", valueExpr));
        }
        if (selectedExpr != null) {
            super.setSelected(eval.evalString("selected", selectedExpr));
        }
    }

    @Override
    public void release() {
        super.release();
        this.valueExpr = null;
        this.selectedExpr = null;
    }


}
