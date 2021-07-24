package com.devlogmoa.scheduler;

import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.domain.blog.BlogContents;
import com.devlogmoa.domain.blog.ContentsStatus;
import com.devlogmoa.domain.blog.UsageStatus;
import com.devlogmoa.repository.blog.BlogContentsRepository;
import com.devlogmoa.repository.blog.BlogRepository;
import com.devlogmoa.web.dto.response.rss.RssResponseDto;
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
import java.util.List;

@RequiredArgsConstructor
@Component
public class RssReader {

    private final BlogRepository blogRepository;
    private final BlogContentsRepository blogContentsRepository;

    public static ContentsStatus contentsStatus = ContentsStatus.DEFAULT;

    @Transactional
    public void createRssData(String url, String rssUrl) throws IOException, FeedException {
        SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(rssUrl)));
        List<SyndEntry> entries = feed.getEntries();

        String blogTitle = feed.getTitle();

        Blog blog = createBlog(url, rssUrl, blogTitle);
        createBlogContents(blog, entries);
    }

    private Blog createBlog(String blogLink, String blogRssLink, String blogTitle) {
        Blog findByBlog = blogRepository.findByBlogLink(blogLink);

        if (findByBlog == null) {
            Blog blog = Blog.createBlog(blogLink, blogRssLink, blogTitle);

            blogRepository.save(blog);

            return blog;
        }

        return findByBlog;
    }

    private void createBlogContents(Blog blog, List<SyndEntry> entries) {
        if (UsageStatus.isUse(blog.getUsageStatus())) {
            BlogContents findLastBlogContents = blogContentsRepository.findTopByBlogIdOrderByPubDateDesc(blog.getId());

            for (SyndEntry entry : entries) {
                createBlogContents(blog, entry, findLastBlogContents);
            }
        }
    }

    private void createBlogContents(Blog blog, SyndEntry entry, BlogContents findLastBlogContents) {
        if (findLastBlogContents == null || findLastBlogContents.isNewPublish(entry.getPublishedDate(), entry.getLink())) {
            BlogContents blogDetail = BlogContents.createPublish(RssResponseDto.newRss(entry, blog));
            blogContentsRepository.save(blogDetail);

            contentsStatus = ContentsStatus.NEW;
        }
    }
}
