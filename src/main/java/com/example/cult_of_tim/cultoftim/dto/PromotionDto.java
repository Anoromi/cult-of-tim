package com.example.cult_of_tim.cultoftim.dto;

import java.time.LocalDateTime;

public class PromotionDto {
    private Long id;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean globalPromotion;

    public Long getId() {
        return id;
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

    public boolean isGlobalPromotion() {
        return globalPromotion;
    }

    public void setGlobalPromotion(boolean globalPromotion) {
        this.globalPromotion = globalPromotion;
    }
}

