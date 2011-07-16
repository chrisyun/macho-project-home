package com.ibm.tivoli.icbc.result.dns;

import java.io.Writer;
import java.net.InetAddress;
import java.util.Date;

import com.ibm.tivoli.icbc.result.Result;
import com.ibm.tivoli.icbc.result.dns.cname.DNSCNameResult;
import com.ibm.tivoli.icbc.result.dns.ns.DNSResult4NS;
import com.ibm.tivoli.icbc.result.dns.ns.Domain;
import com.ibm.tivoli.icbc.result.dns.ns.NameServer;
import com.ibm.tivoli.icbc.util.InetAddressConvertor;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public abstract class DNSResult extends Result {

  private Date beginTime = new Date();
  private Date endTime = null;

  /**
   * Create a XStream instance.
   * @return
   */
  protected static XStream getXStream() {
    XStream xstream = new XStream(new DomDriver("UTF-8"));
    xstream.alias("DNSResult", DNSCNameResult.class);
    xstream.alias("LookupEntity", LookupEntity.class);

    xstream.alias("DNSResult4NS", DNSResult4NS.class);
    xstream.alias("Domain", Domain.class);
    xstream.alias("NameServer", NameServer.class);
    xstream.alias("InetAddress", InetAddress.class);
    xstream.registerConverter(new InetAddressConvertor());

    return xstream;
  }

  public DNSResult() {
    super();
  }

  public Date getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public abstract void toXML(Writer out);

}