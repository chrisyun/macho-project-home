/**
 * 
 */
package com.ibm.tivoli.icbc.probe.dns;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.Type;

import com.ibm.tivoli.icbc.probe.DNSProbe;
import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.result.dns.DNSResult;
import com.ibm.tivoli.icbc.result.dns.a.AItem;
import com.ibm.tivoli.icbc.result.dns.a.DNSResult4A;

/**
 * <pre>
 * Resolve target hostname, procedure is:
 * 1. Split domain name from target hostname
 * 2. Resolve NS Record, and get all of name server for domain name
 * 3. For each name server, resolve A record of target hostname
 * </pre>
 * 
 * @author Zhao Dong Lu
 * 
 */
public class NSAndARecordProbeV2 extends BaseDNSProbe implements DNSProbe {

  private static Log log = LogFactory.getLog(NSAndARecordProbeV2.class);

  private NameServerLookup nameServerLookup = new NameServerLookupImpl();

  public NSAndARecordProbeV2() throws ProbeException {
    super();
  }

  public NameServerLookup getNameServerLookup() {
    return nameServerLookup;
  }

  public void setNameServerLookup(NameServerLookup nameServerLookup) {
    this.nameServerLookup = nameServerLookup;
  }

  /**
   * Using System default resolv to Probe CNAME and A
   * 
   * @param targets
   */
  public NSAndARecordProbeV2(List<String> targets) throws ProbeException {
    super(targets);
  }

  public NSAndARecordProbeV2(Resolver resolver, List<String> targets) throws ProbeException {
    super(resolver, targets);
  }

  public NSAndARecordProbeV2(String[] targets) throws ProbeException {
    this(Arrays.asList(targets));
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.icbc.probe.Probe#run()
   */
  public DNSResult run() throws ProbeException {
    DNSResult4A result = new DNSResult4A();
    for (String target : this.getTargets()) {
      try {
        result.setTarget(target);
        String domain = getDomain(target);
        List<String> nameServerAddresses = this.nameServerLookup.getNameServerAddresses(this.getResolver(), domain);

        if (nameServerAddresses == null || nameServerAddresses.size() == 0) {
          result.setError("Fail to resolv name server for domain: " + domain);
          return result;
        } else {
          for (String nameServerAddress : nameServerAddresses) {
            Lookup lookupTarget = new Lookup(target, Type.A);
            lookupTarget.setCache(null);
            
            AItem aitem = new AItem(target, nameServerAddress);
            try {
              lookupTarget.setResolver(new SimpleResolver(nameServerAddress));

              Record[] rs1 = lookupTarget.run();
              if (rs1 != null && rs1.length > 0) {
                for (Record r1 : rs1) {
                  if (r1 instanceof ARecord) {
                    aitem = new AItem(target, nameServerAddress, ((ARecord) r1).getAddress().getHostAddress());
                  }
                }
              } else {
                throw new UnknownHostException("could not get response from name server: " + nameServerAddress + ", cause: " + lookupTarget.getErrorString());
              }
            } catch (UnknownHostException e) {
              log.error("Resolv error, cause: " + e.getMessage(), e);
              aitem.setError(e.getMessage());
            } finally {
              result.getAitems().add(aitem);
            }
          }
        }
      } catch (Exception e) {
        log.error("Could not lookup NS Record, cause: " + e.getMessage(), e);
        result.setError(e.getMessage());
      }
    }
    
    return result;
  }

  private String getDomain(String target) {
    if (StringUtils.isEmpty(target)) {
      return null;
    }
    int index = target.indexOf(".");
    if (index <= 0) {
      return null;
    }
    return target.substring(index + 1, target.length());
  }

}
