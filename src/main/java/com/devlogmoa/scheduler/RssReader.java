package com.devlogmoa.scheduler;

import com.devlogmoa.config.blog.BlogProperties;
import com.devlogmoa.config.blog.BlogPropertiesDto;
import com.devlogmoa.util.CustomDateUtils;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class RssReader {

    public RssDtos ReadRss(BlogProperties blogProperties) throws IOException{
        List<BlogPropertiesDto> blogPropertiesDtos = blogProperties.getList();
        RssDtos rssDtos = new RssDtos();

        for (BlogPropertiesDto blogPropertiesDto : blogPropertiesDtos) {
            String rssUrl = blogPropertiesDto.getRssUrl(); // 블로그 rss 주소
            Read(rssDtos, blogPropertiesDto, rssUrl);
        }

        return rssDtos;
    }

    private void Read(RssDtos rssDtos, BlogPropertiesDto blogPropertiesDto, String rssUrl) throws IOException {
        SyndFeed feed;

        try {
            feed = new SyndFeedInput().build(new XmlReader(new URL(rssUrl)));
            List<SyndEntry> entries = feed.getEntries();

            addRssDots(rssDtos, blogPropertiesDto, rssUrl, feed, entries);
        } catch (FeedException e) {
            System.out.println(e.getMessage() + ", rssUrl : " + rssUrl);
        }
    }

    private void addRssDots(RssDtos rssDtos, BlogPropertiesDto blogPropertiesDto, String rssUrl, SyndFeed feed, List<SyndEntry> entries) {
        for (SyndEntry entry : entries) {
            String url = blogPropertiesDto.getUrl(); // 블로그 주소
            String title = feed.getTitle();  // 블로그 타이틀
            String pubLink = entry.getLink();  // 게시글 주소
            String pubTitle = entry.getTitle(); // 게시글 타이틀
            LocalDate pubDate = CustomDateUtils.parseLocalDate(entry.getPublishedDate()); // 게시글 생성일

            rssDtos.add(new RssDto(url, rssUrl, title, pubLink, pubTitle, pubDate));
        }
    }
}