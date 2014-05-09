/*
 * Copyright 2006-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.config.xml;

import com.consol.citrus.endpoint.Endpoint;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.consol.citrus.actions.ReceiveTimeoutAction;
import com.consol.citrus.testng.AbstractActionParserTest;

/**
 * @author Christoph Deppisch
 */
public class ReceiveTimeoutActionParserTest extends AbstractActionParserTest<ReceiveTimeoutAction> {

    @Test
    public void testReceiveTimeoutActionParser() {
        assertActionCount(3);
        assertActionClassAndName(ReceiveTimeoutAction.class, "expect-timeout:myMessageEndpoint");
        
        ReceiveTimeoutAction action = getNextTestActionFromTest();
        Assert.assertEquals(action.getTimeout(), 1000L);
        Assert.assertEquals(action.getEndpoint(), beanDefinitionContext.getBean("myMessageEndpoint", Endpoint.class));
        Assert.assertNull(action.getEndpointUri());
        Assert.assertNull(action.getMessageSelector());

        
        action = getNextTestActionFromTest();
        Assert.assertNull(action.getEndpoint());
        Assert.assertEquals(action.getEndpointUri(), "channel:myMessageEndpoint");

        action = getNextTestActionFromTest();
        Assert.assertEquals(action.getTimeout(), 10000L);
        Assert.assertEquals(action.getEndpoint(), beanDefinitionContext.getBean("myMessageEndpoint", Endpoint.class));
        Assert.assertNull(action.getEndpointUri());
        Assert.assertEquals(action.getMessageSelector(), "operation='Test'");
    }
    
    @Test
    public void testCallTemplateParserUnknownTemplate() {
        try {
            createApplicationContext("failed");
            Assert.fail("Missing bean creation exception due to missing endpoint reference");
        } catch (BeanDefinitionStoreException e) {
            Assert.assertEquals(e.getCause().getMessage(), "Endpoint reference must not be empty");
        }
    }
}
