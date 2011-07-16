/**
 * 
 */
package com.ibm.tivoli.icbc.probe.task;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.icbc.probe.ProbeException;
import com.ibm.tivoli.icbc.result.ResultFormater;
import com.ibm.tivoli.icbc.ws.ProbeService;
import com.ibm.tivoli.icbc.ws.Response;

/**
 * @author Zhao Dong Lu
 * 
 */
public class SoapDataDispatcher implements DataDispatcher {

  private static Log log = LogFactory.getLog(SoapDataDispatcher.class);
  private ProbeService probeServiceFrondEnd = null;

  /**
   * 
   */
  public SoapDataDispatcher() {
    super();
  }

  public ProbeService getProbeServiceFrondEnd() {
    return probeServiceFrondEnd;
  }

  public void setProbeServiceFrondEnd(ProbeService probeServiceFrondEnd) {
    this.probeServiceFrondEnd = probeServiceFrondEnd;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.icbc.probe.task.DataDispatcher#submit(java.util.Date,
   * java.util.Collection)
   */
  @Override
  public void submit(Date date, TaskContext context, ResultFormater formatter, TaskConfig config) throws ProbeException {
    Writer writer = new StringWriter();
    formatter.format(date, context.getTaskResults().values(), writer);

    String xmlDataMessage = writer.toString();
    if (log.isDebugEnabled()) {
      log.debug("Submiting results: " + writer.toString());
    }
    
    probeServiceFrondEnd.setMaxRetry(config.getNumberOfRetry());
    probeServiceFrondEnd.setRetryInterval(config.getRetryInterval());
    
    Response response = probeServiceFrondEnd.submitXMLData(xmlDataMessage);
    if (log.isDebugEnabled()) {
      log.debug("Response code: " + response.getCode());
      log.debug("Response Task Config: " + response.getTaskConfiguration());
    }

    String taskConfigXML = response.getTaskConfiguration();
    
    
    if (!StringUtils.isEmpty(taskConfigXML)) {
      log.info("Load newer config from server, replace old config.");
      String outFilename = (String) context.getAttribute("TASK_CONFIG_URL");
      try {
        if (outFilename.startsWith("file:///")) {
           outFilename = outFilename.substring("file:///".length());
        }
        // Rename and backup old file
        File oldFile = new File(outFilename);
        String oldContent = null;
        if (oldFile.exists()) {
           oldContent = FileUtils.readFileToString(oldFile, "UTF-8");
        }
        
        if (oldContent == null || oldContent.trim().hashCode() != taskConfigXML.trim().hashCode()) {
          log.info("Task config content changed, update task config file: " + oldFile.getCanonicalPath());
          DateFormat format = new SimpleDateFormat("yyyyMMdd.HHmmss");
          FileUtils.copyFile(oldFile, new File(outFilename + "." + format.format(new Date(oldFile.lastModified()))));

          // Write new task config
          File outFile = new File(outFilename);
          FileWriter out = new FileWriter(outFile);
          out.write(taskConfigXML);
          out.flush();
          out.close();
        } else {
          log.info("Nothing changed, keep original task config file: " + oldFile.getCanonicalPath());
        }
        
      } catch (Exception e) {
        throw new ProbeException("fail to dump task config into file: [" + outFilename + "]", e); 
      }
    }
  }

}
