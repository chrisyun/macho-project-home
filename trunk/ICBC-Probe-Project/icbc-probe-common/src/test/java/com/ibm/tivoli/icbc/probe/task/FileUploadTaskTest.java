package com.ibm.tivoli.icbc.probe.task;

import junit.framework.TestCase;

public class FileUploadTaskTest extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testCase1() throws Exception {
    FileUploadTask task = new FileUploadTask();
    //task.setExternalCmd(null);
    task.setSoapAddress("http://219.142.91.203:80/EBankMonitor/services/ProbeServiceImplService");
    task.execute();
  }

}
