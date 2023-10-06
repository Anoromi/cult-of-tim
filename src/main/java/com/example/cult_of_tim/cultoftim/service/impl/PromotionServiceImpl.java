package com.example.cult_of_tim.cultoftim.service.impl;

import com.example.cult_of_tim.cultoftim.converter.PromotionConverter;
import com.example.cult_of_tim.cultoftim.converter.PromotionDiscountConverter;
import com.example.cult_of_tim.cultoftim.dto.PromotionDiscountDto;
import com.example.cult_of_tim.cultoftim.dto.PromotionDto;
import com.example.cult_of_tim.cultoftim.entity.Book;
import com.example.cult_of_tim.cultoftim.entity.Promotion;
import com.example.cult_of_tim.cultoftim.entity.PromotionDiscount;
import com.example.cult_of_tim.cultoftim.repositories.BookRepository;
import com.example.cult_of_tim.cultoftim.repositories.PromotionRepository;
import com.example.cult_of_tim.cultoftim.service.PromotionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final BookRepository bookRepository;
    private final PromotionConverter promotionConverter;
    private final PromotionDiscountConverter promotionDiscountConverter;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository, BookRepository bookRepository, PromotionConverter promotionConverter, PromotionDiscountConverter promotionDiscountConverter) {
        this.promotionRepository = promotionRepository;
        this.bookRepository = bookRepository;
        this.promotionConverter = promotionConverter;
        this.promotionDiscountConverter = promotionDiscountConverter;
    }

    @Override
    public Optional<PromotionDto> getPromotionById(Long id) {
        return promotionRepository.findById(id).map(promotionConverter::toDto);
    }

    @Override
    public List<PromotionDto> getAllPromotions() {
        List<Promotion> promotions = promotionRepository.findAll();
        return promotions.stream()
                .map(promotionConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isGlobal(Long promotionID) {
        return promotionRepository.existsByIdAndGlobalPromotionTrue(promotionID);
    }

    @Override
    public PromotionDto createPromotion(PromotionDto promotionDto) {
        Promotion newPromotion = promotionConverter.toEntity(promotionDto);
        return promotionConverter.toDto(promotionRepository.save(newPromotion));
    }

    @Override
    public PromotionDto updatePromotion(Long id, PromotionDto updatedPromotionDto) {
        Promotion existingPromotion = promotionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found"));

        Promotion updatedPromotion = promotionConverter.toEntity(updatedPromotionDto);
        updatedPromotion.setId(id);

        existingPromotion.setDescription(updatedPromotion.getDescription());
        existingPromotion.setStartDate(updatedPromotion.getStartDate());
        existingPromotion.setEndDate(updatedPromotion.getEndDate());

        return promotionConverter.toDto(promotionRepository.save(existingPromotion));
    }

    @Override
    public void deletePromotion(Long id) {
        promotionRepository.deleteById(id);
    }

    @Override
    public void changePromotionEndDate(Long promotionID, LocalDateTime newEndDate) {
        Promotion promotion = promotionRepository.findById(promotionID)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found"));

        promotion.setEndDate(newEndDate);

        promotionRepository.save(promotion);
    }

    @Override
    public List<PromotionDto> getActivePromotions() {
        LocalDateTime now = LocalDateTime.now();
        return promotionRepository.findAll().stream()
                .filter(promotion -> promotion.getStartDate().isBefore(now) && promotion.getEndDate().isAfter(now))
                .map(promotionConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PromotionDto> getExpiredPromotions() {
        LocalDateTime now = LocalDateTime.now();
        return promotionRepository.findAll().stream()
                .filter(promotion -> promotion.getEndDate().isBefore(now))
                .map(promotionConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isPromotionExpired(Long promotionID) {
        Promotion promotion = promotionRepository.findById(promotionID)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found"));

        return promotion.getEndDate().isBefore(LocalDateTime.now());
    }

    @Override
    public boolean isPromotionActive(Long promotionID) {
        Promotion promotion = promotionRepository.findById(promotionID)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found"));

        LocalDateTime now = LocalDateTime.now();
        return promotion.getStartDate().isBefore(now) && promotion.getEndDate().isAfter(now);
    }

    @Override
    @Transactional
    public void addBookWithDiscountToPromotion(PromotionDiscountDto discountDto) {
        Promotion promotion = promotionRepository.findById(discountDto.getPromotionId())
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found"));

        Book book = bookRepository.findById(discountDto.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        boolean promotionDiscountExists = promotion.getDiscounts().stream()
                .anyMatch(pd -> pd.getBook().getId().equals(discountDto.getBookId()));

        if (!promotionDiscountExists) {
            PromotionDiscount promotionDiscount = new PromotionDiscount();
            promotionDiscount.setPromotion(promotion);
            promotionDiscount.setBook(book);
            promotionDiscount.setDiscountPercentage(discountDto.getDiscountPercentage());

            if (promotion.getDiscounts() == null) {
                promotion.setDiscounts(List.of(promotionDiscount));
            } else {
                promotion.getDiscounts().add(promotionDiscount);
            }

            promotionRepository.save(promotion);
        } else {
            throw new IllegalArgumentException("Promotion discount for the Book with id: " + discountDto.getBookId() + " is already created");
        }
    }
}
