/**
 * 
 */
package com.ibm.tivoli.icbc.probe.dns;

import java.util.Arrays;
import java.util.List;

import org.xbill.DNS.ARecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import com.ibm.tivoli.icbc.probe.DNSProbe;
import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.result.dns.DNSResult;
import com.ibm.tivoli.icbc.result.dns.ns.DNSResult4NS;
import com.ibm.tivoli.icbc.result.dns.ns.Domain;
import com.ibm.tivoli.icbc.result.dns.ns.NameServer;

/**
 * @author Zhao Dong Lu
 * 
 */
public class NSProbe extends BaseDNSProbe implements DNSProbe {

  private NameServerLookup nameServerLookup = new NameServerLookupImpl();

  public NSProbe() throws ProbeException {
    super();
  }

  /**
   * Using System default resolv to Probe CNAME and A
   * 
   * @param targets
   */
  public NSProbe(List<String> targets) throws ProbeException {
    super(targets);
  }

  public NSProbe(Resolver resolver, List<String> targets) throws ProbeException {
    super(resolver, targets);
  }

  public NSProbe(String[] targets) throws ProbeException {
    this(Arrays.asList(targets));
  }

  public NameServerLookup getNameServerLookup() {
    return nameServerLookup;
  }

  public void setNameServerLookup(NameServerLookup nameServerLookup) {
    this.nameServerLookup = nameServerLookup;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.icbc.probe.Probe#run()
   */
  public DNSResult run() throws ProbeException {
    DNSResult4NS result = new DNSResult4NS();
    for (String target : this.getTargets()) {
      Domain domain = new Domain(target);
      result.getDomains().add(domain);
      List<String> ips;
      try {
        ips = this.nameServerLookup.getNameServerAddresses(this.getResolver(), target);
      } catch (Exception e) {
        domain.setError("fail to resolv NS record: " + target + ", cause: " + e.getMessage());
        continue;
      }

      if (ips == null || ips.size() == 0) {
        domain.setError("fail to resolv NS record: " + target);
        continue;
      }
      for (String ip : ips) {
        NameServer nameServer = new NameServer(ip);
        domain.getNameServers().add(nameServer);
        nameServer.getInetAddresses().add(ip);
      }
    }
    return result;
  }

}
