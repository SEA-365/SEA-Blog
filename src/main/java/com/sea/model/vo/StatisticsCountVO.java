package com.sea.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据统计值对象
 * @author: sea
 * @date: 2024/1/18 15:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "数据统计值对象")
public class StatisticsCountVO {
    /**
     * 文章总数
     */
    @ApiModelProperty(name = "articleCount", value = "文章总数", dataType = "Integer")
    private Integer articleCount;

    /**
     * 分类总数
     */
    @ApiModelProperty(name = "categoryCount", value = "分类总数", dataType = "Integer")
    private Integer categoryCount;

    /**
     * 用户总数
     */
    @ApiModelProperty(name = "userCount", value = "用户总数", dataType = "Integer")
    private Integer userCount;

    /**
     * 标签总数
     */
    @ApiModelProperty(name = "tagCount", value = "标签总数", dataType = "Integer")
    private Integer tagCount;


}
