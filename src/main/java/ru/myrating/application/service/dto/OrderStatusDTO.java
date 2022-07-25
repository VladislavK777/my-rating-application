package ru.myrating.application.service.dto;

import ru.myrating.application.domain.enumeration.OrderStatusEnum;

public class OrderStatusDTO {
    private String alias;
    private String name;

    public OrderStatusDTO(OrderStatusEnum status) {
        this.alias = status.name();
        this.name = status.getValue();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
