package com.sea.model.vo;

import lombok.Data;

/**
 * 标签统计数据
 * @author: sea
 * @date: 2024/1/19 20:45
 */
@Data
public class TagStatisticsVO {
    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 背景颜色
     */
    private String bgColor;
    /**
     * 颜色
     */
    private String color;
    /**
     * 数值
     */
    private String value;
}
