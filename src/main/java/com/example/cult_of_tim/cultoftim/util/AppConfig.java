package com.example.cult_of_tim.cultoftim.util;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    CacheManager cacheManager() {
        return new TimCaching();
    }
}
