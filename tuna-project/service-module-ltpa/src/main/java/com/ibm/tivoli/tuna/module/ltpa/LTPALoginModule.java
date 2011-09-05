/**
 * 
 */
package com.ibm.tivoli.tuna.module.ltpa;

import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.tuna.jaas.NamePrincipal;
import com.ibm.tivoli.tuna.util.Base64;

/**
 * @author ZhaoDongLu
 * 
 */
public class LTPALoginModule implements LoginModule {

  private static Log log = LogFactory.getLog(LTPALoginModule.class);

  private byte[] secretKeys;

  // initial state
  private Subject subject;
  private CallbackHandler callbackHandler;
  private Map sharedState;
  private Map options;

  // configurable option
  private boolean debug = false;
  private byte[] ltpaPassword;
  private String ltpa3DESKey;

  // the authentication status
  private String ltpaToken = null;
  private LTPACredential ltpaCredential = null;
  private boolean succeeded = false;
  private boolean commitSucceeded = false;

  // testUser's NamePrincipal
  private NamePrincipal userPrincipal;

  /**
   * 
   */
  public LTPALoginModule() {
    super();
  }

  public LTPALoginModule(String ltpa3desKey, byte[] ltpaPassword) {
    super();
    ltpa3DESKey = ltpa3desKey;
    this.ltpaPassword = ltpaPassword;
  }

  public byte[] getLtpaPassword() {
    return ltpaPassword;
  }

  public void setLtpaPassword(byte[] ltpaPassword) {
    this.ltpaPassword = ltpaPassword;
  }

  public String getLtpa3DESKey() {
    return ltpa3DESKey;
  }

  public void setLtpa3DESKey(String ltpa3desKey) {
    ltpa3DESKey = ltpa3desKey;
  }

  /**
   * Initialize this <code>LoginModule</code>.
   * 
   * <p>
   * 
   * @param subject
   *          the <code>Subject</code> to be authenticated.
   *          <p>
   * 
   * @param callbackHandler
   *          a <code>CallbackHandler</code> for communicating with the end user
   *          (prompting for user names and passwords, for example).
   *          <p>
   * 
   * @param sharedState
   *          shared <code>LoginModule</code> state.
   *          <p>
   * 
   * @param options
   *          options specified in the login <code>Configuration</code> for this
   *          particular <code>LoginModule</code>.
   */
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {

    this.subject = subject;
    this.callbackHandler = callbackHandler;
    this.sharedState = sharedState;
    this.options = options;

    // initialize any configured options
    this.debug = "true".equalsIgnoreCase((String) options.get("debug"));
    this.ltpa3DESKey = (String) options.get("ltpa3DESKey");
    this.ltpaPassword = ((String)options.get("ltpaPassword")).getBytes();
    /*
    try {
      BeanUtils.populate(this, options);
    } catch (IllegalAccessException e) {
      log.error(String.format("Fail to populate options to LoginModule, case: %s"), e);
    } catch (InvocationTargetException e) {
      log.error(String.format("Fail to populate options to LoginModule, case: %s"), e);
    }
    */
  }

  private static String toString(String token) {
    StringBuffer buf = new StringBuffer();

    StringTokenizer st = new StringTokenizer(token, "%");
    String userInfo = st.nextToken();
    String expires = st.nextToken();
    String signature = st.nextToken();

    Date d = new Date(Long.parseLong(expires));
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss z");
    buf.append("Token is for: " + userInfo);
    buf.append('\n');
    buf.append("Token expires at: " + sdf.format(d));
    buf.append('\n');
    buf.append("\n\nFull token string : " + token);
    buf.append('\n');

    return buf.toString();
  }

  private static byte[] getSecretKey(String shared3DES, byte[] password) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException,
      IllegalBlockSizeException, BadPaddingException {
    MessageDigest md = MessageDigest.getInstance("SHA");
    md.update(password);
    byte[] hash3DES = new byte[24];
    System.arraycopy(md.digest(), 0, hash3DES, 0, 20);
    Arrays.fill(hash3DES, 20, 24, (byte) 0);
    // decrypt the real key and return it
    return decrypt(Base64.decode(shared3DES), hash3DES);
  }

  private static byte[] decryptLtpaToken(String encryptedLtpaToken, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
    final byte[] ltpaByteArray = Base64.decode(encryptedLtpaToken);
    return decrypt(ltpaByteArray, key);
  }

  private static byte[] decrypt(byte[] ciphertext, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException,
      IllegalBlockSizeException, BadPaddingException {
    final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
    final KeySpec keySpec = new DESedeKeySpec(key);
    final Key secretKey = SecretKeyFactory.getInstance("TripleDES").generateSecret(keySpec);

    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    return cipher.doFinal(ciphertext);
  }

  public boolean login() throws LoginException {
    
    Callback[] callbacks = new Callback[1];
    callbacks[0] = new LtpaTokenCallback();

    this.ltpaToken = null;
    try {
      callbackHandler.handle(callbacks);
      this.ltpaToken = ((LtpaTokenCallback) callbacks[0]).getToken();
    } catch (java.io.IOException ioe) {
      throw new LoginException(ioe.toString());
    } catch (UnsupportedCallbackException uce) {
      throw new LoginException("Error: " + uce.getCallback().toString() + " not available to garner authentication information " + "from the user");
    }

    log.debug(String.format("LTPA token: [%s]", this.ltpaToken));
    if (secretKeys == null) {
      try {
        // Takes an encrypted base64 ASCII token and decrypts it
        secretKeys = getSecretKey(ltpa3DESKey, ltpaPassword);
      } catch (InvalidKeyException e) {
        this.succeeded = false;
        this.userPrincipal = null;
        log.error("Fail to get secret key.", e);
        throw new LoginException("Fail to get secret key.");
      } catch (NoSuchAlgorithmException e) {
        this.succeeded = false;
        this.userPrincipal = null;
        log.error("Fail to get secret key.", e);
        throw new LoginException("Fail to get secret key.");
      } catch (NoSuchPaddingException e) {
        this.succeeded = false;
        this.userPrincipal = null;
        log.error("Fail to get secret key.", e);
        throw new LoginException("Fail to get secret key.");
      } catch (InvalidKeySpecException e) {
        this.succeeded = false;
        this.userPrincipal = null;
        log.error("Fail to get secret key.", e);
        throw new LoginException("Fail to get secret key.");
      } catch (IllegalBlockSizeException e) {
        this.succeeded = false;
        this.userPrincipal = null;
        log.error("Fail to get secret key.", e);
        throw new LoginException("Fail to get secret key.");
      } catch (BadPaddingException e) {
        this.succeeded = false;
        this.userPrincipal = null;
        log.error("Fail to get secret key.", e);
        throw new LoginException("Fail to get secret key.");
      } catch (Exception e) {
        this.succeeded = false;
        this.userPrincipal = null;
        log.error("Fail to get secret key.", e);
        throw new LoginException("Fail to get secret key.");
      }
    }

    // Decrypt LTPA
    String ltpaPlaintext = null;
    try {
      ltpaPlaintext = new String(decryptLtpaToken(this.ltpaToken, secretKeys));
    } catch (InvalidKeyException e) {
      this.succeeded = false;
      this.userPrincipal = null;
      log.error("Fail to decrypt token.", e);
      throw new LoginException("Fail to decrypt token.");
    } catch (NoSuchAlgorithmException e) {
      this.succeeded = false;
      this.userPrincipal = null;
      log.error("Fail to decrypt token.", e);
      throw new LoginException("Fail to decrypt token.");
    } catch (NoSuchPaddingException e) {
      this.succeeded = false;
      this.userPrincipal = null;
      log.error("Fail to decrypt token.", e);
      throw new LoginException("Fail to decrypt token.");
    } catch (InvalidKeySpecException e) {
      this.succeeded = false;
      this.userPrincipal = null;
      log.error("Fail to decrypt token.", e);
      throw new LoginException("Fail to decrypt token.");
    } catch (IllegalBlockSizeException e) {
      this.succeeded = false;
      this.userPrincipal = null;
      log.error("Fail to decrypt token.", e);
      throw new LoginException("Fail to decrypt token.");
    } catch (BadPaddingException e) {
      this.succeeded = false;
      this.userPrincipal = null;
      log.error("Fail to decrypt token.", e);
      throw new LoginException("Fail to decrypt token.");
    }

    // Parse ltpaPlaintext
    log.debug("LTPA Content: [" + toString(ltpaPlaintext) + "]");
    this.ltpaCredential = parseCredential(ltpaPlaintext);
    this.succeeded = true;
    this.userPrincipal = new NamePrincipal(this.ltpaCredential.getSubject());
    return true;
  }

  private LTPACredential parseCredential(String ltpaPlaintext) {
    LTPACredential result = new LTPACredential();
    StringTokenizer st = new StringTokenizer(ltpaPlaintext, "%");
    String userInfo = st.nextToken();
    String expires = st.nextToken();
    String signature = st.nextToken();

    Date d = new Date(Long.parseLong(expires));
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss z");

    result.setExpireTime(d);

    st = new StringTokenizer(userInfo, "/");
    // u:user\:ids.hq.unicom.local\:389
    String realm = st.nextToken();
    // Cut u:user\
    result.setRealm(realm.substring(8));
    String subject = st.nextToken();
    result.setSubject(subject);
    return result;
  }

  /**
   * <p>
   * This method is called if the LoginContext's overall authentication
   * succeeded (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL
   * LoginModules succeeded).
   * 
   * <p>
   * If this LoginModule's own authentication attempt succeeded (checked by
   * retrieving the private state saved by the <code>login</code> method), then
   * this method associates a <code>NamePrincipal</code> with the
   * <code>Subject</code> located in the <code>LoginModule</code>. If this
   * LoginModule's own authentication attempted failed, then this method removes
   * any state that was originally saved.
   * 
   * <p>
   * 
   * @exception LoginException
   *              if the commit fails.
   * 
   * @return true if this LoginModule's own login and commit attempts succeeded,
   *         or false otherwise.
   */
  public boolean commit() throws LoginException {
    if (succeeded == false) {
      return false;
    } else {
      // add a Principal (authenticated identity)
      // to the Subject

      // assume the user we authenticated is the NamePrincipal
      if (!subject.getPrincipals().contains(userPrincipal))
        subject.getPrincipals().add(userPrincipal);

      if (debug) {
        log.info("\t\t[SampleLoginModule] " + "added NamePrincipal to Subject");
      }

      commitSucceeded = true;
      return true;
    }
  }

  /**
   * <p>
   * This method is called if the LoginContext's overall authentication failed.
   * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules did
   * not succeed).
   * 
   * <p>
   * If this LoginModule's own authentication attempt succeeded (checked by
   * retrieving the private state saved by the <code>login</code> and
   * <code>commit</code> methods), then this method cleans up any state that was
   * originally saved.
   * 
   * <p>
   * 
   * @exception LoginException
   *              if the abort fails.
   * 
   * @return false if this LoginModule's own login and/or commit attempts
   *         failed, and true otherwise.
   */
  public boolean abort() throws LoginException {
    if (succeeded == false) {
      return false;
    } else if (succeeded == true && commitSucceeded == false) {
      // login succeeded but overall authentication failed
      succeeded = false;
      userPrincipal = null;
    } else {
      // overall authentication succeeded and commit succeeded,
      // but someone else's commit failed
      logout();
    }
    return true;
  }

  /**
   * Logout the user.
   * 
   * <p>
   * This method removes the <code>NamePrincipal</code> that was added by the
   * <code>commit</code> method.
   * 
   * <p>
   * 
   * @exception LoginException
   *              if the logout fails.
   * 
   * @return true in all cases since this <code>LoginModule</code> should not be
   *         ignored.
   */
  public boolean logout() throws LoginException {

    subject.getPrincipals().remove(userPrincipal);
    succeeded = false;
    succeeded = commitSucceeded;
    userPrincipal = null;
    return true;
  }
}
