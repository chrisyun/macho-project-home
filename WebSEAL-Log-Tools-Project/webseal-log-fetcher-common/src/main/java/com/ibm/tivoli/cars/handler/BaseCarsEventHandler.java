package com.ibm.tivoli.cars.handler;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import com.ibm.tivoli.cars.WebSEALRequestLogEvent;

public abstract class BaseCarsEventHandler implements EventHandler {

  private String webSEALUrl = "HTTP://oa.tiakanglife.com";
  private String webSEALInstaceId = "default-webseald-tivoli1";
  private String webSEALLocation = "tivoli1";

  public BaseCarsEventHandler() {
    super();
  }

  public String getWebSEALUrl() {
    return webSEALUrl;
  }

  public void setWebSEALUrl(String webSEALUrl) {
    this.webSEALUrl = webSEALUrl;
  }

  public String getWebSEALInstaceId() {
    return webSEALInstaceId;
  }

  public void setWebSEALInstaceId(String webSEALInstaceId) {
    this.webSEALInstaceId = webSEALInstaceId;
  }

  public String getWebSEALLocation() {
    return webSEALLocation;
  }

  public void setWebSEALLocation(String webSEALLocation) {
    this.webSEALLocation = webSEALLocation;
  }

  protected String getMessage(WebSEALRequestLogEvent logEvent) throws IOException {
    return this.getMessage(Arrays.asList(logEvent));
  }

  protected String getMessage(List<WebSEALRequestLogEvent> logEvents) throws IOException {
    StringWriter output = new StringWriter();
    output.write("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:ibm:cars:10\">\n");
    output.write("   <soapenv:Header/>\n");
    output.write("   <soapenv:Body>\n");
    output.write("      <urn:sendEvent>\n");
    output.write("         <!--1 or more repetitions:-->\n");
    output.write("         <urn:event>\n");
    for (WebSEALRequestLogEvent event : logEvents) {
      toXML(event, output);
    }
    output.write("         </urn:event>\n");
    output.write("      </urn:sendEvent>\n");
    output.write("   </soapenv:Body>\n");
    output.write("</soapenv:Envelope>\n");
    return output.toString();
  }

  public void toXML(WebSEALRequestLogEvent logEvent, Writer output) throws IOException {
    String globalInstanceId = UUID.randomUUID().toString();
    globalInstanceId = globalInstanceId.replace('-', '0');
    globalInstanceId = "CBA6011DFDFFBBEBF" + globalInstanceId.toUpperCase();
    String url = this.webSEALUrl + logEvent.getResourceUrl();

    // 2010-10-25T05:46:03.281193Z
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    calendar.setTime(logEvent.getTimestamp());
    // Convert GMT TimeZone
    Date ts = calendar.getTime();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSS");
    timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    String creationTime = dateFormat.format(ts) + "T" + timeFormat.format(ts) + "Z";
    output.write("<CommonBaseEvent creationTime=\"" + creationTime + "\" extensionName=\"IBM_CBA_AUDIT_RESOURCE_ACCESS\" globalInstanceId=\""
        + globalInstanceId + "\"\n");
    output.write("  version=\"1.0.1\">\n");
    output.write("  <extendedDataElements name=\"outcome\" type=\"string\">\n");
    output.write("    <values>CARSComplexType:CARSAuditOutcome</values>\n");
    output.write("    <children name=\"result\" type=\"string\">\n");
    output.write("      <values>SUCCESSFUL</values>\n");
    output.write("    </children>\n");
    output.write("    <children name=\"majorStatus\" type=\"int\">\n");
    output.write("      <values>0</values>\n");
    output.write("    </children>\n");
    output.write("    <children name=\"minorStatus\" type=\"int\">\n");
    output.write("      <values>0</values>\n");
    output.write("    </children>\n");
    output.write("  </extendedDataElements>\n");
    output.write("  <extendedDataElements name=\"userInfo\" type=\"string\">\n");
    output.write("    <values>CARSComplexType:CARSAuditUserInfo</values>\n");
    output.write("    <children name=\"registryUserName\" type=\"string\">\n");
    // output.write("      <values>uid=tangjj,cn=users,dc=taikanglife</values>\n");
    output.write("      <values>uid=" + logEvent.getUserid() + ",cn=users,dc=taikanglife</values>\n");
    output.write("    </children>\n");
    output.write("    <children name=\"appUserName\" type=\"string\">\n");
    output.write("      <values>" + logEvent.getUserid() + "</values>\n");
    output.write("    </children>\n");
    output.write("    <children name=\"domain\" type=\"string\">\n");
    output.write("      <values>Default</values>\n");
    output.write("    </children>\n");
    output.write("    <children name=\"location\" type=\"string\">\n");
    output.write("      <values>" + logEvent.getClientIP() + "</values>\n");
    output.write("    </children>\n");
    output.write("    <children name=\"locationType\" type=\"string\">\n");
    output.write("      <values>IPV4</values>\n");
    output.write("    </children>\n");
    output.write("    <children name=\"sessionId\" type=\"string\">\n");
    // output.write("      <values>059bc5fa-dffb-11df-bebf-18a9054d5aa0</values>\n");
    output.write("      <values></values>\n");
    output.write("    </children>\n");
    output.write("    <children name=\"uniqueId\" type=\"int\">\n");
    output.write("      <values>-99999</values>\n");
    output.write("    </children>\n");
    output.write("  </extendedDataElements>\n");
    output.write("  <extendedDataElements name=\"resourceInfo\" type=\"string\">\n");
    output.write("    <values>CARSComplexType:CARSAuditResourceInfo</values>\n");
    output.write("    <children name=\"nameInApp\" type=\"string\">\n");
    output.write("      <values>" + url + "</values>\n");
    output.write("    </children>\n");
    output.write("    <children name=\"nameInPolicy\" type=\"string\">\n");
    String appName = (logEvent.getApplication() != null) ? logEvent.getApplication().getName() : "";
    output.write("      <values>" + appName + "</values>\n");
    output.write("    </children>\n");
    output.write("    <children name=\"type\" type=\"string\">\n");
    output.write("      <values>url</values>\n");
    output.write("    </children>\n");
    output.write("  </extendedDataElements>\n");
    output.write("  <extendedDataElements name=\"accessDecision\" type=\"string\">\n");
    output.write("    <values>permitted</values>\n");
    output.write("  </extendedDataElements>\n");
    output.write("  <extendedDataElements name=\"permissionInfo\" type=\"string\">\n");
    output.write("    <values>CARSComplexType:CARSAuditPermissionInfo</values>\n");
    output.write("  </extendedDataElements>\n");
    output.write("  <extendedDataElements name=\"action\" type=\"string\">\n");
    output.write("    <values>httpRequest</values>\n");
    output.write("  </extendedDataElements>\n");
    output.write("  <extendedDataElements name=\"httpURLInfo\" type=\"string\">\n");
    output.write("    <values>CARSComplexType:CARSAuditHTTPURLInfo</values>\n");
    output.write("    <children name=\"url\" type=\"string\">\n");
    output.write("      <values>" + url + "</values>\n");
    output.write("    </children>\n");
    output.write("    <children name=\"method\" type=\"string\">\n");
    output.write("      <values>" + logEvent.getHttpMethod() + "</values>\n");
    output.write("    </children>\n");
    output.write("    <children name=\"responseCode\" type=\"int\">\n");
    output.write("      <values>" + logEvent.getHttpCode() + "</values>\n");
    output.write("    </children>\n");
    output.write("  </extendedDataElements>\n");
    output.write("  <extendedDataElements name=\"CARSEventVersion\" type=\"string\">\n");
    output.write("    <values>6.0</values>\n");
    output.write("  </extendedDataElements>\n");
    output
        .write("  <sourceComponentId application=\"ITAMeB 6.1 - tivoli\" component=\"IBM Tivoli Access Manager for e-business\" componentIdType=\"ProductName\"\n");
    output.write("    instanceId=\"" + webSEALInstaceId + "\" location=\"" + this.webSEALLocation
        + "\" locationType=\"FQHostname\" subComponent=\"webseald\"\n");
    output.write("    componentType=\"http://www.ibm.com/namespaces/autonomic/Tivoli_componentTypes\" />\n");
    output.write("  <situation categoryName=\"ReportSituation\">\n");
    output
        .write("    <situationType xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"ReportSituation\" reasoningScope=\"INTERNAL\" reportCategory=\"SECURITY\"></situationType>\n");
    output.write("  </situation>\n");
    output.write("</CommonBaseEvent>\n");

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.tivoli.cars.EventUploaded#upload(com.ibm.tivoli.cars.
   * WebSEALRequestLogEvent)
   */
  public abstract void handle(String logLine, WebSEALRequestLogEvent logEvent) throws Exception;

}