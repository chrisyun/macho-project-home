/**
 * 
 */
package com.ibm.tivoli.icbc.result.http;

/**
 * @author Zhao Dong Lu
 *
 */
public class URLAccessResult {

  private String url = null;
  /**
   * In milliseconds
   */
  private long dnsTime = 0;
  /**
   * In milliseconds
   */
  private long netTime = 0;
  /**
   * In milliseconds
   */
  private long downloadTime = 0;

  private String httpCode = null;
  
  private int downloadSize = 0;
  
  private String error = null;
  
  private int downloadElements = 0;
  
  private String imageFile = null;
  
  private PageElementResult pageElementResult = null;
  /**
   * 
   */
  public URLAccessResult() {
    super();
  }

  public URLAccessResult(String url, long dnsTime, long netTime, long downloadTime, String httpCode, int downloadSize) {
    super();
    this.url = url;
    this.dnsTime = dnsTime;
    this.netTime = netTime;
    this.downloadTime = downloadTime;
    this.httpCode = httpCode;
    this.downloadSize = downloadSize;
  }

  public long getNetTime() {
    return netTime;
  }

  public void setNetTime(long netTime) {
    this.netTime = netTime;
  }

  public long getDnsTime() {
    return dnsTime;
  }

  public void setDnsTime(long dnsTime) {
    this.dnsTime = dnsTime;
  }

  public long getDownloadTime() {
    return downloadTime;
  }

  public void setDownloadTime(long downloadTime) {
    this.downloadTime = downloadTime;
  }

  public String getHttpCode() {
    return httpCode;
  }

  public void setHttpCode(String httpCode) {
    this.httpCode = httpCode;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getDownloadSize() {
    return downloadSize;
  }

  public void setDownloadSize(int downloadSize) {
    this.downloadSize = downloadSize;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  /**
   * @return the downloadElements
   */
  public int getDownloadElements() {
    return downloadElements;
  }

  /**
   * @param downloadElements the downloadElements to set
   */
  public void setDownloadElements(int downloadElements) {
    this.downloadElements = downloadElements;
  }

  /**
   * @return the imageFile
   */
  public String getImageFile() {
    return imageFile;
  }

  /**
   * @param imageFile the imageFile to set
   */
  public void setImageFile(String imageFile) {
    this.imageFile = imageFile;
  }

  /**
   * @return the pageElementResult
   */
  public PageElementResult getPageElementResult() {
    return pageElementResult;
  }

  /**
   * @param pageElementResult the pageElementResult to set
   */
  public void setPageElementResult(PageElementResult pageElementResult) {
    this.pageElementResult = pageElementResult;
  }

}
