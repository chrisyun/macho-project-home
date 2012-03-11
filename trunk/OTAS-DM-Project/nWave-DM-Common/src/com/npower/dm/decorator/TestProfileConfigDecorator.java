package com.npower.dm.decorator;

import java.util.Locale;

import com.npower.dm.core.ProfileConfig;
import com.npower.dm.hibernate.entity.ProfileConfigEntity;
import com.npower.dm.util.MessageResources;
import com.npower.dm.util.MessageResourcesFactory;

import junit.framework.TestCase;

public class TestProfileConfigDecorator extends TestCase {
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
  
  public void testProfileConfigName() throws Exception {
    MessageResourcesFactory factory = MessageResourcesFactory.createFactory();
    MessageResources messageResources = factory.createResources("com/npower/dm/decorator/dictionary");

    ResourceManager<ProfileConfig> resources = new ProfileConfigResourceManager(messageResources);
    
    {
      ProfileConfig profileConfig = new ProfileConfigEntity();
      profileConfig.setName("GMail");
      profileConfig.setDescription("descriptionGMail");
      
      ProfileConfigDecorator decorator = new ProfileConfigDecorator(Locale.CHINA, profileConfig, resources);
      assertEquals("GMail” œ‰", decorator.getName());
    }
  }

}
