package com.telemedicine.notification.enums;

public enum EmailTemplateType {
    OTP_VERIFICATION("verify_otp.ftl");
    private String value;
    EmailTemplateType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
