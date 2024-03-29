package com.tutucha.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Software extends Navigation {

  private Vendor vendor = new Vendor();
  private String version = null;
  private int size = 0;
  /**
   * 1 ~ 100
   */
  private int rank = 0;
  private float cpaPrice = (float) 0.0;
  private List<String> screenshots = new ArrayList<String>();
  /**
   * 例如：公测等
   */
  private String status = null;
  
  private long totalDownload = System.currentTimeMillis() % 1000000;

  private Date releaseTime = new Date();
  
  private String lang = "中文";
  
  public Software() {
    super();
  }

  public Software(String id, String label, String url, String icon, String categoryId) {
    super(id, label, url, icon, categoryId);
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  public Vendor getVendor() {
    return vendor;
  }

  public void setVendor(Vendor vendor) {
    this.vendor = vendor;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getRank() {
    return rank;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  public float getCpaPrice() {
    return cpaPrice;
  }

  public void setCpaPrice(float cpaPrice) {
    this.cpaPrice = cpaPrice;
  }

  public List<String> getScreenshots() {
    return screenshots;
  }

  public void setScreenshots(List<String> screenshots) {
    this.screenshots = screenshots;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public long getTotalDownload() {
    return totalDownload;
  }

  public void setTotalDownload(long totalDownload) {
    this.totalDownload = totalDownload;
  }

  public Date getReleaseTime() {
    return releaseTime;
  }

  public void setReleaseTime(Date releaseTime) {
    this.releaseTime = releaseTime;
  }

}