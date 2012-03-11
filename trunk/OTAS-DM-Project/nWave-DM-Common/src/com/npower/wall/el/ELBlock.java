/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELBlock.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELBlock extends com.npower.wall.WallBlock {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String alignExpr;

    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setAlign(String align) {
	this.alignExpr = align;
    }

    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (alignExpr != null) {
            super.setAlign(eval.evalString("align", alignExpr));
        }
    }

    @Override
    public void release()
    {
        super.release();
        this.alignExpr = null;
    }


}
