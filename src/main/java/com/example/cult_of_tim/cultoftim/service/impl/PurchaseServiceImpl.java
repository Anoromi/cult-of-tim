package com.example.cult_of_tim.cultoftim.service.impl;

import com.example.cult_of_tim.cultoftim.converter.PurchaseConverter;
import com.example.cult_of_tim.cultoftim.dto.PurchaseDto;
import com.example.cult_of_tim.cultoftim.entity.Purchase;
import com.example.cult_of_tim.cultoftim.repositories.PurchaseRepository;
import com.example.cult_of_tim.cultoftim.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseConverter purchaseConverter;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, PurchaseConverter purchaseConverter) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseConverter = purchaseConverter;
    }

    @Override
    public Optional<PurchaseDto> getPurchaseById(Long id) {
        return purchaseRepository.findById(id).map(purchaseConverter::toDto);
    }

    @Override
    public List<PurchaseDto> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        return purchases.stream()
                .map(purchaseConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseDto> getPurchasesByUserId(Long userId) {
        List<Purchase> purchases = purchaseRepository.findByUserId(userId);
        return purchases.stream()
                .map(purchaseConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseDto createPurchase(PurchaseDto purchaseDto) {
        Purchase newPurchase = purchaseConverter.toEntity(purchaseDto);
        return purchaseConverter.toDto(purchaseRepository.save(newPurchase));
    }

    @Override
    public PurchaseDto updatePurchase(PurchaseDto purchaseDto) {
        Purchase updatedPurchase = purchaseConverter.toEntity(purchaseDto);

        return purchaseConverter.toDto(purchaseRepository.save(updatedPurchase));
    }

    @Override
    public void deletePurchaseById(Long id) {
        purchaseRepository.deleteById(id);
    }
}

