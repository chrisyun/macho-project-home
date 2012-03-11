/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELCool_menu.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELCool_menu extends com.npower.wall.WallCool_menu {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String colnumExpr;
    private String tabularizeExpr;

    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setColnum(String colnum) {
	this.colnumExpr = colnum;
    }
    @Override
    public void setTabularize(String tblrz) {
	this.tabularizeExpr = tblrz;
    }

    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (colnumExpr != null) {
            super.setColnum(eval.evalString("colnum", colnumExpr));
        }
        if (tabularizeExpr != null) {
            super.setTabularize(eval.evalBoolean("tabularize", tabularizeExpr));
        }
    }

    @Override
    public void release()
    {
        super.release();
        this.colnumExpr = null;
	this.tabularizeExpr = null;
    }


}
