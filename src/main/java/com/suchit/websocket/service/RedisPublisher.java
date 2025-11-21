package com.suchit.websocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suchit.websocket.dto.WebSocketPayload;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisPublisher {
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public RedisPublisher(StringRedisTemplate redisTemplate,
                          ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(String destination, Object body) throws JsonProcessingException {
        WebSocketPayload payload = new WebSocketPayload(destination, body);
        String msg = objectMapper.writeValueAsString(payload);
        redisTemplate.convertAndSend("websocket-channel", msg);
    }
}
