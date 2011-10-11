/**
 * 
 */
package com.ibm.tivoli.cars.handler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.tivoli.cars.WebSEALRequestLogEvent;
import com.ibm.tivoli.cars.entity.Action;
import com.ibm.tivoli.cars.entity.Application;

/**
 * @author zhaodonglu
 * 
 */
public class W7XMLLogHandler implements EventHandler, PropertiesAware {

  private static Log log = LogFactory.getLog("w7.event.xml");

  private String webSEALUrl = "HTTP://oa.tiakanglife.com";
  private String webSEALInstaceId = "default-webseald-tivoli1";

  private boolean ignoreUnauth = false;

  /**
   * 
   */
  public W7XMLLogHandler() {
    super();
  }

  /**
   * @return the webSEALUrl
   */
  public String getWebSEALUrl() {
    return webSEALUrl;
  }

  /**
   * @param webSEALUrl
   *          the webSEALUrl to set
   */
  public void setWebSEALUrl(String webSEALUrl) {
    this.webSEALUrl = webSEALUrl;
  }

  /**
   * @return the webSEALInstaceId
   */
  public String getWebSEALInstaceId() {
    return webSEALInstaceId;
  }

  /**
   * @param webSEALInstaceId
   *          the webSEALInstaceId to set
   */
  public void setWebSEALInstaceId(String webSEALInstaceId) {
    this.webSEALInstaceId = webSEALInstaceId;
  }

  /**
   * @return the ignoreUnauth
   */
  public boolean isIgnoreUnauth() {
    return ignoreUnauth;
  }

  /**
   * @param ignoreUnauth
   *          the ignoreUnauth to set
   */
  public void setIgnoreUnauth(boolean ignoreUnauth) {
    this.ignoreUnauth = ignoreUnauth;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.cars.handler.EventHandler#handle(java.lang.String,
   * com.ibm.tivoli.cars.WebSEALRequestLogEvent)
   */
  /**
   * 
   * <pre>
   * Sample: 
   * <event>
   *   <when>${logEvent.timstamp} as format[YYYY-MM-ddTHH:mm:ss:s ��.hh:mm]</when>
   *   <what verb="${logEvent.action.name}" noun="${logEvent.application.name}" success="Success"/>
   *   <onwhat type="${logEvent.httpProtocol}" path="${logEvent.httpMethod}" name="${logEvent.resourceUrl}"/>
   *   <who logonname="${logEvent.uid}" realname="${logEvent.uid}"/>
   *   <where type="websealid" name="${webSEALInstaceId}"/>
   *   <whereto type="app" name="${logEvent.application.name}"/>
   *   <wherefrom type="ip" name="${logEvent.clientIP}"/>
   *   <info>httpcode: ${logEvent.httpCode}</info>
   * </event>
   * </pre>
   */
  public void handle(String logLine, WebSEALRequestLogEvent logEvent) throws Exception {
    if (logEvent == null) {
      return;
    }
    Application application = logEvent.getApplication();
    if (application == null) {
      return;
    }
    Action action = logEvent.getAction();
    if (action == null || !action.isUploaded()) {
      return;
    }
    if (this.ignoreUnauth && logEvent.getUserid().equals("Unauth")) {
      return;
    }

    StringBuffer bf = toXml(logEvent);

    log.info(bf);

  }

  public StringBuffer toXml(WebSEALRequestLogEvent logEvent) {

    String clientIP = logEvent.getClientIP();

    String userid = logEvent.getUserid();

    Date timestamp = logEvent.getTimestamp();

    String httpMethod = logEvent.getHttpMethod();

    String resourceUrl = logEvent.getResourceUrl();

    String httpProtocol = logEvent.getHttpProtocol();

    int httpCode = logEvent.getHttpCode();

    int contentLength = logEvent.getContentLength();

    Application application = logEvent.getApplication();

    Action action = logEvent.getAction();

    StringBuffer bf = new StringBuffer();

    DateFormat LOG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);

    // for(int i=1;i<=1000;i++){
    bf.append("<event>\n");
    bf.append("<when>" + LOG_DATE_FORMAT.format(timestamp) + "</when>\n");

    bf.append("<what verb=\"" + action.getName() + "\" noun=\"" + application.getName() + "\" success=\"Success\"/>\n");

    bf.append("<onwhat type=\"" + httpProtocol + "\" path=\"" + httpMethod + "\" name=\"" + resourceUrl + "\"/>\n");

    bf.append("<who logonname=\"" + userid + "\" realname=\"" + userid + "\"/>\n");

    bf.append("<where type=\"websealid\" name=\"" + webSEALInstaceId + "\"/>\n");

    bf.append("<whereto type=\"app\" name=\"" + application.getName() + "\"/>\n");

    bf.append("<wherefrom type=\"ip\" name=\"" + clientIP + "\"/>\n");

    bf.append("<info>httpcode: " + httpCode + "</info>\n");

    bf.append("</event>\n");
    // }
    return bf;

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ibm.tivoli.cars.handler.PropertiesAware#setProperties(java.util.Properties
   * )
   */
  public void setProperties(Properties props) throws Exception {
    this.setWebSEALUrl(props.getProperty("wb.log.process.webseal.base.url"));
    this.setWebSEALInstaceId(props.getProperty("wb.log.process.webseal.instance.id"));
  }

}
