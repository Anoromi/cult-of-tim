package com.example.cult_of_tim.cultoftim.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiscountIndependentView {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String bookName;
    private int discountPercentage;
}
