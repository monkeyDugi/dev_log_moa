package com.devlogmoa.util;

import com.devlogmoa.domain.blog.BlogContents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public class CommonRepository {

    public static void saveIfNullId(JpaRepository repository, BlogContents blogContents, LocalDate pubDate) {
        if (blogContents.getId() == null) {
            repository.save(blogContents);
        } else {
            // 수정일자 업데이트
            blogContents.updatePubDate(pubDate);
        }
    }
}
