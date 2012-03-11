/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/setup/task/TacItem.java,v 1.1 2008/10/31 10:03:04 zhao Exp $
 * $Revision: 1.1 $
 * $Date: 2008/10/31 10:03:04 $
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
package com.npower.dm.setup.task;

/**
 * @author Zhao DongLu
 * @version $Revision: 1.1 $ $Date: 2008/10/31 10:03:04 $
 */
public class TacItem {
  private int lineNumber = 0;
  private String tac = null;
  private String handsetBrand = null;
  private String handsetModel = null;
  private String reportingBody = null;
  private String approvedIn = null;
  private boolean gsm450 = false;
  private boolean gsm850 = false;
  private boolean gsm900 = false;
  private boolean gsm1800 = false;
  private boolean gsm1900 = false;
  private boolean gsmr = false;
  private boolean gsm3 = false;
  private boolean foma = false;
  private boolean iden800 = false;
  private boolean cdma800 = false;
  private boolean cdma1800 = false;
  private boolean cdma1900 = false;
  private boolean tdma = false;
  private boolean satellite = false;
  
  
  /**
   * Default constructor
   */
  public TacItem() {
    super();
  }
  
  /**
   * @param lineNumber
   */
  public TacItem(int lineNumber) {
    super();
    this.lineNumber = lineNumber;
  }

  /**
   * @return the tac
   */
  public String getTac() {
    return tac;
  }
  /**
   * @param tac the tac to set
   */
  public void setTac(String tac) {
    this.tac = tac;
  }
  /**
   * @return the handsetBrand
   */
  public String getHandsetBrand() {
    return handsetBrand;
  }
  /**
   * @param handsetBrand the handsetBrand to set
   */
  public void setHandsetBrand(String handsetBrand) {
    this.handsetBrand = handsetBrand;
  }
  /**
   * @return the handsetModel
   */
  public String getHandsetModel() {
    return handsetModel;
  }
  /**
   * @param handsetModel the handsetModel to set
   */
  public void setHandsetModel(String handsetModel) {
    this.handsetModel = handsetModel;
  }
  /**
   * @return the reportingBody
   */
  public String getReportingBody() {
    return reportingBody;
  }
  /**
   * @param reportingBody the reportingBody to set
   */
  public void setReportingBody(String reportingBody) {
    this.reportingBody = reportingBody;
  }
  /**
   * @return the approvedIn
   */
  public String getApprovedIn() {
    return approvedIn;
  }
  /**
   * @param approvedIn the approvedIn to set
   */
  public void setApprovedIn(String approvedIn) {
    this.approvedIn = approvedIn;
  }
  /**
   * @return the gsm450
   */
  public boolean isGsm450() {
    return gsm450;
  }
  /**
   * @param gsm450 the gsm450 to set
   */
  public void setGsm450(boolean gsm450) {
    this.gsm450 = gsm450;
  }
  /**
   * @return the gsm850
   */
  public boolean isGsm850() {
    return gsm850;
  }
  /**
   * @param gsm850 the gsm850 to set
   */
  public void setGsm850(boolean gsm850) {
    this.gsm850 = gsm850;
  }
  /**
   * @return the gsm900
   */
  public boolean isGsm900() {
    return gsm900;
  }
  /**
   * @param gsm900 the gsm900 to set
   */
  public void setGsm900(boolean gsm900) {
    this.gsm900 = gsm900;
  }
  /**
   * @return the gsm1800
   */
  public boolean isGsm1800() {
    return gsm1800;
  }
  /**
   * @param gsm1800 the gsm1800 to set
   */
  public void setGsm1800(boolean gsm1800) {
    this.gsm1800 = gsm1800;
  }
  /**
   * @return the gsm1900
   */
  public boolean isGsm1900() {
    return gsm1900;
  }
  /**
   * @param gsm1900 the gsm1900 to set
   */
  public void setGsm1900(boolean gsm1900) {
    this.gsm1900 = gsm1900;
  }
  /**
   * @return the gsmr
   */
  public boolean isGsmr() {
    return gsmr;
  }
  /**
   * @param gsmr the gsmr to set
   */
  public void setGsmr(boolean gsmr) {
    this.gsmr = gsmr;
  }
  /**
   * @return the gsm3
   */
  public boolean isGsm3() {
    return gsm3;
  }
  /**
   * @param gsm3 the gsm3 to set
   */
  public void setGsm3(boolean gsm3) {
    this.gsm3 = gsm3;
  }
  /**
   * @return the foma
   */
  public boolean isFoma() {
    return foma;
  }
  /**
   * @param foma the foma to set
   */
  public void setFoma(boolean foma) {
    this.foma = foma;
  }
  /**
   * @return the iden800
   */
  public boolean isIden800() {
    return iden800;
  }
  /**
   * @param iden800 the iden800 to set
   */
  public void setIden800(boolean iden800) {
    this.iden800 = iden800;
  }
  /**
   * @return the cdma800
   */
  public boolean isCdma800() {
    return cdma800;
  }
  /**
   * @param cdma800 the cdma800 to set
   */
  public void setCdma800(boolean cdma800) {
    this.cdma800 = cdma800;
  }
  /**
   * @return the cdma1800
   */
  public boolean isCdma1800() {
    return cdma1800;
  }
  /**
   * @param cdma1800 the cdma1800 to set
   */
  public void setCdma1800(boolean cdma1800) {
    this.cdma1800 = cdma1800;
  }
  /**
   * @return the cdma1900
   */
  public boolean isCdma1900() {
    return cdma1900;
  }
  /**
   * @param cdma1900 the cdma1900 to set
   */
  public void setCdma1900(boolean cdma1900) {
    this.cdma1900 = cdma1900;
  }
  /**
   * @return the tdma
   */
  public boolean isTdma() {
    return tdma;
  }
  /**
   * @param tdma the tdma to set
   */
  public void setTdma(boolean tdma) {
    this.tdma = tdma;
  }
  /**
   * @return the satellite
   */
  public boolean isSatellite() {
    return satellite;
  }
  /**
   * @param satellite the satellite to set
   */
  public void setSatellite(boolean satellite) {
    this.satellite = satellite;
  }

  /**
   * @return the lineNumber
   */
  public int getLineNumber() {
    return lineNumber;
  }

  /**
   * @param lineNumber the lineNumber to set
   */
  public void setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
  }
}
