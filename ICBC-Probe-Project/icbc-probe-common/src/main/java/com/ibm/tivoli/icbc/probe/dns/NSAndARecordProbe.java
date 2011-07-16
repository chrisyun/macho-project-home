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
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import com.ibm.tivoli.icbc.probe.DNSProbe;
import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.result.dns.DNSResult;
import com.ibm.tivoli.icbc.result.dns.a.AItem;
import com.ibm.tivoli.icbc.result.dns.a.DNSResult4A;
import com.ibm.tivoli.icbc.result.dns.ns.NameServer;

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
public class NSAndARecordProbe extends BaseDNSProbe implements DNSProbe {

  private static Log log = LogFactory.getLog(NSAndARecordProbe.class);

  public NSAndARecordProbe() throws ProbeException {
    super();
  }

  /**
   * Using System default resolv to Probe CNAME and A
   * 
   * @param targets
   */
  public NSAndARecordProbe(List<String> targets) throws ProbeException {
    super(targets);
  }

  public NSAndARecordProbe(Resolver resolver, List<String> targets) throws ProbeException {
    super(resolver, targets);
  }

  public NSAndARecordProbe(String[] targets) throws ProbeException {
    this(Arrays.asList(targets));
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.icbc.probe.Probe#run()
   */
  public DNSResult run() throws ProbeException {
    try {
      DNSResult4A result = new DNSResult4A();
      for (String target : this.getTargets()) {
        Lookup lookup = new Lookup(getDomain(target), Type.NS);
        lookup.setCache(null);
        // Setup default resolver for Lookup NS record
        lookup.setResolver(this.getResolver());
        Record[] records = lookup.run();

        if (records == null) {
           result.setError("fail to resovle NS record for [" + target + "], cause: " + lookup.getErrorString());
           return result;
        }
        
        for (Record record : records) {
          if (record instanceof NSRecord) {
            NSRecord r = (NSRecord) record;
            // String domainName = r.getName().toString();
            String nameServerName = r.getTarget().toString();
            // Resolve A record for this NameServer
            Lookup lookup4A = new Lookup(nameServerName, Type.A);
            lookup.setCache(null);
            lookup.setResolver(this.getResolver());

            // Add into result set
            NameServer nameServer = new NameServer(nameServerName);

            Record[] rsa = lookup4A.run();
            if (rsa == null) {
              result.setError("fail to resovle A record for [" + nameServerName + "], cause: " + lookup4A.getErrorString());
              return result;
            }
            for (Record record4A : rsa) {
              ARecord r4a = (ARecord) record4A;
              nameServer.getInetAddresses().add(r4a.getAddress().toString());
              Lookup lookupTarget = new Lookup(target, Type.A);
              lookupTarget.setCache(null);
              try {
                lookupTarget.setResolver(new SimpleResolver(r4a.getAddress().getHostAddress()));

                Record[] rs1 = lookupTarget.run();
                if (rs1 != null) {
                  for (Record r1 : rs1) {
                    if (r1 instanceof ARecord) {
                      result.getAitems().add(new AItem(target, r4a.getAddress().getHostAddress(), ((ARecord) r1).getAddress().getHostAddress()));
                    }
                  }
                }
              } catch (UnknownHostException e) {
                log.error("Resolv error, cause: " + e.getMessage(), e);
              }
            }

            log.debug(r.toString());
            log.debug(nameServer.getInetAddresses());
          }
        }
      }
      return result;
    } catch (TextParseException e) {
      throw new ProbeException("Could not lookup NS Record, cause: " + e.getMessage(), e);
    }
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
