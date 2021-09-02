package com.devlogmoa.scheduler;

import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.domain.blog.BlogContents;
import com.devlogmoa.domain.blog.ContentsStatus;
import com.devlogmoa.domain.blog.UsageStatus;
import com.devlogmoa.repository.blog.BlogContentsRepository;
import com.devlogmoa.repository.blog.BlogRepository;
import com.devlogmoa.util.CommonRepository;
import com.devlogmoa.util.CustomDateUtils;
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
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class RssReader {

    private final BlogRepository blogRepository;
    private final BlogContentsRepository blogContentsRepository;

    private ContentsStatus contentsStatus = ContentsStatus.DEFAULT;

    @Transactional
    public void createRssData(String url, String rssUrl) throws IOException {
        SyndFeed feed;

        try {
            feed = new SyndFeedInput().build(new XmlReader(new URL(rssUrl)));
            List<SyndEntry> entries = feed.getEntries();

            String blogTitle = feed.getTitle();

            Blog blog = createBlog(url, rssUrl, blogTitle);
            createBlogContents(blog, entries);
        } catch (FeedException e) {
            System.out.println(e.getMessage() + ", rssUrl : " + rssUrl);
        }
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

    /**
     * rss 파싱한 데이터를 저장 및 업데이트한다.
     * @param blog : 해당 블로그 정보
     * @param entry : rss 데이터
     * @param findLastBlogContents : 해당 블로그 최신 글 TOP 1개
     */
    protected void createBlogContents(Blog blog, SyndEntry entry, BlogContents findLastBlogContents) {
        if (findLastBlogContents == null) {
            BlogContents blogDetail = BlogContents.createPublish(RssResponseDto.newRss(entry, blog));
            blogContentsRepository.save(blogDetail);

            contentsStatus = ContentsStatus.NEW;
        } else {
            LocalDate pubDate = CustomDateUtils.parseLocalDate(entry.getPublishedDate());

            if (findLastBlogContents.isNewPublish(pubDate)) {
                BlogContents findBlogContents = blogContentsRepository.findByPubLink(entry.getLink())
                        .orElseGet(() -> BlogContents.createPublish(RssResponseDto.newRss(entry, blog)));

                findBlogContents.updatePubDate(pubDate);
                CommonRepository.saveIfNullId(blogContentsRepository, findBlogContents, findBlogContents.getId());

                contentsStatus = ContentsStatus.NEW;
            }
        }
    }

    /**
     * 신규 컨텐츠가 있으면 true를 반환하고, 상태를 DEFAULT로 초기화.
     * @return boolean
     */
    public boolean isContentsStatus() {
        if (contentsStatus == ContentsStatus.NEW) {
            contentsStatus = ContentsStatus.DEFAULT;

            return true;
        }

        return false;
    }

    ContentsStatus test_getContentsStatus() {
        return contentsStatus;
    }

    void test_setContentsStatus(ContentsStatus contentsStatus) {
        this.contentsStatus = contentsStatus;
    }
}