/**
 * 
 */
package com.ibm.tivoli.icbc.result.dns;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;

/**
 * @author Zhao Dong Lu
 * 
 */
public class LookupEntity {

  private String lookupTarget = null;
  private String lookupType = null;
  private ResolverEntity resolverEntity = null;
  private Date beginTime = new Date();
  private Date endTime = null;

  private List<Record> result = new ArrayList<Record>();

  private Map<Record, LookupEntity> nextLookups = new LinkedHashMap<Record, LookupEntity>();
  
  private String error = null;

  /**
   * 
   */
  public LookupEntity() {
    super();
  }

  public LookupEntity(String target, int type, Resolver resolver) {
    this.lookupTarget = target;
    this.lookupType = Integer.toString(type);
    if (resolver instanceof SimpleResolver) {
       this.resolverEntity = new ResolverEntity(((SimpleResolver)resolver).toString());
    }
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getLookupTarget() {
    return lookupTarget;
  }

  public void setLookupTarget(String lookupTarget) {
    this.lookupTarget = lookupTarget;
  }

  public String getLookupType() {
    return lookupType;
  }

  public void setLookupType(String lookupType) {
    this.lookupType = lookupType;
  }

  public List<Record> getResult() {
    return result;
  }

  public void setResult(List<Record> result) {
    this.result = result;
  }

  public Map<Record, LookupEntity> getNextLookups() {
    return nextLookups;
  }

  public void setNextLookups(Map<Record, LookupEntity> nextLookups) {
    this.nextLookups = nextLookups;
  }

  public ResolverEntity getResolverEntity() {
    return resolverEntity;
  }

  public void setResolverEntity(ResolverEntity resolverEntity) {
    this.resolverEntity = resolverEntity;
  }

  public Date getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

}
