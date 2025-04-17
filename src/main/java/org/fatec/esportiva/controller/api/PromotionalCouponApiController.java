package org.fatec.esportiva.controller.api;

import lombok.RequiredArgsConstructor;
import org.fatec.esportiva.dto.response.PromotionalCouponResponseDto;
import org.fatec.esportiva.service.PromotionalCouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/promotionalCoupon")
@RequiredArgsConstructor
public class PromotionalCouponApiController {
    private final PromotionalCouponService promotionalCouponService;

    @GetMapping("/{code}")
    public ResponseEntity<PromotionalCouponResponseDto> getPromotionalCoupon(@PathVariable String code){
        return ResponseEntity.ok(promotionalCouponService.getPromotionalCoupon(code));
    }
}
