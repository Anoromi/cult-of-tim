package com.example.cult_of_tim.cultoftim.converter;

import com.example.cult_of_tim.cultoftim.dto.PromotionDto;
import com.example.cult_of_tim.cultoftim.entity.Promotion;
import org.springframework.stereotype.Component;

@Component
public class PromotionConverter {

    public PromotionDto toDto(Promotion promotion) {
        return PromotionDto.builder()
                .id(promotion.getId())
                .description(promotion.getDescription())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .globalPromotion(promotion.isGlobalPromotion())
                .build();
    }

    public Promotion toEntity(PromotionDto promotionDto) {
        return Promotion.builder()
                .id(promotionDto.getId())
                .description(promotionDto.getDescription())
                .startDate(promotionDto.getStartDate())
                .endDate(promotionDto.getEndDate())
                .globalPromotion(promotionDto.isGlobalPromotion())
                .build();
    }
}

