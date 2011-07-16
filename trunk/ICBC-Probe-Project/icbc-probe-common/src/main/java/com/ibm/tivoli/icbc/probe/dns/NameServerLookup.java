package com.ibm.tivoli.icbc.probe.dns;

import java.util.List;

import org.xbill.DNS.Resolver;
import org.xbill.DNS.TextParseException;

import com.ibm.tivoli.icbc.probe.ProbeException;

public interface NameServerLookup {

  public List<String> getNameServerAddresses(Resolver resolver, String domain) throws TextParseException, ProbeException;

  public void clear();
}