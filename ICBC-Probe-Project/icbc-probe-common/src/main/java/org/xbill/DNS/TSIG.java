// Copyright (c) 1999-2004 Brian Wellington (bwelling@xbill.org)

package org.xbill.DNS;

import java.util.Date;

import org.xbill.DNS.utils.HMAC;
import org.xbill.DNS.utils.base64;

/**
 * Transaction signature handling. This class generates and verifies TSIG
 * records on messages, which provide transaction security.
 * 
 * @see TSIGRecord
 * 
 * @author Brian Wellington
 */

public class TSIG {

  private static final String HMAC_MD5_STR = "HMAC-MD5.SIG-ALG.REG.INT.";
  private static final String HMAC_SHA1_STR = "hmac-sha1.";
  private static final String HMAC_SHA256_STR = "hmac-sha256.";

  /** The domain name representing the HMAC-MD5 algorithm. */
  public static final Name HMAC_MD5 = Name.fromConstantString(HMAC_MD5_STR);

  /** The domain name representing the HMAC-MD5 algorithm (deprecated). */
  public static final Name HMAC = HMAC_MD5;

  /** The domain name representing the HMAC-SHA1 algorithm. */
  public static final Name HMAC_SHA1 = Name.fromConstantString(HMAC_SHA1_STR);

  /** The domain name representing the HMAC-SHA256 algorithm. */
  public static final Name HMAC_SHA256 = Name.fromConstantString(HMAC_SHA256_STR);

  /**
   * The default fudge value for outgoing packets. Can be overriden by the
   * tsigfudge option.
   */
  public static final short FUDGE = 300;

  private Name name, alg;
  private String digest;
  private byte[] key;

  private void getDigest() {
    if (alg.equals(HMAC_MD5))
      digest = "md5";
    else if (alg.equals(HMAC_SHA1))
      digest = "sha-1";
    else if (alg.equals(HMAC_SHA256))
      digest = "sha-256";
    else
      throw new IllegalArgumentException("Invalid algorithm");
  }

  /**
   * Creates a new TSIG key, which can be used to sign or verify a message.
   * 
   * @param algorithm
   *          The algorithm of the shared key.
   * @param name
   *          The name of the shared key.
   * @param key
   *          The shared key's data.
   */
  public TSIG(Name algorithm, Name name, byte[] key) {
    this.name = name;
    this.alg = algorithm;
    this.key = key;
    getDigest();
  }

  /**
   * Creates a new TSIG key with the hmac-md5 algorithm, which can be used to
   * sign or verify a message.
   * 
   * @param name
   *          The name of the shared key.
   * @param key
   *          The shared key's data.
   */
  public TSIG(Name name, byte[] key) {
    this(HMAC_MD5, name, key);
  }

  /**
   * Creates a new TSIG object, which can be used to sign or verify a message.
   * 
   * @param name
   *          The name of the shared key.
   * @param key
   *          The shared key's data represented as a base64 encoded string.
   * @throws IllegalArgumentException
   *           The key name is an invalid name
   * @throws IllegalArgumentException
   *           The key data is improperly encoded
   */
  public TSIG(Name algorithm, String name, String key) {
    this.key = base64.fromString(key);
    if (this.key == null)
      throw new IllegalArgumentException("Invalid TSIG key string");
    try {
      this.name = Name.fromString(name, Name.root);
    } catch (TextParseException e) {
      throw new IllegalArgumentException("Invalid TSIG key name");
    }
    this.alg = algorithm;
    getDigest();
  }

  /**
   * Creates a new TSIG object, which can be used to sign or verify a message.
   * 
   * @param name
   *          The name of the shared key. The legal values are "hmac-md5",
   *          "hmac-sha1", and "hmac-sha256".
   * @param key
   *          The shared key's data represented as a base64 encoded string.
   * @throws IllegalArgumentException
   *           The key name is an invalid name
   * @throws IllegalArgumentException
   *           The key data is improperly encoded
   */
  public TSIG(String algorithm, String name, String key) {
    this(HMAC_MD5, name, key);
    if (algorithm.equalsIgnoreCase("hmac-md5"))
      this.alg = HMAC_MD5;
    else if (algorithm.equalsIgnoreCase("hmac-sha1"))
      this.alg = HMAC_SHA1;
    else if (algorithm.equalsIgnoreCase("hmac-sha256"))
      this.alg = HMAC_SHA256;
    else
      throw new IllegalArgumentException("Invalid TSIG algorithm");
    getDigest();
  }

  /**
   * Creates a new TSIG object with the hmac-md5 algorithm, which can be used to
   * sign or verify a message.
   * 
   * @param name
   *          The name of the shared key
   * @param key
   *          The shared key's data, represented as a base64 encoded string.
   * @throws IllegalArgumentException
   *           The key name is an invalid name
   * @throws IllegalArgumentException
   *           The key data is improperly encoded
   */
  public TSIG(String name, String key) {
    this(HMAC_MD5, name, key);
  }

  /**
   * Creates a new TSIG object with the hmac-md5 algorithm, which can be used to
   * sign or verify a message.
   * 
   * @param str
   *          The TSIG key, in the form name:secret, name/secret,
   *          alg:name:secret, or alg/name/secret. If an algorithm is specified,
   *          it must be "hmac-md5", "hmac-sha1", or "hmac-sha256".
   * @throws IllegalArgumentException
   *           The string does not contain both a name and secret.
   * @throws IllegalArgumentException
   *           The key name is an invalid name
   * @throws IllegalArgumentException
   *           The key data is improperly encoded
   */
  static public TSIG fromString(String str) {
    String[] parts = str.split("[:/]");
    if (parts.length < 2 || parts.length > 3)
      throw new IllegalArgumentException("Invalid TSIG key " + "specification");
    if (parts.length == 3)
      return new TSIG(parts[0], parts[1], parts[2]);
    else
      return new TSIG(HMAC_MD5, parts[0], parts[1]);
  }

  /**
   * Generates a TSIG record with a specific error for a message that has been
   * rendered.
   * 
   * @param m
   *          The message
   * @param b
   *          The rendered message
   * @param error
   *          The error
   * @param old
   *          If this message is a response, the TSIG from the request
   * @return The TSIG record to be added to the message
   */
  public TSIGRecord generate(Message m, byte[] b, int error, TSIGRecord old) {
    Date timeSigned;
    if (error != Rcode.BADTIME)
      timeSigned = new Date();
    else
      timeSigned = old.getTimeSigned();
    int fudge;
    HMAC hmac = null;
    if (error == Rcode.NOERROR || error == Rcode.BADTIME)
      hmac = new HMAC(digest, key);

    fudge = Options.intValue("tsigfudge");
    if (fudge < 0 || fudge > 0x7FFF)
      fudge = FUDGE;

    if (old != null) {
      DNSOutput out = new DNSOutput();
      out.writeU16(old.getSignature().length);
      if (hmac != null) {
        hmac.update(out.toByteArray());
        hmac.update(old.getSignature());
      }
    }

    /* Digest the message */
    if (hmac != null)
      hmac.update(b);

    DNSOutput out = new DNSOutput();
    name.toWireCanonical(out);
    out.writeU16(DClass.ANY); /* class */
    out.writeU32(0); /* ttl */
    alg.toWireCanonical(out);
    long time = timeSigned.getTime() / 1000;
    int timeHigh = (int) (time >> 32);
    long timeLow = (time & 0xFFFFFFFFL);
    out.writeU16(timeHigh);
    out.writeU32(timeLow);
    out.writeU16(fudge);

    out.writeU16(error);
    out.writeU16(0); /* No other data */

    if (hmac != null)
      hmac.update(out.toByteArray());

    byte[] signature;
    if (hmac != null)
      signature = hmac.sign();
    else
      signature = new byte[0];

    byte[] other = null;
    if (error == Rcode.BADTIME) {
      out = new DNSOutput();
      time = new Date().getTime() / 1000;
      timeHigh = (int) (time >> 32);
      timeLow = (time & 0xFFFFFFFFL);
      out.writeU16(timeHigh);
      out.writeU32(timeLow);
      other = out.toByteArray();
    }

    return (new TSIGRecord(name, DClass.ANY, 0, alg, timeSigned, fudge, signature, m.getHeader().getID(), error, other));
  }

  /**
   * Generates a TSIG record with a specific error for a message and adds it to
   * the message.
   * 
   * @param m
   *          The message
   * @param error
   *          The error
   * @param old
   *          If this message is a response, the TSIG from the request
   */
  public void apply(Message m, int error, TSIGRecord old) {
    Record r = generate(m, m.toWire(), error, old);
    m.addRecord(r, Section.ADDITIONAL);
    m.tsigState = Message.TSIG_SIGNED;
  }

  /**
   * Generates a TSIG record for a message and adds it to the message
   * 
   * @param m
   *          The message
   * @param old
   *          If this message is a response, the TSIG from the request
   */
  public void apply(Message m, TSIGRecord old) {
    apply(m, Rcode.NOERROR, old);
  }

  /**
   * Generates a TSIG record for a message and adds it to the message
   * 
   * @param m
   *          The message
   * @param old
   *          If this message is a response, the TSIG from the request
   */
  public void applyStream(Message m, TSIGRecord old, boolean first) {
    if (first) {
      apply(m, old);
      return;
    }
    Date timeSigned = new Date();
    int fudge;
    HMAC hmac = new HMAC(digest, key);

    fudge = Options.intValue("tsigfudge");
    if (fudge < 0 || fudge > 0x7FFF)
      fudge = FUDGE;

    DNSOutput out = new DNSOutput();
    out.writeU16(old.getSignature().length);
    hmac.update(out.toByteArray());
    hmac.update(old.getSignature());

    /* Digest the message */
    hmac.update(m.toWire());

    out = new DNSOutput();
    long time = timeSigned.getTime() / 1000;
    int timeHigh = (int) (time >> 32);
    long timeLow = (time & 0xFFFFFFFFL);
    out.writeU16(timeHigh);
    out.writeU32(timeLow);
    out.writeU16(fudge);

    hmac.update(out.toByteArray());

    byte[] signature = hmac.sign();
    byte[] other = null;

    Record r = new TSIGRecord(name, DClass.ANY, 0, alg, timeSigned, fudge, signature, m.getHeader().getID(), Rcode.NOERROR, other);
    m.addRecord(r, Section.ADDITIONAL);
    m.tsigState = Message.TSIG_SIGNED;
  }

  /**
   * Verifies a TSIG record on an incoming message. Since this is only called in
   * the context where a TSIG is expected to be present, it is an error if one
   * is not present. After calling this routine, Message.isVerified() may be
   * called on this message.
   * 
   * @param m
   *          The message
   * @param b
   *          An array containing the message in unparsed form. This is
   *          necessary since TSIG signs the message in wire format, and we
   *          can't recreate the exact wire format (with the same name
   *          compression).
   * @param length
   *          The length of the message in the array.
   * @param old
   *          If this message is a response, the TSIG from the request
   * @return The result of the verification (as an Rcode)
   * @see Rcode
   */
  public byte verify(Message m, byte[] b, int length, TSIGRecord old) {
    m.tsigState = Message.TSIG_FAILED;
    TSIGRecord tsig = m.getTSIG();
    HMAC hmac = new HMAC(digest, key);
    if (tsig == null)
      return Rcode.FORMERR;

    if (!tsig.getName().equals(name) || !tsig.getAlgorithm().equals(alg)) {
      if (Options.check("verbose"))
        System.err.println("BADKEY failure");
      return Rcode.BADKEY;
    }
    long now = System.currentTimeMillis();
    long then = tsig.getTimeSigned().getTime();
    long fudge = tsig.getFudge();
    if (Math.abs(now - then) > fudge * 1000) {
      if (Options.check("verbose"))
        System.err.println("BADTIME failure");
      return Rcode.BADTIME;
    }

    if (old != null && tsig.getError() != Rcode.BADKEY && tsig.getError() != Rcode.BADSIG) {
      DNSOutput out = new DNSOutput();
      out.writeU16(old.getSignature().length);
      hmac.update(out.toByteArray());
      hmac.update(old.getSignature());
    }
    m.getHeader().decCount(Section.ADDITIONAL);
    byte[] header = m.getHeader().toWire();
    m.getHeader().incCount(Section.ADDITIONAL);
    hmac.update(header);

    int len = m.tsigstart - header.length;
    hmac.update(b, header.length, len);

    DNSOutput out = new DNSOutput();
    tsig.getName().toWireCanonical(out);
    out.writeU16(tsig.dclass);
    out.writeU32(tsig.ttl);
    tsig.getAlgorithm().toWireCanonical(out);
    long time = tsig.getTimeSigned().getTime() / 1000;
    int timeHigh = (int) (time >> 32);
    long timeLow = (time & 0xFFFFFFFFL);
    out.writeU16(timeHigh);
    out.writeU32(timeLow);
    out.writeU16(tsig.getFudge());
    out.writeU16(tsig.getError());
    if (tsig.getOther() != null) {
      out.writeU16(tsig.getOther().length);
      out.writeByteArray(tsig.getOther());
    } else {
      out.writeU16(0);
    }

    hmac.update(out.toByteArray());

    if (hmac.verify(tsig.getSignature())) {
      m.tsigState = Message.TSIG_VERIFIED;
      return Rcode.NOERROR;
    } else {
      if (Options.check("verbose"))
        System.err.println("BADSIG failure");
      return Rcode.BADSIG;
    }
  }

  /**
   * Verifies a TSIG record on an incoming message. Since this is only called in
   * the context where a TSIG is expected to be present, it is an error if one
   * is not present. After calling this routine, Message.isVerified() may be
   * called on this message.
   * 
   * @param m
   *          The message
   * @param b
   *          The message in unparsed form. This is necessary since TSIG signs
   *          the message in wire format, and we can't recreate the exact wire
   *          format (with the same name compression).
   * @param old
   *          If this message is a response, the TSIG from the request
   * @return The result of the verification (as an Rcode)
   * @see Rcode
   */
  public int verify(Message m, byte[] b, TSIGRecord old) {
    return verify(m, b, b.length, old);
  }

  /**
   * Returns the maximum length of a TSIG record generated by this key.
   * 
   * @see TSIGRecord
   */
  public int recordLength() {
    return (name.length() + 10 + alg.length() + 8 + // time signed, fudge
        18 + // 2 byte MAC length, 16 byte MAC
        4 + // original id, error
    8); // 2 byte error length, 6 byte max error field.
  }

  public static class StreamVerifier {
    /**
     * A helper class for verifying multiple message responses.
     */

    private TSIG key;
    private HMAC verifier;
    private int nresponses;
    private int lastsigned;
    private TSIGRecord lastTSIG;

    /** Creates an object to verify a multiple message response */
    public StreamVerifier(TSIG tsig, TSIGRecord old) {
      key = tsig;
      verifier = new HMAC(key.digest, key.key);
      nresponses = 0;
      lastTSIG = old;
    }

    /**
     * Verifies a TSIG record on an incoming message that is part of a multiple
     * message response. TSIG records must be present on the first and last
     * messages, and at least every 100 records in between. After calling this
     * routine, Message.isVerified() may be called on this message.
     * 
     * @param m
     *          The message
     * @param b
     *          The message in unparsed form
     * @return The result of the verification (as an Rcode)
     * @see Rcode
     */
    public int verify(Message m, byte[] b) {
      TSIGRecord tsig = m.getTSIG();

      nresponses++;

      if (nresponses == 1) {
        int result = key.verify(m, b, lastTSIG);
        if (result == Rcode.NOERROR) {
          byte[] signature = tsig.getSignature();
          DNSOutput out = new DNSOutput();
          out.writeU16(signature.length);
          verifier.update(out.toByteArray());
          verifier.update(signature);
        }
        lastTSIG = tsig;
        return result;
      }

      if (tsig != null)
        m.getHeader().decCount(Section.ADDITIONAL);
      byte[] header = m.getHeader().toWire();
      if (tsig != null)
        m.getHeader().incCount(Section.ADDITIONAL);
      verifier.update(header);

      int len;
      if (tsig == null)
        len = b.length - header.length;
      else
        len = m.tsigstart - header.length;
      verifier.update(b, header.length, len);

      if (tsig != null) {
        lastsigned = nresponses;
        lastTSIG = tsig;
      } else {
        boolean required = (nresponses - lastsigned >= 100);
        if (required) {
          m.tsigState = Message.TSIG_FAILED;
          return Rcode.FORMERR;
        } else {
          m.tsigState = Message.TSIG_INTERMEDIATE;
          return Rcode.NOERROR;
        }
      }

      if (!tsig.getName().equals(key.name) || !tsig.getAlgorithm().equals(key.alg)) {
        if (Options.check("verbose"))
          System.err.println("BADKEY failure");
        m.tsigState = Message.TSIG_FAILED;
        return Rcode.BADKEY;
      }

      DNSOutput out = new DNSOutput();
      long time = tsig.getTimeSigned().getTime() / 1000;
      int timeHigh = (int) (time >> 32);
      long timeLow = (time & 0xFFFFFFFFL);
      out.writeU16(timeHigh);
      out.writeU32(timeLow);
      out.writeU16(tsig.getFudge());
      verifier.update(out.toByteArray());

      if (verifier.verify(tsig.getSignature()) == false) {
        if (Options.check("verbose"))
          System.err.println("BADSIG failure");
        return Rcode.BADSIG;
      }

      verifier.clear();
      out = new DNSOutput();
      out.writeU16(tsig.getSignature().length);
      verifier.update(out.toByteArray());
      verifier.update(tsig.getSignature());

      return Rcode.NOERROR;
    }
  }

}
