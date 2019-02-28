package com.pixelstack.ims.common.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result_Success extends Result {

    public Result_Success(int status, String message) {
        super(status, message);
    }

}
