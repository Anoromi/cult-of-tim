package com.example.cult_of_tim.cultoftim.converter;

import com.example.cult_of_tim.cultoftim.dto.PurchaseDto;
import com.example.cult_of_tim.cultoftim.entity.Purchase;
import org.springframework.stereotype.Component;

@Component
public class PurchaseConverter {

    public PurchaseDto toDto(Purchase purchase) {
        return PurchaseDto.builder()
                .id(purchase.getId())
                .purchaseDate(purchase.getPurchaseDate())
                .userId(purchase.getUserId())
                .build();
    }

    public Purchase toEntity(PurchaseDto purchaseDto) {
        return Purchase.builder()
                .id(purchaseDto.getId())
                .purchaseDate(purchaseDto.getPurchaseDate())
                .userId(purchaseDto.getUserId())
                .build();
    }
}

