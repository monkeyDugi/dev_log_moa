package com.devlogmoa.scheduler;

import com.devlogmoa.config.blog.BlogProperties;
import com.devlogmoa.config.blog.BlogPropertiesDto;
import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.domain.blog.BlogContents;
import com.devlogmoa.domain.blog.ContentsStatus;
import com.devlogmoa.domain.blog.UsageStatus;
import com.devlogmoa.mail.MailService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class RssReader {

    private final BlogRepository blogRepository;
    private final BlogContentsRepository blogContentsRepository;
    private final MailService mailService;

    @Transactional
    public void createRssData(BlogProperties blogProperties) throws IOException {
        ContentsStatus contentsStatus = ContentsStatus.OLD;

        for (BlogPropertiesDto blogPropertiesDto : blogProperties.getList()) {
            ContentsStatus rssDataContentsStatus = createRssData(blogPropertiesDto.getUrl(), blogPropertiesDto.getRssUrl());
            if (contentsStatus == ContentsStatus.OLD) {
                contentsStatus = rssDataContentsStatus;
            }
        }

        if (contentsStatus == ContentsStatus.NEW) {
            mailService.sendEmail();
        }
    }

    private ContentsStatus createRssData(String url, String rssUrl) throws IOException {
        SyndFeed feed;
        ContentsStatus contentsStatus = ContentsStatus.OLD;

        try {
            feed = new SyndFeedInput().build(new XmlReader(new URL(rssUrl)));
            List<SyndEntry> entries = feed.getEntries();

            String blogTitle = feed.getTitle();

            Blog blog = createBlog(url, rssUrl, blogTitle);
            contentsStatus = createBlogContents(blog, entries);
        } catch (FeedException e) {
            System.out.println(e.getMessage() + ", rssUrl : " + rssUrl);
        }

        return contentsStatus;
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

    private ContentsStatus createBlogContents(Blog blog, List<SyndEntry> entries) {
        ContentsStatus contentsStatus = ContentsStatus.OLD;

        if (UsageStatus.isUse(blog.getUsageStatus())) {
            BlogContents findLastBlogContents = blogContentsRepository.findTopByBlogIdOrderByPubDateDesc(blog.getId());

            for (SyndEntry entry : entries) {
                contentsStatus = createBlogContents(blog, entry, findLastBlogContents);
            }
        }

        return contentsStatus;
    }

    /**
     * rss 파싱한 데이터를 저장 및 업데이트한다.
     * @param blog : 해당 블로그 정보
     * @param entry : rss 데이터
     * @param findLastBlogContents : 해당 블로그 최신 글 TOP 1개
     */
    ContentsStatus createBlogContents(Blog blog, SyndEntry entry, BlogContents findLastBlogContents) {
        ContentsStatus contentsStatus = ContentsStatus.OLD;
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

        return contentsStatus;
    }
}