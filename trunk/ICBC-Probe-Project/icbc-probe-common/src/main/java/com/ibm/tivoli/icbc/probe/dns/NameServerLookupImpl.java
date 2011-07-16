/**
 * 
 */
package com.ibm.tivoli.icbc.probe.dns;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.result.dns.ns.NameServer;

/**
 * @author Zhao Dong Lu
 * 
 */
public class NameServerLookupImpl implements NameServerLookup {

  private static Log log = LogFactory.getLog(NSAndARecordProbeV2.class);

  private Map<String, List<String>> cachedNSRecords = new LinkedHashMap<String, List<String>>();

  /**
   * 
   */
  public NameServerLookupImpl() {
    super();
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.icbc.probe.dns.NameServerLookup#getNameServerAddresses(org.xbill.DNS.Resolver, java.lang.String)
   */
  public synchronized List<String> getNameServerAddresses(Resolver resolver, String domain) throws TextParseException, ProbeException {
    if (!this.cachedNSRecords.containsKey(domain)) {
      List<String> result = this.lookupNameServer(resolver, domain);
      this.cachedNSRecords.put(domain, result);
    }
    return this.cachedNSRecords.get(domain);
  }

  /**
   * Name Server IP list
   * @param resolver
   * @param domain
   * @return
   * @throws ProbeException
   * @throws TextParseException
   */
  private List<String> lookupNameServer(Resolver resolver, String domain) throws ProbeException, TextParseException {
    List<String> result = new ArrayList<String>();

    Lookup lookup = new Lookup(domain, Type.NS);
    lookup.setCache(null);
    // Setup default resolver for Lookup NS record
    lookup.setResolver(resolver);
    Record[] records = lookup.run();

    if (records == null) {
      throw new ProbeException("fail to resovle NS record for [" + domain + "], cause: " + lookup.getErrorString());
    }

    for (Record record : records) {
      if (record instanceof NSRecord) {
        NSRecord r = (NSRecord) record;
        // String domainName = r.getName().toString();
        String nameServerName = r.getTarget().toString();
        // Resolve A record for this NameServer
        Lookup lookup4A = new Lookup(nameServerName, Type.A);
        lookup4A.setCache(null);
        lookup4A.setResolver(resolver);

        // Add into result set
        NameServer nameServer = new NameServer(nameServerName);

        Record[] rsa = lookup4A.run();
        if (rsa == null) {
          throw new ProbeException("fail to resovle A record for [" + nameServerName + "], cause: " + lookup4A.getErrorString());
        }
        for (Record record4A : rsa) {
          ARecord r4a = (ARecord) record4A;
          nameServer.getInetAddresses().add(r4a.getAddress().toString());
          result.add(r4a.getAddress().getHostAddress());
        }

        log.debug(r.toString());
        log.debug(nameServer.getInetAddresses());
      }
    }
    return result;
  }

  @Override
  public synchronized void clear() {
    cachedNSRecords.clear();
  }

}
