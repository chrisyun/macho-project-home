/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/unicom/sync/model/SyncModelItem.java,v 1.4 2008/11/19 04:16:30 zhao Exp $
 * $Revision: 1.4 $
 * $Date: 2008/11/19 04:16:30 $
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

import com.npower.unicom.sync.SyncAction;
import com.npower.unicom.sync.SyncItem;

/**
 * @author Huang ChunPing
 * @version $Revision: 1.4 $ ${date}2:23:51 PM$ com.npower.unicom.sync.model
 *          nWave-DM-Common SyncModelItem.java
 */
public class SyncModelItem extends SyncItem {

  /**
   * 终端厂商名称
   */
  private String companyName;

  /**
   * 终端型号
   */
  private String terminalModel;

  /**
   * 操作系统名称
   */
  private String os;

  /**
   * 操作系统版本号
   */
  private String osVersion;

  /**
   * 终端固件版本号
   */
  private String version;

  /**
   * 
   */
  private String uaProf;

  /**
   * 是否支持GPRS
   */
  private String gprs = "1";

  /**
   * 是否支持WCDMA
   */
  private String wcdma = "1";

  /**
   * 是否支持HSDPA
   */
  private String hsdpa = "1";

  /**
   * 是否支持HSUPA
   */
  private String hsupa = "1";

  /**
   * 是否支持WAP
   */
  private String wap = "1";

  /**
   * WAP版本号
   */
  private String wapVersion;

  /**
   * 浏览器供应商名称
   */
  private String browserVendor;

  /**
   * 浏览器版本号
   */
  private String browserVersion;

  /**
   * 是否支持InternetBrowser
   */
  private String internetBrowser = "1";

  /**
   * InternetBrowser版本号
   */
  private String internetBrowserVersion;

  /**
   * 是否支持HTTP下载
   */
  private String httpDownload = "1";

  /**
   * 是否支持OMADownload
   */
  private String omaDownload = "1";

  /**
   * DM客户端名称
   */
  private String dmClient;

  /**
   * DM客户端版本号
   */
  private String dmVersion;

  /**
   * 产品发布时间
   */
  private String releaseData;

  /**
   * 是否为触摸屏
   */
  private String isTouchScreen = "1";

  /**
   * 是否为彩屏
   */
  private String isColorScreen = "1";

  /**
   * 分辨率高度
   */
  private String screenHeight;

  /**
   * 分辨率宽度
   */
  private String screenWidth;

  /**
   * 可显示文字列
   */
  private String colums;

  /**
   * 可显示文字行
   */
  private String rows;

  /**
   * 支持灰度
   */
  private String greyscale;

  /**
   * 是否支持gif
   */
  private String gif = "1";

  /**
   * 是否支持jpg
   */
  private String jpg = "1";

  /**
   * 是否支持bmp
   */
  private String bmp = "1";

  /**
   * 是否支持动态gif
   */
  private String gif_animated = "1";

  /**
   * 是否支持铃声下载
   */
  private String ringtoneDownload = "1";

  /**
   * 是否支持wav铃音
   */
  private String ringtone_wav = "1";

  /**
   * 是否支持mp3铃音
   */
  private String ringtone_mp3 = "1";

  /**
   * 是否支持视频
   */
  private String video = "1";

  /**
   * 视频格式
   */
  private String videoFamat;

  /**
   * 是否支持蓝牙
   */
  private String blueTooth = "1";

  /**
   * 蓝牙版本号
   */
  private String blueToothVersion;

  /**
   * 是否支持红外
   */
  private String irDa = "1";

  /**
   * 是否支持USB
   */
  private String usb = "1";

  /**
   * USB版本
   */
  private String usbVersion;

  /**
   * 是否支持FM收音机
   */
  private String fm = "1";

  /**
   * 是否支持免提扬声器
   */
  private String speaker = "1";

  /**
   * 是否支持摄像头
   */
  private String camera = "1";

  /**
   * 摄像头分辨率
   */
  private String cameraResolutionpixels;

  /**
   * 摄像头数量
   */
  private String cameraNum;

  /**
   * 摄像头闪光灯
   */
  private String cameraFlash;

  /**
   * 是否支持外置存储卡
   */
  private String extMemory = "1";

  /**
   * 外置存储卡类型
   */
  private String extMemoryType;

  /**
   * 外置存储卡最大容量
   */
  private String extMemoryMaxSize;

  /**
   * 是否支持OMA DRM
   */
  private String omadrm = "1";

  /**
   * OMA DRM版本号
   */
  private String drmVersion;

  /**
   * 是否支持流媒体
   */
  private String streaming = "1";

  /**
   * 是否支持H.263
   */
  private String streamingH263 = "1";

  /**
   * 是否支持H.264
   */
  private String streamingH264 = "1";

  /**
   * 是否支持ACC音频编码
   */
  private String streamingACC = "1";

  /**
   * 是否支持MPEG4
   */
  private String streamingMP4 = "1";

  /**
   * 是否支持3GPP
   */
  private String streaming3gpp = "1";

  /**
   * 是否支持WMV
   */
  private String streamingWMV = "1";

  /**
   * 是否支持MOV
   */
  private String streamingMOV = "1";

  /**
   * 是否支持RM8.0
   */
  private String steamingRM8 = "1";

  /**
   * 是否支持RM10.0
   */
  private String streamingRM10 = "1";

  /**
   * 是否支持J2ME
   */
  private String j2me = "1";

  /**
   * 是否支持J2ME下载
   */
  private String j2meDownload = "1";

  /**
   * Jar文件下载大小
   */
  private String jarDLMaxSize;

  /**
   * JAVA CLDC的版本
   */
  private String javaCLDCVersion;

  /**
   * JAVA MIDP的版本
   */
  private String javaMIDPVersion;

  /**
   * 是否支持MMS
   */
  private String mms = "1";

  /**
   * 是否支持3GPPMMS
   */
  private String mms3gpp = "1";

  /**
   * 是否支持WBXML MMS
   */
  private String mmsWBXML = "1";

  /**
   * 是否支持png MMS
   */
  private String mmspng = "1";

  /**
   * 是否支持WML MMS
   */
  private String mmsWML = "1";

  /**
   * 是否支持视频MMS
   */
  private String mmsVideo = "1";

  /**
   * MMS最大字节数
   */
  private String mmsMaxSize;

  /**
   * 是否支持MMS转发
   */
  private String mmsForward = "1";

  /**
   * 是否支持Flash
   */
  private String flash = "1";

  /**
   * 是否支持VT
   */
  private String vt = "1";

  /**
   * 是否支持H.263编码的VT
   */
  private String vtH263 = "1";

  /**
   * 是否支持H.264编码的VT
   */
  private String vtH264 = "1";

  /**
   * 是否支持MPEG4编码的VT
   */
  private String vtMP4 = "1";

  /**
   * 是否支持IMAP4
   */
  private String imap4 = "1";

  /**
   * 是否支持POP3
   */
  private String pop3 = "1";

  /**
   * 是否有EmialClient
   */
  private String emailClient = "1";

  /**
   * Email客户端名称
   */
  private String emailClientName = "1";

  /**
   * Email客户端版本号
   */
  private String emailClientVersion = "1";

  /**
   * 是否支持定位
   */
  private String agps = "1";

  /**
   * 是否支持二维码
   */
  private String barRecog = "1";

  /**
   * 是否支持SIP
   */
  private String sip = "1";

  /**
   * 是否支持PIM
   */
  private String pim = "1";

  /**
   * SycML版本号
   */
  private String sycML;

  /**
   * 是否支持VidewSharing
   */
  private String videoSharing = "1";

  public SyncModelItem() {
    super();
  }

  /**
   * @param id
   * @param action
   */
  public SyncModelItem(String id, SyncAction action) {
    super(id, action);
  }

  /**
   * @return the companyName
   */
  public String getCompanyName() {
    return companyName;
  }

  /**
   * @param companyName the companyName to set
   */
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  /**
   * @return the terminalModel
   */
  public String getTerminalModel() {
    return terminalModel;
  }

  /**
   * @param terminalModel the terminalModel to set
   */
  public void setTerminalModel(String terminalModel) {
    this.terminalModel = terminalModel;
  }

  /**
   * @return the os
   */
  public String getOs() {
    return os;
  }

  /**
   * @param os the os to set
   */
  public void setOs(String os) {
    this.os = os;
  }

  /**
   * @return the osVersion
   */
  public String getOsVersion() {
    return osVersion;
  }

  /**
   * @param osVersion the osVersion to set
   */
  public void setOsVersion(String osVersion) {
    this.osVersion = osVersion;
  }

  /**
   * @return the version
   */
  public String getVersion() {
    return version;
  }

  /**
   * @param version the version to set
   */
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * @return the uaProf
   */
  public String getUaProf() {
    return uaProf;
  }

  /**
   * @param uaProf the uaProf to set
   */
  public void setUaProf(String uaProf) {
    this.uaProf = uaProf;
  }

  /**
   * @return the gprs
   */
  public String getGprs() {
    return gprs;
  }

  /**
   * @param gprs the gprs to set
   */
  public void setGprs(String gprs) {
    this.gprs = gprs;
  }

  /**
   * @return the wcdma
   */
  public String getWcdma() {
    return wcdma;
  }

  /**
   * @param wcdma the wcdma to set
   */
  public void setWcdma(String wcdma) {
    this.wcdma = wcdma;
  }

  /**
   * @return the hsdpa
   */
  public String getHsdpa() {
    return hsdpa;
  }

  /**
   * @param hsdpa the hsdpa to set
   */
  public void setHsdpa(String hsdpa) {
    this.hsdpa = hsdpa;
  }

  /**
   * @return the hsupa
   */
  public String getHsupa() {
    return hsupa;
  }

  /**
   * @param hsupa the hsupa to set
   */
  public void setHsupa(String hsupa) {
    this.hsupa = hsupa;
  }

  /**
   * @return the wap
   */
  public String getWap() {
    return wap;
  }

  /**
   * @param wap the wap to set
   */
  public void setWap(String wap) {
    this.wap = wap;
  }

  /**
   * @return the wapVersion
   */
  public String getWapVersion() {
    return wapVersion;
  }

  /**
   * @param wapVersion the wapVersion to set
   */
  public void setWapVersion(String wapVersion) {
    this.wapVersion = wapVersion;
  }

  /**
   * @return the browserVendor
   */
  public String getBrowserVendor() {
    return browserVendor;
  }

  /**
   * @param browserVendor the browserVendor to set
   */
  public void setBrowserVendor(String browserVendor) {
    this.browserVendor = browserVendor;
  }

  /**
   * @return the browserVersion
   */
  public String getBrowserVersion() {
    return browserVersion;
  }

  /**
   * @param browserVersion the browserVersion to set
   */
  public void setBrowserVersion(String browserVersion) {
    this.browserVersion = browserVersion;
  }

  /**
   * @return the internetBrowser
   */
  public String getInternetBrowser() {
    return internetBrowser;
  }

  /**
   * @param internetBrowser the internetBrowser to set
   */
  public void setInternetBrowser(String internetBrowser) {
    this.internetBrowser = internetBrowser;
  }

  /**
   * @return the internetBrowserVersion
   */
  public String getInternetBrowserVersion() {
    return internetBrowserVersion;
  }

  /**
   * @param internetBrowserVersion the internetBrowserVersion to set
   */
  public void setInternetBrowserVersion(String internetBrowserVersion) {
    this.internetBrowserVersion = internetBrowserVersion;
  }

  /**
   * @return the httpDownload
   */
  public String getHttpDownload() {
    return httpDownload;
  }

  /**
   * @param httpDownload the httpDownload to set
   */
  public void setHttpDownload(String httpDownload) {
    this.httpDownload = httpDownload;
  }

  /**
   * @return the omaDownload
   */
  public String getOmaDownload() {
    return omaDownload;
  }

  /**
   * @param omaDownload the omaDownload to set
   */
  public void setOmaDownload(String omaDownload) {
    this.omaDownload = omaDownload;
  }

  /**
   * @return the dmClient
   */
  public String getDmClient() {
    return dmClient;
  }

  /**
   * @param dmClient the dmClient to set
   */
  public void setDmClient(String dmClient) {
    this.dmClient = dmClient;
  }

  /**
   * @return the dmVersion
   */
  public String getDmVersion() {
    return dmVersion;
  }

  /**
   * @param dmVersion the dmVersion to set
   */
  public void setDmVersion(String dmVersion) {
    this.dmVersion = dmVersion;
  }

  /**
   * @return the releaseData
   */
  public String getReleaseData() {
    return releaseData;
  }

  /**
   * @param releaseData the releaseData to set
   */
  public void setReleaseData(String releaseData) {
    this.releaseData = releaseData;
  }

  /**
   * @return the isTouchScreen
   */
  public String getIsTouchScreen() {
    return isTouchScreen;
  }

  /**
   * @param isTouchScreen the isTouchScreen to set
   */
  public void setIsTouchScreen(String isTouchScreen) {
    this.isTouchScreen = isTouchScreen;
  }

  /**
   * @return the isColorScreen
   */
  public String getIsColorScreen() {
    return isColorScreen;
  }

  /**
   * @param isColorScreen the isColorScreen to set
   */
  public void setIsColorScreen(String isColorScreen) {
    this.isColorScreen = isColorScreen;
  }

  /**
   * @return the screenHeight
   */
  public String getScreenHeight() {
    return screenHeight;
  }

  /**
   * @param screenHeight the screenHeight to set
   */
  public void setScreenHeight(String screenHeight) {
    this.screenHeight = screenHeight;
  }

  /**
   * @return the screenWidth
   */
  public String getScreenWidth() {
    return screenWidth;
  }

  /**
   * @param screenWidth the screenWidth to set
   */
  public void setScreenWidth(String screenWidth) {
    this.screenWidth = screenWidth;
  }

  /**
   * @return the colums
   */
  public String getColums() {
    return colums;
  }

  /**
   * @param colums the colums to set
   */
  public void setColums(String colums) {
    this.colums = colums;
  }

  /**
   * @return the rows
   */
  public String getRows() {
    return rows;
  }

  /**
   * @param rows the rows to set
   */
  public void setRows(String rows) {
    this.rows = rows;
  }

  /**
   * @return the greyscale
   */
  public String getGreyscale() {
    return greyscale;
  }

  /**
   * @param greyscale the greyscale to set
   */
  public void setGreyscale(String greyscale) {
    this.greyscale = greyscale;
  }

  /**
   * @return the gif
   */
  public String getGif() {
    return gif;
  }

  /**
   * @param gif the gif to set
   */
  public void setGif(String gif) {
    this.gif = gif;
  }

  /**
   * @return the jpg
   */
  public String getJpg() {
    return jpg;
  }

  /**
   * @param jpg the jpg to set
   */
  public void setJpg(String jpg) {
    this.jpg = jpg;
  }

  /**
   * @return the bmp
   */
  public String getBmp() {
    return bmp;
  }

  /**
   * @param bmp the bmp to set
   */
  public void setBmp(String bmp) {
    this.bmp = bmp;
  }

  /**
   * @return the gif_animated
   */
  public String getGif_animated() {
    return gif_animated;
  }

  /**
   * @param gif_animated the gif_animated to set
   */
  public void setGif_animated(String gif_animated) {
    this.gif_animated = gif_animated;
  }

  /**
   * @return the ringtoneDownload
   */
  public String getRingtoneDownload() {
    return ringtoneDownload;
  }

  /**
   * @param ringtoneDownload the ringtoneDownload to set
   */
  public void setRingtoneDownload(String ringtoneDownload) {
    this.ringtoneDownload = ringtoneDownload;
  }

  /**
   * @return the ringtone_wav
   */
  public String getRingtone_wav() {
    return ringtone_wav;
  }

  /**
   * @param ringtone_wav the ringtone_wav to set
   */
  public void setRingtone_wav(String ringtone_wav) {
    this.ringtone_wav = ringtone_wav;
  }

  /**
   * @return the ringtone_mp3
   */
  public String getRingtone_mp3() {
    return ringtone_mp3;
  }

  /**
   * @param ringtone_mp3 the ringtone_mp3 to set
   */
  public void setRingtone_mp3(String ringtone_mp3) {
    this.ringtone_mp3 = ringtone_mp3;
  }

  /**
   * @return the video
   */
  public String getVideo() {
    return video;
  }

  /**
   * @param video the video to set
   */
  public void setVideo(String video) {
    this.video = video;
  }

  /**
   * @return the videoFamat
   */
  public String getVideoFamat() {
    return videoFamat;
  }

  /**
   * @param videoFamat the videoFamat to set
   */
  public void setVideoFamat(String videoFamat) {
    this.videoFamat = videoFamat;
  }

  /**
   * @return the blueTooth
   */
  public String getBlueTooth() {
    return blueTooth;
  }

  /**
   * @param blueTooth the blueTooth to set
   */
  public void setBlueTooth(String blueTooth) {
    this.blueTooth = blueTooth;
  }

  /**
   * @return the blueToothVersion
   */
  public String getBlueToothVersion() {
    return blueToothVersion;
  }

  /**
   * @param blueToothVersion the blueToothVersion to set
   */
  public void setBlueToothVersion(String blueToothVersion) {
    this.blueToothVersion = blueToothVersion;
  }

  /**
   * @return the irDa
   */
  public String getIrDa() {
    return irDa;
  }

  /**
   * @param irDa the irDa to set
   */
  public void setIrDa(String irDa) {
    this.irDa = irDa;
  }

  /**
   * @return the usb
   */
  public String getUsb() {
    return usb;
  }

  /**
   * @param usb the usb to set
   */
  public void setUsb(String usb) {
    this.usb = usb;
  }

  /**
   * @return the usbVersion
   */
  public String getUsbVersion() {
    return usbVersion;
  }

  /**
   * @param usbVersion the usbVersion to set
   */
  public void setUsbVersion(String usbVersion) {
    this.usbVersion = usbVersion;
  }

  /**
   * @return the fm
   */
  public String getFm() {
    return fm;
  }

  /**
   * @param fm the fm to set
   */
  public void setFm(String fm) {
    this.fm = fm;
  }

  /**
   * @return the speaker
   */
  public String getSpeaker() {
    return speaker;
  }

  /**
   * @param speaker the speaker to set
   */
  public void setSpeaker(String speaker) {
    this.speaker = speaker;
  }

  /**
   * @return the camera
   */
  public String getCamera() {
    return camera;
  }

  /**
   * @param camera the camera to set
   */
  public void setCamera(String camera) {
    this.camera = camera;
  }

  /**
   * @return the cameraResolutionpixels
   */
  public String getCameraResolutionpixels() {
    return cameraResolutionpixels;
  }

  /**
   * @param cameraResolutionpixels the cameraResolutionpixels to set
   */
  public void setCameraResolutionpixels(String cameraResolutionpixels) {
    this.cameraResolutionpixels = cameraResolutionpixels;
  }

  /**
   * @return the cameraNum
   */
  public String getCameraNum() {
    return cameraNum;
  }

  /**
   * @param cameraNum the cameraNum to set
   */
  public void setCameraNum(String cameraNum) {
    this.cameraNum = cameraNum;
  }

  /**
   * @return the cameraFlash
   */
  public String getCameraFlash() {
    return cameraFlash;
  }

  /**
   * @param cameraFlash the cameraFlash to set
   */
  public void setCameraFlash(String cameraFlash) {
    this.cameraFlash = cameraFlash;
  }

  /**
   * @return the extMemory
   */
  public String getExtMemory() {
    return extMemory;
  }

  /**
   * @param extMemory the extMemory to set
   */
  public void setExtMemory(String extMemory) {
    this.extMemory = extMemory;
  }

  /**
   * @return the extMemoryType
   */
  public String getExtMemoryType() {
    return extMemoryType;
  }

  /**
   * @param extMemoryType the extMemoryType to set
   */
  public void setExtMemoryType(String extMemoryType) {
    this.extMemoryType = extMemoryType;
  }

  /**
   * @return the extMemoryMaxSi
   */
  public String getExtMemoryMaxSize() {
    return extMemoryMaxSize;
  }

  /**
   * @param extMemoryMaxSi the extMemoryMaxSi to set
   */
  public void setExtMemoryMaxSize(String extMemoryMaxSize) {
    this.extMemoryMaxSize = extMemoryMaxSize;
  }

  /**
   * @return the omadrm
   */
  public String getOmadrm() {
    return omadrm;
  }

  /**
   * @param omadrm the omadrm to set
   */
  public void setOmadrm(String omadrm) {
    this.omadrm = omadrm;
  }

  /**
   * @return the drmVersion
   */
  public String getDrmVersion() {
    return drmVersion;
  }

  /**
   * @param drmVersion the drmVersion to set
   */
  public void setDrmVersion(String drmVersion) {
    this.drmVersion = drmVersion;
  }

  /**
   * @return the streaming
   */
  public String getStreaming() {
    return streaming;
  }

  /**
   * @param streaming the streaming to set
   */
  public void setStreaming(String streaming) {
    this.streaming = streaming;
  }

  /**
   * @return the streamingH263
   */
  public String getStreamingH263() {
    return streamingH263;
  }

  /**
   * @param streamingH263 the streamingH263 to set
   */
  public void setStreamingH263(String streamingH263) {
    this.streamingH263 = streamingH263;
  }

  /**
   * @return the streamingH264
   */
  public String getStreamingH264() {
    return streamingH264;
  }

  /**
   * @param streamingH264 the streamingH264 to set
   */
  public void setStreamingH264(String streamingH264) {
    this.streamingH264 = streamingH264;
  }

  /**
   * @return the streamingACC
   */
  public String getStreamingACC() {
    return streamingACC;
  }

  /**
   * @param streamingACC the streamingACC to set
   */
  public void setStreamingACC(String streamingACC) {
    this.streamingACC = streamingACC;
  }

  /**
   * @return the streamingMP4
   */
  public String getStreamingMP4() {
    return streamingMP4;
  }

  /**
   * @param streamingMP4 the streamingMP4 to set
   */
  public void setStreamingMP4(String streamingMP4) {
    this.streamingMP4 = streamingMP4;
  }

  /**
   * @return the streaming3gpp
   */
  public String getStreaming3gpp() {
    return streaming3gpp;
  }

  /**
   * @param streaming3gpp the streaming3gpp to set
   */
  public void setStreaming3gpp(String streaming3gpp) {
    this.streaming3gpp = streaming3gpp;
  }

  /**
   * @return the streamingWMV
   */
  public String getStreamingWMV() {
    return streamingWMV;
  }

  /**
   * @param streamingWMV the streamingWMV to set
   */
  public void setStreamingWMV(String streamingWMV) {
    this.streamingWMV = streamingWMV;
  }

  /**
   * @return the streamingMOV
   */
  public String getStreamingMOV() {
    return streamingMOV;
  }

  /**
   * @param streamingMOV the streamingMOV to set
   */
  public void setStreamingMOV(String streamingMOV) {
    this.streamingMOV = streamingMOV;
  }

  /**
   * @return the steamingRM8
   */
  public String getSteamingRM8() {
    return steamingRM8;
  }

  /**
   * @param steamingRM8 the steamingRM8 to set
   */
  public void setSteamingRM8(String steamingRM8) {
    this.steamingRM8 = steamingRM8;
  }

  /**
   * @return the streamingRM10
   */
  public String getStreamingRM10() {
    return streamingRM10;
  }

  /**
   * @param streamingRM10 the streamingRM10 to set
   */
  public void setStreamingRM10(String streamingRM10) {
    this.streamingRM10 = streamingRM10;
  }

  /**
   * @return the j2me
   */
  public String getJ2me() {
    return j2me;
  }

  /**
   * @param j2me the j2me to set
   */
  public void setJ2me(String j2me) {
    this.j2me = j2me;
  }

  /**
   * @return the j2meDownload
   */
  public String getJ2meDownload() {
    return j2meDownload;
  }

  /**
   * @param download the j2meDownload to set
   */
  public void setJ2meDownload(String download) {
    j2meDownload = download;
  }

  /**
   * @return the jarDLMaxSize
   */
  public String getJarDLMaxSize() {
    return jarDLMaxSize;
  }

  /**
   * @param jarDLMaxSize the jarDLMaxSize to set
   */
  public void setJarDLMaxSize(String jarDLMaxSize) {
    this.jarDLMaxSize = jarDLMaxSize;
  }

  /**
   * @return the javaCLDCVersion
   */
  public String getJavaCLDCVersion() {
    return javaCLDCVersion;
  }

  /**
   * @param javaCLDCVersion the javaCLDCVersion to set
   */
  public void setJavaCLDCVersion(String javaCLDCVersion) {
    this.javaCLDCVersion = javaCLDCVersion;
  }

  /**
   * @return the javaMIDPVersion
   */
  public String getJavaMIDPVersion() {
    return javaMIDPVersion;
  }

  /**
   * @param javaMIDPVersion the javaMIDPVersion to set
   */
  public void setJavaMIDPVersion(String javaMIDPVersion) {
    this.javaMIDPVersion = javaMIDPVersion;
  }

  /**
   * @return the mms
   */
  public String getMms() {
    return mms;
  }

  /**
   * @param mms the mms to set
   */
  public void setMms(String mms) {
    this.mms = mms;
  }

  /**
   * @return the mms3gpp
   */
  public String getMms3gpp() {
    return mms3gpp;
  }

  /**
   * @param mms3gpp the mms3gpp to set
   */
  public void setMms3gpp(String mms3gpp) {
    this.mms3gpp = mms3gpp;
  }

  /**
   * @return the mmsWBXML
   */
  public String getMmsWBXML() {
    return mmsWBXML;
  }

  /**
   * @param mmsWBXML the mmsWBXML to set
   */
  public void setMmsWBXML(String mmsWBXML) {
    this.mmsWBXML = mmsWBXML;
  }

  /**
   * @return the mmspng
   */
  public String getMmspng() {
    return mmspng;
  }

  /**
   * @param mmspng the mmspng to set
   */
  public void setMmspng(String mmspng) {
    this.mmspng = mmspng;
  }

  /**
   * @return the mmsWML
   */
  public String getMmsWML() {
    return mmsWML;
  }

  /**
   * @param mmsWML the mmsWML to set
   */
  public void setMmsWML(String mmsWML) {
    this.mmsWML = mmsWML;
  }

  /**
   * @return the mmsVideo
   */
  public String getMmsVideo() {
    return mmsVideo;
  }

  /**
   * @param mmsVideo the mmsVideo to set
   */
  public void setMmsVideo(String mmsVideo) {
    this.mmsVideo = mmsVideo;
  }

  /**
   * @return the mmsMaxSize
   */
  public String getMmsMaxSize() {
    return mmsMaxSize;
  }

  /**
   * @param mmsMaxSize the mmsMaxSize to set
   */
  public void setMmsMaxSize(String mmsMaxSize) {
    this.mmsMaxSize = mmsMaxSize;
  }

  /**
   * @return the mmsForward
   */
  public String getMmsForward() {
    return mmsForward;
  }

  /**
   * @param mmsForward the mmsForward to set
   */
  public void setMmsForward(String mmsForward) {
    this.mmsForward = mmsForward;
  }

  /**
   * @return the flash
   */
  public String getFlash() {
    return flash;
  }

  /**
   * @param flash the flash to set
   */
  public void setFlash(String flash) {
    this.flash = flash;
  }

  /**
   * @return the vt
   */
  public String getVt() {
    return vt;
  }

  /**
   * @param vt the vt to set
   */
  public void setVt(String vt) {
    this.vt = vt;
  }

  /**
   * @return the vtH263
   */
  public String getVtH263() {
    return vtH263;
  }

  /**
   * @param vtH263 the vtH263 to set
   */
  public void setVtH263(String vtH263) {
    this.vtH263 = vtH263;
  }

  /**
   * @return the vtH264
   */
  public String getVtH264() {
    return vtH264;
  }

  /**
   * @param vtH264 the vtH264 to set
   */
  public void setVtH264(String vtH264) {
    this.vtH264 = vtH264;
  }

  /**
   * @return the vtMP4
   */
  public String getVtMP4() {
    return vtMP4;
  }

  /**
   * @param vtMP4 the vtMP4 to set
   */
  public void setVtMP4(String vtMP4) {
    this.vtMP4 = vtMP4;
  }

  /**
   * @return the imaP4
   */
  public String getImap4() {
    return imap4;
  }

  /**
   * @param imaP4 the imaP4 to set
   */
  public void setImap4(String imap4) {
    this.imap4 = imap4;
  }

  /**
   * @return the pop3
   */
  public String getPop3() {
    return pop3;
  }

  /**
   * @param pop3 the pop3 to set
   */
  public void setPop3(String pop3) {
    this.pop3 = pop3;
  }

  /**
   * @return the emailClient
   */
  public String getEmailClient() {
    return emailClient;
  }

  /**
   * @param emailClient the emailClient to set
   */
  public void setEmailClient(String emailClient) {
    this.emailClient = emailClient;
  }

  /**
   * @return the emailClientName
   */
  public String getEmailClientName() {
    return emailClientName;
  }

  /**
   * @param emailClientName the emailClientName to set
   */
  public void setEmailClientName(String emailClientName) {
    this.emailClientName = emailClientName;
  }

  /**
   * @return the emailClientVersion
   */
  public String getEmailClientVersion() {
    return emailClientVersion;
  }

  /**
   * @param emailClientVersion the emailClientVersion to set
   */
  public void setEmailClientVersion(String emailClientVersion) {
    this.emailClientVersion = emailClientVersion;
  }

  /**
   * @return the agps
   */
  public String getAgps() {
    return agps;
  }

  /**
   * @param agps the agps to set
   */
  public void setAgps(String agps) {
    this.agps = agps;
  }

  /**
   * @return the barRecog
   */
  public String getBarRecog() {
    return barRecog;
  }

  /**
   * @param barRecog the barRecog to set
   */
  public void setBarRecog(String barRecog) {
    this.barRecog = barRecog;
  }

  /**
   * @return the sip
   */
  public String getSip() {
    return sip;
  }

  /**
   * @param sip the sip to set
   */
  public void setSip(String sip) {
    this.sip = sip;
  }

  /**
   * @return the pim
   */
  public String getPim() {
    return pim;
  }

  /**
   * @param pim the pim to set
   */
  public void setPim(String pim) {
    this.pim = pim;
  }

  /**
   * @return the sycML
   */
  public String getSycML() {
    return sycML;
  }

  /**
   * @param sycML the sycML to set
   */
  public void setSycML(String sycML) {
    this.sycML = sycML;
  }

  /**
   * @return the videoSharing
   */
  public String getVideoSharing() {
    return videoSharing;
  }

  /**
   * @param videoSharing the videoSharing to set
   */
  public void setVideoSharing(String videoSharing) {
    this.videoSharing = videoSharing;
  }

}
