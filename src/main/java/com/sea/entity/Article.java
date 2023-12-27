package com.sea.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/** 文章表
 * @author: sea
 * @date: 2023/10/20 15:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_article")
public class Article {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;//文章id
    private String author;//文章作者

    //    private Long tagId;//标签id,一篇文章可能有多个标签，一个标签也可能包含多篇文章，因此新建一张关联表
    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryId;//分类id

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;//文章作者用户id
    private String content;//文章内容
    private Integer countViews;//浏览量
    private Integer totalWords;//总字数
    private Integer likes;//点赞数
    private String title;//文章标题
    private String description;//文章描述
    private String imageUrl;//文章logo
    private Integer isTop;//是否置顶，0-否，1-是，默认0
    @TableLogic
    private Integer isDelete;//是否删除，逻辑删除，0-否，1-是，默认0
    private Integer status;//文章状态，1-发布，2-密码，3-草稿，默认1
    private String password;//文章访问密码
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//修改时间

    @TableField(exist = false)//仅方便前端展示，数据库中并不存在该字段
    private List<Tag> tagList;//文章标签list

    @TableField(exist = false)
    private String categoryName;//方便前端展示，增加该属性，数据库表中不增加；
}
