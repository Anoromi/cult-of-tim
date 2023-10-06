package com.example.cult_of_tim.cultoftim.converter;

import com.example.cult_of_tim.cultoftim.dto.PromotionDto;
import com.example.cult_of_tim.cultoftim.entity.Promotion;
import org.springframework.stereotype.Component;

@Component
public class PromotionConverter {

    public PromotionDto toDto(Promotion promotion) {
        PromotionDto promotionDto = new PromotionDto();
        promotionDto.setId(promotion.getId());
        promotionDto.setDescription(promotion.getDescription());
        promotionDto.setStartDate(promotion.getStartDate());
        promotionDto.setEndDate(promotion.getEndDate());
        promotionDto.setGlobalPromotion(promotion.isGlobalPromotion());
        return promotionDto;
    }

    public Promotion toEntity(PromotionDto promotionDto) {
        Promotion promotion = new Promotion();
        promotion.setId(promotionDto.getId());
        promotion.setDescription(promotionDto.getDescription());
        promotion.setStartDate(promotionDto.getStartDate());
        promotion.setEndDate(promotionDto.getEndDate());
        promotion.setGlobalPromotion(promotionDto.isGlobalPromotion());
        return promotion;
    }
}

