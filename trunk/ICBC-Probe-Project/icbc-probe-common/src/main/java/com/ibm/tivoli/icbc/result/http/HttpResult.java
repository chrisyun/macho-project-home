/**
 * 
 */
package com.ibm.tivoli.icbc.result.http;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ibm.tivoli.icbc.result.Result;
import com.ibm.tivoli.icbc.util.InetAddressConvertor;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Zhao Dong Lu
 * 
 */
public class HttpResult extends Result {

  private Date timestamp = new Date();
  private List<TargetURL> targetURLs = new ArrayList<TargetURL>();

  /**
   * 
   */
  public HttpResult() {
    super();
  }

  public List<TargetURL> getTargetURLs() {
    return targetURLs;
  }

  public void setTargetURLs(List<TargetURL> targetURLs) {
    this.targetURLs = targetURLs;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * Create a XStream instance.
   * 
   * @return
   */
  protected static XStream getXStream() {
    XStream xstream = new XStream(new DomDriver("UTF-8"));
    xstream.alias("HttpResult", HttpResult.class);
    xstream.alias("URLAccessResult", URLAccessResult.class);
    xstream.alias("TargetURL", TargetURL.class);
    xstream.registerConverter(new InetAddressConvertor());

    return xstream;
  }

  public void toXML(Writer out) {
    XStream xs = getXStream();
    xs.toXML(this, out);
  }

}
