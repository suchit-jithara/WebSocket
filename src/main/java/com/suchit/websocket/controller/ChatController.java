package com.suchit.websocket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.suchit.websocket.dto.ChatMessage;
import com.suchit.websocket.service.RedisPublisher;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final RedisPublisher publisher;

    public ChatController(RedisPublisher publisher) {
        this.publisher = publisher;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage message) throws JsonProcessingException {
        // Publish message to Redis
        publisher.publish("/topic/messages", message);
    }
}
