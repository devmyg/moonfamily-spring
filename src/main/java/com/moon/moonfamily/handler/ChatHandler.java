package com.moon.moonfamily.handler;

import com.moon.moonfamily.dto.MessageDto;
import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatHandler {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ResponseDto<MessageDto> handleMessage(@Payload String message, SimpMessageHeaderAccessor accessor) {
        return chatService.send(message, accessor.getFirstNativeHeader("Authorization"));
    }
}
