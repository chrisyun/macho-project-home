package com.npower.dl;

import com.npower.dm.core.DMException;

public abstract class BaseDownloadDescriptor {

  private String downloadID = null;
  private String downloadURI = null;
  private String installNotifyURI = null;

  public BaseDownloadDescriptor() {
    super();
  }

  public String getContentType() throws DMException {
    return DownloadDescriptor.OMA_DOWNLOAD_DESCRIPTOR_CONTENT_TYPE;
  }

  /**
   * @return the downloadID
   */
  public String getDownloadID() {
    return downloadID;
  }

  /**
   * @param downloadID the downloadID to set
   */
  public void setDownloadID(String downloadID) {
    this.downloadID = downloadID;
  }

  /**
   * @return the downloadURL
   */
  public String getDownloadURI() {
    return downloadURI;
  }

  /**
   * @param downloadURL the downloadURL to set
   */
  public void setDownloadURI(String downloadURL) {
    this.downloadURI = downloadURL;
  }

  /**
   * @return the installNotifyURI
   */
  public String getInstallNotifyURI() {
    return installNotifyURI;
  }

  /**
   * @param installNotifyURI the installNotifyURI to set
   */
  public void setInstallNotifyURI(String installNotifyURI) {
    this.installNotifyURI = installNotifyURI;
  }

  /**
   * @return
   * @throws DMException
   */
  public abstract String getContent() throws DMException;
  
}