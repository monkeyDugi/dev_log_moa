package com.devlogmoa.repository;

import com.devlogmoa.domain.Blog;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@RequiredArgsConstructor
@Repository
public class BlogRepository {

    private final EntityManager em;

    public void save(Blog blog) {
        em.persist(blog);
    }

    public Blog findByLink(String link) {
        String sql = "select b " +
                       "From Blog b " +
                      "where b.link=:link";

        try {
            return em.createQuery(sql, Blog.class)
                    .setParameter("link", link)
                    .getSingleResult();
        } catch (EmptyResultDataAccessException | NoResultException e) {
            return null;
        }
    }

}
