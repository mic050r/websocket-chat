package kr.hs.study.springboot_chat.controller;

import kr.hs.study.springboot_chat.domain.Message;
import kr.hs.study.springboot_chat.service.MessageService;
import kr.hs.study.springboot_chat.service.RedisPublisher;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final RedisPublisher redisPublisher;
    private final MessageService messageService; // 메시지 저장을 위한 서비스

    public WebSocketController(RedisPublisher redisPublisher, MessageService messageService) {
        this.redisPublisher = redisPublisher;
        this.messageService = messageService;
    }

    @MessageMapping("/sendMessage")
    public void handleChatMessage(Message message) {
        // 메시지에서 필드 추출
        String sender = message.getSender();
        String receiver = message.getReceiver();
        String content = message.getContent();

        // 메시지를 데이터베이스에 저장
        messageService.saveMessage(sender, receiver, content);

        // Redis의 'chat' 채널로 메시지 퍼블리시
        redisPublisher.publish("chat", content);
    }
}