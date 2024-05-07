package com.telemedicine.notification.enums;

public enum ErrorCodes {

    NOTIFICATION_INVALID_TYPE("TELE_001"),
    NOTIFICATION_ERROR("TELE_002"),
    TEMPLATE_NOT_FOUND("TELE_003"),
    TEMPLATE_ERROR("TELE_004");
    private String value;
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    private ErrorCodes(String value) {
        this.value = value;
    }
}