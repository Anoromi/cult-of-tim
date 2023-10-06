package com.example.cult_of_tim.cultoftim.converter;

import com.example.cult_of_tim.cultoftim.dto.PromotionDiscountDto;
import com.example.cult_of_tim.cultoftim.entity.PromotionDiscount;
import org.springframework.stereotype.Component;

@Component
public class PromotionDiscountConverter {

    public PromotionDiscountDto toDto(PromotionDiscount promotionDiscount) {
        PromotionDiscountDto promotionDiscountDto = new PromotionDiscountDto();
        promotionDiscountDto.setPromotionId(promotionDiscount.getPromotion().getId());
        promotionDiscountDto.setBookId(promotionDiscount.getBook().getId());
        promotionDiscountDto.setDiscountPercentage(promotionDiscount.getDiscountPercentage());
        return promotionDiscountDto;
    }

    public PromotionDiscount toEntity(PromotionDiscountDto promotionDiscountDto) {
        throw new UnsupportedOperationException();
        /*PromotionDiscount promotionDiscount = new PromotionDiscount();
        Promotion promotion = new Promotion();
        promotion.setId(promotionDiscountDto.getPromotionId());
        promotionDiscount.setPromotion(promotion);
        Book book = new Book();
        book.setId(promotionDiscountDto.getBookId());
        promotionDiscount.setBook(book);
        promotionDiscount.setDiscountPercentage(promotionDiscountDto.getDiscountPercentage());
        return promotionDiscount;*/
    }
}

