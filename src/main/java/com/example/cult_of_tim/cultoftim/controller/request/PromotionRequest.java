package com.example.cult_of_tim.cultoftim.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionRequest {
    private Long id;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean globalPromotion;
}
