/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELMenu.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELMenu extends com.npower.wall.WallMenu {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String colorizeExpr;
    private String autonumberExpr;

    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setColorize(String colorize) {
	this.colorizeExpr = colorize;
    }
    //attribute Setters
    @Override
    public void setAutonumber(String an) {
	this.autonumberExpr = an;
    }

    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (colorizeExpr != null) {
            super.setColorize(eval.evalBoolean("colorize", colorizeExpr));
        }

        if (autonumberExpr != null) {
            super.setAutonumber(eval.evalBoolean("autonumber", autonumberExpr));
        }
    }

    @Override
    public void release()
    {
        super.release();
        this.colorizeExpr = null;
        this.autonumberExpr = null;
    }


}
