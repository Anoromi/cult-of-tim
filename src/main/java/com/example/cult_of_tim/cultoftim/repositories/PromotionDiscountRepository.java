package com.example.cult_of_tim.cultoftim.repositories;

import com.example.cult_of_tim.cultoftim.models.PromotionDiscount;
import com.example.cult_of_tim.cultoftim.models.PromotionDiscountID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionDiscountRepository extends JpaRepository<PromotionDiscount, PromotionDiscountID> {

    Optional<PromotionDiscount> findByPromotionIdAndBookId(PromotionDiscountID id);

    List<PromotionDiscount> findByPromotionId(Long promotionId);

    List<PromotionDiscount> findByBookId(Long bookId);

    PromotionDiscount save(PromotionDiscount promotionDiscount);

    void deleteByPromotionIdAndBookId(Long promotionId, Long bookId);
}