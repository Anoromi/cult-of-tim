package com.example.cult_of_tim.cultoftim.dao.mock;

import com.example.cult_of_tim.cultoftim.dao.PromotionDao;
import com.example.cult_of_tim.cultoftim.models.Promotion;

import java.util.*;

public class PromotionDaoMock implements PromotionDao {

    private final Map<Long, Promotion> promotionMap = new HashMap<>();
    private Long nextPromotionId = 1L;

    @Override
    public List<Promotion> getAllPromotions() {
        return new ArrayList<>(promotionMap.values());
    }

    @Override
    public Optional<Promotion> getPromotionById(Long id) {
        return Optional.ofNullable(promotionMap.get(id));
    }

    @Override
    public Long createPromotion(Promotion promotion) {
        promotion.setId(nextPromotionId++);
        promotionMap.put(promotion.getId(), promotion);
        return promotion.getId();
    }

    @Override
    public Promotion updatePromotion(Promotion promotion) {
        promotionMap.put(promotion.getId(), promotion);
        return promotion;
    }

    @Override
    public void deletePromotion(Long id) {
        promotionMap.remove(id);
    }

    @Override
    public List<Promotion> getUserPromotions(Long userID) {
        List<Promotion> userPromotions = new ArrayList<>();

        for (Promotion promotion : promotionMap.values()) {
            if (promotion.getUserIDs().contains(userID)) {
                userPromotions.add(promotion);
            }
        }

        return userPromotions;
    }

    @Override
    public boolean isGlobal(Long promotionID) {
        Promotion promotion = promotionMap.get(promotionID);
        return promotion != null && promotion.isGlobalPromotion();
    }
}