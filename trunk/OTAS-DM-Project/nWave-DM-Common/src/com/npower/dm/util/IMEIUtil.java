/**
  * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/util/IMEIUtil.java,v 1.3 2008/04/30 08:29:56 zhao Exp $
  * $Revision: 1.3 $
  * $Date: 2008/04/30 08:29:56 $
  *
  * ===============================================================================================
  * License, Version 1.1
  *
  * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
  *
  * This SOURCE CODE FILE, which has been provided by NPower as part
  * of a NPower product for use ONLY by licensed users of the product,
  * includes CONFIDENTIAL and PROPRIETARY information of NPower.
  *
  * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
  * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
  * THE PRODUCT.
  *
  * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
  * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
  * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
  * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
  * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
  * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
  * CODE FILE.
  * ===============================================================================================
  */
package com.npower.dm.util;

import com.npower.dm.core.DMException;

/**
 * Caculate IMEI , more information see spec: 3GPP TS 23.003 V7.1.0 (2006-09)
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.3 $ $Date: 2008/04/30 08:29:56 $
 */
public class IMEIUtil {
  
  /**
   * Generate IMEI number
   * @return
   * @throws DMException
   */
  public static String generateIMEI() throws DMException {
    String tac = "00000000";
    double d = 100000000.0 * Math.random();
    long sn = (long)d;
    String s = "" + sn;
    for (int i = 0; i < 6 - s.length(); i++) {
        s += "0" + s;
    }
    s = s.substring(0, 6);
    return calculateIMEI(tac + s);
  }
  
  /**
   * The International Mobile station Equipment Identity and Software Version number (IMEISV), as defined in clause 6, is a 16 digit decimal number composed of three distinct elements:
   * -   an 8 digit Type Allocation Code (TAC);
   * -   a 6 digit Serial Number (SNR); and
   * -   a 2 digit Software Version Number (SVN).
   * 
   * Computation of CD from the IMEI proceeds as follows:
   * Step 1: Double the values of the odd labelled digits D1, D3, D5 ... D13 of the IMEI.
   * Step 2: Add together the individual digits of all the seven numbers obtained in Step 1, and then add this sum to the sum of all the even labelled digits D2, D4, D6 ... D14 of the IMEI.
   * Step 3: If the number obtained in Step 2 ends in 0, then set CD to be 0. If the number obtained in Step 2 does not end in 0, then set CD to be that number subtracted from the next higher number which does end in 0
   *
   * @param imei7Bytes
   * @return
   */
  public static String calculateIMEIBy14Digital(byte[] imei7Bytes) {
    int[] imeiDigitals = new int[14];
    for (int i = 0; i < 7; i++) {
        int first = (imei7Bytes[i] & 0xf0) >> 4;
        int second = (imei7Bytes[i] & 0x0f);
        imeiDigitals[2 * i] = first;
        imeiDigitals[2 * i + 1] = second;
    }
    int step2 = 0;
    for (int i = 0; i < 14; i++) {
        if (i % 2 == 0) {
           step2 += imeiDigitals[i];
        } else {
          int s = 2 * imeiDigitals[i];
          step2 += s % 10;
          step2 += s / 10;
        }
    }
    // Step 3
    int checkcode = 0;
    if (step2 % 10 != 0) {
       checkcode = 10 - step2 % 10;
    }
    // Calcualte IMEI String
    String result = "";
    for (int i = 0; i < imeiDigitals.length; i++) {
        result += imeiDigitals[i];
    }
    result += checkcode;
    return result;
  }
  
  public static String calculateIMEI(String imei) throws DMException {
    if (imei.length() == 15) {
       if (!imei.endsWith("0")) {
          return imei;
       } else {
         imei = imei.substring(0, 14);
       }
    }
    if (imei.length() != 14) {
       throw new DMException("Imei length error:" + imei);
    }
    
    byte[] imeiBytes = hexStringToBytes(imei);
    return calculateIMEIBy14Digital(imeiBytes);
  }

  /**
   * Converts a byte array to a string with hex values.
   * 
   * @param data
   *          Data to convert
   * @return the encoded string
   */
  public static String bytesToHexString(byte[] data) {
    StringBuffer hexStrBuff = new StringBuffer(data.length * 2);
  
    for (int i = 0; i < data.length; i++) {
      String hexByteStr = Integer.toHexString(data[i] & 0xff).toUpperCase();
      if (hexByteStr.length() == 1) {
        hexStrBuff.append("0");
      }
      hexStrBuff.append(hexByteStr);
    }
  
    return hexStrBuff.toString();
  }

  /**
   * Converts a string of hex characters to a byte array.
   * 
   * @param hexString
   *          The hex string to read
   * @return the resulting byte array
   */
  public static byte[] hexStringToBytes(String hexString) {
    byte[] data = new byte[hexString.length() / 2];
  
    for (int i = 0; i < data.length; i++) {
      String a = hexString.substring(i * 2, i * 2 + 2);
      data[i] = (byte) Integer.parseInt(a, 16);
    }
  
    return data;
  }

}
