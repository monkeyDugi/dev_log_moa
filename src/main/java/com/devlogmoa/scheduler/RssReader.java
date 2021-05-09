package com.devlogmoa.scheduler;

import com.devlogmoa.domain.Blog;
import com.devlogmoa.domain.BlogDetail;
import com.devlogmoa.repository.BlogDetailRepository;
import com.devlogmoa.repository.BlogRepository;
import com.devlogmoa.web.dto.RssDto;
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
    private final BlogDetailRepository blogDetailRepository;

    @Transactional
    public void createRssData(String url, String rssUrl) throws IOException, FeedException {
        SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(rssUrl)));
        List<SyndEntry> entries = feed.getEntries();

        String blogLink = feed.getLink();
        String blogTitle = feed.getTitle();

        Blog blog = createBlog(blogLink, rssUrl, blogTitle);
        createBlogDetail(blog, entries);
    }

    private Blog createBlog(String blogLink, String blogRssLink, String blogTitle) {
        Blog findByBlog = blogRepository.findByLink(blogLink);

        if (findByBlog == null) {
            Blog blog = Blog.createBlog(blogLink, blogRssLink, blogTitle);

            blogRepository.save(blog);

            return blog;
        }

        return findByBlog;
    }

    private void createBlogDetail(Blog blog, List<SyndEntry> entries) {
        BlogDetail findLastBlogDetail = blogDetailRepository.findTopByBlogIdOrderByPubDateDesc(blog.getId());

        for (SyndEntry entry : entries) {
            mergeBlogDetail(blog, entry, findLastBlogDetail);
        }
    }

    private void mergeBlogDetail(Blog blog, SyndEntry entry, BlogDetail findLastBlogDetail) {

        if (findLastBlogDetail == null || findLastBlogDetail.isNewPublish(entry.getPublishedDate(), entry.getLink())) {
            BlogDetail blogDetail = BlogDetail.createPublish(RssDto.newRss(entry, blog));

            blogDetailRepository.save(blogDetail);
        } else {
            updatePublish(findLastBlogDetail, entry);
        }
    }

    private void updatePublish(BlogDetail findLastBlogDetail, SyndEntry entry) {
        if (findLastBlogDetail.getPubLink().equals(entry.getLink())) {
            findLastBlogDetail.updatePublish(RssDto.existNewRss(entry));
        }
    }
}
