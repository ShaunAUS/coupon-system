package com.example.api.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * kafka producer 로직
 */
@Component
public class CouponCreateProducer {

    private final KafkaTemplate<String,Long> kafkaTemplate;

    public CouponCreateProducer(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //topic에 데이터를 전달
    public void create(Long userId) {
        kafkaTemplate.send("coupon_create", userId);
    }
}
