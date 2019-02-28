package com.pixelstack.ims.common.exception;

public abstract class Result {
    private int status;
    private String message;

    public Result(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
