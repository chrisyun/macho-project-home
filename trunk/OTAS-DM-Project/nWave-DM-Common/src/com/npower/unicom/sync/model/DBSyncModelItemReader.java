/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/model/DBSyncModelItemReader.java,v 1.7 2008/11/20 09:17:03 zhao Exp $
 * $Revision: 1.7 $
 * $Date: 2008/11/20 09:17:03 $
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

package com.npower.unicom.sync.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.hibernate.entity.ModelEntity;
import com.npower.dm.management.SearchBean;
import com.npower.unicom.sync.DBSyncItemReader;
import com.npower.unicom.sync.SyncAction;
import com.npower.unicom.sync.SyncItem;

/**
 * @author Huang ChunPing
 * @version $Revision: 1.7 $ ${date}7:08:36 PM$
 * com.npower.unicom.sync.model
 * nWave-DM-Common
 * DBSyncModelItemReader.java
 */
public class DBSyncModelItemReader extends DBSyncItemReader<Model> {

  private static Log log = LogFactory.getLog(DBSyncModelItemReader.class);
  
  /**
   * @param props
   */
  public DBSyncModelItemReader(Properties props) {
    super(props);
  }

  /**
   * @param properties
   * @param fromTime
   * @param toTime
   */
  public DBSyncModelItemReader(Properties properties, Date fromTime, Date toTime) {
    super(properties, fromTime, toTime);
  }


  @Override
  protected SyncItem convert(SyncAction action, Model model) {
    // ½«Model×ª»»ÎªSyncItem
    SyncModelItem item = new SyncModelItem(Long.toString(model.getID()), action);
    item.setCompanyName(model.getManufacturer().getName());
    item.setTerminalModel(model.getManufacturerModelId());
    String os = model.getCharacterValue("general", "operating.system");
    item.setOs(os);
    String osVersion = model.getCharacterValue("general", "developer.platform");
    item.setOsVersion(osVersion);
    /*
    item.setVersion(null);
    item.setUaProf(null);
    item.setGprs(null);
    item.setWcdma(null);
    item.setHsdpa(null);
    item.setHsupa(null);
    item.setWap(null);
    item.setWapVersion(null);
    item.setBrowserVendor(null);
    item.setBrowserVersion(null);
    item.setInternetBrowser(null);
    item.setInternetBrowserVersion(null);
    item.setHttpDownload(null);
    item.setOmaDownload(null);
    item.setDmClient(null);
    item.setDmVersion(null);
    */
    String releaseData = model.getCharacterValue("general", "announced");
    item.setReleaseData(releaseData);
    /*
    item.setIsTouchScreen(null);
    item.setIsColorScreen(null);
    item.setScreenHeight(null);
    item.setScreenWidth(null);
    item.setColums(null);
    item.setRows(null);
    item.setGreyscale(null);
    item.setGif(null);
    item.setJpg(null);
    item.setBmp(null);
    item.setGif_animated(null);
    item.setRingtoneDownload(null);
    item.setRingtone_wav(null);
    item.setRingtone_mp3(null);
    item.setVideo(null);
    item.setVideoFamat(null);
    item.setBlueTooth(null);
    item.setBlueToothVersion(null);
    item.setIrDa(null);
    item.setUsb(null);
    item.setUsbVersion(null);
    item.setFm(null);
    item.setSpeaker(null);
    item.setCamera(null);
    item.setCameraResolutionpixels(null);
    item.setCameraNum(null);
    item.setCameraFlash(null);
    item.setExtMemory(null);
    item.setExtMemoryType(null);
    item.setExtMemoryMaxSize(null);
    item.setOmadrm(null);
    item.setDrmVersion(null);
    item.setStreaming(null);
    item.setStreamingH263(null);
    item.setStreamingH264(null);
    item.setStreamingACC(null);
    item.setStreamingMP4(null);
    item.setStreaming3gpp(null);
    item.setStreamingWMV(null);
    item.setStreamingMOV(null);
    item.setSteamingRM8(null);
    item.setStreamingRM10(null);
    item.setJ2me(null);
    item.setJ2meDownload(null);
    item.setJarDLMaxSize(null);
    item.setJavaCLDCVersion(null);
    item.setJavaMIDPVersion(null);
    item.setMms(null);
    item.setMms3gpp(null);
    item.setMmsWBXML(null);
    item.setMmspng(null);
    item.setMmsWML(null);
    item.setMmsVideo(null);
    item.setMmsMaxSize(null);
    item.setMmsForward(null);
    item.setFlash(null);
    item.setVt(null);
    item.setVtH263(null);
    item.setVtH264(null);
    item.setVtMP4(null);
    item.setImap4(null);
    item.setPop3(null);
    item.setEmailClient(null);
    item.setEmailClientName(null);
    item.setEmailClientVersion(null);
    item.setAgps(null);
    item.setBarRecog(null);
    item.setSip(null);
    item.setPim(null);
    item.setSycML(null);
    item.setVideoSharing(null);
    */
    return item;
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.DBSyncItemReader#getIterator4Add()
   */
  @Override
  protected Iterator<Model> getIterator4Add() throws DMException {
    List<Model> models = new ArrayList<Model>();
    SearchBean bean = this.getFactory().createSearchBean();
    Criteria criteria = bean.newCriteriaInstance(ModelEntity.class);
    if (null != this.getFromTime()) {
       criteria.add(Expression.ge("createdTime", this.getFromTime()));
    }
    if (null != this.getToTime()) {
       criteria.add(Expression.lt("createdTime", this.getToTime()));
    }
    models = criteria.list();
    log.info("[" + this.getClass().getSimpleName() + "] total items: " + models.size() + " need to add");
    return models.iterator();
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.DBSyncItemReader#getIterator4Delete()
   */
  @Override
  protected Iterator<Model> getIterator4Delete() throws DMException {
    List<Model> models = new ArrayList<Model>();
    log.info("[" + this.getClass().getSimpleName() + "] total items: " + models.size() + " need to delete");
    return models.iterator();
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.DBSyncItemReader#getIterator4Modify()
   */
  @Override
  protected Iterator<Model> getIterator4Modify() throws DMException {
    List<Model> models = new ArrayList<Model>();
    SearchBean bean = this.getFactory().createSearchBean();
    Criteria criteria = bean.newCriteriaInstance(ModelEntity.class);
    if (null != this.getFromTime()) {
      criteria.add(Expression.ge("lastUpdatedTime", this.getFromTime()));
    }
    if (null != this.getToTime()) {
       criteria.add(Expression.lt("lastUpdatedTime", this.getToTime()));
    }
    models = criteria.list();
    log.info("[" + this.getClass().getSimpleName() + "] total items: " + models.size() + " need to modify");
    return models.iterator();
  }

}
