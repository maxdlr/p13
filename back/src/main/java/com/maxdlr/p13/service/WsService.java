package com.maxdlr.p13.service;

import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class WsService {

  private SimpMessagingTemplate messagingTemplate;

  public WsService(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  public void send(Map<String, Object> headers, String topic, Object response) {
    SimpMessageHeaderAccessor headerAccessor = this.buildHeaders(headers);
    Message<Object> wsMessage = this.buildMessage(headerAccessor, response);

    this.messagingTemplate.convertAndSend(topic, wsMessage);
  }

  private SimpMessageHeaderAccessor buildHeaders(Map<String, Object> headers) {
    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
    headers.putAll(headers);
    return headerAccessor;
  }

  private Message<Object> buildMessage(SimpMessageHeaderAccessor headerAccessor, final Object payload) {
    return MessageBuilder.withPayload(payload)
        .setHeaders(headerAccessor)
        .build();
  }
}
