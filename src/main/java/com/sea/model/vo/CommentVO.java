package com.sea.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 评论信息值对象 - 前端新增评论时，需要封装的信息
 * 注意：实体类的字段名称与数据库中的字段必须对应，
 *  例如：comment_reply_id <====> commentRelyId 进行对应；
 * @author: sea
 * @date: 2023/10/19 15:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "评论信息对象")
public class CommentVO {
    @NotNull(message = "文章id不能为空！")
    @ApiModelProperty(name = "articleId", value = "文章id", dataType = "Long")
    private Long articleId;//文章id

    @NotBlank(message = "文章作者不能为空！")
    @ApiModelProperty(name = "articleAuthor", value = "文章作者", dataType = "String")
    private String articleAuthor;//文章作者

    @NotNull(message = "评论创建者id不能为空！")
    @ApiModelProperty(name = "commentCreateId", value = "评论创建者id", dataType = "Long")
    private Long commentCreateId;//评论创建者id

    @ApiModelProperty(name = "commentRelyId", value = "被评论回复者id", dataType = "Long")
    private Long commentReplyId;//被评论回复者id

    private LocalDateTime commentCreateTime;//评论创建时间

    @NotBlank(message = "评论内容不能为空！")
    @ApiModelProperty(name = "commentContent", value = "评论内容", dataType = "String")
    private String commentContent;//评论内容

    @NotNull(message = "isDelete不能为空！")
    @ApiModelProperty(name = "isDelete", value = "评论是否被删除，0-否，1-是，默认0", dataType = "Integer")
    private Integer isDelete;//评论是否被删除，评论是否被删除，0-否，1-是，默认0

    @NotNull(message = "isReview不能为空！")
    @ApiModelProperty(name = "isReview", value = "评论是否通过审核，0-否，1-是，默认0", dataType = "Integer")
    private Integer isReview;//评论是否通过审核，0-否，1-是，默认0
}
