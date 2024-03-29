// Copyright (c) 1999-2004 Brian Wellington (bwelling@xbill.org)

package org.xbill.DNS;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Constants and functions relating to DNSSEC (algorithm constants). DNSSEC
 * provides authentication for DNS information. RRsets are signed by an
 * appropriate key, and a SIG record is added to the set. A KEY record is
 * obtained from DNS and used to validate the signature, The KEY record must
 * also be validated or implicitly trusted - to validate a key requires a series
 * of validations leading to a trusted key. The key must also be authorized to
 * sign the data.
 * 
 * @see SIGRecord
 * @see KEYRecord
 * @see RRset
 * 
 * @author Brian Wellington
 */

public class DNSSEC {

  public static class Algorithm {
    private Algorithm() {
    }

    /** RSA/MD5 public key (deprecated) */
    public static final int RSAMD5 = 1;

    /** Diffie Hellman key */
    public static final int DH = 2;

    /** DSA public key */
    public static final int DSA = 3;

    /** Elliptic Curve key */
    public static final int ECC = 4;

    /** RSA/SHA1 public key */
    public static final int RSASHA1 = 5;

    /** DSA/SHA1, NSEC3-aware public key */
    public static final int DSA_NSEC3_SHA1 = 6;

    /** RSA/SHA1, NSEC3-aware public key */
    public static final int RSA_NSEC3_SHA1 = 7;

    /** RSA/SHA256 public key */
    public static final int RSASHA256 = 8;

    /** RSA/SHA512 public key */
    public static final int RSASHA512 = 10;

    /** Indirect keys; the actual key is elsewhere. */
    public static final int INDIRECT = 252;

    /** Private algorithm, specified by domain name */
    public static final int PRIVATEDNS = 253;

    /** Private algorithm, specified by OID */
    public static final int PRIVATEOID = 254;

    private static Mnemonic algs = new Mnemonic("DNSSEC algorithm", Mnemonic.CASE_UPPER);

    static {
      algs.setMaximum(0xFF);
      algs.setNumericAllowed(true);

      algs.add(RSAMD5, "RSAMD5");
      algs.add(DH, "DH");
      algs.add(DSA, "DSA");
      algs.add(ECC, "ECC");
      algs.add(RSASHA1, "RSASHA1");
      algs.add(DSA_NSEC3_SHA1, "DSA-NSEC3-SHA1");
      algs.add(RSA_NSEC3_SHA1, "RSA-NSEC3-SHA1");
      algs.add(RSASHA256, "RSASHA256");
      algs.add(RSASHA512, "RSASHA512");
      algs.add(INDIRECT, "INDIRECT");
      algs.add(PRIVATEDNS, "PRIVATEDNS");
      algs.add(PRIVATEOID, "PRIVATEOID");
    }

    /**
     * Converts an algorithm into its textual representation
     */
    public static String string(int alg) {
      return algs.getText(alg);
    }

    /**
     * Converts a textual representation of an algorithm into its numeric code.
     * Integers in the range 0..255 are also accepted.
     * 
     * @param s
     *          The textual representation of the algorithm
     * @return The algorithm code, or -1 on error.
     */
    public static int value(String s) {
      return algs.getValue(s);
    }
  }

  public static final int RSAMD5 = Algorithm.RSAMD5;
  public static final int RSA = Algorithm.RSAMD5;
  public static final int DH = Algorithm.DH;
  public static final int DSA = Algorithm.DSA;
  public static final int RSASHA1 = Algorithm.RSASHA1;
  public static final int DSA_NSEC3_SHA1 = Algorithm.DSA_NSEC3_SHA1;
  public static final int RSA_NSEC3_SHA1 = Algorithm.RSA_NSEC3_SHA1;
  public static final int RSASHA256 = Algorithm.RSASHA256;
  public static final int RSASHA512 = Algorithm.RSASHA512;

  public static final int Failed = -1;
  public static final int Insecure = 0;
  public static final int Secure = 1;

  private DNSSEC() {
  }

  private static void digestSIG(DNSOutput out, SIGBase sig) {
    out.writeU16(sig.getTypeCovered());
    out.writeU8(sig.getAlgorithm());
    out.writeU8(sig.getLabels());
    out.writeU32(sig.getOrigTTL());
    out.writeU32(sig.getExpire().getTime() / 1000);
    out.writeU32(sig.getTimeSigned().getTime() / 1000);
    out.writeU16(sig.getFootprint());
    sig.getSigner().toWireCanonical(out);
  }

  /**
   * Creates a byte array containing the concatenation of the fields of the SIG
   * record and the RRsets to be signed/verified. This does not perform a
   * cryptographic digest.
   * 
   * @param sig
   *          The SIG record used to sign/verify the rrset.
   * @param rrset
   *          The data to be signed/verified.
   * @return The data to be cryptographically signed or verified.
   */
  public static byte[] digestRRset(RRSIGRecord sig, RRset rrset) {
    DNSOutput out = new DNSOutput();
    digestSIG(out, sig);

    int size = rrset.size();
    Record[] records = new Record[size];

    Iterator it = rrset.rrs();
    Name name = rrset.getName();
    Name wild = null;
    int sigLabels = sig.getLabels() + 1; // Add the root label back.
    if (name.labels() > sigLabels)
      wild = name.wild(name.labels() - sigLabels);
    while (it.hasNext())
      records[--size] = (Record) it.next();
    Arrays.sort(records);

    DNSOutput header = new DNSOutput();
    if (wild != null)
      wild.toWireCanonical(header);
    else
      name.toWireCanonical(header);
    header.writeU16(rrset.getType());
    header.writeU16(rrset.getDClass());
    header.writeU32(sig.getOrigTTL());
    for (int i = 0; i < records.length; i++) {
      out.writeByteArray(header.toByteArray());
      int lengthPosition = out.current();
      out.writeU16(0);
      out.writeByteArray(records[i].rdataToWireCanonical());
      int rrlength = out.current() - lengthPosition - 2;
      out.save();
      out.jump(lengthPosition);
      out.writeU16(rrlength);
      out.restore();
    }
    return out.toByteArray();
  }

  /**
   * Creates a byte array containing the concatenation of the fields of the SIG
   * record and the message to be signed/verified. This does not perform a
   * cryptographic digest.
   * 
   * @param sig
   *          The SIG record used to sign/verify the rrset.
   * @param msg
   *          The message to be signed/verified.
   * @param previous
   *          If this is a response, the signature from the query.
   * @return The data to be cryptographically signed or verified.
   */
  public static byte[] digestMessage(SIGRecord sig, Message msg, byte[] previous) {
    DNSOutput out = new DNSOutput();
    digestSIG(out, sig);

    if (previous != null)
      out.writeByteArray(previous);

    msg.toWire(out);
    return out.toByteArray();
  }

}
