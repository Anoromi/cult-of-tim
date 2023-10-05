package com.example.cult_of_tim.cultoftim.repositories;

import com.example.cult_of_tim.cultoftim.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    //Optional<Purchase> findById(Long id);

    //List<Purchase> findAll();

    List<Purchase> findByUserId(Long userId);

    //Purchase save(Purchase purchase);

    //void deleteById(Long id);
}