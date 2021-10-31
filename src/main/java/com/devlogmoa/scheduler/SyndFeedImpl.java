package com.devlogmoa.scheduler;

import com.devlogmoa.config.blog.BlogPropertiesDto;
import com.devlogmoa.util.CustomDateUtils;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

@Component
public class SyndFeedImpl implements SyndFeedable {

    @Override
    public FeedDtos read(BlogPropertiesDto blogPropertiesDto, String rssUrl) throws IOException {
        FeedDtos feedDtos = new FeedDtos();

        try {
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(rssUrl)));
            List<SyndEntry> entries = feed.getEntries();

            addRssDots(feedDtos, blogPropertiesDto, rssUrl, feed, entries);
        } catch (FeedException e) {
            System.out.println(e.getMessage() + ", rssUrl : " + rssUrl);
        }

        return feedDtos;
    }

    private void addRssDots(FeedDtos feedDtos, BlogPropertiesDto blogPropertiesDto, String rssUrl, SyndFeed feed, List<SyndEntry> entries) {
        for (SyndEntry entry : entries) {
            String url = blogPropertiesDto.getUrl(); // 블로그 주소
            String title = feed.getTitle();  // 블로그 타이틀
            String pubLink = entry.getLink();  // 게시글 주소
            String pubTitle = entry.getTitle(); // 게시글 타이틀
            LocalDate pubDate = CustomDateUtils.parseLocalDate(entry.getPublishedDate()); // 게시글 생성일

            feedDtos.add(new FeedDto(url, rssUrl, title, pubLink, pubTitle, pubDate));
        }
    }
}
