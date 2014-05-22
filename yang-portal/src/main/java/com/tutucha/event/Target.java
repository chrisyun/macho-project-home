package com.tutucha.event;

public class Target {

  private TargetType type = null;
  
  private String channelId = null;

  private String pageId = null;

  private String areaId = null;

  private String navigationId = null;

  private String url = null;

  public Target() {
    super();
  }

  public String getChannelId() {
    return channelId;
  }

  public void setChannelId(String channelId) {
    this.channelId = channelId;
  }

  public TargetType getType() {
    return type;
  }

  public void setType(TargetType type) {
    this.type = type;
  }

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  public String getAreaId() {
    return areaId;
  }

  public void setAreaId(String areaId) {
    this.areaId = areaId;
  }

  public String getNavigationId() {
    return navigationId;
  }

  public void setNavigationId(String navigationId) {
    this.navigationId = navigationId;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return "Target [type=" + type + ", pageId=" + pageId + ", areaId=" + areaId + ", navigationId=" + navigationId + ", url=" + url + "]";
  }

}
