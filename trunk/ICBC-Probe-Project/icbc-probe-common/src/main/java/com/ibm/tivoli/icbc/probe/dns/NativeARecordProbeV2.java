package com.ibm.tivoli.icbc.probe.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import com.ibm.tivoli.icbc.probe.Probe;
import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.result.dns.a.DNSNativeAResult;

public class NativeARecordProbeV2 implements Probe<DNSNativeAResult> {
  private static Log log = LogFactory.getLog(NativeARecordProbeV2.class);
  
  private String target = null;

  public NativeARecordProbeV2() {
    super();
  }

  /**
   * @return the target
   */
  public String getTarget() {
    return target;
  }

  /**
   * @param target the target to set
   */
  public void setTarget(String target) {
    this.target = target;
  }

  @Override
  public DNSNativeAResult run() throws ProbeException {
    DNSNativeAResult result = new DNSNativeAResult();
    try {
      Lookup lookupTarget = new Lookup(this.target, Type.A);

      lookupTarget.setResolver(new SimpleResolver());
      long beginTime = System.currentTimeMillis();
      Record[] rs = lookupTarget.run();
      long end = System.currentTimeMillis();

      if (rs != null && rs.length == 1 && rs[0] instanceof ARecord) {
        ARecord ar = (ARecord)rs[0];
        result.setIpAddress(ar.getAddress().getHostAddress());
      } else {
        result.setError("Fail to resolv A record for: " + this.target);
      }
      result.setTarget(this.target);
      result.setElapseTime((int)(end - beginTime));
    } catch (UnknownHostException e) {
      result.setError("Fail to resolv A record for: " + this.target);
      log.error("Fail to resolv A record for: " + this.target, e);
    } catch (TextParseException e) {
      result.setError("Fail to resolv A record for: " + this.target);
      log.error("Fail to resolv A record for: " + this.target, e);
    }
    return result;
  }

}
