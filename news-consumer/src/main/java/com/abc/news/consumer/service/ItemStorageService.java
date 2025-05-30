package com.abc.news.consumer.service;

import com.abc.news.consumer.model.Item;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class ItemStorageService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String HASH_KEY = "articles";

    public ItemStorageService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveArticles(Item item,String key) {
        HashOperations<String, String, Item> hashOps = redisTemplate.opsForHash();
        redisTemplate.expire(HASH_KEY, Duration.ofHours(24));
        hashOps.put(HASH_KEY, key, item);
    }

    public Optional<Item> getArticleById(String id) {
        HashOperations<String, String, Item> hashOps = redisTemplate.opsForHash();
        return Optional.ofNullable(hashOps.get(HASH_KEY, id));
    }
}
