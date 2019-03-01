package com.pixelstack.ims.common.exception;

public class UnAuthorizedException extends GlobalException {
    public UnAuthorizedException(String message, int code) {
        super(message, code);
    }
}
