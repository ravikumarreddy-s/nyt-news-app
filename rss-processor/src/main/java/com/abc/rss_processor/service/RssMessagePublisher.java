package com.abc.rss_processor.service;

import com.abc.rss_processor.model.RssFeed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class RssMessagePublisher {
    @Autowired
    private KafkaTemplate<String,Object> template;

    public void sendMessageToTopic(RssFeed rssFeed){
        for (RssFeed.Item item : rssFeed.getChannel().getItems()) {
            String key = generateIdFromLink(item.getLink());
            CompletableFuture<SendResult<String,Object>> future= template.send("nyt.rss.articles",key,item);
            future.whenComplete((result,ex)->{
                if(ex==null){
                    log.info("Message sent: {}  offset: {}",rssFeed,result.getRecordMetadata().offset());
                }else{
                    log.error("Unable to send message:{} error:{}",rssFeed,result.getRecordMetadata().offset());
                }
            });
        }
    }

    private String generateIdFromLink(String link) {
        return Integer.toHexString(link.hashCode());
    }


}
