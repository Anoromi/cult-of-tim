package com.example.cult_of_tim.cultoftim.service.impl;

import com.example.cult_of_tim.cultoftim.models.Promotion;
import com.example.cult_of_tim.cultoftim.repositories.PromotionRepository;
import com.example.cult_of_tim.cultoftim.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public Optional<Promotion> getPromotionById(Long id) {
        return promotionRepository.findById(id);
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }


    @Override
    public boolean isGlobal(Long promotionID) {
        return promotionRepository.existsByIdAndGlobalPromotionTrue(promotionID);
    }

    @Override
    public Promotion createPromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion updatePromotion(Long id, Promotion updatedPromotion) {
        updatedPromotion.setId(id);
        return promotionRepository.save(updatedPromotion);
    }

    @Override
    public void deletePromotion(Long id) {
        promotionRepository.deleteById(id);
    }

    @Override
    public void changePromotionEndDate(Long promotionID, LocalDateTime newEndDate) {
        Promotion promotion = promotionRepository.findById(promotionID)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found"));

        promotion.setEndDate(newEndDate);

        promotionRepository.save(promotion);
    }

    @Override
    public List<Promotion> getActivePromotions() {
        LocalDateTime now = LocalDateTime.now();
        return promotionRepository.findAll().stream()
                .filter(promotion -> promotion.getStartDate().isBefore(now) && promotion.getEndDate().isAfter(now))
                .toList();
    }

    @Override
    public List<Promotion> getExpiredPromotions() {
        LocalDateTime now = LocalDateTime.now();
        return promotionRepository.findAll().stream()
                .filter(promotion -> promotion.getEndDate().isBefore(now))
                .toList();
    }

    @Override
    public boolean isPromotionExpired(Long promotionID) {
        Promotion promotion = promotionRepository.findById(promotionID)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found"));

        return promotion.getEndDate().isBefore(LocalDateTime.now());
    }

    @Override
    public boolean isPromotionActive(Long promotionID) {
        Promotion promotion = promotionRepository.findById(promotionID)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found"));

        LocalDateTime now = LocalDateTime.now();
        return promotion.getStartDate().isBefore(now) && promotion.getEndDate().isAfter(now);
    }
}

