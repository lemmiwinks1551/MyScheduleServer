package com.lemmiwinks.myscheduleserver.repository;

import com.lemmiwinks.myscheduleserver.entity.DbVersionsEtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbVersionsEtagRepository extends JpaRepository<DbVersionsEtag, Integer> {

    // Метод для получения записи по имени таблицы.
    DbVersionsEtag findByTableName(String tableName);
}
