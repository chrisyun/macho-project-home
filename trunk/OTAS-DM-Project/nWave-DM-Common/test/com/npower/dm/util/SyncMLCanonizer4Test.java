/**
 * Copyright (C) 2003-2006 Funambol
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.npower.dm.util;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import sync4j.framework.logging.Sync4jLogger;
import sync4j.framework.tools.XMLTools;

/**
 * This class is used for transformer the message in the canonical form.
 *
 */
public class SyncMLCanonizer4Test implements Serializable {

  // ------------------------------------------------------------ Private data
  private static final Logger log = Sync4jLogger.getLogger("engine");

  // ------------------------------------------------------------ Constructors
  public SyncMLCanonizer4Test() {
  }

  // ---------------------------------------------------------- Public methods

  /**
   * The message must be canonized every time before call the XML-Java
   * mapping tool (JiBX) that it parsers and generates the SyncML object.
   *
   * @param message the input XML message
   *
   * @return message the input XML message canonized
   **/
  public String canonizeOutputMessage(String message) {
      if (log.isLoggable(Level.FINEST)) {
          log.finest("Starting process of canonization on output message");
      }
      String response = metInfNamespaceHandler(message).replaceAll("<MoreData></MoreData>", "<MoreData/>");

      return mgmtTreeNamespaceHandler(response);
  }


  /**
   * The message input must be canonized every time before call the XML-Java
   * mapping tool (JiBX) that it parsers and generates the SyncML object.
   *
   * @param message the input XML message
   *
   * @return message the input XML message canonized
   **/
  public String canonizeInputMessage(String message) {
      if (log.isLoggable(Level.FINEST)) {
          log.finest("Starting process of canonization on input message");
      }
      message = replaceEntity(message);
      return message;
  }


  /**
   * Replace into message, into the tag <Data> amd if it isn't a CDATA:
   * <ui>
   * <li>'&' with '&amp;amp;'
   *      (if the character isn't the fisrt char of &amp;amp; or &amp;lt; or &amp;gt; or &amp;quot;)
   * <li>'<' with '&amp;lt;'
   * <li>'>' with '&amp;gt;'
   * <li>'"' with '&amp;quot;'
   * </ui>
   * @param message the original message xml
   *
   * @return message the message updated
   */
  private String replaceEntity(String msg) {

      int s = 0;
      int e = 0;

      StringBuffer response = new StringBuffer();
      while ( (e = msg.indexOf("<Data>", s)) >= 0) {

          // 6 = length of <Data>
          response = response.append(msg.substring(s, e + 6));

          int a = msg.indexOf("</Data>", e);
          String data = msg.substring(e + 6, a);

          if (data.startsWith("<![CDATA[")) {
              // not replace nothing
          } else if (data.trim().startsWith("<SyncML xmlns=\"syncml:dmddf1.2\">")) {
              // The data contains a MgmtTree
          } else {
              data = XMLTools.replaceAmp(data);
              data = StringUtils.replace(data, "<", "&lt;");
              data = StringUtils.replace(data, ">", "&gt;");
              data = StringUtils.replace(data, "\"", "&quot;");
          }
          s = a + 7; // length of </Data>
          response.append(data).append("</Data>");
      }
      response.append(msg.substring(s, msg.length()));

      return response.toString();
  }
  

  /**
   * This is a temporary solution in order to obviate to a JiBX bug: it does
   * not allow to declare the namespace to level of structure.
   *
   * @param msg the server response
   *
   * @return the response with namespace correctly added into MetInf element
   */
  private String metInfNamespaceHandler(String msg) {
      int s = 0;
      int e = 0;

      StringBuffer response = new StringBuffer();
      while (( e = msg.indexOf("<Meta", s)) >= 0) {

          response = response.append(msg.substring(s, e));

          int a = msg.indexOf("</Meta>", e);
          String meta = msg.substring(e, a);

          meta = meta.replaceAll("<Type>"   , "<Type xmlns='syncml:metinf'>");
          meta = meta.replaceAll("<Format>" , "<Format xmlns='syncml:metinf'>");
          meta = meta.replaceAll("<Mark>"   , "<Mark xmlns='syncml:metinf'>");
          meta = meta.replaceAll("<Size>"   , "<Size xmlns='syncml:metinf'>");
          meta = meta.replaceAll("<Anchor>" , "<Anchor xmlns='syncml:metinf'>");
          meta = meta.replaceAll("<Version>", "<Version xmlns='syncml:metinf'>");
          meta = meta.replaceAll("<NextNonce>" , "<NextNonce xmlns='syncml:metinf'>");
          meta = meta.replaceAll("<MaxMsgSize>", "<MaxMsgSize xmlns='syncml:metinf'>");
          meta = meta.replaceAll("<MaxObjSize>", "<MaxObjSize xmlns='syncml:metinf'>");
          meta = meta.replaceAll("<EMI>"    , "<EMI xmlns='syncml:metinf'>");
          meta = meta.replaceAll("<Mem>"    , "<Mem xmlns='syncml:metinf'>");

          s = a + 7;
          response.append(meta).append("</Meta>");
      }
      String sResponse = response.append(msg.substring(s, msg.length())).toString();

      return sResponse;
  }


  /**
   * This is a temporary solution in order to obviate to a JiBX bug: it does
   * not allow to declare the namespace to level of structure.
   *
   * @param msg the server response
   *
   * @return the response with namespace correctly added into MetInf element
   */
  private String mgmtTreeNamespaceHandler(String msg) {
      int s = 0;
      int e = 0;

      int cont = 0;
      StringBuffer response = new StringBuffer();
      while (( e = msg.indexOf("<SyncML>", s)) >= 0) {

          response = response.append(msg.substring(s, e));

          int a = msg.indexOf("</SyncML>", e);

          String mgtmTree = msg.substring(e + 8, a);

          if (cont > 0) {
              if (mgtmTree.trim().startsWith("<MgmtTree>")) {
                  response.append("<SyncML xmlns='syncml:dmddf1.2'>");
              }
              response.append(mgtmTree);
              response.append("</SyncML>");
              s = a + 9;

              } else {
                  response.append("<SyncML>");
                  s = e + 8;
              }

              cont ++;
          }
          String sResponse = response.append(msg.substring(s, msg.length())).toString();

          return sResponse;
      }


}