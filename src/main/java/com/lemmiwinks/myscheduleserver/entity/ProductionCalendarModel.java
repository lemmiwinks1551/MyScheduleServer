package com.lemmiwinks.myscheduleserver.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity // модель, которая отвечает за таблицу production_calendar
public class ProductionCalendarModel {
    @Id // аннотация уникального идентификатора
    @GeneratedValue(strategy = GenerationType.IDENTITY) // генерирует новое значение при добавлении записи
    private int id;
    private String date;
    private int typeId;
    private String typeText;
    private String note;
    private String weekDay;
}
