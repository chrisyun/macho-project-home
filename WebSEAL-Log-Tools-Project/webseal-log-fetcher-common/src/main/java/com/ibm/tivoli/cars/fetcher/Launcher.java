/**
 * 
 */
package com.ibm.tivoli.cars.fetcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cars.WebSEALRequestLogProcessor;
import com.ibm.tivoli.cars.entity.ApplicationConfigHelper;
import com.ibm.tivoli.cars.handler.EventHandler;
import com.ibm.tivoli.cars.handler.SoapEventHandlerImpl;

/**
 * @author zhaodonglu
 * 
 */
public class Launcher {

  private static Log log = LogFactory.getLog(Launcher.class);

  private WebSEALRequestLogProcessor processor;
  /**
   * Charset of log file
   */
  private String charset = System.getProperty("file.encoding", "iso8859-1");
  private EventHandler eventhandler;

  /**
   * @param processor
   * @param charset  logFile charset
   * @param eventUploader
   */
  public Launcher(WebSEALRequestLogProcessor processor, EventHandler eventhandler) {
    super();
    this.processor = processor;
    this.eventhandler = eventhandler;
  }

  /**
   * @param processor
   * @param charset  logFile charset
   * @param eventUploader
   */
  public Launcher(WebSEALRequestLogProcessor processor, EventHandler eventhandler, String logFileCharset) {
    super();
    this.processor = processor;
    this.eventhandler = eventhandler;
    this.charset = logFileCharset;
  }

  /**
   * 
   */
  public Launcher() {
    super();
  }

  public void process(File[] inputFiles, InputStream jmtInputStream, InputStream appConfigInputStream) throws IOException, FileNotFoundException, UnsupportedEncodingException, Exception {
    // 提取文件信息
    if (inputFiles != null && inputFiles.length > 0) {
       for (File logFile: inputFiles) {
         Reader eventReader = new InputStreamReader(new FileInputStream(logFile), charset);
         log.info("Processing log file: [" + logFile.getCanonicalPath() + "]");
         process(eventReader, jmtInputStream, appConfigInputStream);
         log.info("Finished log file: [" + logFile.getCanonicalPath() + "]");
       }
    } else {
      Reader eventReader = new InputStreamReader(System.in, charset);
      System.out.println("Load log from stdio: ");
      process(eventReader, jmtInputStream, appConfigInputStream);
    }   
  }

  public void process(Reader eventReader, InputStream jmtInputStream, InputStream appConfigInputStream) throws IOException, Exception {
    ApplicationConfigHelper applicationConfigHelper = new ApplicationConfigHelper(jmtInputStream, appConfigInputStream);
    processor.setApplicationConfigHelper(applicationConfigHelper);

    processor.setEventReader(eventReader);
    processor.setEventHandler(eventhandler);
    processor.process();
  }
  

  private static SoapEventHandlerImpl getEventHandler(Properties props) {
    SoapEventHandlerImpl eventUploader = new SoapEventHandlerImpl();
    eventUploader.setCarsServiceURL(props.getProperty("wb.log.process.cars.soap.url"));
    eventUploader.setWebSEALUrl(props.getProperty("wb.log.process.webseal.base.url"));
    eventUploader.setWebSEALInstaceId(props.getProperty("wb.log.process.webseal.instance.id"));
    eventUploader.setWebSEALNetworkId(props.getProperty("wb.log.process.webseal.network.id"));
    eventUploader.setWebSEALLocation(props.getProperty("wb.log.process.webseal.hostname"));
    eventUploader.setCarsUsername(props.getProperty("wb.log.process.cars.soap.username", null));
    eventUploader.setCarsPassword(props.getProperty("wb.log.process.cars.soap.password", null));
    return eventUploader;
  }

  private static List<File> getLogFiles(String inputFilenameStr) {
    List<File> inputFiles = new ArrayList<File>();
    if (inputFilenameStr != null && inputFilenameStr.trim().length() > 0) {
      String[] filenames = StringUtils.split(inputFilenameStr, " ");
      for (String inputFilename : filenames) {
        File inputFile = new File(inputFilename);
        if (inputFile.exists()) {
          inputFiles.add(inputFile);
        }
      }
    }
    return inputFiles;
  }

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    if (args == null || args.length < 2) {
      System.err.println("Usage: LogProcessor -c <configFile> [-f <inputFile1> <inputFile2> ...] -d [log directory] [filename pattern]");
      System.exit(1);
    }

    String configFilename = null;
    String inputFilenameStr = null;
    for (int i = 0; i < args.length; i++) {
      if ("-c".equals(args[i])) {
        configFilename = args[i + 1];
      } else if ("-f".equals(args[i])) {
        inputFilenameStr = args[i + 1];
      }
    }

    if (configFilename == null || configFilename.trim().length() == 0) {
      System.err.println("Missing config file");
      System.exit(1);
    }

    File configFile = new File(configFilename);
    if (!configFile.exists()) {
      System.err.println("Missing config file: " + configFile.getCanonicalPath());
      System.exit(1);
    }

    Properties props = new Properties(System.getProperties());
    props.load(new FileInputStream(configFile));
    System.out.println("Load properties from: [" + configFile.getCanonicalPath() + "], with properties: " + props.toString());
    log.info("Load properties from: [" + configFile.getCanonicalPath() + "], with properties: " + props.toString());
    
    // Get log files
    List<File> inputFiles = getLogFiles(inputFilenameStr);

    // Get Event uploader
    SoapEventHandlerImpl eventhandler = getEventHandler(props);

    String charset = props.getProperty("wb.log.process.log.charset", System.getProperty("file.encoding", "iso8859-1"));
    log.info("Using WebSEAL request log file charset: " + charset);
    // Load JMT
    File jmtFile = new File(props.getProperty("wb.log.process.webseal.jmt.file"));
    log.info("JMT config path: [" + jmtFile.getCanonicalPath() + "]");
    InputStream jmtInputStream = new FileInputStream(jmtFile);
    
    // Load Application and action definition
    File appCfgFile = new File(props.getProperty("wb.log.process.webseal.app.def.file"));
    log.info("App DEF path: [" + appCfgFile.getCanonicalPath() + "]");
    InputStream appConfigInputStream = new FileInputStream(appCfgFile);

    // Launch Log processor
    Launcher launcher = new Launcher(new WebSEALRequestLogProcessor(), eventhandler, charset);
    launcher.process(inputFiles.toArray(new File[0]), jmtInputStream, appConfigInputStream);
    System.exit(0);
  }
}
