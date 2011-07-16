/**
 * 
 */
package com.ibm.tivoli.icbc.probe.task;

import java.io.InputStreamReader;
import java.io.Reader;

import com.ibm.tivoli.icbc.probe.task.TaskConfig;
import com.ibm.tivoli.icbc.probe.task.TaskConfigFactory;
import com.ibm.tivoli.icbc.probe.task.TaskRunMode;

import junit.framework.TestCase;

/**
 * @author Zhao Dong Lu
 *
 */
public class TaskConfigFactoryTest extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link com.ibm.tivoli.icbc.probe.task.TaskConfigFactory#loadTaskConfig(java.io.Reader)}.
   */
  public void testLoad() throws Exception {
    TaskConfigFactory factory = TaskConfigFactory.newInstance();
    Reader configReader = new InputStreamReader(this.getClass().getResourceAsStream("/config/tasks.config.1.xml"), "UTF-8");
    TaskConfig config = factory.loadTaskConfig(configReader);
    assertNotNull(config);
    
    assertEquals(100, config.getInterval());
    assertEquals(1, config.getTasks().size());
    assertEquals("123", config.getTasks().get(0).getId());
    assertEquals("TASK123", config.getTasks().get(0).getName());
    assertEquals("DNS_CNAME", config.getTasks().get(0).getType());
    assertEquals(1000, config.getTasks().get(0).getDelay());
    assertEquals(TaskRunMode.CONCURRENT, config.getTasks().get(0).getRunMode());
    
    assertEquals(1, config.getTasks().get(0).getParameters().size());
    assertEquals("target", config.getTasks().get(0).getParameters().get(0).getName());
    assertEquals(2, config.getTasks().get(0).getParameters().get(0).getValues().size());
    assertEquals("www.icbc.com.cn", config.getTasks().get(0).getParameters().get(0).getValues().get(0));
    assertEquals("www.icbc-ltd.com", config.getTasks().get(0).getParameters().get(0).getValues().get(1));
    
  }

}
