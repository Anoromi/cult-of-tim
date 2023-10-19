package com.example.cult_of_tim.cultoftim.controller;

import com.example.cult_of_tim.cultoftim.controller.request.PromotionRequest;
import com.example.cult_of_tim.cultoftim.dto.PromotionDto;
import com.example.cult_of_tim.cultoftim.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/promotions")
public class PromotionController {
    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    private PromotionDto mapToPromotionDto(PromotionRequest promotionRequest){
        return PromotionDto.builder()
                .id(promotionRequest.getId())
                .description(promotionRequest.getDescription())
                .startDate(promotionRequest.getStartDate())
                .endDate(promotionRequest.getEndDate())
                .globalPromotion(promotionRequest.isGlobalPromotion())
                .build();
    }

    private PromotionRequest mapToPromotionRequest(PromotionDto promotionDto){
        return PromotionRequest.builder()
                .id(promotionDto.getId())
                .description(promotionDto.getDescription())
                .startDate(promotionDto.getStartDate())
                .endDate(promotionDto.getEndDate())
                .globalPromotion(promotionDto.isGlobalPromotion())
                .build();
    }

    @PostMapping
    public ResponseEntity<PromotionRequest> createPromotion(@RequestBody PromotionRequest promotionRequest) {
        PromotionDto promotionDto = mapToPromotionDto(promotionRequest);
        PromotionDto createdPromotion = promotionService.createPromotion(promotionDto);
        PromotionRequest response = mapToPromotionRequest(createdPromotion);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionRequest> getPromotionById(@PathVariable Long id) {
        Optional<PromotionDto> promotionDto = promotionService.getPromotionById(id);
        if (promotionDto.isPresent()) {
            PromotionRequest response = mapToPromotionRequest(promotionDto.get());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<PromotionRequest> getAllPromotions() {
        List<PromotionDto> promotionDtos = promotionService.getAllPromotions();
        return promotionDtos.stream()
                .map(this::mapToPromotionRequest)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromotionRequest> updatePromotion(@PathVariable Long id, @RequestBody PromotionRequest updatedPromotion) {
        Optional<PromotionDto> existingPromotion = promotionService.getPromotionById(id);
        if (existingPromotion.isPresent()) {
            PromotionDto updatedPromotionDto = promotionService.updatePromotion(id, mapToPromotionDto(updatedPromotion));
            PromotionRequest response = mapToPromotionRequest(updatedPromotionDto);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotionById(@PathVariable Long id) {
        Optional<PromotionDto> existingPromotion = promotionService.getPromotionById(id);
        if (existingPromotion.isPresent()) {
            promotionService.deletePromotion(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handlePromotionNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
