/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/common/plugins/MyPlugIn.java,v 1.1 2008/11/03 10:51:30 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/11/03 10:51:30 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2008 NPower Network Software Ltd.  All rights reserved.
 *
 * This SOURCE CODE FILE, which has been provided by NPower as part
 * of a NPower product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of NPower.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 * ===============================================================================================
 */
package com.npower.common.plugins;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/11/03 10:51:30 $
 */
public class MyPlugIn implements PlugIn {
  
  private String name = null;
  
  private int interval = 1000;

  private Thread thread;

  /**
   * 
   */
  public MyPlugIn() {
    super();
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the interval
   */
  public int getInterval() {
    return interval;
  }

  /**
   * @param interval the interval to set
   */
  public void setInterval(int interval) {
    this.interval = interval;
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy() {
    if (this.thread != null) {
       this.thread.interrupt();
    }
  }

  /* (non-Javadoc)
   * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet, org.apache.struts.config.ModuleConfig)
   */
  public void init(ActionServlet arg0, ModuleConfig arg1) throws ServletException {
    System.err.println("Starting PlugIn#" + this.getName() + ", interval=" + this.getInterval());
    thread = new Thread(new Runnable(){

      public void run() {
        try {
          while (true) {
                System.err.println("PlugIn#" + MyPlugIn.this.getName() + " waked up!");
                Thread.sleep(MyPlugIn.this.getInterval());
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }});
    thread.start();
  }

}
