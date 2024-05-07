package com.telemedicine.notification.enums;

public enum NotificationType {
    EMAIL("EMAIL");
    private String value;
    NotificationType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
