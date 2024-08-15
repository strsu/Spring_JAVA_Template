package com.example.template.global.error.exception;

import com.example.template.global.error.ErrorCode;

public class EntityAlreadyExistException extends BusinessException {

    public EntityAlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}