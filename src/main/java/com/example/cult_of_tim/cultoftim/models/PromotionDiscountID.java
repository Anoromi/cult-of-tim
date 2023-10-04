package com.example.cult_of_tim.cultoftim.models;

import java.io.Serializable;

public class PromotionDiscountID implements Serializable {
    private Long promotionId;
    private Long bookId;

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
