package com.devlogmoa.repository;

import com.devlogmoa.domain.BlogDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BlogDetailRepository {

    private final EntityManager em;

    public void save(List<BlogDetail> blogDetails) {
        for (BlogDetail blogDetail : blogDetails) {
            em.persist(blogDetail);
        }
    }

    public BlogDetail findByBlogIdMaxPurDate(Long blogId) {
        String sql = "select b " +
                       "from BlogDetail b " +
                      "where b.blog.id=:blogId " +
                      "order by b.pubDate desc";

        try {
            return em.createQuery(sql, BlogDetail.class)
                    .setParameter("blogId", blogId)
                    .setFirstResult(0)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (EmptyResultDataAccessException | NoResultException e) {
            return new BlogDetail();
        }
    }

    public List<BlogDetail> findAll() {
        String sql = "select b from BlogDetail b join fetch b.blog order by b.pubDate desc";

        return em.createQuery(sql, BlogDetail.class)
                .setFirstResult(0)
                .setMaxResults(20)
                .getResultList();
    }
}
