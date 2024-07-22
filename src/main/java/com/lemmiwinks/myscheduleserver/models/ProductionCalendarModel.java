package com.lemmiwinks.myscheduleserver.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getTypeText() {
        return typeText;
    }

    public String getNote() {
        return note;
    }

    public String getWeekDay() {
        return weekDay;
    }

    @Override
    public String toString() {
        return "ProductionCalendarModel{" +
                "id=" + id +
                ", date=" + date +
                ", typeId=" + typeId +
                ", typeText='" + typeText + '\'' +
                ", note='" + note + '\'' +
                ", weekDay='" + weekDay + '\'' +
                '}';
    }
}
