package com.ibm.tivoli.icbc.probe.dns;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.icbc.probe.Probe;
import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.result.dns.a.DNSNativeAResult;

public class NativeARecordProbe implements Probe<DNSNativeAResult> {
  private static Log log = LogFactory.getLog(NativeARecordProbe.class);
  
  private String target = null;

  public NativeARecordProbe() {
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
      long beginTime = System.currentTimeMillis();
      InetAddress ip = InetAddress.getByName(this.target);
      result.setIpAddress(ip.getHostAddress());
      result.setTarget(this.target);
      result.setElapseTime((int)(System.currentTimeMillis() - beginTime));
    } catch (UnknownHostException e) {
      result.setError("Fail to resolv A record for: " + this.target);
      log.error("Fail to resolv A record for: " + this.target, e);
    }
    return result;
  }

}
