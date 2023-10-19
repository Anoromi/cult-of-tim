package com.example.cult_of_tim.cultoftim.repositories;

import com.example.cult_of_tim.cultoftim.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

//    boolean existsByIdAndGlobalPromotion(Long promotionId, boolean global);

    boolean existsByIdAndGlobalPromotionTrue(Long promotionId);

}
