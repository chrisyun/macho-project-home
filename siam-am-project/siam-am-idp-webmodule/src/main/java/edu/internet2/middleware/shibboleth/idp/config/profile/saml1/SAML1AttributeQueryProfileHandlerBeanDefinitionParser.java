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

package edu.internet2.middleware.shibboleth.idp.config.profile.saml1;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

import com.ibm.siam.am.idp.config.profile.ProfileHandlerNamespaceHandler;

import edu.internet2.middleware.shibboleth.idp.config.profile.saml2.AbstractSAML2ProfileHandlerBeanDefinitionParser;
import edu.internet2.middleware.shibboleth.idp.profile.saml1.AttributeQueryProfileHandler;

/**
 * Spring bean definition parser for {@link AttributeQueryProfileHandler} profile handlers.
 */
public class SAML1AttributeQueryProfileHandlerBeanDefinitionParser extends
        AbstractSAML2ProfileHandlerBeanDefinitionParser {

    /** Schema type. */
    public static final QName SCHEMA_TYPE = new QName(ProfileHandlerNamespaceHandler.NAMESPACE, "SAML1AttributeQuery");

    /** {@inheritDoc} */
    protected Class getBeanClass(Element arg0) {
        return AttributeQueryProfileHandler.class;
    }

    /** {@inheritDoc} */
    protected void doParse(Element config, BeanDefinitionBuilder builder) {
        super.doParse(config, builder);
    }
}