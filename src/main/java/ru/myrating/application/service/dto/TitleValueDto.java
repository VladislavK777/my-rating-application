package ru.myrating.application.service.dto;

import java.io.Serializable;

public class TitleValueDto implements Serializable {
    private String title;
    private Object text;

    public TitleValueDto() {
    }

    public TitleValueDto(String title, Object text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getText() {
        return text;
    }

    public void setText(Object text) {
        this.text = text;
    }
}
