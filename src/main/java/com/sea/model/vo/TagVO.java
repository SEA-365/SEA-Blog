package com.sea.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 标签信息值对象 - 前端新增标签时，需要封装的信息
 * @author: sea
 * @date: 2023/10/19 15:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "标签信息对象")
public class TagVO {
    @ApiModelProperty(name = "id", value = "标签id", dataType = "Long")
    private Long id;//标签id

    @NotBlank(message = "标签名称不能为空！")
    @ApiModelProperty(name = "tagName", value = "标签名称", dataType = "String")
    private String tagName;//标签名称
}
