package com.sea.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态码
 * @author: sea
 * @date: 2023/10/20 17:04
 */
@AllArgsConstructor
@Getter
public enum StatusCodeEnum {

    SUCCESS(20000, "操作成功！"),
    FAIL(50000, "操作失败！");


    private final Integer code;
    private final String description;
}
