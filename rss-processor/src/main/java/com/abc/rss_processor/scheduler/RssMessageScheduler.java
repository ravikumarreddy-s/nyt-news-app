package com.abc.rss_processor.scheduler;

import com.abc.rss_processor.model.RssFeed;
import com.abc.rss_processor.service.RssMessagePublisher;
import com.abc.rss_processor.service.RssService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RssMessageScheduler {

    private RssService rssService;

    private RssMessagePublisher rssMessagePublisher;

    @Value("${rss.feed.url}")
    private String rssFeedURL;

    public RssMessageScheduler(RssService rssService,RssMessagePublisher rssMessagePublisher){
        this.rssMessagePublisher=rssMessagePublisher;
        this.rssService=rssService;
    }

    @Scheduled(cron = "0 */5 * ? * *")
    public void pollNewsFeed() throws Exception {
        RssFeed rssFeed=rssService.getFeed(rssFeedURL);
        rssMessagePublisher.sendMessageToTopic(rssFeed);
    }

}
