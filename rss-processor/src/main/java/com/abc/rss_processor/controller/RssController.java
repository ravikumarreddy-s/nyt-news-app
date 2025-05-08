package com.abc.rss_processor.controller;

import com.abc.rss_processor.model.RssFeed;
import com.abc.rss_processor.service.RssMessagePublisher;
import com.abc.rss_processor.service.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RssController {

    @Autowired
    private RssService rssService;

    @Autowired
    private RssMessagePublisher rssMessagePublisher;

    @GetMapping("/rss-json")
    public RssFeed getRssAsJson(@RequestParam String url) throws Exception {
        RssFeed rssFeed=rssService.getFeed(url);
        rssMessagePublisher.sendMessageToTopic(rssFeed);
        return rssFeed;
    }
}