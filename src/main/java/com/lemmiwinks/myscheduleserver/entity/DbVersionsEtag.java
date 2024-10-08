package com.lemmiwinks.myscheduleserver.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class DbVersionsEtag {
    // Содержит данные о версии и ETag для таблицы.
    @Id
    @GeneratedValue
    private int id;
    private String tableName;
    private String etag;
    private LocalDateTime lastUpdate;
}
