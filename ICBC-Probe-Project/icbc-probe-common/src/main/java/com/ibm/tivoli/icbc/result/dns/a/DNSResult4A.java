/**
 * 
 */
package com.ibm.tivoli.icbc.result.dns.a;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.ibm.tivoli.icbc.result.dns.DNSResult;
import com.thoughtworks.xstream.XStream;

/**
 * @author Zhao Dong Lu
 *
 */
public class DNSResult4A extends DNSResult {
  
  private List<AItem> aitems = new ArrayList<AItem>();
  
  private String target = new String();

  /**
   * 
   */
  public DNSResult4A() {
    super();
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public List<AItem> getAitems() {
    return aitems;
  }

  public void setAitems(List<AItem> aitems) {
    this.aitems = aitems;
  }

  @Override
  public void toXML(Writer out) {
    XStream xs = getXStream();
    xs.toXML(this, out);
  }

}
