package ru.myrating.application.service.dto;

import java.io.Serializable;

public class OpenCloseDto implements Serializable {
    private boolean open;
    private boolean close;

    public OpenCloseDto() {
    }

    public OpenCloseDto(boolean open, boolean close) {
        this.open = open;
        this.close = close;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }
}
