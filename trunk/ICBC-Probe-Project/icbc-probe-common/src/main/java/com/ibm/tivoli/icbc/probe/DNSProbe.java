/**
 * 
 */
package com.ibm.tivoli.icbc.probe;

import com.ibm.tivoli.icbc.result.dns.DNSResult;

/**
 * @author Zhao Dong Lu
 *
 */
public interface DNSProbe extends Probe<DNSResult>{

  /**
   * Run probe
   * @throws ProbeException
   */
  public abstract DNSResult run() throws ProbeException;
}
