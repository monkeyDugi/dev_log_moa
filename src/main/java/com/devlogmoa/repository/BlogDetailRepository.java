package com.devlogmoa.repository;

import com.devlogmoa.domain.BlogDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
                       "From BlogDetail b " +
                      "where b.blog.id=:blogId " +
                      "order by b.pubDate desc";

        return (BlogDetail) em.createQuery(sql)
                .setParameter("blogId", blogId)
                .setFirstResult(0)
                .setMaxResults(1)
                .getSingleResult();
    }

}
