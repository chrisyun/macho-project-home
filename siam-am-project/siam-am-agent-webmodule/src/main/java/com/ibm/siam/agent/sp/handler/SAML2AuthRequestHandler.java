/**
 * 
 */
package com.ibm.siam.agent.sp.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.common.IdentifierGenerator;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.saml2.binding.encoding.HTTPRedirectDeflateEncoder;
import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.transport.http.HTTPInTransport;
import org.opensaml.ws.transport.http.HTTPOutTransport;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.schema.XSBooleanValue;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ibm.siam.agent.web.AccessEnforcer;

import edu.internet2.middleware.shibboleth.common.profile.ProfileException;

/**
 * @author zhaodonglu
 * 
 */
public class SAML2AuthRequestHandler extends BaseProfileHandler implements SPAuthenticationHandler {
  private static Log log = LogFactory.getLog(SAML2AuthRequestHandler.class);
  
  public static final String URL_RETURN_TO_APP = "__LAST_ACCESS_URL";

  private IdentifierGenerator idGenerator;

  /**
   * 
   */
  public SAML2AuthRequestHandler() {
    super();
  }

  /**
   * @return the idGenerator
   */
  public IdentifierGenerator getIdGenerator() {
    return idGenerator;
  }

  /**
   * @param idGenerator the idGenerator to set
   */
  public void setIdGenerator(IdentifierGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  /* (non-Javadoc)
   * @see com.ibm.siam.agent.web.BaseProfileHandler#processRequest(org.opensaml.ws.transport.http.HTTPInTransport, org.opensaml.ws.transport.http.HTTPOutTransport)
   */
  @Override
  public void processRequest(HTTPInTransport inTransport, HTTPOutTransport outTransport) throws ProfileException {
    XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
    
    NameID nameid = (NameID) buildXMLObject(NameID.DEFAULT_ELEMENT_NAME);
    nameid.setFormat("urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress");
    nameid.setValue("j.doe@company.com");

    Subject subject = (Subject) buildXMLObject(Subject.DEFAULT_ELEMENT_NAME);
    subject.setNameID(nameid);

    Audience audience = (Audience) buildXMLObject(Audience.DEFAULT_ELEMENT_NAME);
    audience.setAudienceURI("urn:foo:sp.example.org");

    AudienceRestriction ar = (AudienceRestriction) buildXMLObject(AudienceRestriction.DEFAULT_ELEMENT_NAME);
    ar.getAudiences().add(audience);

    Conditions conditions = (Conditions) buildXMLObject(Conditions.DEFAULT_ELEMENT_NAME);
    conditions.getAudienceRestrictions().add(ar);

    AuthnContextClassRef classRef = (AuthnContextClassRef) buildXMLObject(AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
    String authenMethod = (String)inTransport.getAttribute(AccessEnforcer.ATTR_NAME_AUTHEN_METHOD);
    //classRef.setAuthnContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
    classRef.setAuthnContextClassRef(authenMethod);

    RequestedAuthnContext rac = (RequestedAuthnContext) buildXMLObject(RequestedAuthnContext.DEFAULT_ELEMENT_NAME);
    rac.getAuthnContextClassRefs().add(classRef);

    Issuer issuer = (Issuer)buildXMLObject(Issuer.DEFAULT_ELEMENT_NAME);
    issuer.setValue("https://spdev.saml.ibm.com");

    SAMLObjectBuilder<AuthnRequest> responseBuilder = (SAMLObjectBuilder<AuthnRequest>) builderFactory.getBuilder(AuthnRequest.DEFAULT_ELEMENT_NAME);
    AuthnRequest authRequest = responseBuilder.buildObject();
    //authRequest.setSubject(subject);
    //authRequest.setConditions(conditions);
    authRequest.setRequestedAuthnContext(rac);
    authRequest.setIssuer(issuer);
    NameIDPolicy nameIDPolicy = (NameIDPolicy) buildXMLObject(NameIDPolicy.DEFAULT_ELEMENT_NAME);
    nameIDPolicy.setAllowCreate(true);
    authRequest.setNameIDPolicy(nameIDPolicy );

    authRequest.setForceAuthn(XSBooleanValue.valueOf("false"));
    authRequest.setAssertionConsumerServiceURL("https://spdev.saml.ibm.com/sp/SSO/SAML2/POST");
    authRequest.setAttributeConsumingServiceIndex(0);
    authRequest.setProviderName("SomeProvider");
    authRequest.setID(idGenerator.generateIdentifier());
    authRequest.setVersion(SAMLVersion.VERSION_20);
    authRequest.setIssueInstant(new DateTime());
    authRequest.setDestination("https://idp.saml.ibm.com/idp/profile/SAML2/Redirect/SSO");
    authRequest.setConsent("urn:oasis:names:tc:SAML:2.0:consent:obtained");
    

    SAMLObjectBuilder<Endpoint> endpointBuilder = (SAMLObjectBuilder<Endpoint>) builderFactory.getBuilder(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
    Endpoint samlEndpoint = endpointBuilder.buildObject();
    samlEndpoint.setLocation("https://idp.saml.ibm.com/idp/profile/SAML2/Redirect/SSO");

    BasicSAMLMessageContext messageContext = new BasicSAMLMessageContext();
    messageContext.setOutboundMessageTransport(outTransport);
    messageContext.setPeerEntityEndpoint(samlEndpoint);
    messageContext.setOutboundSAMLMessage(authRequest);
    messageContext.setRelayState("relay");

    // Track return URL to application
    ServletRequestAttributes servletAttrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpServletRequest httpRequest = servletAttrs.getRequest();
    HttpSession session = httpRequest.getSession(true);
    session.setAttribute(URL_RETURN_TO_APP, httpRequest.getRequestURL().toString());
    
    // Send SAML request
    HTTPRedirectDeflateEncoder encoder = new HTTPRedirectDeflateEncoder();
    try {
      encoder.encode(messageContext);
    } catch (MessageEncodingException e) {
      throw new ProfileException(e.getMessage(), e);
    }
    return;
  }

  /**
   * Builds the requested XMLObject.
   * 
   * @param objectQName name of the XMLObject
   * 
   * @return the build XMLObject
   */
  public XMLObject buildXMLObject(QName objectQName){
      XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
      XMLObjectBuilder builder = builderFactory.getBuilder(objectQName);
      return builder.buildObject(objectQName.getNamespaceURI(), objectQName.getLocalPart(), objectQName.getPrefix());
  }


}
