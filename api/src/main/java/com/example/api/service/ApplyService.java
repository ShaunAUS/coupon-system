package com.example.api.service;

import com.example.api.domain.Coupon;
import com.example.api.repository.CouponCountRepository;
import com.example.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {
    private final CouponRepository couponRepository;

    private final CouponCountRepository couponCountRepository;

    public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
    }

    public void apply(Long userId) {
        //long count = couponRepository.count();
        Long count = couponCountRepository.increment(); // redis incr 명령어 (1증가)

        //100개넘으면 종료
        if (count >100) {
            return;
        }
        couponRepository.save(new Coupon(userId));
    }
}
