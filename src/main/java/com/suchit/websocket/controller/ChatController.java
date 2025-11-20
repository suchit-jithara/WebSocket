package com.suchit.websocket.controller;

import com.suchit.websocket.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/send")          // client sends to: /app/send
    @SendTo("/topic/messages")        // server broadcasts to: /topic/messages
    public ChatMessage processMessage(ChatMessage msg) {
        return msg;   // simply return back to all subscribers
    }
}
