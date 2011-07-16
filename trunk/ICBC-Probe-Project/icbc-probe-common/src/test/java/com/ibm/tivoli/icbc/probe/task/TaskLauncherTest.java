/**
 * 
 */
package com.ibm.tivoli.icbc.probe.task;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * @author Zhao Dong Lu
 *
 */
public class TaskLauncherTest extends TestCase {

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
  
  public void testLauncher() throws Exception {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("com/ibm/tivoli/icbc/spring/taskBean.xml");
    TaskLauncher taskLauncher = (TaskLauncher)applicationContext.getBean("taskLauncher");
    TaskContext context = taskLauncher.getTaskContext();
    context.setAttribute("TASK_CONFIG_URL", "file:////home/zhao/workspaces/ICBCProbe/src/test/resources/config/tasks.config.2.xml");
    taskLauncher.run( );
    
    //assertFalse(context.getTaskResults().isEmpty());
  }

  public void testLauncherICMP() throws Exception {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("com/ibm/tivoli/icbc/spring/taskBean.xml");
    TaskLauncher taskLauncher = (TaskLauncher)applicationContext.getBean("taskLauncher");
    TaskContext context = taskLauncher.getTaskContext();
    context.setAttribute("TASK_CONFIG_URL", "file:////C:/Users/IBM_ADMIN/workspace/ICBC-Probe-Project/icbc-probe-common/src/test/resources/config/tasks.config.icmp.1.xml");
    taskLauncher.run( );
    
    //assertFalse(context.getTaskResults().isEmpty());
  }

  public void testLauncherIE() throws Exception {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("com/ibm/tivoli/icbc/spring/taskBean.xml");
    TaskLauncher taskLauncher = (TaskLauncher)applicationContext.getBean("taskLauncher");
    TaskContext context = taskLauncher.getTaskContext();
    context.setAttribute("TASK_CONFIG_URL", "file:////C:/Users/IBM_ADMIN/workspace/ICBC-Probe-Project/icbc-probe-common/src/test/resources/config/tasks.config.ie.2.xml");
    taskLauncher.run( );
    
    //assertFalse(context.getTaskResults().isEmpty());
  }
  
  public void testLauncherIEForever() throws Exception {
   for (int i = 0; i < 1000; i++) {
     this.testLauncherIE();
     Thread.sleep(60 * 1000);
   }
  }
  
  public void testLauncherNativeA() throws Exception {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("com/ibm/tivoli/icbc/spring/taskBean.xml");
    TaskLauncher taskLauncher = (TaskLauncher)applicationContext.getBean("taskLauncher");
    TaskContext context = taskLauncher.getTaskContext();
    context.setAttribute("TASK_CONFIG_URL", "file:////C:/Users/IBM_ADMIN/workspace/ICBC-Probe-Project/icbc-probe-common/src/test/resources/config/tasks.config.dns.nativeA.xml");
    taskLauncher.run( );
    
    //assertFalse(context.getTaskResults().isEmpty());
  }
  
  public void notestScheduler() throws Exception {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("com/ibm/tivoli/icbc/spring/mainBean.xml");
    //TaskLauncher taskLauncher = (TaskLauncher)applicationContext.getBean("taskLauncher");
    //taskLauncher.run();
    
    Thread.sleep(100 * 60 * 60 * 1000);
  }
  
  /*
  public static boolean isChinese(char c) {
    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS 
        || ub == Character.UnicodeBlock.C
        || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
        || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
        || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
        || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
        ) {
      
    }
  }
  */
}
