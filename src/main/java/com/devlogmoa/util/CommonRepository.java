package com.devlogmoa.util;

import org.springframework.data.jpa.repository.JpaRepository;

public class CommonRepository {

    public static void saveIfNullId(JpaRepository repository, Object entity, Long id) {
        if (id == null) {
            repository.save(entity);
        }
    }
}