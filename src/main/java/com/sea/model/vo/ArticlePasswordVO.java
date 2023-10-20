package com.sea.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 访问加密文章值对象
 * @author: sea
 * @date: 2023/10/20 18:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "访问加密文章对象")
public class ArticlePasswordVO {
    @ApiModelProperty(name = "articleId", value = "文章id", dataType = "Long")
    private Long articleId;//文章id

    @ApiModelProperty(name = "articlePassword", value = "文章访问密码", dataType = "String")
    private String articlePassword;//文章访问密码
}
