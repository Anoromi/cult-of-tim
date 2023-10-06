package com.example.cult_of_tim.cultoftim.converter;

import com.example.cult_of_tim.cultoftim.dto.PurchaseDto;
import com.example.cult_of_tim.cultoftim.entity.Purchase;
import org.springframework.stereotype.Component;

@Component
public class PurchaseConverter {

    public PurchaseDto toDto(Purchase purchase) {
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setId(purchase.getId());
        purchaseDto.setPurchaseDate(purchase.getPurchaseDate());
        purchaseDto.setUserId(purchase.getUserId());
        return purchaseDto;
    }

    public Purchase toEntity(PurchaseDto purchaseDto) {
        Purchase purchase = new Purchase();
        purchase.setId(purchaseDto.getId());
        purchase.setPurchaseDate(purchaseDto.getPurchaseDate());
        purchase.setUserId(purchaseDto.getUserId());
        return purchase;
    }
}

