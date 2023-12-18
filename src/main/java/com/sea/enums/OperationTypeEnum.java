package com.sea.enums;

import lombok.Getter;

/**
 * @author: sea
 * @date: 2023/12/18 16:29
 */
@Getter
public enum OperationTypeEnum {
    SYSTEM("SYSTEM", "系统操作（默认）"),
    LOGIN("LOGIN", "登录操作"),
    LOGOUT("LOGOUT", "登出操作"),
    INSERT("INSERT", "添加操作"),
    DELETE("DELETE", "删除操作"),
    UPDATE("UPDATE", "修改操作"),
    SELECT("SELECT", "查询操作");


    private String value;
    private String description;

    OperationTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
}
