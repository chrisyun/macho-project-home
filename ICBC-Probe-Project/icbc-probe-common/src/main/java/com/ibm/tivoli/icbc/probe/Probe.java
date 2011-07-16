/**
 * 
 */
package com.ibm.tivoli.icbc.probe;


/**
 * @author Zhao Dong Lu
 *
 */
public interface Probe<R> {
  
  public R run() throws ProbeException;
  
}
