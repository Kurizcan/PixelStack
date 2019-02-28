package com.pixelstack.ims.common.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestExceptionHandler
{
    private static Logger logger = (Logger) LogManager.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result_Error handleResourceNotFoundException(NotFoundException e)
    {
        logger.error(e.getMessage(), e);                // 打印错误信息
        return new Result_Error(e.getMessage(), e.getCode(), new ResponseEntity<>(HttpStatus.NOT_FOUND).getStatusCodeValue());
    }

    @ExceptionHandler(value = InternalErrorException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result_Error handleResourceInternalServerErrorException(InternalErrorException e) {
        logger.error(e.getMessage(), e);                // 打印错误信息
        return new Result_Error(e.getMessage(), e.getCode(), new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR).getStatusCodeValue());
    }


}
