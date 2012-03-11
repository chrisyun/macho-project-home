package com.npower.unicom.sync.firmware;

import com.npower.unicom.sync.SyncAction;
import com.npower.unicom.sync.SyncItem;

public class FirmwareSyncItem extends SyncItem {

  private String manufacturer;
  private String model;
  private String fromVersion;
  private String toVersion;
  private String firmwareName;
  private byte[] bytes;

  public FirmwareSyncItem() {
    super();
  }
  
  public FirmwareSyncItem(String id, SyncAction action) {
    super(id, action);
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getFromVersion() {
    return fromVersion;
  }

  public void setFromVersion(String fromVersion) {
    this.fromVersion = fromVersion;
  }

  public String getToVersion() {
    return toVersion;
  }

  public void setToVersion(String toVersion) {
    this.toVersion = toVersion;
  }

  public String getFirmwareName() {
    return firmwareName;
  }

  public void setFirmwareName(String firmwareName) {
    this.firmwareName = firmwareName;
  }

  /**
   * @return the bytes
   */
  public byte[] getBytes() {
    return bytes;
  }

  /**
   * @param bytes the bytes to set
   */
  public void setBytes(byte[] bytes) {
    this.bytes = bytes;
  }
}
