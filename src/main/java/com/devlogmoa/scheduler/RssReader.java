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

    public static ContentsStatus contentsStatus = ContentsStatus.DEFAULT;

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
        // 서비스 첫 오픈 시 데이터 없을 때(첫 스케줄링)
        if (findLastBlogContents == null) {
            // rss에서 가져온 신규 컨텐츠 생성
            BlogContents blogDetail = BlogContents.createPublish(RssResponseDto.newRss(entry, blog));
            // 신규 컨텐츠 저장
            blogContentsRepository.save(blogDetail);

            // 신규 여부 상태 NEW
            contentsStatus = ContentsStatus.NEW;
        // 두 번째 스케줄링 부터는 여기
        } else {
            // rss에서 가져온 하나의 컨테츠 수정일자
            LocalDate pubDate = CustomDateUtils.parseLocalDate(entry.getPublishedDate());

            // 신규이거나 수정이거나
            if (findLastBlogContents.isNewPublish(pubDate)) {
                // 수정이면 이미 존재하는 컨텐츠이므로 find
                BlogContents findBlogContents = blogContentsRepository.findByPubLink(entry.getLink())
                        // 신규이면 find가 null이므로 신규 컨텐츠 생성
                        .orElseGet(() -> BlogContents.createPublish(RssResponseDto.newRss(entry, blog)));

                // id가 null이면 신규 컨텐츠이므로 save하는 로직
                CommonRepository.saveIfNullId(blogContentsRepository, findBlogContents, pubDate);

                // 신규 여부 상태 NEW
                contentsStatus = ContentsStatus.NEW;
            }
        }
    }
}