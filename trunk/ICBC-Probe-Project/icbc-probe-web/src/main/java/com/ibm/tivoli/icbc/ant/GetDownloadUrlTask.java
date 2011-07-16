package com.ibm.tivoli.icbc.ant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.tools.ant.Task;

public class GetDownloadUrlTask  extends Task {
  
  /**
   * From this URL, we can get availiable probe software version
   */
  private String path = null;
  private String toProperty = null;

  public GetDownloadUrlTask() {
    super();
  }
  
  public String getToProperty() {
    return toProperty;
  }

  public void setToProperty(String toProperty) {
    this.toProperty = toProperty;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  /* (non-Javadoc)
   * @see org.apache.tools.ant.Task#execute()
   */
  public void execute() {
    try {
      FileInputStream in = new FileInputStream(new File("C:/icbc-probe/conf/probe.xml"));
      Properties prop = new Properties();
      prop.loadFromXML(in);
      String probeAgentUrl = prop.getProperty("probe.agent.server.soap.url");
      this.log("Probe URL: " + probeAgentUrl );

      URL u = new URL(probeAgentUrl);
      URL result = new URL(u.getProtocol() + "://" + u.getHost() + ":" + u.getPort() + this.path);
      this.log("Download URL: " + result.toString());
      getProject().setNewProperty(this.toProperty, result.toString());
    } catch (MalformedURLException e) {
      //log(e, 0);
      e.printStackTrace();
    } catch (IOException e) {
      //log(e, 0);
      e.printStackTrace();
    }
  }

}
