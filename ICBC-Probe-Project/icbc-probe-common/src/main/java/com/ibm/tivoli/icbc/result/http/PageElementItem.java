package com.ibm.tivoli.icbc.result.http;


public class PageElementItem {
  private String url = null;
  private int downloadSize = 0;
  private int downloadElapseTime = 0;
  private String httpCode = null;

  public static PageElementItem newInstance(HttpResult httpResult) {
    if (httpResult == null || httpResult.getTargetURLs() == null || httpResult.getTargetURLs().size() == 0
        || httpResult.getTargetURLs().get(0).getAccesses() == null || httpResult.getTargetURLs().get(0).getAccesses().size() == 0) {
      return null;
    }
    return new PageElementItem(httpResult.getTargetURLs().get(0).getUrl(), httpResult.getTargetURLs().get(0).getAccesses().get(0).getHttpCode(), httpResult
        .getTargetURLs().get(0).getAccesses().get(0).getDownloadSize(), (int) httpResult.getTargetURLs().get(0).getAccesses().get(0).getDownloadTime());
  }

  public PageElementItem(String url, String httpCode, int downloadSize, int downloadElapseTime) {
    super();
    this.httpCode = httpCode;
    this.downloadSize = downloadSize;
    this.downloadElapseTime = downloadElapseTime;
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @param url
   *          the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * @return the downloadSize
   */
  public int getDownloadSize() {
    return downloadSize;
  }

  /**
   * @param downloadSize
   *          the downloadSize to set
   */
  public void setDownloadSize(int downloadSize) {
    this.downloadSize = downloadSize;
  }

  /**
   * @return the downloadElapseTime
   */
  public int getDownloadElapseTime() {
    return downloadElapseTime;
  }

  /**
   * @param downloadElapseTime
   *          the downloadElapseTime to set
   */
  public void setDownloadElapseTime(int downloadElapseTime) {
    this.downloadElapseTime = downloadElapseTime;
  }

  /**
   * @return the httpCode
   */
  public String getHttpCode() {
    return httpCode;
  }

  /**
   * @param httpCode
   *          the httpCode to set
   */
  public void setHttpCode(String httpCode) {
    this.httpCode = httpCode;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ElementResult [downloadSize=" + downloadSize + ", downloadElapseTime=" + downloadElapseTime + ", httpCode=" + httpCode + ", url=" + url + "]\n";
  }

}
