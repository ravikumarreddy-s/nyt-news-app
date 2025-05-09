package com.abc.news.consumer.service;

import com.abc.news.consumer.model.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@Slf4j
public class KafkaRssConsumer {

    private ItemStorageService itemStorageService;

    private final String redisKey = "articles:";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaRssConsumer(ItemStorageService itemStorageService){
        this.itemStorageService=itemStorageService;
    }

    @KafkaListener(topics = "nyt.rss.articles", groupId = "news-consumer-group")
    public void consume(String message, @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key) {
        try {
            Item article = objectMapper.readValue(message, Item.class);
            log.info("Received Article Key: {} and Title: {}",key,article.getTitle() );
            itemStorageService.saveArticles(article,key);

            if(!isOlderThan24Hours(article.getPubDate()) && !itemStorageService.getArticleById(key).isEmpty()) {
                itemStorageService.saveArticles(article,key);
                log.info("Stored Article Key: {} in Redis",key);
            }else{
                log.info("Skipping Article Key: {}",key);
            }

        } catch (JsonProcessingException e) {
            log.error("Failed to parse message:{}",message);
        }
    }

    private boolean isOlderThan24Hours(String pubDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        ZonedDateTime publishedAt = ZonedDateTime.parse(pubDate, formatter);
        ZonedDateTime now = ZonedDateTime.now();
        return publishedAt.isBefore(now.minusHours(24));
    }
}
