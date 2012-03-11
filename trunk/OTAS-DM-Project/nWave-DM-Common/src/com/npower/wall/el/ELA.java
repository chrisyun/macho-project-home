/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELA.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELA extends com.npower.wall.WallA {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String hrefExpr;
    private String accesskeyExpr;
    private String titleExpr;
    private String opwv_iconExpr;

    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setHref(String href) {
	this.hrefExpr = href;
    }
    @Override
    public void setAccesskey(String accesskey) {
	this.accesskeyExpr = accesskey;
    }
    @Override
    public void setTitle(String title) {
	this.titleExpr = title;
    }

    @Override
    public void setOpwv_icon(String oi) {
	this.opwv_iconExpr = oi;
    }


    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (hrefExpr != null) {
            super.setHref(eval.evalString("href", hrefExpr));
        }
        if (accesskeyExpr != null) {
            super.setAccesskey(eval.evalString("accesskey", accesskeyExpr));
        }
        if (titleExpr != null) {
            super.setTitle(eval.evalString("title", titleExpr));
        }
        if (opwv_iconExpr != null) {
            super.setOpwv_icon(eval.evalString("opwv_icon", opwv_iconExpr));
        }
 
    }

    @Override
    public void release() {
        super.release();
        this.hrefExpr = null;
        this.accesskeyExpr = null;
        this.titleExpr = null;
        this.opwv_iconExpr = null;
    }


}
