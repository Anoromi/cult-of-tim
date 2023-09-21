package com.example.cult_of_tim.cultoftim.service;

import com.example.cult_of_tim.cultoftim.models.Promotion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PromotionService {
    Optional<Promotion> getPromotionById(Long id);

    List<Promotion> getAllPromotions();

    List<Promotion> getUserPromotions(Long userID);

    boolean isGlobal(Long promotionID);

    Long createPromotion(Promotion promotion);

    Promotion updatePromotion(Long id, Promotion updatedPromotion);

    void deletePromotion(Long id);

    void assignPromotionToUser(Long promotionID, Long userID);

    void removePromotionFromUser(Long promotionID, Long userID);

    void changePromotionEndDate(Long promotionID, LocalDateTime newEndDate);

    List<Promotion> getActivePromotions();

    List<Promotion> getExpiredPromotions();

    boolean isPromotionExpired(Long promotionID);

    boolean isPromotionActive(Long promotionID);
}

