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

    public void setPromotion(Promotion promotion) { this.promotion = promotion;}
}


