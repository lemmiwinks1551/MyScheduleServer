package com.lemmiwinks.myscheduleserver.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class DbVersionsEtag {
    // Содержит данные о версии и ETag для таблицы.

    @Id
    @GeneratedValue
    private int id;
    private String tableName;
    private String etag;
    private LocalDateTime lastUpdate;

    public String getTableName() {
        return tableName;
    }

    public String getEtag() {
        return etag;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
}
