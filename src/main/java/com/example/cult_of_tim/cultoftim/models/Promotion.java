package com.example.cult_of_tim.cultoftim.models;

import com.cult_of_tim.auth.cultoftimauth.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotionId")
    private Long id;

    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @OneToMany
    private List<PromotionDiscount> discounts;

    private boolean globalPromotion;

    public Long getId() {
        return id;
    }


    public boolean isGlobalPromotion() {
        return globalPromotion;
    }

    public void setGlobalPromotion(boolean globalPromotion) {
        this.globalPromotion = globalPromotion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }


    public List<PromotionDiscount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<PromotionDiscount> discounts) {
        this.discounts = discounts;
    }

}
