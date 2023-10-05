package com.example.cult_of_tim.cultoftim.service;

import com.example.cult_of_tim.cultoftim.models.Promotion;
import com.example.cult_of_tim.cultoftim.models.PromotionDiscount;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PromotionService {
    Optional<Promotion> getPromotionById(Long id);

    List<Promotion> getAllPromotions();

    boolean isGlobal(Long promotionID);

    Promotion createPromotion(String description, LocalDateTime startDate, LocalDateTime endDate, List<PromotionDiscount> discounts, boolean globalPromotion);

    Promotion updatePromotion(Long id, String description, LocalDateTime startDate, LocalDateTime endDate, List<PromotionDiscount> discounts, boolean globalPromotion);

    void deletePromotion(Long id);

    void changePromotionEndDate(Long promotionID, LocalDateTime newEndDate);

    List<Promotion> getActivePromotions();

    List<Promotion> getExpiredPromotions();

    boolean isPromotionExpired(Long promotionID);

    boolean isPromotionActive(Long promotionID);
}

