/*
 * Copyright 2006-2018 the original author or authors.
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

package com.consol.citrus.ftp.config.xml;

import com.consol.citrus.channel.ChannelEndpointAdapter;
import com.consol.citrus.ftp.client.SftpEndpointConfiguration;
import com.consol.citrus.ftp.server.SftpServer;
import com.consol.citrus.testng.AbstractBeanDefinitionParserTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @author Roland Huss, Christoph Deppisch
 */
public class SftpServerParserTest extends AbstractBeanDefinitionParserTest {

    @Test
    public void testSftpServerParser() {
        Map<String, SftpServer> servers = beanDefinitionContext.getBeansOfType(SftpServer.class);

        Assert.assertEquals(servers.size(), 3);

        // 1st server
        SftpServer server = servers.get("sftpServer1");
        Assert.assertEquals(server.getName(), "sftpServer1");
        Assert.assertEquals(server.getPort(), 22);
        Assert.assertFalse(server.isAutoStart());
        Assert.assertTrue(((SftpEndpointConfiguration) server.getEndpointConfiguration()).isAutoLogin());
        Assert.assertTrue(((SftpEndpointConfiguration) server.getEndpointConfiguration()).isAutoConnect());
        Assert.assertNull(server.getAllowedKeyPath());
        Assert.assertNull(server.getHostKeyPath());
        Assert.assertNull(server.getUser());
        Assert.assertNull(server.getPassword());
        Assert.assertTrue(server.getEndpointAdapter() instanceof ChannelEndpointAdapter);
        Assert.assertNotNull(server.getMessageConverter());
        Assert.assertNull(server.getActor());

        // 2nd server
        server = servers.get("sftpServer2");
        Assert.assertEquals(server.getName(), "sftpServer2");
        Assert.assertEquals(server.getPort(), 10022);
        Assert.assertFalse(server.isAutoStart());
        Assert.assertFalse(((SftpEndpointConfiguration) server.getEndpointConfiguration()).isAutoLogin());
        Assert.assertFalse(((SftpEndpointConfiguration) server.getEndpointConfiguration()).isAutoConnect());
        Assert.assertEquals(server.getAllowedKeyPath(), "classpath:com/consol/citrus/sftp/citrus_pub.pem");
        Assert.assertEquals(server.getHostKeyPath(), "classpath:com/consol/citrus/sftp/citrus.pem");
        Assert.assertEquals(server.getUser(), "foo");
        Assert.assertEquals(server.getPassword(), "bar");
        Assert.assertTrue(server.getEndpointAdapter() instanceof ChannelEndpointAdapter);
        Assert.assertNull(server.getActor());

        // 3rd server
        server = servers.get("sftpServer3");
        Assert.assertEquals(server.getName(), "sftpServer3");
        Assert.assertEquals(server.getPort(), 22);
        Assert.assertFalse(server.isAutoStart());
        Assert.assertNull(server.getAllowedKeyPath());
        Assert.assertNull(server.getHostKeyPath());
        Assert.assertNull(server.getUser());
        Assert.assertNull(server.getPassword());
        Assert.assertEquals(server.getEndpointAdapter(), beanDefinitionContext.getBean("sftpServerAdapter"));
        Assert.assertNull(server.getActor());
    }

}
