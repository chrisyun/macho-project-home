/**
 * 
 */
package com.ibm.tivoli.icbc.probe.task;

import java.util.Date;

import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.result.ResultFormater;

/**
 * @author Zhao Dong Lu
 *
 */
public interface DataDispatcher {

  void submit(Date date, TaskContext context, ResultFormater formatter, TaskConfig config) throws ProbeException;

}
