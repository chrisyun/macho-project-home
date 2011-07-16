/**
 * 
 */
package com.ibm.tivoli.icbc.result.dns.ns;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ibm.tivoli.icbc.result.dns.DNSResult;
import com.thoughtworks.xstream.XStream;

/**
 * @author Zhao Dong Lu
 *
 */
public class DNSResult4NS extends DNSResult {
  
  private List<Domain> domains = new ArrayList<Domain>();
  private Date timeStamp = new Date();

  /**
   * 
   */
  public DNSResult4NS() {
    super();
  }

  public List<Domain> getDomains() {
    return domains;
  }

  public void setDomains(List<Domain> domains) {
    this.domains = domains;
  }

  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  @Override
  public void toXML(Writer out) {
    XStream xs = getXStream();
    xs.toXML(this, out);
  }

}
