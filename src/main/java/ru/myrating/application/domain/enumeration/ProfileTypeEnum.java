package ru.myrating.application.domain.enumeration;

public enum ProfileTypeEnum {
    LEGAL_ENTITY("Юридическое лицо"),
    INDIVIDUAL_ENTITY("Физическое лицо");

    private final String value;

    ProfileTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
