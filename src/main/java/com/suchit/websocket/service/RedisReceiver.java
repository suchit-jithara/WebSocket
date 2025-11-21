package com.suchit.websocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suchit.websocket.dto.WebSocketPayload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisReceiver {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper objectMapper;

    public RedisReceiver(SimpMessagingTemplate simpMessagingTemplate,
                         ObjectMapper objectMapper) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.objectMapper = objectMapper;
    }

    public void receiveMessage(String message) throws JsonProcessingException {
        WebSocketPayload payload = objectMapper.readValue(message, WebSocketPayload.class);
        simpMessagingTemplate.convertAndSend(payload.getDestination(), payload.getBody());
    }
}
