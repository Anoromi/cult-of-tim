package com.example.cult_of_tim.cultoftim.converter;

import com.example.cult_of_tim.cultoftim.dto.PromotionDto;
import com.example.cult_of_tim.cultoftim.entity.Promotion;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class PromotionConverter {

    public PromotionDto toDto(Promotion promotion) {
        return PromotionDto.builder()
                .id(promotion.getId())
                .description(promotion.getDescription())
                .startDate(toDate(promotion.getStartDate()))
                .endDate(toDate(promotion.getEndDate()))
                .globalPromotion(promotion.isGlobalPromotion())
                .build();
    }

    public Promotion toEntity(PromotionDto promotionDto) {
        return Promotion.builder()
                .id(promotionDto.getId())
                .description(promotionDto.getDescription())
                .startDate(toLocalDateTime(promotionDto.getStartDate()))
                .endDate(toLocalDateTime(promotionDto.getEndDate()))
                .globalPromotion(promotionDto.isGlobalPromotion())
                .build();
    }

    LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    Date toDate(LocalDateTime date) {
        return Date.from(date.toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}

