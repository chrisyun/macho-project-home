package com.ibm.tivoli.icbc.result;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.Date;

import com.ibm.tivoli.icbc.probe.ProbeException;

public interface ResultFormater {

  public abstract void format(Date timestamp, Collection<Result> results, OutputStream out, String charset) throws ProbeException;

  public abstract void format(Date timestamp, Collection<Result> results, Writer writer) throws ProbeException;

  public abstract void format(Date timestamp, Result[] results, OutputStream out, String charset) throws ProbeException;

  /**
   * Output to ICBC XML format
   * 
   * @param timestamp
   * @param results
   * @param writer
   * @throws ProbeException
   */
  public abstract void format(Date timestamp, Result[] results, Writer writer) throws ProbeException;

}