package com.moon.moonfamily.handler;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatHandler {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String handleMessage(String message) {
        return message;
    }
}
