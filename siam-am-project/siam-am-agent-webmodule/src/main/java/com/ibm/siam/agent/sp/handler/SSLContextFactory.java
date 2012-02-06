package com.ibm.siam.agent.sp.handler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * Factory to create a bougus SSLContext.
 * 
 * @author The Apache Directory Project (mina-dev@directory.apache.org)
 * @version $Rev: 555855 $, $Date: 2007-07-13 12:19:00 +0900 (Fri, 13 Jul 2007)
 *          $
 */
public class SSLContextFactory {

  private static Log log = LogFactory.getLog(SSLContextFactory.class);

  private static SSLContext serverInstance = null;

  private static SSLContext clientInstance = null;

  /**
   * Get SSLContext singleton.
   * 
   * @return SSLContext
   * @throws java.security.GeneralSecurityException
   * 
   */
  public static SSLContext getServerSSLContext(String protocol,
      String keyManagerAlgorithm, String keyStorePath, String keyStoreType,
      char[] keyStorePassword, char[] keyPassword, String trustedStorePath,
      String trustedStoreType, char[] trustedStorePassword)
      throws GeneralSecurityException {
    SSLContext retInstance = null;
    synchronized (SSLContextFactory.class) {
      if (serverInstance == null) {
        try {
          // Init Key Manager
          KeyManager[] keyMgrs = initKeyManagers(keyManagerAlgorithm,
              keyStoreType, keyStorePath, keyStorePassword, keyPassword);
          // Init trust managers
          TrustManager[] trustManagers = initTrustManagers(trustedStorePath,
              trustedStoreType, trustedStorePassword);

          SSLContext sslContext = SSLContext.getInstance(protocol);
          sslContext.init(keyMgrs, trustManagers, null);
          serverInstance = sslContext;
        } catch (Exception ioe) {
          throw new GeneralSecurityException("Can't create Server SSLContext:"
              + ioe);
        }
      }
    }
    retInstance = serverInstance;
    return retInstance;
  }

  /**
   * Get SSLContext singleton.
   * 
   * @return SSLContext
   * @throws java.security.GeneralSecurityException
   * 
   */
  public static SSLContext getClientSSLContext(String protocol)
      throws GeneralSecurityException {
    SSLContext retInstance = null;
    synchronized (SSLContextFactory.class) {
      if (clientInstance == null) {
        clientInstance = createClientSSLContext(protocol);
      }
    }
    retInstance = clientInstance;
    return retInstance;
  }

  private static TrustManager[] initTrustManagers(String trustCertsStorePath,
      String trustManagerAlgorithm, char[] trustCertsStorePassword)
      throws KeyStoreException, IOException, NoSuchAlgorithmException,
      CertificateException {
    // Initialize trust manager factory and set trusted CA list using keystore
    if (StringUtils.isEmpty(trustCertsStorePath)) {
      // Unset trustCertsStorePath, disable trust manager
      log.info("Unset [trustCertsStorePath] parameter, disable TrustManager");
      TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
          return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {
          log.debug(String.format("Client certs;[%s], authType: [%s]", certs,
              authType));
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
          log.debug(String.format("Client certs;[%s], authType: [%s]", certs,
              authType));
        }
      } };
      return trustAllCerts;
    } else {
      log.info(String.format("Loading trust certs from store: [%s]",
          trustCertsStorePath));
      // Load key store
      KeyStore keystore = KeyStore.getInstance("JKS");
      InputStream in = getResourceAsStream(SSLContextFactory.class,
          trustCertsStorePath);
      if (in == null) {
        throw new IOException(String.format("Could not reading from : [%s]",
            trustCertsStorePath));
      }
      keystore.load(in, trustCertsStorePassword);

      TrustManagerFactory tmf = TrustManagerFactory
          .getInstance(trustManagerAlgorithm);
      tmf.init(keystore);
      TrustManager[] trustManagers = tmf.getTrustManagers();
      return trustManagers;
    }
  }

  /**
   * 根据路径加载资源.
   * path允许使用"classpath:"或"file:", 如果未使用上述前缀, 则按照Classpath进行加载.<br/>
   * 例如: 
   * classpath:/certs/server_pwd_importkey.jks, 表示从classpath中加载相关资源<br/>
   * file:/home/saml/conf/certs/client_pwd_importkey.jks, 表示从文件系统加载相关资源.<br/>
   * /certs/saml/conf/certs/client_pwd_importkey.jks, 表示从classpath中加载相关资源.<br/>
   * 
   * @param clazz
   * @param path
   * @return
   * @throws FileNotFoundException
   */
  public static InputStream getResourceAsStream(Class clazz, String path) throws FileNotFoundException {
    if (path == null) {
      return null;
    }
    if (path.startsWith("file:")) {
      String realPath = path.substring("file:".length());
      return new FileInputStream(realPath);
    } else if (path.startsWith("classpath:")) {
      String realPath = path.substring("classpath:".length());
      return clazz.getResourceAsStream(realPath);
    } else {
      InputStream in = clazz.getResourceAsStream(path);
      return in;
    }
  }

  /**
   * @param keyManagerFactoryAlgorithm
   * @param keyStore
   * @param keyPassword
   * @return
   * @throws NoSuchAlgorithmException
   * @throws KeyStoreException
   * @throws UnrecoverableKeyException
   * @throws IOException
   * @throws CertificateException
   */
  private static KeyManager[] initKeyManagers(String keyManagerAlgorithm,
      String keyStoreType, String keyStorePath, char[] storePassword,
      char[] keyPassword) throws NoSuchAlgorithmException, KeyStoreException,
      UnrecoverableKeyException, IOException, CertificateException {
    if (StringUtils.isEmpty(keyStorePath)) {
      log.info("Unset [keyStorePath], disable local private and certificate.");
      return null;
    } else {
      log.info(String.format(
          "Loading private key and certificate from store: [%s]", keyStorePath));
      // Set up key manager factory to use our key store
      // Load KetStore
      KeyStore ks = KeyStore.getInstance(keyStoreType);
      log.info(String.format(
          "Loading SSL private key from KeyStore: [%s], type: [%s]",
          keyStorePath, keyStoreType));
      InputStream in = null;
      try {
        in = getResourceAsStream(SSLContextFactory.class, keyStorePath);
        if (in == null) {
          throw new IOException(String.format("Could not reading from : [%s]",
              keyStorePath));
        }
        ks.load(in, storePassword);
      } finally {
        if (in != null) {
          try {
            in.close();
          } catch (IOException ignored) {
            log.warn("Ignored error, cause: " + ignored);
          }
        }
      }

      KeyManagerFactory kmf = KeyManagerFactory
          .getInstance(keyManagerAlgorithm);
      kmf.init(ks, keyPassword);
      return kmf.getKeyManagers();
    }
  }

  private static SSLContext createClientSSLContext(String protocol)
      throws GeneralSecurityException {
    SSLContext context = SSLContext.getInstance(protocol);
    context.init(null, AllTrustedManagerFactory.X509_MANAGERS, null);
    return context;
  }

}
