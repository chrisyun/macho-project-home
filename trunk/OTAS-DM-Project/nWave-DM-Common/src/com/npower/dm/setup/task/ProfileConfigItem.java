package com.npower.dm.setup.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ProfileConfigItem {
  
  private String              externalID;
  
  private String              profileName;

  private String              template;

  private String              carrier;

  private String              napProfile;

  private String              proxyProfile;

  private boolean             isUserProfile = true;

  private String              description;

  private List<AttributeItem> attributes    = new ArrayList<AttributeItem>();

  /**
   * 
   */
  public ProfileConfigItem() {
    super();
  }

  public String getProfileName() {
    return profileName;
  }

  public void setProfileName(String profileName) {
    this.profileName = profileName;
  }

  public String getTemplate() {
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  public String getCarrier() {
    return carrier;
  }

  public void setCarrier(String carrier) {
    this.carrier = carrier;
  }

  public String getNapProfile() {
    return napProfile;
  }

  public void setNapProfile(String napProfile) {
    this.napProfile = napProfile;
  }

  public String getProxyProfile() {
    return proxyProfile;
  }

  public void setProxyProfile(String proxyProfile) {
    this.proxyProfile = proxyProfile;
  }

  public List<AttributeItem> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<AttributeItem> attributes) {
    this.attributes = attributes;
  }

  public void addAttribute(AttributeItem attributeItem) {
    this.attributes.add(attributeItem);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return the isUserProfile
   */
  public boolean isUserProfile() {
    return isUserProfile;
  }

  /**
   * @param isUserProfile the isUserProfile to set
   */
  public void setUserProfile(boolean isUserProfile) {
    this.isUserProfile = isUserProfile;
  }

  /**
   * @param isUserProfile the isUserProfile to set
   */
  public void setIsUserProfile(String isUserProfile) {
    isUserProfile = (StringUtils.isEmpty(isUserProfile))?"true":isUserProfile;
    this.isUserProfile = Boolean.parseBoolean(isUserProfile);
  }

  /**
   * @return Returns the externalID.
   */
  public String getExternalID() {
    return externalID;
  }

  /**
   * @param externalID The externalID to set.
   */
  public void setExternalID(String externalID) {
    this.externalID = externalID;
  }
}
