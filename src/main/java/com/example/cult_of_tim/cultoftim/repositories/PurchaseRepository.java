package com.example.cult_of_tim.cultoftim.repositories;

import com.example.cult_of_tim.cultoftim.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByUserId(UUID userId);

}