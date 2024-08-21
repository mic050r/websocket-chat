package kr.hs.study.springboot_chat.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;

    public RedisSubscriber(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // Redis에서 수신한 메시지를 WebSocket을 통해 클라이언트에게 전송
        String receivedMessage = new String(message.getBody());
        messagingTemplate.convertAndSend("/topic/messages", receivedMessage);
    }
}
