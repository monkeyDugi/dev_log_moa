package com.devlogmoa.scheduler;

import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.domain.blog.BlogContents;
import com.devlogmoa.domain.blog.UsageStatus;
import com.devlogmoa.repository.blog.BlogContentsRepository;
import com.devlogmoa.repository.blog.BlogRepository;
import com.devlogmoa.util.CommonRepository;
import com.devlogmoa.web.dto.response.rss.RssResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class RssStorage {

    private final BlogRepository blogRepository;
    private final BlogContentsRepository blogContentsRepository;

    /**
     * @param syndFeedDtos
     * Blog 데이터 생성
     * 생성된 Blog 데이터로 -> BlogContents 데이터 생성
     */
    public void save(SyndFeedDtos syndFeedDtos) {
        List<FeedDtos> feedDtosList = syndFeedDtos.getFeedDtos();
        for (FeedDtos feedDtos : feedDtosList) {
            try {
                Blog blog = saveBlog(feedDtos.firstFeed());
                saveBlogContents(blog, feedDtos);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage() + "rss 정보를 가져오지 못했습니다. rss 주소를 확인 해주세요");
            }
        }
    }

    private Blog saveBlog(FeedDto feedDto) {
        String blogLink = feedDto.getUrl();
        String blogRssLink = feedDto.getRssUrl();
        String blogTitle = feedDto.getTitle();

        Blog findBlog = blogRepository.findByBlogLink(blogLink);
        if (findBlog == null) {
            Blog blog = Blog.createBlog(blogLink, blogRssLink, blogTitle);

            blogRepository.save(blog);

            return blog;
        }

        return findBlog;
    }

    private void saveBlogContents(Blog blog, FeedDtos feedDtos) {
        if (UsageStatus.isUse(blog.getUsageStatus())) {
            BlogContents findLastBlogContents = blogContentsRepository.findTopByBlogIdOrderByPubDateDesc(blog.getId());

            List<FeedDto> feedDtosList = feedDtos.getFeedDtos();
            for (FeedDto feedDto : feedDtosList) {
                saveBlogContents(blog, feedDto, findLastBlogContents);
            }
        }
    }

    private void saveBlogContents(Blog blog, FeedDto feedDto, BlogContents findLastBlogContents) {
        if (findLastBlogContents == null) {
            BlogContents blogContents = BlogContents.createPublish(RssResponseDto.newRss(feedDto, blog));
            blogContentsRepository.save(blogContents);
        } else {
            LocalDate pubDate = feedDto.getPubDate();
            String pubLink = feedDto.getPubLink();

            if (findLastBlogContents.isNewPublish(pubDate)) {
                BlogContents findBlogContents = blogContentsRepository.findByPubLink(pubLink)
                        .orElseGet(() -> BlogContents.createPublish(RssResponseDto.newRss(feedDto, blog)));

                findBlogContents.updatePubDate(pubDate);
                CommonRepository.saveIfNullId(blogContentsRepository, findBlogContents, findBlogContents.getId());
            }
        }
    }
}
