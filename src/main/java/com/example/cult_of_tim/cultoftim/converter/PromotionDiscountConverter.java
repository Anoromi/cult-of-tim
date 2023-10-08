package com.example.cult_of_tim.cultoftim.converter;

import com.example.cult_of_tim.cultoftim.dto.PromotionDiscountDto;
import com.example.cult_of_tim.cultoftim.entity.PromotionDiscount;
import org.springframework.stereotype.Component;

@Component
public class PromotionDiscountConverter {

    public PromotionDiscountDto toDto(PromotionDiscount promotionDiscount) {
        return PromotionDiscountDto.builder()
                .promotionId(promotionDiscount.getPromotion().getId())
                .bookId(promotionDiscount.getBook().getId())
                .discountPercentage(promotionDiscount.getDiscountPercentage())
                .build();
    }

    public PromotionDiscount toEntity(PromotionDiscountDto promotionDiscountDto) {
        throw new UnsupportedOperationException();
    }
}

