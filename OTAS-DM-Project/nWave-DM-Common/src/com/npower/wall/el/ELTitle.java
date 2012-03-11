/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELTitle.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELTitle extends com.npower.wall.WallTitle {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String enforce_titleExpr;

    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setEnforce_title(String enforce_title) {
	this.enforce_titleExpr = enforce_title;
    }

    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (enforce_titleExpr != null) {
            super.setEnforce_title(eval.evalBoolean("enforce_title", enforce_titleExpr));
        }
    }

    @Override
    public void release()
    {
        super.release();
        this.enforce_titleExpr = null;
    }


}
