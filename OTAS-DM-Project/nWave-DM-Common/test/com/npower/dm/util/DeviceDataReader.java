package com.npower.dm.util;

import java.io.IOException;

import com.npower.dm.core.DMException;

public interface DeviceDataReader {

  /**
   * Read a data item
   * @return
   */
  public abstract DeviceData read() throws IOException;

  /**
   * Open reader
   * @throws DMException
   */
  public abstract void open() throws IOException;

  /**
   * Close reader
   * @throws DMException
   */
  public abstract void close() throws  IOException;

}