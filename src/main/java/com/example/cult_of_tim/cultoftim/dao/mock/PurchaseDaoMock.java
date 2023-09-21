package com.example.cult_of_tim.cultoftim.dao.mock;

import com.example.cult_of_tim.cultoftim.dao.PurchaseDao;
import com.example.cult_of_tim.cultoftim.models.Purchase;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PurchaseDaoMock implements PurchaseDao {

    private final Map<Long, Purchase> purchaseMap = new HashMap<>();
    private Long nextPurchaseId = 1L;

    @Override
    public Optional<Purchase> getPurchaseById(Long id) {
        return Optional.ofNullable(purchaseMap.get(id));
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return new ArrayList<>(purchaseMap.values());
    }

    @Override
    public List<Purchase> getPurchasesByUserId(Long userId) {
        List<Purchase> userPurchases = new ArrayList<>();
        for (Purchase purchase : purchaseMap.values()) {
            if (purchase.getUserId().equals(userId)) {
                userPurchases.add(purchase);
            }
        }
        return userPurchases;
    }

    @Override
    public Long createPurchase(Purchase purchase) {
        purchase.setId(nextPurchaseId++);
        purchaseMap.put(purchase.getId(), purchase);
        return purchase.getId();
    }

    @Override
    public Purchase updatePurchase(Purchase purchase) {
        purchaseMap.put(purchase.getId(), purchase);
        return purchase;
    }

    @Override
    public void deletePurchaseById(Long id) {
        purchaseMap.remove(id);
    }
}

