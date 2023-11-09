package com.sea.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 文章标签关联表
 * @author: sea
 * @date: 2023/11/5 14:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_article_tag")
public class ArticleTag {
    //这个注解告诉 MyBatis-Plus 框架：
    //      1.ArticleTag 类中的 id 属性将与数据库表的主键字段 "id" 相关联，并且使用自动生成的主键值。
    //      2.这允许你在插入新记录时不必手动指定主键值，数据库会自动处理。
    @TableId(value = "id", type = IdType.AUTO)
    Long id;//关联表id
    Long articleId;//文章id
    Long tagId;//标签id
}
