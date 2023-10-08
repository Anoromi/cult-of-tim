package com.example.cult_of_tim.cultoftim.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PromotionDiscountID implements Serializable {
    private Long promotion;
    private Long book;
}
