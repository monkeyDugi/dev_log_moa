package com.devlogmoa.util;

import org.springframework.data.jpa.repository.JpaRepository;

public class CommonRepository {

    public static<T, ID> void saveIfNullId(JpaRepository<T, ID> repository, T entity, ID id) {
        if (id == null) {
            repository.save(entity);
        }
    }
}