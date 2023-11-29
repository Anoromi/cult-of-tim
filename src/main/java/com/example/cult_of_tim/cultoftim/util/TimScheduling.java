package com.example.cult_of_tim.cultoftim.util;

import com.cult_of_tim.auth.cultoftimauth.repositories.UserTokenRepository;
import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

import static javax.management.timer.Timer.ONE_DAY;

@Component
public class TimScheduling {

    @Autowired
    CacheManager cacheManager;
    @Autowired
    private UserService userService;
    @Scheduled(cron = "0 0 1 * * ?")
    public void cleanDatabaseFromOutdatedTokens() {
        userService.deleteAllExpiredTokens();

    }
    @Scheduled(fixedRate = ONE_DAY)
    public void clearCache() {
        evictAllCaches();
    }

    public void evictAllCaches() {
        cacheManager.getCacheNames().stream().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }
}
