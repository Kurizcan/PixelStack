package com.pixelstack.ims.common.exception;

public class InternalErrorException extends GlobalException {
    public InternalErrorException(String message, int code) {
        super(message, code);
    }
}
