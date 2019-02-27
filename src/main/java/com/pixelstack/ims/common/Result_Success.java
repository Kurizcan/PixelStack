package com.pixelstack.ims.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result_Success {
    private int status = 200;
    private String message;

    public Result_Success(String message) { this.message = message;}

}
