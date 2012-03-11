/*
 ***************************************************************************
 * Copyright 2004,2005 Luca Passani                                        *
 * Distributed under the Mozilla Public License                            *
 *   http://www.mozilla.org/NPL/MPL-1.1.txt                                *
 ***************************************************************************
 *   $Author: zhao $
 *   $Header: /home/master/nWave-DM-Common/src/com/npower/wall/el/ELMarquee.java,v 1.1 2008/05/12 09:38:17 zhao Exp $
 */

package com.npower.wall.el;

import javax.servlet.jsp.JspException;


public class ELMarquee extends com.npower.wall.WallMarquee {

    /**
   * 
   */
  private static final long serialVersionUID = 1L;
    private String directionExpr;
    private String behaviorExpr;
    private String loopExpr;
    private String bgcolorExpr;

    @Override
    public int doStartTag() throws JspException {

        evaluateExpressions();	
	return super.doStartTag();
    }

    //attribute Setters
    @Override
    public void setDirection(String direction) {
	this.directionExpr = direction;
    }
    @Override
    public void setBehavior(String behavior) {
	this.behaviorExpr = behavior;
    }
    @Override
    public void setLoop(String loop) {
	this.loopExpr = loop;
    }

    @Override
    public void setBgcolor(String oi) {
	this.bgcolorExpr = oi;
    }


    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (directionExpr != null) {
            super.setDirection(eval.evalString("direction", directionExpr));
        }
        if (behaviorExpr != null) {
            super.setBehavior(eval.evalString("behavior", behaviorExpr));
        }
        if (loopExpr != null) {
            super.setLoop(eval.evalString("loop", loopExpr));
        }
        if (bgcolorExpr != null) {
            super.setBgcolor(eval.evalString("bgcolor", bgcolorExpr));
        }
 
    }

    @Override
    public void release() {
        super.release();
        this.directionExpr = null;
        this.behaviorExpr = null;
        this.loopExpr = null;
        this.bgcolorExpr = null;
    }


}
