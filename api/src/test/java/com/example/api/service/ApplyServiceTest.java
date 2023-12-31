package com.example.api.service;

import com.example.api.repository.CouponRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplyServiceTest {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("쿠폰을 한번만 발급한다")
    void apply() {
        applyService.apply(1L);
        long count = couponRepository.count();
        assertThat(count).isEqualTo(1);
    }


    @Test
    @DisplayName("쿠폰을 한번에 여러번 발급한다")
    void applyV2() throws InterruptedException {
        int threadCount = 1000;

        ExecutorService executorService = Executors.newFixedThreadPool(32);//병렬 작업을 도와주는 자바 API
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);// 다른 쓰레드의 작업이 끝날떄까지 기다려주게 도와주는 클래스

        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> applyService.apply(userId)); // producer -> topic 으로 데이터 전달
            try {
                applyService.apply(userId);
            } finally {
                countDownLatch.countDown();
            }

        }
        countDownLatch.await();
        Thread.sleep(10000);
        long count = couponRepository.count();
        assertThat(count).isEqualTo(100);
    }

    @Test
    @DisplayName("한명당 한개의 쿠포만 발급")
    void applyV3() throws InterruptedException {
        int threadCount = 1000;

        ExecutorService executorService = Executors.newFixedThreadPool(32);//병렬 작업을 도와주는 자바 API
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);// 다른 쓰레드의 작업이 끝날떄까지 기다려주게 도와주는 클래스

        for (int i = 0; i < threadCount; i++) {
            long userId = i;
            executorService.submit(() -> applyService.apply(userId)); // producer -> topic 으로 데이터 전달
            try {
                applyService.apply(1L);
            } finally {
                countDownLatch.countDown();
            }

        }
        countDownLatch.await();
        Thread.sleep(10000);
        long count = couponRepository.count();
        assertThat(count).isEqualTo(100);
    }


}