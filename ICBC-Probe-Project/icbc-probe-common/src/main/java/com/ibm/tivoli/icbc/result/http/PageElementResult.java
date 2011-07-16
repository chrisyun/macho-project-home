package com.ibm.tivoli.icbc.result.http;

import java.util.Set;

public class PageElementResult {
  /**
   * 页面中下载元素的个数
   */
  private int totalElement = 0;

  private Set<PageElementItem> topN4Slow = null;
  private Set<PageElementItem> topN4Size = null;

  public PageElementResult() {
    super();
  }

  public PageElementResult(Set<PageElementItem> topN4Size, Set<PageElementItem> topN4Slow) {
    super();
    this.topN4Size = topN4Size;
    this.topN4Slow = topN4Slow;
  }

  /**
   * @return the totalElement
   */
  public int getTotalElement() {
    return totalElement;
  }

  /**
   * @param totalElement the totalElement to set
   */
  public void setTotalElement(int totalElement) {
    this.totalElement = totalElement;
  }

  /**
   * @return the topN4Slow
   */
  public Set<PageElementItem> getTopN4Slow() {
    return topN4Slow;
  }

  /**
   * @param topN4Slow
   *          the topN4Slow to set
   */
  public void setTopN4Slow(Set<PageElementItem> topN4Slow) {
    this.topN4Slow = topN4Slow;
  }

  /**
   * @return the topN4Size
   */
  public Set<PageElementItem> getTopN4Size() {
    return topN4Size;
  }

  /**
   * @param topN4Size
   *          the topN4Size to set
   */
  public void setTopN4Size(Set<PageElementItem> topN4Size) {
    this.topN4Size = topN4Size;
  }

}
