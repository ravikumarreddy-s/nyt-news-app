package com.abc.rss_processor.service;

import com.abc.rss_processor.model.RssFeed;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.net.URL;

@Service
public class RssService {
    public RssFeed getFeed(String url) throws Exception {
        URL rssUrl = new URL(url);
        JAXBContext jaxbContext = JAXBContext.newInstance(RssFeed.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (RssFeed) unmarshaller.unmarshal(rssUrl);
    }
}