/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/model/FileSyncModelItemReader.java,v 1.5 2008/11/20 09:17:03 zhao Exp $
 * $Revision: 1.5 $
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

import java.io.File;

import org.apache.commons.lang.StringUtils;

import com.npower.unicom.sync.FileSyncItemReader;
import com.npower.unicom.sync.ParseException;
import com.npower.unicom.sync.SyncItem;

/**
 * @author Huang ChunPing
 * @version $Revision: 1.5 $ ${date}3:52:10 PM$
 * com.npower.unicom.sync.model
 * nWave-DM-Common
 * FileSyncModelItemReader.java
 */
public class FileSyncModelItemReader extends FileSyncItemReader {

  /**
   * @param file
   */
  public FileSyncModelItemReader(File file) {
    super(file);
  }

  /* (non-Javadoc)
   * @see com.npower.unicom.sync.FileSyncItemReader#parse(java.lang.String)
   */
  @Override
  protected SyncItem parse(String line) throws ParseException {
    SyncModelItem item = null;
    if (StringUtils.isNotEmpty(line)) {
      item = new SyncModelItem();
      String[] cols = StringUtils.splitPreserveAllTokens(line, '\t');
      if (cols == null || (cols.length != 94 && cols.length != 93)) {
         throw new ParseException("parse error: " + line);
      }
      item.setId(cols[0]);
      item.setCompanyName(cols[2]);
      item.setTerminalModel(cols[3]);
      item.setOs(cols[4]);
      item.setOsVersion(cols[5]);
      item.setVersion(cols[6]);
      item.setUaProf(cols[7]);
      item.setGprs(cols[8]);
      item.setWcdma(cols[9]);
      item.setHsdpa(cols[10]);
      item.setHsupa(cols[11]);
      item.setWap(cols[12]);
      item.setWapVersion(cols[13]);
      item.setBrowserVendor(cols[14]);
      item.setBrowserVersion(cols[15]);
      item.setInternetBrowser(cols[16]);
      item.setInternetBrowserVersion(cols[17]);
      item.setHttpDownload(cols[18]);
      item.setOmaDownload(cols[19]);
      item.setDmClient(cols[20]);
      item.setDmVersion(cols[21]);
      item.setReleaseData(cols[22]);
      item.setIsTouchScreen(cols[23]);
      item.setIsColorScreen(cols[24]);
      item.setScreenHeight(cols[25]);
      item.setScreenWidth(cols[26]);
      item.setColums(cols[27]);
      item.setRows(cols[28]);
      item.setGreyscale(cols[29]);
      item.setGif(cols[30]);
      item.setJpg(cols[31]);
      item.setBmp(cols[32]);
      item.setGif_animated(cols[33]);
      item.setRingtoneDownload(cols[34]);
      item.setRingtone_wav(cols[35]);
      item.setRingtone_mp3(cols[36]);
      item.setVideo(cols[37]);
      item.setVideoFamat(cols[38]);
      item.setBlueTooth(cols[39]);
      item.setBlueToothVersion(cols[40]);
      item.setIrDa(cols[41]);
      item.setUsb(cols[42]);
      item.setUsbVersion(cols[43]);
      item.setFm(cols[44]);
      item.setSpeaker(cols[45]);
      item.setCamera(cols[46]);
      item.setCameraResolutionpixels(cols[47]);
      item.setCameraNum(cols[48]);
      item.setCameraFlash(cols[49]);
      item.setExtMemory(cols[50]);
      item.setExtMemoryType(cols[51]);
      item.setExtMemoryMaxSize(cols[52]);
      item.setOmadrm(cols[53]);
      item.setDrmVersion(cols[54]);
      item.setStreaming(cols[55]);
      item.setStreamingH263(cols[56]);
      item.setStreamingH264(cols[57]);
      item.setStreamingACC(cols[58]);
      item.setStreamingMP4(cols[59]);
      item.setStreaming3gpp(cols[60]);
      item.setStreamingWMV(cols[61]);
      item.setStreamingMOV(cols[62]);
      item.setSteamingRM8(cols[63]);
      item.setStreamingRM10(cols[64]);
      item.setJ2me(cols[65]);
      item.setJ2meDownload(cols[66]);
      item.setJarDLMaxSize(cols[67]);
      item.setJavaCLDCVersion(cols[68]);
      item.setJavaMIDPVersion(cols[69]);
      item.setMms(cols[70]);
      item.setMms3gpp(cols[71]);
      item.setMmsWBXML(cols[72]);
      item.setMmspng(cols[73]);
      item.setMmsWML(cols[74]);
      item.setMmsVideo(cols[75]);
      item.setMmsMaxSize(cols[76]);
      item.setMmsForward(cols[77]);
      item.setFlash(cols[78]);
      item.setVt(cols[79]);
      item.setVtH263(cols[80]);
      item.setVtH264(cols[81]);
      item.setVtMP4(cols[82]);
      item.setImap4(cols[83]);
      item.setPop3(cols[84]);
      item.setEmailClient(cols[85]);
      item.setEmailClientName(cols[86]);
      item.setEmailClientVersion(cols[87]);
      item.setAgps(cols[88]);
      item.setBarRecog(cols[89]);
      item.setSip(cols[90]);
      item.setPim(cols[91]);
      item.setSycML(cols[92]);
      if (cols.length == 94) {
         item.setVideoSharing(cols[93]);
      }
    }
    return item;
  }
  
}
