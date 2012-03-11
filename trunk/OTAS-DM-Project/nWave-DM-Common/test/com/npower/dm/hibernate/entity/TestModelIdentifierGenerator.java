package com.npower.dm.hibernate.entity;

import java.io.Serializable;

import junit.framework.TestCase;

import org.hibernate.HibernateException;
import org.hibernate.id.IdentifierGenerator;

import com.npower.dm.core.Manufacturer;
import com.npower.dm.core.Model;

public class TestModelIdentifierGenerator extends TestCase {
  
  private IdentifierGenerator generator;


  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    generator = new ModelIdentifierGenerator();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  
  public void testEmpty() throws Exception {
    Model model = new ModelEntity();
    try {
         generator.generate(null, model);
         fail("Must throw HibernateException");
    } catch (HibernateException e) {
      assertTrue(true);
    }
    
    Manufacturer manufacturer = new ManufacturerEntity();
    model.setManufacturer(manufacturer);
    try {
        generator.generate(null, model);
        fail("Must throw HibernateException");
    } catch (HibernateException e) {
      assertTrue(true);
    }
    
    manufacturer.setExternalId("NOKIA");
    try {
        generator.generate(null, model);
        fail("Must throw HibernateException");
    } catch (HibernateException e) {
      assertTrue(true);
    }
    
    model.setManufacturerModelId("6120c");
    try {
        Serializable id = generator.generate(null, model);
        assertNotNull(id);
        assertTrue(id instanceof Long);
        assertTrue(((Long)id).longValue() > 0);
    } catch (HibernateException e) {
      fail("Not throw HibernateException");
    }
  }

  public void testHashCode1() throws Exception {
    Model m1 = new ModelEntity();
    Manufacturer ma1 = new ManufacturerEntity();
    m1.setManufacturer(ma1);
    ma1.setExternalId("NOKIA");
    m1.setManufacturerModelId("6120c");
    
    Model m2 = new ModelEntity();
    Manufacturer ma2 = new ManufacturerEntity();
    m2.setManufacturer(ma2);
    ma2.setExternalId("Nokia");
    m2.setManufacturerModelId("6120c");
    
    Model m3 = new ModelEntity();
    Manufacturer ma3 = new ManufacturerEntity();
    m3.setManufacturer(ma3);
    ma3.setExternalId("nokia");
    m3.setManufacturerModelId("6120C");
    
    Serializable id1 = generator.generate(null, m1);
    assertNotNull(id1);
    assertTrue(id1 instanceof Long);
    assertTrue(((Long)id1).longValue() > 0);
    
    Serializable id2 = generator.generate(null, m2);
    assertNotNull(id2);
    assertTrue(id2 instanceof Long);
    assertTrue(((Long)id2).longValue() > 0);
    
    Serializable id3 = generator.generate(null, m3);
    assertNotNull(id3);
    assertTrue(id3 instanceof Long);
    assertTrue(((Long)id3).longValue() > 0);
    
    assertEquals(((Long)id1).longValue(), ((Long)id2).longValue());
    assertEquals(((Long)id2).longValue(), ((Long)id3).longValue());
  }
  
  public void testHashCode2() throws Exception {
    Model m1 = new ModelEntity();
    Manufacturer ma1 = new ManufacturerEntity();
    m1.setManufacturer(ma1);
    ma1.setExternalId("NOKIA");
    m1.setManufacturerModelId("6120c");
    
    Model m2 = new ModelEntity();
    Manufacturer ma2 = new ManufacturerEntity();
    m2.setManufacturer(ma2);
    ma2.setExternalId("Motorola");
    m2.setManufacturerModelId("V8i");
    
    Model m3 = new ModelEntity();
    Manufacturer ma3 = new ManufacturerEntity();
    m3.setManufacturer(ma3);
    ma3.setExternalId("SonyEricsson");
    m3.setManufacturerModelId("W810c");
    
    Serializable id1 = generator.generate(null, m1);
    assertNotNull(id1);
    assertTrue(id1 instanceof Long);
    assertTrue(((Long)id1).longValue() > 0);
    
    Serializable id2 = generator.generate(null, m2);
    assertNotNull(id2);
    assertTrue(id2 instanceof Long);
    assertTrue(((Long)id2).longValue() > 0);
    
    Serializable id3 = generator.generate(null, m3);
    assertNotNull(id3);
    assertTrue(id3 instanceof Long);
    assertTrue(((Long)id3).longValue() > 0);
    
    assertNotSame(((Long)id1).longValue(), ((Long)id2).longValue());
    assertNotSame(((Long)id2).longValue(), ((Long)id3).longValue());
    assertNotSame(((Long)id1).longValue(), ((Long)id3).longValue());
  }
  
}
