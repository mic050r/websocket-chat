package kr.hs.study.springboot_chat.controller;

import kr.hs.study.springboot_chat.domain.Message;
import kr.hs.study.springboot_chat.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final MessageService messageService;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    // 모든 메시지 조회
    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    // 특정 사용자가 주고받은 메시지 조회 (예: 사용자 간 개인 채팅)
    @GetMapping("/messages/between")
    public List<Message> getMessagesBetweenUsers(@RequestParam("sender") String sender,
                                                 @RequestParam("receiver") String receiver) {
        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("Sender and receiver must be provided.");
        }
        // sender -> receiver 메시지 가져오기
        List<Message> senderToReceiverMessages = messageService.getMessages(sender, receiver);

        // receiver -> sender 메시지 가져오기
        List<Message> receiverToSenderMessages = messageService.getMessages(receiver, sender);

        // 모든 메시지를 한 리스트에 합치고 최신순으로 정렬
        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(senderToReceiverMessages);
        allMessages.addAll(receiverToSenderMessages);

        // 타임스탬프를 기준으로 정렬 (오름차순, 즉 오래된 순)
        allMessages.sort(Comparator.comparing(Message::getTimestamp));
        return allMessages;
    }
}