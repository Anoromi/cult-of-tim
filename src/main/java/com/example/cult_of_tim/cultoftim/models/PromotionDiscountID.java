package com.example.cult_of_tim.cultoftim.models;

import java.io.Serializable;

public class PromotionDiscountID implements Serializable {
    private Long promotion;
    private Long book;

    public Long getPromotion() {
        return promotion;
    }

    public void setPromotion(Long promotion) {
        this.promotion = promotion;
    }

    public Long getBook() {
        return book;
    }

    public void setBook(Long book) {
        this.book = book;
    }
}
