package com.ibm.tivoli.icbc.probe.dns;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.xbill.DNS.ExtendedResolver;
import org.xbill.DNS.Resolver;

import com.ibm.tivoli.icbc.probe.DNSProbe;
import com.ibm.tivoli.icbc.probe.ProbeException;

public abstract class BaseDNSProbe implements DNSProbe {

  private Resolver resolver = null;
  private List<String> targets = new ArrayList<String>();

  protected BaseDNSProbe() throws ProbeException {
    super();
    try {
      this.resolver = new ExtendedResolver();
    } catch (UnknownHostException e) {
      throw new ProbeException("Could not initialize CNameProbe, cause: " + e.getMessage(), e);
    }
  }

  /**
   * Using System default resolv to Probe CNAME and A
   * @param targets
   */
  public BaseDNSProbe(List<String> targets) throws ProbeException {
    this();
    this.targets = targets;
  }

  public BaseDNSProbe(Resolver resolver, List<String> targets) throws ProbeException {
    this();
    this.resolver = resolver;
    this.targets = targets;
  }

  public Resolver getResolver() {
    return resolver;
  }

  public void setResolver(Resolver resolver) {
    this.resolver = resolver;
  }

  public List<String> getTargets() {
    return targets;
  }

  public void setTargets(List<String> targets) {
    this.targets = targets;
  }

}