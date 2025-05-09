package com.abc.rss.api.controller;

import com.abc.rss.api.model.Item;
import com.abc.rss.api.service.RSSService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
public class ArticleController {

    private final RSSService rssService;

    public ArticleController(RSSService rssService) {
        this.rssService = rssService;
    }

    @GetMapping
    public List<Item> getNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sort
    ) {
        boolean sortDesc = sort.equalsIgnoreCase("desc");
        return rssService.getArticles(page, size, sortDesc);
    }
}
