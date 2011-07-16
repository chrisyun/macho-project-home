/**
 * 
 */
package com.ibm.tivoli.icbc.probe.task;

import java.io.IOException;
import java.io.Reader;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.ibm.tivoli.icbc.probe.ProbeException;

/**
 * @author Zhao Dong Lu
 *
 */
public class TaskConfigFactory {

  /**
   * 
   */
  public TaskConfigFactory() {
    super();
  }
  
  public static TaskConfigFactory newInstance() throws ProbeException {
    return new TaskConfigFactory();
  }
  
  public TaskConfig loadTaskConfig(Reader config) throws ProbeException {
    TaskConfig launcher = new TaskConfig();
    Digester digester = this.getDigester();
    digester.push(launcher);
    try {
      digester.parse(config);
      return launcher;
    } catch (IOException e) {
      throw new ProbeException("Fail to read TaskConfig from config: " + config, e);
    } catch (SAXException e) {
      throw new ProbeException("Fail to read TaskConfig from config: " + config, e);
    }
  }
  
  private Digester getDigester() {
    Digester digester = new Digester();
    digester.setValidating(false);

    digester.addBeanPropertySetter("*/taskconfig/scheduletime/interval", "interval");
    digester.addBeanPropertySetter("*/taskconfig/retry/delay", "retryInterval");
    digester.addBeanPropertySetter("*/taskconfig/retry/times", "numberOfRetry");
    
    digester.addObjectCreate("*/taskconfig/tasks/task", Task.class);
    digester.addBeanPropertySetter("*/taskconfig/tasks/task/id", "id");
    digester.addBeanPropertySetter("*/taskconfig/tasks/task/name", "name");
    digester.addBeanPropertySetter("*/taskconfig/tasks/task/type", "type");
    digester.addBeanPropertySetter("*/taskconfig/tasks/task/btype", "btype");
    digester.addBeanPropertySetter("*/taskconfig/tasks/task/runmode", "runModeAsString");
    digester.addBeanPropertySetter("*/taskconfig/tasks/task/delay", "delay");
    
    digester.addObjectCreate("*/taskconfig/tasks/task/parameters/parameter", Parameter.class);
    digester.addBeanPropertySetter("*/taskconfig/tasks/task/parameters/parameter/name", "name");
    digester.addBeanPropertySetter("*/taskconfig/tasks/task/parameters/parameter/value", "value");


    digester.addSetNext("*/taskconfig/tasks/task/parameters/parameter", "addParameter");
    digester.addSetNext("*/taskconfig/tasks/task", "add");
    
    return (digester);

  }

}
