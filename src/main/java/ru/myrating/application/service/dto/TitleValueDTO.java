package ru.myrating.application.service.dto;

import java.io.Serializable;

public class TitleValueDTO implements Serializable {
    private String title;
    private Object text;

    public TitleValueDTO() {
    }

    public TitleValueDTO(String title, Object text) {
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
