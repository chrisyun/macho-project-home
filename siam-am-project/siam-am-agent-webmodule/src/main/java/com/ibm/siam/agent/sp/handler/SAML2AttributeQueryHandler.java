/**
 * 
 */
package com.ibm.siam.agent.sp.handler;

import java.security.NoSuchAlgorithmException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.IdentifierGenerator;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.impl.SecureRandomIdentifierGenerator;
import org.opensaml.saml2.binding.encoding.HTTPSOAP11Encoder;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeQuery;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.soap.client.BasicSOAPMessageContext;
import org.opensaml.ws.soap.client.http.HttpClientBuilder;
import org.opensaml.ws.soap.client.http.HttpSOAPClient;
import org.opensaml.ws.soap.client.http.TLSProtocolSocketFactory;
import org.opensaml.ws.soap.common.SOAPException;
import org.opensaml.ws.soap.soap11.Body;
import org.opensaml.ws.soap.soap11.Envelope;
import org.opensaml.ws.transport.InTransport;
import org.opensaml.ws.transport.OutTransport;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.util.XMLHelper;


import edu.internet2.middleware.shibboleth.common.profile.ProfileException;
import edu.internet2.middleware.shibboleth.common.profile.ProfileHandler;

/**
 * @author zhaodonglu
 * 
 */
public class SAML2AttributeQueryHandler implements ProfileHandler {
  private static Log log = LogFactory.getLog(SAML2AttributeQueryHandler.class);

  /**
   * 
   */
  public SAML2AttributeQueryHandler() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ibm.siam.agent.web.ProfileHandler#process(javax.servlet.http.
   * HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  public void process(FilterChain chain, HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    String transientNameId = "";
  }



  /* (non-Javadoc)
   * @see edu.internet2.middleware.shibboleth.common.profile.ProfileHandler#processRequest(org.opensaml.ws.transport.InTransport, org.opensaml.ws.transport.OutTransport)
   */
  public void processRequest(InTransport inTransport, OutTransport outTransport) throws ProfileException {
    // TODO Auto-generated method stub
    
  }
  /**
   * Builds the requested XMLObject.
   * 
   * @param objectQName
   *          name of the XMLObject
   * 
   * @return the build XMLObject
   */
  public XMLObject buildXMLObject(QName objectQName) {
    XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
    XMLObjectBuilder builder = builderFactory.getBuilder(objectQName);
    return builder.buildObject(objectQName.getNamespaceURI(), objectQName.getLocalPart(), objectQName.getPrefix());
  }

  private static Envelope buildSOAP11Envelope(XMLObject payload) {
    XMLObjectBuilderFactory bf = Configuration.getBuilderFactory();
    Envelope envelope = (Envelope) bf.getBuilder(Envelope.DEFAULT_ELEMENT_NAME).buildObject(
        Envelope.DEFAULT_ELEMENT_NAME);
    Body body = (Body) bf.getBuilder(Body.DEFAULT_ELEMENT_NAME).buildObject(Body.DEFAULT_ELEMENT_NAME);

    body.getUnknownXMLObjects().add(payload);
    envelope.setBody(body);

    return envelope;
  }

  private void sendAttributeQuery(String transientNameId) throws NoSuchAlgorithmException {
    BasicParserPool parserPool = new BasicParserPool();
    parserPool.setNamespaceAware(true);

    // Build the outgoing message structures
    XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
    AttributeQuery attrQueryReq = buildAttributeRequestObject(builderFactory, transientNameId);

    Envelope envelope = buildSOAP11Envelope(attrQueryReq);

    // SOAP context used by the SOAP client
    BasicSOAPMessageContext soapContext = new BasicSOAPMessageContext();
    soapContext.setOutboundMessage(envelope);

    // Build the SOAP client
    HttpClientBuilder clientBuilder = new HttpClientBuilder();
    clientBuilder.setHttpsProtocolSocketFactory(new TLSProtocolSocketFactory(null, AllTrustedManagerFactory.X509_MANAGERS[0]));

    HttpSOAPClient soapClient = new HttpSOAPClient(clientBuilder.buildClient(), parserPool);

    // Send the message
    try {
      String serverEndpoint = "https://idp.saml.ibm.com/idp/profile/SAML2/SOAP/AttributeQuery";
      soapClient.send(serverEndpoint, soapContext);
    } catch (SOAPException e) {
      e.printStackTrace();
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (org.opensaml.xml.security.SecurityException e) {
      e.printStackTrace();
    }

    // Access the SOAP response envelope
    Envelope soapResponse = (Envelope) soapContext.getInboundMessage();

    System.out.println("SOAP Response was:");
    System.out.println(XMLHelper.prettyPrintXML(soapResponse.getDOM()));
  }

  /**
   * @param response
   * @param transientNameId
   * @throws ConfigurationException
   * @throws NoSuchAlgorithmException
   * @throws Exception
   * @throws MessageEncodingException
   */
  private XMLObject getAttributeQueryResult(String transientNameId) throws ConfigurationException,
      NoSuchAlgorithmException, Exception, MessageEncodingException {
    DefaultBootstrap.bootstrap();

    VelocityEngine velocityEngine = new VelocityEngine();
    velocityEngine.setProperty(RuntimeConstants.ENCODING_DEFAULT, "UTF-8");
    velocityEngine.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
    velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
    velocityEngine.setProperty("classpath.resource.loader.class",
        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    velocityEngine.init();
    XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();

    AttributeQuery attrQueryReq = buildAttributeRequestObject(builderFactory, transientNameId);

    SAMLObjectBuilder<Endpoint> endpointBuilder = (SAMLObjectBuilder<Endpoint>) builderFactory
        .getBuilder(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
    Endpoint samlEndpoint = endpointBuilder.buildObject();
    samlEndpoint.setLocation("https://idp.saml.ibm.com/idp/profile/SAML2/Redirect/SSO");
    samlEndpoint.setResponseLocation("https://spdev.saml.ibm.com/sp/POST");

    BasicSAMLMessageContext messageContext = new BasicSAMLMessageContext();
    messageContext.setPeerEntityEndpoint(samlEndpoint);
    messageContext.setInboundSAMLMessage(attrQueryReq);
    messageContext.setRelayState("relay");

    HTTPSOAP11Encoder encoder = new HTTPSOAP11Encoder();
    encoder.encode(messageContext);

    XMLObject samlResp = messageContext.getOutboundMessage();
    return samlResp;
  }

  /**
   * @param builderFactory
   * @param transientNameId
   * @return
   * @throws NoSuchAlgorithmException
   */
  private AttributeQuery buildAttributeRequestObject(XMLObjectBuilderFactory builderFactory, String transientNameId)
      throws NoSuchAlgorithmException {
    IdentifierGenerator IdGenerator = new SecureRandomIdentifierGenerator();
    NameID nameid = (NameID) buildXMLObject(NameID.DEFAULT_ELEMENT_NAME);
    nameid.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:transient");
    nameid.setValue(transientNameId);
    nameid.setNameQualifier("https://idp.saml.ibm.com/idp/shibboleth");
    nameid.setSPNameQualifier("https://sp.saml.ibm.com/shibboleth");

    Subject subject = (Subject) buildXMLObject(Subject.DEFAULT_ELEMENT_NAME);
    subject.setNameID(nameid);

    Issuer issuer = (Issuer) buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME);
    issuer.setValue("https://spdev.saml.ibm.com/shibboleth");

    SAMLObjectBuilder<AttributeQuery> responseBuilder = (SAMLObjectBuilder<AttributeQuery>) builderFactory
        .getBuilder(AttributeQuery.DEFAULT_ELEMENT_NAME);
    AttributeQuery attrQueryReq = responseBuilder.buildObject();
    attrQueryReq.setSubject(subject);

    attrQueryReq.setIssuer(issuer);
    attrQueryReq.setID(IdGenerator.generateIdentifier());
    attrQueryReq.setVersion(SAMLVersion.VERSION_20);
    attrQueryReq.setIssueInstant(new DateTime());
    attrQueryReq.setDestination("https://idp.saml.ibm.com/idp/profile/SAML2/SOAP/AttributeQuery");
    attrQueryReq.setConsent("urn:oasis:names:tc:SAML:2.0:consent:obtained");

    Attribute attrOfGivenName = (Attribute) buildXMLObject(Attribute.DEFAULT_ELEMENT_NAME);
    attrOfGivenName.setFriendlyName("givenName");
    attrOfGivenName.setName("urn:oid:2.5.4.42");
    attrOfGivenName.setNameFormat("urn:oasis:names:tc:SAML:2.0:attrname-format:uri");
    //attrQueryReq.getAttributes().add(attrOfGivenName);
    return attrQueryReq;
  }

}
