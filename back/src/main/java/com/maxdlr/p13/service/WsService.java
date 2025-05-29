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

  public <RecordType> void send(Map<String, Object> headers, String topic, RecordType response) {
    SimpMessageHeaderAccessor headerAccessor = this.buildHeaders(headers);
    Message<RecordType> wsMessage = this.buildMessage(headerAccessor, response);

    System.out.println(String.format("""
        --------------------------------- SENDING MESSAGE ---------------------------------
        headers: %s,
        topic: %s,
        response: %s
        -----------------------------------------------------------------------------------
              """, headers.toString(), topic, response.toString()));

    this.messagingTemplate.convertAndSend("/topic/" + topic, wsMessage);

    System.out.println("--------------------------------- MESSAGE SENT ---------------------------------");
  }

  private SimpMessageHeaderAccessor buildHeaders(Map<String, Object> headers) {
    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
    headers.putAll(headers);
    return headerAccessor;
  }

  private <RecordType> Message<RecordType> buildMessage(SimpMessageHeaderAccessor headerAccessor,
      final RecordType payload) {
    return MessageBuilder.withPayload(payload)
        .setHeaders(headerAccessor)
        .build();
  }
}
