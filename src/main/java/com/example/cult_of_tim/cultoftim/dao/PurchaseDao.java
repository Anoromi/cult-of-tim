package com.example.cult_of_tim.cultoftim.dao;

import com.example.cult_of_tim.cultoftim.models.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseDao {
    Optional<Purchase> getPurchaseById(Long id);

    List<Purchase> getAllPurchases();

    List<Purchase> getPurchasesByUserId(Long userId);

    Long createPurchase(Purchase purchase);

    Purchase updatePurchase(Purchase purchase);

    void deletePurchaseById(Long id);
}

