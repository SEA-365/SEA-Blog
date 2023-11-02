package com.sea.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/** 文章表
 * @author: sea
 * @date: 2023/10/20 15:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_article")
public class Article {
    private Long id;//文章id
    private String author;//文章作者
    private Long tagId;//标签id
    private Long categoryId;//分类id
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
}
