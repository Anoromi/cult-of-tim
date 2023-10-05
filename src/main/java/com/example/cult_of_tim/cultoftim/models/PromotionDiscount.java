package com.example.cult_of_tim.cultoftim.models;

import jakarta.persistence.*;


@Entity
@IdClass(PromotionDiscountID.class)
public class PromotionDiscount {

    //@Id
    //@Column(name = "promotionId")
    //private Long promotionId;

    //@Id
    //@Column(name = "bookId")
    //private Long bookId;


    @Id
    @ManyToOne
    @PrimaryKeyJoinColumn(name="promotionId", referencedColumnName="promotionId")
    private Promotion promotion;


    @Id
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "bookId", referencedColumnName = "bookId")
    private Book book;

    private int discountPercentage;
}


