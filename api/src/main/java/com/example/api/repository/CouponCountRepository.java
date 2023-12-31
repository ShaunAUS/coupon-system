package com.example.api.repository;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CouponCountRepository {
    private final RedisTemplate<String, String> redisTemplate;

    public CouponCountRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //redis incr 명령어 사용
    public Long increment() {
        return redisTemplate
                .opsForValue()
                .increment("coupon_count");
    }
}
