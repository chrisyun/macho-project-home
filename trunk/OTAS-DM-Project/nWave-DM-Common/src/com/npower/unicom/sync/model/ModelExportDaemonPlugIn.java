package com.npower.unicom.sync.model;

import java.io.File;
import java.util.Date;

import com.npower.dm.core.DMException;
import com.npower.dm.server.engine.EngineConfig;
import com.npower.unicom.sync.AbstractExportDaemonPlugIn;
import com.npower.unicom.sync.DBSyncItemReader;
import com.npower.unicom.sync.SyncItemWriter;

public class ModelExportDaemonPlugIn extends AbstractExportDaemonPlugIn {

  public ModelExportDaemonPlugIn() {
    super();
  }

  @Override
  protected DBSyncItemReader<?> getSyncItemReader() {
    try {
      return new DBSyncModelItemReader(EngineConfig.getProperties());
    } catch (DMException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @Override
  protected SyncItemWriter getSyncItemWriter(File requestFile, Date fromTime, Date toTime) {
    return new FileSyncModelItemWriter(requestFile, fromTime, toTime);
  }

}
