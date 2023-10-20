package com.sea.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: sea
 * @date: 2023/10/20 17:26
 */
@AllArgsConstructor
@Getter
public enum ArticleStatusEnum {
    PUBLIC(1, "已发布"),

    SECRET(2, "密码"),

    DRAFT(3, "草稿");

    private final Integer code;
    private final String description;
}
