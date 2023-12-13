package com.example.cult_of_tim.cultoftim.service;

import com.example.cult_of_tim.cultoftim.dto.DiscountIndependentView;
import com.example.cult_of_tim.cultoftim.dto.PromotionDiscountDto;
import com.example.cult_of_tim.cultoftim.dto.PromotionDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PromotionService {
    Optional<PromotionDto> getPromotionById(Long id);

    List<PromotionDto> getAllPromotions();

    List<PromotionDiscountDto> getAllPromotionDiscounts(Long id);

    PromotionDiscountDto getDiscountByIds(Long promotionId, Long bookId);

    List<DiscountIndependentView> getAllIndependentDiscounts();

    List<DiscountIndependentView> getAllIndependentDiscountsByBookName(String bookName);

    boolean isGlobal(Long promotionID);

    PromotionDto createPromotion(PromotionDto promotion);

    PromotionDto updatePromotion(Long id, PromotionDto updatedPromotion);

    void deletePromotion(Long id);

    void changePromotionEndDate(Long promotionID, LocalDateTime newEndDate);

    List<PromotionDto> getActivePromotions();

    List<PromotionDto> getExpiredPromotions();

    boolean isPromotionExpired(Long promotionID);

    boolean isPromotionActive(Long promotionID);

    void addBookWithDiscountToPromotion(PromotionDiscountDto promotionDiscount);

    void updatePromotionDiscount(PromotionDiscountDto discountDto);

    void deletePromotionDiscount(Long promotionId, Long bookId);

    Integer getPrice(Long bookId, Integer originalPrice);
}

