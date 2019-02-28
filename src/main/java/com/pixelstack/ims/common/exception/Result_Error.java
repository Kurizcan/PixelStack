package com.pixelstack.ims.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Setter
@Getter
public class Result_Error extends Result
{
    /**
     * 结果返回形式包括：
     * 错误内容
     * 自定义错误码等
     */

    private String error;
    private int code;

    public Result_Error(String error, int code, int state)
    {
        super(code, error);
        this.error = error;
        this.code = code;

    }

    public enum ErrorCode {

        USER_NOT_FOUND(40401),              // 用户不存在
        USER_ALREADY_EXIST(40001),          // 用户已存在
        INSERT_ERROR(50001),                // 插入数据库失败
        UPDATE_ERROR(50002),                // 更新数据失败
        UNAUTHORIZATION_NOTOKEN(40101),     // 用户未进行认证
        UNAUTHORIZATION_NOUSER(40102),      // token 无指定用户
        UNAUTHORIZATION_OUT_OF_DATE(40103), // token 已经过期
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
