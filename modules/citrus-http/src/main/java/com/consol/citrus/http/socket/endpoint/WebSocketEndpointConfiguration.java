/*
 * Copyright 2006-2015 the original author or authors.
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

package com.consol.citrus.http.socket.endpoint;

import com.consol.citrus.endpoint.EndpointConfiguration;
import com.consol.citrus.http.socket.handler.CitrusWebSocketHandler;
import com.consol.citrus.http.socket.message.WebSocketMessageConverter;

/**
 * Web socket endpoint configuration interface defines set of properties all web socket endpoints should have.
 * @author Christoph Deppisch
 * @since 2.3
 */
public interface WebSocketEndpointConfiguration extends EndpointConfiguration {

    /**
     * Gets or constructs new web socket handler.
     * @return
     */
    CitrusWebSocketHandler getHandler();

    /**
     * Sets web socket handler.
     * @return
     */
    void setHandler(CitrusWebSocketHandler handler);

    /**
     * Gets the web socket endpoint uri.
     * @return
     */
    String getEndpointUri();

    /**
     * Sets the web socket endpoint uri.
     * @param endpointUri
     */
    void setEndpointUri(String endpointUri);

    /**
     * Gets the message converter.
     * @return
     */
    WebSocketMessageConverter getMessageConverter();

    /**
     * Sets the message converter.
     *
     * @param messageConverter
     */
    void setMessageConverter(WebSocketMessageConverter messageConverter);
}