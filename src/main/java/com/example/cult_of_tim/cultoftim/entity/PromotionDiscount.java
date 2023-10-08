package com.example.cult_of_tim.cultoftim.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@IdClass(PromotionDiscountID.class)
public class PromotionDiscount {

    @Id
    @ManyToOne
    @JoinColumn(name="promotion_id", referencedColumnName="promotion_id")
    private Promotion promotion;


    @Id
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private Book book;

    private int discountPercentage;
}


