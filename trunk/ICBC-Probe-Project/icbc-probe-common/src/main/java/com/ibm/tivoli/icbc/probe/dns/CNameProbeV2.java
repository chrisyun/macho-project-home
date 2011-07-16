/**
 * 
 */
package com.ibm.tivoli.icbc.probe.dns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xbill.DNS.CNAMERecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import com.ibm.tivoli.icbc.probe.DNSProbe;
import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.result.dns.DNSResult;
import com.ibm.tivoli.icbc.result.dns.LookupEntity;
import com.ibm.tivoli.icbc.result.dns.cname.DNSCNameResult;

/**
 * @author Zhao Dong Lu
 *
 */
public class CNameProbeV2 extends BaseDNSProbe implements DNSProbe {
  
  private static Log log = LogFactory.getLog(CNameProbeV2.class);
  
  public CNameProbeV2() throws ProbeException {
    super();
  }

  /**
   * Using System default resolv to Probe CNAME and A
   * @param targets
   */
  public CNameProbeV2(List<String> targets) throws ProbeException {
    super(targets);
  }

  public CNameProbeV2(Resolver resolver, List<String> targets) throws ProbeException {
    super(resolver, targets);
  }

  public CNameProbeV2(String[] targets) throws ProbeException {
    this(Arrays.asList(targets));
  }

  /* (non-Javadoc)
   * @see com.ibm.tivoli.icbc.probe.Probe#run()
   */
  public DNSResult run() throws ProbeException {
    DNSCNameResult result = new DNSCNameResult();
    try {
      for (String target: this.getTargets()) {
          Lookup lookup = new Lookup(target, Type.A);
          lookup.setCache(null);
          lookup.setResolver(this.getResolver());
          
          // Save to DNS Result
          LookupEntity entity = new LookupEntity(target, Type.A, this.getResolver());
          result.getLookupEntities().add(entity);
          
          lookup(0, lookup, entity);
      }
      return result;
    } catch (TextParseException e) {
      throw new ProbeException("Could not lookup CNAME Record, cause: " + e.getMessage(), e);
    }
  }

  private void lookup(int level, Lookup lookup, LookupEntity entity) throws ProbeException {
    entity.setBeginTime(new Date());
    Record[] records = lookup.run();
    entity.setEndTime(new Date());
    
    if (records == null) {
      entity.setError("fail to resolv: " + lookup.getResult() + ", cause: " + lookup.getErrorString());
      return;
    }
    List<CNAMERecord> cnameRecords = lookup.getCnames();
    
    List<Record> result = new ArrayList<Record>();
    result.addAll(cnameRecords);
    result.addAll(Arrays.asList(records));
    
    for (Record record: result) {
      // Save to DNS Result
      entity.getResult().add(record);
      
      for (int i = 0; i < level; i++) {
        System.out.print("\t");
      }
      System.out.println(record.toString());
      if (record instanceof CNAMERecord) {
        Name target2 = ((CNAMERecord)record).getTarget();
        LookupEntity entity2 = new LookupEntity(target2.toString(), Type.ANY, this.getResolver());
        entity.getNextLookups().put(record, entity2);
      }
    }
  }

}
