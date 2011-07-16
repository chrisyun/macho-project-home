/**
 * 
 */
package com.ibm.tivoli.icbc.probe.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.AccessControlException;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

import com.ibm.tivoli.icbc.probe.ProbeException;

/**
 * @author Zhao Dong Lu
 * 
 */
public class ProbeServer {

  private static Log log = LogFactory.getLog(ProbeServer.class);

  /**
   * A random number generator that is <strong>only</strong> used if the
   * shutdown command string is longer than 1024 characters.
   */
  private Random random = null;

  private int port = 11050;

  private String shutdown = "SHUTDOWN";

  private boolean started;

  private ApplicationContext context;

  /**
   * 
   */
  public ProbeServer() {
    super();
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getShutdown() {
    return shutdown;
  }

  public void setShutdown(String shutdown) {
    this.shutdown = shutdown;
  }

  public void start() throws ProbeException {
    log.info("Starting Probe Daemon ...");
    try {
      context = new ClassPathXmlApplicationContext("classpath*:com/ibm/tivoli/icbc/spring/mainBean.xml");
    } catch (BeansException e) {
      if (StringUtils.isNotEmpty(System.getProperty("icbc.probe.home"))) {
         File file = new File(System.getProperty("icbc.probe.home"), "config/com/ibm/tivoli/icbc/spring/mainBean.xml");
         try {
          //context = new XmlBeanFactory(new FileSystemResource("/tmp/icbc-probe-0.0.1-SNAPSHOT/config/com/ibm/tivoli/icbc/spring/mainBean.xml"));
          //("file://tmp/icbc-probe-0.0.1-SNAPSHOT/config/com/ibm/tivoli/icbc/spring/mainBean.xml");
        } catch (BeansException e1) {
          throw new ProbeException(e.getMessage(), e1);
        } catch (Exception e1) {
          throw new ProbeException(e.getMessage(), e1);
        }
      }
      throw e;
    } catch (Exception e) {
      throw new ProbeException(e.getMessage(), e);
    }
    this.started = true;
  }

  public void stop() throws ProbeException {
    // Validate and update our current component state
    if (!started)
      throw new ProbeException("standardServer.stop.notStarted");

    log.info("Stopping Probe Daemon ...");
    started = false;

  }

  /**
   * Wait until a proper shutdown command is received, then return.
   */
  public void await() {
    // Set up a server socket to wait on
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
      if (log.isDebugEnabled())
        log.debug("Listening shutdown port#" + port);
    } catch (IOException e) {
      log.fatal("Create Shutdown Port[" + port + "]: ", e);
      System.exit(1);
    }

    // Loop waiting for a connection and a valid command
    while (true) {

      // Wait for the next connection
      Socket socket = null;
      InputStream stream = null;
      try {
        socket = serverSocket.accept();
        socket.setSoTimeout(10 * 1000); // Ten seconds
        stream = socket.getInputStream();
      } catch (AccessControlException ace) {
        log.fatal("StandardServer.accept security exception: ", ace);
        continue;
      } catch (IOException e) {
        log.fatal("StandardServer.accept exception: ", e);
        System.exit(1);
      }

      // Read a set of characters from the socket
      StringBuffer command = new StringBuffer();
      int expected = 1024; // Cut off to avoid DoS attack
      while (expected < this.getShutdown().length()) {
        if (random == null)
          random = new Random(System.currentTimeMillis());
        expected += (random.nextInt() % 1024);
      }
      while (expected > 0) {
        int ch = -1;
        try {
          ch = stream.read();
        } catch (IOException e) {
          log.fatal("StandardServer.await read: ", e);
          e.printStackTrace();
          ch = -1;
        }
        if (ch < 32) // Control character or EOF terminates loop
          break;
        command.append((char) ch);
        expected--;
      }

      // Close the socket now that we are done with it
      try {
        socket.close();
      } catch (IOException e) {
        ;
      }

      // Match against our command string
      boolean match = command.toString().equals(this.getShutdown());
      if (match) {
        break;
      } else
        log.fatal("StandardServer.await: Invalid command '" + command.toString() + "' received");

    }

    // Close the server socket and return
    try {
      serverSocket.close();
      System.exit(0);
    } catch (IOException e) {
      ;
    }
  }
  
  public static void main(String[] args) throws Exception {
    ProbeServer server = new ProbeServer();
    server.start();
    server.await();
  }
}
