package ru.myrating.application.domain.enumeration;

public enum OrderStatusEnum {
    NEW("Новый"),
    PAID("Оплачен"),
    SENT("Отправлен"),
    FAULT("Ошибка");

    private final String value;

    OrderStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
