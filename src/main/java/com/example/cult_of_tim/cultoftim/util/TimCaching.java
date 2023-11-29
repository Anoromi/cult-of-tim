package com.example.cult_of_tim.cultoftim.util;

import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.Collection;
import java.util.Spliterators;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.StreamSupport;

public class TimCaching implements CacheManager {

    ConcurrentHashMap<String, Cache> caches = new ConcurrentHashMap<>();

    @Override
    public Cache getCache(String name) {
        return caches.computeIfAbsent(name, k -> new TimCache(name, new ConcurrentHashMap<>()));
    }

    @Override
    public Collection<String> getCacheNames() {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(caches.keys().asIterator(), 0), false
        ).toList();
    }


    @AllArgsConstructor
    public static class TimCache implements Cache {

        String name;
        ConcurrentHashMap<Object, Object> map;


        @Override
        public String getName() {
            return name;
        }

        @Override
        public Object getNativeCache() {
            return map;
        }

        @Override
        public ValueWrapper get(Object key) {
            var result = map.get(key);
            if (result == null)
                return null;
            return new SimpleValueWrapper(result);
        }

        @Override
        public <T> T get(Object key, Class<T> type) {
            var result = get(key);
            if (type.isInstance(result.get()))
                return (T) result.get();
            return null;
        }

        @Override
        public <T> T get(Object key, Callable<T> valueLoader) {
            var result = map.get(key);
            if (result == null) {
                try {
                    return valueLoader.call();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return (T) result;
        }

        @Override
        public void put(Object key, Object value) {
            map.put(key, value);
        }

        @Override
        public void evict(Object key) {
            map.remove(key);
        }

        @Override
        public void clear() {
            map.clear();
        }
    }
}

