package com.example.cult_of_tim.cultoftim.service.impl;

import com.example.cult_of_tim.cultoftim.dao.PurchaseDao;
import com.example.cult_of_tim.cultoftim.models.Purchase;
import com.example.cult_of_tim.cultoftim.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseMockService implements PurchaseService {

    private final PurchaseDao purchaseDao;

    @Autowired
    public PurchaseMockService(PurchaseDao purchaseDao) {
        this.purchaseDao = purchaseDao;
    }

    @Override
    public Optional<Purchase> getPurchaseById(Long id) {
        return purchaseDao.getPurchaseById(id);
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseDao.getAllPurchases();
    }

    @Override
    public List<Purchase> getPurchasesByUserId(Long userId) {
        return purchaseDao.getPurchasesByUserId(userId);
    }

    @Override
    public Long createPurchase(Purchase purchase) {
        return purchaseDao.createPurchase(purchase);
    }

    @Override
    public Purchase updatePurchase(Purchase purchase) {
        return purchaseDao.updatePurchase(purchase);
    }

    @Override
    public void deletePurchaseById(Long id) {
        purchaseDao.deletePurchaseById(id);
    }
}

