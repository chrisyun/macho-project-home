package com.ibm.tivoli.icbc.ant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.apache.tools.ant.Task;

public class CheckSoftwareUpdateTask  extends Task {
  
  /**
   * From this URL, we can get availiable probe software version
   */
  private String url = null;
  private String toUpdateFlagProperty = null;
  private String toDownloadURLProperty = null;
  
  private String metaInfoParentDir = null;

  public CheckSoftwareUpdateTask() {
    super();
  }
  
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getToUpdateFlagProperty() {
    return toUpdateFlagProperty;
  }

  public void setToUpdateFlagProperty(String toUpdateFlagProperty) {
    this.toUpdateFlagProperty = toUpdateFlagProperty;
  }

  public String getToDownloadURLProperty() {
    return toDownloadURLProperty;
  }

  public void setToDownloadURLProperty(String toDownloadURLProperty) {
    this.toDownloadURLProperty = toDownloadURLProperty;
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
    if (this.url == null || this.url.trim().length() == 0) {
       this.log("Please specify a URL");
       return;
    }
    
    String agentSoftwareVersion = null;
    String downloadURL = null;
    try {
      URL u = new URL(this.url);
      InputStream in = u.openStream();
      
      BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
      String line = reader.readLine();
      while (line != null) {
            if (line.startsWith("version:")) {
               agentSoftwareVersion = line.substring("version:".length()).trim();
            } else if (line.startsWith("download-url:")) {
              downloadURL = line.substring("download-url:".length()).trim();
            }
            line = reader.readLine();
      }
    } catch (MalformedURLException e) {
      //log(e, 0);
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      //log(e, 0);
      e.printStackTrace();
    } catch (IOException e) {
      //log(e, 0);
      e.printStackTrace();
    }
    
    if (agentSoftwareVersion == null) {
      log("Could not get version number from URL: " + this.url);
      return;
    }
    
    String localVersion = this.getLocalVersion();
    if (localVersion == null) {
      log("Could not get local version number from Dir: " + this.metaInfoParentDir);
      return;
    }
    
    boolean update = false;
    if (localVersion.compareToIgnoreCase(agentSoftwareVersion) < 0) {
       update = true;
    }
    log("Server version is [" + agentSoftwareVersion + "], local version is [" + localVersion + "]");
    log("Download URL [" + downloadURL + "]");

    if (this.toUpdateFlagProperty == null || this.toUpdateFlagProperty.trim().length() == 0) {
       log("Server version is [" + agentSoftwareVersion + "], local version is [" + localVersion + "], please set 'toProperty' attribute in this task");
       return;
    }
    if (update) {
      getProject().setNewProperty(this.toUpdateFlagProperty, Boolean.toString(update));
      getProject().setNewProperty(this.toDownloadURLProperty, downloadURL);
      log("New Agent package available, version: " + agentSoftwareVersion);
    } else {
      log("Not found new agent package!");
    }
  }
  
  private String getLocalVersion() {
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
      return null;
    }
    
    String localVersion = version + "." + revision;
    return localVersion;    
  }

}
