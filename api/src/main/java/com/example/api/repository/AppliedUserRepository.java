package com.example.api.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * 1인당 쿠폰 1개 발급 로직사용시 필요
 */
@Repository
public class AppliedUserRepository {
    private final RedisTemplate<String, Long> redisTemplate;

    public AppliedUserRepository(RedisTemplate<String, Long> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    //redis set 사용
    public Long add(Long userId) {
        return redisTemplate
                .opsForSet()
                .add("applied_user", userId);
    }
}
