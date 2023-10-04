package com.example.cult_of_tim.cultoftim.service;

import com.example.cult_of_tim.cultoftim.models.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {
    Optional<Purchase> getPurchaseById(Long id);

    List<Purchase> getAllPurchases();

    List<Purchase> getPurchasesByUserId(Long userId);

    Purchase createPurchase(Purchase purchase);

    Purchase updatePurchase(Purchase purchase);

    void deletePurchaseById(Long id);
}

