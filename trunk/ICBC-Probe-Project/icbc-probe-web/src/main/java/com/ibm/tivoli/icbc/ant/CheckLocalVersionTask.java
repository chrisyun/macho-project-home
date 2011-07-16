package com.ibm.tivoli.icbc.ant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.apache.tools.ant.Task;

public class CheckLocalVersionTask  extends Task {
  
  private String toProperty = null;
  
  private String metaInfoParentDir = null;

  public CheckLocalVersionTask() {
    super();
  }
  
  public String getToProperty() {
    return toProperty;
  }

  public void setToProperty(String toProperty) {
    this.toProperty = toProperty;
  }

  public String getMetaInfoParentDir() {
    return metaInfoParentDir;
  }

  public void setMetaInfoParentDir(String metaInfoParentDir) {
    this.metaInfoParentDir = metaInfoParentDir;
  }

  /* (non-Javadoc)
   * @see org.apache.tools.ant.Task#execute()
   */
  public void execute() {
    String version = null;
    String revision = null;
    try {
      InputStream inputStream = new FileInputStream(new File(this.metaInfoParentDir, "META-INF/MANIFEST.MF"));
      Manifest manifest = new Manifest(inputStream);
      Attributes attributes = manifest.getMainAttributes();
      version = attributes.getValue("Application-Version");
      String builtTime = attributes.getValue("HudsonBuildId");
      revision = attributes.getValue("HudsonBuildNumber");
    } catch (IOException e) {
      //log(e, 0);
      e.printStackTrace();
    }
    
    if (version == null || revision == null) {
      log("Could not get version number from /META-INF/MANIFEST.MF");
      return;
    }
    
    String localVersion = version + "." + revision;
    if (this.toProperty == null || this.toProperty.trim().length() == 0) {
       log("Version is [" + localVersion + "], please set 'toProperty' attribute in this task");
       return;
    }
    
    log("Version is [" + localVersion + "]");
    getProject().setNewProperty(this.toProperty, localVersion);
  }

}
