/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELFont.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELFont extends com.npower.wall.WallFont {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String colorExpr;
    private String sizeExpr;
    private String faceExpr;

    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setColor(String color) {
	this.colorExpr = color;
    }
    @Override
    public void setSize(String size) {
	this.sizeExpr = size;
    }
    @Override
    public void setFace(String face) {
	this.faceExpr = face;
    }


    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (colorExpr != null) {
            super.setColor(eval.evalString("color", colorExpr));
        }
        if (sizeExpr != null) {
            super.setSize(eval.evalString("size", sizeExpr));
        }
        if (faceExpr != null) {
            super.setFace(eval.evalString("face", faceExpr));
        }
    }

    @Override
    public void release() {
        super.release();
        this.colorExpr = null;
        this.sizeExpr = null;
        this.faceExpr = null;
    }


}
