package com.sea.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

    /**
     * 近7天发文章数据
     */
    @ApiModelProperty(name = "articlePast7Day", value = "近7天发文章数据", dataType = "ArrayList<HashMap<String, Integer>>")
    private ArrayList<HashMap<String, Integer>> articlePast7Day;

    /**
     * 文章分类占比
     */
    @ApiModelProperty(name = "articleCategoryStatistics", value = "文章分类占比", dataType = "")
    private ArrayList<HashMap<String, Integer>> articleCategory;

    /**
     * 近7天访问量
     */
    @ApiModelProperty(name = "visitPast7Day", value = "近7天发文章数据", dataType = "ArrayList<HashMap<String, Integer>>")
    private ArrayList<HashMap<String, Integer>> visitPast7Day;


    /**
     * 标签统计数据
     */
    @ApiModelProperty(name = "tagStatistics", value = "标签统计数据", dataType = "ArrayList<TagStatisticsVO>")
    private ArrayList<TagStatisticsVO> tagStatistics;

    /**
     *
     */



}
