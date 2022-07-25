package ru.myrating.application.domain.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum OrderStatusEnum {
    NEW("Новый", true),
    PAID("Оплачен", true),
    SENT("Отправлен", false),
    FAULT("Ошибка заказа", false);

    private final String value;
    private final boolean isVisible;

    OrderStatusEnum(String value, boolean isVisible) {
        this.value = value;
        this.isVisible = isVisible;
    }

    public String getValue() {
        return value;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public static List<OrderStatusEnum> getOrderStatusForFront() {
        List<OrderStatusEnum> list = new ArrayList<>();
        for (OrderStatusEnum status : values()) {
            if (status.isVisible)
                list.add(status);
        }
        return list;
    }
}
