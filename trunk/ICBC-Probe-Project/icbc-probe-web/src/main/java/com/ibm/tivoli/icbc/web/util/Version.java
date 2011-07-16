package com.ibm.tivoli.icbc.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

public class Version {

  public static String getVersion(ServletContext application) {
    String defaultVersion = "";
    try {
      InputStream inputStream = application.getResourceAsStream("/META-INF/MANIFEST.MF");
      Manifest manifest = new Manifest(inputStream);
      Attributes attributes = manifest.getMainAttributes();
      String version = attributes.getValue("Application-Version");
      String builtTime = attributes.getValue("HudsonBuildId");
      String revision = attributes.getValue("HudsonBuildNumber");

      if (version != null && version != "" && builtTime != null && builtTime != "" && revision != null && revision != "") {

        String versionTmp = "";
        if (version.contains("SNAPSHOT")) {
          versionTmp = version.substring(0, version.indexOf("-"));
        } else {
          versionTmp = version;
        }

        return versionTmp + " Build " + builtTime.substring(0, 10) + " Rev:" + revision;
      }
    } catch (IOException e) {
    }
    return defaultVersion;
  }

}
