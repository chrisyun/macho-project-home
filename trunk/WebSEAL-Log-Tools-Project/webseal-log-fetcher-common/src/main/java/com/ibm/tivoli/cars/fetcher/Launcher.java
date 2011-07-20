/**
 * 
 */
package com.ibm.tivoli.cars.fetcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
  private static final String FINISHED_FLAG = ".finished";

  private WebSEALRequestLogProcessor processor;

  public static class FilePatternFilter implements FilenameFilter {
    private String filenamePattern = null;

    public FilePatternFilter(String filenamePattern) {
      super();
      this.filenamePattern = filenamePattern;
    }

    public boolean accept(File dir, String name) {
      if (name.endsWith(FINISHED_FLAG)) {
         return false;
      }
      String p = StringUtils.replace(this.filenamePattern, ".", "\\.");
      p = StringUtils.replace(p, "-", "\\-");
      p = StringUtils.replace(p, "*", ".*");
      Pattern pattern = Pattern.compile(p);
      Matcher matcher = pattern.matcher(name);
      return matcher.matches();
    }

  }

  /**
   * Charset of log file
   */
  private String charset = System.getProperty("file.encoding", "iso8859-1");
  private EventHandler eventhandler;

  private ApplicationConfigHelper applicationConfigHelper;

  /**
   * @param processor
   * @param charset
   *          logFile charset
   * @param eventUploader
   */
  public Launcher(WebSEALRequestLogProcessor processor, EventHandler eventhandler) {
    super();
    this.processor = processor;
    this.eventhandler = eventhandler;
  }

  /**
   * @param processor
   * @param charset
   *          logFile charset
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

  public void process(List<File> inputFiles, InputStream jmtInputStream, InputStream appConfigInputStream) throws IOException, FileNotFoundException,
      UnsupportedEncodingException, Exception {
    // 提取文件信息
    if (inputFiles != null) {
      for (File logFile : inputFiles) {
        Reader eventReader = new InputStreamReader(new FileInputStream(logFile), charset);
        log.info("Processing log file: [" + logFile.getCanonicalPath() + "]");
        System.out.println("[" + (new Date()) + ":]Processing log file: [" + logFile.getCanonicalPath() + "]");
        process(eventReader, jmtInputStream, appConfigInputStream);
        eventReader.close();
        log.info("Finished log file: [" + logFile.getCanonicalPath() + "]");
        System.out.println("[" + (new Date()) + ":]Finished log file: [" + logFile.getCanonicalPath() + "]");
        // Rename
        File destFile = new File(logFile.getParent(), logFile.getName() + FINISHED_FLAG);
        boolean ok = logFile.renameTo(destFile);
        log.info("Rename log file to: [" + destFile.getCanonicalPath() + "]");
        System.out.println("[" + (new Date()) + ":]Rename log file to: [" + destFile.getCanonicalPath() + "]");
      }
    } else {
      Reader eventReader = new InputStreamReader(System.in, charset);
      System.out.println("Load log from stdio: ");
      process(eventReader, jmtInputStream, appConfigInputStream);
    }
  }

  public void process(Reader eventReader, InputStream jmtInputStream, InputStream appConfigInputStream) throws IOException, Exception {
    if (applicationConfigHelper == null) {
       applicationConfigHelper = new ApplicationConfigHelper(jmtInputStream, appConfigInputStream);
    }
    processor.setApplicationConfigHelper(applicationConfigHelper);

    processor.setEventReader(eventReader);
    processor.setEventHandler(eventhandler);
    processor.process();
  }

  private static EventHandler getEventHandler(Properties props) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    String className = props.getProperty("wb.log.process.cars.class", SoapEventHandlerImpl.class.getCanonicalName());
    EventHandler handler = (EventHandler) Class.forName(className).newInstance();
    if (handler instanceof SoapEventHandlerImpl) {
      SoapEventHandlerImpl eventUploader = (SoapEventHandlerImpl) handler;
      eventUploader.setCarsServiceURL(props.getProperty("wb.log.process.cars.soap.url"));
      eventUploader.setWebSEALUrl(props.getProperty("wb.log.process.webseal.base.url"));
      eventUploader.setWebSEALInstaceId(props.getProperty("wb.log.process.webseal.instance.id"));
      eventUploader.setWebSEALNetworkId(props.getProperty("wb.log.process.webseal.network.id"));
      eventUploader.setWebSEALLocation(props.getProperty("wb.log.process.webseal.hostname"));
      eventUploader.setCarsUsername(props.getProperty("wb.log.process.cars.soap.username", null));
      eventUploader.setCarsPassword(props.getProperty("wb.log.process.cars.soap.password", null));
      return eventUploader;
    } else {
      return handler;
    }
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

  private static List<File> getLogFiles(File dir, String filePattern) throws IOException {
    
    log.info("Discovering log file in directory: [" + dir.getCanonicalPath() + "] with pasttern [" + filePattern + "]");
    System.out.println("Discovering log files in directory: [" + dir.getCanonicalPath() + "] with pasttern [" + filePattern + "]");
    if (!dir.exists()) {
      System.out.println("Log directory no exists: [" + dir.getCanonicalPath() + "]");
      System.exit(1);
    }
    FilePatternFilter filter = new FilePatternFilter(filePattern);
    File[] files = dir.listFiles(filter);
    return Arrays.asList(files);
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    if (args == null || args.length < 2) {
      System.err.println("Usage: LogProcessor -c <configFile> [-f <inputFile1> <inputFile2> ...] -d [log directory] [filename pattern]");
      System.exit(1);
    }

    List<File> inputFiles = null;
    String configFilename = null;
    String inputFilenameStr = null;
    for (int i = 0; i < args.length; i++) {
      if ("-c".equals(args[i])) {
        configFilename = args[i + 1];
      } else if ("-f".equals(args[i])) {
        inputFilenameStr = args[i + 1];
        // Get log files
        inputFiles = getLogFiles(inputFilenameStr);
      } else if ("-d".equals(args[i])) {
        File dir = new File(args[i + 1]);
        String filePattern = args[i + 2];
        inputFiles = getLogFiles(dir, filePattern);
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

    // Get Event uploader
    EventHandler eventhandler = getEventHandler(props);

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
    launcher.process(inputFiles, jmtInputStream, appConfigInputStream);
    System.exit(0);
  }


}
