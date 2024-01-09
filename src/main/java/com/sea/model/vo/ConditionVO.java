package com.sea.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 一些查询条件，例如：前端分页查询请求，需要封装 页码 和 每页记录条数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "查询条件")
public class ConditionVO {

    @ApiModelProperty(name = "pageNum", value = "页码", dataType = "Long")
    private Integer pageNum;

    @ApiModelProperty(name = "pageSize", value = "每页记录条数", dataType = "Long")
    private Integer pageSize;

    @ApiModelProperty(name = "keywords", value = "搜索内容", dataType = "String")
    private String keywords;

    @ApiModelProperty(name = "categoryId", value = "分类id", dataType = "Integer")
    private Integer categoryId;

    @ApiModelProperty(name = "categoryName", value = "分类名称", dataType = "String")
    private String categoryName;

    @ApiModelProperty(name = "tagId", value = "标签id", dataType = "Integer")
    private Long tagId;

    @ApiModelProperty(name = "tagName", value = "标签名称", dataType = "String")
    private String tagName;

    @ApiModelProperty(name = "status", value = "文章状态", dataType = "Integer")
    private Integer status;

    @ApiModelProperty(name = "startTime", value = "开始时间", dataType = "LocalDateTime")
    private LocalDateTime startTime;

    @ApiModelProperty(name = "endTime", value = "结束时间", dataType = "LocalDateTime")
    private LocalDateTime endTime;

    @ApiModelProperty(name = "isDelete", value = "是否删除", dataType = "Integer")
    private Integer isDelete;

    @ApiModelProperty(name = "isTop", value = "是否置顶", dataType = "Integer")
    private Integer isTop;

}
