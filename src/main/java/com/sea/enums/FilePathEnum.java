package com.sea.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilePathEnum {

    AVATAR("uploadFile/avatar/", "头像路径"),

    ARTICLE("uploadFile/article/", "文章图片路径"),

    VOICE("uploadFile/voice/", "音频路径"),

    MD("uploadFile/markdown/", "md文件路径");

    private final String path;

    private final String desc;

}
