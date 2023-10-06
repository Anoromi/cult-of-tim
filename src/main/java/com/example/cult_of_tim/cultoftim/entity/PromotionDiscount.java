package com.example.cult_of_tim.cultoftim.entity;

import jakarta.persistence.*;


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

    public int getDiscountPercentage() { return discountPercentage; }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Book getBook() { return book; }

    public void setBook(Book book) {
        this.book = book;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) { this.promotion = promotion;}
}


