/**
 * 
 */
package com.ibm.tivoli.icbc;

import junit.framework.TestCase;

import org.xbill.DNS.ARecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.Type;

/**
 * @author Zhao Dong Lu
 *
 */
public class LookupTest extends TestCase {

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testCase1() throws Exception {
    Record [] records = new Lookup("icbc.com.cn", Type.NS).run();
    for (int i = 0; i < records.length; i++) {
        NSRecord rec = (NSRecord) records[i];
        
        // Lookup DNS Server A record
        Record[] rs = (new Lookup(rec.getTarget(), Type.A)).run();
        
        System.out.print(rec.toString());
        
        for (int j = 0; rs != null && j < rs.length; j++) {
            ARecord r = (ARecord)rs[j];
            System.out.print("[" + r.toString() + "] ");
            System.out.println();
            
            // Resove a hostname
            Resolver resolver = new SimpleResolver(r.getAddress().getHostAddress());
            
            Lookup myLookup = new Lookup("www.icbc.com.cn", Type.A);
            myLookup.setResolver(resolver);
            myLookup.setCache(null);
            
            long begin = System.currentTimeMillis();
            Record[] results = myLookup.run();
            long finished = System.currentTimeMillis();
            
            System.out.println("\t" + (finished - begin) + "ms, " + results);
        }
        
        
    }
  }
}
