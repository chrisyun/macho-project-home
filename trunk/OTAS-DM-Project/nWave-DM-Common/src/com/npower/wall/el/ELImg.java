/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELImg.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELImg extends com.npower.wall.WallImg {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String srcExpr;
    private String altExpr;

    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setSrc(String src) {
	this.srcExpr = src;
    }
    @Override
    public void setAlt(String alt) {
	this.altExpr = alt;
    }

    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (srcExpr != null) {
            super.setSrc(eval.evalString("src", srcExpr));
        }
        if (altExpr != null) {
            super.setAlt(eval.evalString("alt", altExpr));
        }
    }

    @Override
    public void release() {
        super.release();
        this.srcExpr = null;
        this.altExpr = null;
    }


}
