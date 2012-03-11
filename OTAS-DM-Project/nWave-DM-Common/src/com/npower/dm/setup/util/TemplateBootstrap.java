package com.npower.dm.setup.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.digester.Digester;

import com.npower.dm.core.ProfileTemplate;
import com.npower.setup.core.SetupException;

public class TemplateBootstrap {

  private String         filename = null;

  public ProfileTemplate template = null;

  public TemplateBootstrap() {
    super();
  }

  /**
   * @return the filename
   */
  public String getFilename() {
    return filename;
  }

  /**
   * @param filename
   *          the filename to set
   */
  public void setFilename(String filename) {
    this.filename = filename;
  }

  /**
   * @return the setup
   */
  public ProfileTemplate getProfileTemplate() {
    return template;
  }

  /**
   * @param setup
   *          the setup to set
   */
  public void setJDBCTask(ProfileTemplate template) {
    this.template = template;
  }

  private Digester createDigester() {
    Digester digester = new Digester();
    digester.setValidating(false);

    return (digester);
  }

  public void execute() throws SetupException {

    File file = new File(this.getFilename());
    if (!file.exists()) {
      throw new SetupException("Could not load setup config file: " + this.getFilename());
    }

    Digester digester = createDigester();
    try {
      digester.push(this);
      digester.parse(new FileInputStream(file));

    } catch (Exception e) {
      throw new SetupException(e);
    }

  }

}
