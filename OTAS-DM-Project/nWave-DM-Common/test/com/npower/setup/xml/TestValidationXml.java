package com.npower.setup.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import junit.framework.TestCase;

import org.xml.sax.SAXException;

public class TestValidationXml extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testValidationSetupConfig() throws SAXException, IOException {
    SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
    URL xsdfile = this.getClass().getClassLoader().getResource("xsdfile/setup.config.xsd");

    Schema schema = factory.newSchema(xsdfile);
    Validator validator = schema.newValidator();
    
    InputStream xmlfile = this.getClass().getClassLoader().getResourceAsStream("setup/manufacturers/nokia.xml");
    Source source = new StreamSource(xmlfile);
    try {
        validator.validate(source);
        assertTrue(true);
    }
    catch (SAXException ex) {
        assertTrue(false);
    } 
  }
}
