package com.example.cult_of_tim.cultoftim.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionDto {
    private Long id;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
//    private List<PromotionDiscountDto> discounts;
    private boolean globalPromotion;
}

