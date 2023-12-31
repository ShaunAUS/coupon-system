package com.example.api.service;

import com.example.api.domain.Coupon;
import com.example.api.producer.CouponCreateProducer;
import com.example.api.repository.AppliedUserRepository;
import com.example.api.repository.CouponCountRepository;
import com.example.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {
    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;
    private final CouponCreateProducer couponCreateProducer;
    private final AppliedUserRepository appliedUserRepository;

    public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository, CouponCreateProducer couponCreateProducer, AppliedUserRepository appliedUserRepository) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
        this.couponCreateProducer = couponCreateProducer;
        this.appliedUserRepository = appliedUserRepository;
    }

    public void apply(Long userId) {

        //유저 한명당 한개만 쿠폰 발급 하는지 체크
        Long apply = appliedUserRepository.add(userId);

        // 이미 발급 요청 했던 유저임
        if(apply != 1) {
            return;
        }


        //long count = couponRepository.count();  redis 적용전
        Long count = couponCountRepository.increment(); // redis incr 명령어 (1증가)

        //100개넘으면 종료
        if (count >100) {
            return;
        }
        //couponRepository.save(new Coupon(userId)); kafka 적용전
        couponCreateProducer.create(userId); // kafka 적용, producer -> topic 으로 데이터 전달
    }
}
