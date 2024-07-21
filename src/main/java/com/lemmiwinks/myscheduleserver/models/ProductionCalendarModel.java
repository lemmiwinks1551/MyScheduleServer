package com.lemmiwinks.myscheduleserver.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity // модель, которая отвечает за таблицу production_calendar
public class ProductionCalendarModel {

    @Id // аннотация уникального идентификатора
    @GeneratedValue(strategy = GenerationType.IDENTITY) // генерирует новое значение при добавлении записи
    private int id;
    private Date date;
    private int typeId;
    private String typeText;
    private String note;
    private String weekDay;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
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
