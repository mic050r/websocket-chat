package kr.hs.study.springboot_chat.service;

import jakarta.transaction.Transactional;
import kr.hs.study.springboot_chat.domain.Message;
import kr.hs.study.springboot_chat.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // 메시지 저장
    @Transactional
    public void saveMessage(String sender, String receiver, String content) {

        Message chatMessage = new Message();
        chatMessage.setSender(sender);
        chatMessage.setReceiver(receiver);
        chatMessage.setContent(content);
        chatMessage.setTimestamp(LocalDateTime.now());

        // 인스턴스 필드 messageRepository를 통해 save 메서드 호출
        messageRepository.save(chatMessage);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getMessages(String sender, String receiver) {
        return messageRepository.findBySenderAndReceiver(sender, receiver);
    }

}