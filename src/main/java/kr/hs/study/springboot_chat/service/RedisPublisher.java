package kr.hs.study.springboot_chat.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisPublisher {

    private final StringRedisTemplate redisTemplate;

    public RedisPublisher(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 채팅 메시지 저장 및 퍼블리시
    public void publish(String channel, String message) {
        // Redis에 메시지 저장 (예: 채널별 메시지 리스트)
        redisTemplate.opsForList().rightPush(channel, message);
        // 채널에 메시지 퍼블리시
        redisTemplate.convertAndSend(channel, message);
    }

    // Redis에서 메시지 조회
    public List<String> getMessages(String channel) {
        return redisTemplate.opsForList().range(channel, 0, -1);
    }
}