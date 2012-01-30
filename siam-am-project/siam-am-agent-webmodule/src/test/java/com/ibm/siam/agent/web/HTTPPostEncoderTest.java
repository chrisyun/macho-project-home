/*
 * Licensed to the University Corporation for Advanced Internet Development, 
 * Inc. (UCAID) under one or more contributor license agreements.  See the 
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache 
 * License, Version 2.0 (the "License"); you may not use this file except in 
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.siam.agent.web;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.joda.time.DateTime;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.saml2.binding.encoding.HTTPPostEncoder;
import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.ws.transport.http.HttpServletResponseAdapter;
import org.opensaml.xml.schema.XSBooleanValue;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Test case for {@link HTTPPostEncoder}.
 */
public class HTTPPostEncoderTest extends BaseTestCase {

  /** Velocity template engine. */
  private VelocityEngine velocityEngine;

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  public void setUp() throws Exception {
    super.setUp();

    velocityEngine = new VelocityEngine();
    velocityEngine.setProperty(RuntimeConstants.ENCODING_DEFAULT, "UTF-8");
    velocityEngine.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");
    velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
    velocityEngine.setProperty("classpath.resource.loader.class",
        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    velocityEngine.init();
  }

  /**
   * Tests encoding a SAML message to an servlet response.
   * 
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public void testResponseEncoding() throws Exception {
    SAMLObjectBuilder<StatusCode> statusCodeBuilder = (SAMLObjectBuilder<StatusCode>) builderFactory
        .getBuilder(StatusCode.DEFAULT_ELEMENT_NAME);
    StatusCode statusCode = statusCodeBuilder.buildObject();
    statusCode.setValue(StatusCode.SUCCESS_URI);

    SAMLObjectBuilder<Status> statusBuilder = (SAMLObjectBuilder<Status>) builderFactory
        .getBuilder(Status.DEFAULT_ELEMENT_NAME);
    Status responseStatus = statusBuilder.buildObject();
    responseStatus.setStatusCode(statusCode);

    SAMLObjectBuilder<Response> responseBuilder = (SAMLObjectBuilder<Response>) builderFactory
        .getBuilder(Response.DEFAULT_ELEMENT_NAME);
    Response samlMessage = responseBuilder.buildObject();
    samlMessage.setID("foo");
    samlMessage.setVersion(SAMLVersion.VERSION_20);
    samlMessage.setIssueInstant(new DateTime(0));
    samlMessage.setStatus(responseStatus);

    SAMLObjectBuilder<Endpoint> endpointBuilder = (SAMLObjectBuilder<Endpoint>) builderFactory
        .getBuilder(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
    Endpoint samlEndpoint = endpointBuilder.buildObject();
    samlEndpoint.setLocation("http://example.org");
    samlEndpoint.setResponseLocation("http://example.org/response");

    MockHttpServletResponse response = new MockHttpServletResponse();
    HttpServletResponseAdapter outTransport = new HttpServletResponseAdapter(response, false);

    BasicSAMLMessageContext messageContext = new BasicSAMLMessageContext();
    messageContext.setOutboundMessageTransport(outTransport);
    messageContext.setPeerEntityEndpoint(samlEndpoint);
    messageContext.setOutboundSAMLMessage(samlMessage);
    messageContext.setRelayState("relay");

    HTTPPostEncoder encoder = new HTTPPostEncoder(velocityEngine, "/templates/saml2-post-binding.vm");
    encoder.encode(messageContext);

    assertEquals("Unexpected content type", "text/html", response.getContentType());
    assertEquals("Unexpected character encoding", response.getCharacterEncoding(), "UTF-8");
    assertEquals("Unexpected cache controls", "no-cache, no-store", response.getHeader("Cache-control"));
    assertEquals(833802980, response.getContentAsString().hashCode());
  }

  @SuppressWarnings("unchecked")
  public void testRequestEncoding() throws Exception {
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
    classRef.setAuthnContextClassRef("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");

    RequestedAuthnContext rac = (RequestedAuthnContext) buildXMLObject(RequestedAuthnContext.DEFAULT_ELEMENT_NAME);
    rac.getAuthnContextClassRefs().add(classRef);

    SAMLObjectBuilder<AuthnRequest> responseBuilder = (SAMLObjectBuilder<AuthnRequest>) builderFactory.getBuilder(AuthnRequest.DEFAULT_ELEMENT_NAME);
    AuthnRequest authRequest = responseBuilder.buildObject();
    authRequest.setSubject(subject);
    authRequest.setConditions(conditions);
    authRequest.setRequestedAuthnContext(rac);

    authRequest.setForceAuthn(XSBooleanValue.valueOf("true"));
    authRequest.setAssertionConsumerServiceURL("https://spdev.saml.ibm.com/Shibboleth.sso/SAML2/POST");
    authRequest.setAttributeConsumingServiceIndex(0);
    authRequest.setProviderName("SomeProvider");
    authRequest.setID("abe567de6");
    authRequest.setVersion(SAMLVersion.VERSION_20);
    authRequest.setIssueInstant(new DateTime());
    authRequest.setDestination("https://idp.saml.ibm.com/idp/profile/SAML2/Redirect/SSO");
    authRequest.setConsent("urn:oasis:names:tc:SAML:2.0:consent:obtained");
    

    SAMLObjectBuilder<Endpoint> endpointBuilder = (SAMLObjectBuilder<Endpoint>) builderFactory.getBuilder(AssertionConsumerService.DEFAULT_ELEMENT_NAME);
    Endpoint samlEndpoint = endpointBuilder.buildObject();
    samlEndpoint.setLocation("http://example.org");
    samlEndpoint.setResponseLocation("http://example.org/response");

    MockHttpServletResponse response = new MockHttpServletResponse();
    HttpServletResponseAdapter outTransport = new HttpServletResponseAdapter(response, false);

    BasicSAMLMessageContext messageContext = new BasicSAMLMessageContext();
    messageContext.setOutboundMessageTransport(outTransport);
    messageContext.setPeerEntityEndpoint(samlEndpoint);
    messageContext.setOutboundSAMLMessage(authRequest);
    messageContext.setRelayState("relay");

    HTTPPostEncoder encoder = new HTTPPostEncoder(velocityEngine, "/templates/saml2-post-binding.vm");
    encoder.encode(messageContext);

    assertEquals("Unexpected content type", "text/html", response.getContentType());
    assertEquals("Unexpected character encoding", response.getCharacterEncoding(), "UTF-8");
    assertEquals("Unexpected cache controls", "no-cache, no-store", response.getHeader("Cache-control"));
    assertEquals("", response.getContentAsString());
  }
}
