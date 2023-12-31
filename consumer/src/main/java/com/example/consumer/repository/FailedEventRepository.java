package com.example.consumer.repository;

import com.example.consumer.domain.FailedEvent;
import org.springframework.data.jpa.repository.JpaRepository;


//consumer에서 쿠폰 발급하다가 실패할경우
public interface FailedEventRepository extends JpaRepository<FailedEvent, Long> {
}
