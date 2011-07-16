package com.ibm.tivoli.icbc.ant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tools.ant.Task;

public class CheckDownloadVersionTask  extends Task {
  
  /**
   * From this URL, we can get availiable probe software version
   */
  private String url = null;
  private String toProperty = null;

  public CheckDownloadVersionTask() {
    super();
  }
  
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getToProperty() {
    return toProperty;
  }

  public void setToProperty(String toProperty) {
    this.toProperty = toProperty;
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
    try {
      URL u = new URL(this.url);
      InputStream in = u.openStream();
      
      BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
      String line = reader.readLine();
      while (line != null) {
            if (line.startsWith("version:")) {
               agentSoftwareVersion = line.substring("version:".length()).trim();
               break;
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
    
    if (this.toProperty == null || this.toProperty.trim().length() == 0) {
       log("Version is [" + agentSoftwareVersion + "], please set 'toProperty' attribute in this task");
       return;
    }
    
    log("Version is [" + agentSoftwareVersion + "]");
    getProject().setNewProperty(this.toProperty, agentSoftwareVersion);
  }

}
