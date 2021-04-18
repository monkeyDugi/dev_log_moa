package com.devlogmoa.util;

import com.devlogmoa.domain.Blog;
import com.devlogmoa.domain.BlogDetail;
import com.devlogmoa.repository.BlogDetailRepository;
import com.devlogmoa.repository.BlogRepository;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class RssReader {

    private final BlogRepository blogRepository;
    private final BlogDetailRepository blogDetailRepository;

    @Transactional
    public void createTestData(String url) throws IOException, FeedException {
        String rssLink = url + "rss";

        SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(rssLink)));
        List<SyndEntry> entries = feed.getEntries();

        String mainLink = feed.getLink();
        System.out.println("mainLink = " + mainLink);

        Blog blog = new Blog();
        blog.setLink(mainLink);
        blog.setRssLink(rssLink);
        blogRepository.save(blog);

        List<BlogDetail> blogDetails = new ArrayList<>();
        for (SyndEntry entry : entries) {
            String title = entry.getTitle();
            String link = entry.getLink();
            Date pubDate = entry.getPublishedDate();
            String description = entry.getDescription().getValue();

            System.out.println("title = " + title);

            BlogDetail blogDetail = new BlogDetail();
            blogDetail.setPostLink(link);
            blogDetail.setPubDate(pubDate);
            blogDetail.setTitle(title);
            blogDetail.setContents(description);
            blogDetail.setBlog(blog);

            blogDetails.add(blogDetail);
        }

        blogDetailRepository.save(blogDetails);
    }
}
