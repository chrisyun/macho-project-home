package com.ibm.tivoli.cars;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogParser {
  
  //private DateFormat LOG_DATE_FORMAT = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
  //private DateFormat LOG_DATE_FORMAT = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.CHINESE);
  private DateFormat LOG_DATE_FORMAT = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");
  private static Log log = LogFactory.getLog(LogParser.class);

  public LogParser() {
    super();
  }

  public LogParser(Locale local) {
    super();
    LOG_DATE_FORMAT = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", local);
  }
  
  public WebSEALRequestLogEvent parseWebSEALRequestLogEvent(String line) throws Exception {
    if (line.startsWith("tail")) {
      return null;
    }
    String[] p = StringUtils.split(line, '"');
    // 10.17.76.4 - zougb1 [25/Oct/2010:14:27:51 +0800] "GET /oa/Login?event=LdapToOa HTTP/1.1" 302 261
    if (p.length != 3) {
      return null;
    }

    String[] s = StringUtils.split(p[0], ' ');
    String clientIP = s[0];
    String userid = s[2];
    String t = s[3];
    Date timestamp = null;
    try {
      t = t + " " + s[4];
      t = StringUtils.strip(t, "[");
      t = StringUtils.strip(t, "]");
      // System.out.println(LOG_DATE_FORMAT.format(new Date()));
      timestamp = LOG_DATE_FORMAT.parse(t);
    } catch (ParseException e) {
      log.error("parse date fail: " + t, e);
      return null;
    }

    s = StringUtils.split(p[1], ' ');
    String httpMethod = s[0];
    String resourceUrl = s[1];
    String httpProtocol = s[2];

    s = StringUtils.split(p[2], ' ');
    int httpCode = Integer.parseInt(s[0]);
    int contentLength = Integer.parseInt(s[1]);

    WebSEALRequestLogEvent result = new WebSEALRequestLogEvent();
    result.setClientIP(clientIP);
    result.setUserid(userid);

    result.setTimestamp(timestamp);
    result.setHttpMethod(httpMethod);
    result.setResourceUrl(resourceUrl);
    result.setHttpProtocol(httpProtocol);

    result.setHttpCode(httpCode);
    result.setContentLength(contentLength);
    return result;
  }

}
