package com.example.cult_of_tim.cultoftim.dao;

import com.example.cult_of_tim.cultoftim.models.Promotion;

import java.util.List;
import java.util.Optional;

public interface PromotionDao {
    Optional<Promotion> getPromotionById(Long id);

    List<Promotion> getAllPromotions();

    List<Promotion> getUserPromotions(Long userID);

    boolean isGlobal(Long promotionID);

    void createPromotion(Promotion promotion);

    void updatePromotion(Promotion promotion);

    void deletePromotion(Long id);
}

