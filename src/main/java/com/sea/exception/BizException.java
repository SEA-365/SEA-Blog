package com.sea.exception;

import com.sea.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author: sea
 * @date: 2023/11/5 20:19
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BizException extends RuntimeException {
    private Integer code = StatusCodeEnum.FAIT.getCode();
    private String message;

    public BizException(String message) {
        this.message = message;
    }

    public BizException(StatusCodeEnum statusCodeEnum){
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getDescription();
    }
}
