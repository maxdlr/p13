package com.maxdlr.p13.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxdlr.p13.repository.WsMessageRepository;

/**
 * MessageRestController
 */
@RestController
@RequestMapping("api/messages")
public class MessageRestController {

  WsMessageRepository messageRepository;

  MessageRestController(WsMessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  @GetMapping
  public ResponseEntity getAllMessages() {
    return ResponseEntity.ok(this.messageRepository.findAll());
  }
}
