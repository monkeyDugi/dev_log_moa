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
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class RssReader {

    private final BlogRepository blogRepository;
    private final BlogDetailRepository blogDetailRepository;

    @Transactional
    public void searchRssData(String url) throws IOException, FeedException {
        String rssLink = url + "rss";

        SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(rssLink)));
        List<SyndEntry> entries = feed.getEntries();

        String mainLink = feed.getLink();
        String mainTitle = feed.getTitle();

        Blog blog = mergeBlog(rssLink, mainLink, mainTitle);
        mergeBlogDetail(blog, entries);
    }

    private void mergeBlogDetail(Blog blog, List<SyndEntry> entries) {
        BlogDetail findOneBlogDetail = blogDetailRepository.findByBlogIdMaxPurDate(blog.getId());
        List<BlogDetail> blogDetails = new ArrayList<>();

        for (SyndEntry entry : entries) {

            if (findOneBlogDetail == null) {
                if (entry != null) {
                    BlogDetail blogDetail = new BlogDetail();
                    blogDetail.createPublish(entry.getLink(), entry.getPublishedDate(), entry.getTitle(), entry.getDescription().getValue(), blog);

                    blogDetails.add(blogDetail);
                    blogDetailRepository.save(blogDetails);
                }
            } else if (findOneBlogDetail.isNewPublish(entry.getPublishedDate())) {
                if (findOneBlogDetail.getPubLink().equals(entry.getLink())) {
                    findOneBlogDetail.updatePublish(entry.getPublishedDate(), entry.getTitle(), entry.getDescription().getValue());
                } else {
                    BlogDetail blogDetail = new BlogDetail();
                    blogDetail.createPublish(entry.getLink(), entry.getPublishedDate(), entry.getTitle(), entry.getDescription().getValue(), blog);

                    blogDetails.add(blogDetail);
                    blogDetailRepository.save(blogDetails);
                }
            }
        }
    }

    private Blog mergeBlog(String rssLink, String mainLink, String mainTitle) {
        Blog findByBlog = blogRepository.findByLink(mainLink);
        Blog blog = new Blog();

        if (findByBlog == null) {
            blog.setLink(mainLink);
            blog.setRssLink(rssLink);
            blog.setMainTitle(mainTitle);

            blogRepository.save(blog);

            return blog;
        }

        return findByBlog;
    }
}
