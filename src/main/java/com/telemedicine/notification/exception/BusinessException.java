package com.telemedicine.notification.exception;

import com.telemedicine.notification.enums.ErrorCodes;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.validation.Path;
import java.util.Map;

@Data
public class BusinessException extends RuntimeException{

    private final HttpStatus status;
    private String message;
    private String code;
    private Map<Path, String> validationData;
    private ErrorCodes errorCode;
    private Object[] args;

    public BusinessException(final ErrorCodes errorCode, final HttpStatus status) {
        super();
        this.status = status;
        this.errorCode = errorCode;
        this.message = errorCode.name();
        this.code = errorCode.getValue();
    }

    public BusinessException(final Map<Path, String> data, final HttpStatus status) {
        super();
        this.validationData = data;
        this.status = status;
    }

}

