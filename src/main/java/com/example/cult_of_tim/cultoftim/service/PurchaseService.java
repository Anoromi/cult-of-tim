package com.example.cult_of_tim.cultoftim.service;

import com.example.cult_of_tim.cultoftim.dto.PurchaseDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PurchaseService {
    Optional<PurchaseDto> getPurchaseById(Long id);

    List<PurchaseDto> getAllPurchases();

    List<PurchaseDto> getPurchasesByUserId(Long userId);

    PurchaseDto createPurchase(PurchaseDto purchase);

    PurchaseDto updatePurchase(PurchaseDto purchase);

    void deletePurchaseById(Long id);
    PurchaseDto purchaseBooks(UUID userId, List<Long> bookIds);
}

