package com.sea.model.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: sea
 * @date: 2023/10/20 16:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "文章信息对象")
public class ArticleVO {
    @ApiModelProperty(name = "id", value = "文章id", dataType = "Long")
    private Long id;//文章id

    @NotBlank(message = "文章作者不能为空！")
    @ApiModelProperty(name = "author", value = "文章作者", dataType = "String")
    private String author;//文章作者

//    这三个参数在转换为文章信息时需要进行一些处理，其中:
//      (1)标签id可能会有多个，需要根据前端给出的tagList进行：1.未出现过的标签=>新建标签；2.标签名=>标签id的转换 3.文章与标签之间的关联;
//      (2)分类id一般是一个：1.未出现过得分类=>新建分类；2.分类名=>分类id的转换；
//    todo: (3)文章作者，需要获取当前登录用户的信息【这个等登录注册模块写完就能做了！！】
//    private Long tagId;//标签id
//    private Long categoryId;//分类id
//    private Long userId;//文章作者用户id
    @ApiModelProperty(name = "tagNames", value = "文章标签", dataType = "List<Integer>")
    private List<String> tagNames;//文章标签List

    @NotBlank(message = "文章内容不能为空！")
    @ApiModelProperty(name = "content", value = "文章内容", dataType = "String")
    private String content;//文章内容

//    todo：这三个参数应该计算统计得出，而不是直接前端给出
//    private Integer countViews;//浏览量
//    private Integer totalWords;//总字数
//    private Integer likes;/文章作者不能为空/点赞数

    @NotBlank(message = "文章标题不能为空")
    @ApiModelProperty(name = "title", value = "文章标题", dataType = "String")
    private String title;//文章标题

    @ApiModelProperty(name = "description", value = "文章描述", dataType = "String")
    private String description;//文章描述

    @ApiModelProperty(name = "imageUrl", value = "文章logo", dataType = "String")
    private String imageUrl;//文章logo

    @ApiModelProperty(name = "isTop", value = "是否置顶，0-否，1-是，默认0", dataType = "Integer")
    private Integer isTop;//是否置顶，0-否，1-是，默认0

    @ApiModelProperty(name = "status", value = "文章状态，1-发布，2-密码，3-草稿", dataType = "Integer")
    private Integer status;//文章状态，1-发布，2-密码，3-草稿

    @ApiModelProperty(name = "password", value = "文章访问密码", dataType = "String")
    private String password;//文章访问密码
}
