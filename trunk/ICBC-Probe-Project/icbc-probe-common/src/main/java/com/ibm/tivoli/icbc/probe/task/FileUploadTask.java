/**
 * 
 */
package com.ibm.tivoli.icbc.probe.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.icbc.probe.ProbeException;

/**
 * @author ZhaoDongLu
 *
 */
public class FileUploadTask {
  private static Log log = LogFactory.getLog(FileUploadTask.class);
  
  private String soapAddress = null;
  
  private String sourceFile = "c:/checkresult.txt";
  private String externalCmd = null;

  private String uploadUrl = "/EBankMonitor/servlet/ClientEnvUpload";
  /**
   * 
   */
  public FileUploadTask() {
    super();
  }
  
  public String getSoapAddress() {
    return soapAddress;
  }

  public void setSoapAddress(String soapAddress) {
    this.soapAddress = soapAddress;
  }

  public String getSourceFile() {
    return sourceFile;
  }

  public void setSourceFile(String sourceFile) {
    this.sourceFile = sourceFile;
  }

  public String getExternalCmd() {
    return externalCmd;
  }

  public void setExternalCmd(String externalCmd) {
    this.externalCmd = externalCmd;
  }

  public String getUploadUrl() {
    return uploadUrl;
  }

  public void setUploadUrl(String uploadUrl) {
    this.uploadUrl = uploadUrl;
  }

  public void execute() {
    boolean upload = false;
    try {
      upload = this.uploadFile();
    } catch (Exception e) {
      log.error("fail to upload file: [" + this.sourceFile + "] to URL: " + this.uploadUrl, e);
    }
    
    try {
      this.executeCmd();
    } catch (ProbeException e) {
      log.error("fail to upload file: [" + this.sourceFile + "] to URL: " + this.uploadUrl, e);
    }
    
    if (!upload) {
      try {
        log.info("Try again bypassed upload task.");
        upload = this.uploadFile();
      } catch (Exception e) {
        log.error("fail to upload file: [" + this.sourceFile + "] to URL: " + this.uploadUrl, e);
      }
    }
    
  }
  
  private boolean uploadFile() throws ProbeException, IOException {
    File file = new File(this.sourceFile);
    if (!file.exists()) {
       log.info("File no exists:[" + file.getCanonicalPath() + ", bypass cuurent upload task.");
       return false;
    }
    String url = this.uploadUrl;
    if (this.uploadUrl.startsWith("/")) {
      String soapURL = soapAddress;
      URL u = new URL(soapURL);
      url = u.getProtocol() + "://" + u.getHost() + ":" + u.getPort() + this.uploadUrl;
    }
    PostMethod method = new PostMethod(url);
    try {
      //method.setRequestHeader("Content-Type", "text/xml;charset=UTF-8");

      method.setRequestBody(new FileInputStream(this.sourceFile));

      HttpClient client = new HttpClient();
      int statusCode = client.executeMethod(method);

      if (statusCode != HttpStatus.SC_OK) {
        log.error("Post Method failed to url: " + url + ", cause: " + method.getStatusLine());
      }
      log.info("Post Data with return code: " + statusCode);
      return true;      
    } catch (HttpException e) {
      log.error("fail to post data to url: " + url + ", cause: " + e.getMessage(), e);
      throw new ProbeException("fail to post data to url: " + url + ", cause: " + e.getMessage(), e);
    } catch (IOException e) {
      log.error("fail to post data to url: " + url + ", cause: " + e.getMessage(), e);
      throw new ProbeException("fail to post data to url: " + url + ", cause: " + e.getMessage(), e);
    } finally {
      if (method != null) {
        try {
          method.releaseConnection();
        } catch (Exception e) {
          log.error(e.getMessage(), e);
          throw new ProbeException(e.getMessage(), e);
        }
      }
    }
  }
  
  private void executeCmd() throws ProbeException {
    String cmdFile = this.externalCmd;
    try {
      if (cmdFile == null || cmdFile.trim().length() == 0) {
         // Load from classpath
         String cmdContent = IOUtils.toString(this.getClass().getResourceAsStream("/com/ibm/tivoli/icbc/probe/task/check.bat"), "GBK");
         if (StringUtils.isNotEmpty(cmdContent)) {
            File cmdFilepath = new File(System.getProperty("java.io.tmpdir").trim(), "check.bat");
            FileWriter out = new FileWriter(cmdFilepath);
            log.info("dump check.bat into file: " + cmdFilepath.getCanonicalPath());
            out.write(cmdContent);
            out.flush();
            out.close();
            cmdFile = cmdFilepath.getCanonicalPath();
         }
      }
      log.info("execute command: " + cmdFile);
      Process process = Runtime.getRuntime().exec(cmdFile);
      String outputMsg = IOUtils.toString(process.getInputStream(), "GBK");
      log.info("execute command: " + cmdFile + " with stdout :[" + outputMsg + "]");
      outputMsg = IOUtils.toString(process.getErrorStream(), "GBK");
      log.info("execute command: " + cmdFile + " with stderr :[" + outputMsg + "]");
      log.info("execute command: " + cmdFile + " return code :[" + process.exitValue() + "]");
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new ProbeException(e.getMessage(), e);
    }
  }

}
