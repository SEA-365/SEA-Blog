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
    SAVE_OK(1000, "保存成功！"),
    SAVE_ERROR(1001, "保存失败！"),
    DELETE_OK(2000, "删除成功！"),
    DELETE_ERROR(2001, "删除失败！"),
    UPDATE_OK(3000, "修改成功！"),
    UPDATE_ERROR(3001, "修改失败！"),
    SELECT_OK(4000, "查询成功！"),
    SELECT_ERROR(4001, "查询失败！");

    private final Integer code;
    private final String description;
}
