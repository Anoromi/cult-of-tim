package com.example.cult_of_tim.cultoftim.util;

import com.example.cult_of_tim.cultoftim.dao.mock.PromotionDaoMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public PromotionDaoMock promotionDaoMock() {
        return new PromotionDaoMock();
    }
}
