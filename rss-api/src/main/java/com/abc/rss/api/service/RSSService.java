package com.abc.rss.api.service;

import com.abc.rss.api.model.Item;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RSSService {

 private final ItemStorageService itemStorageService;

    public RSSService(ItemStorageService itemStorageService) {
        this.itemStorageService = itemStorageService;
    }

    public List<Item> getArticles(int page, int size, boolean sortDesc) {
        List<Item> all = itemStorageService.getAllArticles();

        if (all == null) return List.of();

        return all.stream()
                .sorted(Comparator.comparing(Item::getPubDate, sortDesc ? Comparator.reverseOrder() : Comparator.naturalOrder()))
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }
}
