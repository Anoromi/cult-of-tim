package com.example.cult_of_tim.cultoftim.service.impl;

import com.cult_of_tim.auth.cultoftimauth.model.User;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserRepository;
import com.example.cult_of_tim.cultoftim.converter.PurchaseConverter;
import com.example.cult_of_tim.cultoftim.dto.PurchaseDto;
import com.example.cult_of_tim.cultoftim.entity.Book;
import com.example.cult_of_tim.cultoftim.entity.Promotion;
import com.example.cult_of_tim.cultoftim.entity.PromotionDiscount;
import com.example.cult_of_tim.cultoftim.entity.Purchase;
import com.example.cult_of_tim.cultoftim.repositories.BookRepository;
import com.example.cult_of_tim.cultoftim.repositories.PromotionRepository;
import com.example.cult_of_tim.cultoftim.repositories.PurchaseRepository;
import com.example.cult_of_tim.cultoftim.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseConverter purchaseConverter;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PromotionRepository promotionRepository;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, PurchaseConverter purchaseConverter, UserRepository userRepository, BookRepository bookRepository, PromotionRepository promotionRepository) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseConverter = purchaseConverter;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.promotionRepository = promotionRepository;
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
    @Override
    public PurchaseDto purchaseBooks(UUID userId, List<Long> bookIds) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Book> booksToPurchase = bookRepository.findAllById(bookIds);

        for (Book book : booksToPurchase) {
            if (!book.isAvailable() || book.getQuantity() <= 0) {
                throw new IllegalStateException("Book with id " + book.getId() + " is not available");
            }
        }

        double totalCost = booksToPurchase.stream()
                .mapToDouble(Book::getPrice)
                .sum();

        LocalDateTime now = LocalDateTime.now();
        List<Promotion> activePromotions = promotionRepository.findAll();
        activePromotions = activePromotions.stream().filter(x -> now.isBefore(x.getEndDate())).collect(Collectors.toList());

        double totalDiscountAmount = 0.0;


        for (Promotion promotion : activePromotions) {
            for (PromotionDiscount discount : promotion.getDiscounts()) {
                Book book = discount.getBook();
                double bookPrice = book.getPrice();
                double discountPercentage = discount.getDiscountPercentage() / 100.0;
                double discountAmount = discountPercentage * bookPrice;


                totalDiscountAmount += discountAmount;
            }
        }
        totalCost-= totalDiscountAmount;

        if (user.getBalance() < totalCost) {
            throw new IllegalStateException("Insufficient funds to make the purchase");
        }

        for (Book book : booksToPurchase) {
            book.setQuantity(book.getQuantity() - 1);
            if (book.getQuantity() == 0) {
                book.setAvailable(false);
            }
        }

        user.setBalance(user.getBalance() - totalCost);

        Purchase purchase = Purchase.builder()
                .purchaseDate(LocalDateTime.now())
                .userId(userId)
                .books(booksToPurchase)
                .build();

        Purchase savedPurchase = purchaseRepository.save(purchase);


        return purchaseConverter.toDto(savedPurchase);
    }
}

