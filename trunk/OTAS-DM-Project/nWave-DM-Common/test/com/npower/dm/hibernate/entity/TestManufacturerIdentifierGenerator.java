package com.npower.dm.hibernate.entity;

import java.io.Serializable;

import junit.framework.TestCase;

import org.hibernate.HibernateException;
import org.hibernate.id.IdentifierGenerator;

import com.npower.dm.core.Manufacturer;

public class TestManufacturerIdentifierGenerator extends TestCase {
  
  private IdentifierGenerator generator;


  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    generator = new ManufacturerIdentifierGenerator();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  
  public void testEmpty() throws Exception {
    Manufacturer manufacturer = new ManufacturerEntity();
    try {
        generator.generate(null, manufacturer);
        fail("Must throw HibernateException");
    } catch (HibernateException e) {
      assertTrue(true);
    }
    
    manufacturer.setExternalId("NOKIA");
    try {
        Serializable id = generator.generate(null, manufacturer);
        assertNotNull(id);
        assertTrue(id instanceof Long);
        assertTrue(((Long)id).longValue() > 0);
    } catch (HibernateException e) {
      fail("Not throw HibernateException");
    }
  }

  public void testHashCode1() throws Exception {
    Manufacturer ma1 = new ManufacturerEntity();
    ma1.setExternalId("NOKIA");

    Manufacturer ma2 = new ManufacturerEntity();
    ma2.setExternalId("Nokia");
    
    Manufacturer ma3 = new ManufacturerEntity();
    ma3.setExternalId("nokia");
    
    Serializable id1 = generator.generate(null, ma1);
    assertNotNull(id1);
    assertTrue(id1 instanceof Long);
    assertTrue(((Long)id1).longValue() > 0);
    
    Serializable id2 = generator.generate(null, ma2);
    assertNotNull(id2);
    assertTrue(id2 instanceof Long);
    assertTrue(((Long)id2).longValue() > 0);
    
    Serializable id3 = generator.generate(null, ma3);
    assertNotNull(id3);
    assertTrue(id3 instanceof Long);
    assertTrue(((Long)id3).longValue() > 0);
    
    assertEquals(((Long)id1).longValue(), ((Long)id2).longValue());
    assertEquals(((Long)id2).longValue(), ((Long)id3).longValue());
  }
  
  public void testHashCode2() throws Exception {
    Manufacturer ma1 = new ManufacturerEntity();
    ma1.setExternalId("NOKIA");

    Manufacturer ma2 = new ManufacturerEntity();
    ma2.setExternalId("Motorola");
    
    Manufacturer ma3 = new ManufacturerEntity();
    ma3.setExternalId("SonyEricsson");
    
    Serializable id1 = generator.generate(null, ma1);
    assertNotNull(id1);
    assertTrue(id1 instanceof Long);
    assertTrue(((Long)id1).longValue() > 0);
    
    Serializable id2 = generator.generate(null, ma2);
    assertNotNull(id2);
    assertTrue(id2 instanceof Long);
    assertTrue(((Long)id2).longValue() > 0);
    
    Serializable id3 = generator.generate(null, ma3);
    assertNotNull(id3);
    assertTrue(id3 instanceof Long);
    assertTrue(((Long)id3).longValue() > 0);
    
    assertTrue(((Long)id1).longValue() != ((Long)id2).longValue());
    assertTrue(((Long)id2).longValue() != ((Long)id3).longValue());
    assertTrue(((Long)id1).longValue() != ((Long)id3).longValue());
  }
  
}
