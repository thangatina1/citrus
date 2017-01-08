/*
 * Copyright 2006-2016 the original author or authors.
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

package com.consol.citrus.mail.message;

import com.consol.citrus.exceptions.CitrusRuntimeException;
import com.consol.citrus.mail.model.*;
import com.consol.citrus.message.DefaultMessage;
import org.springframework.xml.transform.StringResult;

/**
 * @author Christoph Deppisch
 * @since 2.7
 */
public class MailMessage extends DefaultMessage {

    private MailMarshaller marshaller = new MailMarshaller();

    private MailRequest mailRequest;
    private MailResponse mailResponse;

    private AcceptRequest acceptRequest;
    private AcceptResponse acceptResponse;

    /**
     * Prevent traditional instantiation.
     */
    private MailMessage() { super(); }

    /**
     * Constructor initializes new request message.
     * @param mailRequest
     */
    private MailMessage(MailRequest mailRequest) {
        super(mailRequest);
        this.mailRequest = mailRequest;
    }

    /**
     * Constructor initializes new response message.
     * @param mailResponse
     */
    private MailMessage(MailResponse mailResponse) {
        super(mailResponse);
        this.mailResponse = mailResponse;
    }

    /**
     * Constructor initializes new request message.
     * @param acceptRequest
     */
    private MailMessage(AcceptRequest acceptRequest) {
        super(acceptRequest);
        this.acceptRequest = acceptRequest;
    }

    /**
     * Constructor initializes new response message.
     * @param acceptResponse
     */
    private MailMessage(AcceptResponse acceptResponse) {
        super(acceptResponse);
        this.acceptResponse = acceptResponse;
    }

    public static MailMessage request() {
        return request(null, null, null);
    }

    public static MailMessage request(String from) {
        return request(from, null, null);
    }

    public static MailMessage request(String from, String to) {
        return request(from, to, null);
    }

    public static MailMessage request(String from, String to, String subject) {
        MailRequest request = new MailRequest();
        request.setFrom(from);
        request.setTo(to);
        request.setSubject(subject);

        return new MailMessage(request);
    }

    public static MailMessage response() {
        return response(250, null);
    }

    public static MailMessage response(int code) {
        return response(code, null);
    }

    public static MailMessage response(int code, String message) {
        MailResponse response = new MailResponse();
        response.setCode(code);
        response.setMessage(message);

        return new MailMessage(response);
    }

    public static MailMessage accept(String from, String to) {
        AcceptRequest accept = new AcceptRequest();
        accept.setFrom(from);
        accept.setTo(to);

        return new MailMessage(accept);
    }

    public static MailMessage accept() {
        return accept(true);
    }

    public static MailMessage accept(boolean result) {
        AcceptResponse acceptResponse = new AcceptResponse();
        acceptResponse.setAccept(result);

        return new MailMessage(acceptResponse);
    }

    public MailMessage subject(String subject) {
        if (mailRequest != null) {
            mailRequest.setSubject(subject);
        } else {
            throw new CitrusRuntimeException("Invalid access to method 'subject' for mail message");
        }

        return this;
    }

    public MailMessage from(String from) {
        if (mailRequest == null && acceptRequest == null) {
            throw new CitrusRuntimeException("Invalid access to method 'from' for mail message");
        }

        if (mailRequest != null) {
            mailRequest.setFrom(from);
        }

        if (acceptRequest != null) {
            acceptRequest.setFrom(from);
        }

        return this;
    }

    public MailMessage to(String to) {
        if (mailRequest == null && acceptRequest == null) {
            throw new CitrusRuntimeException("Invalid access to method 'to' for mail message");
        }

        if (mailRequest != null) {
            mailRequest.setTo(to);
        }

        if (acceptRequest != null) {
            acceptRequest.setTo(to);
        }

        return this;
    }

    public MailMessage cc(String cc) {
        if (mailRequest != null) {
            mailRequest.setCc(cc);
        } else {
            throw new CitrusRuntimeException("Invalid access to method 'cc' for mail message");
        }

        return this;
    }

    public MailMessage bcc(String bcc) {
        if (mailRequest != null) {
            mailRequest.setBcc(bcc);
        } else {
            throw new CitrusRuntimeException("Invalid access to method 'bcc' for mail message");
        }

        return this;
    }

    public MailMessage body(BodyPart bodyPart) {
        if (mailRequest != null) {
            mailRequest.setBody(bodyPart);
        } else {
            throw new CitrusRuntimeException("Invalid access to method 'subject' for mail message");
        }

        return this;
    }

    public MailMessage body(String body) {
        return body(body, "text/plain");
    }

    public MailMessage body(String body, String contentType) {
        if (mailRequest != null) {
            mailRequest.setBody(new BodyPart(body, contentType));
        } else {
            throw new CitrusRuntimeException("Invalid access to method 'subject' for mail message");
        }

        return this;
    }

    @Override
    public <T> T getPayload(Class<T> type) {
        if (String.class.equals(type)) {
            return (T) getPayload();
        } else {
            return super.getPayload(type);
        }
    }

    @Override
    public Object getPayload() {
        StringResult payloadResult = new StringResult();
        if (mailRequest != null) {
            marshaller.marshal(mailRequest, payloadResult);
            return payloadResult.toString();
        } else if (mailResponse != null) {
            marshaller.marshal(mailResponse, payloadResult);
            return payloadResult.toString();
        } else if (acceptRequest != null) {
            marshaller.marshal(acceptRequest, payloadResult);
            return payloadResult.toString();
        } else if (acceptResponse != null) {
            marshaller.marshal(acceptResponse, payloadResult);
            return payloadResult.toString();
        }

        return super.getPayload();
    }
}