package com.example.cult_of_tim.cultoftim.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionDiscountView {
    private Long promotionId;
    private Long bookId;
    private String bookName;
    private int discountPercentage;
}
