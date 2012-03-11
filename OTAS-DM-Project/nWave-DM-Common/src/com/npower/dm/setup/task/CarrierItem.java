package com.npower.dm.setup.task;

import com.npower.dm.core.Carrier;
import com.npower.wap.omacp.OMACPSecurityMethod;

public class CarrierItem {
  public static final String DEFAULT_BOOTSTRAP_MAX_RETRIES = "3";

  private String country                = null;

  private String includedFilesCarriers  = null;

  private String externalID             = null;

  private String bootstrapNAPProfile    = null;

  private String phoneNumberPolicy      = null;

  private String smsCProperties         = null;

  private String authType               = null;

  private String notificationType       = null;

  private String notificationTimeout    = null;

  private String bootstrapTimeout       = null;

  private String notificationMaxRetries = null;

  private String name                   = null;
  
  private String bootstrapNapProfile    = null;

  private String bootstrapDmProfile     = null;
  
  private String bootstrapMaxRetries    = CarrierItem.DEFAULT_BOOTSTRAP_MAX_RETRIES;

  private String defaultBootstrapPinType = OMACPSecurityMethod.USERPIN.toString();

  private String defaultBootstrapUserPin = Carrier.DEFAULT_BOOTSTRAP_USER_PIN;
  
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getIncludedFilesCarriers() {
    return includedFilesCarriers;
  }

  public void setIncludedFilesCarriers(String includedFilesCarriers) {
    this.includedFilesCarriers = includedFilesCarriers;
  }

  public String getExternalID() {
    return externalID;
  }

  public void setExternalID(String externalID) {
    this.externalID = externalID;
  }

  public String getBootstrapNAPProfile() {
    return bootstrapNAPProfile;
  }

  public void setBootstrapNAPProfile(String bootstrapNAPProfil) {
    this.bootstrapNAPProfile = bootstrapNAPProfil;
  }

  public String getPhoneNumberPolicy() {
    return phoneNumberPolicy;
  }

  public void setPhoneNumberPolicy(String phoneNumberPolicy) {
    this.phoneNumberPolicy = phoneNumberPolicy;
  }

  public String getAuthType() {
    return authType;
  }

  public void setAuthType(String authType) {
    this.authType = authType;
  }

  public String getNotificationType() {
    return notificationType;
  }

  public void setNotificationType(String notificationType) {
    this.notificationType = notificationType;
  }

  public String getNotificationTimeout() {
    return notificationTimeout;
  }

  public void setNotificationTimeout(String notificationTimeout) {
    this.notificationTimeout = notificationTimeout;
  }

  public String getBootstrapTimeout() {
    return bootstrapTimeout;
  }

  public void setBootstrapTimeout(String bootstrapTimeout) {
    this.bootstrapTimeout = bootstrapTimeout;
  }

  public String getNotificationMaxRetries() {
    return notificationMaxRetries;
  }

  public void setNotificationMaxRetries(String notificationMaxRetries) {
    this.notificationMaxRetries = notificationMaxRetries;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSmsCProperties() {
    return smsCProperties;
  }

  public void setSmsCProperties(String smsCProperties) {
    this.smsCProperties = smsCProperties;
  }

  /**
   * @return Returns the bootstrapNapProfile.
   */
  public String getBootstrapNapProfile() {
    return bootstrapNapProfile;
  }

  /**
   * @param bootstrapNapProfile The bootstrapNapProfile to set.
   */
  public void setBootstrapNapProfile(String bootstrapNapProfile) {
    this.bootstrapNapProfile = bootstrapNapProfile;
  }

  /**
   * @return Returns the bootstrapDmProfile.
   */
  public String getBootstrapDmProfile() {
    return bootstrapDmProfile;
  }

  /**
   * @param bootstrapDmProfile The bootstrapDmProfile to set.
   */
  public void setBootstrapDmProfile(String bootstrapDmProfile) {
    this.bootstrapDmProfile = bootstrapDmProfile;
  }

  public String getBootstrapMaxRetries() {
    return bootstrapMaxRetries;
  }

  public void setBootstrapMaxRetries(String bootstrapMaxRetries) {
    this.bootstrapMaxRetries = bootstrapMaxRetries;
  }

  public String getDefaultBootstrapPinType() {
    return defaultBootstrapPinType;
  }

  public void setDefaultBootstrapPinType(String defaultBootstrapPinType) {
    this.defaultBootstrapPinType = defaultBootstrapPinType;
  }

  public String getDefaultBootstrapUserPin() {
    return defaultBootstrapUserPin;
  }

  public void setDefaultBootstrapUserPin(String defaultBootstrapUserPin) {
    this.defaultBootstrapUserPin = defaultBootstrapUserPin;
  }
 
}
