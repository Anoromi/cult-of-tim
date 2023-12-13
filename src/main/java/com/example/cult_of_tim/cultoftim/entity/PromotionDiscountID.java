package com.example.cult_of_tim.cultoftim.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDiscountID implements Serializable {
    private Long promotion;
    private Long book;
}
