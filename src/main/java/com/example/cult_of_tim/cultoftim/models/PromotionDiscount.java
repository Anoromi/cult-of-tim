package com.example.cult_of_tim.cultoftim.models;

import jakarta.persistence.*;


@Entity
@IdClass(PromotionDiscountID.class)
public class PromotionDiscount {

    @Id
    @Column(name = "promotionId")
    private Long promotionId;

    @Id
    @Column(name = "bookId")
    private Long bookId;


    @ManyToOne
    @PrimaryKeyJoinColumn(name="promotionId", referencedColumnName="id")
    private Promotion promotion;


    @ManyToOne
    @PrimaryKeyJoinColumn(name="bookId", referencedColumnName = "id")
    private Book book;

    private int discountPercentage;
}


