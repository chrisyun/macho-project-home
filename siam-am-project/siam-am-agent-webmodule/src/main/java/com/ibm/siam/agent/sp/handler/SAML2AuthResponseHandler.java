/**
 * 
 */
package com.ibm.siam.agent.sp.handler;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.servlet.http.HttpSession;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.saml2.binding.decoding.HTTPPostDecoder;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.EncryptedAssertion;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.encryption.Decrypter;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.transport.http.HTTPInTransport;
import org.opensaml.ws.transport.http.HTTPOutTransport;
import org.opensaml.xml.encryption.DecryptionException;
import org.opensaml.xml.encryption.InlineEncryptedKeyResolver;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.security.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xml.security.keyinfo.StaticKeyInfoCredentialResolver;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.util.XMLHelper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.w3c.dom.Element;

import com.ibm.siam.agent.common.SSOPrincipal;

import edu.internet2.middleware.shibboleth.common.profile.ProfileException;

/**
 * @author zhaodonglu
 * 
 */
public class SAML2AuthResponseHandler extends BaseProfileHandler {

  /**
   * 
   */
  public SAML2AuthResponseHandler() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see edu.internet2.middleware.shibboleth.common.profile.ProfileHandler#
   * processRequest(org.opensaml.ws.transport.InTransport,
   * org.opensaml.ws.transport.OutTransport)
   */
  public void processRequest(HTTPInTransport inTransport, HTTPOutTransport outTransport) throws ProfileException {
    BasicSAMLMessageContext<SAMLObject, SAMLObject, SAMLObject> messageContext = new BasicSAMLMessageContext<SAMLObject, SAMLObject, SAMLObject>();
    messageContext.setInboundMessageTransport(inTransport);

    try {
      HTTPPostDecoder decoder = new HTTPPostDecoder();
      decoder.decode(messageContext);
      SAMLObject smlObject = (SAMLObject) messageContext.getInboundSAMLMessage();
      Element dom = smlObject.getDOM();
      XMLHelper.writeNode(dom, System.out);
      System.out.println(XMLHelper.prettyPrintXML(dom));

      Response response = (Response) smlObject;
      Status status = response.getStatus();
      if (status == null || !StatusCode.SUCCESS_URI.equals(status.getStatusCode().getValue())) {
        throw new ProfileException(String.format("Got failure status code: %s", status));
      }

      HttpSession session = null;
      for (EncryptedAssertion encryptedAssertion : response.getEncryptedAssertions()) {
        Decrypter decrypter = this.getDescryptor();
        Assertion resp = decrypter.decrypt(encryptedAssertion);
        XMLHelper.writeNode(resp.getDOM(), System.out);
        System.out.println(XMLHelper.prettyPrintXML(resp.getDOM()));

        SSOPrincipal principal = new SSOPrincipal();

        // Get NameID
        String transientNameId = resp.getSubject().getNameID().getValue();

        // Get AuthnStatment
        String authenMethod = null;
        for (AuthnStatement authnStatement : resp.getAuthnStatements()) {
          authenMethod = authnStatement.getAuthnContext().getAuthnContextClassRef().getAuthnContextClassRef();
          break;
        }

        // Get all attributes
        String uid = null;
        String cn = null;
        for (AttributeStatement aStatement : resp.getAttributeStatements()) {
          for (Attribute attr : aStatement.getAttributes()) {
            if ("uid".equalsIgnoreCase(attr.getFriendlyName())) {
              uid = ((XSString) attr.getAttributeValues().get(0)).getValue();
            } else if ("cn".equalsIgnoreCase(attr.getFriendlyName())) {
              cn = ((XSString) attr.getAttributeValues().get(0)).getValue();
            }
            // TODO need to hold array etc.
            principal.setAttribute(attr.getFriendlyName(),  ((XSString) attr.getAttributeValues().get(0)).getValue());
          }

        }
        if (uid != null) {
          principal.setAuthenMethod(authenMethod);
          principal.setUid(uid);
          principal.setCn(cn);
          ServletRequestAttributes servletAttrs = (ServletRequestAttributes) RequestContextHolder
              .currentRequestAttributes();
          session = servletAttrs.getRequest().getSession(true);
          session.setAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR, principal);
        }
      }
      if (session != null && session.getAttribute(SSOPrincipal.NAME_OF_SESSION_ATTR) != null) {
        outTransport.sendRedirect((String) session.getAttribute(SAML2AuthRequestHandler.URL_RETURN_TO_APP));
        return;
      }
    } catch (MessageDecodingException e) {
      throw new ProfileException(e);
    } catch (KeyStoreException e) {
      throw new ProfileException(e);
    } catch (NoSuchAlgorithmException e) {
      throw new ProfileException(e);
    } catch (CertificateException e) {
      throw new ProfileException(e);
    } catch (UnrecoverableEntryException e) {
      throw new ProfileException(e);
    } catch (org.opensaml.xml.security.SecurityException e) {
      throw new ProfileException(e);
    } catch (IOException e) {
      throw new ProfileException(e);
    } catch (DecryptionException e) {
      throw new ProfileException(e);
    } catch (Exception e) {
      throw new ProfileException(e);
    }
  }

  private Decrypter getDescryptor() throws KeyStoreException, ProfileException, NoSuchAlgorithmException,
      CertificateException, IOException, UnrecoverableEntryException {
    KeyStore keyStore = KeyStore.getInstance("pkcs12");
    String keyStorePath = "/certs/mystore.p12";
    InputStream in = this.getClass().getResourceAsStream(keyStorePath);
    if (in == null) {
      throw new ProfileException(String.format("Could not load key store: %s", keyStorePath));
    }

    String storeFilePass = "passw0rd";
    String keyPassword = "passw0rd";
    keyStore.load(in, storeFilePass.toCharArray());

    String alias = "my";
    KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias,
        new KeyStore.PasswordProtection(keyPassword.toCharArray()));
    X509Certificate certificate = (X509Certificate) keyEntry.getCertificate();
    PrivateKey privateKey = keyEntry.getPrivateKey();
    BasicX509Credential credential = new BasicX509Credential();
    credential.setEntityCertificate(certificate);
    credential.setPrivateKey(privateKey);

    KeyInfoCredentialResolver keyResolver = new StaticKeyInfoCredentialResolver(credential);
    InlineEncryptedKeyResolver encryptionKeyResolver = new InlineEncryptedKeyResolver();

    Decrypter decrypter = new Decrypter(null, keyResolver, encryptionKeyResolver);
    return decrypter;
  }
}
