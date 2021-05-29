package com.devlogmoa.scheduler;

import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.domain.blog.BlogContents;
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

    @Transactional
    public void createRssData(String url, String rssUrl) throws IOException, FeedException {
        SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(rssUrl)));
        List<SyndEntry> entries = feed.getEntries();

        String blogLink = feed.getLink();
        String blogTitle = feed.getTitle();

        Blog blog = createBlog(blogLink, rssUrl, blogTitle);
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
        BlogContents findLastBlogContents = blogContentsRepository.findTopByBlogIdOrderByPubDateDesc(blog.getId());

        for (SyndEntry entry : entries) {
            mergeBlogDetail(blog, entry, findLastBlogContents);
        }
    }

    private void mergeBlogDetail(Blog blog, SyndEntry entry, BlogContents findLastBlogDetail) {

        if (findLastBlogDetail == null || findLastBlogDetail.isNewPublish(entry.getPublishedDate(), entry.getLink())) {
            BlogContents blogDetail = BlogContents.createPublish(RssResponseDto.newRss(entry, blog));

            blogContentsRepository.save(blogDetail);
        } else {
            updatePublish(findLastBlogDetail, entry);
        }
    }

    private void updatePublish(BlogContents findLastBlogDetail, SyndEntry entry) {
        if (findLastBlogDetail.getPubLink().equals(entry.getLink())) {
            findLastBlogDetail.updatePublish(RssResponseDto.existNewRss(entry));
        }
    }
}
