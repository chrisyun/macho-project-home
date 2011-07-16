/**
 * 
 */
package com.ibm.tivoli.icbc.probe.http;

import com.ibm.tivoli.icbc.result.http.PageElementResult;

/**
 * @author zhaodonglu
 * 
 */
public interface BrowserExecutor {
  
  public class BrowserResult {
    private String httpCode = null;
    private long elapseTime;
    private int numberOfDownloadElements;
    private String imageFile;
    private String htmlContent = null;
    private String locationURL = null;
    private long beginFirstNavigationTime = 0;
    
    private PageElementResult pageElementResult = null;
    /**
     * 
     */
    public BrowserResult() {
      super();
    }

    /**
     * @param httpCode
     * @param elapseTime
     * @param numberOfDownloadElements
     * @param imageFile
     */
    public BrowserResult(String httpCode, long elapseTime, int numberOfDownloadElements, String imageFile) {
      super();
      this.httpCode = httpCode;
      this.elapseTime = elapseTime;
      this.numberOfDownloadElements = numberOfDownloadElements;
      this.imageFile = imageFile;
    }

    public String getHttpCode() {
      return httpCode;
    }

    public void setHttpCode(String httpCode) {
      this.httpCode = httpCode;
    }

    public long getElapseTime() {
      return elapseTime;
    }

    public void setElapseTime(long elapseTime) {
      this.elapseTime = elapseTime;
    }

    public int getNumberOfDownloadElements() {
      return numberOfDownloadElements;
    }

    public void setNumberOfDownloadElements(int numberOfDownloadElements) {
      this.numberOfDownloadElements = numberOfDownloadElements;
    }

    public String getImageFile() {
      return imageFile;
    }

    public void setImageFile(String imageFile) {
      this.imageFile = imageFile;
    }

    public long getBeginFirstNavigationTime() {
      return beginFirstNavigationTime;
    }

    public void setBeginFirstNavigationTime(long beginFirstNavigationTime) {
      this.beginFirstNavigationTime = beginFirstNavigationTime;
    }

    /**
     * @return the htmlContent
     */
    public String getHtmlContent() {
      return htmlContent;
    }

    /**
     * @param htmlContent the htmlContent to set
     */
    public void setHtmlContent(String htmlContent) {
      this.htmlContent = htmlContent;
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

    /**
     * @return the locationURL
     */
    public String getLocationURL() {
      return locationURL;
    }

    /**
     * @param locationURL the locationURL to set
     */
    public void setLocationURL(String locationURL) {
      this.locationURL = locationURL;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return "BrowserResult [httpCode=" + httpCode + ", elapseTime=" + elapseTime + ", numberOfDownloadElements=" + numberOfDownloadElements + ", imageFile="
          + imageFile + ", beginFirstNavigationTime=" + beginFirstNavigationTime + "]";
    }

  }

  public BrowserResult navigate(String url);

}
