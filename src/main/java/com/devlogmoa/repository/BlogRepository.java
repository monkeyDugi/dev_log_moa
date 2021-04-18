package com.devlogmoa.repository;

import com.devlogmoa.domain.Blog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Repository
public class BlogRepository {

    private final EntityManager em;

    public void save(Blog blog) {
        em.persist(blog);
    }

    public Blog findByLink(String link) {
        String sql = "select b From Blog b where b.link=:link";

        return (Blog) em.createQuery(sql)
                .setParameter("link", link)
                .getSingleResult();
    }
}
