package com.example.cult_of_tim.cultoftim.models;

import java.time.LocalDateTime;
import java.util.List;

public class Promotion {

    private Long id;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Long> userIDs;
    private boolean globalPromotion;

    public Long getId() {
        return id;
    }

    public List<Long> getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(List<Long> userIDs) {
        this.userIDs = userIDs;
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


}
