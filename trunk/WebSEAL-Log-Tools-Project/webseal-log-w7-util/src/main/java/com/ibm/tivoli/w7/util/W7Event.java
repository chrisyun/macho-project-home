/**
 * 
 */
package com.ibm.tivoli.w7.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 * Sample: 
 * <event>
 *   <when>${logEvent.timstamp} as format[YYYY-MM-ddTHH:mm:ss:s+hh:mm]</when>
 *   <what verb="${logEvent.action.name}" noun="${logEvent.application.name}" success="Success"/>
 *   <onwhat type="${logEvent.httpProtocol}" path="${logEvent.httpMethod}" name="${logEvent.resourceUrl}"/>
 *   <who logonname="${logEvent.uid}" realname="${logEvent.uid}"/>
 *   <where type="websealid" name="${webSEALInstaceId}"/>
 *   <whereto type="app" name="${logEvent.application.name}"/>
 *   <wherefrom type="ip" name="${logEvent.clientIP}"/>
 *   <info>httpcode: ${logEvent.httpCode}</info>
 * </event>
 * </pre>
 * 
 * @author zhaodonglu
 * 
 */
public class W7Event {
  private static DateFormat LOG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
  private static DateFormat LOG_TIMEZONE_FORMAT = new SimpleDateFormat("Z", Locale.ENGLISH);

  private static Validator validator = new Validator();

  private Date when = new Date();
  private W7What what = null;
  private W7OnWhat onWhat = null;
  private W7Who who = null;
  private W7Where where = null;
  private W7Where whereTo = null;
  private W7Where whereFrom = null;
  private String info = null;
  

  /**
   * 
   */
  public W7Event() {
    super();
  }

  /**
   * @param when
   * @param whorealname Arbitrary string values up to 64 bytes each
   * @param whologonname Arbitrary string values up to 64 bytes each
   * @param whatverb Arbitrary string values up to 20 bytes each.
   * @param whatnoun Arbitrary string values up to 20 bytes each.
   * @param whatsuccess Arbitrary string up to 8 characters.
   * @param wheretype Arbitrary string value up to 20 bytes.
   * @param wherename Arbitrary string value up to 128 bytes.
   * @param wherefromtype Arbitrary string value up to 20 bytes.
   * @param wherefromname Arbitrary string value up to 128 bytes.
   * @param wheretotype Arbitrary string value up to 20 bytes.
   * @param wheretoname Arbitrary string value up to 128 bytes.
   * @param onwhattype Arbitrary string value up to 20 bytes.
   * @param onwhatpath Arbitrary string value up to 150 bytes.
   * @param onwhatname Arbitrary string value up to 110 bytes.
   * @param info maximum length of 3900 characters
   */
  public W7Event(Date when, String whorealname, String whologonname, String whatverb, String whatnoun, String whatsuccess, String wheretype, String wherename,
      String wherefromtype, String wherefromname, String wheretotype, String wheretoname, String onwhattype, String onwhatpath, String onwhatname, String info) {
    this.when = when;
    this.what = new W7What(whatverb, whatnoun, whatsuccess);
    this.onWhat = new W7OnWhat(onwhattype, onwhatpath, onwhatname);
    this.who = new W7Who(whologonname, whorealname);
    this.where = new W7Where(wheretype, wherename);
    this.whereTo = new W7Where(wheretotype, wheretoname);
    this.whereFrom = new W7Where(wherefromtype, wherefromname);
    this.info = info;
  }

  /**
   * @param when
   * @param what
   * @param onWhat
   * @param who
   * @param where
   * @param whereTo
   * @param whereFrom
   * @param info
   */
  public W7Event(Date when, W7What what, W7OnWhat onWhat, W7Who who, W7Where where, W7Where whereTo, W7Where whereFrom, String info) {
    super();
    this.when = when;
    this.what = what;
    this.onWhat = onWhat;
    this.who = who;
    this.where = where;
    this.whereTo = whereTo;
    this.whereFrom = whereFrom;
    this.info = info;
  }

  /**
   * @return the when
   */
  public Date getWhen() {
    return when;
  }

  /**
   * @param when
   *          the when to set
   */
  public void setWhen(Date when) {
    this.when = when;
  }

  /**
   * @return the what
   */
  public W7What getWhat() {
    return what;
  }

  /**
   * @param what
   *          the what to set
   */
  public void setWhat(W7What what) {
    this.what = what;
  }

  /**
   * @return the onWhat
   */
  public W7OnWhat getOnWhat() {
    return onWhat;
  }

  /**
   * @param onWhat
   *          the onWhat to set
   */
  public void setOnWhat(W7OnWhat onWhat) {
    this.onWhat = onWhat;
  }

  /**
   * @return the who
   */
  public W7Who getWho() {
    return who;
  }

  /**
   * @param who
   *          the who to set
   */
  public void setWho(W7Who who) {
    this.who = who;
  }

  /**
   * @return the where
   */
  public W7Where getWhere() {
    return where;
  }

  /**
   * @param where
   *          the where to set
   */
  public void setWhere(W7Where where) {
    this.where = where;
  }

  /**
   * @return the whereTo
   */
  public W7Where getWhereTo() {
    return whereTo;
  }

  /**
   * @param whereTo
   *          the whereTo to set
   */
  public void setWhereTo(W7Where whereTo) {
    this.whereTo = whereTo;
  }

  /**
   * @return the whereFrom
   */
  public W7Where getWhereFrom() {
    return whereFrom;
  }

  /**
   * @param whereFrom
   *          the whereFrom to set
   */
  public void setWhereFrom(W7Where whereFrom) {
    this.whereFrom = whereFrom;
  }

  /**
   * @return the info
   */
  public String getInfo() {
    return info;
  }

  /**
   * @param info
   *          the info to set
   */
  public void setInfo(String info) {
    this.info = info;
  }
  
  /**
   * @param out
   * @throws IOException
   * @throws ValidationException 
   */
  public void toXML(Writer out) throws IOException, ValidationException {
    validator .validate(this);
    
    String tz = LOG_TIMEZONE_FORMAT.format(this.getWhen());
    tz = String.format("%s:%s", tz.substring(0, 3), tz.substring(3));

    out.write("<event>");
    // TODO escape XML
    out.write(String.format("<when>%s%s</when>", LOG_DATE_FORMAT.format(this.getWhen()), tz));
    out.write(String.format("<what verb=\"%s\" noun=\"%s\" success=\"%s\"/>", XMLEscape.escape(this.getWhat().getVerb()), this.getWhat().getNoun(), this.getWhat().getSuccess()));
    out.write(String.format("<onwhat type=\"%s\" name=\"%s\" path=\"%s\"/>", this.getOnWhat().getType(), this.getOnWhat().getName(), this.getOnWhat().getPath()));
    out.write(String.format("<who logonname=\"%s\" realname=\"%s\"/>", this.getWho().getLogonname(), this.getWho().getRealname()));
    out.write(String.format("<where type=\"%s\" name=\"%s\"/>", this.getWhere().getType(), this.getWhere().getName()));
    out.write(String.format("<whereto type=\"%s\" name=\"%s\"/>", this.getWhereTo().getType(), this.getWhereTo().getName()));
    out.write(String.format("<wherefrom type=\"%s\" name=\"%s\"/>", this.getWhereFrom().getType(), this.getWhereFrom().getName()));
    out.write(String.format("<info>%s</info>", XMLEscape.escape(this.getInfo())));
    out.write("/<event>");
  }
  
  /**
   * @return
   * @throws IOException
   * @throws ValidationException 
   */
  public String toXML() throws IOException, ValidationException {
    StringWriter out = new StringWriter();
    this.toXML(out);
    return out.toString();
  }

}
