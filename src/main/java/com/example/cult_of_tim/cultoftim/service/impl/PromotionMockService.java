package com.example.cult_of_tim.cultoftim.service.impl;

import com.example.cult_of_tim.cultoftim.dao.PromotionDao;
import com.example.cult_of_tim.cultoftim.models.Promotion;
import com.example.cult_of_tim.cultoftim.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionMockService implements PromotionService {
    private final PromotionDao promotionDao;

    @Autowired
    public PromotionMockService(PromotionDao promotionDao) {
        this.promotionDao = promotionDao;
    }

    @Override
    public Optional<Promotion> getPromotionById(Long id) {
        return promotionDao.getPromotionById(id);
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionDao.getAllPromotions();
    }

    @Override
    public List<Promotion> getUserPromotions(Long userID) {
        return promotionDao.getUserPromotions(userID);
    }

    @Override
    public boolean isGlobal(Long promotionID) {
        return promotionDao.isGlobal(promotionID);
    }

    @Override
    public void createPromotion(Promotion promotion) {
        promotionDao.createPromotion(promotion);
    }

    @Override
    public void updatePromotion(Long id, Promotion updatedPromotion) {
        updatedPromotion.setId(id);
        promotionDao.updatePromotion(updatedPromotion);
    }

    @Override
    public void deletePromotion(Long id) {
        promotionDao.deletePromotion(id);
    }

    @Override
    public void changePromotionEndDate(Long promotionID, LocalDateTime newEndDate) {
        Promotion promotion = promotionDao.getPromotionById(promotionID)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found"));

        promotion.setEndDate(newEndDate);

        promotionDao.updatePromotion(promotion);
    }

    @Override
    public void assignPromotionToUser(Long promotionID, Long userID) {
        Promotion promotion = promotionDao.getPromotionById(promotionID)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found"));

        if (!promotion.getUserIDs().contains(userID)) {
            promotion.getUserIDs().add(userID);
            promotionDao.updatePromotion(promotion);
        }
    }

    @Override
    public void removePromotionFromUser(Long promotionID, Long userID) {
        Promotion promotion = promotionDao.getPromotionById(promotionID)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found"));

        promotion.getUserIDs().remove(userID);
        promotionDao.updatePromotion(promotion);
    }

    @Override
    public List<Promotion> getActivePromotions() {
        LocalDateTime now = LocalDateTime.now();
        return promotionDao.getAllPromotions().stream()
                .filter(promotion -> promotion.getStartDate().isBefore(now) && promotion.getEndDate().isAfter(now))
                .toList();
    }

    @Override
    public List<Promotion> getExpiredPromotions() {
        LocalDateTime now = LocalDateTime.now();
        return promotionDao.getAllPromotions().stream()
                .filter(promotion -> promotion.getEndDate().isBefore(now))
                .toList();
    }

    @Override
    public boolean isPromotionExpired(Long promotionID) {
        Promotion promotion = promotionDao.getPromotionById(promotionID)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found"));

        return promotion.getEndDate().isBefore(LocalDateTime.now());
    }

    @Override
    public boolean isPromotionActive(Long promotionID) {
        Promotion promotion = promotionDao.getPromotionById(promotionID)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found"));

        LocalDateTime now = LocalDateTime.now();
        return promotion.getStartDate().isBefore(now) && promotion.getEndDate().isAfter(now);
    }
}

