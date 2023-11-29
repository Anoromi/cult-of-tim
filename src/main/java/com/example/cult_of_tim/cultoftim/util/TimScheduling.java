package com.example.cult_of_tim.cultoftim.util;

import com.cult_of_tim.auth.cultoftimauth.repositories.UserTokenRepository;
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
    private UserTokenRepository tokenRepository;
    @Scheduled(cron = "0 0 1 * * ?")
    public void cleanDatabaseFromOutdatedTokens() {
        Date now = Date.from(Instant.now());
        tokenRepository.deleteAllByExpiresAtBefore(now);

    }
    @Scheduled(fixedRate = ONE_DAY)
    public void clearCache() {
        evictAllCaches();
    }

    public void evictAllCaches() {
        cacheManager.getCacheNames().stream().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }
}
