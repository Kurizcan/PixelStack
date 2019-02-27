package com.pixelstack.ims.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Setter
@Getter
public class Result_Error
{
    /**
     * 结果返回形式包括：
     * 错误内容
     * 自定义错误码等
     */

    private String error;
    private int code;
    private int state;

    public Result_Error(String error, int code, int state)
    {
        this.error = error;
        this.code = code;
        this.state = state;
    }

    public enum ErrorCode {

        USER_NOT_FOUND(40401),          // 用户不存在
        USER_ALREADY_EXIST(40001),      // 用户已存在
        INSERT_ERROR(50301),            // 插入数据库失败
        UPDATE_ERROR(50302),            // 更新数据失败
        ;

        private int code;

        public int getCode()
        {
            return code;
        }

        ErrorCode(int code)
        {
            this.code = code;
        }
    }

}
