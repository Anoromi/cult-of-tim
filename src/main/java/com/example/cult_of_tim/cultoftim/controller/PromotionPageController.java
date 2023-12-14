package com.example.cult_of_tim.cultoftim.controller;

import com.cult_of_tim.auth.cultoftimauth.dto.UserDTO;
import com.example.cult_of_tim.cultoftim.auth.UserContext;
import com.example.cult_of_tim.cultoftim.dto.*;
import com.example.cult_of_tim.cultoftim.service.BookService;
import com.example.cult_of_tim.cultoftim.service.PromotionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;


@Controller
@AllArgsConstructor
public class PromotionPageController {

    private final PromotionService promotionService;
    private final BookService bookService;

    @GetMapping("/promotions/list")
    public String listPromotions(Model model) {
        String role = "Default";
        UserContext userContext = new UserContext();
        Optional<UserDTO> user = userContext.getUser();
        if (user.isPresent()) {
            role = user.get().getRole();
        }
        model.addAttribute("userRole", role);
        List<PromotionDto> promotions = promotionService.getAllPromotions();
        model.addAttribute("promotions", promotions);
        return "promotion-list";
    }

    @GetMapping("/promotions/discounts/{promotionId}")
    public String viewDiscounts(@PathVariable Long promotionId, @ModelAttribute PromotionDto promotion, Model model) {
        String role = "Default";
        UserContext userContext = new UserContext();
        Optional<UserDTO> user = userContext.getUser();
        if (user.isPresent()) {
            role = user.get().getRole();
        }
        model.addAttribute("userRole", role);

        List<PromotionDiscountDto> discounts = promotionService.getAllPromotionDiscounts(promotionId);
        List<PromotionDiscountView> discountViews = discounts.stream()
                .map(discount ->
                        PromotionDiscountView.builder()
                                .bookId(discount.getBookId())
                                .promotionId(promotionId)
                                .bookName(bookService.getBookById(discount.getBookId()).get().getTitle())
                                .discountPercentage(discount.getDiscountPercentage())
                                .build()
                ).toList();
        model.addAttribute("promotionId", promotionId);
        model.addAttribute("discountViews", discountViews);
        return "promotion-discounts";
    }

    @GetMapping("/promotions/add")
    public String addPromotionPage(Model model) {
        model.addAttribute("promotionDto", new PromotionDto());
        return "promotion-add";
    }

    @PostMapping("/promotions/add")
    public String addPromotion(@Valid @ModelAttribute PromotionDto promotionDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "promotion-add";
        }

        var startDate = promotionDto.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        var endDate = promotionDto.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        if (endDate.isBefore(LocalDateTime.now()) || endDate.isBefore(startDate)) {
            return "redirect:/promotions/add?endDate";
        }

        promotionService.createPromotion(promotionDto);

        return "redirect:/promotions/list";
    }

    @GetMapping("/promotions/edit/{id}")
    public String editPromotionPage(@PathVariable Long id, Model model) {
        PromotionDto promotionDto = promotionService.getPromotionById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid promotion Id:" + id));
        model.addAttribute("promotion", promotionDto);
        return "promotion-edit";
    }

    @PostMapping("/promotions/edit/{id}")
    public String editPromotion(@PathVariable Long id,
                                @Valid @ModelAttribute("promotion") PromotionDto updatedPromotionDto,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "promotion-edit";
        }

        var startDate = updatedPromotionDto.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        var endDate = updatedPromotionDto.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        if (endDate.isBefore(LocalDateTime.now()) || endDate.isBefore(startDate)) {
            return "redirect:/promotions/edit?endDate";
        }

        promotionService.updatePromotion(id, updatedPromotionDto);

        return "redirect:/promotions/list";
    }

    @GetMapping("/discounts/list")
    public String viewDiscounts(@RequestParam(required = false) String bookName, Model model) {
        List<DiscountIndependentView> discounts;

        if (bookName != null) {
            discounts = promotionService.getAllIndependentDiscountsByBookName(bookName);
        } else {
            discounts = promotionService.getAllIndependentDiscounts();
        }

        model.addAttribute("discounts", discounts);
        return "discount-list";
    }

    @GetMapping("/discounts/add/{promotionId}")
    public String addDiscountPage(@PathVariable Long promotionId, Model model) {
        model.addAttribute("discountView", PromotionDiscountView.builder().promotionId(promotionId).build());
        return "discount-add";
    }

    @PostMapping("/discounts/add")
    public String addDiscount(@Valid @ModelAttribute PromotionDiscountView discountView, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "discount-add";
        }

        Optional<BookDto> bookDtoOptional = bookService.getBookByTitle(discountView.getBookName());
        if(bookDtoOptional.isEmpty())
            return "redirect:/discounts/add?bookTitle";
        Long bookId = bookDtoOptional.get().getId();
        PromotionDiscountDto discountDto = PromotionDiscountDto.builder()
                .promotionId(discountView.getPromotionId())
                .bookId(bookId)
                .discountPercentage(discountView.getDiscountPercentage())
                .build();
        promotionService.addBookWithDiscountToPromotion(discountDto);

        return "redirect:/promotions/list";
    }

    @GetMapping("/discounts/edit/{bookId}/promotion/{promotionId}")
    public String editDiscountPage(@PathVariable Long bookId, @PathVariable Long promotionId, Model model) {
        PromotionDiscountDto discountDto = promotionService.getDiscountByIds(bookId, promotionId);

        model.addAttribute("discount", discountDto);
        model.addAttribute("bookName", bookService.getBookById(bookId).get().getTitle());
        return "discount-edit";
    }

    @PostMapping("/discounts/edit/{bookId}/promotion/{promotionId}")
    public String editDiscount(@PathVariable Long bookId,
                               @PathVariable Long promotionId,
                               @Valid @ModelAttribute("discount") PromotionDiscountDto updatedDiscountDto,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "discount-edit";
        }

        updatedDiscountDto.setBookId(bookId);
        updatedDiscountDto.setPromotionId(promotionId);

        promotionService.updatePromotionDiscount(updatedDiscountDto);

        return "redirect:/promotions/list";
    }

    @GetMapping("/discounts/delete/{bookId}/promotion/{promotionId}")
    public String deleteDiscount(@PathVariable Long bookId,
                                 @PathVariable Long promotionId) {
        promotionService.deletePromotionDiscount(bookId, promotionId);
        return "redirect:/promotions/list";
    }

}
