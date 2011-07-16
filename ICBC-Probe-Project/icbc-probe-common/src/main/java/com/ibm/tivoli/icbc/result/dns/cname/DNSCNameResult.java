/**
 * 
 */
package com.ibm.tivoli.icbc.result.dns.cname;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.ibm.tivoli.icbc.result.dns.DNSResult;
import com.ibm.tivoli.icbc.result.dns.LookupEntity;
import com.ibm.tivoli.icbc.result.dns.ResolverEntity;
import com.thoughtworks.xstream.XStream;


/**
 * @author Zhao Dong Lu
 *
 */
public class DNSCNameResult extends DNSResult {
  
  private ResolverEntity resolverEntity = null;
  
  private List<LookupEntity> lookupEntities = new ArrayList<LookupEntity>();
  
  /**
   * 
   */
  public DNSCNameResult() {
    super();
  }

  public ResolverEntity getResolverEntity() {
    return resolverEntity;
  }

  public void setResolverEntity(ResolverEntity resolverEntity) {
    this.resolverEntity = resolverEntity;
  }

  public List<LookupEntity> getLookupEntities() {
    return lookupEntities;
  }

  public void setLookupEntities(List<LookupEntity> lookupEntities) {
    this.lookupEntities = lookupEntities;
  }
  
  public void toXML(Writer out) {
    XStream xs = getXStream();
    xs.toXML(this, out);
  }

}
