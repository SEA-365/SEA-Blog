package com.sea.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 文章评论表
 * @author: sea
 * @date: 2023/10/19 14:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_comment")
public class Comment {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;//评论id
    private Long articleId;//文章id
    private String articleAuthor;//文章作者
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentCreateId;//评论创建者id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentReplyId;//被评论回复者id
    @JsonFormat(timezone = "GMT+8",pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime commentCreateTime;//评论创建时间
    private String commentContent;//评论内容
    private Integer isDelete;//评论是否被删除
    private Integer isReview;//评论是否通过审核
}
