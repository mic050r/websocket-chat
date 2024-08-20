package kr.hs.study.springboot_chat.repository;

import kr.hs.study.springboot_chat.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // 특정 사용자가 주고받은 메시지 조회
    List<Message> findBySenderAndReceiver(String sender, String receiver);
}