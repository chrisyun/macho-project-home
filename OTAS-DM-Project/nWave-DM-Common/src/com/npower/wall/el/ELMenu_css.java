/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELMenu_css.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELMenu_css extends com.npower.wall.WallMenu_css {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String bgcolor1Expr;
    private String bgcolor2Expr;

    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setBgcolor1(String bgcolor1) {
	this.bgcolor1Expr = bgcolor1;
    }
    @Override
    public void setBgcolor2(String bgcolor2) {
	this.bgcolor2Expr = bgcolor2;
    }

    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (bgcolor1Expr != null) {
            super.setBgcolor1(eval.evalString("bgcolor1", bgcolor1Expr));
        }
        if (bgcolor2Expr != null) {
            super.setBgcolor2(eval.evalString("bgcolor2", bgcolor2Expr));
        }
    }

    @Override
    public void release() {
        super.release();
        this.bgcolor1Expr = null;
        this.bgcolor2Expr = null;
    }


}
