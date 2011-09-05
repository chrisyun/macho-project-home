/**
 * 
 */
package com.ibm.tivoli.tuna.service;

/**
 * @author zhaodonglu
 *
 */
public interface RequesterAware {

  public abstract void setRequester(Requester request);
}
