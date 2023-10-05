package com.example.cult_of_tim.cultoftim.repositories;

import com.example.cult_of_tim.cultoftim.models.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    //Optional<Promotion> findById(Long id);

    //List<Promotion> findAll();

    boolean existsByIdAndGlobalPromotion(Long promotionId, boolean global);


    boolean  existsByIdAndGlobalPromotionTrue(Long promotionId);


}
