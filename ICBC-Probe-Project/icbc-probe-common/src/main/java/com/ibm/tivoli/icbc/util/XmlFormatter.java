package com.ibm.tivoli.icbc.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Pretty-prints xml, supplied as a string.
 * <p/>
 * eg. <code>
 * String formattedXml = new XmlFormatter().format("<tag><nested>hello</nested></tag>");
 * </code>
 */
public class XmlFormatter {
  private static Log log = LogFactory.getLog(XmlFormatter.class);

  public XmlFormatter() {
  }

  public static String format(String unformattedXml) {
    try {
      Source xmlInput = new StreamSource(new StringReader(unformattedXml));
      StringWriter stringWriter = new StringWriter();
      StreamResult xmlOutput = new StreamResult(stringWriter);
      Transformer transformer = TransformerFactory.newInstance().newTransformer(); 
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(2));
      transformer.transform(xmlInput, xmlOutput);
      return xmlOutput.getWriter().toString();
    } catch (Throwable e) {
      log.warn("fail to format XML: " + unformattedXml);
      return unformattedXml;
    }
  }

}
