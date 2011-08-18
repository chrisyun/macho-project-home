/**
 * 
 */
package com.ibm.tivoli.icbc.probe.task;

import java.io.File;
import java.io.FileFilter;

import com.ibm.tivoli.icbc.probe.http.JxBrowserExecutorImpl;

/**
 * @author zhaodonglu
 *
 */
public class ClearTempFileTask {

  private int maxHour = 48;

  public ClearTempFileTask(int maxHour) {
    super();
    this.maxHour = maxHour;
  }

  /**
   * @return the maxHour
   */
  public int getMaxHour() {
    return maxHour;
  }

  /**
   * @param maxHour the maxHour to set
   */
  public void setMaxHour(int maxHour) {
    this.maxHour = maxHour;
  }

  public class MyFileFilter implements FileFilter {
    private int maxHour = 48;

    public MyFileFilter() {
      super();
    }

    public MyFileFilter(int maxHour) {
      super();
      this.maxHour = maxHour;
    }

    @Override
    public boolean accept(File file) {
      if (file.isDirectory()) {
         return false;
      }
      
      if (!file.getName().startsWith(JxBrowserExecutorImpl.PROBE_TEMP_FILE_PREFIX)) {
         return false;
      }
      return (System.currentTimeMillis() - file.lastModified()) > this.maxHour  * 3600 * 1000;
    }    
  }

  /**
   * 
   */
  public ClearTempFileTask() {
  }
  
  public void execute() {
    File dir = new File(System.getProperty("java.io.tmpdir"));
    File[] oldFiles = dir.listFiles(new MyFileFilter(this.maxHour));
    if (oldFiles != null) {
       for (File oldFile: oldFiles) {
           oldFile.delete();
       }
    }
  }

}
